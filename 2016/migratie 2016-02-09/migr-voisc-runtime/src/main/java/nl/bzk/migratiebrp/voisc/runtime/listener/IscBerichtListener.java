/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.listener;

import java.util.Date;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
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

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String ERROR_ONGELDIGE_ORIGINATOR_INSTANTIECODE = "Originator bevatte geen geldig instantiecode (%s)";
    private static final String ERROR_ORIGINATOR_NIET_GEVONDEN = "Originator mailbox (%s) kon niet gevonden worden.";
    private static final String ERROR_ONGELDIGE_RECIPIENT_INSTANTIECODE = "Recipient bevatte geen geldig instantiecode (%s)";
    private static final String ERROR_RECIPIENT_NIET_GEVONDEN = "Recipient mailbox (%s) kon niet gevonden worden.";

    @Inject
    private VoiscDatabase voiscDatabase;

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public void onMessage(final Message message) {
        try (MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            LOGGER.debug("Start opslaan bericht van queue.");

            final Bericht bericht = new Bericht();
            bericht.setTijdstipOntvangst(new Date());
            bericht.setStatus(StatusEnum.RECEIVED_FROM_ISC);
            try {
                bericht.setMessageId(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
                LOGGER.debug("[Bericht (message-id) {}]: Lezen bericht ...", bericht.getMessageId());
                bericht.setCorrelationId(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
                bericht.setBerichtInhoud(((TextMessage) message).getText());

                zetOriginatorOpBericht(bericht, message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
                zetRecipientOpBericht(bericht, message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));

                LOGGER.debug("[Bericht (message-id) {}]: Opslaan bericht ...", bericht.getMessageId());
                voiscDatabase.saveBericht(bericht);

                try (MDCCloser berichtIdCloser = MDC.put(MDCVeld.VOISC_BERICHT_ID, bericht.getId())) {
                    LOGGER.info(FunctioneleMelding.VOISC_ISC_ONTVANGEN);
                }
            } catch (final Exception e) {
                // Alle fouten afvangen voor logging (verwerking id zit in de MDC).
                LOGGER.error("Fout opgetreden bij het verwerken van bericht", e);
                throw new IscBerichtException(e);
            }
        }
    }

    private void zetRecipientOpBericht(final Bericht bericht, final String recipient) {
        try {
            final Mailbox recipientMailbox = voiscDatabase.getMailboxByInstantiecode(Integer.valueOf(recipient));
            if (recipientMailbox == null) {
                throw new IscBerichtException(String.format(ERROR_RECIPIENT_NIET_GEVONDEN, recipient));
            } else {
                bericht.setRecipient(recipientMailbox.getMailboxnr());
            }
        } catch (final NumberFormatException e) {
            throw new IscBerichtException(String.format(ERROR_ONGELDIGE_RECIPIENT_INSTANTIECODE, recipient), e);
        }
    }

    private void zetOriginatorOpBericht(final Bericht bericht, final String originator) {
        try {
            final Mailbox originatorMailbox = voiscDatabase.getMailboxByInstantiecode(Integer.valueOf(originator));
            if (originatorMailbox == null) {
                throw new IscBerichtException(String.format(ERROR_ORIGINATOR_NIET_GEVONDEN, originator));
            } else {
                bericht.setOriginator(originatorMailbox.getMailboxnr());
            }
        } catch (final NumberFormatException e) {
            throw new IscBerichtException(String.format(ERROR_ONGELDIGE_ORIGINATOR_INSTANTIECODE, originator), e);
        }
    }
}
