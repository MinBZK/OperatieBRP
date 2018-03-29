/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import org.junit.Assert;
import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Test;
import org.mockito.Mockito;

public class ExceptionLoggerTest {

    private final ExceptionLogger subject = new ExceptionLogger();

    @Test
    public void test() {
        final Throwable cause = new IllegalStateException("IllegalState");
        final Throwable problem = new RuntimeException("Stuk", cause);

        final ExecutionContext executionContext = Mockito.mock(ExecutionContext.class);
        Mockito.when(executionContext.getException()).thenReturn(problem);

        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);
        Mockito.when(processInstance.getId()).thenReturn(142L);

        try {
            subject.execute(executionContext);
            Assert.fail("ExceptionLogger zou exception door moeten gooien, anders wordt deze 'opgegeten'.");
        } catch (final JbpmException e) {
            Assert.assertEquals(problem, e.getCause());
        }
    }

    @Test
    public void testMinimal() {
        final Throwable problem = new IllegalStateException("IllegalState");

        final ExecutionContext executionContext = Mockito.mock(ExecutionContext.class);
        Mockito.when(executionContext.getException()).thenReturn(problem);

        try {
            subject.execute(executionContext);
            Assert.fail("ExceptionLogger zou exception door moeten gooien, anders wordt deze 'opgegeten'.");
        } catch (final JbpmException e) {
            Assert.assertEquals(problem, e.getCause());
        }
    }
}
