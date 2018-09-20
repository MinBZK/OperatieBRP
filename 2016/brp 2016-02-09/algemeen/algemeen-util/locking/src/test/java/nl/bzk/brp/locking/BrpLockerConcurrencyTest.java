/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * LET OP! Dit is een concurrency test van de locker door 2 datasources te gebruiken i.c.m. Threading.
 * De test simuleert 2 applicaties met het aanvragen van locks.
 * Assert.fail() aanroepen in aangemaakte threads werkt niet!
 * Daarom wordt er een boolean [] locks variabele gebruikt in alle tests.
 */
@Category(OverslaanBijInMemoryDatabase.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:locking-concurrency-test.xml")
public class BrpLockerConcurrencyTest {

    private static final Logger        LOGGER                                             = LoggerFactory.getLogger();

    // Verschillende lock setjes, omdat we nergens unlocken.
    private static final List<Integer> LOCK_SET_1                                         = Arrays.asList(1);
    private static final List<Integer> LOCK_SET_2                                         = Arrays.asList(2);
    private static final List<Integer> LOCK_SET_3                                         = Arrays.asList(3);
    private static final List<Integer> LOCK_SET_4                                         = Arrays.asList(4);
    private static final String        LOCK_IS_NIET_VERKREGEN                             = "Lock is niet verkregen!";
    private static final String        TRANSACTIONSTATUSTHREADLOCAL                       =
                                                                                              "TRANSACTIONSTATUSTHREADLOCAL";
    private static final String        TEST_GESLAAGD_WRITE_LOCK_MAG_NIET_WORDEN_VERKREGEN =
                                                                                              "Test geslaagd, write lock mag niet worden verkregen.";
    private static final String        THREAD_A_LOCK_HAD_NOG_AANWEZIG_MOETEN_ZIJN         =
                                                                                              "Thread A lock had nog aanwezig moeten zijn!";

    @Inject
    private ApplicationContext         applicationContext;

    private BrpLocker                  lockerThreadA;
    private BrpLocker                  lockerThreadB;

    @Before
    public void init() {
        final String datasource = "dataSource";
        lockerThreadA = new BrpLockerImpl();
        ReflectionTestUtils.setField(lockerThreadA, datasource, applicationContext.getBean("lockingDataSourceA"));
        lockerThreadB = new BrpLockerImpl();
        ReflectionTestUtils.setField(lockerThreadB, datasource, applicationContext.getBean("lockingDataSourceB"));
    }

    @Test
    public void testReadLockBlokkeertWriteLock() throws BrpLockerExceptie, InterruptedException, SQLException {
        // Wrapper boolean omdat we met de waarde null willen beginnen.
        final Boolean[] locks = new Boolean[2];
        final Boolean[] locksAanwezig = new Boolean[2];

        // We testen hier puur de locking en niet het unlocken, het unlocken laten we niet aan onze threads over
        // maar houden we zelf in de hand. We roepen dus nergens unlock aan in de thread omdat we anders het
        // exclusief locken niet kunnen testen.
        // (We weten niet wanneer welk stukje code van welk thread wordt uitgevoerd)
        final Connection[] connecties = new Connection[2];

        final Thread threadA = new Thread("THREAD-A") {

            @Override
            public void run() {
                try {
                    locks[0] = lockerThreadA.lock(LOCK_SET_1, LockingElement.PERSOON, LockingMode.SHARED, 3);
                    // Hou de lock even vast.
                    locksAanwezig[0] = lockerThreadA.isLockNogAanwezig();
                } catch (final BrpLockerExceptie e) {
                    LOGGER.error(LOCK_IS_NIET_VERKREGEN, e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadA,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[0] = connectieThreadLocal.get();
                }
            }
        };

        final Thread threadB = new Thread("THREAD-B") {

            @Override
            public void run() {
                try {
                    locks[1] = lockerThreadB.lock(LOCK_SET_1, LockingElement.PERSOON, LockingMode.EXCLUSIVE, 3);
                } catch (final BrpLockerExceptie e) {
                    LOGGER.info(TEST_GESLAAGD_WRITE_LOCK_MAG_NIET_WORDEN_VERKREGEN, e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadB,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[1] = connectieThreadLocal.get();
                }
            }
        };

        threadA.start();
        threadA.join();

        threadB.start();
        threadB.join();

        // Release alle locks.
        for (final Connection connectie : connecties) {
            if (!connectie.isClosed()) {
                connectie.commit();
                connectie.close();
            }
        }

        Assert.assertTrue("Thread A moet een read lock krijgen!", locks[0]);
        Assert.assertNull("Thread B mag absoluut geen write lock verkrijgen.", locks[1]);
        Assert.assertTrue(THREAD_A_LOCK_HAD_NOG_AANWEZIG_MOETEN_ZIJN, locksAanwezig[0]);
    }

    @Test
    public void testWriteLockBlokkeertReadLock() throws BrpLockerExceptie, InterruptedException, SQLException {
        final Boolean[] locks = new Boolean[2];
        final Boolean[] locksAanwezig = new Boolean[2];
        // We testen hier puur de locking en niet het unlocken, het unlocken laten we niet aan onze threads over
        // maar houden we zelf in de hand. We roepen dus nergens unlock aan in de thread omdat we anders het
        // exclusief locken niet kunnen testen.
        final Connection[] connecties = new Connection[2];
        final Thread threadA = new Thread() {

            @Override
            public void run() {
                try {
                    locks[0] = lockerThreadA.lock(LOCK_SET_2, LockingElement.PERSOON, LockingMode.EXCLUSIVE, 3);
                    locksAanwezig[0] = lockerThreadA.isLockNogAanwezig();
                } catch (final BrpLockerExceptie e) {
                    LOGGER.error(LOCK_IS_NIET_VERKREGEN, e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadA,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[0] = connectieThreadLocal.get();
                }
            }

        };

        final Thread threadB = new Thread() {

            @Override
            public void run() {
                try {
                    locks[1] = lockerThreadB.lock(LOCK_SET_2, LockingElement.PERSOON, LockingMode.SHARED, 3);
                } catch (final BrpLockerExceptie e) {
                    LOGGER.info("Test geslaagd, read lock mag niet worden verkregen.", e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadB,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[1] = connectieThreadLocal.get();
                }
            }
        };

        threadA.start();
        threadA.join();
        threadB.start();
        threadB.join();

        // Release alle locks.
        for (final Connection connectie : connecties) {
            if (!connectie.isClosed()) {
                connectie.commit();
                connectie.close();
            }
        }

        Assert.assertTrue("Thread A moet een write lock krijgen!", locks[0]);
        Assert.assertNull("Thread B mag absoluut geen read lock verkrijgen.", locks[1]);
        Assert.assertTrue(THREAD_A_LOCK_HAD_NOG_AANWEZIG_MOETEN_ZIJN, locksAanwezig[0]);
    }

    @Test
    public void testReadLockBlokkeertReadLockNiet() throws InterruptedException, SQLException {
        final Boolean[] locks = new Boolean[2];
        final Boolean[] locksAanwezig = new Boolean[2];
        // We testen hier puur de locking en niet het unlocken, het unlocken laten we niet aan onze threads over
        // maar houden we zelf in de hand. We roepen dus nergens unlock aan in de thread omdat we anders het
        // exclusief locken niet kunnen testen.
        final Connection[] connecties = new Connection[2];
        final Thread threadA = new Thread() {

            @Override
            public void run() {
                try {
                    locks[0] = lockerThreadA.lock(LOCK_SET_3, LockingElement.PERSOON, LockingMode.SHARED, 3);
                    locksAanwezig[0] = lockerThreadA.isLockNogAanwezig();
                } catch (final BrpLockerExceptie e) {
                    LOGGER.error(LOCK_IS_NIET_VERKREGEN, e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadA,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[0] = connectieThreadLocal.get();
                }
            }
        };

        final Thread threadB = new Thread() {

            @Override
            public void run() {
                try {
                    locks[1] = lockerThreadB.lock(LOCK_SET_3, LockingElement.PERSOON, LockingMode.SHARED, 3);
                    locksAanwezig[1] = lockerThreadB.isLockNogAanwezig();
                    LOGGER.info("Test geslaagd, read lock mag worden verkregen.");
                } catch (final BrpLockerExceptie e) {
                    LOGGER.error("Thread B moet een read lock verkrijgen.", e);
                } finally {
                    final ThreadLocal<Connection> connectieThreadLocal =
                        (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadB,
                                TRANSACTIONSTATUSTHREADLOCAL);
                    connecties[1] = connectieThreadLocal.get();
                }
            }
        };

        threadA.start();
        threadA.join();
        threadB.start();
        threadB.join();

        // Release alle locks.
        for (final Connection connectie : connecties) {
            if (!connectie.isClosed()) {
                connectie.commit();
                connectie.close();
            }
        }

        Assert.assertTrue("Thread A moet een read lock verkrijgen!", locks[0]);
        Assert.assertTrue("Thread B moet ook een read lock verkrijgen!", locks[1]);
        Assert.assertTrue(THREAD_A_LOCK_HAD_NOG_AANWEZIG_MOETEN_ZIJN, locksAanwezig[0]);
        Assert.assertTrue("Thread B lock had nog aanwezig moeten zijn!", locksAanwezig[1]);
    }

    @Test
    public void testWriteLockBlokkeertWriteLock() throws BrpLockerExceptie, InterruptedException, SQLException {
        final Boolean[] locks = new Boolean[2];
        // We testen hier puur de locking en niet het unlocken, het unlocken laten we niet aan onze threads over
        // maar houden we zelf in de hand. We roepen dus nergens unlock aan in de thread omdat we anders het
        // exclusief locken niet kunnen testen.
        final Connection[] connecties = new Connection[2];

        try {
            final Thread threadA = new Thread() {

                @Override
                public void run() {
                    try {
                        locks[0] = lockerThreadA.lock(LOCK_SET_4, LockingElement.PERSOON, LockingMode.EXCLUSIVE, 3);
                    } catch (final BrpLockerExceptie e) {
                        LOGGER.error(LOCK_IS_NIET_VERKREGEN, e);
                    } finally {
                        final ThreadLocal<Connection> connectieThreadLocal =
                            (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadA,
                                    TRANSACTIONSTATUSTHREADLOCAL);
                        connecties[0] = connectieThreadLocal.get();
                    }
                }

            };

            final Thread threadB = new Thread() {

                @Override
                public void run() {
                    try {
                        locks[1] = lockerThreadB.lock(LOCK_SET_4, LockingElement.PERSOON, LockingMode.EXCLUSIVE, 3);
                    } catch (final BrpLockerExceptie e) {
                        LOGGER.info(TEST_GESLAAGD_WRITE_LOCK_MAG_NIET_WORDEN_VERKREGEN, e);
                    } finally {
                        final ThreadLocal<Connection> connectieThreadLocal =
                            (ThreadLocal<Connection>) ReflectionTestUtils.getField(lockerThreadB,
                                    TRANSACTIONSTATUSTHREADLOCAL);
                        connecties[1] = connectieThreadLocal.get();
                    }
                }

            };

            threadA.start();
            threadA.join();
            threadB.start();
            threadB.join();

            // Release alle locks.
            for (final Connection connectie : connecties) {
                if (!connectie.isClosed()) {
                    connectie.commit();
                }
            }

            Assert.assertTrue("Thread A moet een write lock verkrijgen!", locks[0]);
            Assert.assertNull("Thread B mag absoluut geen write lock verkrijgen!", locks[1]);
        } finally {
            for (final Connection connectie : connecties) {
                connectie.close();
            }
        }
    }
}
