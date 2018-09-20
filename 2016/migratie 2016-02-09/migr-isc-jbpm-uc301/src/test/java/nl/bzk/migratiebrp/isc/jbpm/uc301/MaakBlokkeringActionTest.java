/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakBlokkeringActionTest {

    private MaakBlokkeringAction subject;
    private BerichtenDao berichtenDao;

    private ExecutionContext executionContextMock;
    private ProcessInstance processInstanceMock;

    @Before
    public void setup() {
        subject = new MaakBlokkeringAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);

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
        final ZoekPersoonAntwoordBericht antwoord = new ZoekPersoonAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        antwoord.setPersoonId(1);
        antwoord.setAnummer("8172387435");
        antwoord.setGemeente("1900");

        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setDoelGemeente("0599");
        ii01Bericht.setBronGemeente("0600");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoekPersoonBinnenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(antwoord));
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final BlokkeringVerzoekBericht blokkering = (BlokkeringVerzoekBericht) berichtenDao.leesBericht((Long) result.get("blokkeringBericht"));
        Assert.assertNotNull(blokkering);
        Assert.assertEquals("8172387435", blokkering.getANummer());
        Assert.assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, blokkering.getPersoonsaanduiding());
        Assert.assertEquals("123", blokkering.getProcessId());
        Assert.assertEquals("0599", blokkering.getGemeenteRegistratie());
        Assert.assertEquals("0600", blokkering.getGemeenteNaar());
    }

}
