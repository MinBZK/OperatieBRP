/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.db.LoggingSession;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.log.TransitionLog;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.logging.log.MessageLog;
import org.jbpm.logging.log.ProcessLog;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ListTransitionsForProcessInstanceTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        setupMockDatabase();

        final Map<Token, List<ProcessLog>> logs = maakLogs();

        final LoggingSession loggingSession = Mockito.mock(LoggingSession.class);
        Mockito.when(jbpmContext.getLoggingSession()).thenReturn(loggingSession);
        Mockito.when(loggingSession.findLogsByProcessInstance(4321L)).thenReturn(logs);

        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(processInstance.getId()).thenReturn(4321L);
        Mockito.when(graphSession.getProcessInstance(4321L)).thenReturn(processInstance);
        addTagAttribute("processInstance", processInstance);
        addTagAttribute("target", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(ListTransitionsForProcessInstanceHandler.class);
        Assert.assertEquals("listTransitionsForProcessInstance", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        @SuppressWarnings("unchecked")
        final Collection<TransitionLog> transities = (Collection<TransitionLog>) getExpressionValues().get("target");
        Assert.assertNotNull(transities);
        Assert.assertEquals(7, transities.size());
    }

    private Map<Token, List<ProcessLog>> maakLogs() {
        final Map<Token, List<ProcessLog>> result = new HashMap<>();
        final List<ProcessLog> log1 = new ArrayList<>();
        log1.add(maakMessageLog());
        log1.add(maakTransitionLog());
        log1.add(maakMessageLog());
        log1.add(maakTransitionLog());

        final List<ProcessLog> log2 = new ArrayList<>();
        log2.add(maakMessageLog());
        log2.add(maakTransitionLog());
        log2.add(maakMessageLog());

        result.put(Mockito.mock(Token.class), log1);
        result.put(Mockito.mock(Token.class), log2);
        return result;

    }

    private ProcessLog maakTransitionLog() {
        final TransitionLog result = new TransitionLog();
        result.setDate(new Date());
        return result;
    }

    private ProcessLog maakMessageLog() {
        final MessageLog result = new MessageLog();
        result.setDate(new Date());
        return result;
    }
}
