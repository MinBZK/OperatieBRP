/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.vergrendeling;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor het vergrendelen op de database
 */
@RunWith(PowerMockRunner.class)
public class SleutelVergrendelaarTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SleutelVergrendelaarTest.class);

    @Mock
    private Connection connectie;
    @Mock
    private DataSource dataSource;
    @Mock
    private Statement statement;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ParameterMetaData parameterMetaData;
    @Mock
    private ResultSet resultSet;

    private SleutelVergrendelaar sleutelVergrendelaar;

    @Test
    public void vergrendelVoorLezen() throws SQLException {
        sleutelVergrendelaar.vergrendel(Arrays.asList(1, 2), null, SleutelVergrendelaar.VergrendelMode.GEDEELD);
        Mockito.verify(connectie).rollback();
        Mockito.verify(statement, Mockito.times(1)).close();
        Mockito.verify(preparedStatement, Mockito.times(2)).close();
        Mockito.verify(connectie, Mockito.times(2)).prepareStatement("SELECT pg_advisory_xact_lock_shared(?)");
    }

    @Test
    public void vergrendelVoorSchrijven() throws SQLException {
        sleutelVergrendelaar.vergrendel(Arrays.asList(1, 2), null, SleutelVergrendelaar.VergrendelMode.EXCLUSIEF);
        Mockito.verify(connectie).rollback();
        Mockito.verify(statement, Mockito.times(1)).close();
        Mockito.verify(preparedStatement, Mockito.times(2)).close();
        Mockito.verify(connectie, Mockito.times(2)).prepareStatement("SELECT pg_advisory_xact_lock(?)");
    }


    @Test
    public void connectieSluitenTestMetExceptieInAanmakenPreparedStatement() throws SQLException {
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenThrow(new VergrendelFout());
        try {
            sleutelVergrendelaar.vergrendel(Arrays.asList(1, 2), null, SleutelVergrendelaar.VergrendelMode.GEDEELD);
        } catch (VergrendelFout e) {
            LOGGER.debug("Fout bij het vergrendelen", e);
        }

        Mockito.verify(connectie, Mockito.times(2)).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Test
    public void connectieSluitenTestMetExceptieInCreatieTimeoutStatement() throws SQLException {
        Mockito.when(connectie.createStatement()).thenThrow(new SQLException());
        try {
            sleutelVergrendelaar.vergrendel(Arrays.asList(3), "EBCGFD-HDGER-JDJDF",
                                            SleutelVergrendelaar.VergrendelMode.EXCLUSIEF);
        } catch (VergrendelFout e) {
            LOGGER.debug("Fout bij het vergrendelen", e);
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
            sleutelVergrendelaar
                    .vergrendel(Arrays.asList(1, 2), "2387429384723", SleutelVergrendelaar.VergrendelMode.EXCLUSIEF);
        } catch (VergrendelFout e) {
            LOGGER.debug("Fout bij het vergrendelen", e);
        }

        Mockito.verify(connectie, Mockito.times(2)).rollback();
        Mockito.verify(connectie).close();
        Mockito.verify(statement).close();
    }

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        sleutelVergrendelaar = new SleutelVergrendelaarImpl();
        sleutelVergrendelaar.ontgrendel();

        ReflectionTestUtils.setField(sleutelVergrendelaar, "dataSource", dataSource);
        ReflectionTestUtils.setField(sleutelVergrendelaar, "statementTimeOut", 10);

        Mockito.when(dataSource.getConnection()).thenReturn(connectie);
        Mockito.when(connectie.createStatement()).thenReturn(statement);
        Mockito.when(connectie.prepareStatement(Matchers.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.getParameterMetaData()).thenReturn(parameterMetaData);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        Mockito.when(parameterMetaData.getParameterCount()).thenReturn(1, 1, 1, 0);
    }

}
