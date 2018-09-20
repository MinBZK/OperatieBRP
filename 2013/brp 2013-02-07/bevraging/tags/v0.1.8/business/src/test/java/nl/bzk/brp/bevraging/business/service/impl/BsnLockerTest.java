/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.BsnLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BsnLockerTest {

    /**
     * Toegestane maximale afwijking van de transactietimeout, in procenten.
     */
    private static final double AFWIJKING = 5.0;

    @Inject
    private BsnLocker           bsnLocker;

    /**
     * Simpele test, 0 en 1 BSN combinaties.
     */

    @Test
    public void simpleBsnLockerTestA() {
        assertTrue(bsnLocker.getLocks(1L, Collections.<Long>emptyList(), Collections.<Long>emptyList()));
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestB() {

        assertTrue(bsnLocker.getLocks(1L, Collections.<Long>emptyList(), Arrays.asList(1L)));
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestC() {
        assertTrue(bsnLocker.getLocks(1L, Collections.<Long>emptyList(), Arrays.asList(1L, 2L, 3L)));
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestD() {
        assertTrue(bsnLocker.getLocks(1L, Arrays.asList(1L, 2L, 3L), Collections.<Long>emptyList()));
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestE() {

        assertTrue(bsnLocker.getLocks(1L, Arrays.asList(1L), Arrays.asList(1L)));
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestF() {
        assertTrue(bsnLocker.getLocks(1L, Arrays.asList(1L, 2L, 3L), Arrays.asList(1L, 2L, 3L)));
        bsnLocker.unLock();
    }

    /**
     * binnen de thread mag je niet meerdere keren getLocks() aanroepen (an sich)
     */

    @Test
    public void tweeKeerLockTest() {
        assertTrue(bsnLocker.getLocks(1L, Collections.<Long>emptyList(), Collections.<Long>emptyList()));
        assertFalse(bsnLocker.getLocks(1L, Collections.<Long>emptyList(), Collections.<Long>emptyList()));
        bsnLocker.unLock();
    }

    /**
     * Test LockTypen met elkaaar
     *
     * @throws InterruptedException
     */

    @Test
    public void timoutThreadLockTestA() throws InterruptedException {

        // read - read
        testReadWriteMetAfwijking(Arrays.asList(1L), Collections.<Long>emptyList(), Arrays.asList(1L),
                Collections.<Long>emptyList(), null);
    }

    @Test
    public void timoutThreadLockTestB() throws InterruptedException {

        // read - write
        testReadWriteMetAfwijking(Arrays.asList(1L), Collections.<Long>emptyList(), Collections.<Long>emptyList(),
                Arrays.asList(1L), (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    @Test
    public void timoutThreadLockTestC() throws InterruptedException {

        // write - read
        testReadWriteMetAfwijking(Collections.<Long>emptyList(), Arrays.asList(1L), Arrays.asList(1L),
                Collections.<Long>emptyList(), (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    @Test
    public void timoutThreadLockTestD() throws InterruptedException {

        // write - write
        testReadWriteMetAfwijking(Collections.<Long>emptyList(), Arrays.asList(1L), Collections.<Long>emptyList(),
                Arrays.asList(1L), (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    @Test
    public void timoutThreadLockTestE() throws InterruptedException {

        // read - write
        testReadWriteMetAfwijking(Arrays.asList(1L, 2L), Collections.<Long>emptyList(),
                Collections.<Long>emptyList(), Arrays.asList(2L, 3L),
                (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    @Test
    public void timoutThreadLockTestF() throws InterruptedException {
        // write - read
        testReadWriteMetAfwijking(Collections.<Long>emptyList(), Arrays.asList(1L, 2L), Arrays.asList(2L, 3L),
                Collections.<Long>emptyList(), (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    @Test
    public void timoutThreadLockTestG() throws InterruptedException {
        // write - write
        testReadWriteMetAfwijking(Collections.<Long>emptyList(), Arrays.asList(1L, 2L),
                Collections.<Long>emptyList(), Arrays.asList(2L, 3L),
                (bsnLocker.getTransactionTimeoutSeconds() * 1000L));
    }

    /**
     * timeout test, het niet kunnen verkrijgen van een lock moet(!) 5 (+/-10%) seconde duren, niet langer, niet korter
     *
     * @throws InterruptedException
     */

    public void testReadWriteMetAfwijking(final Collection<Long> readLocksA, final Collection<Long> writeLocksA,
            final Collection<Long> readLocksB, final Collection<Long> writeLocksB, final Long duurInMillies)
    {

        try {

            long startTimeMillis = System.currentTimeMillis();

            assertTrue(bsnLocker.getLocks(1L, readLocksA, writeLocksA));

            Thread myThread = new Thread() {

                @Override
                public void run() {
                    try {
                        bsnLocker.getLocks(1L, readLocksB, writeLocksB);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        bsnLocker.unLock();
                    }
                }

            };

            myThread.start();
            myThread.join();

            if (duurInMillies != null) {
                // timetest van toepassing
                assertTrue(verschilInProcentenMinderDanOfGelijk(AFWIJKING, duurInMillies, System.currentTimeMillis()
                    - startTimeMillis));
            } else {
                // timetest niet van toepassing, dus 'instant' (minder dan 500 milli)
                assertTrue((System.currentTimeMillis() - startTimeMillis) < 500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bsnLocker.unLock();
        }
    }

    @Test
    public void verschilTester() {
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 95));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 97));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 99));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 100));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 101));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 103));
        assertTrue(verschilInProcentenMinderDanOfGelijk(5, 100, 105));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, 100, 105.01));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, 100, 95 - 0.01));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, 100, -105));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, -100, 105));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, 100, -95));
        assertFalse(verschilInProcentenMinderDanOfGelijk(5, -100, 95));
    }

    private boolean verschilInProcentenMinderDanOfGelijk(final double vperc, final double voriginal,
            final double vafwijking)
    {
        boolean result =
            vafwijking <= ((1 + (vperc / 100.0)) * voriginal) && vafwijking >= ((1 + (-vperc / 100.0)) * voriginal);

        return result;
    }

}
