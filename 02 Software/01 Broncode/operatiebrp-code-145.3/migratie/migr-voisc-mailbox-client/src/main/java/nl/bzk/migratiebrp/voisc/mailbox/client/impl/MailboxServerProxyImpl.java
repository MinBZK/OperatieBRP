/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.ChangePasswordConfirmation;
import nl.bzk.migratiebrp.voisc.spd.ChangePasswordRequest;
import nl.bzk.migratiebrp.voisc.spd.GetMessage;
import nl.bzk.migratiebrp.voisc.spd.ListMessages;
import nl.bzk.migratiebrp.voisc.spd.ListMessagesConfirmation;
import nl.bzk.migratiebrp.voisc.spd.ListResult;
import nl.bzk.migratiebrp.voisc.spd.LogoffConfirmation;
import nl.bzk.migratiebrp.voisc.spd.LogoffRequest;
import nl.bzk.migratiebrp.voisc.spd.LogonConfirmation;
import nl.bzk.migratiebrp.voisc.spd.LogonRequest;
import nl.bzk.migratiebrp.voisc.spd.MSList;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.OperationParser;
import nl.bzk.migratiebrp.voisc.spd.OperationRecord;
import nl.bzk.migratiebrp.voisc.spd.Operations;
import nl.bzk.migratiebrp.voisc.spd.PutMessageConfirmation;
import nl.bzk.migratiebrp.voisc.spd.PutMessageOperation;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.SpdElementToStringVisitor;
import nl.bzk.migratiebrp.voisc.spd.exception.EmptyOperationException;
import nl.bzk.migratiebrp.voisc.spd.exception.IllegalLengthException;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.ParseException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.UnknownOpcodeException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.springframework.util.Assert;

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
 */
public class MailboxServerProxyImpl implements MailboxClient {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private SslConnectionFactory sslConnectionFactory;

    /**
     * Constructor.
     * @param sslConnectionFactory SSL connection factory
     */
    @Inject
    public MailboxServerProxyImpl(final SslConnectionFactory sslConnectionFactory) {
        this.sslConnectionFactory = sslConnectionFactory;
    }

    /**
     * Connect to the MailboxServer using a SSL connection.
     */
    @Override
    public final Connection connect() {
        LOGGER.debug("#### Connect");
        final Connection connection = sslConnectionFactory.getObject();
        connection.connect();
        return connection;
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
     * @param mailbox the Mailbox where we logon to
     * @throws SpdProtocolException Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven lengte of onverwachte
     * operationcode
     * @throws MailboxServerPasswordException wordt gegooid als het wachtwoord niet in orde is.
     * @see "LO3.3" Bijlage IV.3.2.2"
     * @see "LO3.3" Bijlage IV.4.1.1"
     * @see "LO3.3 Bijlage IV.5.2.1"
     */
    @Override
    public final void logOn(final Connection connection, final Mailbox mailbox) throws VoaException {

        final Operation request = new Operation.Builder().add(new LogonRequest(mailbox.getMailboxnr(), mailbox.getMailboxpwd())).build();
        sendOperation(connection, request);

        VoaException voaException = null;
        try {
            final Operation response = receiveOperation(connection);

            if (!response.containsRecordOfType(LogonConfirmation.class)) {
                voaException = new SpdProtocolException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_OPCODE,
                        new Object[]{response.records().iterator().next().operationCode()});
            } else {
                voaException = processLogonConfirmation(response);
            }
        } catch (final IllegalLengthException ex) {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_LENGTH,
                    new Object[]{ex.getActualLength(), ex.getExpectedLength()},
                    ex);
        } catch (final EmptyOperationException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_RCV_CONFIRMATION, ex);
        } catch (final UnknownOpcodeException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final ParseException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_ERROR, ex);
        }

        if (voaException != null) {
            throw voaException;
        }
    }

    private VoaException processLogonConfirmation(final Operation response) {
        VoaException voaException = null;
        final LogonConfirmation confirmation = (LogonConfirmation) response.records().iterator().next();
        if (!confirmation.isLoggedOn()) {
            if (confirmation.result() == LogonConfirmation.LogonResult.SECURITY_FAILURE) {
                voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_INCORRECT_PASSWORD);
            } else {
                voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_ERROR, new Object[]{confirmation.result().code()});
            }
        }
        return voaException;
    }

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
     * @throws SpdProtocolException Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven lengte of onverwachte
     * operationcode
     * @see "LO3.3 Bijlage IV.3.2.7"
     * @see "LO3.3 Bijlage IV.4.1.2"
     * @see "LO3.3 Bijlage IV.5.2.8"
     */
    @Override
    public final void logOff(final Connection connection) throws VoaException {

        final Operation request = new Operation.Builder().add(new LogoffRequest()).build();
        sendOperation(connection, request);

        VoaException voaException = null;
        try {
            final Operation response = receiveOperation(connection);

            if (!response.containsRecordOfType(LogoffConfirmation.class)) {
                voaException = new SpdProtocolException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_LOGON_CONF_OPCODE,
                        new Object[]{response.records().iterator().next().operationCode()});
            }
        } catch (final IllegalLengthException ex) {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_CONF_LENGTH,
                    new Object[]{ex.getActualLength(), ex.getExpectedLength()},
                    ex);
        } catch (final EmptyOperationException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_RCV_CONFIRMATION, ex);
        } catch (final UnknownOpcodeException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_CONF_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final ParseException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LOGOFF_ERROR, ex);
        }

        if (voaException != null) {
            throw voaException;
        }
    }

    /**
     * This method tries to change the password on the Mailbox Server.
     * <P>
     * NOTE: this method has a high CCN, but this method is a straightforward message-parsing method.
     * @param mailbox the mailbox of which the password has to change
     * @param newPassWord is the new MailboxServer password
     * @throws SpdProtocolException Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven lengte of onverwachte
     * operationcode
     * @throws MailboxServerPasswordException wordt gegooid als het nieuwe wachtwoord niet voldoet of als de mailboxserver 1 van de 2 wachtwoorden niet
     * goedkeurd
     * @see "LO3.3 Bijlage IV.3.2.6"
     * @see "LO3.3 Bijlage IV.5.2.7"
     */
    @Override
    public final void changePassword(final Connection connection, final Mailbox mailbox, final String newPassWord) throws VoaException {

        final Operation request = new Operation.Builder().add(new ChangePasswordRequest(mailbox.getMailboxpwd(), newPassWord)).build();
        sendOperation(connection, request);

        VoaException voaException = null;
        try {
            final Operation response = receiveOperation(connection);
            final Optional<OperationRecord> confirmationOptional = response.getRecordOfType(ChangePasswordConfirmation.class);
            if (confirmationOptional.isPresent()) {
                final ChangePasswordConfirmation confirmation = (ChangePasswordConfirmation) confirmationOptional.get();
                voaException = processChangePasswordConfirmation(confirmation);
            } else {
                voaException = new SpdProtocolException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_OPCODE,
                        new Object[]{response.records().iterator().next().operationCode()});
            }
        } catch (final IllegalArgumentException ex) {
            voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR, null, ex);
        } catch (final IllegalLengthException ex) {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_LENGTH,
                    new Object[]{ex.getActualLength(), ex.getExpectedLength()},
                    ex);
        } catch (final EmptyOperationException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF, ex);
        } catch (final UnknownOpcodeException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final ParseException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR, ex);
        }

        if (voaException != null) {
            throw voaException;
        }
    }

    private VoaException processChangePasswordConfirmation(final ChangePasswordConfirmation confirmation) {
        VoaException voaException = null;
        switch (confirmation.changePasswordResult()) {
            case OK:
                LOGGER.debug("Wijzigen mailbox wachtwoord is succesvol verlopen");
                break;
            case OLD_PASSWORD_MISSING:
                voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_MISSING);
                break;
            case OLD_PASSWORD_INVALID:
                voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_OLD_PWD_INVALID);
                break;
            case NEW_PASSWORD_MISSING:
                voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_MISSING);
                break;
            case NEW_PASSWORD_UNACCEPTABLE:
                voaException = new MailboxServerPasswordException(MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_NEW_PWD_UNACCEPTABLE);
                break;
            default:
                voaException = new MailboxServerPasswordException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_ERROR,
                        new Object[]{confirmation.changePasswordResult().code()});
        }
        return voaException;
    }

    /**
     * This method sends a LO3Bericht to the MailboxServer. Of verder opgesplits, zodat er ook een receive
     * PutMessageResult bij komt?
     * @param bericht wordt in deze methode ge-updated afhankelijk van de uitkomst van de putMessage operatie.
     * @throws SpdProtocolException Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven lengte of onverwachte
     * operationcode
     * @see "LO3.3 Bijlage IV.3.2.3"
     * @see "LO3.3 Bijlage IV.4.2.1"
     * @see "LO3.3 Bijlage IV.5.2.2"
     */
    @Override
    public final void putMessage(final Connection connection, final Bericht bericht) throws VoaException {
        Assert.notNull(bericht, "bericht mag niet null zijn.");

        final PutMessageOperation request = Operations.fromBericht(bericht);
        sendOperation(connection, request);

        VoaException voaException;
        try {
            final Operation response = receiveOperation(connection);

            if (!response.containsRecordOfType(PutMessageConfirmation.class)) {
                voaException = new SpdProtocolException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_OPCODE,
                        new Object[]{response.records().iterator().next().operationCode()});
            } else {
                voaException = processPutMessageConfirmation(bericht, response);
            }
        } catch (final IllegalLengthException ex) {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_LENGTH,
                    new Object[]{ex.getActualLength(), ex.getExpectedLength()},
                    ex);
        } catch (final EmptyOperationException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_PUTMESSAGE_CONFIRMATION, ex);
        } catch (final UnknownOpcodeException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final ParseException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_ERROR, ex);
        }

        if (voaException != null) {
            throw voaException;
        }
    }

    private VoaException processPutMessageConfirmation(final Bericht bericht, final Operation response) {
        VoaException voaException = null;
        final PutMessageConfirmation confirmation = (PutMessageConfirmation) response.records().iterator().next();
        if (confirmation.isOk()) {
            bericht.setDispatchSequenceNumber(confirmation.getDispatchSequenceNumber());
            bericht.setTijdstipMailbox(Timestamp.from(confirmation.getSubmissionTime()));
            if (!confirmation.getMessageId().equals(bericht.getMessageId())) {
                voaException = new SpdProtocolException(
                        MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_CONF_INCORRECT_MSG_ID,
                        new Object[]{bericht.getMessageId(), confirmation.getMessageId()});
            }
        } else {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_PUTMESSAGE_ERROR,
                    new Object[]{confirmation.result().code(), bericht.getMessageId(), bericht.getRecipient()});
        }
        return voaException;
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
     * @param msSequenceNumber is a sequenceNumber from the ListMessagesResult
     * @return LO3Bericht with filled fields from the sPd-GBA-message
     * @throws SpdProtocolException wordt gegooid als er een fout optreedt bij het onvangen van een bericht
     * @see "LO3.3 Bijlage IV.3.2.4"
     * @see "LO3.3 Bijlage IV.4.3.3"
     * @see "LO3.3 Bijlage IV.5.2.3"
     */
    @Override
    public final Bericht getMessage(final Connection connection, final int msSequenceNumber) throws VoaException {
        final Operation request = new Operation.Builder().add(new GetMessage(msSequenceNumber)).build();
        sendOperation(connection, request);

        final Bericht result = new Bericht();

        try {
            final Operation response = receiveOperation(connection);
            for (final OperationRecord record : response.records()) {
                record.vulBericht(result);
            }
        } catch (final NumberFormatException ex) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, ex, result);
        } catch (final UnknownOpcodeException ex) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_MSENTRY_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final
        IllegalArgumentException
                | ParseException ex) {
            throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE, null, ex, result);
        }

        return result;
    }

    /**
     * This method sends a ListMessages request to the MailboxServer and receives a ListMessagesResult or
     * ListMessageConfirmation.
     * @param nextSequenceNr the SequenceNumber of the first new message to read on the Mailbox Server.
     * @param sequenceNumbers List which will filled with all the MSSequenceNumbers of the new messages on the Mailbox Server.
     * @param listLimitNr maximaal aantal van berichten om op te halen
     * @param msStatus welke status er op gehaald moet worden
     * @param prio welke prioriteit er aan berichten moeten worden opgehaald
     * @return new nextSequenceNr. When this number is 0, then there are no more new messages on the Mailbox Server. When this number > 0 this is the new
     * NextSequenceNr which should be used for a new call to this method.
     * @throws SpdProtocolException Wordt gegooid als er een fout in het sPD protocol wordt ontdekt, bv lengte klopt niet met opgegeven lengte of onverwachte
     * operationcode
     * @see "LO3.3 Bijlage IV.3.2.4"
     * @see "LO3.3 Bijlage IV.4.3.2"
     * @see "LO3.3 Bijlage IV.5.2.5"
     */
    @Override
    public final int listMessages(
            final Connection connection,
            final int nextSequenceNr,
            final List<Integer> sequenceNumbers,
            final int listLimitNr,
            final String msStatus,
            final SpdConstants.Priority prio) throws VoaException {
        final Operation request =
                new Operation.Builder().add(
                        new ListMessages.Builder().limit(listLimitNr).status(msStatus).priority(prio).fromMSSequenceNumber(nextSequenceNr).build()).build();
        sendOperation(connection, request);

        int nextMSSequenceNr = 0;

        VoaException voaException = null;
        try {
            final Operation response = receiveOperation(connection);

            for (final OperationRecord record : response.records()) {
                if (record instanceof ListMessagesConfirmation) {
                    voaException = processListMessageConfirmation((ListMessagesConfirmation) record);
                } else if (record instanceof ListResult) {
                    nextMSSequenceNr = processListResult((ListResult) record);
                } else if (record instanceof MSList) {
                    procesMSList(sequenceNumbers, (MSList) record);
                } else {
                    voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_CONF_OPCODE, new Object[]{record.operationCode()});
                }
            }
        } catch (final NumberFormatException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, null, ex);
        } catch (final UnknownOpcodeException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_CONF_OPCODE, new Object[]{ex.getOpcode()}, ex);
        } catch (final
        IllegalArgumentException
                | ParseException ex) {
            voaException = new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_LISTMESSAGES, null, ex);
        }

        if (voaException != null) {
            throw voaException;
        }

        return nextMSSequenceNr;
    }

    private void procesMSList(final List<Integer> sequenceNumbers, final MSList record) {
        final MSList list = record;
        sequenceNumbers.addAll(list.entries().stream().map(MSList.MSEntry::getMsSequenceNumber).collect(Collectors.toList()));
    }

    private int processListResult(final ListResult record) {
        int nextMSSequenceNr = 0;
        final ListResult result = record;
        if (result.getNextMSSequenceNumber() != null) {
            nextMSSequenceNr = result.getNextMSSequenceNumber();
        }
        return nextMSSequenceNr;
    }

    private VoaException processListMessageConfirmation(final ListMessagesConfirmation record) {
        VoaException voaException = null;
        if (record.listError() != ListMessagesConfirmation.ListError.NO_ENTRIES) {
            voaException = new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_ERROR,
                    new Object[]{Integer.valueOf(record.listError().code())});
        }
        return voaException;
    }

    /**
     * IMPORTANT NOTICE!<br>
     * To prevent dependencies on the platform's default characterset encoding, we will do a bitwise copy of the Java
     * string to a byte array. This byte array is sent to the SSL connection.
     * @param operation het bericht in sPd-formaat dat verstuurd moet worden naar de mailbox
     */
    private void sendOperation(final Connection connection, final Operation operation) {
        final String request = new SpdElementToStringVisitor().visit(operation).toString();
        LOGGER.debug("Sending request: {}", request);
        connection.write(GBACharacterSet.convertTeletexStringToByteArray(request));
    }

    /**
     * Read data from the SSL connection into the buffer.
     * @return message object met daarin het bericht van de mailbox en de lengte.
     */
    private Operation receiveOperation(final Connection connection) throws VoaException {
        final byte[] response = connection.read();
        LOGGER.debug(
                "Receiving response: {}",
                (response == null || response.length == 0) ? null : GBACharacterSet.convertTeletexByteArrayToString(response));
        return OperationParser.parse(response);
    }
}
