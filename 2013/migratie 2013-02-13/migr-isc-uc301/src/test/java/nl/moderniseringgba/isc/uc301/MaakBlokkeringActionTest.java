/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;

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
        //@formatter:off
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) BrpBerichtFactory.SINGLETON.getBericht(
            "<zoekPersoonAntwoord xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\">" 
               + "<status>Ok</status>" 
               + "<gevondenPersonen>" 
                   + "<gevondenPersoon>" 
                       + "<aNummer>8172387435</aNummer>" 
                       + "<bijhoudingsgemeente>1900</bijhoudingsgemeente>" 
                   + "</gevondenPersoon>" 
               + "</gevondenPersonen>" 
           + "</zoekPersoonAntwoord>");
        //@formatter:on

        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setDoelGemeente("0599");
        ii01Bericht.setBronGemeente("0600");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("zoekPersoonBinnenGemeenteAntwoordBericht", antwoord);
        parameters.put("input", ii01Bericht);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final BlokkeringVerzoekBericht blokkering = (BlokkeringVerzoekBericht) result.get("blokkeringBericht");
        Assert.assertNotNull(blokkering);
        Assert.assertEquals("8172387435", blokkering.getANummer());
        Assert.assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA,
                blokkering.getPersoonsaanduiding());
        Assert.assertEquals("123", blokkering.getProcessId());
        Assert.assertEquals("0599", blokkering.getGemeenteRegistratie());
        Assert.assertEquals("0600", blokkering.getGemeenteNaar());
    }

}
