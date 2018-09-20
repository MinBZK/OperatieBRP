/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.handler;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.isc.runtime.service.Service;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerkt inkomende berichten door ze door te geven aan een {@link InboundService}.
 */
public final class InboundMessageListener implements MessageListener {

    private Service inboundService;

    /**
     * Zet de inbound service.
     *
     * @param inboundService
     *            de te zetten inbound service
     */
    @Required
    public void setInboundService(final Service inboundService) {
        this.inboundService = inboundService;
    }

    @Override
    @Transactional(value = "iscTransactionManager", propagation = Propagation.REQUIRED)
    public void onMessage(final javax.jms.Message jmsMessage) {
        try (MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            final Message message = JmsUtil.leesMessage(jmsMessage);
            inboundService.verwerk(message);
        } catch (final JMSException e) {
            throw new IllegalArgumentException("Kan bericht niet verwerken", e);
        }
    }

}
