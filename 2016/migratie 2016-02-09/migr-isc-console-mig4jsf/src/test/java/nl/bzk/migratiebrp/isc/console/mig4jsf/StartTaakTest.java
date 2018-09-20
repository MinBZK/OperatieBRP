/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmStartTaakCommand;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class StartTaakTest extends AbstractTagTest {

    @Test
    public void testOk() throws Exception {
        // Setup
        setupMockDatabase();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        // Parameters
        addTagAttribute("task", (long) 4567);
        addTagAttribute("actorId", "beheerder");

        // Execute
        final JbpmActionListener subject = initializeSubject(StartTaakHandler.class);
        Assert.assertEquals("startTaak", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<JbpmStartTaakCommand> commandCaptor = ArgumentCaptor.forClass(JbpmStartTaakCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        final JbpmStartTaakCommand command = commandCaptor.getValue();
        Assert.assertEquals(Long.valueOf(4567L), ReflectionTestUtils.getField(command, "taakId"));
        Assert.assertEquals("beheerder", ReflectionTestUtils.getField(command, "actorId"));
    }

    @Test
    public void testNull() throws Exception {
        setupMockDatabase();

        addTagAttribute("task", null);
        addTagAttribute("actorId", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(StartTaakHandler.class);
        Assert.assertEquals("startTaak", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext).setError("Fout bij het voltooien van de taak.", "De geselecteerde taak is niet geldig.");

        Assert.assertFalse("success".equals(jbpmJsfContext.getOutcome()));
    }

}
