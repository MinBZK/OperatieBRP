/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Category(OverslaanBijInMemoryDatabase.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:locking-beans-test.xml")
public class BrpLockerTest {

    private static final Logger        LOGGER          = LoggerFactory.getLogger();

    private static final String        FOUTMELDING     = "Er is een fout opgetreden: ";

    private static final List<Integer> GEEN_LOCKS      = Collections.emptyList();

    private static final int           DEFAULT_TIMEOUT = 2;

    /**
     * Toegestane maximale afwijking van de transactietimeout, in procenten.
     */
    private static final double        AFWIJKING       = 10.0;

    @Inject
    private BrpLocker                  brpLocker;

    @Test
    public void lockingdatabaseIdsNullCheck() throws BrpLockerExceptie {
        assertFalse(brpLocker.lock(null, LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingZonderDatabaseIds() throws BrpLockerExceptie {
        assertFalse(brpLocker.lock(GEEN_LOCKS, LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertFalse(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingMetEenDatabaseId() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingMetMeerdereDatabaseId() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockElementNaVrijgevenVanLock() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());

        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockenVanVerschillendeElementenMetDezelfdeIds() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.ADMINISTRATIEVE_HANDELING, LockingMode.EXCLUSIVE,
                DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingVandezelfdeElementenMetVerschillendeDatabaseIds() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        assertTrue(brpLocker.lock(Arrays.asList(2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingVandezelfdeElementenMetDeelsDezelfdeDatabaseIds() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        assertTrue(brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingMetMeerdereBijhoudingen() throws BrpLockerExceptie {
        // bijhouding actie 1 gaat element 1 dmv een exclusief lock locken.
        // hierdoor zal bijhouding actie 2 (lockpoging 2 voor element 1) mislukken
        testReadWriteMetAfwijking(Arrays.asList(1), LockingMode.EXCLUSIVE, Arrays.asList(1), LockingMode.EXCLUSIVE,
                DEFAULT_TIMEOUT * 1000L);

        // bijhouding actie 3 (het vervolgens opnieuw locken van element 1) zou nu wel weer moeten lukken
        // aangezien de locks van bovenstaande acties weer zijn vrijgegeven
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.EXCLUSIVE, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());

        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockCheckZonderZettenVanEenLock() {
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test(expected = BrpLockerExceptie.class)
    public void lockenVanMeerderePersonenMetTimeoutVanNul() throws BrpLockerExceptie {
        // het locken van meerdere personen binnen 0 miliseconden zou moeten valen
        brpLocker.lock(Arrays.asList(1, 2, 3, 4, 5), LockingElement.PERSOON, LockingMode.SHARED, 0);
    }

    @Test(expected = BrpLockerExceptie.class)
    public void lockenVanMeerdereAdminstratieHandelingenMetTimeoutVanNul() throws BrpLockerExceptie {
        // het locken van meerdere administratieve handelingen binnen 0 miliseconden zou moeten valen
        brpLocker
                .lock(Arrays.asList(1, 2, 3, 4, 5), LockingElement.ADMINISTRATIEVE_HANDELING, LockingMode.EXCLUSIVE, 0);
    }

    /**
     * Onderstaande testen controleren verschillende LockingModes met elkaar, zoals:
     * read - read: het bevragen van twee (keer dezelfde) personen
     * read - write: het bijhouden van een persoon die reeds bevraagd word
     * write - read: het bevragen van een persoon die reeds bijgehouden wordt
     * etc. etc...
     */
    @Test
    public void timoutThreadLockTestA() throws BrpLockerExceptie {
        // read - read
        testReadWriteMetAfwijking(Arrays.asList(1), LockingMode.SHARED, Arrays.asList(1), LockingMode.SHARED, null);
    }

    @Test
    public void timoutThreadLockTestB() throws InterruptedException, BrpLockerExceptie {
        // read - write
        testReadWriteMetAfwijking(Arrays.asList(1), LockingMode.SHARED, Arrays.asList(1), LockingMode.EXCLUSIVE,
                DEFAULT_TIMEOUT * 1000L);
    }

    @Test
    public void timoutThreadLockTestC() throws InterruptedException, BrpLockerExceptie {
        // write - read
        testReadWriteMetAfwijking(Arrays.asList(1), LockingMode.EXCLUSIVE, Arrays.asList(1), LockingMode.SHARED,
                DEFAULT_TIMEOUT * 1000L);
    }

    @Test
    public void timoutThreadLockTestD() throws InterruptedException, BrpLockerExceptie {
        // write - write
        testReadWriteMetAfwijking(Arrays.asList(1), LockingMode.EXCLUSIVE, Arrays.asList(1), LockingMode.EXCLUSIVE,
                DEFAULT_TIMEOUT * 1000L);
    }

    @Test
    public void timoutThreadLockTestE() throws InterruptedException, BrpLockerExceptie {
        // read - write
        testReadWriteMetAfwijking(Arrays.asList(1, 2), LockingMode.SHARED, Arrays.asList(2, 3), LockingMode.EXCLUSIVE,
                DEFAULT_TIMEOUT * 1000L);
    }

    @Test
    public void timoutThreadLockTestF() throws InterruptedException, BrpLockerExceptie {
        // write - read
        testReadWriteMetAfwijking(Arrays.asList(1, 2), LockingMode.EXCLUSIVE, Arrays.asList(2, 3), LockingMode.SHARED,
                DEFAULT_TIMEOUT * 1000L);
    }

    @Test
    public void timoutThreadLockTestG() throws InterruptedException, BrpLockerExceptie {
        // write - write
        testReadWriteMetAfwijking(Arrays.asList(2, 3), LockingMode.EXCLUSIVE, Arrays.asList(2, 3),
                LockingMode.EXCLUSIVE, DEFAULT_TIMEOUT * 1000L);
    }

    /**
     * timeout test, het niet kunnen verkrijgen van een lock moet(!) 5 (+/-10%) seconde duren, niet langer, niet korter
     *
     * @param lockingIdsA Read locks die behoren bij set A.
     * @param lockingModeA Locking mode bij set A
     * @param lockingIdsB Read locks die behoren bij set B.
     * @param lockingModeB Locking mode bij set B
     * @param duurInMillies Hoelang het mag duren.
     */
    public void testReadWriteMetAfwijking(final Collection<Integer> lockingIdsA, final LockingMode lockingModeA,
            final Collection<Integer> lockingIdsB, final LockingMode lockingModeB, final Long duurInMillies)
    {
        try {
            final long startTimeMillis = System.currentTimeMillis();
            assertTrue(brpLocker.lock(lockingIdsA, LockingElement.PERSOON, lockingModeA, DEFAULT_TIMEOUT));

            final boolean[] lockVerkregen = new boolean[1];

            final Thread myThread = new Thread() {

                @Override
                public void run() {
                    try {
                        lockVerkregen[0] =
                            brpLocker.lock(lockingIdsB, LockingElement.PERSOON, lockingModeB, DEFAULT_TIMEOUT);
                    } catch (final BrpLockerExceptie e) {
                        LOGGER.error(FOUTMELDING, e.getMessage(), e);
                    } finally {
                        brpLocker.unLock();
                    }
                }

            };

            myThread.start();
            myThread.join();

            if (duurInMillies != null) {
                // timetest van toepassing, dus controleren of de wachttijd binnen de marge valt
                assertTrue(verschilInProcentenMinderDanOfGelijk(AFWIJKING, duurInMillies, System.currentTimeMillis()
                    - startTimeMillis));
                // timetest van toepassing, dus er zou geen lock verkregen moeten zijn
                assertFalse(lockVerkregen[0]);
            } else {
                // timetest niet van toepassing, dus 'instant' (minder dan 1000 milli)
                assertTrue((System.currentTimeMillis() - startTimeMillis) < 1000);
                // timetest niet van toepassing, dus lock zou verkregen moeten zijn
                assertTrue(lockVerkregen[0]);
            }
        } catch (InterruptedException | BrpLockerExceptie e) {
            LOGGER.error(FOUTMELDING, e.getMessage(), e);
        } finally {
            brpLocker.unLock();
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

    @After
    public void cleanup() {
        brpLocker.unLock();
    }
}
