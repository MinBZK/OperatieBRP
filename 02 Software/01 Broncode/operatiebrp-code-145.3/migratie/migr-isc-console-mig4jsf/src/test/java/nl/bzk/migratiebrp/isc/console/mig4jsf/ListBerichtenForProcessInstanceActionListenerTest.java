/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import javax.el.ELContext;
import javax.el.ValueExpression;
import org.jbpm.jsf.JbpmJsfContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ListBerichtenForProcessInstanceActionListenerTest {

    private FacesContextImpl facesContext;

    @Mock
    private ELContext elContext;

    @Mock
    private ValueExpression processInstanceExpression;
    @Mock
    private ValueExpression targetExpression;
    @Mock
    private ValueExpression pagerExpression;
    @Mock
    private ValueExpression filterExpression;

    @Mock
    private JbpmJsfContext jbpmJsfContext;

    @Test
    public void test() {
        facesContext = new FacesContextImpl();
        facesContext.setElContext(elContext);
        FacesContextImpl.setContext(facesContext);
        Mockito.when(pagerExpression.getValue(Mockito.any(ELContext.class))).thenReturn(null);

        final ListBerichtenForProcessInstanceActionListener listener =
                new ListBerichtenForProcessInstanceActionListener(processInstanceExpression, targetExpression, pagerExpression, filterExpression);
        listener.verwerkAction(jbpmJsfContext, null);

        Mockito.verify(jbpmJsfContext, Mockito.times(1)).setError("Error loading pager for berichten list", "The pager value is null");
    }
}