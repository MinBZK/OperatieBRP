/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import org.springframework.jms.support.JmsUtils;

/**
 * Basis afhandeling van berichten.
 */
public abstract class AbstractMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onMessage(final Message message) {
        try {
            final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            final String correlationId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);
            if (messageId == null || "".equals(messageId)) {
                LOGGER.error("Bericht ontvangen zonder message-id!\n{}", message);
                throw new IllegalArgumentException("Bericht ontvangen zonder message-id");
            } else {
                LoggerFactory.getBerichtVerkeerLogger().info("[Bericht {}]: Parse bericht ...", messageId);
                verwerkBericht(message, messageId, correlationId);
            }
        } catch (final JMSException e1) {
            throw JmsUtils.convertJmsAccessException(e1);
        }
    }

    private void verwerkBericht(Message message, String messageId, String correlationId) throws JMSException {
        if (message instanceof TextMessage) {
            verwerkBericht(((TextMessage) message).getText(), messageId, correlationId);

        } else {
            LOGGER.error("[Bericht {}]: JMS bericht is niet van het type TextMessage", messageId);
        }
    }

    /**
     * Verwerk bericht.
     * @param text bericht inhoud
     * @param messageId message id
     * @param correlationId correlation id
     */
    protected abstract void verwerkBericht(final String text, final String messageId, final String correlationId);
}
