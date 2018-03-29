/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoiscMailboxImplTest {

    @Mock
    private MailboxClient mailboxClient;
    @Mock
    private MailboxRepository mailboxRepository;
    @Mock
    private BerichtRepository berichtRepository;
    @Mock
    private VoiscQueue archiveringService;
    @Mock
    private Connection connection;

    @InjectMocks
    private VoiscMailboxImpl subject;

    @Test
    public void testConnectToMailboxServer() throws VoiscMailboxException {
        // Execute
        subject.connectToMailboxServer();

        // Verify
        Mockito.verify(mailboxClient, Mockito.times(1)).connect();
        Mockito.verifyNoMoreInteractions(mailboxClient, mailboxRepository, berichtRepository);
    }

    @Test(expected = VoiscMailboxException.class)
    public void testConnectToMailboxServerFailure() throws VoiscMailboxException {
        // Setup
        Mockito.doThrow(new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD, null)).when(mailboxClient).connect();

        // Execute
        subject.connectToMailboxServer();
    }

    @Test
    public void testLogin() throws VoiscMailboxException, VoaException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd("password");

        final Calendar laatsteWijzigingPwd = Calendar.getInstance();
        laatsteWijzigingPwd.add(Calendar.DAY_OF_MONTH, -59);
        mailbox.setLaatsteWijzigingPwd(new Timestamp(laatsteWijzigingPwd.getTimeInMillis()));

        // Execute
        subject.login(connection, mailbox);

        // Verify
        Mockito.verify(mailboxClient, Mockito.times(1)).logOn(connection, mailbox);
        Mockito.verifyNoMoreInteractions(mailboxClient, mailboxRepository, berichtRepository);
    }

    @Test
    public void testLoginMetWachtwoordChange() throws VoaException, VoiscMailboxException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd("password");
        final Calendar laatsteWijzigingPwd = Calendar.getInstance();
        laatsteWijzigingPwd.add(Calendar.DAY_OF_MONTH, -61);
        mailbox.setLaatsteWijzigingPwd(new Timestamp(laatsteWijzigingPwd.getTimeInMillis()));

        // Execute
        subject.login(connection, mailbox);

        // Verify
        Mockito.verify(mailboxClient, Mockito.times(1)).logOn(connection, mailbox);
        final ArgumentCaptor<String> newPasswordCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mailboxClient, Mockito.times(1)).changePassword(Matchers.eq(connection), Matchers.eq(mailbox), newPasswordCaptor.capture());
        Mockito.verify(mailboxRepository, Mockito.times(1)).save(mailbox);

        Mockito.verifyNoMoreInteractions(mailboxClient, mailboxRepository, berichtRepository);

        final String newPassword = newPasswordCaptor.getValue();
        Assert.assertEquals(newPassword, mailbox.getMailboxpwd());
    }

    @Test(expected = VoiscMailboxException.class)
    public void testLoginMetWachtwoordChangeFailure() throws VoaException, MailboxServerPasswordException, VoiscMailboxException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd("password");
        final Calendar laatsteWijzigingPwd = Calendar.getInstance();
        laatsteWijzigingPwd.add(Calendar.DAY_OF_MONTH, -61);
        mailbox.setLaatsteWijzigingPwd(new Timestamp(laatsteWijzigingPwd.getTimeInMillis()));

        Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD))
                .when(mailboxClient)
                .changePassword(Matchers.eq(connection), Matchers.eq(mailbox), Matchers.anyString());

        // Execute
        subject.login(connection, mailbox);
    }

    @Test(expected = VoiscMailboxException.class)
    public void testLoginGeenWachtwoordBeschikbaar() throws VoiscMailboxException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd(null);

        // Execute
        subject.login(connection, mailbox);
    }

    @Test(expected = VoiscMailboxException.class)
    public void testLoginMailboxServerPasswordException() throws VoaException, VoiscMailboxException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd("password");

        Mockito.doThrow(new MailboxServerPasswordException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD)).when(mailboxClient)
                .logOn(connection, mailbox);

        // Execute
        subject.login(connection, mailbox);
    }

    @Test(expected = VoiscMailboxException.class)
    public void testLoginSpdProtocolException() throws VoaException, VoiscMailboxException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxpwd("password");

        Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD)).when(mailboxClient).logOn(connection, mailbox);

        // Execute
        subject.login(connection, mailbox);
    }

    @Test
    public void testSendMessageToMailbox() throws VoaException, VoiscQueueException {
        // Setup
        final Bericht bericht1 = new Bericht(); // Ok
        bericht1.setRecipient("3000250");
        final Bericht bericht2 = new Bericht(); // Recipient blocked
        bericht2.setRecipient("3000260");
        final Bericht bericht3 = new Bericht(); // Send failure
        bericht3.setRecipient("3000250");
        final Bericht bericht4 = new Bericht(); // Ok
        bericht4.setRecipient("3000250");

        Mockito.when(mailboxRepository.getMailboxByNummer("3000250")).thenReturn(new Mailbox());
        final Mailbox blockedMailbox = new Mailbox();
        blockedMailbox.setStartBlokkering(new Timestamp(System.currentTimeMillis() - 1000));
        Mockito.when(mailboxRepository.getMailboxByNummer("3000260")).thenReturn(blockedMailbox);

        Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD)).when(mailboxClient).putMessage(connection, bericht3);

        // Execute
        final Mailbox mailbox = new Mailbox();
        mailbox.setMailboxnr("0518010");
        final List<Bericht> messagesToSend = Arrays.asList(bericht1, bericht2, bericht3, bericht4);
        subject.sendMessagesToMailbox(connection, mailbox, messagesToSend);

        // Verify
        Mockito.verify(mailboxRepository, Mockito.times(3)).getMailboxByNummer("3000250");
        Mockito.verify(mailboxRepository, Mockito.times(1)).getMailboxByNummer("3000260");

        Mockito.verify(mailboxClient, Mockito.times(1)).putMessage(connection, bericht1);
        Mockito.verify(mailboxClient, Mockito.times(1)).putMessage(connection, bericht3);
        Mockito.verify(mailboxClient, Mockito.times(1)).putMessage(connection, bericht4);

        Mockito.verify(berichtRepository, Mockito.times(1)).save(bericht1);
        Mockito.verify(berichtRepository, Mockito.times(1)).save(bericht2);
        Mockito.verify(berichtRepository, Mockito.times(1)).save(bericht4);

        Mockito.verify(archiveringService, Mockito.times(1)).archiveerBericht(bericht1, RichtingType.UITGAAND);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveerBericht(bericht4, RichtingType.UITGAAND);

        Mockito.verifyNoMoreInteractions(mailboxClient, mailboxRepository, berichtRepository, archiveringService);

        Assert.assertEquals(StatusEnum.SENT_TO_MAILBOX, bericht1.getStatus());
        Assert.assertEquals(StatusEnum.RECEIVED_FROM_ISC, bericht2.getStatus());
        Assert.assertEquals(StatusEnum.SENT_TO_MAILBOX, bericht4.getStatus());
    }

    @Test
    public void testReceiveMessagesFromMailbox() throws VoaException, VoiscQueueException {
        // Setup
        final Mailbox mailbox = new Mailbox();
        mailbox.setLimitNumber(67);

        Mockito.when(
                mailboxClient.listMessages(
                        Matchers.eq(connection),
                        Matchers.anyInt(),
                        Matchers.anyListOf(Integer.class),
                        Matchers.eq(67),
                        Matchers.eq("012"),
                        Matchers.eq(SpdConstants.Priority.defaultValue())))
                .thenAnswer(invocation -> {
                    final int nextSeqNr = (int) invocation.getArguments()[1];

                    // We weten dat het type van het tweede argument een lijst van integers is, echter geeft
                    // getArguments
                    // een Object terug die we niet type safe kunnen converteren.
                    @SuppressWarnings("unchecked") final List<Integer> seqNummers = (List<Integer>) invocation.getArguments()[2];

                    if (nextSeqNr == 0) {
                        // Eerste call
                        seqNummers.add(14); // ok
                        seqNummers.add(23); // error
                        seqNummers.add(28); // null
                        seqNummers.add(37); // ok

                        return 43;
                    } else if (nextSeqNr == 43) {
                        // Tweede call
                        seqNummers.add(52); // ok

                        return 67;

                    } else if (nextSeqNr == 67) {
                        // Derde call

                        throw new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD);
                    }

                    Assert.fail();
                    return 0;
                });

        final Bericht bericht14 = new Bericht();
        final Bericht bericht37 = new Bericht();
        final Bericht bericht52 = new Bericht();

        Mockito.when(mailboxClient.getMessage(connection, 14)).thenReturn(bericht14);
        Mockito.when(mailboxClient.getMessage(connection, 23)).thenThrow(new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD));
        Mockito.when(mailboxClient.getMessage(connection, 28)).thenReturn(null);
        Mockito.when(mailboxClient.getMessage(connection, 37)).thenReturn(bericht37);
        Mockito.when(mailboxClient.getMessage(connection, 52)).thenReturn(bericht52);

        // Execute
        try {
            subject.receiveMessagesFromMailbox(connection, mailbox);
            Assert.fail("VoiscMailboxException verwacht");
        } catch (final VoiscMailboxException e) {
            Assert.assertTrue(true);
        }

        // Verify
        Mockito.verify(berichtRepository).save(bericht14);
        Mockito.verify(berichtRepository).save(bericht37);
        Mockito.verify(berichtRepository).save(bericht52);
        Mockito.verify(archiveringService).archiveerBericht(bericht14, RichtingType.INGAAND);
        Mockito.verify(archiveringService).archiveerBericht(bericht37, RichtingType.INGAAND);
        Mockito.verify(archiveringService).archiveerBericht(bericht52, RichtingType.INGAAND);
    }

    @Test
    public void testLogout() throws VoaException {
        // Execute
        subject.logout(connection);

        // Verify
        Mockito.verify(mailboxClient, Mockito.times(1)).logOff(connection);
    }

    @Test
    public void testLogoutSpdProtocolException() throws VoaException {
        // Setup
        Mockito.doThrow(new SpdProtocolException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD)).when(mailboxClient).logOff(connection);

        // Execute
        subject.logout(connection);
    }

}
