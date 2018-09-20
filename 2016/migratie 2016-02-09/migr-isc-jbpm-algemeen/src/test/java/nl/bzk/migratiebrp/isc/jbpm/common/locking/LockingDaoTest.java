/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.locking;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import nl.bzk.migratiebrp.isc.jbpm.common.JbpmDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@Ignore
public class LockingDaoTest {
    private final LockingDao subject = new JbpmLockingDao();

    private JbpmContext jbpmContext;

    private Session session;
    private Connection connection;

    @Before
    public void setup() throws Exception {
        // Setup jbpm context
        jbpmContext = Mockito.mock(JbpmContext.class);

        final Field jbpmConfigurationField = JbpmDao.class.getDeclaredField("JBPM_CONFIGURATION");
        jbpmConfigurationField.setAccessible(true);
        final JbpmConfiguration jbpmConfiguration = (JbpmConfiguration) jbpmConfigurationField.get(null);

        // Link jbpm context to subject
        final Method pushJbpmContextMethod = JbpmConfiguration.class.getDeclaredMethod("pushJbpmContext", JbpmContext.class);
        pushJbpmContextMethod.setAccessible(true);
        pushJbpmContextMethod.invoke(jbpmConfiguration, jbpmContext);

        // Session
        session = Mockito.mock(Session.class);
        connection = Mockito.mock(Connection.class);
        Mockito.when(jbpmContext.getSession()).thenReturn(session);
        Mockito.when(session.getTransaction()).thenReturn(Mockito.mock(Transaction.class));
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws SQLException {
                final Work work = (Work) invocation.getArguments()[0];
                work.execute(connection);
                return null;
            }
        }).when(session).doWork(Matchers.<Work>any());
    }

    @After
    public void tearDown() throws Exception {
        final Field jbpmConfigurationField = JbpmDao.class.getDeclaredField("JBPM_CONFIGURATION");
        jbpmConfigurationField.setAccessible(true);
        final JbpmConfiguration jbpmConfiguration = (JbpmConfiguration) jbpmConfigurationField.get(null);

        // Link jbpm context to subject
        final Method popJbpmContextMethod = JbpmConfiguration.class.getDeclaredMethod("popJbpmContext", JbpmContext.class);
        popJbpmContextMethod.setAccessible(true);
        popJbpmContextMethod.invoke(jbpmConfiguration, jbpmContext);
    }

    @Test
    public void testUnlock() throws Exception {
        final PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement("DELETE FROM mig_lock WHERE id = ?")).thenReturn(statement);

        subject.unlock(32);

        Mockito.verify(statement).setLong(1, 32);
        Mockito.verify(statement).execute();
        Mockito.verify(statement).close();
    }

    @Test
    public void testLock() throws Exception {
        final Long lockId = 42L;
        // Prepare statements
        final Statement statement = Mockito.mock(Statement.class);
        Mockito.when(connection.createStatement()).thenReturn(statement);

        final PreparedStatement lockStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement("INSERT INTO mig_lock(tijdstip, process_instance_id) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS))
               .thenReturn(lockStatement);
        final ResultSet lockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(lockStatement.getGeneratedKeys()).thenReturn(lockResultSet);
        Mockito.when(lockResultSet.getLong(1)).thenReturn(lockId);

        final PreparedStatement anummerStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement("INSERT INTO mig_lock_anummer(tijdstip, lock_id, anummer) VALUES(?, ?, ?)")).thenReturn(anummerStatement);

        // Execute
        final Set<Long> anummers = new LinkedHashSet<>();
        anummers.add(123L);
        anummers.add(456L);
        Assert.assertEquals(lockId, subject.lock(5442312L, anummers));

        // Verify
        final InOrder inOrder = Mockito.inOrder(statement, lockStatement, anummerStatement);
        inOrder.verify(statement).executeUpdate("SAVEPOINT make_lock");
        inOrder.verify(statement).close();

        inOrder.verify(lockStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(lockStatement).setLong(2, 5442312L);
        inOrder.verify(lockStatement).executeUpdate();
        inOrder.verify(lockStatement).getGeneratedKeys();
        inOrder.verify(lockStatement).close();

        inOrder.verify(anummerStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(anummerStatement).setLong(2, 42L);
        inOrder.verify(anummerStatement).setLong(3, 123L);
        inOrder.verify(anummerStatement).executeUpdate();
        inOrder.verify(anummerStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(anummerStatement).setLong(2, 42L);
        inOrder.verify(anummerStatement).setLong(3, 456L);
        inOrder.verify(anummerStatement).executeUpdate();
        inOrder.verify(anummerStatement).close();

        Mockito.verifyNoMoreInteractions(statement);
        Mockito.verifyNoMoreInteractions(lockStatement);
        Mockito.verifyNoMoreInteractions(anummerStatement);
    }

    @Test
    public void testLockFailure() throws Exception {
        final Long lockId = 42L;
        // Prepare statements
        final Statement statement = Mockito.mock(Statement.class);
        Mockito.when(connection.createStatement()).thenReturn(statement);

        final PreparedStatement lockStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement("INSERT INTO mig_lock(tijdstip, process_instance_id) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS))
               .thenReturn(lockStatement);
        final ResultSet lockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(lockStatement.getGeneratedKeys()).thenReturn(lockResultSet);
        Mockito.when(lockResultSet.getLong(1)).thenReturn(lockId);

        final PreparedStatement anummerStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement("INSERT INTO mig_lock_anummer(tijdstip, lock_id, anummer) VALUES(?, ?, ?)")).thenReturn(anummerStatement);

        // Gooi fout! Het anummer is al gelocked (uniquekey dingetje)
        Mockito.when(anummerStatement.executeUpdate()).thenReturn(1).thenThrow(SQLException.class);

        // Execute
        final Set<Long> anummers = new LinkedHashSet<>();
        anummers.add(123L);
        anummers.add(456L);
        Assert.assertEquals(null, subject.lock(5442312L, anummers));

        // Verify
        final InOrder inOrder = Mockito.inOrder(statement, lockStatement, anummerStatement);
        inOrder.verify(statement).executeUpdate("SAVEPOINT make_lock");
        inOrder.verify(statement).close();

        inOrder.verify(lockStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(lockStatement).setLong(2, 5442312L);
        inOrder.verify(lockStatement).executeUpdate();
        inOrder.verify(lockStatement).getGeneratedKeys();
        inOrder.verify(lockStatement).close();

        inOrder.verify(anummerStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(anummerStatement).setLong(2, 42L);
        inOrder.verify(anummerStatement).setLong(3, 123L);
        inOrder.verify(anummerStatement).executeUpdate();
        inOrder.verify(anummerStatement).setTimestamp(Matchers.eq(1), Matchers.any(Timestamp.class));
        inOrder.verify(anummerStatement).setLong(2, 42L);
        inOrder.verify(anummerStatement).setLong(3, 456L);
        inOrder.verify(anummerStatement).executeUpdate();
        inOrder.verify(anummerStatement).close();

        inOrder.verify(statement).executeUpdate("ROLLBACK TO SAVEPOINT make_lock");
        inOrder.verify(statement).close();

        Mockito.verifyNoMoreInteractions(statement);
        Mockito.verifyNoMoreInteractions(lockStatement);
        Mockito.verifyNoMoreInteractions(anummerStatement);
    }
}
