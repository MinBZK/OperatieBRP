/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.sql.DataSource;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link BrpLockerImpl} class, waar specifiek het correct sluiten van de connectie in wordt getest.
 */
@Category(OverslaanBijInMemoryDatabase.class)
@RunWith(PowerMockRunner.class)
public class BrpLockerImplConnectieSluitTest {

    private static final Logger LOGGER          = LoggerFactory.getLogger();

    private static final int    DEFAULT_TIMEOUT = 2;

    @Mock
    private Connection          connectie;
    @Mock
    private DataSource          dataSource;
    @Mock
    private Statement           statement;
    @Mock
    private PreparedStatement   preparedStatement;
    @Mock
    private ParameterMetaData   parameterMetaData;
    @Mock
    private ResultSet           resultSet;

    private BrpLocker           brpLocker;

    @Test
    public void connectieSluitenTestNormaleFlow() throws BrpLockerExceptie, SQLException {
        brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT);
        brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.EXCLUSIVE, DEFAULT_TIMEOUT);
        Mockito.verify(connectie).rollback();
        Mockito.verify(statement, Mockito.times(1)).close();
        Mockito.verify(preparedStatement, Mockito.times(3)).close();
        Mockito.verify(connectie, Mockito.times(2)).prepareStatement("SELECT pg_advisory_xact_lock_shared(?,?)");
        Mockito.verify(connectie, Mockito.times(1)).prepareStatement("SELECT pg_advisory_xact_lock(?,?)");
    }

    @Test
    public void connectieSluitenTestMetExceptieInAanmakenPreparedStatement() throws SQLException {
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenThrow(new SQLException());
        try {
            brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT);
        } catch (final BrpLockerExceptie e) {
            LOGGER.error(e.getMessage(), e);
        }

        Mockito.verify(connectie, Mockito.times(2)).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInCreatieTimeoutStatement() throws SQLException {
        Mockito.when(connectie.createStatement()).thenThrow(new SQLException());
        try {
            brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT);
        } catch (final BrpLockerExceptie e) {
            LOGGER.error(e.getMessage(), e);
        }

        Mockito.verify(connectie, Mockito.times(2)).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(connectie).createStatement();
        Mockito.verify(statement, Mockito.never()).execute(Matchers.anyString());
        Mockito.verify(statement, Mockito.never()).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInExecuterenTimeoutStatement() throws SQLException {
        Mockito.when(statement.execute(Matchers.anyString())).thenThrow(new SQLException());
        try {
            brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT);
        } catch (final BrpLockerExceptie e) {
            LOGGER.error(e.getMessage(), e);
        }

        Mockito.verify(connectie, Mockito.times(2)).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInUnlock() throws SQLException {
        Mockito.doThrow(new SQLException()).when(connectie).commit();
        try {
            assertTrue(brpLocker.lock(Arrays.asList(1, 2), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
            brpLocker.unLock();
        } catch (final BrpLockerExceptie e) {
            LOGGER.error(e.getMessage(), e);
        }

        Mockito.verify(connectie).close();
    }

    @Before
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);
        brpLocker = new BrpLockerImpl();
        brpLocker.unLock();

        ReflectionTestUtils.setField(brpLocker, "dataSource", dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connectie);
        Mockito.when(connectie.createStatement()).thenReturn(statement);
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.getParameterMetaData()).thenReturn(parameterMetaData);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        Mockito.when(parameterMetaData.getParameterCount()).thenReturn(2, 2, 2, 0);
    }

    @After
    public void cleanup() {
        brpLocker.unLock();
    }
}
