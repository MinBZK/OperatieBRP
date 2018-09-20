/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import nl.ictu.spg.common.util.conversion.GBACharacterSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpdAdapter {

    public static final int BLOCK_SIZE = 4096;

    public static final int RESULT_TERMINATOR = 1;

    public static final int LENGTH_LENGTH = 5;

    public static final int OPER_LENGTH = 3;

    public static final int OPER_PUT_ENV = 120;

    public static final int OPER_MSG_HEADING = 150;

    public static final int OPER_MSG_BODY = 180;

    public static final int OPER_GETMSG = 200;

    public static final int OPER_LISTMSG = 400;

    public static final int OPER_SUMMARIZE = 500;

    public static final int OPER_LOGON = 900;

    public static final int OPER_CHANGE_PASSWD = 910;

    public static final int OPER_LOGOFF = 990;

    public static final String TERMINATOR = "00000";

    private static final String DELIVERY_REPORT = "DelRep";
    public static final String DELIVERY_REPORT_BLOCKED = DELIVERY_REPORT + "Blocked";
    public static final String DELIVERY_REPORT_NA = DELIVERY_REPORT + "NA";

    public static final String STATUS_REPORT = "StaRep";

    private static final SimpleDateFormat DF_DATE_TIME = new SimpleDateFormat("yyMMddHHmm");

    private static final DecimalFormat DF_SEQUENCE = new DecimalFormat("000000000");
    private static final DecimalFormat DF_RESULT_CODE = new DecimalFormat("0000");
    private static final DecimalFormat DF_LENGTH = new DecimalFormat("00000");
    private Socket socket = null;

    private static final Log LOGGER = LogFactory.getLog(SpdAdapter.class);

    private InputStream in = null;

    private BufferedOutputStream out = null;

    public FormatsPdMessage msg = null;

    public SpdAdapter(final Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = new BufferedOutputStream(socket.getOutputStream());
        msg = new FormatsPdMessage();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getPassword() {
        return msg.logonPassword;
    }

    public String getNewPassword() {
        return msg.logonNewPassword;
    }

    public int getLimitNumber() {
        return msg.listMessagesLimitNumber;
    }

    public String getMsStatus() {
        return msg.msStatus;
    }

    public String getLogonUsername() {
        return msg.logonUsername;
    }

    public int getMsSequenceNr() {
        return msg.msSequenceNr;
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (final Exception e) {
            LOGGER.debug(e);
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (final Exception e) {
            LOGGER.error(e);
        }
        try {
            // Sleep for 5 sec so the client can read the response
            socket.close();
        } catch (final Exception e) {
            LOGGER.debug(e);
        }
        LOGGER.info("Connection closed");
    }

    /**
     * Reads a specified number of bytes from the inputstream. This method blocks until the specified number of bytes
     * has been reached or until the end of the inputstream has been reached.
     * 
     * @param is
     *            InputStream to read bytes from
     * @param buf
     *            Byte-array to put the read bytes in
     * @param offset
     *            Offset in buf to start writing bytes to
     * @param numberOfBytesToRead
     *            Number of bytes to read
     * @return number of bytes actually read; this should be equal to numberOfBytesToRead, unless the end of the
     *         inputstream has been reached
     * 
     * @throws IOException
     */
    private int readBlocked(final InputStream is, final byte buf[], int offset, final int numberOfBytesToRead)
            throws IOException {
        LOGGER.debug("readBlocked() with offset=" + offset + " and length=" + numberOfBytesToRead);
        int totalRead = 0;
        int read = 0;

        while (totalRead < numberOfBytesToRead) {
            read = in.read(buf, offset, numberOfBytesToRead - totalRead);
            totalRead += read;
            offset += read;

            if (read < 0) {
                break; // end of the inputstream; break out of loop
            }
        }

        return totalRead;
    }

    /**
     * Leest de input tot aan de verwachte terminator('00000') of totdat de verbinding een time-out opleverd.
     * 
     * @return the sPD operation
     * @throws IOException
     */
    public SpdOperations readInput() throws IOException {
        final byte buf[] = new byte[20000];
        int bytesRead = 0;
        int operation = 0;
        SpdOperations mainOperation = SpdOperations.NO_OP;
        LOGGER.debug("Waiting for data ...");
        // Read message length
        do {
            bytesRead = readBlocked(in, buf, 0, LENGTH_LENGTH);
            LOGGER.debug("Read length (" + bytesRead + ") " + toHexString(buf, bytesRead));
            if (bytesRead != LENGTH_LENGTH) {
                if (bytesRead < 0) {
                    final String msg = "Connection closed by client";
                    LOGGER.info(msg);
                    throw new IOException(msg);
                }
            } else {
                int length = 0;
                try {
                    length = Integer.parseInt(new String(buf, 0, LENGTH_LENGTH));
                } catch (final NumberFormatException e) {
                    final String msg = "Lengte is geen getal";
                    LOGGER.error(msg, e);
                    sendNoOperationConfirmation(1271);
                }

                // Check for the termination sequence
                if (length == 0) {
                    operation = RESULT_TERMINATOR;
                } else {
                    // Read message
                    bytesRead = readBlocked(in, buf, 0, length);

                    if (bytesRead != length) {
                        LOGGER.warn("Expected " + length + " bytes of data, read: " + bytesRead);
                        sendNoOperationConfirmation(1266);
                    } else {
                        LOGGER.debug("Read data: " + toHexString(buf, bytesRead));

                        // Retrieve operation code
                        try {
                            operation = Integer.parseInt(new String(buf, 0, OPER_LENGTH));
                        } catch (final NumberFormatException e) {
                            final String msg = "Operation is geen getal";
                            LOGGER.error(msg + e);
                            sendNoOperationConfirmation(1002);
                        }

                        /*
                         * Controleer de opcode en sla eventuele info op (bij bv putMessage)
                         */
                        switch (operation) {
                            case OPER_PUT_ENV:
                                mainOperation = SpdOperations.PUT_MESG;
                                msg.peLength = length;
                                msg.peOpcode = operation;
                                msg.putMessagePutEnvelopeOriginatorOrName = new String(buf, 3, 7);
                                break;
                            case OPER_MSG_HEADING:
                                msg.mhLength = length;
                                msg.mhOpcode = operation;
                                msg.putMessageMessageHeadingMessageId = new String(buf, 3, 12);
                                msg.putMessageMessageHeadingCrossReference = new String(buf, 15, 12);
                                msg.putMessageMessageHeadingFirstRecipientOrName = new String(buf, 37, 7);
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
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("putMessage OPER_MSG_BODY: " + msg);
                                }
                                break;
                            case OPER_LOGON:
                                mainOperation = SpdOperations.LOGON;
                                msg.logonUsername = new String(buf, 3, 7);
                                msg.logonPassword = new String(buf, 10, 8);
                                break;
                            case OPER_CHANGE_PASSWD:
                                mainOperation = SpdOperations.CHG_PWD;
                                msg.logonPassword = new String(buf, 3, 8);
                                msg.logonNewPassword = new String(buf, 11, 8);
                                break;
                            case OPER_LISTMSG:
                                mainOperation = SpdOperations.LIST_MESGS;
                                msg.listMessagesLimitNumber = Integer.parseInt(new String(buf, 3, 3));
                                msg.msStatus = new String(buf, 6, 3);
                                break;
                            case OPER_GETMSG:
                                mainOperation = SpdOperations.GET_MESG;
                                msg.msSequenceNr = Integer.parseInt(new String(buf, 3, 9));
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
     * Send the message to the client.
     * 
     * @param gemeenteCode
     * @param message
     * @throws IOException
     */
    public void sendGetMessageResult(final Mailbox mailbox) throws IOException {
        final DecimalFormat dfSequence = new DecimalFormat("000000000");
        final int sequenceNr = getMsSequenceNr();
        final MailboxEntry entry = mailbox.getMsgFormInbox(sequenceNr);

        if (entry != null) {
            final String originator = entry.getOriginatorOrRecipient();
            final String recipient = mailbox.getNummer();
            final Date now = new Date();
            final String msEntry = "210" + dfSequence.format(sequenceNr);

            final StringBuilder record = new StringBuilder();
            record.append(DF_LENGTH.format(msEntry.length())).append(msEntry);

            final String mesg = entry.getMesg();
            if (mesg.startsWith(DELIVERY_REPORT)) {
                final String delReport = createDeliveryReport(entry, now);
                record.append(DF_LENGTH.format(delReport.length())).append(delReport);
            } else {
                // MessageResult = getEnvelope + messageHeading + messageBody
                final String getEnvelope =
                        "220" + originator + "21" + DF_DATE_TIME.format(now) + "Z" + DF_DATE_TIME.format(now) + "Z"
                                + recipient;
                record.append(DF_LENGTH.format(getEnvelope.length())).append(getEnvelope);
                if (mesg.startsWith(STATUS_REPORT)) {
                    final String statReport = "270" + originator + "1" + entry.getCrossReference() + "0";
                    record.append(DF_LENGTH.format(statReport.length())).append(statReport);
                } else {
                    final String messageHeading =
                            "250" + entry.getMessageId() + entry.getCrossReference() + originator + recipient + "0";

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
        delReport.append("260").append(DF_DATE_TIME.format(now)).append("Z");
        delReport.append(msSeqId).append("001");
        delReport.append(entry.getOriginatorOrRecipient());
        if (DELIVERY_REPORT_BLOCKED.equals(delReportType)) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.HOUR, 6);
            delReport.append(DF_DATE_TIME.format(cal.getTime())).append("Z");
            delReport.append("1035");
        } else {
            delReport.append("           ");
            delReport.append("1059");
        }

        return delReport.toString();
    }

    public void sendListMessagesResult(final Mailbox mailbox) throws IOException {
        final DecimalFormat dfSequence = new DecimalFormat("000000000");
        final Set<MailboxEntry> filteredEntries = mailbox.filterInbox(getMsStatus(), getLimitNumber());
        final int nextFilteredMSSequenceNr = mailbox.getNextFilteredMSSequenceNr();

        final String nextMsSequenceNr = dfSequence.format(nextFilteredMSSequenceNr);
        final int nrOfEntries = filteredEntries.size();
        if (nrOfEntries > 0) {
            final String listResult = "410" + nextMsSequenceNr;
            final StringBuffer msEntries = new StringBuffer();
            final Date now = new Date();
            for (final MailboxEntry entry : filteredEntries) {
                msEntries.append(dfSequence.format(entry.getMsSequenceId()) + entry.getStatus() + "0"
                        + DF_DATE_TIME.format(now) + "Z" + entry.getOriginatorOrRecipient());
            }

            final String MSList = "411" + DF_LENGTH.format(nrOfEntries) + msEntries;
            final String record =
                    DF_LENGTH.format(listResult.length()) + listResult + DF_LENGTH.format(MSList.length()) + MSList
                            + TERMINATOR;
            LOGGER.info("sendListMessagesResult length: " + nrOfEntries);
            out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        } else {
            sendListMessagesConfirmationNoMesgs();
        }
        out.flush();
    }

    public void sendListMessagesConfirmationNoMesgs() throws IOException {
        final String msg = "419" + "1113";
        final String record = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * Send summarize error code.
     * 
     * @throws IOException
     */
    public void sendSummarizeConfirmation() throws IOException {

        final String msg = "590" + "1112";
        final String record = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * The logon confirmation.
     * 
     * @throws IOException
     */
    public void sendLogonConfirmation(final int resultcode) throws IOException {

        final String msg =
                "909" + DF_RESULT_CODE.format(resultcode) + "           "
                        + "012345678901234567890123456789012345678901234567890123456789";
        final String record = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    public void sendChangePasswordConfirmation(final int resultcode) throws IOException {

        final String msg = "919" + DF_RESULT_CODE.format(resultcode);
        final String confirmation = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(confirmation));
        out.flush();
    }

    /**
     * The logoff confirmation.
     * 
     * @throws IOException
     */
    public void sendLogoffConfirmation() throws IOException {
        final String msg = "999" + "0000";
        final String record = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    /**
     * Send a PutMessageConfirmation using this.msg.PutMessageMessageHeadingMessageId. It reads the first line of the
     * configured filename, and sends that line back as the putMessageConfirmation.
     * 
     * @throws IOException
     */
    public void sendPutMessageConfirmation(final MailboxEntry entry, final int resultcode) throws IOException {
        final StringBuilder msg = new StringBuilder();
        msg.append("190").append(DF_RESULT_CODE.format(resultcode));
        if (entry != null) {
            msg.append(DF_SEQUENCE.format(entry.getMsSequenceId()));
            msg.append(DF_DATE_TIME.format(new Date()));
            msg.append("Z").append(entry.getMessageId());
        }
        final String record = DF_LENGTH.format(msg.length()) + msg.toString() + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    private void sendNoOperationConfirmation(final int errorcode) throws IOException {

        final String msg = "009" + errorcode;
        final String record = DF_LENGTH.format(msg.length()) + msg + TERMINATOR;
        out.write(GBACharacterSet.convertTeletexStringToByteArray(record));
        out.flush();
    }

    public static class FormatsPdMessage {
        public int msSequenceNr;

        public String msStatus;

        String logonUsername;

        String logonPassword;
        String logonNewPassword;

        int peLength, mhLength, mbLength;

        int peOpcode, mhOpcode, mbOpcode;

        int listMessagesLimitNumber;

        String putMessagePutEnvelopeOriginatorOrName;

        String putMessageMessageHeadingMessageId, putMessageMessageHeadingCrossReference,
                putMessageMessageHeadingFirstRecipientOrName;

        String message;

        @Override
        public String toString() {
            final StringBuffer buff = new StringBuffer("\n----------LogonData--------------------------------------");
            buff.append("\nLogonUsername=                      " + logonUsername);
            buff.append("\nLogonPassword=                      " + logonPassword);
            buff.append("\nListMessagesLimitNumber=            " + listMessagesLimitNumber);
            buff.append("\n----------PutMessage-------------------------------------");
            buff.append("\nEnvelopeLength=                     " + peLength);
            buff.append("\nEnvelopeOperationCode=              " + peOpcode);
            buff.append("\nPutEnvelopeOriginatorOrName=        " + putMessagePutEnvelopeOriginatorOrName);
            buff.append("\nMessageHeadingLength=               " + mhLength);
            buff.append("\nMessageHeadingOperationCode=        " + mhOpcode);
            buff.append("\nMessageHeadingMessageId=            " + putMessageMessageHeadingMessageId);
            buff.append("\nputMessageMessageHeadingCrossRef=   " + putMessageMessageHeadingCrossReference);
            buff.append("\nMessageHeadingFirstRecipientOrName= " + putMessageMessageHeadingFirstRecipientOrName);
            buff.append("\nMessageBodyLength=                  " + mbLength);
            buff.append("\nMessageBodyOperationCode=           " + mbOpcode);
            buff.append("\nBodyString=                         " + message);
            buff.append("\n---------------------------------------------------------");
            return buff.toString();
        }
    }

    /**
     * Helper function to dump the received bytes in a somewhat readable form.
     * 
     * @param in
     * @param length
     * @return A readable String respresentation of the byte array
     */
    private String toHexString(final byte[] in, final int length) {
        if (in == null) {
            return "<null>";
        }
        final StringBuffer sb = new StringBuffer("(" + length + "/" + in.length + ")");
        for (int i = 0; i < in.length && i < length; i++) {
            if (in[i] > 31 && in[i] < 127) {
                sb.append((char) in[i]);
            } else {
                sb.append("[" + in[i] + "]");
            }
        }
        return sb.toString();
    }

}
