/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.service.AfnemerQueueService;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.service.StappenService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

@RunWith(MockitoJUnitRunner.class)
public class VerzenderTest {

    private static final Logger    LOGGER    = LoggerFactory.getLogger();

    @InjectMocks
    private final              Verzender verzender = new Verzender();

    @Mock
    private AfnemerQueueService afnemerQueueService;
    @Mock
    private StappenService      stappenService;

    private ActiveMQConnectionFactory connectionFactory;
    private VerzendTellerJMXBean      verzendingTellerBean;

    private final int aantalVerzendThreads    = 3;
    private final int aantalBerichtenPerIteratie    = 5;

    private final int aantalPartijen          = 10;
    private final int aantalBerichtenPerQueue = 10;

    private final int verwerkingstijdVerzender = 50;

    @Before
    public void voorTest() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                TimeUnit.MILLISECONDS.sleep(verwerkingstijdVerzender);
                return null;
            }
        }).when(stappenService).voerStappenUit(Mockito.<BerichtContext>any());

        ReflectionTestUtils.setField(verzender, "aantalVerzendThreads", aantalVerzendThreads);
        ReflectionTestUtils.setField(verzender, "aantalBerichtenPerIteratie", aantalBerichtenPerIteratie);
        ReflectionTestUtils.setField(verzender, "wachttijdVoorBerichtMillis", 50);

        verzendingTellerBean = new VerzendTellerJMXBean();
        ReflectionTestUtils.setField(verzender, "verzendingTellerBean", verzendingTellerBean);

        connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        final RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(1);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        final ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(aantalBerichtenPerIteratie);
        connectionFactory.setPrefetchPolicy(prefetchPolicy);

        final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        ReflectionTestUtils.setField(verzender, "pooledConnectionFactory", pooledConnectionFactory);

        final LinkedList<PartijCodeAttribuut> partijLijst = new LinkedList<>();
        for (int i = 0; i < aantalPartijen; i++) {
            partijLijst.add(new PartijCodeAttribuut(i));
        }
        Mockito.when(afnemerQueueService.haalPartijCodesWaarvoorGeleverdMoetWorden()).thenReturn(partijLijst);


        verzender.naMaak();


    }


    @Test(timeout = 30000)
    public void testNormaleVerwerking() throws Exception {


        final Thread berichtProducer = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("start plaatsen berichten");
                try {
                    final QueueConnection queueConnection = connectionFactory.createQueueConnection();
                    final Session session = queueConnection.createSession(false, 1);

                    for (int i = 0; i < aantalPartijen; i++) {
                        final Destination afnemerQueue = new ActiveMQQueue(Partij.PREFIX_AFNEMER_QUEUE_NAAM + i);
                        final MessageProducer producer = session.createProducer(afnemerQueue);

                        for (int j = 0; j < aantalBerichtenPerQueue; j++) {
                            producer.send(new ActiveMQTextMessage());
                        }
                    }

                    session.close();
                    queueConnection.close();

                } catch (final JMSException e) {
                    LOGGER.debug("Queue connectie verbroken", e.getMessage());
                }

                LOGGER.debug("klaar met plaatsen berichten");
            }
        });
        berichtProducer.start();

        final int verwachtAantalBerichten = aantalPartijen * aantalBerichtenPerQueue;
        while (verzendingTellerBean.getAantalGeleverdeBerichten() != verwachtAantalBerichten) {
            LOGGER.debug(String.format("Nog niet alle berichten ontvangen [%d / %d]",
                verzendingTellerBean.getAantalGeleverdeBerichten(), verwachtAantalBerichten));
            TimeUnit.SECONDS.sleep(1);
        }

        verzender.voorDestroy();
        Mockito.verify(stappenService, Mockito.times(verwachtAantalBerichten)).voerStappenUit(Mockito.<BerichtContext>any());

    }

    @Test(timeout = 30000)
    public void testUpdateAfnemers() throws Exception {

        // maakt een nieuw setje afnemers aan
        final AtomicInteger ai = new AtomicInteger(this.aantalPartijen);
        final int extraPartijen = 10;
        Mockito.when(afnemerQueueService.haalPartijCodesWaarvoorGeleverdMoetWorden()).then(new Answer<List<PartijCodeAttribuut>>() {
            @Override
            public List<PartijCodeAttribuut> answer(final InvocationOnMock invocation) throws Throwable {
                final LinkedList<PartijCodeAttribuut> partijLijst = new LinkedList<>();
                for (int i = 0; i < extraPartijen; i++) {
                    partijLijst.add(new PartijCodeAttribuut(ai.incrementAndGet() - 1));
                }
                return partijLijst;
            }
        });

        // dit voegt dynamisch afnemer queues toe die de verzender gaat bemonsteren
        verzender.updateAfnemers();
        verzender.updateAfnemers();

        final Thread berichtProducer = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("start plaatsen berichten");

                try {
                    final QueueConnection queueConnection = connectionFactory.createQueueConnection();
                    final Session session = queueConnection.createSession(false, 1);

                    for (int i = 0; i < ai.get() ; i++) {
                        final Destination afnemerQueue = new ActiveMQQueue(Partij.PREFIX_AFNEMER_QUEUE_NAAM + i);
                        final MessageProducer producer = session.createProducer(afnemerQueue);
                        producer.send(new ActiveMQTextMessage());
                    }

                    session.close();
                    queueConnection.close();

                } catch (final JMSException e) {
                    LOGGER.debug("Queue connectie verbroken", e.getMessage());
                }

                LOGGER.debug("klaar met plaatsen berichten");
            }
        });
        berichtProducer.start();

        // lees berichten van de nieuwe queues
        while (verzendingTellerBean.getAantalGeleverdeBerichten() != ai.get()) {
            LOGGER.debug("{} / {} berichten ontvangen",
                verzendingTellerBean.getAantalGeleverdeBerichten(), ai.get());
            TimeUnit.SECONDS.sleep(1);
        }
        LOGGER.info("Alle {} berichten ontvangen", ai.get());
            verzender.voorDestroy();
        Mockito.verify(stappenService, Mockito.times(ai.get())).voerStappenUit(Mockito.<BerichtContext>any());
    }


    @Test
    public void testBlauwDependency() throws NoSuchMethodException {

        final ManagedResource managedResource = Verzender.class.getAnnotation(ManagedResource.class);
        Assert.notNull(managedResource);
        Assert.isTrue("nl.bzk.brp.levering.verzending:name=Verzender".equals(managedResource.objectName()), "objectName gelijk");

        final Method updateAfnemersMethode = Verzender.class.getMethod("updateAfnemers");
        final ManagedOperation updateAfnemersMethodeAnnotatie = updateAfnemersMethode.getAnnotation(ManagedOperation.class);
        Assert.notNull(updateAfnemersMethodeAnnotatie);

    }
}
