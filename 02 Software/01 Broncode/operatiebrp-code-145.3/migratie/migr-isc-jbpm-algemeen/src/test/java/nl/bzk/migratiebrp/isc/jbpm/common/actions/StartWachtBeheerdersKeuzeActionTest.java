/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.rapportage.RapportageDao;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutenDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartWachtBeheerdersKeuzeActionTest {

    // Dependencies
    @Mock
    private RapportageDao rapportageDao;
    @Mock
    private FoutenDao foutenDao;

    // Subject
    @InjectMocks
    private StartWachtBeheerdersKeuzeAction subject;

    // Context
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private ProcessInstance processInstance;

    @Test
    public void test() throws Exception {
        final Field foutenDaoField = StartWachtBeheerdersKeuzeAction.class.getDeclaredField("foutenDao");
        foutenDaoField.setAccessible(true);
        foutenDaoField.set(subject, foutenDao);

        // Execution context
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);

        // Process instance
        Mockito.when(processInstance.getId()).thenReturn(142L);

        // Fouten DAO
        Mockito.when(foutenDao.haalFoutcodeOp(342L)).thenReturn("FOUTJE-BEDANKT");

        ExecutionContext.pushCurrentContext(executionContext);
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(FoutafhandelingConstants.REGISTRATIE_ID, 342L);
            subject.execute(parameters);
        } finally {
            ExecutionContext.popCurrentContext(executionContext);
        }

        Mockito.verify(rapportageDao).updateFoutreden(Matchers.eq(142L), Matchers.<Timestamp>any(), Matchers.eq("FOUTJE-BEDANKT"));
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
}
