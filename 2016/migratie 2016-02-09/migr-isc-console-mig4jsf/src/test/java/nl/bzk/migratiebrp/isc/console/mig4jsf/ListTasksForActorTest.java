/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.spi.InitialContextFactory;
import javax.security.auth.Subject;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.config.Configuration;
import org.jbpm.jsf.core.config.ConfigurationLocator;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class, InitialContext.class, ConfigurationLocator.class, Configuration.class, Subject.class })
public class ListTasksForActorTest extends AbstractTagTest {

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
        addTagAttribute("actorId", 42L);
        addTagAttribute("target", null);

        setupGetRoles();

        final TaskInstance userTask1 = Mockito.mock(TaskInstance.class);
        Mockito.when(userTask1.getId()).thenReturn(1L);
        final TaskInstance userTask2 = Mockito.mock(TaskInstance.class);
        Mockito.when(userTask2.getId()).thenReturn(2L);
        final List<TaskInstance> userTasks = new ArrayList<>();
        userTasks.add(userTask1);
        userTasks.add(userTask2);

        final TaskInstance groupTask1 = Mockito.mock(TaskInstance.class);
        Mockito.when(groupTask1.getId()).thenReturn(101L);
        final TaskInstance groupTask2 = Mockito.mock(TaskInstance.class);
        Mockito.when(groupTask2.getId()).thenReturn(2L);
        final List<TaskInstance> groupTasks = Arrays.asList(new TaskInstance[] {groupTask1, groupTask2, });

        Mockito.when(jbpmContext.getTaskList("gebruikersnaam")).thenReturn(userTasks);
        final List<String> roles = new ArrayList<>();
        roles.add("role1");
        roles.add("role2");

        Mockito.when(jbpmContext.getGroupTaskList(roles)).thenReturn(groupTasks);

        Mockito.when(jbpmContext.getActorId()).thenReturn("gebruikersnaam");

        // Execute
        final JbpmActionListener subject = initializeSubject(ListTasksForActorHandler.class);
        Assert.assertEquals("listTasksForActor", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        @SuppressWarnings("unchecked")
        final List<TaskInstance> tasks = (List<TaskInstance>) getExpressionValues().get("target");
        Assert.assertNotNull(tasks);
        Assert.assertEquals(3, tasks.size());
    }

    private void setupGetRoles() throws Exception {
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        final Principal userPrincipal = Mockito.mock(Principal.class);
        Mockito.when(externalContext.getUserPrincipal()).thenReturn(userPrincipal);

        PowerMockito.mockStatic(ConfigurationLocator.class);
        final Configuration configuration = PowerMockito.mock(Configuration.class);
        Mockito.when(ConfigurationLocator.getInstance()).thenReturn(configuration);
        Mockito.when(configuration.useJsfActorId()).thenReturn(true);

        final Subject subject = PowerMockito.mock(Subject.class);
        Mockito.when(MockedContextFactory.CONTEXT.lookup(Matchers.anyString())).thenReturn(subject);

        final Set<Group> groups = new HashSet<>();
        final Principal role1 = makePrincipal("role1");
        final Principal role2 = makePrincipal("role2");
        final Group group = makeGroup(role1, role2);
        groups.add(group);
        Mockito.when(subject.getPrincipals(Group.class)).thenReturn(groups);
    }

    private Group makeGroup(final Principal... roles) {
        final Group group = Mockito.mock(Group.class);
        Mockito.when(group.members()).thenAnswer(new Answer<Enumeration<? extends Principal>>() {

            @Override
            public Enumeration<Principal> answer(final InvocationOnMock invocation) throws Throwable {
                return Collections.<Principal>enumeration(Arrays.asList(roles));
            }
        });

        return group;
    }

    private Principal makePrincipal(final String name) {
        final Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn(name);

        return principal;
    }

    public static class MockedContextFactory implements InitialContextFactory {
        private static final Context CONTEXT = Mockito.mock(Context.class);

        @Override
        public Context getInitialContext(final Hashtable<?, ?> env) {
            return CONTEXT;
        }
    }
}
