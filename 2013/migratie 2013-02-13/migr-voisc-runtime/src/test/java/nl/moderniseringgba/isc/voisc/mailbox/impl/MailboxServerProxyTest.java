/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.MailboxServerPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.SpdProtocolException;
import nl.moderniseringgba.isc.voisc.exceptions.SslPasswordException;
import nl.moderniseringgba.isc.voisc.mailbox.Connection;
import nl.moderniseringgba.isc.voisc.mailbox.Message;
import nl.moderniseringgba.isc.voisc.utils.StringUtil;
import nl.moderniseringgba.isc.voisc.utils.VoaUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

// CHECKSTYLE:OFF - Test klasse
/**
 * This class tests the MailboxServerProxy class. Each public method is tested. The MailboxServerSimulator has as
 * default behaviour. It returnes default always correct messages.<BR>
 * But to test the MailboxServerProxy class for Exceptional situations it's needed to manipulate the message which will
 * be received from the MailboxServerSimulator. Therefore it's possible to create an answer message and write this
 * message to a file, corresponding with the expected message.<BR>
 * The MBS-simulator first checks if there is a file with a FileName corresponding to the message. If there exists such
 * a file, the MBS-simulator uses this file, otherwise it uses its normal default messages.
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class MailboxServerProxyTest {

    private static final DecimalFormat DF_LENGTH = new DecimalFormat("00000");

    @Mock
    private Connection mbsConnection;

    @InjectMocks
    private MailboxServerProxyImpl mbsProxy;

    private static final String MAILBOX_NR = "1905010";

    private static final String MAILBOX_PWD = "1905010a";

    private static final int MAX_LIST_LIMIT_NR = 171;

    private static final int ZERO_LIST_LIMIT_NR = 0;

    private static final int TOO_MUCH_LIST_LIMIT_NR = 200;

    private static final String MSSTATUS = "012";

    private static final String PRIORITY = " ";

    private Mailbox mailbox;

    @Before
    public void setUp() {
        mailbox = createMailbox();
        mbsProxy.setErefPrefix("TA");
    }

    private Mailbox createMailbox() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxnr(MAILBOX_NR);
        mailbox.setMailboxpwd(MAILBOX_PWD);
        return mailbox;
    }

    @Test
    public void testConnect() {
        mbsProxy.connect();
        Mockito.verify(mbsConnection, Mockito.times(1)).connect();
    }

    /**
     * This method tests a normal LogonRequest (and indirectly a normal LogoffRequest).
     * 
     * 
     */
    @Test
    public void testLogonRequest() {
        final Message message = new Message();
        final String msg =
                "909" + "0000" + "00000000000" + "012345678901234567890123456789012345678901234567890123456789";
        // final String record = dfLength.format(msg.length()) + msg + SpdConstants.TERMINATOR;
        // final byte[] mesg = record.getBytes();
        message.setMessageToMailbox(DF_LENGTH.format(msg.length()) + msg);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                final Object[] args = invocation.getArguments();
                final Message mesg = (Message) args[0];
                final String msgString = mesg.getMessage();
                final String expected = "00018900" + MAILBOX_NR + MAILBOX_PWD + SpdConstants.TERMINATOR;

                Assert.assertEquals("Bericht is niet goed", expected, msgString);
                return null;
            }
        }).when(mbsConnection).write(Matchers.any(Message.class));
        Mockito.when(mbsConnection.read()).thenReturn(message);
        // Test logon confirmation with error code

        try {
            mbsProxy.logOn(mailbox);
            Mockito.verify(mbsConnection).read();
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a LogonRequest with an incorrect SSLPassword
     */
    @Test
    public void testConnectRequestWithIncorrectSSLPassword() {
        Mockito.doThrow(new SslPasswordException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD))
                .when(mbsConnection).connect();
        // Test logon confirmation with error code
        try {
            mbsProxy.connect();
            Assert.fail("Er wordt een SSLPasswordException verwacht");
        } catch (final SslPasswordException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8006]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a LogonRequest with a LogonConfirmation (which contains an error code in the LogonResult field)
     * The MailboxServerProxy should throw a SpdProtocolException.
     */
    @Test
    public void testLogonWithConfirmationError() {
        try {
            // Test logon confirmation with error code 1037 (Mailbox temporarily blocked)
            final String logonConfirmation =
                    "0007890910379602051100ZProefomgeving                                               "
                            + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8000]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogonZeroBytes() {
        try {
            // Test logon confirmation with error code 1037 (Mailbox temporarily blocked)
            final String logonConfirmation = "";
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8001]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogonWrongLength() {
        try {
            final String logonConfirmation = "000019090000000000000000" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8002]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogonWrongOpcode() {
        try {
            final String logonConfirmation = "000079990000" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8003]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogonNFE() {
        try {
            final String logonConfirmation = "000079A90000" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8000]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a LogonRequest with a LogonConfirmation (which indicates that the LogonRequest has used an
     * incorrect MailboxServer password) The MailboxServerProxy should throw a MailboxServerPasswordException
     */
    @Test
    public void testLogonWithIncorrectPassword() {
        try {
            // Test logon confirmation with wrong password
            final String logonConfirmation =
                    "0007890910339602051100ZProefomgeving                                               "
                            + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8004]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogoff() {
        final String logoffConfirmation = "000079990000" + SpdConstants.TERMINATOR;
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(logoffConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected = "00003990" + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.logOff();
            Mockito.verify(mbsConnection).read();
        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht!" + se.getMessage());
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogoffWrongOpCode() {
        final String logoffConfirmation = "000079A90000" + SpdConstants.TERMINATOR;
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(logoffConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.logOff();
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8013]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testLogoffNoBytesRead() {
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(new byte[0]);
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.logOff();
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8010]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a LogoffRequest with an incorrect operation code (939)
     */
    @Test
    public void testLogoffWithWrongOpertionCode() {
        try {
            // Test logoff confirmation with incorrect operation code
            final String logoffConfirmation = "000079390000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(logoffConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.logOff();
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8012]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a normal ChangePassWordRequest (and indirectly a normal LogonRequest and LogoffRequest).
     * 
     */
    @Test
    public void testChangePasswordRequest() {
        final String nwPassword = "BIZ199A3";

        try {
            // Test ChangePassword request
            final String changePasswordConfirmation = "000079190000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected = "00019910" + MAILBOX_PWD + nwPassword + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.changePassword(mailbox, nwPassword);
            Mockito.verify(mbsConnection).read();
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdUnacceptable() {
        final String nwPassword = "BBB199A3";

        try {
            // Test ChangePassword request with changePassword Confirmation (which contains code 1134)
            final String changePasswordConfirmation = "000079191134" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8027]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOldPwdMissing() {
        final String nwPassword = "BBB199A3";

        try {
            final String changePasswordConfirmation = "000079191131" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8024]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOldPwdInvalid() {
        final String nwPassword = "BBB199A3";

        try {
            final String changePasswordConfirmation = "000079191132" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8025]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdMissing() {
        final String nwPassword = "BBB199A3";

        try {
            final String changePasswordConfirmation = "000079191133" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8026]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestDefaultError() {
        final String nwPassword = "BIZ199A3";

        // Test ChangePassword request with changePassword Confirmation (which contains code 3434)
        try {
            final String changePasswordConfirmation = "000079193434" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8028]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdShort() {
        final String[] nwPasswords = new String[] { "YO", "PWD1S2LONG" };

        for (final String nwPassword : nwPasswords) {
            try {
                final String changePasswordConfirmation = "000079193434" + SpdConstants.TERMINATOR;

                final Message message = new Message();
                message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
                Mockito.when(mbsConnection.read()).thenReturn(message);

                mbsProxy.changePassword(mailbox, nwPassword);
                Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
            } catch (final MailboxServerPasswordException me) {
                Assert.assertTrue(me.getMessage().startsWith("[MELDING-8020]:"));
            } catch (final Exception e) {
                Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
            }
        }
    }

    @Test
    public void testChangePasswordRequestNoBytesRead() {
        final String nwPassword = "BIZ199A3";

        try {
            final String changePasswordConfirmation = "";

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8021]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestLengthNotMatched() {
        final String nwPassword = "BIZ199A3";

        try {
            final String changePasswordConfirmation = "000089190000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8022]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOpcodeInvalid() {
        final String nwPassword = "BIZ199A3";

        try {
            final String changePasswordConfirmation = "000079290000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8023]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNFE() {
        final String nwPassword = "BIZ199A3";

        try {
            final String changePasswordConfirmation = "000079A90000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail("Er wordt een MailboxServerPasswordException verwacht!");
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8028]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a PutMessage with a correct PutMessageConfirmation
     * 
     */
    @Test
    public void testPutMessage() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String recipient = "1902010";
        final String eref = "TA0000000001";
        final String submissionTime = "1207171621Z";
        final Date expectedTime = VoaUtil.convertSpdTimeStringToDate(submissionTime);
        final String berichtInhoud = "00000000Lq0101234567890";

        final Bericht bericht = new Bericht();
        bericht.setId(1);
        // set bericht fields
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setRecipient(recipient);
        bericht.setEref(eref);

        try {
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation =
                    "00039" + "190" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String putEnvelope = "00024" + "120" + "       " + "2" + "0" + "           " + "1";
                    final String msgHeader =
                            "00045" + "150" + eref + "            " + "       " + "001" + recipient + "1";
                    final String msgBody = "00026" + "180" + berichtInhoud;
                    final String expected = putEnvelope + msgHeader + msgBody + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.putMessage(bericht);

            Assert.assertEquals("DispatchSequenceNr is niet goed gevuld.", bericht.getDispatchSequenceNumber(),
                    dispatchSequenceNr);
            Assert.assertEquals("ExpectedTime is incorrect aan bericht.getTijdstipVerzendingOntvangst",
                    expectedTime.toString(), bericht.getTijdstipVerzendingOntvangst().toString());
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageNoBytes() {
        // init
        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref("AA0123456789");
        bericht.setRecipient("1902010");
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(new byte[0]);
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8090]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageGeenBericht() {
        try {
            mbsProxy.putMessage(null);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen VoaException verwacht! " + se.getMessage());
        } catch (final Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageNoRecipient() {
        // init
        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref("AA0123456789");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8095]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageTooShortRecipient() {
        // init
        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref("AA0123456789");
        bericht.setRecipient("1");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8095]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageNoEref() {
        // init
        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref(null);
        bericht.setRecipient("1901010");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8094]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageEmptyEref() {
        // init
        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref("");
        bericht.setRecipient("1901010");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8094]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageInvalidLength() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String recipient = "1902010";
        final String eref = "TA0000000001";
        final String submissionTime = "1207171621Z";
        final String berichtInhoud = "00000000Lq0101234567890";

        final Bericht bericht = new Bericht();
        bericht.setId(1);
        // set bericht fields
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setRecipient(recipient);
        bericht.setEref(eref);
        try {
            final String putMessageConfirmation =
                    "00040" + "190" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8091]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageIncorrectResult() {
        // init
        final Bericht bericht = new Bericht();
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "AA0123456789";
        final String submissionTime = "1207171621Z";

        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref("AA0123456789");
        bericht.setRecipient("1902010");
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation =
                    "00039" + "190" + "1051" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8093]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageErefNotEqual() {
        // init
        final Bericht bericht = new Bericht();
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "AA0123456789";
        final String submissionTime = "1207171621Z";

        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref(eref.replace('9', '8'));
        bericht.setRecipient("1902010");
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation =
                    "00039" + "190" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8097]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * 
     */
    @Test
    public void testPutMessageNFE() {
        // init
        final Bericht bericht = new Bericht();
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "AA0123456789";
        final String submissionTime = "1207171621Z";

        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref(eref);
        bericht.setRecipient("1902010");
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation =
                    "00039" + "1A0" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8096]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a PutMessage with a incorrect PutMessageConfirmation. (the operationcode is incorrect).
     * 
     */
    @Test
    public void testPutMessageWithConfirmationError() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "AA0123456789";
        final String submissionTime = "1207171621Z";

        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud("00000000Lq0101234567890");
        bericht.setEref(eref);
        bericht.setRecipient("1902010");

        try {
            // Test putMessageConfirmation with incorrect operationcode (192 ipv 190)
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation =
                    "00039" + "192" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8092]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests if the specified length of the BerichtBody of an outgoing bericht with TeleTex characters is
     * correct.
     */
    @Test
    public void testPutMessageLength() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "TA0000000001";
        final String submissionTime = "1207171621Z";
        final String berichtInhoud =
                "00000000Vb01" + TestGBACharacterSet.VRIJ_BERICHT_TELETEX_TEKENS.replace('\n', 'n');
        final int originalLength = berichtInhoud.length();

        // create outgoing bericht with TeleTex characters
        final Bericht bericht = new Bericht();
        bericht.setId(1);
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setEref(eref);
        bericht.setRecipient("1902010");

        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                final int offsetVb01 = 16;
                final int startVb01Inhoud = 8;
                final Object[] args = invocation.getArguments();
                final Message mesg = (Message) args[0];
                final String msgString = mesg.getMessage();

                final String vb01 = msgString.substring(msgString.indexOf("Vb01") - offsetVb01);
                int verzondenLength = new Integer(vb01.substring(0, 5)).intValue();
                verzondenLength -= 3; // minus the length of the operationcode (180)
                Assert.assertEquals("Bericht-lengtes zijn niet gelijk", originalLength, verzondenLength);
                Assert.assertEquals("Berichten zijn niet gelijk", berichtInhoud,
                        vb01.substring(startVb01Inhoud, vb01.length() - SpdConstants.TERMINATOR.length()));
                return null;
            }

        }).when(mbsConnection).write(Matchers.any(Message.class));

        // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
        // MessageId[12]
        final String putMessageConfirmation =
                "00039" + "190" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
        final Message message = new Message();
        message.setMessageFromMailbox(putMessageConfirmation.getBytes());
        Mockito.when(mbsConnection.read()).thenReturn(message);

        // test
        try {
            mbsProxy.putMessage(bericht);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a PutMessage TeleTex Characters. The test should not 'hang'. This might indicate a wrong SPD
     * message format: The data lengt is greater than the actual data wich causes the MBS to wait for more data that is
     * not available.
     */
    @Test
    public void testPutMessageTeleTex() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String eref = "TA0000000001";
        final String submissionTime = "1207171621Z";
        final Date expectedTime = VoaUtil.convertSpdTimeStringToDate(submissionTime);

        final Bericht bericht = new Bericht();
        final String vb01TeleTex =
                "00000000Vb01" + TestGBACharacterSet.VRIJ_BERICHT_TELETEX_TEKENS.replace('\n', 'n') + "00000";

        // set bericht fields
        bericht.setId(1);
        bericht.setBerichtInhoud(vb01TeleTex);
        bericht.setEref(eref);
        bericht.setRecipient("1902010");

        try {
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation =
                    "00039" + "190" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);

            Assert.assertEquals("DispatchSequenceNr is niet goed gevuld.", bericht.getDispatchSequenceNumber(),
                    dispatchSequenceNr);
            // check tijdstip verzending ontvangst
            Assert.assertEquals("ExpectedTime is incorrect aan bericht.getTijdstipVerzendingOntvangst",
                    expectedTime.toString(), bericht.getTijdstipVerzendingOntvangst().toString());
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a ListMessagesResult with 10
     * MSSequenceNrs.
     */
    @Test
    public void testListMessages() {
        final List<Integer> seqNrs = new ArrayList<Integer>();
        int nextSequenceNr = 0;

        try {
            // ListMessagesResult: ListResult + MSList
            // ListResult: Length[5] + Operationcode[3] + NextMSSequenceNumber[9] +
            // MSList: Length[5] + Operationcode[3] + NumberOfMSEntries[5] +MSEntries
            // MSEntries: MSSequenceNumber[9] + MSStatus[1] + Priority[1] + DeliveryTime[11] + OriginatorORName[7];

            final StringBuilder msEntries = new StringBuilder();
            final DecimalFormat dfSequenceNr = new DecimalFormat("000000000");
            for (int i = 1; i <= 10; i++) {
                msEntries.append(dfSequenceNr.format(i));
                msEntries.append(0);
                msEntries.append(0);
                msEntries.append("1210181642Z");
                msEntries.append("1904010");
            }
            final String msList = "00298" + "411" + "00010" + msEntries.toString();
            final String listResult = "00012" + "410" + "000000001";
            final String listMessagesResult = listResult + msList + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesResult.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected =
                            "00028" + "400" + "171" + "012" + " " + "000000000" + "         "
                                    + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);

            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 1. Nl: " + nextSequenceNr, nextSequenceNr == 1);
            Assert.assertTrue("List.size is ongelijk aan 10. Nl: " + seqNrs.size(), seqNrs.size() == 10);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a ListMessagesResult with
     * 170 MSSequenceNrs. To test this operation there should be a Logon and a Logoff operation. (using a file to
     * manipulate the MailboxSimulator)
     */
    @Test
    public void testListMessagesWithMoreMessages() {
        String listMessagesConfirmation =
                "000124100000003870493841100170000000217200510211708Z9002070000000218200510211708Z1810010000000219200510211708Z1810010000000220200510211708Z1810010000000221200510211708Z1810010000000222200510211708Z1810010000000223200510211708Z1810010000000224200510211708Z1810010000000225200510211708Z1810010000000226200510211708Z1810010000000227200510211708Z1810010000000228200510211708Z1810010000000229200510211708Z1810010000000230200510211708Z1810010000000231200510211708Z1810010000000232200510211708Z1810010000000233200510211708Z1810010000000234200510211708Z1810010000000235200510211708Z1810010000000236200510211708Z1810010000000237200510211708Z1810010000000238200510211709Z1810010000000239200510211709Z1810010000000240200510211709Z1810010000000241200510211709Z1810010000000242200510211709Z1810010000000243200510211709Z1810010000000244200510211709Z1810010000000245200510211709Z1810010000000246200510211709Z1810010000000247200510211709Z1810010000000248200510211709Z1810010000000249200510211709Z1810010000000250200510211709Z1810010000000251200510211709Z1810010000000252200510211709Z1810010000000253200510211709Z1810010000000254200510211709Z1810010000000255200510211709Z1810010000000256200510211709Z1810010000000257200510211709Z1810010000000258200510211709Z1810010000000259200510211709Z1810010000000260200510211709Z1810010000000261200510211709Z1810010000000262200510211709Z1810010000000263200510211709Z1810010000000264200510211709Z1810010000000265200510211709Z1810010000000266200510211709Z";
        listMessagesConfirmation +=
                "1810010000000267200510211709Z1810010000000268200510211709Z1810010000000269200510211709Z1810010000000270200510211709Z1810010000000271200510211709Z1810010000000272200510211709Z1810010000000273200510211709Z1810010000000274200510211709Z1810010000000275200510211709Z1810010000000276200510211709Z1810010000000277200510211709Z1810010000000278200510211709Z1810010000000279200510211709Z1810010000000280200510211709Z1810010000000281200510211709Z1810010000000282200510211709Z1810010000000283200510211709Z1810010000000284200510211709Z1810010000000285200510211709Z1810010000000286200510211709Z1810010000000287200510211709Z1810010000000288200510211709Z1810010000000289200510211709Z1810010000000290200510211709Z1810010000000291200510211709Z1811010000000292200510211709Z1811010000000293200510211709Z1811010000000294200510211709Z1811010000000295200510211709Z1811010000000296200510211709Z1811010000000297200510211709Z1811010000000298200510211709Z1811010000000299200510211709Z1811010000000300200510211709Z1811010000000301200510211709Z1811010000000302200510211709Z1811010000000303200510211709Z1811010000000304200510211709Z1811010000000305200510211709Z1811010000000306200510211709Z1811010000000307200510211709Z1811010000000308200510211709Z1811010000000309200510211709Z1811010000000310200510211709Z1811010000000311200510211709Z1811010000000312200510211709Z1811010000000313200510211709Z1811010000000314200510211709Z1811010000000315200510211709Z1811010000000316200510211709Z1811010000000317200510211709Z";
        listMessagesConfirmation +=
                "1811010000000318200510211709Z1811010000000319200510211709Z1811010000000320200510211709Z1811010000000321200510211709Z1811010000000322200510211709Z1811010000000323200510211709Z1811010000000324200510211709Z1811010000000325200510211709Z1811010000000326200510211709Z1811010000000327200510211709Z1811010000000328200510211709Z1811010000000329200510211709Z1811010000000330200510211709Z1811010000000331200510211709Z1811010000000332200510211709Z1811010000000333200510211709Z1811010000000334200510211709Z1811010000000335200510211709Z1811010000000336200510211709Z1811010000000337200510211709Z1810010000000338200510211709Z1810010000000339200510211709Z1810010000000340200510211709Z1810010000000341200510211709Z1810010000000342200510211709Z1810010000000343200510211709Z1810010000000344200510211709Z1810010000000345200510211709Z1810010000000346200510211709Z1810010000000347200510211709Z1810010000000348200510211709Z1810010000000349200510211709Z1810010000000350200510211709Z1810010000000351200510211709Z1810010000000352200510211709Z1810010000000353200510211709Z1810010000000354200510211709Z1810010000000355200510211709Z1810010000000356200510211709Z1810010000000357200510211709Z1810010000000358200510211709Z1810010000000359200510211709Z1810010000000360200510211709Z1810010000000361200510211709Z1810010000000362200510211709Z1810010000000363200510211709Z1810010000000364200510211709Z1810010000000365200510211709Z1810010000000366200510211709Z1810010000000367200510211709Z1810010000000368200510211709Z1810010000000369200510211709Z1810010000000370200510211709Z1810010000000371200510211709Z1810010000000372200510211709Z1810010000000373200510211709Z1810010000000374200510211709Z1810010000000375200510211709Z1810010000000376200510211709Z1810010000000377200510211709Z1810010000000378200510211709Z1810010000000379200510211709Z1810010000000380200510211709Z1810010000000381200510211709Z1810010000000382200510211709Z1810010000000383200510211709Z1810010000000384200510211709Z1810010000000385200510211709Z1810010000000386200510211709Z1810010";
        listMessagesConfirmation += SpdConstants.TERMINATOR;

        // Test listMessageResult with 170 MSSequenceNumbers
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            // execute
            final List<Integer> seqNrs = new ArrayList<Integer>();
            final int nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);

            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 387. Nl: " + nextSequenceNr, nextSequenceNr == 387);
            Assert.assertTrue("List.size is ongelijk aan 170. Nl: " + seqNrs.size(), seqNrs.size() == 170);
            Assert.assertTrue("Het laatste item in de List is niet gelijk aan 386. Nl: " + seqNrs.get(169), seqNrs
                    .get(169).equals(new Integer(386)));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a Confirmation with the code
     * 1113 (which means: no (new) messages on the MBS)
     * 
     */
    @Test
    public void testListMessagesNoNewMessagesConfirmation() {
        final List<Integer> seqNrs = new ArrayList<Integer>();
        int nextSequenceNr = 0;

        // Test listMessageConfirmation
        try {
            // ListMessagesConfirmation: Length[5] + Operationcode[3] + ListError[4]
            final String listMessagesConfirmation = "00007" + "419" + "1113" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);

            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 0. Nl: " + nextSequenceNr, nextSequenceNr == 0);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a Confirmation with an error
     * code 1114.
     * 
     */
    @Test
    public void testListMessagesWithConfirmationError() {
        final List<Integer> seqNrs = new ArrayList<Integer>();
        int nextSequenceNr = 0;

        try {
            // ListMessagesConfirmation: Length[5] + Operationcode[3] + ListError[4]
            final String listMessagesConfirmation = "00007" + "419" + "1114" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 0. Nl: " + nextSequenceNr, nextSequenceNr == 0);
        } catch (final SpdProtocolException spe) {
            Assert.assertTrue(spe.getMessage().startsWith("[MELDING-8032]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesNoBytesRead() {
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(new byte[0]);
            Mockito.when(mbsConnection.read()).thenReturn(message);

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException spe) {
            Assert.assertTrue(spe.getMessage().startsWith("[MELDING-8030]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesLessThenMinLimit() {
        try {
            // ListMessagesConfirmation: Length[5] + Operationcode[3] + ListError[4]
            final String listMessagesConfirmation = "00007" + "419" + "1113" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected =
                            "00028" + "400" + "040" + "012" + " " + "000000000" + "         "
                                    + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), ZERO_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesMoreThenMaxLimit() {
        try {
            // ListMessagesConfirmation: Length[5] + Operationcode[3] + ListError[4]
            final String listMessagesConfirmation = "00007" + "419" + "1113" + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected =
                            "00028" + "400" + "171" + "012" + " " + "000000000" + "         "
                                    + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), TOO_MUCH_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesResultNoMessagesListed() {
        try {
            final String listResult = "00045" + "410";
            final String listMessagesResult = listResult + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesResult.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException spe) {
            Assert.assertTrue(spe.getMessage().startsWith("[MELDING-8033]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesIllegalOpcode() {
        try {
            final String listResult = "00045" + "412";
            final String listMessagesResult = listResult + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesResult.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException spe) {
            Assert.assertTrue(spe.getMessage().startsWith("[MELDING-8031]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testListMessagesOpcodeNFE() {
        try {
            final String listResult = "00045" + "41a";
            final String listMessagesResult = listResult + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(listMessagesResult.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException spe) {
            Assert.assertTrue(spe.getMessage().startsWith("[MELDING-8032]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a ListMessage and serveral getMessages with a normal getMessageResults
     * 
     */
    @Test
    public void testGetMessage() {

        Bericht bericht = null;
        final String bref = "ANTWOORD2   ";
        final String eref = "AA0123456789";
        final String deliveryTime = "0408121045Z";
        final Date expectedTime = VoaUtil.convertSpdTimeStringToDate(deliveryTime);
        final int msSequenceNumber = 123456790;
        final String recipient = "1831010";
        final String originator = "1904010";
        final String inhoud =
                "00000000Lg01200505181430000008919258058000000000002539011910110010891925805801200093019439280210018CorneliaFrancisca0240007Wiegman03100081907020303200041157033000460300410001V6110001V821000403638220008199409308230003PKA851000819260715861000819951001021750210020FranciscaGeertruida0230003van0240006Velzen03100081880060503200041157033000460300410001V621000819070203821000403638220008199409308230002PK851000819070203861000819940930031620210016HendrikAlbertus0240007Wiegman03100081877052303200041157033000460300410001M621000819070203821000403638220008199409308230002PK85100081907020386100081994093004051051000400016310003001851000819070203861000819940930052220110010671307312301200093019459370210018AdrianusHendrikus0240010Holthuizen031000819040215032000411570330004603006100081926071506200040363063000460301510001H821000403638220008199409308230002PK8510008192607158610008199510010704268100081994093069100040363701000108710001P08131091000405180920008199505151010001W1030008200107281110015NieuweParklaan11200025811600062597LD7210001K85100082001072886100082001072958127091000405180920008199505151010001W1030008199505151110011Lobelialaan11200025311600062555PC7210001K85100081995051586100081995051658126091000403630920008192101011010001W1030008195407091110010Maasstraat11200026911600061078HE7210001A851000819540709861000819940930091770110010579601382301200093019618270210018AngelieFrancisca0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081995100109158011001082526131320210015Eveline Johanna0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081994093009165011001090737506700210022Franciscus Engelbertus0240010Holthuizen0310008193003150320004036303300046030821000403638220008199409308230002PK851000819300315861000819940930091690110010706374630801200093019559180210010Margaretha0240010Holthuizen0310008192810120320004036303300046030821000403638220008199409308230002PK851000819281012861000819951001091670110010584593708601200093019579270210008Adrianus0240010Holthuizen0310008192702030320004036303300046030821000403638220008199409308230002PK851000819270203861000819951001121703510002NI3520009IB87656303530008200312153540005B05183550008200812153580003154821000405188220008200312208230030Aanvraagformulier reisdocument851000820031215861000820031220121933510002EK3520009T505049053530008199812043540005B05183550008200312043560008200312153570001I3580003154821000405188220008199812048230030Aanvraagformulier reisdocument851000820031215861000820031215140284010006900208851000819941122";
        // cleanup
        try {
            final StringBuffer getMessageResult = new StringBuffer();
            // MSEntry: Length[5] + Operationcode[3] + MSSequencenumber[9]
            getMessageResult.append("00012").append("210").append(msSequenceNumber);
            // GetEnvelope: Length[5] + Operationcode[3] + OriginatorORName[7] + Contenttype[1] + Priority[1] +
            // DeliveryTime[11] + SubmissionTime[11] + ActualRecipientORName[7]
            getMessageResult.append("00041").append("220").append(originator).append("2").append("1")
                    .append(deliveryTime).append("0408121045Z").append(recipient);
            // GetMessageHeading: Length[5] + Operationcode[3] + MessasgeId[12] + CrossReference[12] +
            // OriginatorUsername[7] + ActualRecipientORName[7] + ActualNotificationRequest[1]
            getMessageResult.append("00042").append("250").append(bref).append(eref).append("1234567")
                    .append("1234567").append("0");
            // GetMessageBody: Length[5] + Operationcode[3] + Bodystring[max 19000]
            getMessageResult.append(StringUtil.zeroPadded(inhoud.length() + 3, 5)).append("280").append(inhoud);
            getMessageResult.append(SpdConstants.TERMINATOR);

            final Message message = new Message();
            message.setMessageFromMailbox(getMessageResult.toString().getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Object[] args = invocation.getArguments();
                    final Message mesg = (Message) args[0];
                    final String msgString = mesg.getMessage();
                    final String expected = "00012" + "200" + msSequenceNumber + SpdConstants.TERMINATOR;

                    Assert.assertEquals("Bericht is niet goed", expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            bericht = mbsProxy.getMessage(msSequenceNumber);
            Assert.assertTrue(
                    "Het MSSequenceNumber uit het bericht is niet gelijk aan het SequenceNumber uit de ListResult",
                    bericht.getDispatchSequenceNumber().equals(msSequenceNumber));
            // MessageHeader
            Assert.assertNotNull(bericht.getBerichtInhoud());
            Assert.assertEquals(eref, bericht.getEref2());
            Assert.assertEquals(bref, bericht.getBref());
            // MessageEnvelope
            Assert.assertEquals(originator, bericht.getOriginator());
            Assert.assertEquals(expectedTime.toString(), bericht.getTijdstipVerzendingOntvangst().toString());
            Assert.assertEquals(recipient, bericht.getRecipient());
            // MessageBody
            Assert.assertEquals(inhoud, bericht.getBerichtInhoud());

        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This test will receive one null-bericht with an incorrect MessageBody operationcode <br>
     */
    @Test
    public void testGetMessageIncorrectOpcode() {
        try {
            // operationcode of MessageBody is 282 (normally: 280)
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 19019001901900000003282"
                            + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8099]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     */
    @Test
    public void testGetMessageNoBytesRead() {
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(new byte[0]);
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8040]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method tests a getMessage with a normal getMessageResult of a Null bericht. All filled fields of the
     * LO3Bericht are checked.
     * 
     */
    @Test
    public void testGetMessageNullBericht() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 19019001901900000003280"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            final Bericht bericht = mbsProxy.getMessage(123);

            // (MSEntry) dispatch_sequence_number
            Assert.assertEquals("DispatchSeqNr is ongelijk aan: 11950", new Integer(11950),
                    bericht.getDispatchSequenceNumber());
            // (GetEnvelope) originator_or_recipient
            Assert.assertEquals("Originator is ongelijk aan: 1901900", "1901900", bericht.getOriginator());
            // (GetEnvelope) tijdstip_verzending_ontvangst
            final Date expectedDeliveryTime = VoaUtil.convertSpdTimeStringToDate("0408290918Z");
            Assert.assertEquals("Tijdstip_verzending_ontvangst is ongelijk aan: 2004-08-29 09:18",
                    expectedDeliveryTime.toString(), bericht.getTijdstipVerzendingOntvangst().toString());
            // (MessageHeading) MessageId

            Assert.assertEquals("MessageId is ongelijk aan: ANTWOORD", bericht.getBref(), "ANTWOORD    ");
            // (MessageHeading) CrossReference
            Assert.assertEquals("CrossReference is ongelijk aan: YY000000001", bericht.getEref2(), "YY000000001 ");
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageNullBerichtEref() {
        final String[] erefs = new String[] { "            ", "000000000000", "0           ", "           0" };
        for (final String eref : erefs) {
            try {
                final String nullBericht =
                        "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    " + eref
                                + "19019001901900000003280" + SpdConstants.TERMINATOR;

                final Message message = new Message();
                message.setMessageFromMailbox(nullBericht.getBytes());
                Mockito.when(mbsConnection.read()).thenReturn(message);
                final Bericht bericht = mbsProxy.getMessage(123);

                // (MessageHeading) CrossReference
                Assert.assertNull("CrossReference is niet null", bericht.getEref2());
            } catch (final Exception e) {
                Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
            }
        }
    }

    @Test
    public void testGetMessageMessageHeadingWrongLength() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000041250ANTWOORD    YY000000001 19019001901900000003280"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8061]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageMessageHeadingLengthNFE() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z18310100004a250ANTWOORD    YY000000001 19019001901900000003280"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageMessageBodyWrongLength() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 19019001901900000004280"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8062]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageMessageBodyLengthNFE() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 1901900190190000000a280"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageMessageBodyNullBytes() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 19019001901900000006280B"
                            + '\u0000' + "S" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8046]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method test a getMessage with a normal DeliveryReport as result
     * 
     */
    @Test
    public void testGetMessageDeliveryReport() {
        // Test Delivery Report
        try {
            final String deliveryReport =
                    "00012210000011950000482600408231236Z0812002000011234567           0001"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            final Bericht delRep = mbsProxy.getMessage(123);

            // dispatch_sequence_number
            Assert.assertEquals("DispatchSeqNr is ongelijk aan: 81200200", delRep.getDispatchSequenceNumber(),
                    new Integer(81200200));
            // report_delivery_time
            final Date expectedDelRepTime = VoaUtil.convertSpdTimeStringToDate("0408231236Z");
            Assert.assertEquals("Report delivery time is ongelijk aan: 2004-08-23 12:36",
                    expectedDelRepTime.toString(), delRep.getReportDeliveryTime().toString());
            // originator_or_recipient
            Assert.assertEquals("Recipient is ongelijk aan: 1234567", "1234567", delRep.getRecipient());
            // non_delivery_reason
            Assert.assertEquals("NonDeliveryReason is ongelijk aan: 0001", "0001", delRep.getNonDeliveryReason());

        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht! " + se.getMessage());
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportWrongLength() {
        // Test Delivery Report
        try {
            final String deliveryReport =
                    "00012210000011950000492600408231236Z0812002000011234567           0001"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8050]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportMoreThenOneRecipient() {
        // Test Delivery Report
        try {
            final String deliveryReport =
                    "00012210000011950000482600408231236Z0812002000021234567           00011234567           0001"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8052]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportRecipientNFE() {
        // Test Delivery Report
        try {
            final String deliveryReport =
                    "00012210000011950000482600408231236Z08120020000a1234567           0001"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * This method test a getMessage with a normal StatusReport as result
     */
    @Test
    public void testGetMessageStatusReport() {
        // Test Status Report
        try {
            final String statusReport =
                    "00012210000011950000412201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            final Bericht statRep = mbsProxy.getMessage(123);

            // (MSEntry) dispatch_sequence_number
            Assert.assertEquals("DispatchSeqNr: ", new Integer(11950), statRep.getDispatchSequenceNumber());
            // (GetEnvelope) tijdstip_verzending_ontvangst
            final Date expectedDeliveryTime = VoaUtil.convertSpdTimeStringToDate("0408121045Z");
            Assert.assertEquals("Tijdstip_verzending_ontvangst", expectedDeliveryTime.toString(), statRep
                    .getTijdstipVerzendingOntvangst().toString());
            // (GetEnvelope) originator_or_recipient
            Assert.assertEquals("Originator", "1831010", statRep.getOriginator());
            // non_receipt_reason
            Assert.assertEquals("NonReceiptReason", "1", statRep.getNonReceiptReason());
        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht! " + se.getMessage());
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportWrongLengthEnvelope() {
        // Test Status Report
        try {
            final String statusReport =
                    "00012210000011950000402201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8060]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportLengthEnvelopeNFE() {
        // Test Status Report
        try {
            final String statusReport =
                    "000122100000119500004a2201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportWrongLength() {
        // Test Status Report
        try {
            final String statusReport =
                    "00012210000011950000412201831010210408121045Z0408121045Z18310100002327018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8070]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportLengthNFE() {
        // Test Status Report
        try {
            final String statusReport =
                    "00012210000011950000412201831010210408121045Z0408121045Z18310100002a27018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * Test a getMessage request which receives a GetMessageConfirmation. NOTE: before a GetMessage can be executed on
     * the MailboxServer simulator you have to execute a ListMessage. Otherwise the GetMessage will fail.
     */
    @Test
    public void testGetMessageMessageConfirmation() {
        // Test getMessage confirmation with error code (1071) Bericht niet gevonden
        try {
            final String getMessageConfirmation = "000072901071" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(getMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8042]:"));
            Assert.assertTrue(se.getMessage().contains("1071"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    /**
     * Test a getMessage request which receives a GetMessageConfirmation. NOTE: before a GetMessage can be executed on
     * the MailboxServer simulator you have to execute a ListMessage. Otherwise the GetMessage will fail.
     */
    @Test
    public void testGetMessageMessageConfirmationWrongLength() {
        // Test getMessage confirmation with error code (1071) Bericht niet gevonden
        try {
            final String getMessageConfirmation = "000062901071" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(getMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8041]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageWrongAnswer() {
        // Test met LogoffConfirmation ipv GetMessageResult
        try {
            final String logoffConfirmation = "000070090000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(logoffConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8043]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageNFE() {
        // Test met LogoffConfirmation ipv GetMessageResult
        try {
            final String logoffConfirmation = "000070A90000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(logoffConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }

    @Test
    public void testMSEntryNFE() {
        final String bref = "ANTWOORD2   ";
        final String eref = "AA0123456789";
        final String deliveryTime = "0408121045Z";
        final String msSequenceNumber = "12345679A";
        final String recipient = "1831010";
        final String originator = "1904010";
        final String inhoud =
                "00000000Lg01200505181430000008919258058000000000002539011910110010891925805801200093019439280210018CorneliaFrancisca0240007Wiegman03100081907020303200041157033000460300410001V6110001V821000403638220008199409308230003PKA851000819260715861000819951001021750210020FranciscaGeertruida0230003van0240006Velzen03100081880060503200041157033000460300410001V621000819070203821000403638220008199409308230002PK851000819070203861000819940930031620210016HendrikAlbertus0240007Wiegman03100081877052303200041157033000460300410001M621000819070203821000403638220008199409308230002PK85100081907020386100081994093004051051000400016310003001851000819070203861000819940930052220110010671307312301200093019459370210018AdrianusHendrikus0240010Holthuizen031000819040215032000411570330004603006100081926071506200040363063000460301510001H821000403638220008199409308230002PK8510008192607158610008199510010704268100081994093069100040363701000108710001P08131091000405180920008199505151010001W1030008200107281110015NieuweParklaan11200025811600062597LD7210001K85100082001072886100082001072958127091000405180920008199505151010001W1030008199505151110011Lobelialaan11200025311600062555PC7210001K85100081995051586100081995051658126091000403630920008192101011010001W1030008195407091110010Maasstraat11200026911600061078HE7210001A851000819540709861000819940930091770110010579601382301200093019618270210018AngelieFrancisca0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081995100109158011001082526131320210015Eveline Johanna0240010Holthuizen0310008193106120320004036303300046030821000403638220008199409308230002PK85100081931061286100081994093009165011001090737506700210022Franciscus Engelbertus0240010Holthuizen0310008193003150320004036303300046030821000403638220008199409308230002PK851000819300315861000819940930091690110010706374630801200093019559180210010Margaretha0240010Holthuizen0310008192810120320004036303300046030821000403638220008199409308230002PK851000819281012861000819951001091670110010584593708601200093019579270210008Adrianus0240010Holthuizen0310008192702030320004036303300046030821000403638220008199409308230002PK851000819270203861000819951001121703510002NI3520009IB87656303530008200312153540005B05183550008200812153580003154821000405188220008200312208230030Aanvraagformulier reisdocument851000820031215861000820031220121933510002EK3520009T505049053530008199812043540005B05183550008200312043560008200312153570001I3580003154821000405188220008199812048230030Aanvraagformulier reisdocument851000820031215861000820031215140284010006900208851000819941122";
        // cleanup
        try {
            final StringBuffer getMessageResult = new StringBuffer();
            // MSEntry: Length[5] + Operationcode[3] + MSSequencenumber[9]
            getMessageResult.append("00012").append("210").append(msSequenceNumber);
            // GetEnvelope: Length[5] + Operationcode[3] + OriginatorORName[7] + Contenttype[1] + Priority[1] +
            // DeliveryTime[11] + SubmissionTime[11] + ActualRecipientORName[7]
            getMessageResult.append("00041").append("220").append(originator).append("2").append("1")
                    .append(deliveryTime).append("0408121045Z").append(recipient);
            // GetMessageHeading: Length[5] + Operationcode[3] + MessasgeId[12] + CrossReference[12] +
            // OriginatorUsername[7] + ActualRecipientORName[7] + ActualNotificationRequest[1]
            getMessageResult.append("00042").append("250").append(bref).append(eref).append("1234567")
                    .append("1234567").append("0");
            // GetMessageBody: Length[5] + Operationcode[3] + Bodystring[max 19000]
            getMessageResult.append("02594").append("280").append(inhoud);
            getMessageResult.append(SpdConstants.TERMINATOR);

            final Message message = new Message();
            message.setMessageFromMailbox(getMessageResult.toString().getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail("Er wordt geen Exception verwacht! " + e.getMessage());
        }
    }
}
