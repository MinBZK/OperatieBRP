/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wa01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakWa01BerichtActionTest {

    private MaakWa01BerichtAction subject;
    private BerichtenDao berichtenDao;
    private ExecutionContext executionContextMock;
    private ContextInstance contextInstance;

    @Before
    public void setup() {
        subject = new MaakWa01BerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);

        executionContextMock = Mockito.mock(ExecutionContext.class);
        contextInstance = Mockito.mock(ContextInstance.class);
        Mockito.when(executionContextMock.getContextInstance()).thenReturn(contextInstance);
        ExecutionContext.pushCurrentContext(executionContextMock);
    }

    @After
    public void destroy() {
        ExecutionContext.popCurrentContext(executionContextMock);
    }

    @Test
    public void test() {
        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setOudAnummer(1231231234L);
        input.setNieuwAnummer(8768768765L);
        input.setDatumIngangGeldigheid(19900404);
        input.setBronGemeente("0530");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(5675675678L, "Piet", "Pietersen", 19770101, "0517", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht = new LeesUitBrpAntwoordBericht("2345354-wfsdf-23432", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(input));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(leesUitBrpAntwoordBericht));
        // Doel gemeente staat in de token scope variabele
        Mockito.when(executionContextMock.getVariable("doelGemeente")).thenReturn("0430");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNull(result);

        final ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(contextInstance).setVariable(Matchers.eq("wa01Bericht"), argument.capture(), Matchers.any(Token.class));
        final Long wa01BerichtId = argument.getValue();
        final Wa01Bericht wa01Bericht = (Wa01Bericht) berichtenDao.leesBericht(wa01BerichtId);

        Assert.assertNotNull(wa01Bericht);
        Assert.assertEquals("0530", wa01Bericht.getBronGemeente());
        Assert.assertEquals("0430", wa01Bericht.getDoelGemeente());
        Assert.assertEquals(Long.valueOf(1231231234L), wa01Bericht.getOudANummer());
        Assert.assertEquals(Long.valueOf(8768768765L), wa01Bericht.getNieuwANummer());
        Assert.assertEquals(new Lo3Datum(19900404), wa01Bericht.getDatumGeldigheid());
        Assert.assertEquals("Piet", wa01Bericht.getVoornamen());
        Assert.assertEquals(null, wa01Bericht.getAdellijkeTitelPredikaatCode());
        Assert.assertEquals(null, wa01Bericht.getVoorvoegselGeslachtsnaam());
        Assert.assertEquals("Pietersen", wa01Bericht.getGeslachtsnaam());
        Assert.assertEquals(new Lo3Datum(19770101), wa01Bericht.getGeboortedatum());
        Assert.assertEquals(new Lo3GemeenteCode("0517"), wa01Bericht.getGeboorteGemeenteCode());
        Assert.assertEquals(new Lo3LandCode("6030"), wa01Bericht.getGeboorteLandCode());

    }
}
