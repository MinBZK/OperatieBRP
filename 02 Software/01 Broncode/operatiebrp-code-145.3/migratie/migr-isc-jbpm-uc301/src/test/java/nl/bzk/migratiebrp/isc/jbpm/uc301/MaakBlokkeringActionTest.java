/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MaakBlokkeringActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakBlokkeringAction subject;

    private ExecutionContext executionContextMock;
    private ProcessInstance processInstanceMock;


    @Before
    public void setup() {
        berichtenDao = new InMemoryBerichtenDao();

        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", intToDate(2009_01_01), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("060001", "0600", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));

        subject = new MaakBlokkeringAction(berichtenDao);
    }

    private static Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Before
    public void setupProces() {
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
        antwoord.setPersoonId(1L);
        antwoord.setAnummer("8172387435");
        antwoord.setGemeente("1900");

        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setDoelPartijCode("059901");
        ii01Bericht.setBronPartijCode("060001");

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
        Assert.assertEquals("059901", blokkering.getGemeenteRegistratie());
        Assert.assertEquals("060001", blokkering.getGemeenteNaar());
    }

}
