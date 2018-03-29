/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import org.apache.activemq.broker.BrokerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * BASIC load test voor plaats afnemer JMS bericht.
 *
 * Maakt gebruikt van de productie configuratie van de JMS broker.
 * Test messagegroup functionaliteit.
 */
@Ignore //FIXME ROOD-1934 draait niet inmemory en testen tegen postgres worden blijkbaar alleen lokaal gedraaid...?
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:robuustheid/jms-load-test.xml")
public class LoadTestVeelAutorisaties {

    private final JsonStringSerializer serializer = new JsonStringSerializer();
    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static final int AANTAL_AUTORISATIES = 100000;
    public static final int AANTAL_BERICHTFORMATEN = 20;
    public static final int AANTAL_BERICHTEN = 1000;

    @Autowired
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Autowired
    private BrokerService broker;

    @Autowired
    private LoadTestMessageListener testMessageListener;

    @Test(timeout = 5 * 60 * 1000)
    public void testVeelAutorisaties() throws Exception {

        while (!broker.isStarted()) {
            LOGGER.debug("Wacht tot activemq broker gestart is...");
            TimeUnit.SECONDS.sleep(1);
        }
        LOGGER.debug("Broker gestart");

        // start produceren

        final int aantalAfnemers = AANTAL_AUTORISATIES;
        ToegangLeveringsAutorisatie[] afnemer = new ToegangLeveringsAutorisatie[aantalAfnemers];
        for (int i = 0; i < aantalAfnemers; i++) {
            afnemer[i] = LoadTestUtils.maakToegangLeveringsautorisatie(String.valueOf(1000 + i));

        }

        final int aantalBerichtFormaten = AANTAL_BERICHTFORMATEN;
        final int aantalTeVerzendenBerichten = AANTAL_BERICHTEN;
        final String[] berichten = LoadTestUtils.maakBerichten(aantalBerichtFormaten);
        final Random random = new Random();
        for (int i = 0; i < aantalTeVerzendenBerichten; i++) {
            final String bericht = berichten[random.nextInt(aantalBerichtFormaten)];
            final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
            archiveringOpdracht.setTijdstipVerzending(DatumUtil.nuAlsZonedDateTime());
            final SynchronisatieBerichtGegevens metaGegevens = SynchronisatieBerichtGegevens.builder()
                    .metArchiveringOpdracht(archiveringOpdracht).build();
            final ToegangLeveringsAutorisatie tla = afnemer[random.nextInt(aantalAfnemers)];
            plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList(new AfnemerBericht(metaGegevens, tla)));

            if (i % 100 == 0) {
                LOGGER.debug("Voortgang produceren {} / {}", i, aantalTeVerzendenBerichten);
            }
        }

        int totaalOntvangenBerichten = 0;
        while (totaalOntvangenBerichten != aantalTeVerzendenBerichten) {
            totaalOntvangenBerichten = testMessageListener.getAantalOntvangen();
            LOGGER.debug("Voortgang consumeren: {} / {}", totaalOntvangenBerichten, aantalTeVerzendenBerichten);
            TimeUnit.SECONDS.sleep(1);
        }

        LOGGER.debug("Klaar met consumeren");
    }
}
