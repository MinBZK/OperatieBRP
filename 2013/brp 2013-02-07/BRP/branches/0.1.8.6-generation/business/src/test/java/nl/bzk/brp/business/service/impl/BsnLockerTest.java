/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.handlers.BsnLockerExceptie;
import nl.bzk.brp.business.service.BrpBusinessConfiguratie;
import nl.bzk.brp.business.service.BsnLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BsnLockerTest {

    private static final List<Integer> GEEN_LOCKS = Collections.<Integer> emptyList();

    private static final List<Integer> BSNS_1     = Arrays.asList(1);
    private static final List<Integer> BSNS_1_2_3 = Arrays.asList(1, 2, 3);
    private static final List<Integer> BSNS_1_2   = Arrays.asList(1, 2);
    private static final List<Integer> BSNS_2_3   = Arrays.asList(2, 3);

    /**
     * Toegestane maximale afwijking van de transactietimeout, in procenten.
     */
    private static final double        AFWIJKING  = 10.0;

    @Inject
    private BsnLocker                  bsnLocker;

    @Inject
    private BrpBusinessConfiguratie    brpBusinessConfiguratie;

    /**
     * Simpele test, 0 en 1 BSN combinaties.
     */

    @Test
    public void simpleBsnLockerTestA() {
        bsnLocker.getLocks(GEEN_LOCKS, GEEN_LOCKS, null);
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestB() {
        bsnLocker.getLocks(GEEN_LOCKS, Arrays.asList(1), null);
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestC() {
        bsnLocker.getLocks(GEEN_LOCKS, BSNS_1_2_3, null);
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestD() {
        bsnLocker.getLocks(BSNS_1_2_3, GEEN_LOCKS, null);
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestE() {
        bsnLocker.getLocks(Arrays.asList(1), Arrays.asList(1), null);
        bsnLocker.unLock();
    }

    @Test
    public void simpleBsnLockerTestF() {
        bsnLocker.getLocks(BSNS_1_2_3, BSNS_1_2_3, null);
        bsnLocker.unLock();
    }

    /**
     * binnen de thread mag je niet meerdere keren getLocks() aanroepen (an sich)
     */

    @Test
    public void tweeKeerLockTest() {
        bsnLocker.getLocks(GEEN_LOCKS, GEEN_LOCKS, null);
        try {
            bsnLocker.getLocks(GEEN_LOCKS, GEEN_LOCKS, null);
            fail("Test zou hier niet moeten komen vanwege een verwachte exceptie in voorgaande regel");
        } catch (IllegalStateException e) {
            assertNotNull(e);
        }
        bsnLocker.unLock();
    }

    /**
     * Test LockTypen met elkaaar
     *
     */
    @Test
    public void timoutThreadLockTestA() {
        // read - read
        testReadWriteMetAfwijking(Arrays.asList(1), GEEN_LOCKS, Arrays.asList(1), GEEN_LOCKS, null);
    }

    @Test
    public void timoutThreadLockTestB() throws InterruptedException {
        // read - write
        testReadWriteMetAfwijking(Arrays.asList(1), GEEN_LOCKS, GEEN_LOCKS, Arrays.asList(1),
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    @Test
    public void timoutThreadLockTestC() throws InterruptedException {
        // write - read
        testReadWriteMetAfwijking(GEEN_LOCKS, BSNS_1, BSNS_1, GEEN_LOCKS,
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    @Test
    public void timoutThreadLockTestD() throws InterruptedException {
        // write - write
        testReadWriteMetAfwijking(GEEN_LOCKS, BSNS_1, GEEN_LOCKS, BSNS_1,
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    @Test
    public void timoutThreadLockTestE() throws InterruptedException {
        // read - write
        testReadWriteMetAfwijking(BSNS_1_2, GEEN_LOCKS, GEEN_LOCKS, BSNS_2_3,
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    @Test
    public void timoutThreadLockTestF() throws InterruptedException {
        // write - read
        testReadWriteMetAfwijking(GEEN_LOCKS, BSNS_1_2, BSNS_2_3, GEEN_LOCKS,
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    @Test
    public void timoutThreadLockTestG() throws InterruptedException {
        // write - write
        testReadWriteMetAfwijking(GEEN_LOCKS, BSNS_2_3, GEEN_LOCKS, BSNS_2_3,
                (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * 1000L));
    }

    /**
     * timeout test, het niet kunnen verkrijgen van een lock moet(!) 5 (+/-10%) seconde duren, niet langer, niet korter
     *
     * @param readLocksA Read locks die behoren bij set A.
     * @param writeLocksA Write locks die behoren bij set A.
     * @param readLocksB Read locks die behoren bij set B.
     * @param writeLocksB Write locks die behoren bij set B.
     * @param duurInMillies Hoelang het mag duren.
     */
    public void testReadWriteMetAfwijking(final Collection<Integer> readLocksA, final Collection<Integer> writeLocksA,
            final Collection<Integer> readLocksB, final Collection<Integer> writeLocksB, final Long duurInMillies)
    {
        try {
            long startTimeMillis = System.currentTimeMillis();
            bsnLocker.getLocks(readLocksA, writeLocksA, null);

            Thread myThread = new Thread() {

                @Override
                public void run() {
                    try {
                        bsnLocker.getLocks(readLocksB, writeLocksB, null);
                    } catch (BsnLockerExceptie e) {
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
                // timetest niet van toepassing, dus 'instant' (minder dan 1000 milli)
                assertTrue((System.currentTimeMillis() - startTimeMillis) < 1000);
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

        return vafwijking <= ((1 + (vperc / 100.0)) * voriginal) && vafwijking >= ((1 + (-vperc / 100.0)) * voriginal);
    }

}
