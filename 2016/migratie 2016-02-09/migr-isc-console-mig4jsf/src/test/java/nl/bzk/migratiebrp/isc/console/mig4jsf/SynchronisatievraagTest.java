/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.console.mig4jsf.util.ValidationUtil;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSynchronisatievraagCommand;
import org.jbpm.JbpmConfiguration.Configs;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.msg.MessageService;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;
import org.jbpm.svc.Services;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;

@PrepareForTest(Configs.class)
public class SynchronisatievraagTest extends AbstractTagTest {

    private ProcessInstance processInstance;
    private ContextInstance contextInstance;
    private MessageService messageService;
    private JbpmActionListener subject;

    @Before
    public void setupConfigs() {
        PowerMockito.mockStatic(Configs.class);
        Mockito.when(Configs.getInt("jbpm.job.retries")).thenReturn(6);
    }

    private void setupSubject(final String gemeente, final String anummer, final String bulk) throws Exception {
        addTagAttribute("gemeente", gemeente);
        addTagAttribute("aNummer", anummer);
        addTagAttribute("bulkBestand", bulk == null ? null : bulk.getBytes());
        addTagAttribute("target", null);

        setupDatabase(
            "/sql/mig-drop.sql",
            "/sql/jbpm-drop.sql",
            "/sql/jbpm-create.sql",
            "/sql/mig-create.sql",
            "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-berichten.sql");

        // Execute
        subject = initializeSubject(SynchronisatievraagHandler.class);
        Assert.assertEquals("synchronisatievraag", subject.getName());
    }

    private void setupProcess(final String processDefinitionName) {
        processInstance = Mockito.mock(ProcessInstance.class);
        contextInstance = Mockito.mock(ContextInstance.class);
        final Token rootToken = Mockito.mock(Token.class);

        Mockito.when(jbpmContext.newProcessInstance(processDefinitionName)).thenReturn(processInstance);
        Mockito.when(processInstance.getContextInstance()).thenReturn(contextInstance);
        Mockito.when(processInstance.getRootToken()).thenReturn(rootToken);
        Mockito.when(rootToken.getProcessInstance()).thenReturn(processInstance);
        Mockito.when(processInstance.getId()).thenReturn(4321L);
    }

    private void setupMessageService() {
        messageService = Mockito.mock(MessageService.class);

        final Map<String, Object> serviceFactories = new HashMap<>();
        serviceFactories.put(Services.SERVICENAME_MESSAGE, new ServiceFactory() {
            private static final long serialVersionUID = 1L;

            @Override
            public Service openService() {
                return messageService;
            }

            @Override
            public void close() {
            }
        });

        final Services services = new Services(serviceFactories);
        Mockito.when(jbpmContext.getServices()).thenReturn(services);
    }

    @Test
    public void testUc811() throws Exception {
        final String anummer = "4398684193";
        Assert.assertTrue(ValidationUtil.valideerANummer(anummer));
        setupProcess("uc811");
        setupMessageService();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        // Execute
        setupSubject("0599", anummer, null);
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<JbpmSynchronisatievraagCommand> commandCaptor = ArgumentCaptor.forClass(JbpmSynchronisatievraagCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        //
        final JbpmSynchronisatievraagCommand command = commandCaptor.getValue();
        Assert.assertEquals("uc811", ReflectionTestUtils.getField(command, "processDefinitionName"));

    }

    @Test
    public void testUc812() throws Exception {
        final String anummer = "4398684193";
        Assert.assertTrue(ValidationUtil.valideerANummer(anummer));
        setupProcess("uc812");
        setupMessageService();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        final String bulkInhoud = "0599," + anummer;

        // Execute
        setupSubject(null, null, bulkInhoud);
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<JbpmSynchronisatievraagCommand> commandCaptor = ArgumentCaptor.forClass(JbpmSynchronisatievraagCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        //
        final JbpmSynchronisatievraagCommand command = commandCaptor.getValue();
        Assert.assertEquals("uc812", ReflectionTestUtils.getField(command, "processDefinitionName"));
    }
}
