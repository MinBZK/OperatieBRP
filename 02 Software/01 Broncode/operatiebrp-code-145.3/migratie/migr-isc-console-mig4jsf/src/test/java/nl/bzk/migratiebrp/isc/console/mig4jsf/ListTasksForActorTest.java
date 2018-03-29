/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

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
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class, InitialContext.class, ConfigurationLocator.class, Configuration.class, Subject.class})
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
        final List<TaskInstance> groupTasks = Arrays.asList(new TaskInstance[]{groupTask1, groupTask2,});

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
        final Principal userPrincipal = new Principal() {

            @Override
            public String getName() {
                return "userPrincipal";
            }
        };
        Mockito.when(externalContext.getUserPrincipal()).thenReturn(userPrincipal);

        PowerMockito.mockStatic(ConfigurationLocator.class);
        final Configuration configuration = PowerMockito.mock(Configuration.class);
        Mockito.when(ConfigurationLocator.getInstance()).thenReturn(configuration);
        Mockito.when(configuration.useJsfActorId()).thenReturn(true);

        final Subject subject = PowerMockito.mock(Subject.class);

        final Principal role1 = makePrincipal("role1");
        subject.getPrincipals().add(role1);
        final Principal role2 = makePrincipal("role2");
        subject.getPrincipals().add(role2);
        final Enumeration<Principal> roleEnumeration = Collections.<Principal>enumeration(Arrays.asList(role1, role2));
        final java.security.acl.Group group = new TestGroup(roleEnumeration);
        // subject.getPrincipals(java.security.acl.Group.class).add(group);
        PowerMockito.when(subject.getPrincipals(java.security.acl.Group.class)).thenReturn(Collections.singleton(group));
        PowerMockito.when(MockedContextFactory.CONTEXT.lookup(Mockito.anyString())).thenReturn(subject);
    }

    private class TestGroup implements java.security.acl.Group {

        private final Enumeration<? extends Principal> roles;

        public TestGroup(final Enumeration<? extends Principal> roles) {
            this.roles = roles;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean addMember(final Principal user) {
            return false;
        }

        @Override
        public boolean isMember(final Principal member) {
            return false;
        }

        @Override
        public Enumeration<? extends Principal> members() {
            return roles;
        }

        @Override
        public boolean removeMember(final Principal user) {
            return false;
        }

    }

    private Principal makePrincipal(final String name) {
        final Principal principal = new Principal() {
            @Override
            public String getName() {
                return name;
            }

            ;
        };

        return principal;
    }

    public static class MockedContextFactory implements InitialContextFactory {
        private static final Context CONTEXT = PowerMockito.mock(Context.class);

        @Override
        public Context getInitialContext(final Hashtable<?, ?> env) {
            return CONTEXT;
        }
    }
}
