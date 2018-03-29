/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.listener;

import java.sql.Timestamp;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.VoiscDatabase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Listener om berichten van ISC op te slaan in de database.
 */
public final class IscBerichtListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ERROR_ORIGINATOR_NIET_GEVONDEN = "Originator mailbox (%s) kon niet gevonden worden.";
    private static final String ERROR_RECIPIENT_NIET_GEVONDEN = "Recipient mailbox (%s) kon niet gevonden worden.";

    private VoiscDatabase voiscDatabase;

    /**
     * Constructor.
     * @param voiscDatabase voisc database
     */
    @Inject
    public IscBerichtListener(final VoiscDatabase voiscDatabase) {
        this.voiscDatabase = voiscDatabase;
    }

    @Override

    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public void onMessage(final Message message) {
        LOG.debug("Start opslaan bericht van queue.");

        final Bericht bericht = new Bericht();
        bericht.setTijdstipOntvangst(new Timestamp(System.currentTimeMillis()));
        bericht.setStatus(StatusEnum.RECEIVED_FROM_ISC);
        try {
            bericht.setMessageId(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
            LOG.debug("[Bericht (message-id) {}]: Lezen bericht ...", bericht.getMessageId());
            bericht.setCorrelationId(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
            bericht.setBerichtInhoud(((TextMessage) message).getText());

            String recipient = message.getStringProperty(JMSConstants.BERICHT_RECIPIENT);
            String originator = message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR);
            zetRecipientOpBericht(bericht, recipient);
            zetOriginatorOpBericht(bericht, originator, recipient);

            if (message.propertyExists(JMSConstants.REQUEST_NON_RECEIPT_NOTIFICATION)) {
                bericht.setRequestNonReceiptNotification(true);
            } else {
                bericht.setRequestNonReceiptNotification(false);
            }

            LOG.debug("[Bericht (message-id) {}]: Opslaan bericht ...", bericht.getMessageId());
            voiscDatabase.saveBericht(bericht);

            MDCProcessor.extra(MDCVeld.VOISC_BERICHT_ID, bericht.getId()).run(() -> LOG.info(FunctioneleMelding.VOISC_ISC_ONTVANGEN));
        } catch (final JMSException | RuntimeException e) {
            // Alle fouten afvangen voor logging (verwerking id zit in de MDC).
            LOG.error("Fout opgetreden bij het verwerken van bericht", e);
            throw new IscBerichtException(e);
        }
    }

    private void zetRecipientOpBericht(final Bericht bericht, final String recipient) {
        final Mailbox recipientMailbox = voiscDatabase.getMailboxByPartijcode(recipient);
        if (recipientMailbox == null) {
            throw new IscBerichtException(String.format(ERROR_RECIPIENT_NIET_GEVONDEN, recipient));
        } else {
            bericht.setRecipient(recipientMailbox.getMailboxnr());
        }
    }

    private void zetOriginatorOpBericht(final Bericht bericht, final String originator, final String recipient) {
        if ("199902".equals(originator)) {
            final Mailbox recipientMailbox = voiscDatabase.getMailboxByPartijcode(recipient);
            if (recipientMailbox == null) {
                throw new IscBerichtException(String.format(ERROR_RECIPIENT_NIET_GEVONDEN, recipient));
            } else {
                bericht.setOriginator(recipientMailbox.getVerzender());
            }
        } else {
            final Mailbox originatorMailbox = voiscDatabase.getMailboxByPartijcode(originator);
            if (originatorMailbox == null) {
                throw new IscBerichtException(String.format(ERROR_ORIGINATOR_NIET_GEVONDEN, originator));
            } else {
                bericht.setOriginator(originatorMailbox.getMailboxnr());
            }
        }
    }
}
