/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.nfr.loadgenerator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Genereert constante load op het systeem.
 */
public class LoadGeneratorMain {

    private static final Logger LOGGER                            = LoggerFactory.getLogger();
    private static final String DBHOST                            = "dbhost";
    private static final String DBNAME                            = "dbname";
    private static final String LOCALHOST                         = "localhost";
    private static final String NFR_PROFIEL_1                     = "nfr_profiel1";
    private static final String ADMINISTRATIEVE_HANDELINGEN_QUEUE = "AdministratieveHandelingen";
    private static final String BROKER_URL                        = "brokerUrl";
    private static final String TCP_LOCALHOST_61616               = "tcp://localhost:61616";
    private static final int    DUIZEND                           = 1000;

    /**
     * Main methode van de Load Generator.
     *
     * @param args argumenten
     * @throws InterruptedException de interrupted exception
     */
    public static void main(final String[] args) throws InterruptedException {

        final ApplicationContext applicationContext = maakContext();
        final LoadGeneratorService service = applicationContext.getBean(LoadGeneratorService.class);
        final List<Handeling> handelingen = service.geefTeVerwerkenHandelingen();
        LOGGER.info("Totaal onverwerkte handelingen: " + handelingen.size());
        new Bijhouder(service, handelingen).run();
        LOGGER.info("Klaar!");
    }


    /**
     * Maak de context application context.
     *
     * @return the application context
     */
    private static ApplicationContext maakContext() {

        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put(DBHOST, getPropertyNotNull(DBHOST, LOCALHOST));
        properties.put(DBNAME, getPropertyNotNull(DBNAME, NFR_PROFIEL_1));
        propConfig.setProperties(properties);

        applicationContext.addBeanFactoryPostProcessor(propConfig);
        applicationContext.setConfigLocation("classpath:/spring-context.xml");
        applicationContext.refresh();

        return applicationContext;
    }

    /**
     * Geeft de property als deze niet null is.
     *
     * @param property     property
     * @param defaultValue default value
     * @return de property als deze niet null is, anders wordt er een IllegalStateException gegooid.
     */
    private static String getPropertyNotNull(final String property, final String defaultValue) {
        final String value = System.getProperty(property, defaultValue);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalStateException("Property niet gevonden: " + property);
        }
        return value;
    }

    /**
     * Bijhouder klasse die administratieve handelingen id's op queue zet.
     */
    private static class Bijhouder {

        private final Iterator<Handeling>  handelingenIterator;
        private final LoadGeneratorService service;
        private       int                  handelingenOpQueueGezet;
        //handelingen per seconde
        private final int load = Integer.parseInt(getPropertyNotNull("load", "15"));
        private MessageProducer producer;
        private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder =
            new JsonStringSerializer<>(AdministratieveHandelingVerwerktOpdracht.class);

        /**
         * Instantieert een nieuwe bijhouder.
         *
         * @param service     service
         * @param handelingen handelingen
         */
        public Bijhouder(final LoadGeneratorService service, final List<Handeling> handelingen) {
            this.service = service;
            this.handelingenIterator = handelingen.iterator();
            maakActiveMqProducer();
        }

        /**
         * Maakt active mq producer.
         */
        private void maakActiveMqProducer() {
            final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(getPropertyNotNull(BROKER_URL, TCP_LOCALHOST_61616));
            final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
            pooledConnectionFactory.setConnectionFactory(connectionFactory);
            try {
                final Session session = pooledConnectionFactory.createQueueConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
                producer = session.createProducer(new ActiveMQQueue(ADMINISTRATIEVE_HANDELINGEN_QUEUE));
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Start de load generator.
         */
        public void run() {
            LOGGER.info("Start Loadgenerator");
            final long start = System.currentTimeMillis();

            while (handelingenIterator.hasNext()) {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (final InterruptedException e) {
                    throw new RuntimeException(e);
                }

                final int verwachtTotaalQueuePlaatsingen = ((int) ((System.currentTimeMillis() - start) / DUIZEND)) * load;
                LOGGER.info("Verwacht totaal aantal queue plaatsingen: " + verwachtTotaalQueuePlaatsingen);
                final int tePlaatsenHandelingen = verwachtTotaalQueuePlaatsingen - handelingenOpQueueGezet;
                LOGGER.info("Nog te plaatsen handelingen: " + tePlaatsenHandelingen);
                for (int i = 0; i < tePlaatsenHandelingen; i++) {
                    if (handelingenIterator.hasNext()) {
                        final Handeling handeling = handelingenIterator.next();
                        try {
                            final ActiveMQTextMessage message = new ActiveMQTextMessage();
                            message.setText(
                                serialiseerder.serialiseerNaarString(new AdministratieveHandelingVerwerktOpdracht(handeling.getHandelingId(), null,
                                    Collections.singletonList(handeling.getHandelingId().intValue()))));
                            service.markeerStart(handeling.getHandelingId());
                            producer.send(message);
                        } catch (final JMSException e) {
                            throw new RuntimeException();
                        }
                        handelingenOpQueueGezet++;
                    } else {
                        LOGGER.warn("Geen te plaatsen handelingen meer.");
                        break;
                    }
                }
            }
            LOGGER.info("Stop Loadgenerator");
        }
    }
}
