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
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;

/**
 * Basis afhandeling van berichten.
 */
public abstract class AbstractMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger VERKEER_LOG = LoggerFactory.getBerichtVerkeerLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onMessage(final Message message) {
        try {
            final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            final String correlationId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);

            MDC.put(MDCVeld.SYNC_BERICHT_REFERENTIE, messageId);
            MDC.put(MDCVeld.SYNC_CORRELATIE_REFERENTIE, correlationId);

            if (messageId == null || "".equals(messageId)) {
                LOG.error("Bericht ontvangen zonder message-id!\n{}", message);
            } else {
                VERKEER_LOG.info("[Bericht {}]: Parse bericht ...", messageId);

                if (message instanceof TextMessage) {
                    verwerkBericht(((TextMessage) message).getText(), messageId, correlationId);

                } else {
                    LOG.error("[Bericht {}]: JMS bericht is niet van het type TextMessage", messageId);
                }
            }
        } catch (final JMSException e1) {
            LOG.error("Kan message-id niet lezen van bericht, of geen valide bericht!\n{}", message);
        } finally {
            MDC.remove(MDCVeld.SYNC_BERICHT_REFERENTIE);
            MDC.remove(MDCVeld.SYNC_CORRELATIE_REFERENTIE);
        }
    }

    /**
     * Verwerk bericht.
     *
     * @param text
     *            bericht inhoud
     * @param messageId
     *            message id
     * @param correlationId
     *            correlation id
     */
    protected abstract void verwerkBericht(final String text, final String messageId, final String correlationId);
}
