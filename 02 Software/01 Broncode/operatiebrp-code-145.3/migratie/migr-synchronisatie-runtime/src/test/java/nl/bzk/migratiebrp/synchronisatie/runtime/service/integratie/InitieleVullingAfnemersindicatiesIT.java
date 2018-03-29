/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;

public class InitieleVullingAfnemersindicatiesIT extends AbstractInitieleVullingIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("queueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    @Named("syncDalDataSource")
    private DataSource dataSource;

    @Inject
    @Named("queueSyncAntwoord")
    private Destination antwoordQueue;

    @Before
    public void setup() throws Exception {
        // Initiele vulling persoon
        final Lo3Persoonslijst lo3Persoonslijst = leesPl("iv-afnemersindicaties-persoon.xls");
        assertEquals("4363838497", lo3Persoonslijst.getActueelAdministratienummer());
        assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3Persoonslijst);
        assertTrue(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        assertFalse(komtAnummerActueelVoor("6709152545"));

        // Partijen en autorisatie
        assertTrue(komtPartijVoor("059901"));
        maakAutorisatieVoor("059901");
        assertTrue(komtPartijVoor("062601"));
        maakAutorisatieVoor("062601");
        assertTrue(komtPartijVoor("062701"));
        assertFalse(komtPartijVoor("888808"));
    }

    public int telAfnemersindicatiesVoorPersoon(final String anummer) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate.queryForObject(
                "select count(*) from autaut.persafnemerindicatie, kern.pers where pers.anr = ? and pers.id = persafnemerindicatie.pers",
                Integer.class,
                anummer);
    }

    private void maakAutorisatieVoor(final String partijCode) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Partij rol afnemer
        final Integer partijId = jdbcTemplate.queryForObject("select id from kern.partij where code = ?", Integer.class, partijCode);

        Integer partijRolId;
        try {
            partijRolId = jdbcTemplate.queryForObject("select id from kern.partijrol where partij = ? and rol = 1", Integer.class, partijId);
        } catch (final EmptyResultDataAccessException e) {
            jdbcTemplate.update("insert into kern.partijrol(partij, rol) values (?, 1)", partijId);
            partijRolId = jdbcTemplate.queryForObject("select id from kern.partijrol where partij = ? and rol = 1", Integer.class, partijId);
        }

        // Autorisatie
        final String levsautorisatieNaam = "lev-aut-test-" + partijCode;
        jdbcTemplate.update(
                "insert into autaut.levsautorisatie(stelsel, indmodelautorisatie, naam, datingang) values(2, false, ?, 20000101)",
                levsautorisatieNaam);
        final Integer leveringautorisatieId =
                jdbcTemplate.queryForObject("select id from autaut.levsautorisatie where naam = ?", Integer.class, levsautorisatieNaam);

        jdbcTemplate.update(
                "insert into autaut.toeganglevsautorisatie(geautoriseerde, levsautorisatie, afleverpunt) values(?, ?, 'gba')",
                partijRolId,
                leveringautorisatieId);

    }

    private SyncBericht verwerk(final String verzoekResource) throws IOException, JMSException {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

        // Initiele vulling afnemersindicaties
        final String input = IOUtils.toString(InitieleVullingAfnemersindicatiesIT.class.getResourceAsStream(verzoekResource));
        LOGGER.info("input: {}", input);
        jmsTemplate.send("iv.request", session -> {
            final Message message = session.createTextMessage(input);
            message.setStringProperty("iscBerichtReferentie", "messageId001");
            return message;
        });

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (final InterruptedException e) {
            // Ignore
        }
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        assertNotNull(message);
        LOGGER.info("Message: {}", message);
        final String text = ((TextMessage) message).getText();
        LOGGER.info("Message: {}", text);
        return SyncBerichtFactory.SINGLETON.getBericht(text);
    }

    @Test
    public void testEnkeleAfnemer() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-enkele-afnemer.xml");

        assertEquals(1, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));

        // Controleer IndAG
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final Map<String, Object> afnemerindicatie = jdbcTemplate.queryForMap(
                "select persafnemerindicatie.* from autaut.persafnemerindicatie, kern.pers where pers.anr = ? and pers.id = persafnemerindicatie.pers",
                "4363838497");
        System.out.println("Afnemerindicatie: " + afnemerindicatie);
        assertNotNull(afnemerindicatie.get("id"));

        assertNotNull(afnemerindicatie.get("pers"));
        assertNotNull(afnemerindicatie.get("partij"));
        assertNotNull(afnemerindicatie.get("levsautorisatie"));
        assertNull(afnemerindicatie.get("dataanvmaterieleperiode")); // Wordt niet gevuld bij IV
        assertNull(afnemerindicatie.get("dateindevolgen")); // Wordt niet gevuld bij IV
        assertEquals(true, afnemerindicatie.get("indag"));

        final List<Map<String, Object>> hisAfnemerindicatie = jdbcTemplate.queryForList(
                "select his_persafnemerindicatie.* from autaut.his_persafnemerindicatie where his_persafnemerindicatie.persafnemerindicatie = ? order by "
                        + "tsreg desc",
                afnemerindicatie.get("id"));
        assertEquals(2, hisAfnemerindicatie.size());

        Map<String, Object> hisAfnemerindicatieActueel = hisAfnemerindicatie.get(0);
        System.out.println("Historie afnemerindicatie (actueel record): " + hisAfnemerindicatieActueel);
        assertNotNull(hisAfnemerindicatieActueel.get("id"));
        assertNotNull(hisAfnemerindicatieActueel.get("persafnemerindicatie"));
        assertNotNull(hisAfnemerindicatieActueel.get("tsreg"));
        assertNull(hisAfnemerindicatieActueel.get("tsverval"));
        assertNull(hisAfnemerindicatieActueel.get("dienstinh")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieActueel.get("dienstverval")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieActueel.get("dataanvmaterieleperiode")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieActueel.get("dateindevolgen")); // Wordt niet gevuld bij IV

        Map<String, Object> hisAfnemerindicatieBeeindigd = hisAfnemerindicatie.get(1);
        System.out.println("Historie afnemerindicatie (beeindigd record): " + hisAfnemerindicatieBeeindigd);
        assertNotNull(hisAfnemerindicatieBeeindigd.get("id"));
        assertNotNull(hisAfnemerindicatieBeeindigd.get("persafnemerindicatie"));
        assertNotNull(hisAfnemerindicatieBeeindigd.get("tsreg"));
        assertNotNull(hisAfnemerindicatieBeeindigd.get("tsverval"));
        assertNull(hisAfnemerindicatieBeeindigd.get("dienstinh")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieBeeindigd.get("dienstverval")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieBeeindigd.get("dataanvmaterieleperiode")); // Wordt niet gevuld bij IV
        assertNull(hisAfnemerindicatieBeeindigd.get("dateindevolgen")); // Wordt niet gevuld bij IV
    }

    @Test
    public void testMeerdereAfnemer() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-meerdere-afnemers.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(2, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testAfnemerZonderAutorisatie() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-afnemer-zonder-autorisatie.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN011", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testNietBestaandeAfnemer() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-niet-bestaande-afnemer.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN010", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testDubbeleAfnemerActueel0() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-actueel-0.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals("AFN004", antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testDubbeleAfnemerActueel1() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-actueel-1.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN004", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testDubbeleAfnemerHistorisch0() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-historisch-0.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals("AFN004", antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testDubbeleAfnemerHistorisch1() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-historisch-1.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN004", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testNietBestaandePersoon() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-niet-bestaande-persoon.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        // Beide regels bevatten AFN009
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals("AFN009", antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN009", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testLegeStapel() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-lege-stapel.xml");

        assertEquals(2, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(1).getStatus());
        assertEquals(1, antwoord.getAfnemersindicaties().get(1).getStapelNummer());
        assertEquals("AFN002", antwoord.getAfnemersindicaties().get(1).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testHeelVeelHistorie() throws Exception {
        assertEquals(0, telAfnemersindicatiesVoorPersoon("4363838497"));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-heel-veel-historie.xml");

        assertEquals(1, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.TOEGEVOEGD, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals(null, antwoord.getAfnemersindicaties().get(0).getFoutmelding());

        assertEquals(1, telAfnemersindicatiesVoorPersoon("4363838497"));
    }

    @Test
    public void testLeegANummer() throws Exception {
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-leeg-anummer.xml");
        assertEquals(1, antwoord.getAfnemersindicaties().size());
        assertEquals(StatusType.FOUT, antwoord.getAfnemersindicaties().get(0).getStatus());
        assertEquals(0, antwoord.getAfnemersindicaties().get(0).getStapelNummer());
        assertEquals("AFN001", antwoord.getAfnemersindicaties().get(0).getFoutmelding());
    }
}
