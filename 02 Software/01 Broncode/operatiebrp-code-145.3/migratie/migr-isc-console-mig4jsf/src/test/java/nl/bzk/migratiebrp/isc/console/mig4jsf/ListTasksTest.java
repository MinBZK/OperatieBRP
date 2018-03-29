/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.security.auth.Subject;
import nl.bzk.migratiebrp.isc.console.mig4jsf.JbpmSqlHelper.Sql;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.config.Configuration;
import org.jbpm.jsf.core.config.ConfigurationLocator;
import org.jbpm.persistence.PersistenceService;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Query.class, FacesContext.class, InitialContext.class, ConfigurationLocator.class, Configuration.class, Subject.class})
public class ListTasksTest extends AbstractTagTest {

    private Properties oudeSystemProperties;

    @Before
    public void setupContext() {
        oudeSystemProperties = System.getProperties();
        System.setProperty("java.naming.factory.initial", this.getClass().getName() + "$MockedContextFactory");
    }

    @After
    public void teardownContext() {
        System.setProperties(oudeSystemProperties);
    }

    @Test
    public void test() throws Exception {
        addTagAttribute("includeEnded", true);
        addTagAttribute("target", null);

        setupDatabase(
                "/sql/mig-drop.sql",
                "/sql/jbpm-drop.sql",
                "/sql/jbpm-create.sql",
                "/sql/mig-create.sql",
                "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-jbpm_taskinstance.sql");

        final String SQL_ENDED = "select id_, class_, version_, actorid_, start_, end_ from jbpm_taskinstance ti";
        final List<TaskInstance> gevondenTaken = getTaskInstance(jbpmContext, SQL_ENDED);

        final Session mockedSession = Mockito.mock(Session.class);
        final Query mockedQuery = PowerMockito.mock(Query.class);

        Mockito.when(jbpmJsfContext.getJbpmContext().getSession()).thenReturn(mockedSession);

        // Session kan ook via PersistenceService worden opgehaald
        final Services services = Mockito.mock(Services.class);
        Mockito.when(jbpmContext.getServices()).thenReturn(services);
        final PersistenceService persistenceService = Mockito.mock(PersistenceService.class);
        Mockito.when(services.getPersistenceService()).thenReturn(persistenceService);
        Mockito.when(persistenceService.getCustomSession(Matchers.eq(Session.class))).thenReturn(mockedSession);

        // //
        Mockito.when(mockedSession.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti")).thenReturn(mockedQuery);
        PowerMockito.when(mockedQuery.list()).thenReturn(gevondenTaken);

        // Execute
        final JbpmActionListener subject = initializeSubject(ListTasksHandler.class);
        Assert.assertEquals("listTasks", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        @SuppressWarnings("unchecked")
        final List<TaskInstance> tasks = (List<TaskInstance>) getExpressionValues().get("target");
        Assert.assertNotNull(tasks);
        Assert.assertEquals(1, tasks.size());
    }

    private List<TaskInstance> getTaskInstance(final JbpmContext jbpmContext, final String query) {
        return JbpmSqlHelper.execute(jbpmContext, new Sql<List<TaskInstance>>() {

            private final TaskInstanceMapper taskInstanceMapper = new TaskInstanceMapper();

            @Override
            public List<TaskInstance> execute(final Connection connection) throws SQLException {
                final PreparedStatement statement = connection.prepareStatement(query);

                statement.execute();
                final ResultSet rs = statement.getResultSet();

                final List<TaskInstance> taskInstance = new ArrayList<>();
                while (rs.next()) {
                    taskInstance.add(taskInstanceMapper.map(rs));
                }
                statement.close();

                return taskInstance;
            }
        });
    }

    /**
     * TaskInstance mapper.
     */
    private static final class TaskInstanceMapper {

        /**
         * Map een resultset naar een task instance.
         * @param rs result set
         * @return task instance
         * @throws SQLException bij sql fouten
         */
        @SuppressWarnings("deprecation")
        public TaskInstance map(final ResultSet rs) throws SQLException {
            final TaskInstance taskInstance = new TaskInstance();

            // /taskInstance.setId(rs.getLong(1));
            taskInstance.setActorId(rs.getString(4));
            taskInstance.setStart(rs.getDate(5));
            taskInstance.setEnd(rs.getDate(6));

            return taskInstance;
        }
    }

}
