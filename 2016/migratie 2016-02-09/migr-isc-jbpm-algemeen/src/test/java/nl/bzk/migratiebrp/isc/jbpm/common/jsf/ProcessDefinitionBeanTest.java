/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JbpmJsfUtil.class)
public class ProcessDefinitionBeanTest {

    private final ProcessDefinitionBean subject = new ProcessDefinitionBean();

    @Mock
    private TaskInstance task;
    @Mock
    private ProcessInstance process;
    @Mock
    private ProcessDefinition processDefinition;
    @Mock
    private Token superProcessToken;
    @Mock
    private ProcessInstance superProcess;
    @Mock
    private ProcessDefinition superProcessDefinition;

    @Test
    public void testGetProcessDefinition() {
        PowerMockito.mockStatic(JbpmJsfUtil.class);
        Mockito.when(JbpmJsfUtil.getTaskInstance()).thenReturn(task);
        Mockito.when(task.getProcessInstance()).thenReturn(process);
        Mockito.when(process.getProcessDefinition()).thenReturn(processDefinition);

        Assert.assertEquals(processDefinition, subject.getProcessDefinition());
    }

    @Test
    public void testGetRootProcessDefinitionNoParent() {
        PowerMockito.mockStatic(JbpmJsfUtil.class);
        Mockito.when(JbpmJsfUtil.getTaskInstance()).thenReturn(task);
        Mockito.when(task.getProcessInstance()).thenReturn(process);
        Mockito.when(process.getSuperProcessToken()).thenReturn(null);
        Mockito.when(process.getProcessDefinition()).thenReturn(processDefinition);

        Assert.assertEquals(processDefinition, subject.getRootProcessDefinition());
    }

    @Test
    public void testGetRootProcessDefinitionWithParent() {
        PowerMockito.mockStatic(JbpmJsfUtil.class);
        Mockito.when(JbpmJsfUtil.getTaskInstance()).thenReturn(task);
        Mockito.when(task.getProcessInstance()).thenReturn(process);
        Mockito.when(process.getSuperProcessToken()).thenReturn(superProcessToken);
        Mockito.when(superProcessToken.getProcessInstance()).thenReturn(superProcess);
        Mockito.when(superProcess.getSuperProcessToken()).thenReturn(null);
        Mockito.when(superProcess.getProcessDefinition()).thenReturn(superProcessDefinition);

        Assert.assertEquals(superProcessDefinition, subject.getRootProcessDefinition());
    }
}
