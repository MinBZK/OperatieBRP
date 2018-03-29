/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.Collections;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmTaakCommand;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.impl.UpdatesHashMap;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class VerwerkTaakTest extends AbstractTagTest {

    @Test
    public void testOk() throws Exception {
        setupMockDatabase();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        final UpdatesHashMap variableMap = new UpdatesHashMap();
        variableMap.put("restart", "afkeuren");

        addTagAttribute("id", (long) 4567);
        addTagAttribute("transition", "ok");
        addTagAttribute("comment", "Test");
        addTagAttribute("variableMap", variableMap);

        // Execute
        final JbpmActionListener subject = initializeSubject(VerwerkTaakHandler.class);
        Assert.assertEquals("verwerkTaak", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<JbpmTaakCommand> commandCaptor = ArgumentCaptor.forClass(JbpmTaakCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        final JbpmTaakCommand command = commandCaptor.getValue();
        Assert.assertEquals(Long.valueOf(4567L), ReflectionTestUtils.getField(command, "taakId"));
        Assert.assertEquals(Collections.emptySet(), ReflectionTestUtils.getField(command, "taakVariabelenVerwijderd"));
        Assert.assertEquals(variableMap, ReflectionTestUtils.getField(command, "taakVariabelenGewijzigd"));
        Assert.assertEquals("Test", ReflectionTestUtils.getField(command, "commentaar"));
        Assert.assertEquals("ok", ReflectionTestUtils.getField(command, "transitieNaam"));
    }

    @Test
    public void testTaakIdNull() throws Exception {
        setupMockDatabase();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        addTagAttribute("id", null);
        addTagAttribute("transition", null);
        addTagAttribute("comment", null);
        addTagAttribute("variableMap", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(VerwerkTaakHandler.class);
        Assert.assertEquals("verwerkTaak", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext).setError("Fout bij het voltooien van de taak.", "De geselecteerde taak is niet geldig.");

        Assert.assertFalse("success".equals(jbpmJsfContext.getOutcome()));
    }

    @Test
    public void testAllesNullBehalveId() throws Exception {
        setupMockDatabase();
        setupMockSpringService();

        final CommandClient commandClient = Mockito.mock(CommandClient.class);
        Mockito.when(beanFactory.getBean(CommandClient.class)).thenReturn(commandClient);

        addTagAttribute("id", (long) 4567);
        addTagAttribute("transition", null);
        addTagAttribute("comment", null);
        addTagAttribute("variableMap", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(VerwerkTaakHandler.class);
        Assert.assertEquals("verwerkTaak", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<JbpmTaakCommand> commandCaptor = ArgumentCaptor.forClass(JbpmTaakCommand.class);
        Mockito.verify(commandClient).executeCommand(commandCaptor.capture());
        Assert.assertTrue("success".equals(jbpmJsfContext.getOutcome()));

        final JbpmTaakCommand command = commandCaptor.getValue();
        Assert.assertEquals(Long.valueOf(4567L), ReflectionTestUtils.getField(command, "taakId"));
        Assert.assertEquals(Collections.emptySet(), ReflectionTestUtils.getField(command, "taakVariabelenVerwijderd"));
        Assert.assertEquals(Collections.emptyMap(), ReflectionTestUtils.getField(command, "taakVariabelenGewijzigd"));
        Assert.assertEquals(null, ReflectionTestUtils.getField(command, "commentaar"));
        Assert.assertEquals(null, ReflectionTestUtils.getField(command, "transitieNaam"));
    }
}
