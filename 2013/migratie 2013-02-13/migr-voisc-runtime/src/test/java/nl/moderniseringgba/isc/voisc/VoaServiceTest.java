/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;
import nl.moderniseringgba.isc.voisc.exceptions.ConnectionException;
import nl.moderniseringgba.isc.voisc.exceptions.MailboxServerPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.SpdProtocolException;
import nl.moderniseringgba.isc.voisc.exceptions.SslPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.exceptions.VoaRuntimeException;
import nl.moderniseringgba.isc.voisc.jms.QueueService;
import nl.moderniseringgba.isc.voisc.mailbox.MailboxServerProxy;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jms.MessageEOFException;

// CHECKSTYLE:OFF Tests, we vangen Exception en Throwable expres af.
@RunWith(MockitoJUnitRunner.class)
public class VoaServiceTest {

    @Mock
    private VoiscDbProxy voiscDbProxy;

    @Mock
    private MailboxServerProxy mailboxServerProxy;

    @Mock
    private QueueService queueService;

    @InjectMocks
    private VoaServiceImpl voaService;

    private LogboekRegel regel;

    @Before
    public void setUp() {
        regel = new LogboekRegel();
    }

    @Test
    public void connectToMailboxServerSuccesTest() {
        try {
            voaService.connectToMailboxServer();

        } catch (final Exception e) {
            fail("Connect naar de mailbox is mislukt.");
        }
    }

    @Test
    public void connectToMailboxServerFailGeneralSecurityTest() {
        boolean exceptionThrown = false;
        try {
            Mockito.doThrow(
                    new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null,
                            new GeneralSecurityException())).when(mailboxServerProxy).connect();
            voaService.connectToMailboxServer();

        } catch (final ConnectionException e) {
            exceptionThrown = true;
        }
        assertTrue("Er is geen exception gegooid. Dit moet wel.", exceptionThrown);
    }

    @Test
    public void connectToMailboxServerFailUnrecoverableKeyTest() {
        boolean exceptionThrown = false;
        try {
            Mockito.doThrow(new SslPasswordException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD))
                    .when(mailboxServerProxy).connect();

            voaService.connectToMailboxServer();

        } catch (final SslPasswordException e) {
            exceptionThrown = true;
        }
        assertTrue("Er is geen exception gegooid. Dit moet wel.", exceptionThrown);
    }

    @Test
    public void loginMailboxSuccesTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setLaatsteWijzigingPwd(new Date());
        try {
            voaService.login(regel, mailbox);
            Mockito.verify(mailboxServerProxy, Mockito.times(1)).logOn(Matchers.any(Mailbox.class));
        } catch (final VoaException e) {
            fail("Er zou geen exception op moeten treden.");
        }
    }

    @Test
    public void loginMailboxFailMailboxServerPasswordTest() {

        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxnr("12034");
        mailbox.setMailboxpwd("");
        mailbox.setLaatsteWijzigingPwd(new Date());

        try {
            Mockito.doThrow(new MailboxServerPasswordException("8004")).when(mailboxServerProxy).logOn(mailbox);

            voaService.login(regel, mailbox);
            Assert.assertTrue(regel.hasFoutmelding());
        } catch (final VoaException e) {
            Assert.fail();
        }
    }

    @Test
    public void loginChangePassword() {
        final Calendar vorigeWijziging = Calendar.getInstance();
        vorigeWijziging.add(Calendar.DATE, -90);
        final Date vorigeWijzigingDt = vorigeWijziging.getTime();
        final String vorigePwd = "12034";
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd(vorigePwd);
        mailbox.setMailboxnr("12345");
        mailbox.setLaatsteWijzigingPwd(vorigeWijzigingDt);

        try {
            voaService.login(regel, mailbox);
            Assert.assertNotSame(vorigePwd, mailbox.getMailboxpwd());
            Assert.assertNotSame(vorigeWijzigingDt, mailbox.getLaatsteWijzigingPwd());
            Mockito.verify(mailboxServerProxy, Mockito.times(1)).logOn(Matchers.any(Mailbox.class));
            Mockito.verify(mailboxServerProxy, Mockito.times(1)).changePassword(Matchers.any(Mailbox.class),
                    Matchers.anyString());
            Mockito.verify(voiscDbProxy, Mockito.times(1)).saveMailbox(mailbox);
        } catch (final VoaException e) {
            Assert.fail("Geen exceptie verwacht");
        }
    }

    @Test
    public void loginChangePasswordException() {
        final Calendar vorigeWijziging = Calendar.getInstance();
        vorigeWijziging.add(Calendar.DATE, -90);
        final Date vorigeWijzigingDt = vorigeWijziging.getTime();
        final String vorigePwd = "12034";
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd(vorigePwd);
        mailbox.setMailboxnr("12345");
        mailbox.setLaatsteWijzigingPwd(vorigeWijzigingDt);

        try {
            Mockito.doThrow(new MailboxServerPasswordException("8027")).when(mailboxServerProxy)
                    .changePassword(Matchers.any(Mailbox.class), Matchers.anyString());
            voaService.login(regel, mailbox);
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8115]:"));
        }
        Assert.assertSame(vorigePwd, mailbox.getMailboxpwd());
        Assert.assertSame(vorigeWijzigingDt, mailbox.getLaatsteWijzigingPwd());
        try {
            Mockito.verify(mailboxServerProxy, Mockito.times(1)).logOn(Matchers.any(Mailbox.class));
            Mockito.verify(mailboxServerProxy, Mockito.times(1)).changePassword(Matchers.any(Mailbox.class),
                    Matchers.anyString());
        } catch (final VoaException e) {
            fail("Geen VoaException verwacht bij verify");
        }
    }

    @Test
    public void loginMailboxFailSSLPasswordTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setLaatsteWijzigingPwd(new Date());

        try {
            Mockito.doThrow(new SslPasswordException("8006")).when(mailboxServerProxy).logOn(mailbox);

            voaService.login(regel, mailbox);
            Assert.assertTrue(regel.hasFoutmelding());
        } catch (final VoaException e) {
            Assert.fail();
        }
    }

    @Test
    public void loginMailboxFailSpdProtocolTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setLaatsteWijzigingPwd(new Date());

        try {
            Mockito.doThrow(new SpdProtocolException("8003")).when(mailboxServerProxy).logOn(mailbox);

            voaService.login(regel, mailbox);
            Assert.assertTrue(regel.hasFoutmelding());
        } catch (final VoaException e) {
            Assert.fail();
        }
    }

    @Test
    public void logoutFailIgnoreTest() {
        try {
            Mockito.doThrow(new SpdProtocolException("8007")).when(mailboxServerProxy).logOff();
            voaService.logout();
        } catch (final SpdProtocolException e) {
            fail("De exception wordt gelogt maar er zouden geen fouten gegooid moeten worden.");
        }
    }

    @Test
    public void logoutSuccesTest() {
        try {
            voaService.logout();
        } catch (final Exception e) {
            fail("Er mag geen fout optreden.");
        }
    }

    @Test
    public void sendMessagesToMailboxSuccesTest() {
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        messages.add(bericht);

        Mockito.when(voiscDbProxy.getMailboxByNummer(Matchers.anyString())).thenReturn(createMailbox());
        try {
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            Mockito.verify(voiscDbProxy, Mockito.times(0)).sendEsbErrorBericht(Matchers.any(Bericht.class),
                    Matchers.anyString());
        } catch (final VoaException e) {
            fail("Er mag geen fout optreden bij het verzenden.");
        }
    }

    @Test
    public void sendMessagesToMailboxMissingTest() {
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        messages.add(bericht);

        Mockito.when(voiscDbProxy.getMailboxByNummer(Matchers.anyString())).thenReturn(null);
        try {
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            Mockito.verify(voiscDbProxy).sendEsbErrorBericht(Matchers.any(Bericht.class), Matchers.anyString());
        } catch (final VoaException e) {
            fail("Er mag geen fout optreden bij het verzenden.");
        }
    }

    @Test
    public void sendMessagesToMailboxSpdProtocolFailTest() {
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        bericht.setId(0l);
        messages.add(bericht);

        try {
            // 1051 Geadresseerde verkeerd opgegeven
            Mockito.doThrow(new SpdProtocolException("8095")).when(mailboxServerProxy).putMessage(bericht);
            Mockito.when(voiscDbProxy.getMailboxByNummer(bericht.getRecipient())).thenReturn(createMailbox());
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            Mockito.verify(voiscDbProxy, Mockito.times(0)).sendEsbErrorBericht(Matchers.any(Bericht.class),
                    Matchers.anyString());
        } catch (final VoaException e) {
            fail("Er mag geen exceptie gegooid worden.");
        }
    }

    @Test
    public void sendMessagesToMailboxSpdProtocolFailKnownMSSequenceId() {
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        bericht.setId(0l);
        bericht.setDispatchSequenceNumber(1);
        messages.add(bericht);

        try {
            // 1051 Geadresseerde verkeerd opgegeven
            Mockito.doThrow(new SpdProtocolException("8095")).when(mailboxServerProxy).putMessage(bericht);
            Mockito.when(voiscDbProxy.getMailboxByNummer(bericht.getRecipient())).thenReturn(createMailbox());
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            Mockito.verify(voiscDbProxy, Mockito.times(0)).sendEsbErrorBericht(Matchers.any(Bericht.class),
                    Matchers.anyString());
        } catch (final VoaException e) {
            fail("Er mag geen exceptie gegooid worden.");
        }
    }

    @Test
    public void sendMessagesToMailboxFailBerichtSaveTest() {
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        messages.add(bericht);

        try {
            Mockito.doThrow(new VoaException("8001", null)).when(voiscDbProxy).saveBericht(bericht, false, false);
            Mockito.when(voiscDbProxy.getMailboxByNummer(bericht.getRecipient())).thenReturn(createMailbox());
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            Mockito.verify(voiscDbProxy, Mockito.times(0)).sendEsbErrorBericht(Matchers.any(Bericht.class),
                    Matchers.anyString());
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            assertNotNull("Er zou een VoaException gegooid moeten worden.", e);
        }
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxSuccesTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904010");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(2);

        try {
            Mockito.when(
                    mailboxServerProxy.listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            @SuppressWarnings("unchecked")
                            final List<Integer> seqNumbers = (List<Integer>) invocation.getArguments()[1];
                            seqNumbers.add(1);
                            seqNumbers.add(2);
                            return 1;
                        }
                    });

            Mockito.when(
                    mailboxServerProxy.listMessages(1, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            return 0;
                        }
                    });

            final Bericht bericht = new Bericht();
            final String lo3BerichtInhoud =
                    "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
            bericht.setBerichtInhoud(lo3BerichtInhoud);
            bericht.setRecipient("1904010");
            bericht.setEref2("eref2");
            final String esbMesgId = "esbMesgId";
            final Bericht eerderBericht = new Bericht();
            eerderBericht.setEsbMessageId(esbMesgId);
            Mockito.when(mailboxServerProxy.getMessage(1)).thenReturn(bericht);
            Mockito.when(voiscDbProxy.getBerichtByEref("eref2")).thenReturn(eerderBericht);

            final LogboekRegel logboekRegel = new LogboekRegel();

            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);

            assertTrue("Er moet 1 bericht goed ontvangen zijn (seqnr 1).", logboekRegel.getAantalOntvangenOK() == 1);
            assertTrue("Er moet 1 bericht niet goed ontvangen zijn (seqnr 1).",
                    logboekRegel.getAantalOntvangenNOK() == 1);
            assertEquals(esbMesgId, bericht.getEsbCorrelationId());
        } catch (final Exception e) {
            fail("Er wordt geen Exception verwacht");
        }
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxDeliveryRepSuccesTest() {
        boolean exceptionThrown = false;
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            Mockito.when(
                    mailboxServerProxy.listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            @SuppressWarnings("unchecked")
                            final List<Integer> seqNumbers = (List<Integer>) invocation.getArguments()[1];
                            seqNumbers.add(1);
                            return 0;
                        }
                    });

            final Bericht eerderBericht = new Bericht();
            final String lo3BerichtInhoud =
                    "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
            eerderBericht.setBerichtInhoud(lo3BerichtInhoud);
            eerderBericht.setRecipient("1904");

            final Bericht bericht = new Bericht();
            bericht.setBerichtInhoud(lo3BerichtInhoud);
            bericht.setRecipient("1904010");
            bericht.setNonDeliveryReason("nonDelReason");
            bericht.setDispatchSequenceNumber(1);
            bericht.setEref2("");
            Mockito.when(mailboxServerProxy.getMessage(1)).thenReturn(bericht);
            Mockito.when(voiscDbProxy.getBerichtByDispatchSequenceNumber(Matchers.anyInt()))
                    .thenReturn(eerderBericht);

            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Bericht bericht = (Bericht) invocation.getArguments()[0];
                    if (bericht.equals(eerderBericht)) {
                        assertTrue("Bericht heeft geen report gegevens opgeslagen", bericht.isReport());
                    } else {
                        assertFalse("Bericht heeft report gegevens opgeslagen", bericht.isReport());
                        assertEquals(Bericht.AANDUIDING_IN_UIT_UIT, bericht.getAanduidingInUit());
                        assertEquals(eerderBericht.getBerichtInhoud(), bericht.getBerichtInhoud());
                        assertEquals(eerderBericht.getBref(), bericht.getBref());
                        assertEquals(eerderBericht.getEref(), bericht.getEref());
                        assertEquals(eerderBericht.getEsbCorrelationId(), bericht.getEsbCorrelationId());
                        assertEquals(eerderBericht.getEsbMessageId(), bericht.getEsbMessageId());
                        assertEquals(eerderBericht.getOriginator(), bericht.getOriginator());
                        assertEquals(eerderBericht.getRecipient(), bericht.getRecipient());
                    }
                    return bericht;
                }
            }).when(voiscDbProxy)
                    .saveBericht(Matchers.any(Bericht.class), Matchers.anyBoolean(), Matchers.anyBoolean());

            final LogboekRegel logboekRegel = new LogboekRegel();
            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);
            assertTrue(eerderBericht.getNonDeliveryReason().equals("nonDelReason"));
            assertTrue("Er moet 1 bericht goed ontvangen zijn (seqnr 1).", logboekRegel.getAantalOntvangenOK() == 1);
            Mockito.verify(voiscDbProxy, Mockito.times(2)).saveBericht(Matchers.any(Bericht.class),
                    Matchers.anyBoolean(), Matchers.anyBoolean());
        } catch (final Exception e) {
            exceptionThrown = true;
        }
        assertFalse("Er zou geen exception gegooid moeten worden.", exceptionThrown);
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxStatusRepSuccesTest() {
        boolean exceptionThrown = false;
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            Mockito.when(
                    mailboxServerProxy.listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            @SuppressWarnings("unchecked")
                            final List<Integer> seqNumbers = (List<Integer>) invocation.getArguments()[1];
                            seqNumbers.add(1);
                            return 0;
                        }
                    });

            final Bericht eerderBericht = new Bericht();
            final String lo3BerichtInhoud =
                    "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
            eerderBericht.setBerichtInhoud(lo3BerichtInhoud);
            eerderBericht.setRecipient("1904");

            final Bericht bericht = new Bericht();
            bericht.setBerichtInhoud(lo3BerichtInhoud);
            bericht.setRecipient("1904010");
            bericht.setNonReceiptReason("nonReceiptReason");
            bericht.setDispatchSequenceNumber(1);
            bericht.setEref2("1234");
            Mockito.when(mailboxServerProxy.getMessage(1)).thenReturn(bericht);
            // Mockito.when(voiscDbProxy.getBerichtByEref(Matchers.anyString())).thenReturn(null);
            Mockito.when(voiscDbProxy.getBerichtByEref(Matchers.anyString())).thenReturn(eerderBericht);

            Mockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    final Bericht bericht = (Bericht) invocation.getArguments()[0];
                    if (bericht.equals(eerderBericht)) {
                        assertTrue("Bericht heeft geen report gegevens opgeslagen", bericht.isReport());
                    } else {
                        assertFalse("Bericht heeft report gegevens opgeslagen", bericht.isReport());
                        assertEquals(Bericht.AANDUIDING_IN_UIT_UIT, bericht.getAanduidingInUit());
                        assertEquals(eerderBericht.getBerichtInhoud(), bericht.getBerichtInhoud());
                        assertEquals(eerderBericht.getBref(), bericht.getBref());
                        assertEquals(eerderBericht.getEref(), bericht.getEref());
                        assertEquals(eerderBericht.getEsbCorrelationId(), bericht.getEsbCorrelationId());
                        assertEquals(eerderBericht.getEsbMessageId(), bericht.getEsbMessageId());
                        assertEquals(eerderBericht.getOriginator(), bericht.getOriginator());
                        assertEquals(eerderBericht.getRecipient(), bericht.getRecipient());
                    }
                    return bericht;
                }
            }).when(voiscDbProxy)
                    .saveBericht(Matchers.any(Bericht.class), Matchers.anyBoolean(), Matchers.anyBoolean());

            final LogboekRegel logboekRegel = new LogboekRegel();
            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);
            assertEquals("nonReceiptReason", eerderBericht.getNonReceiptReason());
            assertTrue("Er moet 1 bericht goed ontvangen zijn (seqnr 1).", logboekRegel.getAantalOntvangenOK() == 1);
            Mockito.verify(voiscDbProxy, Mockito.times(2)).saveBericht(Matchers.any(Bericht.class),
                    Matchers.anyBoolean(), Matchers.anyBoolean());
        } catch (final Exception e) {
            exceptionThrown = true;
        }
        assertFalse("Er zou geen exception gegooid moeten worden.", exceptionThrown);
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxListMessagesProtocolFoutTest() {
        boolean exceptionThrown = false;
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            Mockito.doThrow(new SpdProtocolException("1111"))
                    .when(mailboxServerProxy)
                    .listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()), mailbox.getLimitNumber(),
                            SpdConstants.MSSTATUS, SpdConstants.PRIORITY);

            voaService.receiveAndStoreMessagesFromMailbox(mailbox, new LogboekRegel());
        } catch (final VoaException e) {
            exceptionThrown = true;
        }
        assertTrue("Er zou een protocol exception gegooid moeten worden.", exceptionThrown);
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxReceiveFailTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            Mockito.when(
                    mailboxServerProxy.listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            @SuppressWarnings("unchecked")
                            final List<Integer> seqNumbers = (List<Integer>) invocation.getArguments()[1];
                            seqNumbers.add(1);
                            seqNumbers.add(0);
                            return 0;
                        }
                    });
            Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE))
                    .when(mailboxServerProxy).getMessage(1);
            Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE))
                    .when(mailboxServerProxy).getMessage(0);

            final LogboekRegel logboekRegel = new LogboekRegel();
            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);
            assertTrue("Er zou geen ontvangen bericht moeten zijn.", logboekRegel.getAantalOntvangenOK() == 0
                    && logboekRegel.getAantalOntvangenNOK() == 2);
        } catch (final VoaException e) {
            fail("Geen VoaException verwacht");
        }
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxReceiveVoaException() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        final LogboekRegel logboekRegel = new LogboekRegel();
        try {
            Mockito.when(
                    mailboxServerProxy.listMessages(0, new ArrayList<Integer>(mailbox.getLimitNumber()),
                            mailbox.getLimitNumber(), SpdConstants.MSSTATUS, SpdConstants.PRIORITY)).thenAnswer(
                    new Answer<Object>() {

                        @Override
                        public Object answer(final InvocationOnMock invocation) throws Throwable {
                            @SuppressWarnings("unchecked")
                            final List<Integer> seqNumbers = (List<Integer>) invocation.getArguments()[1];
                            seqNumbers.add(0);
                            return 0;
                        }
                    });

            Mockito.when(mailboxServerProxy.getMessage(0)).thenReturn(new Bericht());

            Mockito.doThrow(new VoaException(MessagesCodes.ERRMSG_VOSPG_DB_CONNECTION_FAILED, null))
                    .when(voiscDbProxy)
                    .saveBericht(Matchers.any(Bericht.class), Matchers.anyBoolean(), Matchers.anyBoolean());

            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);
        } catch (final VoaException e) {
            assertTrue("Er zou geen ontvangen bericht moeten zijn.", logboekRegel.getAantalOntvangenOK() == 0
                    && logboekRegel.getAantalOntvangenNOK() == 1);
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8109]:"));
        }
    }

    @Test
    public void receiveAndStoreMessagesFromMailboxTest() {
        boolean exceptionThrown = false;
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE))
                    .when(mailboxServerProxy).getMessage(1);
            Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_RCV_GETMESSAGE))
                    .when(mailboxServerProxy).getMessage(0);

            final LogboekRegel logboekRegel = new LogboekRegel();
            voaService.receiveAndStoreMessagesFromMailbox(mailbox, logboekRegel);
            assertTrue("Er zou geen ontvangen bericht moeten zijn.", logboekRegel.getAantalOntvangenOK() == 0
                    && logboekRegel.getAantalOntvangenNOK() == 0);
        } catch (final VoaException e) {
            e.printStackTrace();
            exceptionThrown = true;
        }
        assertFalse("Er zou geen exception gegooid moeten worden.", exceptionThrown);
    }

    @Test
    public void getMessagesToSendSuccesTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            final Bericht bericht = new Bericht();
            final String lo3BerichtInhoud =
                    "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
            bericht.setBerichtInhoud(lo3BerichtInhoud);
            bericht.setRecipient("1904010");
            final List<Bericht> mockBerichten = new ArrayList<Bericht>();
            mockBerichten.add(bericht);
            Mockito.when(voiscDbProxy.getBerichtToSendMBS(mailbox.getGemeentecode())).thenReturn(mockBerichten);

            final List<Bericht> berichten = voaService.getMessagesToSend(mailbox);

            assertTrue("Er moet 1 bericht zijn om te vervangen.", berichten.size() > 0);
        } catch (final Exception e) {
            fail("Exception zou niet moeten optreden.");
        }
    }

    @Test
    public void getMessagesToSendSuccesNoMessageTest() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(1);

        try {
            final List<Bericht> mockBerichten = new ArrayList<Bericht>();
            Mockito.when(voiscDbProxy.getBerichtToSendMBS(mailbox.getGemeentecode())).thenReturn(mockBerichten);

            final List<Bericht> berichten = voaService.getMessagesToSend(mailbox);

            assertTrue("Er moet 1 bericht zijn om te vervangen.", berichten.size() == 0);
        } catch (final Exception e) {
            fail("Exception zou niet moeten optreden.");
        }
    }

    @Test
    public void sendMessagesToEsbSucces() {
        final List<Bericht> messagesToSend = new ArrayList<Bericht>();

        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        bericht.setEsbCorrelationId("1234");
        messagesToSend.add(bericht);

        Mockito.doAnswer(new Answer<Bericht>() {
            @Override
            public Bericht answer(final InvocationOnMock invocation) {
                final Object[] args = invocation.getArguments();
                final Bericht bericht = (Bericht) args[0];
                bericht.setEsbMessageId(BerichtId.generateMessageId());
                return null;
            }
        }).when(queueService).sendMessage(bericht);

        voaService.sendMessagesToEsb(messagesToSend);
        assertEquals("De status zou queue sent moeten zijn.", messagesToSend.get(0).getStatus(),
                StatusEnum.QUEUE_SENT);
        assertNotNull("De messageId mag niet leeg zijn.", messagesToSend.get(0).getEsbMessageId());
    }

    @Test
    public void sendMessagesToEsbJMSFout() {
        final List<Bericht> messagesToSend = new ArrayList<Bericht>();

        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        bericht.setEsbCorrelationId("1234");
        bericht.setStatus(StatusEnum.MAILBOX_RECEIVED);
        messagesToSend.add(bericht);

        Mockito.doThrow(new MessageEOFException(new javax.jms.MessageEOFException("Iets fout"))).when(queueService)
                .sendMessage(bericht);

        voaService.sendMessagesToEsb(messagesToSend);
        assertEquals("Bericht zou niet op verzonden moeten staan.", messagesToSend.get(0).getStatus(),
                StatusEnum.MAILBOX_RECEIVED);
    }

    @Test
    public void sendMessagesToEsbBerichtDBFout() {
        final List<Bericht> messagesToSend = new ArrayList<Bericht>();

        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient("1904010");
        bericht.setEsbCorrelationId("1234");
        messagesToSend.add(bericht);

        try {
            Mockito.doThrow(new VoaException(MessagesCodes.ERRMSG_VOSPG_DB_CONNECTION_FAILED, null))
                    .when(voiscDbProxy)
                    .saveBericht(Matchers.any(Bericht.class), Matchers.anyBoolean(), Matchers.anyBoolean());

            voaService.sendMessagesToEsb(messagesToSend);
        } catch (final Exception e) {
            fail("Exception zou niet moeten optreden.");
        }
    }

    @Test
    public void sendMessagesToMailboxWrongRecipientTest() {
        final String recipient = "1908010";
        final List<Bericht> messages = new ArrayList<Bericht>();
        final Bericht bericht = new Bericht();
        final String lo3BerichtInhoud =
                "00000000Ib010A2012050500999011710110010918239123401200098934839440210006Gerard0240010Dungelmann03100082000010103200040599033000460300410001M6110001E8110004059981200071 A8192851000820000101861000820000102021710110010901273641401200091481923410210005Anouk0240004Snel03100081970010103200041900033000460300410001V6210008200001018110004059981200071 A8192851000820000101861000820000102031830110010821390439401200091388394140210011Mario James0240010Dungelmann03100081969010103200041901033000460300410001M6210008200001018110004059981200071 A81928510008200001018610008200001020405105100040001631000302085100082000010186100082000010207058681000820000101701000108010004000180200170000000000000000008106091000405990920008200001011010001W102000405991030008200001011110001.7210001I851000820000101861000820000102100693910002003920008000000003930008000000008510008200001018610008200001021103833100011851000820100101861000820100102121073510002PD3520009NA13248963530008200001013540006BI0599355000820100101358000261851000820000101861000820000102";
        bericht.setBerichtInhoud(lo3BerichtInhoud);
        bericht.setRecipient(recipient);
        messages.add(bericht);

        Mockito.when(voiscDbProxy.getMailboxByNummer(recipient)).thenReturn(null);
        try {
            Mockito.doAnswer(new Answer<Bericht>() {
                @Override
                public Bericht answer(final InvocationOnMock invocation) {
                    // Gaat het om het errorbericht dat we willen?
                    if (((String) invocation.getArguments()[1]).equals("Recipient bestaat niet.")) {
                        throw new MockitoException("errorbericht gemaakt!");
                    }
                    return null;
                }
            }).when(voiscDbProxy).sendEsbErrorBericht(Matchers.any(Bericht.class), Matchers.anyString());
            voaService.sendMessagesToMailbox(new LogboekRegel(), messages);
            fail("Er zou een MockitoException gegooid moeten zijn.");
        } catch (final VoaException e) {
            fail("Er mag geen fout optreden bij het verzenden.");
        } catch (final MockitoException e) {
            assertTrue("Verkeerde message.", e.getMessage().equals("errorbericht gemaakt!"));
        }
    }

    @Test
    public void getMessagesToSendToQueue() {
        voaService.getMessagesToSendToQueue(171);
        Mockito.verify(voiscDbProxy, Mockito.times(1)).getBerichtToSendQueue(171);
    }

    private Mailbox createMailbox() {
        final Mailbox mailbox = new Mailbox();
        mailbox.setGemeentecode("1904");
        mailbox.setMailboxnr("1904010");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(2);

        return mailbox;
    }
}
