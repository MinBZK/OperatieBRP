/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import javax.net.ssl.SSLException;
import nl.bzk.migratiebrp.tools.mailbox.SpdAdapter.FormatSpdMessage;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Models the mailbox client. The client has a connection with the MailboxServer over which messages are exchanged.
 */
public final class MailboxProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int ERRORCODE_SUCCES = 0;
    private static final int ERRORCODE_GEEN_MAILBOX = 1036;
    private static final int ERRORCODE_MAILOX_GEBLOKKEERD = 1037;
    private static final int ERRORCODE_WRONG_PASSWORD = 1132;
    private static final int ERRORCODE_OVERIG = 1205;
    private static final int ERRORCODE_PUTMESSAGE_GEADDRESSEERDE_NIET_BEREIKBAAR = 1502;
    private static final int ERRORCODE_COMMANDO_NIET_TOEGESTAAN = 1002;

    // The connection with the client
    private final SpdAdapter spdAdapter;

    // The mailbox factory
    private final MailboxFactory mbFactory;

    // De geopende mailbox
    private Mailbox mailbox;

    /**
     * Constructor.
     * 
     * @param connection
     *            connectie
     * @param mbFactory
     *            mailbox factory
     * @throws IOException
     *             als de input- of outputstream (van de connectie) niet geopend kan worden.
     */
    public MailboxProcess(final Socket connection, final MailboxFactory mbFactory) throws IOException {
        spdAdapter = new SpdAdapter(connection);

        this.mbFactory = mbFactory;
    }

    /**
     * Handle the Mailbox State Machine.
     * 
     */
    public void process() {
        boolean keepAlive = true;
        try {
            while (keepAlive) {
                final SpdOperations mainOperation = spdAdapter.readInput();
                keepAlive = handleMainOperation(mainOperation);
            }
        } catch (final SSLException e) {
            LOGGER.error("SSLException opgetreden bij het opzetten van de server socket", e);
        } catch (final IOException e) {
            LOGGER.error("Fout tijdens het gebruik van de socket", e);
        } finally {
            // Als de mailbox niet netjes is afgesloten dan hier
            if (mailbox != null) {
                try {
                    mailbox.save();
                } catch (final MailboxException e) {
                    LOGGER.error("Probleem bij (nood)afsluiting mailbox.", e);
                } finally {
                    mailbox.close();
                }

            }
        }
    }

    private boolean handleMainOperation(final SpdOperations operation) throws IOException {
        boolean keepAlive = true;
        switch (operation) {
            case LOGON:
                LOGGER.debug("Logon operation");
                handleLogon();
                break;
            case LOGOFF:
                LOGGER.debug("Logoff operation");
                handleLogoff();
                keepAlive = false;
                break;
            case CHG_PWD:
                LOGGER.debug("ChangePassword operation");
                handleChangePassword();
                break;
            case PUT_MESG:
                LOGGER.debug("PutMessage operation");
                handlePutMessage();
                break;
            case LIST_MESGS:
                LOGGER.debug("ListMessages operation");
                handleListMessages();
                break;
            case GET_MESG:
                LOGGER.debug("GetMessage operation");
                handleGetMessage();
                break;

            default:
                // Zou de NO_OP case kunnen zijn.
        }

        return keepAlive;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void handleLogon() throws IOException {
        final Mailbox vorigeMailbox = mailbox;

        int resultcode = openMailbox();

        // Alleen doorgaan met controles als mailbox geopend is (en dus wachtwoord
        if (mailbox != null) {
            if (mailbox.getStatus() == Mailbox.STATUS_NOT_PRESENT) {
                LOGGER.warn("Geen mailbox aanwezig. Spd-foutcode 1036");
                mailbox.close();
                mailbox = null;
                resultcode = ERRORCODE_GEEN_MAILBOX;
            } else if (mailbox.getStatus() == Mailbox.STATUS_TEMPORARY_BLOCKED) {
                LOGGER.warn("Mailbox tijdelijk geblokkeerd. Spd-foutcode 1037");
                mailbox.close();
                mailbox = null;
                resultcode = ERRORCODE_MAILOX_GEBLOKKEERD;
            } else if (!mailbox.checkPassword(spdAdapter.getPassword())) {
                LOGGER.info("Ongeldig wachtwoord bij inloggen voor mailbox " + mailbox.getMailboxnr());
                mailbox.close();
                mailbox = null;
                resultcode = ERRORCODE_WRONG_PASSWORD;
            }
        }

        if (mailbox == null) {
            // Foutsituatie en dan moet de vorige mailbox open blijven
            mailbox = vorigeMailbox;
        } else {
            if (vorigeMailbox != null) {
                try {
                    vorigeMailbox.save();
                } catch (final MailboxException e) {
                    LOGGER.error("Probleem bij opslaan en afsluiten mailbox na inloggen op andere mailbox.", e);
                } finally {
                    vorigeMailbox.close();
                }
            }
        }

        spdAdapter.sendLogonConfirmation(mailbox, resultcode);
    }

    private int openMailbox() {
        int resultcode = ERRORCODE_SUCCES;

        LOGGER.debug("Opening mailbox for " + spdAdapter.getLogonUsername());
        mailbox = mbFactory.getMailbox(spdAdapter.getLogonUsername());

        try {
            mailbox.open();
        } catch (final MailboxException e) {
            LOGGER.error("Probleem bij opnenen mailbox", e);
            mailbox.close();
            mailbox = null;
            resultcode = ERRORCODE_OVERIG;
        }
        return resultcode;
    }

    private void handleLogoff() throws IOException {
        if (mailbox != null) {
            try {
                mailbox.save();
                mailbox.close();
                mailbox = null;
            } catch (final MailboxException e) {
                LOGGER.error("Probleem bij opslaan en afsluiten mailbox na uitloggen.", e);
            }
        }

        spdAdapter.sendLogoffConfirmation();

    }

    private void handleChangePassword() throws IOException {
        int errorcode = ERRORCODE_SUCCES;

        if (!mailbox.checkPassword(spdAdapter.getPassword())) {
            LOGGER.info("Ongeldig wachtwoord bij wijzigen wachtwoord voor mailbox " + mailbox.getMailboxnr());
            errorcode = ERRORCODE_WRONG_PASSWORD;
        } else {
            // Alle andere controles die van toepassing zijn op een wachtwoord, doet deze implementatie niet.
            // Kan later natuurlijk wel worden toegevoegd.
            mailbox.setPassword(spdAdapter.getNewPassword());
            try {
                mailbox.save();
            } catch (final MailboxException e) {
                LOGGER.warn("Probleem bij opslaan mailbox na wijzigen wachtwoord", e);
            }
        }

        spdAdapter.sendChangePasswordConfirmation(mailbox, errorcode);
    }

    /**
     * Bij de PutMessage openen we de mailbox van de recipient om daar het bericht aan toe te voegen.
     * 
     * @throws IOException
     */
    private void handlePutMessage() throws IOException {
        if (mailbox == null) {
            LOGGER.warn("Put message zonder mailbox");
            spdAdapter.sendPutMessageConfirmation(null, null, ERRORCODE_COMMANDO_NIET_TOEGESTAAN);
        }

        storeMessageAndSendConfirmation();

    }

    private void handleGetMessage() throws IOException {
        spdAdapter.sendGetMessageResult(mailbox);

    }

    private void handleListMessages() throws IOException {
        spdAdapter.sendListMessagesResult(mailbox);

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void storeMessageAndSendConfirmation() throws IOException {
        final FormatSpdMessage mesg = spdAdapter.getMsg();

        // SITUATIE VOOR TEST: Indien de geadresseerd mailbox 9999999 is dan geven we
        // foutmelding 1052 Geadresseerde niet bereikbaar vanuit deze mailbox (Security error).
        if ("9999999".equals(mesg.getPutMessageMessageHeadingFirstRecipientOrName())) {
            spdAdapter.sendPutMessageConfirmation(mailbox, null, ERRORCODE_PUTMESSAGE_GEADDRESSEERDE_NIET_BEREIKBAAR);
            return;
        }

        final MailboxEntry sentEntry =
                maakMailboxEntry(
                    mesg.getPutMessageMessageHeadingFirstRecipientOrName(),
                    mesg.getPutMessageMessageHeadingCrossReference(),
                    mesg.getMessage(),
                    mesg.getPutMessageMessageHeadingMessageId(),
                    MailboxEntry.STATUS_SENT);
        mailbox.addEntry(sentEntry);

        final boolean specialCaseRecipientIsThisMailbox = mailbox.getMailboxnr().equals(mesg.getPutMessageMessageHeadingFirstRecipientOrName());
        final Mailbox recipientMailbox;
        if (specialCaseRecipientIsThisMailbox) {
            recipientMailbox = mailbox;
        } else {
            recipientMailbox = mbFactory.getMailbox(mesg.getPutMessageMessageHeadingFirstRecipientOrName());
        }

        try {
            if (!specialCaseRecipientIsThisMailbox) {
                recipientMailbox.open();
            }

            if (recipientMailbox.getStatus() == Mailbox.STATUS_TEMPORARY_BLOCKED) {
                createDeliveryReport(sentEntry, SpdAdapter.DELIVERY_REPORT_BLOCKED);
            } else if (recipientMailbox.getStatus() == Mailbox.STATUS_NOT_PRESENT) {
                createDeliveryReport(sentEntry, SpdAdapter.DELIVERY_REPORT_NA);
            } else {
                final MailboxEntry entry =
                        maakMailboxEntry(
                            mesg.getLogonUsername(),
                            mesg.getPutMessageMessageHeadingCrossReference(),
                            mesg.getMessage(),
                            mesg.getPutMessageMessageHeadingMessageId(),
                            MailboxEntry.STATUS_NEW);

                recipientMailbox.addEntry(entry);
                recipientMailbox.save();
            }
        } catch (final MailboxException e) {
            LOGGER.error("Fout bij opslaan bericht in mailbox van ontvangende partij.", e);
        } finally {
            if (!specialCaseRecipientIsThisMailbox) {
                recipientMailbox.close();
            }
        }

        //
        spdAdapter.sendPutMessageConfirmation(mailbox, sentEntry, ERRORCODE_SUCCES);
    }

    private MailboxEntry maakMailboxEntry(
        final String originatorOrRecipient,
        final String crossReference,
        final String message,
        final String messageId,
        final int status)
    {
        final MailboxEntry mailboxEntry = new MailboxEntry();
        mailboxEntry.setOriginatorOrRecipient(originatorOrRecipient);
        mailboxEntry.setCrossReference(crossReference);
        mailboxEntry.setMesg(message);
        mailboxEntry.setMessageId(messageId);
        mailboxEntry.setStatus(status);
        return mailboxEntry;
    }

    private void createDeliveryReport(final MailboxEntry sentEntry, final String deliveryReport) {
        final DecimalFormat dfSequence = new DecimalFormat("000000000");

        final FormatSpdMessage mesg = spdAdapter.getMsg();
        final MailboxEntry entry = new MailboxEntry();
        entry.setOriginatorOrRecipient(mesg.getPutMessageMessageHeadingFirstRecipientOrName());
        final String message = deliveryReport + ";" + dfSequence.format(sentEntry.getMsSequenceId());
        entry.setMesg(message);
        entry.setStatus(MailboxEntry.STATUS_NEW);

        mailbox.addEntry(entry);

    }
}
