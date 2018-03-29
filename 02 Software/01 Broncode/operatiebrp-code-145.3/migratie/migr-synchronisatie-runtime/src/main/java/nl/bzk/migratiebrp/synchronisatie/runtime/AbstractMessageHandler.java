/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.springframework.jms.core.JmsTemplate;

/**
 * Abstract message handler (common helper methods).
 */
public abstract class AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Destination destination;
    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     * @param destination destination (queue sync antwoord)
     * @param connectionFactory queue connection factory
     */
    protected AbstractMessageHandler(final Destination destination, final ConnectionFactory connectionFactory) {
        this.destination = destination;
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    /**
     * Lees bericht inhoud.
     * @param message message
     * @return inhoud
     * @throws BerichtLeesException bij lees fouten
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
     * @param bericht antwoord
     */
    protected final void stuurAntwoord(final SyncBericht bericht) {
        try {
            Herhaal.herhaalOperatie(() -> {
                jmsTemplate.send(destination, session -> maakAntwoordBericht(session, bericht));
                return null;
            });
        } catch (final HerhaalException e) {
            LOG.error("Kon bericht met Id=" + bericht.getMessageId() + " niet verzenden.", e);
        }
    }

    private Message maakAntwoordBericht(Session session, final SyncBericht bericht) throws JMSException {
        try {
            final Message message = session.createTextMessage(bericht.format());
            MDCProcessor.registreerVerwerkingsCode(message);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
            message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
            return message;
        } catch (final BerichtInhoudException exceptie) {
            LOG.error("Fout opgetreden bij versturen antwoord: {}", exceptie);
            return null;
        }
    }
}
