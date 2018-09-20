/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Abstract message handler (common helper methods).
 */
public abstract class AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    @Inject
    @Named("queueSyncAntwoord")
    private Destination destination;

    /**
     * Zet de waarde van connection factory.
     *
     * @param connectionFactory
     *            connection factory
     */
    @Inject
    @Named("queueConnectionFactory")
    final void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    /**
     * Lees bericht inhoud.
     *
     * @param message
     *            message
     * @return inhoud
     * @throws BerichtLeesException
     *             bij lees fouten
     */
    protected final String bepaalBerichtInhoud(final Message message) throws BerichtLeesException {
        final String result;
        try {
            if (message instanceof TextMessage) {
                result = ((TextMessage) message).getText();
            } else {
                throw new BerichtLeesException("Het JMS bericht is niet van het type TextMessage");
            }
        } catch (final JMSException e) {
            throw new BerichtLeesException("Het JMS bericht kon niet worden gelezen door een fout: ", e);
        }
        return result;
    }

    /**
     * Verstuur het antwoord.
     *
     * @param bericht
     *            antwoord
     */
    protected final void stuurAntwoord(final SyncBericht bericht) {
        try {
            Herhaal.herhaalOperatie(new Callable<Object>() {
                @Override
                public Object call() {
                    jmsTemplate.send(destination, new MessageCreator() {
                        @Override
                        public Message createMessage(final Session session) throws JMSException {
                            try {
                                final Message message = session.createTextMessage(bericht.format());
                                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
                                return message;
                            } catch (final BerichtInhoudException exceptie) {
                                return null;
                            }
                        }
                    });
                    return null;
                }

            });
        } catch (final HerhaalException e) {
            LOG.error("Kon bericht met Id=" + bericht.getMessageId() + " niet verzenden.", e);
        }
    }

    /**
     * Fout bij het verwerken van JMS bericht.
     */
    public static final class BerichtLeesException extends Exception {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         *
         * @param message
         *            message
         */
        public BerichtLeesException(final String message) {
            super(message);
        }

        /**
         * Constructor.
         *
         * @param message
         *            message
         * @param cause
         *            cause
         */
        public BerichtLeesException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
