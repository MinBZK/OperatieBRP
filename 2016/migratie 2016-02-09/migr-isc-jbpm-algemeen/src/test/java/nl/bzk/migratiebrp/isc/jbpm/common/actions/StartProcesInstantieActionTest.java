/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.rapportage.RapportageDao;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartProcesInstantieActionTest {

    // Dependencies

    @Mock
    private RapportageDao rapportageDao;
    @Mock
    private BerichtenDao berichtenDao;

    // Subject

    @InjectMocks
    private StartProcesInstantieAction subject;

    // Context
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private ProcessInstance processInstance;
    @Mock
    private ProcessDefinition processDefinition;

    private final Date startDate = new Date();
    private final Timestamp startTimestamp = new Timestamp(startDate.getTime());

    @Before
    public void setupContext() {
        // Execution context
        ExecutionContext.pushCurrentContext(executionContext);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);

        // Process instance
        Mockito.when(processInstance.getId()).thenReturn(142L);
        Mockito.when(processInstance.getStart()).thenReturn(startDate);
        Mockito.when(processInstance.getProcessDefinition()).thenReturn(processDefinition);

        // Process definition
        Mockito.when(processDefinition.getId()).thenReturn(242L);
        Mockito.when(processDefinition.getName()).thenReturn("procDef");

        // BerichtDAO
        final BerichtMetaData result = new BerichtMetaData();
        result.setBerichtType("TYPETJE");
        result.setKanaal("KANAAL");
        Mockito.when(berichtenDao.leesBerichtMetaData(42L)).thenReturn(result);
    }

    @After
    public void teardownContext() {
        ExecutionContext.popCurrentContext(executionContext);
    }

    @Test
    public void testDefault() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 42L);
        test(parameters);
    }

    @Test
    public void testLo3Bericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lo3Bericht", 42L);
        test(parameters);
    }

    @Test
    public void testBrpBericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("brpBericht", 42L);
        test(parameters);
    }

    @Test
    public void testBlokkeringBericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringBericht", 42L);
        test(parameters);
    }

    @Test
    public void testOverigBericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("overigBericht", 42L);
        test(parameters);
    }

    private void test(final Map<String, Object> parameters) {
        // Execute
        subject.execute(parameters);

        // Verify
        Mockito.verify(rapportageDao).voegStartProcesInstantieToe("procDef", "TYPETJE", "KANAAL", 142L, startTimestamp);
    }

    @Test
    public void testBeheerderConsole() {
        final Map<String, Object> parameters = new HashMap<>();

        // Execute
        subject.execute(parameters);

        // Verify
        Mockito.verify(rapportageDao).voegStartProcesInstantieToe("procDef", "BEHEERDER", "CONSOLE", 142L, startTimestamp);
    }
}
