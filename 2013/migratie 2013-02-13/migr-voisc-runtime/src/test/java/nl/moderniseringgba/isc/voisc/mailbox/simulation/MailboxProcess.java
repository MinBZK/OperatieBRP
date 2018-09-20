/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.net.ssl.SSLException;

import nl.moderniseringgba.isc.voisc.mailbox.simulation.SpdAdapter.FormatsPdMessage;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * Models the mailbox client. The client has a connection with the MailboxServer over which messages are exchanged.
 * 
 */
public class MailboxProcess {
    // The connection with the client
    private SpdAdapter spdAdapter = null;

    // The mailbox factory
    private MailboxFactory mbFactory;

    // De geopende mailbox
    private Mailbox mailbox = null;
    // Als het openen niet lukt bij een relogon, dan blijft de oude mailbox actief.
    private Mailbox vorigeMailbox = null;

    private static final Logger LOGGER = LoggerFactory.getLogger();

    public MailboxProcess(final Socket connection) throws IOException {
        spdAdapter = new SpdAdapter(connection);
    }

    /**
     * Handle the Mailbox State Machine.
     * 
     */
    public void process() {
        SpdOperations mainOperation = SpdOperations.NO_OP;
        boolean keepAlive = true;
        try {
            while (keepAlive) {
                mainOperation = spdAdapter.readInput();
                keepAlive = handleMainOperation(mainOperation);
            }
        } catch (final SSLException e) {
            LOGGER.error("SSLException opgetreden bij het opzetten van de server socket", e);
        } catch (final IOException e) {
            LOGGER.error("Fout tijdens het gebruik van de socket", e);
        } finally {
            closeMailbox();
            spdAdapter.close();
        }
    }

    private boolean handleMainOperation(final SpdOperations operation) throws IOException {
        boolean keepAlive = true;
        switch (operation) {
            case CHG_PWD:
                LOGGER.info("ChangePassword operation");
                changePassword();
                break;
            case LIST_MESGS:
                LOGGER.info("ListMessages operation");
                spdAdapter.sendListMessagesResult(mailbox);
                break;
            case GET_MESG:
                LOGGER.info("GetMessage operation");
                spdAdapter.sendGetMessageResult(mailbox);
                break;
            case LOGOFF:
                LOGGER.info("Logoff operation");
                closeMailbox();
                spdAdapter.sendLogoffConfirmation();
                keepAlive = false;
                break;
            case LOGON:
                LOGGER.info("Logon operation");
                // bij een re-logon moet eerst de oude mailbox worden afgesloten.
                if (mailbox != null) {
                    closeMailbox();
                }
                openMailbox();
                break;
            case PUT_MESG:
                LOGGER.info("PutMessage operation");
                storeMessageAndSendConfirmation();
                break;
            default:
                // Zou de NO_OP case kunnen zijn.
        }
        return keepAlive;
    }

    private void changePassword() throws IOException {
        int errorcode = 0;
        if (!mailbox.getWachtwoord().equals(spdAdapter.getPassword())) {
            errorcode = 1132;
        }
        // Alle andere controles die van toepassing zijn op een wachtwoord, doet deze implementatie niet.
        // Kan later natuurlijk wel worden toegevoegd.
        mailbox.setWachtwoord(spdAdapter.getNewPassword());

        spdAdapter.sendChangePasswordConfirmation(errorcode);
    }

    /**
     * Close all open mailboxes.
     * 
     */
    private void closeMailbox() {
        if (mailbox != null) {
            try {
                mbFactory.storeMailbox(mailbox);
            } catch (final IOException e) {
                LOGGER.error("Kan de mailbox niet opslaan", e);
            }
        }
        vorigeMailbox = mailbox;
        mailbox = null;
    }

    private void openMailbox() throws IOException {
        int resultcode = 0;

        LOGGER.info("Opening mailbox for " + spdAdapter.getLogonUsername());
        mailbox = mbFactory.loadMailbox(spdAdapter.getLogonUsername());
        if (mailbox.isNotPresent()) {
            LOGGER.warn("Geen mailbox aanwezig. Spd-foutcode 1036");
            mailbox = vorigeMailbox;
            resultcode = 1036;
        }
        if (mailbox.isTempBlocked()) {
            LOGGER.warn("Mailbox tijdelijk geblokkeerd. Spd-foutcode 1037");
            mailbox = vorigeMailbox;
            resultcode = 1037;
        }
        spdAdapter.sendLogonConfirmation(resultcode);
    }

    public void setMailboxFactory(final MailboxFactory mbFactory) {
        this.mbFactory = mbFactory;
    }

    private void storeMessageAndSendConfirmation() throws IOException {
        final FormatsPdMessage mesg = spdAdapter.msg;
        MailboxEntry entry = null;
        final int resultcode = 0;
        final Mailbox recipientMailbox =
                mbFactory.loadMailbox(mesg.putMessageMessageHeadingFirstRecipientOrName, false);
        final int msSequenceNr = recipientMailbox.getNextMsSequenceNr();
        entry = new MailboxEntry();
        entry.setOriginatorOrRecipient(mesg.logonUsername);
        entry.setCrossReference(mesg.putMessageMessageHeadingCrossReference);
        entry.setMesg(mesg.message);
        entry.setMessageId(mesg.putMessageMessageHeadingMessageId);
        entry.setMsSequenceNr(msSequenceNr);
        entry.setStatus(MailboxEntry.STATUS_NEW);

        boolean delReportSent = false;
        if (recipientMailbox.isNotPresent()) {
            createDeliveryReport(false, msSequenceNr);
            delReportSent = true;
        }
        if (recipientMailbox.isTempBlocked()) {
            createDeliveryReport(true, msSequenceNr);
            delReportSent = true;
        }
        if (!delReportSent && mesg.message.contains("STATREP")) {
            createStatusReport(mesg.putMessageMessageHeadingFirstRecipientOrName,
                    mesg.putMessageMessageHeadingMessageId);
        } else {
            recipientMailbox.getInbox().put(msSequenceNr, entry);
            recipientMailbox.setNextMSSequenceNr(msSequenceNr + 1);
            mbFactory.storeMailbox(recipientMailbox);
        }
        spdAdapter.sendPutMessageConfirmation(entry, resultcode);
    }

    private void createDeliveryReport(final boolean tempBlocked, final int msgMsSequenceNr) {
        // create delivery Report
        final DecimalFormat dfSequence = new DecimalFormat("000000000");
        final int msSequenceNr = mailbox.getNextMsSequenceNr();

        final FormatsPdMessage mesg = spdAdapter.msg;
        final MailboxEntry entry = new MailboxEntry();
        entry.setOriginatorOrRecipient(mesg.putMessageMessageHeadingFirstRecipientOrName);
        final String message =
                (tempBlocked ? SpdAdapter.DELIVERY_REPORT_BLOCKED : SpdAdapter.DELIVERY_REPORT_NA) + ";"
                        + dfSequence.format(msgMsSequenceNr);
        entry.setMesg(message);
        entry.setMsSequenceNr(msSequenceNr);
        entry.setStatus(MailboxEntry.STATUS_NEW);

        mailbox.getInbox().put(msSequenceNr, entry);
        mailbox.setNextMSSequenceNr(msSequenceNr + 1);
    }

    private void createStatusReport(final String recipient, final String removedMesgId) {
        final int msSequenceNr = mailbox.getNextMsSequenceNr();
        final String message = SpdAdapter.STATUS_REPORT;
        final MailboxEntry entry = new MailboxEntry();
        entry.setOriginatorOrRecipient(recipient);
        entry.setMesg(message);
        entry.setMsSequenceNr(msSequenceNr);
        entry.setCrossReference(removedMesgId);
        entry.setStatus(MailboxEntry.STATUS_NEW);

        mailbox.getInbox().put(msSequenceNr, entry);
        mailbox.setNextMSSequenceNr(msSequenceNr + 1);
    }
}
