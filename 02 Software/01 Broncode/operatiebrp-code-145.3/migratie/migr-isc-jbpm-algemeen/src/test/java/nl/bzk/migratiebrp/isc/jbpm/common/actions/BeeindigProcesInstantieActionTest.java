/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.sql.Timestamp;
import java.util.HashMap;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.rapportage.RapportageDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeeindigProcesInstantieActionTest {

    // Dependencies
    @Mock
    private RapportageDao rapportageDao;
    @Mock
    private BerichtenDao berichtenDao;

    // Subject
    @InjectMocks
    private BeeindigProcesInstantieAction subject;

    // Context
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private ProcessInstance processInstance;

    @Test
    public void test() {
        // Execution context
        ExecutionContext.pushCurrentContext(executionContext);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);

        // Process instance
        Mockito.when(processInstance.getId()).thenReturn(142L);

        try {
            subject.execute(new HashMap<String, Object>());
        } finally {
            ExecutionContext.popCurrentContext(executionContext);
        }

        Mockito.verify(rapportageDao).updateEindeProcesInstantie(Matchers.eq(142L), Matchers.<Timestamp>any());
    }

    @Test(expected = IllegalStateException.class)
    public void testGeenExecutionContext() {
        subject.execute(new HashMap<String, Object>());
    }

    @Test(expected = IllegalStateException.class)
    public void testGeenProcessinstance() {
        // Execution context
        ExecutionContext.pushCurrentContext(executionContext);

        try {
            subject.execute(new HashMap<String, Object>());
        } finally {
            ExecutionContext.popCurrentContext(executionContext);
        }
    }

    @Test
    public void testChildProcessinstance() {
        // Execution context
        ExecutionContext.pushCurrentContext(executionContext);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);

        // Process instance
        Mockito.when(processInstance.getSuperProcessToken()).thenReturn(Mockito.mock(Token.class));

        try {
            subject.execute(new HashMap<String, Object>());
        } finally {
            ExecutionContext.popCurrentContext(executionContext);
        }

        Mockito.verifyNoMoreInteractions(rapportageDao);
    }

}
