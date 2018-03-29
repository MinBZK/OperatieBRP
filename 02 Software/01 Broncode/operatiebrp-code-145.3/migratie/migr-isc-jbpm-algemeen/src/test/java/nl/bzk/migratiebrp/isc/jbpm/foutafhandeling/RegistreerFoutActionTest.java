/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RegistreerFoutActionTest {
    // Subject
    private final RegistreerFoutAction subject = new RegistreerFoutAction();
    // Dependencies
    private FoutenDao foutenDao;
    private ExecutionContext executionContext;

    @Before
    public void setup() {
        foutenDao = Mockito.mock(FoutenDao.class);
        subject.setFoutenDao(foutenDao);

        executionContext = Mockito.mock(ExecutionContext.class);
        ExecutionContext.pushCurrentContext(executionContext);
    }

    @After
    public void destroy() {
        ExecutionContext.popCurrentContext(executionContext);
    }

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(FoutafhandelingConstants.FOUT, "test.fout.001");
        parameters.put(FoutafhandelingConstants.FOUTMELDING, "blablabla");

        // Mock
        final ProcessInstance foutProcessInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(foutProcessInstance);
        final Token superToken = Mockito.mock(Token.class);
        Mockito.when(foutProcessInstance.getSuperProcessToken()).thenReturn(superToken);
        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(superToken.getProcessInstance()).thenReturn(processInstance);
        Mockito.when(processInstance.getSuperProcessToken()).thenReturn(null);
        final ProcessDefinition processDefinition = Mockito.mock(ProcessDefinition.class);
        Mockito.when(processInstance.getProcessDefinition()).thenReturn(processDefinition);
        Mockito.when(processDefinition.getName()).thenReturn("testdef");
        Mockito.when(processInstance.getId()).thenReturn(42L);

        // Expected
        Mockito.when(foutenDao.registreerFout("test.fout.001", "blablabla", "testdef", 42L, null, null)).thenReturn(13L);

        // Execute
        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(13L, result.get(FoutafhandelingConstants.REGISTRATIE_ID));
    }
}
