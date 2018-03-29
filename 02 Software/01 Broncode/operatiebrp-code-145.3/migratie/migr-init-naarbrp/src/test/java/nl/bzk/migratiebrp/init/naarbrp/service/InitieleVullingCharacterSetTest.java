/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockTextMessage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.init.naarbrp.repository.PortInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test de service voor het versturen van LO3-berichten.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:runtime-test-beans.xml", initializers = PortInitializer.class)
public class InitieleVullingCharacterSetTest {

    @Resource
    private DestinationManager manager;

    @Inject
    private InitieleVullingService service;

    @Inject
    private DataSource dataSource;

    @Before
    public void before() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE initvul.initvullingresultBU AS (SELECT * from initvul.initvullingresult) WITH DATA");
        jdbcTemplate.update("DELETE FROM initvul.initvullingresult");
        jdbcTemplate.update(
                "INSERT INTO initvul.initvullingresult"
                        + "(gbav_pl_id,"
                        + " bericht_inhoud,"
                        + " datum_opschorting,"
                        + " reden_opschorting,"
                        + " anummer,"
                        + " datumtijd_opname_in_gbav,"
                        + " berichtidentificatie,"
                        + " berichttype,"
                        + " gemeente_van_inschrijving,"
                        + " conversie_resultaat)"
                        + "  VALUES"
                        + "  (11,"
                        + "   '00497011770110010734505193701200099416914520210007Johanna0240004Maas0310008200401010320"
                        + "0041033033000460300410001V6110001E821000407628220008199409308230003PKA851000819750831861000"
                        + "819951001020110240004Maas030220240004Maas03300045003040510510004000163100030018510008197508"
                        + "3186100081994093007077681000819940930691000407627010001780100040003802001720070110113041000"
                        + "8710001P08129091000406270920008199603191010001W1030008200701091110013D\u00c8omaar\u0073traat1120"
                        + "0023111600062575RD7210001I851000820070109861000820070110', "
                        + "NULL, NULL, 7345051937, '2012-07-09 18:17:06.577', 184, 'Lg01', 762, 'TE_VERZENDEN')");
        jdbcTemplate.update(
                "INSERT INTO initvul.initvullingresult"
                        + "(gbav_pl_id,"
                        + " bericht_inhoud,"
                        + " datum_opschorting,"
                        + " reden_opschorting,"
                        + " anummer,"
                        + " datumtijd_opname_in_gbav,"
                        + " berichtidentificatie,"
                        + " berichttype,"
                        + " gemeente_van_inschrijving,"
                        + " conversie_resultaat)"
                        + "  VALUES"
                        + "  (12,"
                        + "   '00497011770110010129703510501200099416914520210007Johanna0240004Maas0310008200401010320"
                        + "0041033033000460300410001V6110001E821000407628220008199409308230003PKA851000819750831861000"
                        + "819951001020110240004Maas030220240004Maas03300045003040510510004000163100030018510008197508"
                        + "3186100081994093007077681000819940930691000407627010001780100040003802001720070110113041000"
                        + "8710001P08129091000406270920008199603191010001W1030008200701091110013Doemaar\u001dtraat1120"
                        + "0023111600062575RD7210001I851000820070109861000820070110', "
                        + "NULL, NULL, 1297035105, '2012-07-09 18:17:06.577', 184, 'La01', 762, 'TE_VERZENDEN')");

        service.setBatchPersoon(100);
        service.setBatchAutorisatie(100);
        service.setBatchAfnemersindicatie(100);
        service.setBatchProtocollering(10);
        service.setAantalProtocollering(1000);
    }

    @After
    public void after() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("DELETE from initvul.initvullingresult");
        jdbcTemplate.update("INSERT INTO initvul.initvullingresult SELECT * FROM initvul.initvullingresultBU");
        jdbcTemplate.update("DROP TABLE initvul.initvullingresultBU");
    }

    @Test
    public void leesBerichtEnControleerXmlParsing() throws JMSException, BerichtSyntaxException, OngeldigePersoonslijstException {
        service.setBatchPersoon(15);

        try {
            service.synchroniseerPersonen();
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List<MockTextMessage> messages = queue.getCurrentMessageList();
        Assert.assertEquals("Aantal berichten klopt niet!", 2, messages.size());

        final List<String> verwachtteIds = new ArrayList<>();
        verwachtteIds.add("1297035105");
        verwachtteIds.add("7345051937");

        for (final MockTextMessage message : messages) {
            final String berichtText = message.getText();
            System.out.println("BERICHT TEXT: " + berichtText);

            final SyncBericht bericht = SyncBerichtFactory.SINGLETON.getBericht(berichtText);

            assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());

            final SynchroniseerNaarBrpVerzoekBericht syncBericht = (SynchroniseerNaarBrpVerzoekBericht) bericht;

            final Lo3Persoonslijst pl = syncBericht.getLo3Persoonslijst();
            assertTrue(verwachtteIds.contains(pl.getActueelAdministratienummer()));
            verwachtteIds.remove(pl.getActueelAdministratienummer());

            Logging.initContext();
            if (Objects.equals(pl.getActueelAdministratienummer(), "1297035105")) {
                try {
                    new Lo3SyntaxControle().controleer(Lo3Inhoud.parseInhoud(syncBericht.getLo3PersoonslijstAlsTeletexString()));
                    fail("OngeldigePersoonslijstException verwacht");
                } catch (final OngeldigePersoonslijstException e) {
                    final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
                    assertEquals(1, logRegels.size());
                    assertEquals(
                            "LogRegel[herkomst=Lo3Herkomst[categorie=08,stapel=0,voorkomen=0],severity=ERROR,"
                                    + "soortMeldingCode=TELETEX,lo3ElementNummer=ELEMENT_1110]",
                            logRegels.iterator().next().toString());
                }
            } else {
                new Lo3SyntaxControle().controleer(Lo3Inhoud.parseInhoud(syncBericht.getLo3PersoonslijstAlsTeletexString()));
            }
            Logging.destroyContext();
        }

        assertTrue("Niet alle verwachtte berichten zijn gevonden!", verwachtteIds.size() == 0);

        queue.clear();
    }
}
