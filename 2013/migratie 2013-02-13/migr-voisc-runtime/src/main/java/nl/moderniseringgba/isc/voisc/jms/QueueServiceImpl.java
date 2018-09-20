/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.repository.MailboxRepository;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class QueueServiceImpl implements QueueService {

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("vospgOntvangstQueue")
    private Destination vospgOntvangst;

    @Inject
    private MailboxRepository mailboxRepo;

    /*
     * (non-Javadoc)
     * 
     * @see nl.moderniseringgba.isc.voa.jms.QueueService#sendMessage(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public final void sendMessage(final Bericht bericht) {
        jmsTemplate.send(vospgOntvangst, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht.getBerichtInhoud());
                fillMessage(bericht, message);
                return message;
            }
        });
    }

    private void fillMessage(final Bericht bericht, final Message message) throws JMSException {
        // bericht.setEsbMessageId(BerichtId.generateMessageId());

        final Mailbox recipientMailbox = mailboxRepo.getMailboxByNummer(bericht.getRecipient());
        final Mailbox originatorMailbox = mailboxRepo.getMailboxByNummer(bericht.getOriginator());

        message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, originatorMailbox.getGemeentecode());
        message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, recipientMailbox.getGemeentecode());

        message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getEsbMessageId());
        message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getEsbCorrelationId());

    }
}
