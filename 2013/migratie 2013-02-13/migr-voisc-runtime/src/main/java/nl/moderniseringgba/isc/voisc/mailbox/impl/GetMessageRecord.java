/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.exceptions.SpdProtocolException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is a representation of a GetMessage from the MailboxServer.
 * 
 */
public class GetMessageRecord {

    /**
     * 
     */
    public static final int MT_GBA_MESSAGE = 1;
    /**
     * 
     */
    public static final int MT_STATUS_REPORT = 2;
    /**
     * 
     */
    public static final int MT_DELIVERY_REPORT = 3;
    /**
     * 
     */
    public static final int MT_MESSAGE_CONFIRMATION = 4;

    private static final Log LOGGER = LogFactory.getLog(GetMessageRecord.class);

    private String msEntry;
    private String messageConfirmation;
    private String getEnvelope;
    private String messageHeading;
    private String messageBody;
    private String statusReport;
    private String deliveryReport;
    private int messageType;
    private final String message;

    /**
     * Constructor.
     * 
     * @param message
     *            String with data received from the MailboxServer
     */
    GetMessageRecord(final String message) {
        this.message = message;
    }

    /**
     * Determine message type AND parse the message into different datablocks.
     * 
     * @throws SpdProtocolException
     *             wordt gegooid als er niet wordt voldaan aan het sPD-protocol
     * 
     */
    public final void determineMessageType() throws SpdProtocolException {
        int operationCode = 0;
        try {
            operationCode =
                    Integer.parseInt(message.substring(SpdConstants.OPCODE_START, SpdConstants.OPCODE_START
                            + SpdConstants.OPCODE_LENGTH));
            switch (operationCode) {
                case SpdConstants.OPC_MSENTRY:
                    handleMSEntry(message);
                    break;
                case SpdConstants.OPC_MESSAGE_CONFIRMATION:
                    handleMsgConfirmation(message);
                    break;
                default:
                    // this is an unknown message result (no MSEntry (210) or MessageConfirmation (290))
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_MSENTRY_OPCODE,
                            new Object[] { operationCode });
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, nfe);
        }
    }

    private void handleMSEntry(final String message) throws SpdProtocolException {
        // this is GetMessageResult
        msEntry = message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MSENTRY);
        LOGGER.debug("MSEntry= " + msEntry);

        final String mesg = message.substring(SpdConstants.TOT_LENGTH_MSENTRY);
        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode =
                Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));
        switch (operationCode) {
            case SpdConstants.OPC_GET_ENVELOP:
                handleEnvelope(mesg);
                break;
            case SpdConstants.OPC_DELIVERY_REPORT:
                handleDeliveryReport(mesg);
                break;
            default:
                // this is an unknown message result
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_ILLEGAL_MESSAGE_TYPE);
        }
    }

    private void handleEnvelope(final String message) throws SpdProtocolException {
        getEnvelope = message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_GET_ENVELOP);
        LOGGER.debug("GetEnvelope= " + getEnvelope);

        final String mesg = message.substring(SpdConstants.TOT_LENGTH_GET_ENVELOP);
        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode =
                Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));
        switch (operationCode) {
            case SpdConstants.OPC_MESSAGE_HEADING:
                handleMessageHeading(mesg);
                break;
            case SpdConstants.OPC_STATUS_REPORT:
                handleStatusReport(mesg);
                break;
            default:
                // this is an unknown message result
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_ILLEGAL_MESSAGE_TYPE);
        }
    }

    // this is a Normal GBA-bericht or a Status Report
    private void handleMessageHeading(final String message) throws SpdProtocolException {
        // this is a Normal GBA-bericht
        messageHeading = message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MESSAGE_HEADING);
        LOGGER.debug("MessageHeading= " + messageHeading);

        final String mesg = message.substring(SpdConstants.TOT_LENGTH_MESSAGE_HEADING).trim();

        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode =
                Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));

        if (operationCode != SpdConstants.OPC_MESSAGE_BODY) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_ILLEGAL_MESSAGE_TYPE);
        }

        messageBody = mesg.substring(SpdConstants.LENGTH_START, mesg.length() - SpdConstants.LENGTH_LENGTH);
        LOGGER.debug("MessageBody= " + messageBody);

        messageType = MT_GBA_MESSAGE;

    }

    private void handleStatusReport(final String message) {
        // this is a Status Report
        statusReport = message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_STATUS_REPORT);
        LOGGER.debug("StatusReport= " + statusReport);

        messageType = MT_STATUS_REPORT;
    }

    private void handleDeliveryReport(final String message) {
        // this is a Delivery Report
        deliveryReport = message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_DEL_REPORT);
        LOGGER.debug("DeliveryReport= " + deliveryReport);

        messageType = MT_DELIVERY_REPORT;
    }

    private void handleMsgConfirmation(final String message) {
        // this is a GetMessageConfirmation
        messageConfirmation =
                message.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MESSAGE_CONFIRMATION);
        LOGGER.debug("MessageConfirmation= " + messageConfirmation);

        messageType = MT_MESSAGE_CONFIRMATION;
    }

    public final String getDeliveryReport() {
        return deliveryReport;
    }

    public final String getGetEnvelope() {
        return getEnvelope;
    }

    public final String getMessageBody() {
        return messageBody;
    }

    public final String getMessageConfirmation() {
        return messageConfirmation;
    }

    public final String getMessageHeading() {
        return messageHeading;
    }

    public final String getMsEntry() {
        return msEntry;
    }

    public final String getStatusReport() {
        return statusReport;
    }

    public final int getMessageType() {
        return messageType;
    }
}
