/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class BerichtRepositoryTest extends AbstractRepositoryTest {

    private static final String ORIGINATOR = "1234567";

    @Inject
    private BerichtRepository voaRepository;

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void testMbs() {
        final List<Bericht> before = voaRepository.getBerichtToSendMBS(ORIGINATOR);

        Bericht bericht = maakDummyBericht(StatusEnum.RECEIVED_FROM_ISC);
        bericht = voaRepository.save(bericht);
        em.flush();

        final List<Bericht> after = voaRepository.getBerichtToSendMBS(ORIGINATOR);
        Assert.assertEquals(before.size() + 1, after.size());

        bericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);
        bericht = voaRepository.save(bericht);
        em.flush();

        final List<Bericht> check = voaRepository.getBerichtToSendMBS(ORIGINATOR);
        Assert.assertEquals(before.size(), check.size());
    }

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void testQueue() {
        final List<Bericht> before = voaRepository.getBerichtenToSendQueue(100);

        Bericht bericht = maakDummyBericht(StatusEnum.RECEIVED_FROM_MAILBOX);

        bericht = voaRepository.save(bericht);
        em.flush();

        final List<Bericht> after = voaRepository.getBerichtenToSendQueue(100);
        Assert.assertEquals(before.size() + 1, after.size());

        bericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);
        bericht = voaRepository.save(bericht);
        em.flush();

        final List<Bericht> check = voaRepository.getBerichtenToSendQueue(100);
        Assert.assertEquals(before.size(), check.size());
    }

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void testVerwijderVerwerkteBerichten() {
        Bericht bericht1 = maakDummyBericht(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht1 = voaRepository.save(bericht1);

        Bericht bericht2 = maakDummyBericht(StatusEnum.RECEIVED_FROM_ISC);
        bericht2 = voaRepository.save(bericht2);

        Bericht bericht3 = maakDummyBericht(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht3 = voaRepository.save(bericht3);

        Bericht bericht4 = maakDummyBericht(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht4 = voaRepository.save(bericht4);
        em.flush();

        List<Bericht> berichtenToSendQueue = voaRepository.getBerichtenToSendQueue(100);
        Assert.assertEquals(3, berichtenToSendQueue.size());

        List<Bericht> berichtenToSendMBS = voaRepository.getBerichtToSendMBS(ORIGINATOR);
        Assert.assertEquals(1, berichtenToSendMBS.size());

        final Set<StatusEnum> statussen = new HashSet<>();
        statussen.add(StatusEnum.SENT_TO_ISC);
        statussen.add(StatusEnum.SENT_TO_MAILBOX);
        statussen.add(StatusEnum.IGNORED);

        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
        }
        int aantalVerwijderd = voaRepository.verwijderVerzondenBerichtenOuderDan(Calendar.getInstance().getTime(), statussen);
        Assert.assertEquals(0, aantalVerwijderd);
        berichtenToSendMBS = voaRepository.getBerichtToSendMBS(ORIGINATOR);
        Assert.assertEquals(1, berichtenToSendMBS.size());

        // pas status van bericht1 aan naar 'SENT_TO_ISC' zodat deze door de opschoon actie wordt opgepakt.
        bericht1.setStatus(StatusEnum.SENT_TO_ISC);
        bericht1.setTijdstipVerzonden(Calendar.getInstance().getTime());
        bericht1 = voaRepository.save(bericht1);
        em.flush();

        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
        }
        aantalVerwijderd = voaRepository.verwijderVerzondenBerichtenOuderDan(Calendar.getInstance().getTime(), statussen);
        Assert.assertEquals(1, aantalVerwijderd);

        bericht2.setStatus(StatusEnum.SENT_TO_MAILBOX);
        bericht2.setTijdstipVerzonden(new Date());
        bericht2 = voaRepository.save(bericht2);
        bericht3.setStatus(StatusEnum.IGNORED);
        bericht3.setTijdstipVerzonden(new Date());
        bericht3 = voaRepository.save(bericht3);
        em.flush();

        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
        }
        aantalVerwijderd = voaRepository.verwijderVerzondenBerichtenOuderDan(Calendar.getInstance().getTime(), statussen);
        Assert.assertEquals(2, aantalVerwijderd);

        berichtenToSendQueue = voaRepository.getBerichtenToSendQueue(100);
        Assert.assertEquals(1, berichtenToSendQueue.size());
        berichtenToSendMBS = voaRepository.getBerichtToSendMBS(ORIGINATOR);
        Assert.assertEquals(0, berichtenToSendMBS.size());
    }

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void testHerstelStatus() throws InterruptedException {

        Bericht bericht1 = maakDummyBericht(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht1 = voaRepository.save(bericht1);

        Bericht bericht2 = maakDummyBericht(StatusEnum.RECEIVED_FROM_ISC);
        bericht2 = voaRepository.save(bericht2);

        Bericht bericht3 = maakDummyBericht(StatusEnum.SENDING_TO_ISC);
        bericht3.setTijdstipInVerwerking(new Date());
        bericht3 = voaRepository.save(bericht3);

        Bericht bericht4 = maakDummyBericht(StatusEnum.SENT_TO_ISC);
        bericht4 = voaRepository.save(bericht4);
        em.flush();

        int aantalTeVerzenden = voaRepository.getBerichtenToSendQueue(100).size();
        Assert.assertEquals(1, aantalTeVerzenden);

        Thread.sleep(500);

        final int aantalHersteld = voaRepository.herstelStatus(new Date(), StatusEnum.SENDING_TO_ISC, StatusEnum.RECEIVED_FROM_MAILBOX);
        Assert.assertEquals(1, aantalHersteld);

        aantalTeVerzenden = voaRepository.getBerichtenToSendQueue(100).size();
        Assert.assertEquals(2, aantalTeVerzenden);
    }

    private Bericht maakDummyBericht(final StatusEnum status) {
        final Bericht bericht1 = new Bericht();
        bericht1.setOriginator(ORIGINATOR);
        bericht1.setRecipient("7654321");
        bericht1.setStatus(status);
        bericht1.setBerichtInhoud("Bla");
        bericht1.setMessageId("EREF-123123");
        bericht1.setCorrelationId("Bref-133223");
        bericht1.setTijdstipOntvangst(new Date());
        return bericht1;
    }
}
