/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
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
 * <p>
 * Maakt gebruikt van de productie configuratie van de JMS broker. Test messagegroup functionaliteit.
 */
@Ignore //FIXME ROOD-1934 draait niet inmemory en testen tegen postgres worden blijkbaar alleen lokaal gedraaid...?
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:robuustheid/jms-load-test.xml")
public class LoadTestVeelBerichten {
    private final JsonStringSerializer serializer = new JsonStringSerializer();
    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static final int AANTAL_AUTORISATIES = 2000;
    public static final int AANTAL_BERICHTEN_PER_PRODUCER = 100000;
    public static final int AANTAL_BERICHT_FORMATEN = 20;
    public static final int AANTAL_PRODUCERS = 4;

    @Autowired
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Autowired
    private BrokerService broker;

    @Autowired
    private LoadTestMessageListener testMessageListener;

    @Test(timeout = 5 * 60 * 1000)
    public void testVeelBerichten() throws Exception {

        while (!broker.isStarted()) {
            LOGGER.debug("Wacht tot activemq broker gestart is...");
            TimeUnit.SECONDS.sleep(1);
        }
        LOGGER.debug("Broker gestart");

        final int aantalAfnemers = AANTAL_AUTORISATIES;
        ToegangLeveringsAutorisatie[] afnemers = new ToegangLeveringsAutorisatie[aantalAfnemers];
        for (int i = 0; i < aantalAfnemers; i++) {
            afnemers[i] = maakToegangLeveringsautorisatie(String.valueOf(1000 + i));
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        final Producer[] producers = new Producer[AANTAL_PRODUCERS];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer(afnemers);
            executorService.submit(producers[i]);
        }

        executorService.shutdown();

        int totaalOntvangenBerichten = 0;
        while (!executorService.isTerminated()) {
//            totaalOntvangenBerichten = 0;
//            for (final JmsServiceMessageGroupsTest.Consumer consumer : consumers) {
//                totaalOntvangenBerichten += consumer.getAantalOntvangenBerichten();
//            }
//            LOGGER.debug("Voortgang consumeren: {} / {}", totaalOntvangenBerichten, aantalTeVerzendenBerichten);
            TimeUnit.SECONDS.sleep(1);
        }
        // start produceren

//        int totaalOntvangenBerichten = 0;
//        while (totaalOntvangenBerichten != aantalTeVerzendenBerichten) {
//            totaalOntvangenBerichten = testMessageListener.getAantalOntvangen();
//            LOGGER.debug("Voortgang consumeren: {} / {}", totaalOntvangenBerichten, aantalTeVerzendenBerichten);
//            TimeUnit.SECONDS.sleep(1);
//        }

        LOGGER.debug("Klaar met consumeren");


    }


    private ToegangLeveringsAutorisatie maakToegangLeveringsautorisatie(String partijCode) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
//        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final PartijRol geautoriseerde = new PartijRol(new Partij("Partij" + partijCode, partijCode), Rol.AFNEMER);
        return new ToegangLeveringsAutorisatie(geautoriseerde, leveringsautorisatie);

    }

    class Producer implements Runnable {


        private final ToegangLeveringsAutorisatie[] afnemer;

        Producer(final ToegangLeveringsAutorisatie[] afnemer) {
            this.afnemer = afnemer;
        }


        @Override
        public void run() {
            final String[] berichten;
            try {
                berichten = LoadTestUtils.maakBerichten(AANTAL_BERICHT_FORMATEN);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            final Random random = new Random();
            for (int i = 0; i < AANTAL_BERICHTEN_PER_PRODUCER; i++) {
                final String bericht = berichten[random.nextInt(AANTAL_BERICHT_FORMATEN)];
                final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
                archiveringOpdracht.setTijdstipVerzending(DatumUtil.nuAlsZonedDateTime());
                final SynchronisatieBerichtGegevens metaGegevens = SynchronisatieBerichtGegevens.builder()
                        .metArchiveringOpdracht(archiveringOpdracht).build();
                final ToegangLeveringsAutorisatie tla = afnemer[random.nextInt(afnemer.length)];
                plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList(new AfnemerBericht(metaGegevens, tla)));

                if (i % 100 == 0) {
                    LOGGER.debug("Voortgang produceren {} / {}", i, AANTAL_BERICHTEN_PER_PRODUCER);
                }
            }
        }

    }


}
