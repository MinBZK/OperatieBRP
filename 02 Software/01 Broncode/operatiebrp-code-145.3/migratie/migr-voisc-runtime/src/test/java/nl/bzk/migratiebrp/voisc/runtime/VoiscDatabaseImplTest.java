/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.OptimisticLockException;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoiscDatabaseImplTest {

    @Mock
    private BerichtRepository berichtRepository;
    @Mock
    private MailboxRepository mailboxRepository;

    @InjectMocks
    private VoiscDatabaseImpl subject;

    @Test
    public void testSaveBericht() {
        // Setup
        final Bericht bericht = new Bericht();

        // Execute
        subject.saveBericht(bericht);

        // Verify
        Mockito.verify(berichtRepository, Mockito.times(1)).save(bericht);
        Mockito.verifyNoMoreInteractions(berichtRepository, mailboxRepository);
    }

    @Test
    public void testLeesEnLockNaarIscTeVerzendenBerichten() {
        // Setup
        final Bericht candidate1 = new Bericht();
        final Bericht candidate2 = new Bericht();
        final Bericht candidate3 = new Bericht();
        final List<Bericht> candidates = Arrays.asList(candidate1, candidate2, candidate3);
        Mockito.when(berichtRepository.getBerichtenToSendQueue(100)).thenReturn(candidates);

        final Bericht bericht1 = new Bericht();
        final Bericht bericht3 = new Bericht();

        Mockito.when(berichtRepository.save(candidate1)).thenReturn(bericht1);
        Mockito.when(berichtRepository.save(candidate2)).thenThrow(new OptimisticLockException());
        Mockito.when(berichtRepository.save(candidate3)).thenReturn(bericht3);

        // Execute
        final List<Bericht> result = subject.leesEnLockNaarIscTeVerzendenBericht(100);

        // Verify
        Assert.assertEquals(StatusEnum.SENDING_TO_ISC, candidate1.getStatus());
        Assert.assertEquals(StatusEnum.SENDING_TO_ISC, candidate2.getStatus());
        Assert.assertEquals(StatusEnum.SENDING_TO_ISC, candidate3.getStatus());

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(bericht1));
        Assert.assertTrue(result.contains(bericht3));

    }

    @Test
    public void testLeesEnLockNaarMailboxTeVerzendenBerichten() {
        // Setup
        final Bericht candidate1 = new Bericht();
        final Bericht candidate2 = new Bericht();
        final Bericht candidate3 = new Bericht();
        final List<Bericht> candidates = Arrays.asList(candidate1, candidate2, candidate3);
        Mockito.when(berichtRepository.getBerichtToSendMBS("3000240")).thenReturn(candidates);

        final Bericht bericht1 = new Bericht();
        final Bericht bericht3 = new Bericht();

        Mockito.when(berichtRepository.save(candidate1)).thenReturn(bericht1);
        Mockito.when(berichtRepository.save(candidate2)).thenThrow(new OptimisticLockException());
        Mockito.when(berichtRepository.save(candidate3)).thenReturn(bericht3);

        // Execute
        final List<Bericht> result = subject.leesEnLockNaarMailboxTeVerzendenBericht("3000240");

        // Verify
        Assert.assertEquals(StatusEnum.SENDING_TO_MAILBOX, candidate1.getStatus());
        Assert.assertEquals(StatusEnum.SENDING_TO_MAILBOX, candidate2.getStatus());
        Assert.assertEquals(StatusEnum.SENDING_TO_MAILBOX, candidate3.getStatus());

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(bericht1));
        Assert.assertTrue(result.contains(bericht3));
    }

    @Test
    public void testGetMailboxByPartijcode() {
        // Execute
        subject.getMailboxByPartijcode("051801");

        // Verify
        Mockito.verify(mailboxRepository, Mockito.times(1)).getMailboxByPartijcode("051801");
        Mockito.verifyNoMoreInteractions(berichtRepository, mailboxRepository);

    }

    @Test
    public void testSaveMailbox() {
        // Setup
        final Mailbox mailbox = new Mailbox();

        // Execute
        subject.saveMailbox(mailbox);

        // Verify
        Mockito.verify(mailboxRepository, Mockito.times(1)).save(mailbox);
        Mockito.verifyNoMoreInteractions(berichtRepository, mailboxRepository);
    }

    @Test
    public void testSchoonVerstuurdeBerichtenOp() throws VoiscDatabaseException {
        // Setup
        final Date datumVerwerkt = new Date();
        final Set<StatusEnum> statussen = new HashSet<>();
        statussen.add(StatusEnum.SENT_TO_ISC);
        statussen.add(StatusEnum.SENT_TO_MAILBOX);
        statussen.add(StatusEnum.IGNORED);

        Mockito.when(berichtRepository.verwijderVerzondenBerichtenOuderDan(datumVerwerkt, statussen)).thenReturn(1);

        // Execute
        final int aantalOpgeschoond = subject.verwijderVerwerkteBerichtenOuderDan(datumVerwerkt, statussen);

        // Verify
        Mockito.verify(berichtRepository, Mockito.times(1)).verwijderVerzondenBerichtenOuderDan(datumVerwerkt, statussen);
        Mockito.verifyNoMoreInteractions(berichtRepository, mailboxRepository);
        Assert.assertEquals(1, aantalOpgeschoond);
    }

    @Test(expected = VoiscDatabaseException.class)
    public void testSchoonVerstuurdeBerichtenOpNullTS() throws VoiscDatabaseException {
        final Date datumVerwerkt = null;
        final Set<StatusEnum> statussen = new HashSet<>();
        statussen.add(StatusEnum.SENT_TO_ISC);
        statussen.add(StatusEnum.SENT_TO_MAILBOX);
        statussen.add(StatusEnum.IGNORED);
        subject.verwijderVerwerkteBerichtenOuderDan(datumVerwerkt, statussen);
    }

    @Test(expected = VoiscDatabaseException.class)
    public void testSchoonVerstuurdeBerichtenOpNullStatussen() throws VoiscDatabaseException {
        final Date datumVerwerkt = new Date();
        final Set<StatusEnum> statussen = null;
        subject.verwijderVerwerkteBerichtenOuderDan(datumVerwerkt, statussen);
    }

    @Test(expected = VoiscDatabaseException.class)
    public void testSchoonVerstuurdeBerichtenOpLegeStatussen() throws VoiscDatabaseException {
        final Date datumVerwerkt = new Date();
        final Set<StatusEnum> statussen = new HashSet<>();
        subject.verwijderVerwerkteBerichtenOuderDan(datumVerwerkt, statussen);
    }

}
