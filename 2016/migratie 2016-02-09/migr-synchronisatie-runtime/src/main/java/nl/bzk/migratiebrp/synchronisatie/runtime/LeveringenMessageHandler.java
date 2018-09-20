/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Message handler voor BRP leveringen.
 */
public final class LeveringenMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    @Inject
    @Named("queueLevering")
    private Destination queueLevering;

    /**
     * Zet de waarde van connection factory.
     *
     * @param connectionFactory
     *            connection factory
     */
    @Inject
    @Named("queueConnectionFactory")
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void onMessage(final Message message) {
        try (final MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            jmsTemplate.send(queueLevering, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) {
                    return message;
                }
            });

            LOG.info(FunctioneleMelding.SYNC_LEVERING_VERWERKT);
        } catch (final Exception e) {
            LOG.error("Er is een fout opgetreden.", e);
            throw e;
        }
    }
}
