/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.ictu.spg.common.util.conversion.GBACharacterSet;
import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.MailboxServerPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.SpdProtocolException;
import nl.moderniseringgba.isc.voisc.mailbox.Connection;
import nl.moderniseringgba.isc.voisc.mailbox.MailboxServerProxy;
import nl.moderniseringgba.isc.voisc.mailbox.Message;
import nl.moderniseringgba.isc.voisc.utils.StringUtil;
import nl.moderniseringgba.isc.voisc.utils.VoaUtil;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * This class is responsible for the communication with the MailboxServer (MBS).
 * <P>
 * Responsibilities:
 * <UL>
 * <LI>create and maintain a SSL-connection with the MailboxServer
 * <LI>logon to the MBS
 * <LI>execute sPd-commands on the MBS
 * <LI>sends LO3Bericht objects to the MBS
 * <LI>receives berichten from the MBS and create LO3Bericht objects
 * <LI>logoff from the MBS
 * </UL>
 * 
 */
public class MailboxServerProxyImpl implements MailboxServerProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int RESULT_CORRECT = 0;
    private static final DecimalFormat DF_SEQ_NR = new DecimalFormat("000000000");
    private static final DecimalFormat DF_LENGTH = new DecimalFormat("00000");
    private String erefPrefix;

    @Inject
    private Connection mailboxServerConnection;

    /**
     * Logoff from the MailboxServer.<br>
     * The following steps are taken:
     * <UL>
     * <LI>a LogoffRequest is created
     * <LI>the LogoffReqest is send to the MBS
     * <LI>a LogoffConfirmation is received
     * <LI>the LogoffConfirmation is interpreted
     * <LI>the existing SSL-connection is closed.
     * </UL>
     * 
     * @throws SpdProtocolException
     *             Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven
     *             lengte of onverwachte operationcode
     * 
     * @see LO3.3 Bijlage IV.3.2.7
     * @see LO3.3 Bijlage IV.4.1.2
     * @see LO3.3 Bijlage IV.5.2.8
     * 
     */
    @Override
    public final void logOff() throws SpdProtocolException {
        LOGGER.debug("#### Logoff");

        final Message request = createLogoffRequest();
        sendSpdMessage(request);

        /****************************************
         * - LogoffConfirmation: Length [5] Operationcode [3] LogoffResult [4] TotalLength: 7 (exclusive lengthField)
         ***************************************/
        final Message message = receiveSpdMessage();
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        try {
            if (nrBytesRead <= 0) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_RCV_CONFIRMATION);
            }

            checkMesgLength(buf, nrBytesRead, MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_CONF_LENGTH);

            final int operationCode =
                    Integer.parseInt(new String(buf, SpdConstants.OPCODE_START, SpdConstants.OPCODE_LENGTH));
            if (operationCode != SpdConstants.OPC_LOGOFF_CONFIRMATION) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_CONF_OPCODE,
                        new Object[] { operationCode });
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_ERROR, null, nfe);
        } finally {
            mailboxServerConnection.disconnect();
        }
    }

    /**
     * Controle op bericht lengte met de opgegeven lengte in het bericht.
     * 
     * @param buffer
     * @param nrBytesRead
     * @param mesgCode
     * @param params
     * @throws SpdProtocolException
     */
    private void checkMesgLength(
            final byte[] buffer,
            final int nrBytesRead,
            final String mesgCode,
            final Object... params) throws SpdProtocolException {
        final int length =
                Integer.parseInt(new String(buffer, SpdConstants.LENGTH_START, SpdConstants.LENGTH_LENGTH));
        final int realLength = nrBytesRead - SpdConstants.LENGTH_LENGTH;
        if (length != realLength) {
            Object[] mesgParam = null;
            if (params.length != 0) {
                final int paramsLength = params.length;
                mesgParam = new Object[paramsLength + 2];
                System.arraycopy(params, 0, mesgParam, 0, paramsLength);
                mesgParam[paramsLength] = length;
                mesgParam[paramsLength + 1] = realLength;
            } else {
                mesgParam = new Object[2];
                mesgParam[0] = length;
                mesgParam[1] = realLength;
            }
            throw new SpdProtocolException(mesgCode, mesgParam);
        }
    }

    /**
     * Connect to the MailboxServer using a SSL connection.
     */
    @Override
    public final void connect() {
        LOGGER.debug("#### Connect");
        mailboxServerConnection.connect();
    }

    /**
     * Logon into the MailboxServer.<br>
     * The following steps are taken:
     * <UL>
     * <LI>a LogonRequest is created
     * <LI>the LogonReqest is send to the MBS
     * <LI>a LogonConfirmation is received
     * <LI>the LogonConfirmation is interpreted
     * </UL>
     * 
     * @param mailbox
     *            the Mailbox where we logon to
     * @throws SpdProtocolException
     *             Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven
     *             lengte of onverwachte operationcode
     * @throws MailboxServerPasswordException
     *             wordt gegooid als het wachtwoord niet in orde is.
     * @see LO3.3 Bijlage IV.3.2.2
     * @see LO3.3 Bijlage IV.4.1.1
     * @see LO3.3 Bijlage IV.5.2.1
     */
    @Override
    public final void logOn(final Mailbox mailbox) throws SpdProtocolException, MailboxServerPasswordException {
        LOGGER.debug("#### Logon");
        final Message request = createLogonRequest(mailbox);
        sendSpdMessage(request);

        /****************************************
         * - LogonConfirmation: Length [5] Operationcode [3] LogonResult [4] MessageEntryDTS [11] SystemManagerMessage
         * [60] TotalLength: 78 (exclusive lengthField)
         ****************************************/
        final Message message = receiveSpdMessage();
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        try {
            if (nrBytesRead < 0) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_RCV_CONFIRMATION);
            }

            checkMesgLength(buf, nrBytesRead, MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_LENGTH);

            final int operationCode =
                    Integer.parseInt(new String(buf, SpdConstants.OPCODE_START, SpdConstants.OPCODE_LENGTH));

            if (operationCode != SpdConstants.OPC_LOGON_CONFIRMATION) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_OPCODE,
                        new Object[] { operationCode });
            }

            final int logonResult =
                    Integer.parseInt(new String(buf, SpdConstants.LOGON_RESULT_START, SpdConstants.RESULT_LENGTH));
            switch (logonResult) {
                case 0:
                    break;
                case SpdConstants.LOGON_IDENTIFICATIE_ONGELDIG:
                    throw new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_INCORRECT_PASSWORD);
                default:
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_ERROR,
                            new Object[] { logonResult });
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_ERROR, null, nfe);
        }
    }

    /**
     * This method sends a LO3Bericht to the MailboxServer. Of verder opgesplits, zodat er ook een receive
     * PutMessageResult bij komt?
     * 
     * @param bericht
     *            wordt in deze methode ge-updated afhankelijk van de uitkomst van de putMessage operatie.
     * @throws SpdProtocolException
     *             Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven
     *             lengte of onverwachte operationcode
     * @see LO3.3 Bijlage IV.3.2.3
     * @see LO3.3 Bijlage IV.4.2.1
     * @see LO3.3 Bijlage IV.5.2.2
     */
    @Override
    public final void putMessage(final Bericht bericht) throws SpdProtocolException {
        LOGGER.debug("#### vestuurBericht");

        final Message putMessage = createPutMessage(bericht);
        sendSpdMessage(putMessage);

        /****************************************
         * - PutMessageConfirmation: Length [5] Operationcode [3] PutResult [4] DispatchSequenceNumber [9]
         * SubmissionTime [11] MessageID [12]
         ****************************************/
        final Message message = receiveSpdMessage();
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        try {
            if (nrBytesRead < 0) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_PUTMESSAGE_CONFIRMATION);
            }

            checkMesgLength(buf, nrBytesRead, MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_LENGTH,
                    bericht.getEref(), bericht.getRecipient());

            final int operationCode =
                    Integer.parseInt(new String(buf, SpdConstants.OPCODE_START, SpdConstants.OPCODE_LENGTH));

            if (operationCode != SpdConstants.OPC_PUT_MESSAGE_CONFIRMATION) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_OPCODE, new Object[] {
                        operationCode, bericht.getEref(), bericht.getRecipient(), });
            }

            final int putMessageResult =
                    Integer.parseInt(new String(buf, SpdConstants.PUTMESSAGE_RESULT_START, SpdConstants.RESULT_LENGTH));

            if (putMessageResult != RESULT_CORRECT) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_ERROR, new Object[] {
                        putMessageResult, bericht.getEref(), bericht.getRecipient(), });
            } else {
                final int dispatchSeqNr =
                        Integer.parseInt(new String(buf, SpdConstants.PUTMESSAGE_DISPATCH_SEQNR_START,
                                SpdConstants.PUTMESSAGE_DISPATCH_SEQNR_LEN));
                LOGGER.debug("PutMessageConfirmation.DispatchSeqNr= " + dispatchSeqNr);
                bericht.setDispatchSequenceNumber(dispatchSeqNr);

                final String submissionTime =
                        new String(buf, SpdConstants.PUTMESSAGE_SUBMISSION_TIME_START,
                                SpdConstants.PUTMESSAGE_SUBMISSION_TIME_LEN);
                LOGGER.debug("PutMessageConfirmation.SubmissionTime= " + submissionTime);
                bericht.setTijdstipVerzendingOntvangst(VoaUtil.convertSpdTimeStringToDate(submissionTime));

                // trim() because this field can be padded with spaces.
                final String messageId =
                        new String(buf, SpdConstants.PUTMESSAGE_MESSAGE_ID_START,
                                SpdConstants.PUTMESSAGE_MESSAGE_ID_LEN).trim();
                if (!messageId.equals(bericht.getEref())) {
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_INCORRECT_MSG_ID,
                            new Object[] { bericht.getEref(), messageId });
                }
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_NFE, null, nfe);
        }
    }

    /**
     * This method will receive a GBA-bericht from the Mailbox Server.<br>
     * To interpret the content of the GetMessageResult 3 steps have to be taken:
     * <OL>
     * <LI>Determine if the result is a GetMessageResult (210) or a GetMessageConfirmation (290)
     * <LI>Determine if the GetMessageResult contains a Result (220) or a Delivery Report (260)
     * <LI>Determine if the Result (220) contains a normal GBA-bericht (250+280) or a Status Report (270)
     * </OL>
     * See figure below for a diagram with operation codes.
     * 
     * <pre>
     *   Step 1    | Step 2    | Step 3    | Step 4    | Description
     *   ------------------------------------------------------------------
     *   210 --+-----220----+----250---------280-------| Normal GBA-bericht
     *         |            |                          |
     *         |            +----270-------------------| Status Report
     *         |                                       |               
     *         +-----260-------------------------------| Delivery Report
     *                                                 |
     *   290 ------------------------------------------| GetMessageConfirmation
     * </pre>
     * 
     * @param msSequenceNumber
     *            is a sequenceNumber from the ListMessagesResult
     * @return LO3Bericht with filled fields from the sPd-GBA-message
     * @throws SpdProtocolException
     *             wordt gegooid als er een fout optreedt bij het onvangen van een bericht
     * @see LO3.3 Bijlage IV.3.2.4
     * @see LO3.3 Bijlage IV.4.3.3
     * @see LO3.3 Bijlage IV.5.2.3
     */
    @Override
    public final Bericht getMessage(final int msSequenceNumber) throws SpdProtocolException {
        LOGGER.debug("#### ontvangBericht");

        // send GetMessage
        final Message getMessage = createGetMessage(msSequenceNumber);
        sendSpdMessage(getMessage);

        // receive GetMessageResult
        final Message message = receiveSpdMessage();
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        LOGGER.debug("nrBytesRead= " + nrBytesRead);
        final Bericht result = new Bericht();
        if (nrBytesRead < 0) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE);
        }
        // create a new String using a strict bitwise copying of the buffer's content
        final String getMessageResult = GBACharacterSet.convertTeletexByteArrayToString(buf);

        final GetMessageRecord gmr = new GetMessageRecord(getMessageResult);
        gmr.determineMessageType();

        // fill LO3Bericht
        result.setAanduidingInUit(Bericht.AANDUIDING_IN_UIT_IN);
        // CHECKSTYLE:OFF Er is geen default. Als de verkeerde opcode wordt meegegeven, dan resulteerd dit in een
        // SpdException bij gmr.determineMessageType
        switch (gmr.getMessageType()) {
            case GetMessageRecord.MT_MESSAGE_CONFIRMATION:
                final int error = handleMessageConfirmation(gmr.getMessageConfirmation());
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_ERROR,
                        new Object[] { error, });
            case GetMessageRecord.MT_DELIVERY_REPORT:
                handleMSEntry(gmr.getMsEntry(), result);
                handleDeliveryReport(gmr.getDeliveryReport(), result);
                break;
            case GetMessageRecord.MT_STATUS_REPORT:
                handleMSEntry(gmr.getMsEntry(), result);
                handleGetEnvelope(gmr.getGetEnvelope(), result);
                handleStatusReport(gmr.getStatusReport(), result);
                break;
            case GetMessageRecord.MT_GBA_MESSAGE:
                handleMSEntry(gmr.getMsEntry(), result);
                handleGetEnvelope(gmr.getGetEnvelope(), result);
                handleMessageHeading(gmr.getMessageHeading(), result);
                handleMessageBody(gmr.getMessageBody(), result);
                break;
        }
        // CHECKSTYLE:ON
        return result;
    }

    /**
     * This method tries to interpret the DeliveryReport from the MailboxServer
     * 
     * <pre>
     * <code>
     * <b>DeliveryReport:</b>
     * - Length                     [5]
     * - Operationcode              [3]
     * - ReportDeliveryTime         [11]
     * - DispatchSequenceNumber     [9]
     * - NumberofRecipients         [3] --> always 1 in the GBA-V system
     * {
     *   - ReportedRecipeintORName  [7]
     *   - MessageDeliveryTime      [11]
     *   - NonDeliveryReason        [4]
     * }
     * </code>
     * </pre>
     * 
     * @param deliveryReport
     *            String with the DeliveryReport (exlusive MSEntry) received from the MailboxServer
     * @param delRep
     *            LO3Bericht object filled with fields from the method receiveBericht()
     * @throws SpdProtocolException
     */
    private void handleDeliveryReport(final String deliveryReport, final Bericht delRep) throws SpdProtocolException {
        // set field lo3_bericht.kop_berichtsoort_nummer
        try {
            final int length =
                    Integer.parseInt(deliveryReport.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_START
                            + SpdConstants.LENGTH_LENGTH));
            final int realLength = deliveryReport.length() - SpdConstants.LENGTH_LENGTH;
            if (length != realLength) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_DELIVERY_REPORT_LENGTH, new Object[] {
                        length, realLength, }, delRep);
            }

            // ReportDeliveryTime [11]
            final String deliveryTime =
                    deliveryReport.substring(SpdConstants.DELIVERY_REP_DELIVERY_TIME_START,
                            SpdConstants.DELIVERY_REP_DELIVERY_TIME_START
                                    + SpdConstants.DELIVERY_REP_DELIVERY_TIME_LEN);
            delRep.setReportDeliveryTime(VoaUtil.convertSpdTimeStringToDate(deliveryTime));

            // DispatchSequenceNumber [9]
            final int dispatchSeqNr =
                    Integer.parseInt(deliveryReport.substring(SpdConstants.DELIVERY_REP_DISPATCH_SEQNR_START,
                            SpdConstants.DELIVERY_REP_DISPATCH_SEQNR_START
                                    + SpdConstants.DELIVERY_REP_DISPATCH_SEQNR_LEN));
            delRep.setDispatchSequenceNumber(dispatchSeqNr);

            // NumberofRecipients [3] --> should be 1
            final int nrOfRecipients =
                    Integer.parseInt(deliveryReport.substring(SpdConstants.DELIVERY_REP_NR_OR_RECEPIENTS_START,
                            SpdConstants.DELIVERY_REP_NR_OR_RECEPIENTS_START
                                    + SpdConstants.DELIVERY_REP_NR_OR_RECEPIENTS_LEN));
            if (nrOfRecipients != 1) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_DELREP_NR_RECIPIENTS, delRep);
            }

            // ReportedRecipientORName [7]
            final String recipient =
                    deliveryReport.substring(SpdConstants.DELIVERY_REP_RECIPIENT_ORNAME_START,
                            SpdConstants.DELIVERY_REP_RECIPIENT_ORNAME_START
                                    + SpdConstants.DELIVERY_REP_RECIPIENT_ORNAME_LEN);
            delRep.setRecipient(recipient);

            // MessageDeliveryTime or MailboxBlockDTS [11] // is not used
            // NonDeliveryReason [4]
            final String nonDeliveryReason =
                    deliveryReport.substring(SpdConstants.DELIVERY_REP_NON_DEL_REASON_START,
                            SpdConstants.DELIVERY_REP_NON_DEL_REASON_START
                                    + SpdConstants.DELIVERY_REP_NON_DEL_REASON_LEN);
            delRep.setNonDeliveryReason(nonDeliveryReason);
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, nfe, delRep);
        }
    }

    /**
     * This method tries to interpret the StatusReport from the MailboxServer
     * 
     * <pre>
     * <code>
     * <b>StatusReport:</b> 
     * - Length                     [5]
     * - Operationcode              [3]
     * - ActualRecipientORName      [7]  -> is not used
     * - NotificationType           [1]  -> is not used
     * - MessageID                  [12]
     * - NonReceiptReason           [1]
     * </code>
     * </pre>
     * 
     * @param statusReport
     *            (exclusive MSSentry and GetEnvelope) received from the MailboxServer
     * @param statRep
     *            LO3Bericht object filled with fields from the GetEnvelope
     * @throws SpdProtocolException
     */
    private void handleStatusReport(final String statusReport, final Bericht statRep) throws SpdProtocolException {
        try {
            // length
            final int length =
                    Integer.parseInt(statusReport.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_START
                            + SpdConstants.LENGTH_LENGTH));
            final int realLength = statusReport.length() - SpdConstants.LENGTH_LENGTH;
            if (length != realLength) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_STATUS_REPORT_LENGTH, new Object[] {
                        length, realLength, }, statRep);
            }

            // MessageID
            final String messageId =
                    statusReport.substring(SpdConstants.STATUS_REPORT_MESSAGE_ID_START,
                            SpdConstants.STATUS_REPORT_MESSAGE_ID_START + SpdConstants.STATUS_REPORT_MESSAGE_ID_LEN);
            statRep.setEref2(messageId);

            // NonReceiptReason [1]
            final String nonReceiptReason =
                    statusReport.substring(SpdConstants.STATUS_REPORT_NON_REC_REASON_START,
                            SpdConstants.STATUS_REPORT_NON_REC_REASON_START
                                    + SpdConstants.STATUS_REPORT_NON_REC_REASON_LEN);
            statRep.setNonReceiptReason(nonReceiptReason);
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, nfe, statRep);
        }
    }

    /**
     * This method tries to interpret the MessageHeading of the message from the MailboxServer.
     * 
     * <pre>
     * <code>
     * <b>MessageHeading:</b> 
     * - Length                    [5]
     * - Operationcode             [3]
     * - MessageID                 [12]
     * - CrossReference            [12]
     * - OriginatorUsername        [7] -> not used
     * - ActualRecipientORName     [7] -> not used
     * - ActualNotificationRequest [1] -> not used
     * </code>
     * </pre>
     * 
     * @param heading
     *            String with the heading received from the MailboxServer
     * @param bericht
     *            object which is already filled with some fields. New fields will be filled in this method
     * @throws SpdProtocolException
     */
    private void handleMessageHeading(final String heading, final Bericht bericht) throws SpdProtocolException {
        try {
            final int length =
                    Integer.parseInt(heading.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_START
                            + SpdConstants.LENGTH_LENGTH));
            final int realLength = heading.length() - SpdConstants.LENGTH_LENGTH;
            if (length != realLength) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_HEADING_LENGTH, new Object[] { length,
                        realLength, }, bericht);
            }

            // MessageID [12]
            final String messageId =
                    heading.substring(SpdConstants.MH_MESSAGE_ID_START, SpdConstants.MH_MESSAGE_ID_START
                            + SpdConstants.MH_MESSAGE_ID_LEN);
            bericht.setBref(messageId);

            // CrossReference [12]
            String crossReference =
                    heading.substring(SpdConstants.MH_CROSS_REFERENCE_START, SpdConstants.MH_CROSS_REFERENCE_START
                            + SpdConstants.MH_CROSS_REFERENCE_LEN);
            // Test the CrossReference if it's empty (See LO3 IV.4.2.1)
            // An empty CrossReference field contains one of the following values:
            // - 12 spaces -> is already correct and have not to be changed
            // - 12 zero's
            // - 1 zero and 11 spaces
            // - 11 spaces and 1 zero
            if ("            ".equals(crossReference) || "000000000000".equals(crossReference)
                    || "0           ".equals(crossReference) || "           0".equals(crossReference)) {
                crossReference = null;
            }
            bericht.setEref2(crossReference);

            /****************************************
             * MessageBody structure (operationcode = 280): Length [5] Operationcode [3] BodyString [max: 19000]
             ****************************************/

        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, nfe, bericht);
        }
    }

    /**
     * This method tries to interpret the GetEnvelope from the MailboxServer
     * 
     * <pre>
     * <code>
     * <b>GetEnvelope:</b>
     * - Length                     [5]
     * - Operationcode              [3]
     * - OriginatorORName           [7]
     * - ContentType                [1] -> not used
     * - Priority                   [1] -> not used
     * - DeliveryTime               [11]
     * - SubmissionTime             [11] -> not used
     * - ActualRecipientORName      [7] -> not used
     * </code>
     * </pre>
     * 
     * @param confirmation
     *            is the GetEnvelope received from the MailboxServer
     * @param bericht
     *            object which is already filled with some fields. New fields will be filled in this method
     * @throws SpdProtocolException
     */
    private void handleGetEnvelope(final String envelope, final Bericht bericht) throws SpdProtocolException {
        try {
            final int length =
                    Integer.parseInt(envelope.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_START
                            + SpdConstants.LENGTH_LENGTH));
            final int realLength = envelope.length() - SpdConstants.LENGTH_LENGTH;
            if (length != realLength) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_ENVELOPE_LENGTH, new Object[] { length,
                        realLength, }, bericht);
            }

            // OriginatORName (Mailboxnumber from the sender) [7]
            final String originator =
                    envelope.substring(SpdConstants.GE_ORIGINATOR_ORNAME_START,
                            SpdConstants.GE_ORIGINATOR_ORNAME_START + SpdConstants.GE_ORIGINATOR_ORNAME_LEN);
            bericht.setOriginator(originator);

            // contenttype [1] is not used
            // priority [1] is not used

            // DeliveryTime [11]
            final String deliveryTime =
                    envelope.substring(SpdConstants.GE_DELIVERYTIME_START, SpdConstants.GE_DELIVERYTIME_START
                            + SpdConstants.GE_DELIVERYTIME_LEN);
            bericht.setTijdstipVerzendingOntvangst(VoaUtil.convertSpdTimeStringToDate(deliveryTime));

            // SubmissionTime [11]
            // ActualRecipientORName [7] is not used
            final String recipient =
                    envelope.substring(SpdConstants.GE_RECIPIENT_ORNAME_START, SpdConstants.GE_RECIPIENT_ORNAME_START
                            + SpdConstants.GE_RECIPIENT_ORNAME_LEN);
            bericht.setRecipient(recipient);
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, nfe, bericht);
        }
    }

    /**
     * This method tries to interpret the MessageHeading of the message from the MailboxServer.
     * 
     * <pre>
     * <code>
     * <b>MSEntry:</b> 
     * - Length                    [5]
     * - Operationcode             [3]
     * - MSSequenceNumber          [9]
     * </code>
     * </pre>
     * 
     * @param entry
     *            String with the MSEntry received from the MailboxServer
     * @param bericht
     *            object which is already filled with some fields. New fields will be filled in this method
     * @throws SpdProtocolException
     */
    private void handleMSEntry(final String entry, final Bericht bericht) throws SpdProtocolException {
        try {
            // get MSSequenceNumber
            final int msSequenceNumber =
                    Integer.parseInt(entry.substring(SpdConstants.MSE_MSSEQUENCE_NUMBER_START,
                            SpdConstants.MSE_MSSEQUENCE_NUMBER_START + SpdConstants.MSE_MSSEQUENCE_NUMBER_LEN));

            bericht.setDispatchSequenceNumber(msSequenceNumber);
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD,
                    new Object[] { "MSEntry.MSSequenceNumber" }, nfe, bericht);
        }
    }

    /**
     * This method tries to interpret the GetMessageConfirmation from the MailboxServer
     * 
     * <pre>
     * <code>
     * <b>GetMessageConfirmation:</b>
     * - Length                     [5]
     * - Operationcode              [3]
     * - MessageID                  [12]
     * - CrossReference             [12]
     * - OriginatorUsername         [7] -> not used
     * - ActualRecipientORName      [7] -> not used
     * - ActualNotificationRequest  [1] -> not used
     * </code>
     * </pre>
     * 
     * @param confirmation
     *            is the GetMessageConfirmation received from the MailboxServer
     */
    private int handleMessageConfirmation(final String confirmation) throws SpdProtocolException {
        // check the length field
        final int length =
                Integer.parseInt(confirmation.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_LENGTH));
        final int realLength = confirmation.length() - SpdConstants.LENGTH_LENGTH;
        if (length != realLength) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_CONF_LENGTH, new Object[] {
                    length, realLength, });
        }

        final int error =
                Integer.parseInt(confirmation.substring(SpdConstants.GETMESSAGE_CONFIRMATION_RESULT_START,
                        SpdConstants.GETMESSAGE_CONFIRMATION_RESULT_START + SpdConstants.RESULT_LENGTH));
        return error;
    }

    /**
     * This method tries to change the password on the Mailbox Server.
     * <P>
     * NOTE: this method has a high CCN, but this method is a straightforward message-parsing method.
     * 
     * @param mailbox
     *            the mailbox of which the password has to change
     * @param newPassWord
     *            is the new MailboxServer password
     * @throws SpdProtocolException
     *             Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven
     *             lengte of onverwachte operationcode
     * @throws MailboxServerPasswordException
     *             wordt gegooid als het nieuwe wachtwoord niet voldoet of als de mailboxserver 1 van de 2 wachtwoorden
     *             niet goedkeurd
     * @see LO3.3 Bijlage IV.3.2.6
     * @see LO3.3 Bijlage IV.5.2.7
     */
    @Override
    public final void changePassword(final Mailbox mailbox, final String newPassWord) throws SpdProtocolException,
            MailboxServerPasswordException {
        LOGGER.debug("#### changePassWord");

        validateNewPassword(newPassWord);

        final Message changePasswdRequest = createChangePasswordRequest(mailbox.getMailboxpwd(), newPassWord);
        sendSpdMessage(changePasswdRequest);

        final Message message = receiveSpdMessage();

        try {
            final int changePassWordResult = validateChangePasswordConfirmation(message);
            checkPasswordResult(changePassWordResult);
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR, null, nfe);
        }
    }

    /**
     * Validate the sPd response message as a valid ChangePasswordConfirmation.
     * 
     * @param message
     * @throws SpdProtocolException
     * @throws NumberFormatException
     */
    private int validateChangePasswordConfirmation(final Message message) throws SpdProtocolException {
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        if (nrBytesRead <= 0) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF);
        }
        final int length = Integer.parseInt(new String(buf, SpdConstants.LENGTH_START, SpdConstants.LENGTH_LENGTH));
        if (length != nrBytesRead - SpdConstants.LENGTH_LENGTH) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_LENGTH, new Object[] {
                    length, nrBytesRead - SpdConstants.LENGTH_LENGTH, });
        }

        final int operationCode =
                Integer.parseInt(new String(buf, SpdConstants.OPCODE_START, SpdConstants.OPCODE_LENGTH));
        if (operationCode != SpdConstants.OPC_CHANGE_PASSWORD_CONFIRMATION) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_OPCODE,
                    new Object[] { operationCode });
        }

        return Integer.parseInt(new String(buf, SpdConstants.CPWD_RESULT_START, SpdConstants.RESULT_LENGTH));
    }

    /**
     * Validate the new password.
     * 
     * @param newPassWord
     * @throws MailboxServerPasswordException
     */
    private void validateNewPassword(final String newPassWord) throws MailboxServerPasswordException {
        final int pwdMinLength = 6;
        final int pwdMaxLength = 8;
        if (newPassWord.length() > pwdMaxLength || newPassWord.length() < pwdMinLength) {
            throw new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_NEW_PASSWORD_LENGTH,
                    new Object[] { newPassWord.length() });
        }
    }

    /**
     * Checks the result of the 'change password' request.
     * 
     * @param changePassWordResult
     * @throws MailboxServerPasswordException
     */
    private void checkPasswordResult(final int changePassWordResult) throws MailboxServerPasswordException {
        switch (changePassWordResult) {
            case 0:
                LOGGER.debug("Wijzigen mailbox wachtwoord is succesvol verlopen");
                break;
            case SpdConstants.ERR_CODE_CHANGEPASSWORD_OLD_PWD_MISSING:
                throw new MailboxServerPasswordException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_MISSING);
            case SpdConstants.ERR_CODE_CHANGEPASSWORD_OLD_PWD_INVALID:
                throw new MailboxServerPasswordException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_INVALID);
            case SpdConstants.ERR_CODE_CHANGEPASSWORD_NEW_PWD_MISSING:
                throw new MailboxServerPasswordException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_MISSING);
            case SpdConstants.ERR_CODE_CHANGEPASSWORD_NEW_PWD_UNACCEPTABLE:
                throw new MailboxServerPasswordException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_UNACCEPTABLE);
            default:
                throw new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR,
                        new Object[] { changePassWordResult });
        }
    }

    /**
     * This method tries to interpret the MessageBody from the MailboxServer
     * 
     * <pre>
     * <code>
     * <b>MessageBody:</b>
     * - Length                     [5]
     * - Operationcode              [3]
     * - BodyString                 [max: 19000]
     * </code>
     * </pre>
     * 
     * @param confirmation
     *            is the GetEnvelope received from the MailboxServer
     * @param bericht
     *            object which is already filled with some fields. New fields will be filled in this method
     */
    private void handleMessageBody(final String body, final Bericht bericht) throws SpdProtocolException {
        try {
            final int length =
                    Integer.parseInt(body.substring(SpdConstants.LENGTH_START, SpdConstants.LENGTH_START
                            + SpdConstants.LENGTH_LENGTH));
            final int realLength = body.length() - SpdConstants.LENGTH_LENGTH;
            if (length != realLength) {
                // set the berichtInhoud with the 'incorrect' bodycontent
                bericht.setBerichtInhoud(body);

                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_BODY_LENGTH, new Object[] { length,
                        realLength, }, bericht);
            }

            // if there is a berichtBody
            // Length is de opcode en het berichtinhoud
            // Opgehaalde body is length + opcode + inhoud. Als de body langer is dan length + opcode (dus 5+3) dan
            // hebben we een inhoud
            if (body.length() > SpdConstants.MB_BODYSTRING_START) {
                // set bodyString
                final String bodyString =
                        body.substring(SpdConstants.MB_BODYSTRING_START, SpdConstants.MB_BODYSTRING_START + length
                                - SpdConstants.OPCODE_LENGTH);
                bericht.setBerichtInhoud(bodyString);

                if (hasNullBytes(bodyString)) {
                    final List<Integer> nullBytePositions = new ArrayList<Integer>();

                    // correct berichtBody and update bericht with correct content
                    final String correctedBody = correctNullBytes(bodyString, nullBytePositions);
                    bericht.setBerichtInhoud(correctedBody);

                    // throw exception with the corrected bericht object (which will be persisted)
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_CONTAINS_NULL_BYTES,
                            new Object[] { nullBytePositions }, bericht);
                }
            }

        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, nfe, bericht);
        }
    }

    /**
     * This method sends a ListMessages request to the MailboxServer and receives a ListMessagesResult or
     * ListMessageConfirmation.
     * 
     * @param nextSequenceNr
     *            the SequenceNumber of the first new message to read on the Mailbox Server.
     * @param sequenceNumbers
     *            List which will filled with all the MSSequenceNumbers of the new messages on the Mailbox Server.
     * @param listLimitNr
     *            maximaal aantal van berichten om op te halen
     * @param msStatus
     *            welke status er op gehaald moet worden
     * @param prio
     *            welke prioriteit er aan berichten moeten worden opgehaald
     * @return new nextSequenceNr. When this number is 0, then there are no more new messages on the Mailbox Server.
     *         When this number > 0 this is the new NextSequenceNr which should be used for a new call to this method.
     * @throws SpdProtocolException
     *             Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven
     *             lengte of onverwachte operationcode
     * @see LO3.3 Bijlage IV.3.2.4
     * @see LO3.3 Bijlage IV.4.3.2
     * @see LO3.3 Bijlage IV.5.2.5
     */
    @Override
    public final int listMessages(
            final int nextSequenceNr,
            final List<Integer> sequenceNumbers,
            final int listLimitNr,
            final String msStatus,
            final String prio) throws SpdProtocolException {
        LOGGER.debug("#### listMessages");

        int nextMSSequenceNr = 0;

        final Message listMessages = createListMessages(listLimitNr, msStatus, prio, nextSequenceNr);
        sendSpdMessage(listMessages);

        /****************************************
         * ListMessagesResult (410 + 411) - ListResult (410) Length [5] Operationcode [3] NextMSSequenceNumber [9] -
         * MSList (411) Length [5] Operationcode [3] NumberOfMSEntries [5] { MSSequenceNumber [9] MSStatus [1] Priority
         * [1] DeliveryTime [11] OriginatorORName [7] }
         * 
         * ListMessagesConfirmation (419) Length [5] Operationcode [3] ListError [4]
         ****************************************/

        final Message message = receiveSpdMessage();
        final int nrBytesRead = message.getLength();
        final byte[] buf = message.getMessageFromMailbox();

        try {
            if (nrBytesRead <= 0) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_LISTMESSAGES);
            }

            final int operationCode =
                    Integer.parseInt(new String(buf, SpdConstants.OPCODE_START, SpdConstants.OPCODE_LENGTH));
            switch (operationCode) {
                case SpdConstants.OPC_LIST_MESSAGES_CONFIRMATION:
                    final String listError =
                            new String(buf, SpdConstants.LIST_MESSAGES_CONFIRMATION_ERROR_START,
                                    SpdConstants.LIST_MESSAGES_CONFIRMATION_ERROR_LEN);

                    if (Integer.parseInt(listError) != SpdConstants.ERR_CODE_LIST_MESSAGES_NO_ENTRIES) {
                        throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_ERROR,
                                new Object[] { Integer.valueOf(listError) });
                    }
                    break;
                case SpdConstants.OPC_LIST_MESSAGES_RESULT:
                    final int realLength = SpdConstants.OPCODE_LENGTH + SpdConstants.LR_MSSEQUENCE_NUMBER_LEN;
                    if (nrBytesRead < realLength) {
                        throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_LENGTH);
                    }

                    nextMSSequenceNr =
                            Integer.parseInt(new String(buf, SpdConstants.LR_MSSEQUENCE_NUMBER_START,
                                    SpdConstants.LR_MSSEQUENCE_NUMBER_LEN));
                    final int numberOfEntries =
                            Integer.parseInt(new String(buf, SpdConstants.MSL_NUMBER_OF_MSENTRIES_START,
                                    SpdConstants.MSL_NUMBER_OF_MSENTRIES_LEN));

                    // loop through all the entries
                    for (int i = 0; i < numberOfEntries; i++) {
                        final int berichtSeqNr =
                                Integer.parseInt(new String(buf, SpdConstants.MSL_MSSEQUENCE_NUMBER_START
                                        + SpdConstants.MSL_ENTRY_LEN * i, SpdConstants.MSL_MSSEQUENCE_NUMBER_LEN));
                        LOGGER.debug("SequenceNr= " + berichtSeqNr);
                        sequenceNumbers.add(berichtSeqNr);
                    }
                    break;
                default:
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_CONF_OPCODE,
                            new Object[] { operationCode });
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_ERROR, null, nfe);
        }
        return nextMSSequenceNr;
    }

    /**
     * Read data from the SSL connection into the buffer.
     * 
     * @return message object met daarin het bericht van de mailbox en de lengte.
     */
    private Message receiveSpdMessage() {
        return mailboxServerConnection.read();
    }

    /**
     * IMPORTANT NOTICE!<br>
     * To prevent dependencies on the platform's default characterset encoding, we will do a bitwise copy of the Java
     * string to a byte array. This byte array is send to the SSL connection.
     * 
     * @param message
     *            het bericht in sPd-formaat dat verstuurd moet worden naar de mailbox
     */
    private void sendSpdMessage(final Message message) {
        mailboxServerConnection.write(message);
    }

    /**
     * Creates a GetMessage according to the sPd-protocol
     * <P>
     * 
     * <pre>
     * <code>
     * <b>GetMessage:</b>
     *   Length                 [5]
     *   Operationcode          [3]
     *   MSSequenceNumber       [9]
     * </code>
     * </pre>
     * 
     * @return String with the GetMessage
     */
    private Message createGetMessage(final int sequenceNumber) {
        // Length [5] + Operationcode [3] + MSSequenceNumber [9]
        final String getMessage = "00012" + "200" + DF_SEQ_NR.format(sequenceNumber);

        final Message message = new Message();
        message.setMessageToMailbox(getMessage);
        return message;
    }

    /**
     * Creates a listMessages according to the sPd-protocol
     * <P>
     * 
     * <pre>
     * <code>
     * <b>listMessages:</b>
     *   Length                 [5]
     *   Operationcode          [3]
     *   LimitNumber            [3]
     *   MSStatus               [3]
     *   Priority               [1] -> when empty: all the priorities are fetches see LO3 IV.5.3. Table IV.12
     *   FromMSSequenceNumber   [9]
     *   ToMSSequenceNumber     [9]
     * </code>
     * </pre>
     * 
     * @return String with the listMessages
     */
    private Message createListMessages(
            final int listLimit,
            final String status,
            final String priority,
            final int nextSeqNr) {
        int limitNumber = listLimit;
        final DecimalFormat dfLimitNr = new DecimalFormat("000");

        // check and format listLimit Number
        if (limitNumber < SpdConstants.MIN_NUMBER_LIST_MESSAGES) {
            limitNumber = SpdConstants.MIN_NUMBER_LIST_MESSAGES;
        } else if (limitNumber > SpdConstants.MAX_NUMBER_LIST_MESSAGES) {
            limitNumber = SpdConstants.MAX_NUMBER_LIST_MESSAGES;
        }

        final String listMessages =
                "00028" + SpdConstants.OPC_LIST_MESSAGES + dfLimitNr.format(limitNumber)
                        + StringUtil.fillBefore(status, ' ', SpdConstants.LR_STATUS_LENGTH)
                        + StringUtil.fillBefore(priority, ' ', SpdConstants.LR_PRIORITY_LENGTH)
                        + DF_SEQ_NR.format(nextSeqNr) + "         ";
        final Message message = new Message();
        message.setMessageToMailbox(listMessages);
        return message;
    }

    /**
     * Creates a ChangePasswordRequest with given paramaters according to the sPd-protocol
     * 
     * <pre>
     * <code>
     * <b>ChangePasswordRequest:</b>
     * Length           [5]
     * Operationcode    [3]
     * OldPassword      [8]
     * NewPassword      [8]
     * </code>
     * </pre>
     * 
     * @return String with the ChangePasswordRequest
     */
    private Message createChangePasswordRequest(final String oldPasswd, final String newPasswd) {
        final String changePasswordRequest =
                "00019" + "910" + StringUtil.fillBefore(oldPasswd, ' ', SpdConstants.CPWD_MAX_PWD_LENGTH)
                        + StringUtil.fillBefore(newPasswd, ' ', SpdConstants.CPWD_MAX_PWD_LENGTH);
        final Message message = new Message();
        message.setMessageToMailbox(changePasswordRequest);
        return message;
    }

    /**
     * Create a sPd-LogoffRequest.
     * <P>
     * 
     * <pre>
     * <code>
     * <b>LogoffRequest:</b>
     * Length           [5]
     * Operationcode    [3]
     * </code>
     * </pre>
     * 
     * @return LogoffRequest
     */
    private Message createLogoffRequest() {
        // Length [5] Operationcode [3]
        final String logoffRequest = "00003" + SpdConstants.OPC_LOGOFF_REQUEST;
        final Message message = new Message();
        message.setMessageToMailbox(logoffRequest);
        return message;
    }

    /**
     * Creates a LogonRequest with given paramaters according to the sPd-protocol
     * 
     * <pre>
     * <code>
     * <b>LogonRequest:</b>
     * Length           [5]
     * Operationcode    [3]
     * UserName         [7]
     * </code>
     * </pre>
     * 
     * @param username
     *            in fact the MailboxNumber where the program should logon to. This number should have a length of 7.
     * @param password
     *            the MailboxServer password belonging to the MailboxNumber
     * @return String with the LogonRequest
     */
    private Message createLogonRequest(final Mailbox mailbox) {
        // because of variable passwordlength the total length of logonRequest
        // should be determined
        final String logonRequestWithoutLength = "900" + mailbox.getMailboxnr() + mailbox.getMailboxpwd();

        // Compose total logonRequest inclusive Lenght en Terminator fields
        final String logonRequest = DF_LENGTH.format(logonRequestWithoutLength.length()) + logonRequestWithoutLength;

        final Message message = new Message();
        message.setMessageToMailbox(logonRequest);
        return message;
    }

    /**
     * Creates a PutMessage according to the sPd-protocol NOTE: the berichtContent should not be converted to TeleTex.
     * Because the berichtContent is already stored as TeleTex in the database.
     * 
     * <pre>
     * <code>
     *  &lt;b&gt;LogonRequest:&lt;/b&gt;
     * PutEnvelope:
     *  Length                      [5]
     *      Operationcode           [3]
     *      OriginatorORName        [7]
     *      ContentType             [1]
     *      Priority                [1]
     *      DeferredDeliveryTime    [11]
     *      Attention               [1]
     *  MessageHeading
     *      Length                  [5]
     *      Operationcode           [3]
     *      MessageID               [12]
     *      CrossReference          [12]
     *      OriginatorORName        [7]
     *      NumberOfRecipients      [3] -&gt; always 1
     *      RecipientORName         [7]
     *      NotificationRequest     [1]
     *  MessageBody
     *      Length                  [5]
     *      Operationcode           [3]
     *      BodyString              [max 19000]
     * </code>
     * </pre>
     * 
     * @param bericht
     *            het bericht wat naar de mailbox moet worden gestuurd.
     * @return String with the PutMessage
     */
    private Message createPutMessage(final Bericht bericht) throws SpdProtocolException {
        String mailboxNr = "";
        String berichtInhoud = "";
        int berichtLength = 0;
        final String emtpyOriginatorORName = "       ";

        if (bericht == null) {
            throw new IllegalArgumentException("Geen bericht om PutMessage voor te maken");
        }

        // format en filling variables
        mailboxNr = bericht.getRecipient();
        if (mailboxNr == null || mailboxNr.length() != SpdConstants.GE_RECIPIENT_ORNAME_LEN) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SEND_RECIPIENT_INVALID,
                    new Object[] { new Long(bericht.getId()) });
        }

        // test fields
        if (bericht.getEref() == null || bericht.getEref().isEmpty()) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SEND_NO_EREF, new Object[] { new Long(
                    bericht.getId()), });
        }

        berichtInhoud = bericht.getBerichtInhoud();
        berichtLength = berichtInhoud.length() + SpdConstants.OPCODE_LENGTH;
        final DecimalFormat dfMesgId = new DecimalFormat("0000000000");
        final String eref = erefPrefix + dfMesgId.format(bericht.getId());
        bericht.setEref(eref);

        final String messageEnvelope =
                "00024" + "120" + emtpyOriginatorORName + "2" + SpdConstants.PUTMESSAGE_PRIORITY_DEFAULT
                        + "           " + "1";

        final String messageHeading =
                "00045" + "150" + StringUtil.fillBefore(bericht.getEref(), ' ', SpdConstants.MH_MESSAGE_ID_LEN)
                        + StringUtil.fillBefore(bericht.getBref(), ' ', SpdConstants.MH_CROSS_REFERENCE_LEN)
                        + emtpyOriginatorORName + "001" + mailboxNr
                        + SpdConstants.PUTMESSAGE_NON_RECIPIENT_NOTIFICATION_REQUEST;

        final String messageBody = DF_LENGTH.format(berichtLength) + "180" + berichtInhoud;

        final String completeBericht = messageEnvelope + messageHeading + messageBody;

        LOGGER.debug("PutMessage= " + completeBericht);

        final Message message = new Message();
        message.setMessageToMailbox(completeBericht);
        return message;
    }

    /**
     * Checks if the given string contains null-byte.
     * 
     * @param s
     *            to check on null-bytes
     * @return
     */
    private boolean hasNullBytes(final String s) {
        boolean containNullByte = false;
        if (s.indexOf('\u0000') > 0) {
            containNullByte = true;
        }
        return containNullByte;
    }

    /**
     * Converts a string which contains null-bytes into a string without null-bytes. The null-byte characters are
     * replaced with the '?' character.
     * 
     * @param s
     *            string which contains the null-byte characters
     * @param nullBytePositions
     *            optional List which is filled with the positions of the null-byte characters.
     * @return converted string
     */
    private String correctNullBytes(final String s, final List<Integer> nullBytePositions) {
        int fromIndex = 0;
        int nullBytePos = 1;
        final StringBuffer bodyBuffer = new StringBuffer(s);
        while (nullBytePos > 0) {
            nullBytePos = s.indexOf('\u0000', fromIndex);
            if (nullBytePos > 0) {
                fromIndex = nullBytePos + 1;
                bodyBuffer.replace(nullBytePos, fromIndex, "?");
                // fill List
                nullBytePositions.add(nullBytePos);
            }
        }
        return bodyBuffer.toString();
    }

    public final void setErefPrefix(final String prefix) {
        erefPrefix = prefix;
    }
}
