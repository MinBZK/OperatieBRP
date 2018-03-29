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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import org.springframework.jms.core.JmsTemplate;

/**
 * Message handler voor BRP leveringen.
 */
public final class LeveringenMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Destination queueLevering;
    private JmsTemplate jmsTemplate;

    /**
     * Constructor.
     * @param queueLevering levering queue
     * @param connectionFactory queue connection factory
     */
    @Inject
    public LeveringenMessageHandler(@Named("queueLevering") final Destination queueLevering,
                                    @Named("queueConnectionFactory") final ConnectionFactory connectionFactory) {
        this.queueLevering = queueLevering;
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    @Override
    public void onMessage(final Message message) {
        try {
            jmsTemplate.send(queueLevering, session -> message);

            LOG.info(FunctioneleMelding.SYNC_LEVERING_VERWERKT);
        } catch (final Exception e) {
            LOG.error("Er is een fout opgetreden.", e);
            throw e;
        }
    }
}
