/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.constants.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.SslPasswordException;
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

    private static final String MELDING_GEEN_EXCEPTION_VERWACHT = "Er wordt geen Exception verwacht! ";

    private static final String BERICHT_IS_NIET_GOED = "Bericht is niet goed";

    private static final String NEW_LINE = "\n";

    private static final String OPERATION_CODE_190 = "190";

    private static final String LENGTE_39 = "00039";

    private static final String BERICHT_INHOUD = "00000000Lq0101234567890";

    private static final String SUBMISSION_TIME = "1207171621Z";

    private static final String EREF = "TA0000000001";

    private static final String RECIPIENT = "1902010";

    private static final String MELDING_SERVER_PASSWORD_EXCEPTION = "Er wordt een MailboxServerPasswordException verwacht!";

    private static final String FOUTIEF_NIEUW_MAILBOX_PASSWORD = "BBB199A3";

    private static final String NIEUW_MAILBOX_PASSWORD = "BIZ199A3";

    private static final String LENGTE_12 = "00012";

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

    public final static String VRIJ_BERICHT_TELETEX_TEKENS = "            +-------------------------------------------------+\n"
            + "            | *****   Vrij bericht Teletex-tekens       ***** |\n"
            + "            | *****   versie 3.0    februari 2001       ***** |\n"
            + "            +-------------------------------------------------+\n"
            + NEW_LINE
            + "      Dit  bericht  is  uitsluitend  bedoeld  om   de   ontvanger   de\n"
            + "      gelegenheid  te geven na te gaan of alle vereiste TELETEX tekens\n"
            + "      op de juiste wijze  worden  gerepresenteerd  op  terminal  en/of\n"
            + "      printer.   Ondanks  het  feit  dat  u dit bericht tot zover hebt\n"
            + "      kunnen  lezen,  hetgeen  uiteraard  op  een  zekere   mate   van\n"
            + "      betrouwbaarheid  wijst,  kan  een wat systematischer aanpak geen\n"
            + "      kwaad.  We beginnen derhalve met de gebruikelijke  alfanumerieke\n"
            + "      tekenset.\n"
            + NEW_LINE
            + "         ---- Het alfabet in hoofdletters in beide richtingen ----\n"
            + NEW_LINE
            + "                         ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
            + "                         ZYXWVUTSRQPONMLKJIHGFEDCBA\n"
            + NEW_LINE
            + "         --- Het alfabet in kleine letters in beide richtingen ---\n"
            + NEW_LINE
            + "                         abcdefghijklmnopqrstuvwxyz\n"
            + "                         zyxwvutsrqponmlkjihgfedcba\n"
            + NEW_LINE
            + "         ------------ Alle cijfers in beide richtingen -----------\n"
            + NEW_LINE
            + "                                 0123456789\n"
            + "                                 9876543210\n"
            + NEW_LINE
            + "      Ingeval u zowel deze tekensets als alle voorgaande tekst  netjes\n"
            + "      gecentreerd  hebt  kunnen  lezen,  worden  naast de spatie  <sp>\n"
            + "      tevens de \"carriage return\"  <cr> en de  \"line feed\"  <lf>  goed\n"
            + "      afgehandeld.  Twee van de drie besturingskarakters uit par. II.5\n"
            + "      van het LO, blz. 321, zijn daarmee reeds aan  de  orde  geweest.\n"
            + "      De  derde, de \"form feed\"  <ff>, zullen we impliciet testen door\n"
            + "      de nu volgende tabellen telkens  op  een  nieuwe  pagina  af  te\n"
            + "      beelden.\n"
            + "\n\u000C"
            + "      In onderstaande  tabel,  ontleend  aan  par. II.3  van  het  LO,\n"
            + "      blz. 371-377, zijn alle nog niet eerder opgesomde TELETEX tekens\n"
            + "      uit  de  linkerkant  van  de  tabel  in  par. II.2  van  het  LO\n"
            + "      opgenomen.\n"
            + NEW_LINE
            + "          +-----------------------------------------------------+\n"
            + "          |      Naam          Graph |      Naam          Graph |\n"
            + "          |--------------------------+--------------------------|\n"
            + "          |  exclamation mark ... !  |  solidus ............ /  |\n"
            + "          |  quotation mark ..... \"  |  colon .............. :  |\n"
            + "          |  percent sign ....... %  |  semicolon .......... ;  |\n"
            + "          |  ampersand .......... &  |  less-than sign ..... <  |\n"
            + "          |  apostrophe ......... '  |  equals sign ........ =  |\n"
            + "          |  left parenthesis ... (  |  greater than sign .. >  |\n"
            + "          |  right parenthesis .. )  |  question mark ...... ?  |\n"
            + "          |  asterisk ........... *  |  commercial at ...... @  |\n"
            + "          |  plus sign .......... +  |  left  square bracket [  |\n"
            + "          |  comma .............. ,  |  right square bracket ]  |\n"
            + "          |  hyphen,minus sign .. -  |  low line ........... _  |\n"
            + "          |  full stop, period .. .  |  vertical bar ....... |  |\n"
            + "          +-----------------------------------------------------+\n"
            + "\n\u000C"
            + "      In de nu volgende tabel, ontleend  aan  par. II.3  van  het  LO,\n"
            + "      blz. 371-373, vindt u alle TELETEX tekens uit de rechterkant van\n"
            + "      de tabel in par. II.2 van het  LO,  uitgezonderd  de  diakrieten\n"
            + "      (kolom C).\n"
            + NEW_LINE
            + "        +----------------------------------------------------------+\n"
            + "        |        Naam          Graph |        Naam           Graph |\n"
            + "        |----------------------------+-----------------------------|\n"
            + "        |  inv.exclamation mark . \u00A1  |  ohm sign .............. \u00E0  |\n"
            + "        |  cent sign ............ \u00A2  |  capital AE diphtong ... \u00E1  |\n"
            + "        |  pound sign ........... \u00A3  |  capital D with stroke . \u00E2  |\n"
            + "        |  dollar sign .......... \u00A4  |  ord.ind.,feminine ..... \u00E3  |\n"
            + "        |  yen sign ............. \u00A5  |  capital H with stroke . \u00E4  |\n"
            + "        |  number sign .......... \u00A6  |                             |\n"
            + "        |  section sign ......... \u00A7  |  capital L with mid.dot  \u00E7  |\n"
            + "        |  currency symbol ...... \u00A8  |  capital L with stroke . \u00E8  |\n"
            + "        |  angle quot.mark left . \u00AB  |  capital O with slash .. \u00E9  |\n"
            + "        |  degree sign .......... \u00B0  |  capital OE ligature ... \u00EA  |\n"
            + "        |  plus/minus sign ...... \u00B1  |  ord.ind.,masculine .... \u00EB  |\n"
            + "        |  superscript 2 ........ \u00B2  |  capital thorn,Icelandic \u00EC  |\n"
            + "        |  superscript 3 ........ \u00B3  |  capital T with stroke . \u00ED  |\n"
            + "        |  multiply sign ........ \u00B4  |  capital eng, Lapp ..... \u00EE  |\n"
            + "        |  micro sign ........... \u00B5  |  small n with apostrophe \u00EF  |\n"
            + "        |  paragraph sign ....... \u00B6  |  small k, Greenlandic .. \u00F0  |\n"
            + "        |  middle dot ........... \u00B7  |  small ae diphtong ..... \u00F1  |\n"
            + "        |  divide sign .......... \u00B8  |  small d with stroke ... \u00F2  |\n"
            + "        |  angle quot.mark right. \u00BB  |  small eth, Icelandic .. \u00F3  |\n"
            + "        |  fraction one quarter . \u00BC  |  small h with stroke ... \u00F4  |\n"
            + "        |  fraction one half .... \u00BD  |  small i without dot ... \u00F5  |\n"
            + "        |  fract.three quarters . \u00BE  |                             |\n"
            + "        |  inverted question mark \u00BF  |  small l with middle dot \u00F7  |\n"
            + "        |                            |  small l with stroke ... \u00F8  |\n"
            + "        |                            |  small o with slash .... \u00F9  |\n"
            + "        |                            |  small oe ligature ..... \u00FA  |\n"
            + "        |                            |  small sharp s, German . \u00FB  |\n"
            + "        |                            |  small thorn,Icelandic . \u00FC  |\n"
            + "        |                            |  small t with stroke ... \u00FD  |\n"
            + "        |                            |  small eng, Lapp ....... \u00FE  |\n"
            + "        +----------------------------------------------------------+\n"
            + "\n\u000C"
            + "      In deze voorlaatste tabel, ontleend aan par. II.4  van  het  LO,\n"
            + "      blz. 374-377,  vindt  u  een opsomming van de verplichte letter-\n"
            + "      diakriet combinaties van A t/m N die moeten worden ondersteund.\n"
            + NEW_LINE
            + "          +-------------------------------------------------+\n"
            + "          |     Naam         H/K   |     Naam         H/K   |\n"
            + "          |------------------------+------------------------|\n"
            + "          |   A acute ...... \u00C2A/\u00C2a |   G acute ......  /\u00C2g  |\n"
            + "          |   A grave ...... \u00C1A/\u00C1a |   G circumflex . \u00C3G/\u00C3g |\n"
            + "          |   A circumflex . \u00C3A/\u00C3a |   G breve ...... \u00C6G/\u00C6g |\n"
            + "          |   A diaeresis .. \u00C8A/\u00C8a |   G dot ........ \u00C7G/\u00C7g |\n"
            + "          |   A tilde ...... \u00C4A/\u00C4a |   G cedilla .... \u00CBG/   |\n"
            + "          |   A breve ...... \u00C6A/\u00C6a |                        |\n"
            + "          |   A ring ....... \u00CAA/\u00CAa |   H circumflex . \u00C3H/\u00C3h |\n"
            + "          |   A macron ..... \u00C5A/\u00C5a |                        |\n"
            + "          |   A ogonek ..... \u00CEA/\u00CEa |   I acute ...... \u00C2I/\u00C2i |\n"
            + "          |                        |   I grave ...... \u00C1I/\u00C1i |\n"
            + "          |   C acute ...... \u00C2C/\u00C2c |   I circumflex . \u00C3I/\u00C3i |\n"
            + "          |   C circumflex . \u00C3C/\u00C3c |   I diaeresis .. \u00C8I/\u00C8i |\n"
            + "          |   C caron ...... \u00CFC/\u00CFc |   I tilde ...... \u00C4I/\u00C4i |\n"
            + "          |   C dot ........ \u00C7C/\u00C7c |   I dot ........ \u00C7I/   |\n"
            + "          |   C cedilla .... \u00CBC/\u00CBc |   I macron ..... \u00C5I/\u00C5i |\n"
            + "          |                        |   I ogonek ..... \u00CEI/\u00CEi |\n"
            + "          |   D caron ...... \u00CFD/\u00CFd |                        |\n"
            + "          |                        |   J circumflex . \u00C3J/\u00C3j |\n"
            + "          |   E acute ...... \u00C2E/\u00C2e |                        |\n"
            + "          |   E grave ...... \u00C1E/\u00C1e |   K cedilla .... \u00CBK/\u00CBk |\n"
            + "          |   E circumflex . \u00C3E/\u00C3e |                        |\n"
            + "          |   E diaeresis .. \u00C8E/\u00C8e |   L acute ...... \u00C2L/\u00C2l |\n"
            + "          |   E caron ...... \u00CFE/\u00CFe |   L caron ...... \u00CFL/\u00CFl |\n"
            + "          |   E dot ........ \u00C7E/\u00C7e |   L cedilla .... \u00CBL/\u00CBl |\n"
            + "          |   E macron ..... \u00C5E/\u00C5e |                        |\n"
            + "          |   E ogonek ..... \u00CEE/\u00CEe |   N acute ...... \u00C2N/\u00C2n |\n"
            + "          |                        |   N tilde ...... \u00C4N/\u00C4n |\n"
            + "          |                        |   N caron ...... \u00CFN/\u00CFn |\n"
            + "          |                        |   N cedilla .... \u00CBN/\u00CBn |\n"
            + "          +-------------------------------------------------+\n"
            + "\n\u000C"
            + "      In deze laatste  tabel,  ontleend  aan  par. II.4  van  het  LO,\n"
            + "      blz. 374-377,  vindt  u  een opsomming van de verplichte letter-\n"
            + "      diakriet combinaties van O t/m Z die moeten worden ondersteund.\n"
            + NEW_LINE
            + "          +-------------------------------------------------+\n"
            + "          |     Naam         H/K   |     Naam         H/K   |\n"
            + "          |------------------------+------------------------|\n"
            + "          |   O acute ...... \u00C2O/\u00C2o |   U acute ...... \u00C2U/\u00C2u |\n"
            + "          |   O grave ...... \u00C1O/\u00C1o |   U grave ...... \u00C1U/\u00C1u |\n"
            + "          |   O circumflex . \u00C3O/\u00C3o |   U circumflex . \u00C3U/\u00C3u |\n"
            + "          |   O diaeresis .. \u00C8O/\u00C8o |   U diaeresis .. \u00C8U/\u00C8u |\n"
            + "          |   O tilde ...... \u00C4O/\u00C4o |   U tilde ...... \u00C4U/\u00C4u |\n"
            + "          |   O double acute \u00CDO/\u00CDo |   U breve ...... \u00C6U/\u00C6u |\n"
            + "          |   O macron ..... \u00C5O/\u00C5o |   U double acute \u00CDU/\u00CDu |\n"
            + "          |                        |   U ring ....... \u00CAU/\u00CAu |\n"
            + "          |   R acute ...... \u00C2R/\u00C2r |   U macron ..... \u00C5U/\u00C5u |\n"
            + "          |   R caron ...... \u00CFR/\u00CFr |   U ogonek ..... \u00CEU/\u00CEu |\n"
            + "          |   R cedilla .... \u00CBR/\u00CBr |                        |\n"
            + "          |                        |   W circumflex . \u00C3W/\u00C3w |\n"
            + "          |   S acute ...... \u00C2S/\u00C2s |                        |\n"
            + "          |   S circumflex . \u00C3S/\u00C3s |   Y acute ...... \u00C2Y/\u00C2y |\n"
            + "          |   S caron ...... \u00CFS/\u00CFs |   Y circumflex . \u00C3Y/\u00C3y |\n"
            + "          |   S cedilla .... \u00CBS/\u00CBs |   Y diaeresis .. \u00C8Y/\u00C8y |\n"
            + "          |                        |                        |\n"
            + "          |   T caron ...... \u00CFT/\u00CFt |   Z acute ...... \u00C2Z/\u00C2z |\n"
            + "          |   T cedilla .... \u00CBT/\u00CBt |   Z caron ...... \u00CFZ/\u00CFz |\n"
            + "          |                        |   Z dot ........ \u00C7Z/\u00C7z |\n"
            + "          +-------------------------------------------------+\n"
            + NEW_LINE
            + "      Indien u alle TELETEX tekens juist hebt kunnen afbeelden, mag u\n"
            + "      dit deel van de test VRY als geslaagd beschouwen.\n"
            + NEW_LINE
            + "             --------------------------------------------------\n";

    @Before
    public void setUp() {
        mailbox = createMailbox();
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
        final String msg = "909" + "0000" + "00000000000" + "012345678901234567890123456789012345678901234567890123456789";
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

                Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                return null;
            }
        }).when(mbsConnection).write(Matchers.any(Message.class));
        Mockito.when(mbsConnection.read()).thenReturn(message);
        // Test logon confirmation with error code

        try {
            mbsProxy.logOn(mailbox);
            Mockito.verify(mbsConnection).read();
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method tests a LogonRequest with an incorrect SSLPassword
     */
    @Test
    public void testConnectRequestWithIncorrectSSLPassword() {
        Mockito.doThrow(new SslPasswordException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD)).when(mbsConnection).connect();
        // Test logon confirmation with error code
        try {
            mbsProxy.connect();
            Assert.fail("Er wordt een SSLPasswordException verwacht");
        } catch (final SslPasswordException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8006]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
                    "0007890910379602051100ZProefomgeving                                               " + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8000]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
                    "0007890910339602051100ZProefomgeving                                               " + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(logonConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.logOn(mailbox);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8004]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.logOff();
            Mockito.verify(mbsConnection).read();
        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht!" + se.getMessage());
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method tests a normal ChangePassWordRequest (and indirectly a normal LogonRequest and LogoffRequest).
     *
     */
    @Test
    public void testChangePasswordRequest() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

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

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.changePassword(mailbox, nwPassword);
            Mockito.verify(mbsConnection).read();
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdUnacceptable() {
        final String nwPassword = FOUTIEF_NIEUW_MAILBOX_PASSWORD;

        try {
            // Test ChangePassword request with changePassword Confirmation (which contains code 1134)
            final String changePasswordConfirmation = "000079191134" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8027]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOldPwdMissing() {
        final String nwPassword = FOUTIEF_NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000079191131" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8024]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOldPwdInvalid() {
        final String nwPassword = FOUTIEF_NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000079191132" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8025]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdMissing() {
        final String nwPassword = FOUTIEF_NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000079191133" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8026]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestDefaultError() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

        // Test ChangePassword request with changePassword Confirmation (which contains code 3434)
        try {
            final String changePasswordConfirmation = "000079193434" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final MailboxServerPasswordException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8028]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNewPwdShort() {
        final String[] nwPasswords = new String[] {"YO", "PWD1S2LONG" };

        for (final String nwPassword : nwPasswords) {
            try {
                final String changePasswordConfirmation = "000079193434" + SpdConstants.TERMINATOR;

                final Message message = new Message();
                message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
                Mockito.when(mbsConnection.read()).thenReturn(message);

                mbsProxy.changePassword(mailbox, nwPassword);
                Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
            } catch (final MailboxServerPasswordException me) {
                Assert.assertTrue(me.getMessage().startsWith("[MELDING-8020]:"));
            } catch (final Exception e) {
                Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
            }
        }
    }

    @Test
    public void testChangePasswordRequestNoBytesRead() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "";

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8021]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestLengthNotMatched() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000089190000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8022]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestOpcodeInvalid() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000079290000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8023]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testChangePasswordRequestNFE() {
        final String nwPassword = NIEUW_MAILBOX_PASSWORD;

        try {
            final String changePasswordConfirmation = "000079A90000" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(changePasswordConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.changePassword(mailbox, nwPassword);
            Assert.fail(MELDING_SERVER_PASSWORD_EXCEPTION);
        } catch (final SpdProtocolException me) {
            Assert.assertTrue(me.getMessage().startsWith("[MELDING-8028]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String recipient = RECIPIENT;
        final String eref = EREF;
        final String submissionTime = SUBMISSION_TIME;
        final Date expectedTime = VoaUtil.convertSpdTimeStringToDate(submissionTime);
        final String berichtInhoud = BERICHT_INHOUD;

        final Bericht bericht = new Bericht();
        bericht.setId(1L);
        // set bericht fields
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setRecipient(recipient);
        bericht.setMessageId(eref);

        try {
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation =
                    LENGTE_39 + OPERATION_CODE_190 + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
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
                    final String msgHeader = "00045" + "150" + eref + "            " + "       " + "001" + recipient + "1";
                    final String msgBody = "00026" + "180" + berichtInhoud;
                    final String expected = putEnvelope + msgHeader + msgBody + SpdConstants.TERMINATOR;

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            mbsProxy.putMessage(bericht);

            Assert.assertEquals("DispatchSequenceNr is niet goed gevuld.", bericht.getDispatchSequenceNumber(), dispatchSequenceNr);
            Assert.assertEquals(
                "ExpectedTime is incorrect aan bericht.getTijdstipVerzendingOntvangst",
                expectedTime.toString(),
                bericht.getTijdstipMailbox().toString());
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId("AA0123456789");
        bericht.setRecipient(RECIPIENT);
        try {
            final Message message = new Message();
            message.setMessageFromMailbox(new byte[0]);
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8090]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.fail("Er wordt geen " + e.getCause() + " verwacht! " + e.getMessage());
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
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId("AA0123456789");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8095]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId("AA0123456789");
        bericht.setRecipient("1");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8095]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId(null);
        bericht.setRecipient("1901010");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8094]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId("");
        bericht.setRecipient("1901010");
        try {
            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8094]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     *
     */
    @Test
    public void testPutMessageInvalidLength() {
        // init
        final Integer dispatchSequenceNr = 123456789;
        final String recipient = RECIPIENT;
        final String eref = EREF;
        final String submissionTime = SUBMISSION_TIME;
        final String berichtInhoud = BERICHT_INHOUD;

        final Bericht bericht = new Bericht();
        bericht.setId(1L);
        // set bericht fields
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setRecipient(recipient);
        bericht.setMessageId(eref);
        try {
            final String putMessageConfirmation =
                    "00040" + OPERATION_CODE_190 + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8091]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String submissionTime = SUBMISSION_TIME;

        // set bericht fields
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId("AA0123456789");
        bericht.setRecipient(RECIPIENT);
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation =
                    LENGTE_39 + OPERATION_CODE_190 + "1051" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8093]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String submissionTime = SUBMISSION_TIME;

        // set bericht fields
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId(eref.replace('9', '8'));
        bericht.setRecipient(RECIPIENT);
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation =
                    LENGTE_39 + OPERATION_CODE_190 + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8097]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String submissionTime = SUBMISSION_TIME;

        // set bericht fields
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId(eref);
        bericht.setRecipient(RECIPIENT);
        try {
            // Errorcode 1051 Geadresseerde verkeerd opgegeven.
            final String putMessageConfirmation = LENGTE_39 + "1A0" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8096]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String submissionTime = SUBMISSION_TIME;

        final Bericht bericht = new Bericht();
        // set bericht fields
        bericht.setId(1L);
        bericht.setBerichtInhoud(BERICHT_INHOUD);
        bericht.setMessageId(eref);
        bericht.setRecipient(RECIPIENT);

        try {
            // Test putMessageConfirmation with incorrect operationcode (192 ipv 190)
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation = LENGTE_39 + "192" + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);
            Assert.fail("Er wordt een SpdProtocolException verwacht!");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8092]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String eref = EREF;
        final String submissionTime = SUBMISSION_TIME;
        final String berichtInhoud = "00000000Vb01" + VRIJ_BERICHT_TELETEX_TEKENS.replace('\n', 'n');
        final int originalLength = berichtInhoud.length();

        // create outgoing bericht with TeleTex characters
        final Bericht bericht = new Bericht();
        bericht.setId(1L);
        bericht.setBerichtInhoud(berichtInhoud);
        bericht.setMessageId(eref);
        bericht.setRecipient(RECIPIENT);

        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                final int offsetVb01 = 16;
                final int startVb01Inhoud = 8;
                final Object[] args = invocation.getArguments();
                final Message mesg = (Message) args[0];
                final String msgString = mesg.getMessage();

                final String vb01 = msgString.substring(msgString.indexOf("Vb01") - offsetVb01);
                int verzondenLength = Integer.valueOf(vb01.substring(0, 5));
                verzondenLength -= 3; // minus the length of the operationcode (180)
                Assert.assertEquals("Bericht-lengtes zijn niet gelijk", originalLength, verzondenLength);
                Assert.assertEquals(
                    "Berichten zijn niet gelijk",
                    berichtInhoud,
                    vb01.substring(startVb01Inhoud, vb01.length() - SpdConstants.TERMINATOR.length()));
                return null;
            }

        })
               .when(mbsConnection)
               .write(Matchers.any(Message.class));

        // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
        // MessageId[12]
        final String putMessageConfirmation =
                LENGTE_39 + OPERATION_CODE_190 + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
        final Message message = new Message();
        message.setMessageFromMailbox(putMessageConfirmation.getBytes());
        Mockito.when(mbsConnection.read()).thenReturn(message);

        // test
        try {
            mbsProxy.putMessage(bericht);
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
        final String eref = EREF;
        final String submissionTime = SUBMISSION_TIME;
        final Date expectedTime = VoaUtil.convertSpdTimeStringToDate(submissionTime);

        final Bericht bericht = new Bericht();
        final String vb01TeleTex = "00000000Vb01" + VRIJ_BERICHT_TELETEX_TEKENS.replace('\n', 'n') + "00000";

        // set bericht fields
        bericht.setId(1L);
        bericht.setBerichtInhoud(vb01TeleTex);
        bericht.setMessageId(eref);
        bericht.setRecipient(RECIPIENT);

        try {
            // Length[5] + Operationcode[3] + PutResult[4] + DispatchSequenceNumber[9] + SubmissionTime[11] +
            // MessageId[12]
            final String putMessageConfirmation =
                    LENGTE_39 + OPERATION_CODE_190 + "0000" + dispatchSequenceNr + submissionTime + eref + SpdConstants.TERMINATOR;
            final Message message = new Message();
            message.setMessageFromMailbox(putMessageConfirmation.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.putMessage(bericht);

            Assert.assertEquals("DispatchSequenceNr is niet goed gevuld.", bericht.getDispatchSequenceNumber(), dispatchSequenceNr);
            // check tijdstip verzending ontvangst
            Assert.assertEquals(
                "ExpectedTime is incorrect aan bericht.getTijdstipVerzendingOntvangst",
                expectedTime.toString(),
                bericht.getTijdstipMailbox().toString());
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a ListMessagesResult with 10
     * MSSequenceNrs.
     */
    @Test
    public void testListMessages() {
        final List<Integer> seqNrs = new ArrayList<>();
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
            final String listResult = LENGTE_12 + "410" + "000000001";
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
                    final String expected = "00028" + "400" + "171" + "012" + " " + "000000000" + "         " + SpdConstants.TERMINATOR;

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);

            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 1. Nl: " + nextSequenceNr, nextSequenceNr == 1);
            Assert.assertTrue("List.size is ongelijk aan 10. Nl: " + seqNrs.size(), seqNrs.size() == 10);
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            final List<Integer> seqNrs = new ArrayList<>();
            final int nextSequenceNr = mbsProxy.listMessages(0, seqNrs, MAX_LIST_LIMIT_NR, MSSTATUS, PRIORITY);

            // Test
            Assert.assertTrue("NextSequenceNr is ongelijk aan 387. Nl: " + nextSequenceNr, nextSequenceNr == 387);
            Assert.assertTrue("List.size is ongelijk aan 170. Nl: " + seqNrs.size(), seqNrs.size() == 170);
            Assert.assertTrue("Het laatste item in de List is niet gelijk aan 386. Nl: " + seqNrs.get(169), seqNrs.get(169).equals(Integer.valueOf(386)));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a Confirmation with the code
     * 1113 (which means: no (new) messages on the MBS)
     *
     */
    @Test
    public void testListMessagesNoNewMessagesConfirmation() {
        final List<Integer> seqNrs = new ArrayList<>();
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method tests a normal ListMessages request. The MailboxServer simulator returns a Confirmation with an error
     * code 1114.
     *
     */
    @Test
    public void testListMessagesWithConfirmationError() {
        final List<Integer> seqNrs = new ArrayList<>();
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
                    final String expected = "00028" + "400" + "040" + "012" + " " + "000000000" + "         " + SpdConstants.TERMINATOR;

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), ZERO_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
                    final String expected = "00028" + "400" + "171" + "012" + " " + "000000000" + "         " + SpdConstants.TERMINATOR;

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            // execute
            mbsProxy.listMessages(0, new ArrayList<Integer>(), TOO_MUCH_LIST_LIMIT_NR, MSSTATUS, PRIORITY);
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            getMessageResult.append(LENGTE_12).append("210").append(msSequenceNumber);
            // GetEnvelope: Length[5] + Operationcode[3] + OriginatorORName[7] + Contenttype[1] + Priority[1] +
            // DeliveryTime[11] + SubmissionTime[11] + ActualRecipientORName[7]
            getMessageResult.append("00041")
                            .append("220")
                            .append(originator)
                            .append("2")
                            .append("1")
                            .append(deliveryTime)
                            .append("0408121045Z")
                            .append(recipient);
            // GetMessageHeading: Length[5] + Operationcode[3] + MessasgeId[12] + CrossReference[12] +
            // OriginatorUsername[7] + ActualRecipientORName[7] + ActualNotificationRequest[1]
            getMessageResult.append("00042").append("250").append(bref).append(eref).append("1234567").append("1234567").append("0");
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
                    final String expected = LENGTE_12 + "200" + msSequenceNumber + SpdConstants.TERMINATOR;

                    Assert.assertEquals(BERICHT_IS_NIET_GOED, expected, msgString);
                    return null;
                }
            }).when(mbsConnection).write(Matchers.any(Message.class));

            bericht = mbsProxy.getMessage(msSequenceNumber);
            Assert.assertTrue(
                "Het MSSequenceNumber uit het bericht is niet gelijk aan het SequenceNumber uit de ListResult",
                bericht.getDispatchSequenceNumber().equals(msSequenceNumber));
            // MessageHeader
            Assert.assertNotNull(bericht.getBerichtInhoud());
            Assert.assertEquals(bref, bericht.getMessageId());
            Assert.assertEquals(eref, bericht.getCorrelationId());
            // MessageEnvelope
            Assert.assertEquals(originator, bericht.getOriginator());
            Assert.assertEquals(expectedTime.toString(), bericht.getTijdstipMailbox().toString());
            Assert.assertEquals(recipient, bericht.getRecipient());
            // MessageBody
            Assert.assertEquals(inhoud, bericht.getBerichtInhoud());

        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.assertEquals("DispatchSeqNr is ongelijk aan: 11950", Integer.valueOf(11950), bericht.getDispatchSequenceNumber());
            // (GetEnvelope) originator_or_recipient
            Assert.assertEquals("Originator is ongelijk aan: 1901900", "1901900", bericht.getOriginator());
            // (GetEnvelope) tijdstip_verzending_ontvangst
            final Date expectedDeliveryTime = VoaUtil.convertSpdTimeStringToDate("0408290918Z");
            Assert.assertEquals(
                "Tijdstip_verzending_ontvangst is ongelijk aan: 2004-08-29 09:18",
                expectedDeliveryTime.toString(),
                bericht.getTijdstipMailbox().toString());
            // (MessageHeading) MessageId

            Assert.assertEquals("MessageId is ongelijk aan: ANTWOORD", bericht.getMessageId(), "ANTWOORD    ");
            // (MessageHeading) CrossReference
            Assert.assertEquals("CrossReference is ongelijk aan: YY000000001", bericht.getCorrelationId(), "YY000000001 ");
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageNullBerichtEref() {
        final String[] erefs = new String[] {"            ", "000000000000", "0           ", "           0" };
        for (final String eref : erefs) {
            try {
                final String nullBericht =
                        "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    "
                                + eref
                                + "19019001901900000003280"
                                + SpdConstants.TERMINATOR;

                final Message message = new Message();
                message.setMessageFromMailbox(nullBericht.getBytes());
                Mockito.when(mbsConnection.read()).thenReturn(message);
                final Bericht bericht = mbsProxy.getMessage(123);

                // (MessageHeading) CrossReference
                Assert.assertNull("CrossReference is niet null", bericht.getCorrelationId());
            } catch (final Exception e) {
                Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageMessageBodyNullBytes() {
        try {
            final String nullBericht =
                    "00012210000011950000412201901900210408290918Z0408290918Z183101000042250ANTWOORD    YY000000001 19019001901900000006280B"
                            + '\u0000'
                            + "S"
                            + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(nullBericht.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);
            mbsProxy.getMessage(123);
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8046]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            final String deliveryReport = "00012210000011950000482600408231236Z0812002000011234567           0001" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            final Bericht delRep = mbsProxy.getMessage(123);

            // dispatch_sequence_number
            Assert.assertEquals("DispatchSeqNr is ongelijk aan: 81200200", delRep.getDispatchSequenceNumber(), Integer.valueOf(81200200));
            // report_delivery_time
            final Date expectedDelRepTime = VoaUtil.convertSpdTimeStringToDate("0408231236Z");
            Assert.assertEquals("Report delivery time is ongelijk aan: 2004-08-23 12:36", expectedDelRepTime.toString(), delRep.getTijdstipMailbox()
                .toString());
            // originator_or_recipient
            Assert.assertEquals("Originator is ongelijk aan: 1234567", "1234567", delRep.getOriginator());
            // non_delivery_reason
            Assert.assertEquals("NonDeliveryReason is ongelijk aan: 0001", "0001", delRep.getNonDeliveryReason());

        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht! " + se.getMessage());
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportWrongLength() {
        // Test Delivery Report
        try {
            final String deliveryReport = "00012210000011950000492600408231236Z0812002000011234567           0001" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8050]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportMoreThenOneRecipient() {
        // Test Delivery Report
        try {
            final String deliveryReport =
                    "00012210000011950000482600408231236Z0812002000021234567           00011234567           0001" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8052]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageDeliveryReportRecipientNFE() {
        // Test Delivery Report
        try {
            final String deliveryReport = "00012210000011950000482600408231236Z08120020000a1234567           0001" + SpdConstants.TERMINATOR;

            final Message message = new Message();
            message.setMessageFromMailbox(deliveryReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    /**
     * This method test a getMessage with a normal StatusReport as result
     */
    @Test
    public void testGetMessageStatusReport() {
        // Test Status Report
        try {
            final String statusReport = "00012210000011950000412201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            final Bericht statRep = mbsProxy.getMessage(123);

            // (MSEntry) dispatch_sequence_number
            Assert.assertEquals("DispatchSeqNr: ", Integer.valueOf(11950), statRep.getDispatchSequenceNumber());
            // (GetEnvelope) tijdstip_verzending_ontvangst
            final Date expectedDeliveryTime = VoaUtil.convertSpdTimeStringToDate("0408121045Z");
            Assert.assertEquals("Tijdstip_verzending_ontvangst", expectedDeliveryTime.toString(), statRep.getTijdstipMailbox().toString());
            // (GetEnvelope) originator_or_recipient
            Assert.assertEquals("Originator", "1831010", statRep.getOriginator());
            // non_receipt_reason
            Assert.assertEquals("NonReceiptReason", "1", statRep.getNotificationType());
        } catch (final SpdProtocolException se) {
            Assert.fail("Er wordt geen SpdProtocolException verwacht! " + se.getMessage());
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportWrongLengthEnvelope() {
        // Test Status Report
        try {
            final String statusReport = "00012210000011950000402201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8060]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportLengthEnvelopeNFE() {
        // Test Status Report
        try {
            final String statusReport = "000122100000119500004a2201831010210408121045Z0408121045Z18310100002427018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportWrongLength() {
        // Test Status Report
        try {
            final String statusReport = "00012210000011950000412201831010210408121045Z0408121045Z18310100002327018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8070]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }

    @Test
    public void testGetMessageStatusReportLengthNFE() {
        // Test Status Report
        try {
            final String statusReport = "00012210000011950000412201831010210408121045Z0408121045Z18310100002a27018310101YY000000004 100000";

            final Message message = new Message();
            message.setMessageFromMailbox(statusReport.getBytes());
            Mockito.when(mbsConnection.read()).thenReturn(message);

            mbsProxy.getMessage(123);
            Assert.fail("Er wordt een SpdProtocolException verwacht");
        } catch (final SpdProtocolException se) {
            Assert.assertTrue(se.getMessage().startsWith("[MELDING-8098]:"));
        } catch (final Exception e) {
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
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
            getMessageResult.append(LENGTE_12).append("210").append(msSequenceNumber);
            // GetEnvelope: Length[5] + Operationcode[3] + OriginatorORName[7] + Contenttype[1] + Priority[1] +
            // DeliveryTime[11] + SubmissionTime[11] + ActualRecipientORName[7]
            getMessageResult.append("00041")
                            .append("220")
                            .append(originator)
                            .append("2")
                            .append("1")
                            .append(deliveryTime)
                            .append("0408121045Z")
                            .append(recipient);
            // GetMessageHeading: Length[5] + Operationcode[3] + MessasgeId[12] + CrossReference[12] +
            // OriginatorUsername[7] + ActualRecipientORName[7] + ActualNotificationRequest[1]
            getMessageResult.append("00042").append("250").append(bref).append(eref).append("1234567").append("1234567").append("0");
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
            Assert.fail(MELDING_GEEN_EXCEPTION_VERWACHT + e.getMessage());
        }
    }
}
