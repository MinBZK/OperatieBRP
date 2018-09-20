/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class InitieleVullingAfnemersindicatiesIntegratieTest extends AbstractInitieleVullingIntegratieTest {

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
        Assert.assertEquals(Long.valueOf(4363838497L), lo3Persoonslijst.getActueelAdministratienummer());
        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3Persoonslijst);
        Assert.assertTrue(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        Assert.assertFalse(komtAnummerActueelVoor(6709152545L));

        // Partijen en autorisatie
        Assert.assertTrue(komtPartijVoor(59901));
        maakAutorisatieVoor(59901);
        Assert.assertTrue(komtPartijVoor(62601));
        maakAutorisatieVoor(62601);
        Assert.assertTrue(komtPartijVoor(62701));
        Assert.assertFalse(komtPartijVoor(888808));
    }

    public int telAfnemersindicatiesVoorPersoon(final long anummer) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate.queryForObject(
            "select count(*) from autaut.persafnemerindicatie, kern.pers where pers.anr = ? and pers.id = persafnemerindicatie.pers",
            Integer.class,
            anummer);
    }

    private void maakAutorisatieVoor(final int partijCode) {
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
        final String input = IOUtils.toString(InitieleVullingAfnemersindicatiesIntegratieTest.class.getResourceAsStream(verzoekResource));
        LOGGER.info("input: {}", input);
        jmsTemplate.send("iv.request", new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(input);
                message.setStringProperty("iscBerichtReferentie", "messageId001");
                return message;
            }
        });

        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            // Ignore
        }
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull(message);
        LOGGER.info("Message: {}", message);
        final String text = ((TextMessage) message).getText();
        LOGGER.info("Message: {}", text);
        return SyncBerichtFactory.SINGLETON.getBericht(text);
    }

    @Test
    public void testEnkeleAfnemer() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-enkele-afnemer.xml");

        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(null, antwoord.getLogging());
        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testMeerdereAfnemer() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-meerdere-afnemers.xml");

        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(null, antwoord.getLogging());
        Assert.assertEquals(2, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testAfnemerZonderAutorisatie() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-afnemer-zonder-autorisatie.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN011, logRegel.getSoortMeldingCode());
        Assert.assertEquals(1, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());
        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testNietBestaandeAfnemer() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-niet-bestaande-afnemer.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN010, logRegel.getSoortMeldingCode());
        Assert.assertEquals(1, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testDubbeleAfnemerActueel0() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-actueel-0.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN004, logRegel.getSoortMeldingCode());
        Assert.assertEquals(0, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testDubbeleAfnemerActueel1() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-actueel-1.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN004, logRegel.getSoortMeldingCode());
        Assert.assertEquals(1, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testDubbeleAfnemerHistorisch0() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-historisch-0.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN004, logRegel.getSoortMeldingCode());
        Assert.assertEquals(0, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testDubbeleAfnemerHistorisch1() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-dubbele-afnemer-historisch-1.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN004, logRegel.getSoortMeldingCode());
        Assert.assertEquals(1, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    @Ignore("Niet helemaal duidelijk wat we hier gaan doen; SyncFout of DLQ")
    public void testGeenPersoon() throws Exception {
        verwerk("iv-afnemersindicaties-test-geen-persoon.xml");
    }

    @Test
    public void testNietBestaandePersoon() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord =
                (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-niet-bestaande-persoon.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(2, antwoord.getLogging().size());
        // Beide regels bevatten AFN009
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN009, logRegel.getSoortMeldingCode());

        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
    }

    @Test
    public void testLegeStapel() throws Exception {
        Assert.assertEquals(0, telAfnemersindicatiesVoorPersoon(4363838497L));
        final AfnemersindicatiesAntwoordBericht antwoord = (AfnemersindicatiesAntwoordBericht) verwerk("iv-afnemersindicaties-test-lege-stapel.xml");

        Assert.assertEquals(StatusType.WAARSCHUWING, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getLogging().size());
        final LogRegel logRegel = antwoord.getLogging().get(0);
        Assert.assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN002, logRegel.getSoortMeldingCode());
        Assert.assertEquals(1, logRegel.getLo3Herkomst().getStapel());
        Assert.assertEquals(-1, logRegel.getLo3Herkomst().getVoorkomen());

        Assert.assertEquals(1, telAfnemersindicatiesVoorPersoon(4363838497L));
    }
}
