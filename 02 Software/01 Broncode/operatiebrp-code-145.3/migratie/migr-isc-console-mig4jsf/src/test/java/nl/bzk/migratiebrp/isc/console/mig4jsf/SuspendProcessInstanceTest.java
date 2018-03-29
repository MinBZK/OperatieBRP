/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSuspendCommand;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SuspendProcessInstanceTest extends AbstractTagTest {

    @Test
    public void testOk() throws Exception {
        // Setup
        setupMockDatabase();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        // Parameters
        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(processInstance.getId()).thenReturn(42L);
        final Token rootToken = Mockito.mock(Token.class);
        Mockito.when(processInstance.getRootToken()).thenReturn(rootToken);
        addTagAttribute("processInstance", processInstance);

        // Execute
        final JbpmActionListener subject = initializeSubject(SuspendProcessInstanceHandler.class);
        Assert.assertEquals("suspendProcessInstance", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        final ArgumentCaptor<JbpmSuspendCommand> commandCaptor = ArgumentCaptor.forClass(JbpmSuspendCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        final JbpmSuspendCommand command = commandCaptor.getValue();
        Assert.assertEquals(Long.valueOf(42L), ReflectionTestUtils.getField(command, "processInstanceId"));
    }

    @Test
    public void testNull() throws Exception {
        setupMockDatabase();

        addTagAttribute("processInstance", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(SuspendProcessInstanceHandler.class);
        Assert.assertEquals("suspendProcessInstance", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext).setError("Het proces kon niet worden opgeschort.", "Geen process instantie doorgegeven");

        Assert.assertFalse("success".equals(jbpmJsfContext.getOutcome()));
    }
}
