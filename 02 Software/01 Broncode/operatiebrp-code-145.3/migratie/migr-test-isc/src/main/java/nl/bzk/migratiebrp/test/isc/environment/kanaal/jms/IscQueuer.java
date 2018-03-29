/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Queue helper.
 */
public final class IscQueuer {

    private static final int DEFAULT_LOOP_TELLER = 10;

    private static final String DESTINATION_QUEUE = "Queue";

    private static final String DESTINATION_TOPIC = "Topic";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int CLEANUP_TIMEOUT = 500;
    private static final int MAX_CLEANUP_RETRIES = 30;

    @Inject
    private JmsTemplate jmsTemplate;

    /**
     * Verstuur een bericht via JMS.
     * @param destination destination
     * @param bericht bericht
     */
    public void sendMessage(final Destination destination, final Bericht bericht) {
        LOG.info("Sending message (id={}, correlatie={}, originator={}, recipient={}): {}",
                new Object[]{bericht.getBerichtReferentie(), bericht.getCorrelatieReferentie(),
                        bericht.getVerzendendePartij(), bericht.getOntvangendePartij(), bericht.getInhoud(),});

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht.getInhoud());
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getBerichtReferentie());
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelatieReferentie());
                if (bericht.getVerzendendePartij() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, bericht.getVerzendendePartij());
                }
                if (bericht.getOntvangendePartij() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, bericht.getOntvangendePartij());
                }
                if (bericht.getMsSequenceNumber() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, bericht.getMsSequenceNumber());
                }
                if (Boolean.TRUE.equals(bericht.getRequestNonReceiptNotification())) {
                    message.setStringProperty(JMSConstants.REQUEST_NON_RECEIPT_NOTIFICATION, "true");
                }

                return message;
            }
        });
    }

    /**
     * Ontvang een bericht via JMS.
     * @param destination destination
     * @param messageSelector message selector
     * @return ontvangen bericht
     */
    public Bericht ontvangBericht(final Destination destination, final String messageSelector) {
        LOG.info("Ontvang bericht op {} met selector '{}' (timeout {})", new Object[]{destination, messageSelector, jmsTemplate.getReceiveTimeout()});

        final long originalTimeout = jmsTemplate.getReceiveTimeout();
        try {
            jmsTemplate.setReceiveTimeout(3000);

            Message message = null;
            int count = 0;
            while (count++ < DEFAULT_LOOP_TELLER && message == null) {
                LOG.info(" count: {}", count);
                message = jmsTemplate.receiveSelected(destination, messageSelector);
            }

            if (message == null) {
                return null;
            } else {
                final Bericht bericht = mapMessage(message);
                LOG.info("Received bericht: {}", bericht.getInhoud());
                return bericht;
            }

        } finally {
            jmsTemplate.setReceiveTimeout(originalTimeout);
        }
    }

    private Bericht mapMessage(final Message message) {
        final Bericht bericht = new Bericht();

        try {
            bericht.setBerichtReferentie(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
            bericht.setCorrelatieReferentie(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
            bericht.setVerzendendePartij(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
            bericht.setOntvangendePartij(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));

            if (message instanceof TextMessage) {
                bericht.setInhoud(((TextMessage) message).getText());
            } else {
                throw new IllegalArgumentException("Onbekend bericht type: " + message.getClass().getName());
            }

            LOG.info(
                    "Ontvangen request-non-receipt-notification property: '{}'",
                    message.getStringProperty(JMSConstants.REQUEST_NON_RECEIPT_NOTIFICATION));

            bericht.setRequestNonReceiptNotification(Boolean.valueOf(message.getStringProperty(JMSConstants.REQUEST_NON_RECEIPT_NOTIFICATION) != null));

        } catch (final JMSException e) {
            throw new IllegalArgumentException(e);
        }

        return bericht;
    }

    /**
     * Clean up: lees alle 'overblijvende' berichten van de queue.
     * @param destination queue
     * @param serverConnection connector voor JMX.
     * @return lijst aan berichten
     */
    public List<Bericht> cleanUp(final Destination destination, final MBeanServerConnection serverConnection) {
        final long timeout = jmsTemplate.getReceiveTimeout();
        final List<Bericht> result = new ArrayList<>();

        try {
            jmsTemplate.setReceiveTimeout(CLEANUP_TIMEOUT);
            Message message;
            final String queueName;
            final String destinationType;
            if (destination instanceof Topic) {
                queueName = ((Topic) destination).getTopicName();
                destinationType = DESTINATION_TOPIC;
            } else {
                queueName = ((Queue) destination).getQueueName();
                destinationType = DESTINATION_QUEUE;
            }

            int numberOfRetries = 0;
            while (telAantalBerichtenOpQueue(queueName, destinationType, serverConnection) > 0 && numberOfRetries < MAX_CLEANUP_RETRIES) {
                LOG.info("Er staan nog " + telAantalBerichtenOpQueue(queueName, destinationType, serverConnection) + " berichten op de queue.");
                message = jmsTemplate.receive(destination);
                if (message != null) {
                    LOG.info("Bericht gelezen van queue (" + queueName + "):" + message.getJMSMessageID());
                    result.add(mapMessage(message));
                } else {
                    LOG.info("Bericht kon niet worden gelezen van queue (" + queueName + "), wacht " + CLEANUP_TIMEOUT + "ms. Retry = " + numberOfRetries);
                    TimeUnit.MILLISECONDS.sleep(CLEANUP_TIMEOUT);
                    numberOfRetries++;
                }

            }

            if (telAantalBerichtenOpQueue(queueName, destinationType, serverConnection) > 0) {
                LOG.error("Op queue " + queueName + " nog staande berichten konden niet worden gelezen; fallback op JMX operatie.");
                if (DESTINATION_QUEUE.equals(destinationType)) {
                    verwijderBerichtenOpQueueViaJmx(queueName, serverConnection);
                    TimeUnit.MILLISECONDS.sleep(CLEANUP_TIMEOUT);
                }

                if (telAantalBerichtenOpQueue(queueName, destinationType, serverConnection) > 0) {
                    throw new TestException("Berichten op queue (" + queueName + " ) konden ook niet met JMX worden verwijderd.");
                }
            }
        } catch (final
        InterruptedException
                | JMSException
                | TestException e) {
            throw new IllegalStateException("Fout opgetreden bij het lezen van berichten op de queue.", e);
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }

        return result;
    }

    private Integer telAantalBerichtenOpQueue(final String queueName, final String destinationType, final MBeanServerConnection serverConnection) {

        try {

            // Get the MBeanInfo for the JNDIView MBean
            final ObjectName objectNaam =
                    new ObjectName(
                            "org.apache.activemq:type=Broker,brokerName=routeringCentrale,destinationType="
                                    + destinationType
                                    + ",destinationName="
                                    + queueName);
            return ((Long) serverConnection.getAttribute(objectNaam, "QueueSize")).intValue();
        } catch (final
        AttributeNotFoundException
                | InstanceNotFoundException
                | MBeanException
                | ReflectionException
                | IOException
                | MalformedObjectNameException e) {
            throw new IllegalStateException("Fout opgetreden bij het bepalen van het aantal berichten op de queue ( " + queueName + " ).", e);
        }
    }

    private void verwijderBerichtenOpQueueViaJmx(final String queueName, final MBeanServerConnection serverConnection) {
        try {

            // Get the MBeanInfo for the JNDIView MBean
            final ObjectName objectNaam =
                    new ObjectName("org.apache.activemq:type=Broker,brokerName=routeringCentrale,destinationType=Queue,destinationName=" + queueName);
            serverConnection.invoke(objectNaam, "purge", new Object[]{}, new String[]{});
        } catch (final
        MalformedObjectNameException
                | InstanceNotFoundException
                | MBeanException
                | ReflectionException
                | IOException e) {
            throw new IllegalStateException("Fout opgetreden bij het verwijderen van berichten op de queue ( " + queueName + " ). ", e);
        }
    }
}
