/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MaakDeblokkeringActionTest {

    private final MaakDeblokkeringAction subject = new MaakDeblokkeringAction();
    private ExecutionContext executionContextMock;
    private ProcessInstance processInstanceMock;

    @Before
    public void setup() {
        executionContextMock = Mockito.mock(ExecutionContext.class);
        processInstanceMock = Mockito.mock(ProcessInstance.class);

        Mockito.when(executionContextMock.getProcessInstance()).thenReturn(processInstanceMock);
        Mockito.when(processInstanceMock.getId()).thenReturn(123L);

        ExecutionContext.pushCurrentContext(executionContextMock);
    }

    @After
    public void destroy() {
        ExecutionContext.popCurrentContext(executionContextMock);
    }

    @Test
    public void test() throws BerichtSyntaxException, BerichtInhoudException {
        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht();
        blokkeringBericht.setANummer("8172387435");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringBericht", blokkeringBericht);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final DeblokkeringVerzoekBericht deblokkering =
                (DeblokkeringVerzoekBericht) result.get("deblokkeringBericht");
        Assert.assertNotNull(deblokkering);
        Assert.assertEquals("8172387435", deblokkering.getANummer());
        Assert.assertEquals("123", deblokkering.getProcessId());
    }
}
