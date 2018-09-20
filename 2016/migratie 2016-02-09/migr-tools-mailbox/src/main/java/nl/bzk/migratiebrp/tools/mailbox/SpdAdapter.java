/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox.FilterResult;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;

/**
 * SPD Adapter.
 */
public final class SpdAdapter {

    /** Delivery report. */
    public static final String DELIVERY_REPORT = "DelRep";
    /** Delivery report: blocked. */
    public static final String DELIVERY_REPORT_BLOCKED = DELIVERY_REPORT + "Blocked";
    /** Delivery report: not available. */
    public static final String DELIVERY_REPORT_NA = DELIVERY_REPORT + "NA";
    /** Status report. */
    public static final String STATUS_REPORT = "StaRep";

    private static final int START_INDEX_FIRST_RECIPIENT_OR_NAME = 37;
    private static final int START_INDEX_SEQUENCE_NUMBER = 10;
    private static final String NUL_STRING_VALUE = "0";
    private static final String EMPTY_SPACER_DELIVERY_REPORT = "           ";

    private static final int OPERATION_IS_GEEN_GETAL = 1002;
    private static final int ANDERE_DATA_LENGTE_VERWACHT = 1266;
    private static final int LENGTE_IS_GEEN_GETAL = 1271;

    private static final int RESULT_TERMINATOR = 1;

    private static final int LENGTH_LENGTH = 5;

    private static final int OPER_LENGTH = 3;

    private static final int OPER_PUT_ENV = 120;

    private static final int OPER_MSG_HEADING = 150;

    private static final int OPER_MSG_BODY = 180;

    private static final int OPER_GETMSG = 200;

    private static final int OPER_LISTMSG = 400;

    private static final int OPER_LOGON = 900;

    private static final int OPER_CHANGE_PASSWD = 910;

    private static final int OPER_LOGOFF = 990;

    /** Lengte van Message ID en reference. */
    private static final int MESSAGE_ID_LENGTH = 12;

    /** Lengte van verschillende Name velden. */
    private static final int NAME_LENGTH = 7;

    /** Lengte van Password veld. */
    private static final int PASSWORD_LENGTH = 8;

    /** Lengte van verschillende Number velden. */
    private static final int NUMBER_LENGTH = 3;

    /** Lengte van sequence numbers. */
    private static final int SEQUENCE_NUMBER_LENGTH = 9;

    private static final int SIX_HOURS = 6;

    private static final String TERMINATOR = "00000";

    /** Separator. */
    private static final String Z = "Z";

    /** Begin ASCII printable range */
    private static final int ASCII_PRINTABLE_START = 32;
    /** Einde ASCII printable range */
    private static final int ASCII_PRINTABLE_END = 126;

    private static final DecimalFormat DF_SEQUENCE = new DecimalFormat("000000000");
    private static final DecimalFormat DF_RESULT_CODE = new DecimalFormat("0000");
    private static final DecimalFormat DF_LENGTH = new DecimalFormat(TERMINATOR);

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Socket socket;

    private final InputStream in;

    private final BufferedOutputStream out;

    private final FormatSpdMessage msg;

    private final SimpleDateFormat dateFormat;

    /**
     * Constructor.
     *
     * @param socket
     *            socket
     * @throws IOException
     *             als de input- of outputstream niet geopend kan worden.
     */
    public SpdAdapter(final Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = new BufferedOutputStream(socket.getOutputStream());
        msg = new FormatSpdMessage();
        dateFormat = new SimpleDateFormat("yyMMddHHmm");
    }

    /**
     * Geef de waarde van msg.
     *
     * @return msg
     */
    public FormatSpdMessage getMsg() {
        return msg;
    }

    /**
     * Geef de waarde van socket.
     *
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Geef de waarde van password.
     *
     * @return password
     */
    public String getPassword() {
        return msg.logonPassword;
    }

    /**
     * Geef de waarde van new password.
     *
     * @return new password
     */
    public String getNewPassword() {
        return msg.logonNewPassword;
    }

    /**
     * Geef de waarde van limit number.
     *
     * @return limit number
     */
    public int getLimitNumber() {
        return msg.listMessagesLimitNumber;
    }

    /**
     * Geef de waarde van ms status.
     *
     * @return ms status
     */
    public String getMsStatus() {
        return msg.msStatus;
    }

    /**
     * Geef de waarde van logon username.
     *
     * @return logon username
     */
    public String getLogonUsername() {
        return msg.logonUsername;
    }

    /**
     * Geef de waarde van ms sequence nr.
     *
     * @return ms sequence nr
     */
    public int getMsSequenceNr() {
        return msg.msSequenceNr;
    }

    /**
     * Geef de waarde van start at ms sequence nr.
     *
     * @return start at ms sequence nr
     */
    public int getStartAtMsSequenceNr() {
        return msg.startAtMsSequenceNr;
    }

    /**
     * Reads a specified number of bytes from the inputstream. This method blocks until the specified number of bytes
     * has been reached or until the end of the inputstream has been reached.
     *
     * @param buf
     *            Byte-array to put the read bytes in
     * @param startingOffset
     *            Offset in buf to start writing bytes to
     * @param numberOfBytesToRead
     *            Number of bytes to read
     * @return number of bytes actually read; this should be equal to numberOfBytesToRead, unless the end of the
     *         inputstream has been reached
     *
     * @throws IOException
     */
    private int readBlocked(final byte[] buf, final int startingOffset, final int numberOfBytesToRead) throws IOException {
        LOGGER.debug("readBlocked() with offset=" + startingOffset + " and length=" + numberOfBytesToRead);
        int totalRead = 0;
        int read;
        int offset = startingOffset;

        while (totalRead < numberOfBytesToRead) {
            read = in.read(buf, offset, numberOfBytesToRead - totalRead);
            totalRead += read;
            offset += read;

            if (read < 0) {
                // end of the inputstream; break out of loop
                break;
            }
        }

        return totalRead;
    }

    /**
     * Leest de input tot aan de verwachte terminator('00000') of totdat de verbinding een time-out oplevert.
     *
     * @return the sPD operation
     * @throws IOException
     *             wordt gegooid als er een I/O operatie mislukt.
     */
    public SpdOperations readInput() throws IOException {
        final int maxBerichtLengte = 20000;
        final byte[] buf = new byte[maxBerichtLengte];
        int bytesRead;
        int operation = 0;
        SpdOperations mainOperation = SpdOperations.NO_OP;
        LOGGER.debug("Waiting for data ...");
        // Read message length
        do {
            bytesRead = readBlocked(buf, 0, LENGTH_LENGTH);
            LOGGER.debug("Read length (" + bytesRead + ") " + toHexString(buf, bytesRead));
            if (bytesRead != LENGTH_LENGTH) {
                if (bytesRead < 0) {
                    final String message = "Connection closed by client";
                    LOGGER.info(message);
                    throw new IOException(message);
                }
            } else {
                int length = 0;
                try {
                    length = Integer.parseInt(new String(buf, 0, LENGTH_LENGTH));
                } catch (final NumberFormatException e) {
                    final String message = "Lengte is geen getal";
                    LOGGER.error(message, e);
                    sendNoOperationConfirmation(LENGTE_IS_GEEN_GETAL);
                }

                // Check for the termination sequence
                if (length == 0) {
                    operation = RESULT_TERMINATOR;
                } else {
                    // Read message
                    bytesRead = readBlocked(buf, 0, length);

                    if (bytesRead != length) {
                        LOGGER.warn("Expected " + length + " bytes of data, read: " + bytesRead);
                        sendNoOperationConfirmation(ANDERE_DATA_LENGTE_VERWACHT);
                    } else {
                        LOGGER.debug("Read data: " + toHexString(buf, bytesRead));

                        // Retrieve operation code
                        try {
                            operation = Integer.parseInt(new String(buf, 0, OPER_LENGTH));
                        } catch (final NumberFormatException e) {
                            final String message = "Operation is geen getal";
                            LOGGER.error(message + e);
                            sendNoOperationConfirmation(OPERATION_IS_GEEN_GETAL);
                        }

                        /*
                         * Controleer de opcode en sla eventuele info op (bij bv putMessage)
                         */
                        final int baseOffset = OPER_LENGTH;

                        switch (operation) {
                            case OPER_PUT_ENV:
                                mainOperation = SpdOperations.PUT_MESG;
                                msg.peLength = length;
                                msg.peOpcode = operation;
                                msg.putMessagePutEnvelopeOriginatorOrName = new String(buf, baseOffset, NAME_LENGTH);
                                break;
                            case OPER_MSG_HEADING:
                                msg.mhLength = length;
                                msg.mhOpcode = operation;
                                msg.putMessageMessageHeadingMessageId = new String(buf, baseOffset, MESSAGE_ID_LENGTH);
                                msg.putMessageMessageHeadingCrossReference = new String(buf, baseOffset + MESSAGE_ID_LENGTH, MESSAGE_ID_LENGTH);
                                msg.putMessageMessageHeadingFirstRecipientOrName = new String(buf, START_INDEX_FIRST_RECIPIENT_OR_NAME, NAME_LENGTH);
                                break;
                            case OPER_MSG_BODY:
                                msg.mbLength = length;
                                msg.mbOpcode = operation;
                                if (bytesRead - OPER_LENGTH > 0) {
                                    final byte[] buffer = new byte[bytesRead - OPER_LENGTH];
                                    System.arraycopy(buf, OPER_LENGTH, buffer, 0, buffer.length);
                                    msg.message = GBACharacterSet.convertTeletexByteArrayToString(buffer);
                                } else {
                                    msg.message = "";
                                }
                                LOGGER.debug("putMessage OPER_MSG_BODY: {}", msg);
                                break;
                            case OPER_LOGON:
                                mainOperation = SpdOperations.LOGON;
                                msg.logonUsername = new String(buf, baseOffset, NAME_LENGTH);
                                msg.logonPassword = new String(buf, baseOffset + NAME_LENGTH, PASSWORD_LENGTH);
                                break;
                            case OPER_CHANGE_PASSWD:
                                mainOperation = SpdOperations.CHG_PWD;
                                msg.logonPassword = new String(buf, baseOffset, PASSWORD_LENGTH);
                                msg.logonNewPassword = new String(buf, baseOffset + PASSWORD_LENGTH, PASSWORD_LENGTH);
                                break;
                            case OPER_LISTMSG:
                                mainOperation = SpdOperations.LIST_MESGS;
                                msg.listMessagesLimitNumber = Integer.parseInt(new String(buf, baseOffset, NUMBER_LENGTH));
                                msg.msStatus = new String(buf, baseOffset + NUMBER_LENGTH, NUMBER_LENGTH);
                                msg.startAtMsSequenceNr = Integer.parseInt(new String(buf, START_INDEX_SEQUENCE_NUMBER, SEQUENCE_NUMBER_LENGTH));
                                break;
                            case OPER_GETMSG:
                                mainOperation = SpdOperations.GET_MESG;
                                msg.msSequenceNr = Integer.parseInt(new String(buf, baseOffset, SEQUENCE_NUMBER_LENGTH));
                                break;
                            case OPER_LOGOFF:
                                mainOperation = SpdOperations.LOGOFF;
                                break;
                            default:
                                mainOperation = SpdOperations.NO_OP;
                                break;
                        }
                    }
                }
            }
        } while (operation != RESULT_TERMINATOR);
        return mainOperation;
    }

    /**
     * Send the get message message to the client.
     *
     * @param mailbox
     *            mailbox
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendGetMessageResult(final Mailbox mailbox) throws IOException {
        final int sequenceNr = getMsSequenceNr();
        final MailboxEntry entry = mailbox.getEntry(sequenceNr);

        LOGGER.info("[Mailbox {}]: Getting message {}", mailbox.getMailboxnr(), sequenceNr);

        if (entry != null) {
            final String originator = entry.getOriginatorOrRecipient();
            final String recipient = mailbox.getMailboxnr();
            final Date now = new Date();
            final String msEntry = "210" + DF_SEQUENCE.format(sequenceNr);

            final StringBuilder record = new StringBuilder();
            record.append(DF_LENGTH.format(msEntry.length())).append(msEntry);

            final String mesg = entry.getMesg();
            if (mesg.startsWith(DELIVERY_REPORT)) {
                final String delReport = createDeliveryReport(entry, now);
                record.append(DF_LENGTH.format(delReport.length())).append(delReport);
            } else {
                // MessageResult = getEnvelope + messageHeading + messageBody
                final String getEnvelope = "220" + originator + "21" + dateFormat.format(now) + Z + dateFormat.format(now) + Z + recipient;
                record.append(DF_LENGTH.format(getEnvelope.length())).append(getEnvelope);
                if (mesg.startsWith(STATUS_REPORT)) {
                    final String statReport = "270" + originator + "1" + entry.getCrossReference() + NUL_STRING_VALUE;
                    record.append(DF_LENGTH.format(statReport.length())).append(statReport);
                } else {
                    final String messageHeading = "250" + entry.getMessageId() + entry.getCrossReference() + originator + recipient + NUL_STRING_VALUE;

                    final String mesgBody = "280" + mesg;
                    record.append(DF_LENGTH.format(messageHeading.length())).append(messageHeading);
                    record.append(DF_LENGTH.format(mesgBody.length())).append(mesgBody);
                }
            }
            out.write(GBACharacterSet.convertTeletexStringToByteArray(record.toString()));
        } else {
            // Stuur mesg confirmation met foutcode
            final String record = "000072901071";
            out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        }
        out.write(GBACharacterSet.convertTeletexStringToByteArray(TERMINATOR));
        out.flush();
    }

    private String createDeliveryReport(final MailboxEntry entry, final Date now) {
        final String mesg = entry.getMesg();
        final String[] params = mesg.split(";", -1);
        final String delReportType = params[0];
        final String msSeqId = params[1];
        final StringBuilder delReport = new StringBuilder();
        delReport.append("260").append(dateFormat.format(now)).append(Z);
        delReport.append(msSeqId).append("001");
        delReport.append(entry.getOriginatorOrRecipient());
        if (DELIVERY_REPORT_BLOCKED.equals(delReportType)) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.HOUR, SIX_HOURS);
            delReport.append(dateFormat.format(cal.getTime())).append(Z);
            delReport.append("1035");
        } else {
            delReport.append(EMPTY_SPACER_DELIVERY_REPORT);
            delReport.append("1059");
        }

        return delReport.toString();
    }

    /**
     * Send the list messages message to the client.
     *
     * @param mailbox
     *            mailbox
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendListMessagesResult(final Mailbox mailbox) throws IOException {
        final int startAtMsSequenceNr = getStartAtMsSequenceNr();
        final String msStatus = getMsStatus();
        final int limitNumber = getLimitNumber();

        final FilterResult filterResult = mailbox.filterInbox(msStatus, startAtMsSequenceNr, limitNumber);
        final List<MailboxEntry> filteredEntries = filterResult.getEntries();
        final int nextFilteredMSSequenceNr = filterResult.getNextMsSequenceId();

        final String nextMsSequenceNr = DF_SEQUENCE.format(nextFilteredMSSequenceNr);
        final int nrOfEntries = filteredEntries.size();
        if (nrOfEntries > 0) {
            LOGGER.info("[Mailbox {}]: Listing {} messages (status={}, startAt={})", new Object[] {mailbox.getMailboxnr(),
                                                                                                   nrOfEntries,
                                                                                                   msStatus,
                                                                                                   startAtMsSequenceNr, });

            final String listResult = "410" + nextMsSequenceNr;
            final StringBuilder msEntries = new StringBuilder();
            final Date now = new Date();
            for (final MailboxEntry entry : filteredEntries) {
                msEntries.append(DF_SEQUENCE.format(entry.getMsSequenceId()))
                         .append(entry.getStatus())
                         .append(NUL_STRING_VALUE)
                         .append(dateFormat.format(now))
                         .append(Z)
                         .append(entry.getOriginatorOrRecipient());
            }

            final String msList = "411" + DF_LENGTH.format(nrOfEntries) + msEntries;
            final String record = DF_LENGTH.format(listResult.length()) + listResult + DF_LENGTH.format(msList.length()) + msList + TERMINATOR;
            out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        } else {
            LOGGER.debug(
                "[Mailbox {}]: Listing no messages (status={}, startAt={})",
                new Object[] {mailbox.getMailboxnr(), msStatus, startAtMsSequenceNr, });
            sendListMessagesConfirmationNoMesgs();
        }
        out.flush();
    }

    private void sendListMessagesConfirmationNoMesgs() throws IOException {
        final String message = "419" + "1113";
        final String record = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * Send summarize error code.
     *
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendSummarizeConfirmation() throws IOException {

        final String message = "590" + "1112";
        final String record = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * The logon confirmation.
     *
     * @param mailbox
     *            mailbox
     * @param resultcode
     *            resultcode
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendLogonConfirmation(final Mailbox mailbox, final int resultcode) throws IOException {
        LOGGER.debug("[Mailbox {}]: Logon confirmation (result={})", mailbox == null ? null : mailbox.getMailboxnr(), resultcode);

        final String message =
                "909" + DF_RESULT_CODE.format(resultcode) + EMPTY_SPACER_DELIVERY_REPORT + "012345678901234567890123456789012345678901234567890123456789";
        final String record = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * The change password confirmation.
     *
     * @param mailbox
     *            mailbox
     * @param resultcode
     *            resultcode
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendChangePasswordConfirmation(final Mailbox mailbox, final int resultcode) throws IOException {
        LOGGER.info("[Mailbox {}]: Password change confirmation (result={})", mailbox.getMailboxnr(), resultcode);

        final String message = "919" + DF_RESULT_CODE.format(resultcode);
        final String confirmation = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(confirmation));
        out.flush();
    }

    /**
     * The logoff confirmation.
     *
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendLogoffConfirmation() throws IOException {
        LOGGER.debug("Logoff confirmation");

        final String message = "999" + DF_RESULT_CODE;
        final String record = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * Send a PutMessageConfirmation using this.msg.PutMessageMessageHeadingMessageId. It reads the first line of the
     * configured filename, and sends that line back as the putMessageConfirmation.
     *
     * @param mailbox
     *            mailbox
     * @param entry
     *            mailbox entry
     * @param resultcode
     *            resulcode
     * @throws IOException
     *             bij schrijf fouten
     */
    public void sendPutMessageConfirmation(final Mailbox mailbox, final MailboxEntry entry, final int resultcode) throws IOException {
        final Integer msSequenceId = entry == null ? null : entry.getMsSequenceId();
        final String mailboxNr = mailbox == null ? null : mailbox.getMailboxnr();
        LOGGER.info("[Mailbox {}]: Put message confirmation (dispatchSequenceNumber {})", new Object[] {mailboxNr, msSequenceId, });

        final StringBuilder message = new StringBuilder();
        message.append("190").append(DF_RESULT_CODE.format(resultcode));
        if (entry != null) {
            message.append(DF_SEQUENCE.format(msSequenceId));
            message.append(dateFormat.format(new Date()));
            message.append(Z).append(entry.getMessageId());
        }
        final String record = DF_LENGTH.format(message.length()) + message.toString() + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    private void sendNoOperationConfirmation(final int errorcode) throws IOException {

        final String message = "009" + errorcode;
        final String record = DF_LENGTH.format(message.length()) + message + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * Helper function to dump the received bytes in a somewhat readable form.
     *
     * @param buf
     *            the received bytes
     * @param length
     *            the length of the sequence to dump from the received bytes
     * @return A readable String respresentation of the byte array
     */
    private String toHexString(final byte[] buf, final int length) {
        if (buf == null) {
            return "<null>";
        }
        final StringBuilder sb = new StringBuilder("(" + length + "/" + buf.length + ")");
        for (int i = 0; i < buf.length && i < length; i++) {
            if (buf[i] >= ASCII_PRINTABLE_START && buf[i] <= ASCII_PRINTABLE_END) {
                sb.append((char) buf[i]);
            } else {
                sb.append("[").append(buf[i]).append("]");
            }
        }
        return sb.toString();
    }

    /**
     * Data-object gebruikt door de SPD adapter.
     */
    public static final class FormatSpdMessage {

        private int msSequenceNr;
        private int startAtMsSequenceNr;

        private String msStatus;

        private String logonUsername;

        private String logonPassword;
        private String logonNewPassword;

        private int peLength;
        private int mhLength;
        private int mbLength;

        private int peOpcode;
        private int mhOpcode;
        private int mbOpcode;

        private int listMessagesLimitNumber;

        private String putMessagePutEnvelopeOriginatorOrName;

        private String putMessageMessageHeadingMessageId;
        private String putMessageMessageHeadingCrossReference;
        private String putMessageMessageHeadingFirstRecipientOrName;

        private String message;

        /**
         * Geef de waarde van message.
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Geef de waarde van logon username.
         *
         * @return logon username
         */
        public String getLogonUsername() {
            return logonUsername;
        }

        /**
         * Geef de waarde van put message message heading message id.
         *
         * @return put message message heading message id
         */
        public String getPutMessageMessageHeadingMessageId() {
            return putMessageMessageHeadingMessageId;
        }

        /**
         * Geef de waarde van put message message heading cross reference.
         *
         * @return put message message heading cross reference
         */
        public String getPutMessageMessageHeadingCrossReference() {
            return putMessageMessageHeadingCrossReference;
        }

        /**
         * Geef de waarde van put message message heading first recipient or name.
         *
         * @return put message message heading first recipient or name
         */
        public String getPutMessageMessageHeadingFirstRecipientOrName() {
            return putMessageMessageHeadingFirstRecipientOrName;
        }

        @Override
        public String toString() {
            final StringBuilder buff = new StringBuilder("\n----------LogonData--------------------------------------");
            buff.append("\nLogonUsername=                      ").append(logonUsername);
            buff.append("\nLogonPassword=                      ").append(logonPassword);
            buff.append("\nListMessagesLimitNumber=            ").append(listMessagesLimitNumber);
            buff.append("\n----------PutMessage-------------------------------------");
            buff.append("\nEnvelopeLength=                     ").append(peLength);
            buff.append("\nEnvelopeOperationCode=              ").append(peOpcode);
            buff.append("\nPutEnvelopeOriginatorOrName=        ").append(putMessagePutEnvelopeOriginatorOrName);
            buff.append("\nMessageHeadingLength=               ").append(mhLength);
            buff.append("\nMessageHeadingOperationCode=        ").append(mhOpcode);
            buff.append("\nMessageHeadingMessageId=            ").append(putMessageMessageHeadingMessageId);
            buff.append("\nputMessageMessageHeadingCrossRef=   ").append(putMessageMessageHeadingCrossReference);
            buff.append("\nMessageHeadingFirstRecipientOrName= ").append(putMessageMessageHeadingFirstRecipientOrName);
            buff.append("\nMessageBodyLength=                  ").append(mbLength);
            buff.append("\nMessageBodyOperationCode=           ").append(mbOpcode);
            buff.append("\nBodyString=                         ").append(message);
            buff.append("\n---------------------------------------------------------");
            return buff.toString();
        }
    }

}
