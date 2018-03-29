/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevensQueue;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * BASIC load test voor plaats afnemer JMS bericht.
 * <p>
 * Maakt gebruikt van de productie configuratie van de JMS broker. Test messagegroup functionaliteit.
 */
@Ignore //FIXME ROOD-1934 draait niet inmemory en testen tegen postgres worden blijkbaar alleen lokaal gedraaid...?
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jms-messagegroups-test.xml")
public class PlaatsAfnemerBerichtServiceMessageGroupsTest {

    private static final JsonStringSerializer SERIALIZER = new JsonStringSerializer();
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Autowired
    private BrokerService broker;

    @Value("${brp.jms.client.url}")
    private String brokerURL;

    @Test(timeout = 5 * 60 * 1000)
    public void testVerzendenOntvangen() throws Exception {

        while (!broker.isStarted()) {
            LOGGER.debug("Wacht tot activemq broker gestart is...");
            TimeUnit.SECONDS.sleep(1);
        }
        LOGGER.debug("Broker gestart");

        // start produceren

        final ToegangLeveringsAutorisatie[] afnemers = {
                LoadTestUtils.maakToegangLeveringsautorisatie("000111"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000222"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000333"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000444"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000555"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000666"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000777"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000888"),
                LoadTestUtils.maakToegangLeveringsautorisatie("000999"),
        };

        final int aantalBerichtFormaten = 10;
        final int aantalTeVerzendenBerichten = 1000;
        final String[] berichten = LoadTestUtils.maakBerichten(aantalBerichtFormaten);
        final Random random = new Random();
        for (int i = 0; i < aantalTeVerzendenBerichten; i++) {
            final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
            archiveringOpdracht.setTijdstipVerzending(DatumUtil.nuAlsZonedDateTime());
            final SynchronisatieBerichtGegevens metaGegevens = SynchronisatieBerichtGegevens.builder()
                    .metArchiveringOpdracht(archiveringOpdracht).build();
            final String bericht = berichten[random.nextInt(aantalBerichtFormaten)];
            final ToegangLeveringsAutorisatie tla = afnemers[random.nextInt(afnemers.length)];
            plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList(new AfnemerBericht(metaGegevens, tla)));

            if (i % 100 == 0) {
                LOGGER.debug("Voortgang produceren {} / {}", i, aantalTeVerzendenBerichten);
            }
        }

        // start consumeren

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final List<Consumer> consumers = Lists.newArrayList(afnemers).stream().map(toegangLeveringsAutorisatie -> new Consumer
                (SynchronisatieBerichtGegevensQueue.NAAM.getQueueNaam(), brokerURL)).collect(Collectors.toList());
        for (Consumer consumer : consumers) {
            executorService.submit(consumer);
        }
        executorService.shutdown();

        int totaalOntvangenBerichten = 0;
        while (totaalOntvangenBerichten != aantalTeVerzendenBerichten) {
            totaalOntvangenBerichten = 0;
            for (final Consumer consumer : consumers) {
                totaalOntvangenBerichten += consumer.getAantalOntvangenBerichten();
            }
            LOGGER.debug("Voortgang consumeren: {} / {}", totaalOntvangenBerichten, aantalTeVerzendenBerichten);
            TimeUnit.SECONDS.sleep(1);
        }

        LOGGER.debug("Klaar met consumeren");
        executorService.shutdownNow();

        for (Consumer consumer : consumers) {
            consumer.controleerOntvangenBerichten();
        }

    }


    private static class Consumer implements Runnable, ExceptionListener {

        private final String queue;
        private final String brokerURL;
        private final List<SynchronisatieBerichtGegevens> messageList = Lists.newCopyOnWriteArrayList();

        Consumer(final String queue, final String brokerURL) {
            this.queue = queue;
            this.brokerURL = brokerURL;
        }

        int getAantalOntvangenBerichten() {
            return messageList.size();
        }

        void controleerOntvangenBerichten() {
            LOGGER.debug("Aantal berichten: " + messageList.size());

            Integer vorigePartijId = null;
            ZonedDateTime vorigTijdstipVerzending = null;
            for (SynchronisatieBerichtGegevens textMessage : messageList) {

                final int partijId = textMessage.getArchiveringOpdracht().getOntvangendePartijId();
                if (vorigePartijId != null) {
                    Assert.assertEquals("PartijId moet per consumer gelijk zijn", partijId, vorigePartijId.intValue());
                }
                vorigePartijId = partijId;

                final ZonedDateTime datumTijdVerzending = textMessage.getArchiveringOpdracht().getTijdstipVerzending();
                if (vorigTijdstipVerzending != null) {
                    Assert.assertTrue("Berichten moeten in tijd opvolgend zijn", datumTijdVerzending.isAfter(vorigTijdstipVerzending));
                }
                vorigTijdstipVerzending = datumTijdVerzending;
            }
        }

        public void run() {
            try {

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                connection.setExceptionListener(this);

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(queue);

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message

                while (!Thread.currentThread().isInterrupted()) {
                    Message message = consumer.receive(200);

                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        messageList.add(SERIALIZER.deserialiseerVanuitString(textMessage.getText(), SynchronisatieBerichtGegevens.class));
                    }
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                if (e.getCause() != null && e.getCause() instanceof InterruptedException) {
                    return;
                }
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
//            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }
}
