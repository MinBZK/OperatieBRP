/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.logging.exe.LoggingInstance;
import org.jbpm.logging.log.ProcessLog;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class JbpmTransitionCommandTest {

    @Test
    public void test() {
        // Setup
        final Token token = Mockito.mock(Token.class);
        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        final LoggingInstance loggingInstance = Mockito.mock(LoggingInstance.class);
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Expect
        Mockito.when(jbpmContext.getTokenForUpdate(123L)).thenReturn(token);
        Mockito.when(token.getProcessInstance()).thenReturn(processInstance);
        Mockito.when(processInstance.getLoggingInstance()).thenReturn(loggingInstance);

        // Execute
        final JbpmTransitionCommand command = new JbpmTransitionCommand(123L, "transie");
        command.doInContext(jbpmContext);

        // Verify
        Mockito.verify(loggingInstance).addLog(Matchers.any(ProcessLog.class));
        Mockito.verify(token).signal("transie");

    }

}
