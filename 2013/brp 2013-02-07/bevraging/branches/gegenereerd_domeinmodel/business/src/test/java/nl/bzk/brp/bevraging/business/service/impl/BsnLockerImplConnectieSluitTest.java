/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.sql.DataSource;

import nl.bzk.brp.bevraging.business.configuratie.BrpConfiguratie;
import nl.bzk.brp.bevraging.business.service.BsnLocker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link BsnLockerImpl} class, waar specifiek het correct sluiten van de connectie in wordt
 * getest.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BrpConfiguratie.class)
public class BsnLockerImplConnectieSluitTest {

    @Mock
    private Connection        connectie;
    @Mock
    private DataSource        dataSource;
    @Mock
    private Statement         statement;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ParameterMetaData parameterMetaData;
    @Mock
    private ResultSet         resultSet;
    @Mock
    private BrpConfiguratie   brpConfiguratie;

    private BsnLocker         bsnLocker;

    @Test
    public void connectieSluitenTestNormaleFlow() throws SQLException {
        bsnLocker.getLocks(2L, Arrays.asList("1", "2"), Arrays.asList("3"));
        Mockito.verify(connectie, Mockito.atLeastOnce()).close();
        Mockito.verify(statement, Mockito.times(2)).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInAanmakenPreparedStatement() throws SQLException {
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenThrow(new SQLException());
        bsnLocker.getLocks(2L, Arrays.asList("1", "2"), Arrays.asList("3"));

        Mockito.verify(connectie).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInCreatieTimeoutStatement() throws SQLException {
        Mockito.when(connectie.createStatement()).thenThrow(new SQLException());
        bsnLocker.getLocks(2L, Arrays.asList("1", "2"), Arrays.asList("3"));

        Mockito.verify(connectie).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(connectie).createStatement();
        Mockito.verify(statement, Mockito.never()).execute(Matchers.anyString());
        Mockito.verify(statement, Mockito.never()).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInExecuterenTimeoutStatement() throws SQLException {
        Mockito.when(statement.execute(Matchers.anyString())).thenThrow(new SQLException());
        bsnLocker.getLocks(2L, Arrays.asList("1", "2"), Arrays.asList("3"));

        Mockito.verify(connectie).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        bsnLocker = new BsnLockerImpl();
        bsnLocker.unLock();

        ReflectionTestUtils.setField(bsnLocker, "dataSource", dataSource);
        ReflectionTestUtils.setField(bsnLocker, "brpConfiguratie", brpConfiguratie);
        Mockito.when(brpConfiguratie.getDatabaseTimeOutProperty()).thenReturn(5);
        Mockito.when(dataSource.getConnection()).thenReturn(connectie);
        Mockito.when(connectie.createStatement()).thenReturn(statement);
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.getParameterMetaData()).thenReturn(parameterMetaData);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        Mockito.when(parameterMetaData.getParameterCount()).thenReturn(1, 1, 1, 0);
    }
}
