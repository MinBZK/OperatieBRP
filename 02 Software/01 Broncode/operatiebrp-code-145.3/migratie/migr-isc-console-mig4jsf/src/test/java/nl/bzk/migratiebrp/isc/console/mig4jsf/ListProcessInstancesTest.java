/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ListProcessInstancesTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        setupMockDatabase();

        addTagAttribute("processId", 4321L);
        addTagAttribute("pager", new PagerBean(0, 10));
        final Filter filter = Mockito.mock(Filter.class);
        addTagAttribute("filter", filter);
        addTagAttribute("target", null);

        // Count
        Mockito.when(criteria.uniqueResult()).thenReturn(45L);
        final List<ProcessInstance> processInstances = new ArrayList<>();
        Mockito.when(criteria.list()).thenReturn(processInstances);

        // Execute
        final JbpmActionListener subject = initializeSubject(ListProcessInstancesHandler.class);
        Assert.assertEquals("listProcessInstances", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        Assert.assertSame(processInstances, getExpressionValues().get("target"));
    }
}
