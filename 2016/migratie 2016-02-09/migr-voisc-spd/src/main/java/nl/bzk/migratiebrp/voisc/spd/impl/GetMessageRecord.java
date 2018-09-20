/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import nl.bzk.migratiebrp.voisc.spd.constants.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
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
        int operationCode;
        try {
            operationCode =
                    Integer.parseInt(message.substring(SpdConstants.OPCODE_START, SpdConstants.OPCODE_START + SpdConstants.OPCODE_LENGTH));
            switch (operationCode) {
                case SpdConstants.OPC_MSENTRY:
                    handleMSEntry(message);
                    break;
                case SpdConstants.OPC_MESSAGE_CONFIRMATION:
                    handleMsgConfirmation(message);
                    break;
                default:
                    // this is an unknown message result (no MSEntry (210) or MessageConfirmation (290))
                    throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_MSENTRY_OPCODE, new Object[] {operationCode });
            }
        } catch (final NumberFormatException nfe) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, nfe);
        }
    }

    private void handleMSEntry(final String msg) throws SpdProtocolException {
        // this is GetMessageResult
        msEntry = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MSENTRY);
        LOGGER.debug("MSEntry= " + msEntry);

        final String mesg = msg.substring(SpdConstants.TOT_LENGTH_MSENTRY);
        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode = Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));
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

    private void handleEnvelope(final String msg) throws SpdProtocolException {
        getEnvelope = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_GET_ENVELOP);
        LOGGER.debug("GetEnvelope= " + getEnvelope);

        final String mesg = msg.substring(SpdConstants.TOT_LENGTH_GET_ENVELOP);
        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode = Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));
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
    private void handleMessageHeading(final String msg) throws SpdProtocolException {
        // this is a Normal GBA-bericht
        messageHeading = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MESSAGE_HEADING);
        LOGGER.debug("MessageHeading= " + messageHeading);

        final String mesg = msg.substring(SpdConstants.TOT_LENGTH_MESSAGE_HEADING).trim();

        final int opcodeStart = SpdConstants.LENGTH_START + SpdConstants.LENGTH_LENGTH;
        final int operationCode = Integer.parseInt(mesg.substring(opcodeStart, opcodeStart + SpdConstants.OPCODE_LENGTH));

        if (operationCode != SpdConstants.OPC_MESSAGE_BODY) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_ILLEGAL_MESSAGE_TYPE);
        }

        messageBody = mesg.substring(SpdConstants.LENGTH_START, mesg.length() - SpdConstants.LENGTH_LENGTH);
        LOGGER.debug("MessageBody= " + messageBody);

        messageType = MT_GBA_MESSAGE;

    }

    private void handleStatusReport(final String msg) {
        // this is a Status Report
        statusReport = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_STATUS_REPORT);
        LOGGER.debug("StatusReport= " + statusReport);

        messageType = MT_STATUS_REPORT;
    }

    private void handleDeliveryReport(final String msg) {
        // this is a Delivery Report
        deliveryReport = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_DEL_REPORT);
        LOGGER.debug("DeliveryReport= " + deliveryReport);

        messageType = MT_DELIVERY_REPORT;
    }

    private void handleMsgConfirmation(final String msg) {
        // this is a GetMessageConfirmation
        messageConfirmation = msg.substring(SpdConstants.LENGTH_START, SpdConstants.TOT_LENGTH_MESSAGE_CONFIRMATION);
        LOGGER.debug("MessageConfirmation= " + messageConfirmation);

        messageType = MT_MESSAGE_CONFIRMATION;
    }

    /**
     * Geef de waarde van delivery report.
     *
     * @return delivery report
     */
    public final String getDeliveryReport() {
        return deliveryReport;
    }

    /**
     * Geef de waarde van gets the envelope.
     *
     * @return gets the envelope
     */
    public final String getGetEnvelope() {
        return getEnvelope;
    }

    /**
     * Geef de waarde van message body.
     *
     * @return message body
     */
    public final String getMessageBody() {
        return messageBody;
    }

    /**
     * Geef de waarde van message confirmation.
     *
     * @return message confirmation
     */
    public final String getMessageConfirmation() {
        return messageConfirmation;
    }

    /**
     * Geef de waarde van message heading.
     *
     * @return message heading
     */
    public final String getMessageHeading() {
        return messageHeading;
    }

    /**
     * Geef de waarde van ms entry.
     *
     * @return ms entry
     */
    public final String getMsEntry() {
        return msEntry;
    }

    /**
     * Geef de waarde van status report.
     *
     * @return status report
     */
    public final String getStatusReport() {
        return statusReport;
    }

    /**
     * Geef de waarde van message type.
     *
     * @return message type
     */
    public final int getMessageType() {
        return messageType;
    }
}
