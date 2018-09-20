/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import javax.jms.JMSException;
import javax.jms.Session;

import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Berichten verzenden via JMS.
 */
public final class VerzendenAction implements Action {

    private JmsTemplate jmsTemplate;

    /**
     * Zet het JMS Template.
     *
     * @param jmsTemplate
     *            het te zetten JMS Template
     */
    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void setKanaal(final String kanaal) {
        // Ignore
    }

    @Override
    public boolean verwerk(final Message message) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public javax.jms.Message createMessage(final Session session) throws JMSException {
                final javax.jms.Message result = session.createTextMessage(message.getContent());
                result.setStringProperty(JMSConstants.BERICHT_REFERENTIE, message.getMessageId());
                result.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, message.getCorrelatieId());
                result.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, message.getOriginator());
                result.setStringProperty(JMSConstants.BERICHT_RECIPIENT, message.getRecipient());
                return result;
            }
        });
        return true;
    }

}
