/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

@RunWith(JUnit4.class)
public class VerzendingComponentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();


    private static final String TEST_GEGEVENS_JSON =
        "{\"administratieveHandelingId\":123456,\"administratieveHandelingTijdstipRegistratie\":1388574000000,"
            + "\"afleverkanaal\":\"BRP\",\"catalogusOptie\":{\"waarde\":"
            + "\"MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING\"},\"categorieDienst\":{\"waarde\":"
            + "\"MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING\"},"
            + "\"datumAanvangMaterielePeriodeResultaat\":20140520,"
            + "\"datumTijdAanvangFormelePeriodeResultaat\":1388574000000,"
            + "\"datumTijdEindeFormelePeriodeResultaat\":1388574000000,"
            + "\"dienstId\":135,\"geleverdePersoonsIds\":[0],"
            + "\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},\"stuurgegevens\":{"
            + "\"ontvangendePartijId\":364,\"zendendePartijId\":363,\"datumTijdVerzending\":1388574000000,\"referentienummer\":{\"waarde\":\"23213123\"}},"
            + "\"toegangAbonnementId\":0}";

    @Test
    public void testMinimaleOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRouteringCentrale().metVerzending().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.stop();
    }


    @Test
    //@Ignore
    public void testPerformance() throws Exception {

        LOGGER.info(TEST_GEGEVENS_JSON);
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRouteringCentrale().metVerzending().metSynchronisatieBerichtAfnemer().maak();
        omgeving.start();

        final SynchronisatieBerichtAfnemer synchronisatieBerichtAfnemer = omgeving.geefComponent(SynchronisatieBerichtAfnemer.class);

        //zorg dat er een abonnement is
        omgeving.database().voerUitMetTransactie(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                final Resource dsl = new DefaultResourceLoader().getResource("/testdata/abonnement.sql");
                JdbcTestUtils.executeSqlScript(jdbcTemplate, dsl, false);
            }
        });


        final int aantalBerichten = 5000;
        //vul de afnemer-queue met veel berichten
        final RouteringCentrale routeringCentrale = omgeving.geefComponent(RouteringCentrale.class);
        routeringCentrale.voerUit(new RouteringCentrale.SessionVerzoek() {
            @Override
            public Object voerUitMetSessie(final Session sessie) {

                final MessageProducer producer = maakProducer(sessie);

                final Resource resource = new DefaultResourceLoader().getResource("bericht/test_bericht.xml");
                final String xmlBericht;
                try {
                    final StringWriter xmlBerichtWriter = new StringWriter();
                    IOUtils.copy(resource.getInputStream(), xmlBerichtWriter);
                    xmlBericht = xmlBerichtWriter.toString();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    for (int i = 0 ; i < aantalBerichten; i++) {
                        final ActiveMQTextMessage message = new ActiveMQTextMessage();
                        message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, TEST_GEGEVENS_JSON);
                        message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, xmlBericht);
                        message.setJMSType(Stelsel.BRP.toString());
                        message.setStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI, synchronisatieBerichtAfnemer.geefURL());
                        producer.send(message);

                        if (i % 10 == 0) {
                            LOGGER.info("Plaats Queuebericht: " + i);
                        }
                    }
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }

                try {
                    producer.close();
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }

                return null;
            }

            private MessageProducer maakProducer(final Session sessie) {
                try {
                    return sessie.createProducer(new ActiveMQQueue("AFNEMER-199900"));
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }


        });

        omgeving.wachtTotFunctioneelBeschikbaar();


        //new CacheHelper(omgeving).cache();

        long start = System.currentTimeMillis();
        while (synchronisatieBerichtAfnemer.geefAantalOntvangenBerichten() != aantalBerichten) {
            TimeUnit.SECONDS.sleep(1);
            if (synchronisatieBerichtAfnemer.geefAantalOntvangenBerichten() % 500 == 0) {
                LOGGER.info("Ontvangen berichten:" + synchronisatieBerichtAfnemer.geefAantalOntvangenBerichten());
            }
        }
        LOGGER.info("Totale Tijd = " + (System.currentTimeMillis() - start));

        omgeving.stop();
    }
}
