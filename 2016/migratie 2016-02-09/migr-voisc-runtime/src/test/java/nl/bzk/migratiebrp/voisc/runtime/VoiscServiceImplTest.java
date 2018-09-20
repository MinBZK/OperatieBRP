/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoiscServiceImplTest {

    @Mock
    private VoiscDatabase voiscDatabase;
    @Mock
    private VoiscMailbox voiscMailbox;
    @Mock
    private VoiscQueue voiscQueue;
    @Mock
    private MailboxConfiguratie voiscConfiguratie;
    @InjectMocks
    private VoiscIscImpl voiscIsc;
    @InjectMocks
    private VoiscServiceImpl subject;

    @Before
    public void setup() throws ReflectiveOperationException {
        // Write voiscIsc in subject
        final Field voiscIscField = VoiscServiceImpl.class.getDeclaredField("voiscIsc");
        voiscIscField.setAccessible(true);
        voiscIscField.set(subject, voiscIsc);

    }

    @Test
    public void testBerichtenVerzendenNaarIsc() throws VoiscQueueException {
        // Setup
        final Bericht bericht0001 = maakBericht(1, "bref-1", null, null); // Ok
        final Bericht bericht0002 = maakBericht(2, null, "non-receipt", null); // Non-receipt
        final Bericht bericht0003 = maakBericht(3, null, null, "non-delivery"); // Non-delivery
        bericht0003.setDispatchSequenceNumber(2);
        bericht0003.setRecipient("30002000");
        bericht0003.setOriginator("0517");
        final Bericht bericht0004 = maakBericht(4, "bref-4", null, null); // Ok
        final Bericht bericht0005 = maakBericht(5, null, null, null); // Geen bref, maar ok
        final Bericht bericht0006 = maakBericht(6, "bref-6", null, null); // Error
        final Bericht bericht0007 = maakBericht(7, "bref-7", null, null); // Ok
        final List<Bericht> messagesToSend = Arrays.asList(bericht0001, bericht0002, bericht0003, bericht0004, bericht0005, bericht0006, bericht0007);

        Mockito.when(voiscDatabase.leesEnLockNaarIscTeVerzendenBericht(100)).thenReturn(messagesToSend);
        Mockito.doThrow(new VoiscQueueException("Test")).when(voiscQueue).verstuurBerichtNaarIsc(bericht0006);

        // Execute
        subject.berichtenVerzendenNaarIsc();

        // Verify
        Mockito.verify(voiscDatabase).leesEnLockNaarIscTeVerzendenBericht(100);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0001);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0002);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0003);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0004);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0005);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0006);
        Mockito.verify(voiscQueue).verstuurBerichtNaarIsc(bericht0007);

        Mockito.verify(voiscDatabase).saveBericht(bericht0001);
        Mockito.verify(voiscDatabase).saveBericht(bericht0002);
        Mockito.verify(voiscDatabase).saveBericht(bericht0003);
        Mockito.verify(voiscDatabase).saveBericht(bericht0004);
        Mockito.verify(voiscDatabase).saveBericht(bericht0005);
        Mockito.verify(voiscDatabase).saveBericht(bericht0007);

        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0001.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0002.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0003.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0004.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0005.getStatus());
        Assert.assertEquals(StatusEnum.SENDING_TO_ISC, bericht0006.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht0007.getStatus());

        Mockito.verifyNoMoreInteractions(voiscDatabase, voiscMailbox, voiscQueue, voiscConfiguratie);
    }

    private Bericht maakBericht(final int id, final String bref, final String nonReceipt, final String nonDelivery) {
        final Bericht bericht = new Bericht();
        bericht.setId((long) id);
        bericht.setMessageId(bref);
        bericht.setNotificationType(nonReceipt);
        bericht.setNonDeliveryReason(nonDelivery);
        bericht.setStatus(StatusEnum.SENDING_TO_ISC);
        return bericht;
    }

    @Test
    public void testBerichtenVerzendenNaarEnOntvangenVanMailbox() throws VoiscMailboxException {
        // Setup
        final Mailbox mailbox0001 = maakMailbox(1); // Ok
        final Mailbox mailbox0002 = maakMailbox(2); // VoiscException
        final Mailbox mailbox0003 = maakMailbox(3); // Throwable
        final Mailbox mailbox0004 = maakMailbox(4); // Ok
        final Set<Mailbox> mailboxes = new TreeSet<>();
        mailboxes.add(mailbox0001);
        mailboxes.add(mailbox0002);
        mailboxes.add(mailbox0003);
        mailboxes.add(mailbox0004);

        Mockito.when(voiscConfiguratie.bepaalMailboxen()).thenReturn(mailboxes);

        final Bericht mailbox0001Bericht0001 = new Bericht();
        final Bericht mailbox0001Bericht0002 = new Bericht();
        final Bericht mailbox0001Bericht0003 = new Bericht();
        final List<Bericht> mailbox0001Berichten = Arrays.asList(mailbox0001Bericht0001, mailbox0001Bericht0002, mailbox0001Bericht0003);
        Mockito.when(voiscDatabase.leesEnLockNaarMailboxTeVerzendenBericht("1")).thenReturn(mailbox0001Berichten);

        Mockito.doThrow(new VoiscMailboxException("Test")).when(voiscMailbox).login(mailbox0002);

        Mockito.when(voiscDatabase.leesEnLockNaarMailboxTeVerzendenBericht("3")).thenThrow(new IllegalStateException("Test"));

        final List<Bericht> mailbox0004Berichten = new ArrayList<>();
        Mockito.when(voiscDatabase.leesEnLockNaarMailboxTeVerzendenBericht("4")).thenReturn(mailbox0004Berichten);

        // Execute
        subject.berichtenVerzendenNaarEnOntvangenVanMailbox();

        // Verify
        final InOrder inOrder = Mockito.inOrder(voiscConfiguratie, voiscMailbox, voiscDatabase);

        inOrder.verify(voiscConfiguratie).bepaalMailboxen();
        inOrder.verify(voiscMailbox).connectToMailboxServer();

        inOrder.verify(voiscDatabase).leesEnLockNaarMailboxTeVerzendenBericht("1");
        inOrder.verify(voiscMailbox).login(mailbox0001);
        final ArgumentCaptor<Mailbox> mailbox001Captor = ArgumentCaptor.forClass(Mailbox.class);
        inOrder.verify(voiscMailbox).sendMessagesToMailbox(mailbox001Captor.capture(), Matchers.eq(mailbox0001Berichten));
        Assert.assertEquals(mailbox0001, mailbox001Captor.getValue());
        inOrder.verify(voiscMailbox).receiveMessagesFromMailbox(mailbox0001);

        inOrder.verify(voiscDatabase).leesEnLockNaarMailboxTeVerzendenBericht("2");
        inOrder.verify(voiscMailbox).login(mailbox0002);

        inOrder.verify(voiscDatabase).leesEnLockNaarMailboxTeVerzendenBericht("3");

        inOrder.verify(voiscDatabase).leesEnLockNaarMailboxTeVerzendenBericht("4");
        inOrder.verify(voiscMailbox).login(mailbox0004);
        final ArgumentCaptor<Mailbox> mailbox004Captor = ArgumentCaptor.forClass(Mailbox.class);
        inOrder.verify(voiscMailbox).sendMessagesToMailbox(mailbox004Captor.capture(), Matchers.eq(mailbox0004Berichten));
        Assert.assertEquals(mailbox0004, mailbox004Captor.getValue());
        inOrder.verify(voiscMailbox).receiveMessagesFromMailbox(mailbox0004);

        inOrder.verify(voiscMailbox).logout();

        Mockito.verifyNoMoreInteractions(voiscDatabase, voiscMailbox, voiscQueue, voiscConfiguratie);
    }

    private Mailbox maakMailbox(final int instantiecode) {
        final Mailbox mailbox = new Mailbox();
        mailbox.setInstantiecode(instantiecode);
        mailbox.setMailboxnr(Integer.toString(instantiecode));
        return mailbox;
    }

    @Test
    public void testBerichtenVerzendenNaarEnOntvangenVanMailboxGeenVerbinding() throws VoiscMailboxException {
        Mockito.doThrow(new VoiscMailboxException("Test")).when(voiscMailbox).connectToMailboxServer();

        // Execute
        subject.berichtenVerzendenNaarEnOntvangenVanMailbox();

        // Verify
        Mockito.verify(voiscConfiguratie).bepaalMailboxen();
        Mockito.verify(voiscMailbox).connectToMailboxServer();

        Mockito.verifyNoMoreInteractions(voiscDatabase, voiscMailbox, voiscQueue, voiscConfiguratie);

    }

    @Test
    public void testOpschonenVoiscBerichtenNullDate() throws VoiscDatabaseException {
        // Setup
        final Date ouderDan = null;

        // Execute
        final int aantalOpgeschoond = subject.opschonenVoiscBerichten(ouderDan);

        // Verify
        Mockito.verifyNoMoreInteractions(voiscDatabase, voiscConfiguratie, voiscMailbox, voiscQueue);
        Assert.assertEquals(0, aantalOpgeschoond);
    }

    @Test
    public void testOpschonenVoiscBerichten() throws VoiscDatabaseException {
        // Setup
        final Date ouderDan = Calendar.getInstance().getTime();
        final Set<StatusEnum> statussen = new HashSet<>();
        statussen.add(StatusEnum.IGNORED);
        statussen.add(StatusEnum.SENT_TO_ISC);
        statussen.add(StatusEnum.SENT_TO_MAILBOX);
        statussen.add(StatusEnum.PROCESSED_IMMEDIATELY);
        statussen.add(StatusEnum.ERROR);

        Mockito.when(voiscDatabase.verwijderVerwerkteBerichtenOuderDan(ouderDan, statussen)).thenReturn(1);

        // Execute
        final int aantalOpgeschoond = subject.opschonenVoiscBerichten(ouderDan);

        // Verify
        Mockito.verify(voiscDatabase).verwijderVerwerkteBerichtenOuderDan(ouderDan, statussen);
        Mockito.verifyNoMoreInteractions(voiscDatabase, voiscConfiguratie, voiscMailbox, voiscQueue);
        Assert.assertEquals(1, aantalOpgeschoond);
    }

}
