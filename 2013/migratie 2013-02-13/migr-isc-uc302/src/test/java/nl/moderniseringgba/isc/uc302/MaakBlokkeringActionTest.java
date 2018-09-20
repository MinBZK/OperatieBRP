/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MaakBlokkeringActionTest {

    private final MaakBlokkeringAction subject = new MaakBlokkeringAction();
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
        final VerhuizingVerzoekBericht input = new VerhuizingVerzoekBericht();
        input.setANummer("8172387435");
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("0512")));

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", input);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final BlokkeringVerzoekBericht blokkering = (BlokkeringVerzoekBericht) result.get("blokkeringBericht");
        Assert.assertNotNull(blokkering);
        Assert.assertEquals("8172387435", blokkering.getANummer());
        Assert.assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, blokkering.getPersoonsaanduiding());
        Assert.assertEquals("123", blokkering.getProcessId());
    }

}
