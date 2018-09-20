/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.Collection;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ListProcessInstancesForProcessInstanceTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        setupMockDatabase();

        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(processInstance.getId()).thenReturn(4321L);
        Mockito.when(graphSession.getProcessInstance(4321L)).thenReturn(processInstance);

        addTagAttribute("processInstance", processInstance);
        addTagAttribute("target", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(ListProcessInstancesForProcessInstanceHandler.class);
        Assert.assertEquals("listProcessInstancesForProcessInstance", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        @SuppressWarnings("unchecked")
        final Collection<ProcessInstance> bericht = (Collection<ProcessInstance>) getExpressionValues().get("target");
        Assert.assertNotNull(bericht);
        Assert.assertEquals(0, bericht.size());
    }

}
