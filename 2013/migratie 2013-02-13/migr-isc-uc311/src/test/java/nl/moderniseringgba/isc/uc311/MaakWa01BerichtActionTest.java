/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Wa01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.jbpm.graph.exe.ExecutionContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MaakWa01BerichtActionTest {
    private final MaakWa01BerichtAction subject = new MaakWa01BerichtAction();

    private ExecutionContext executionContextMock;

    @Before
    public void setup() {
        executionContextMock = Mockito.mock(ExecutionContext.class);
        ExecutionContext.pushCurrentContext(executionContextMock);
    }

    @After
    public void destroy() {
        ExecutionContext.popCurrentContext(executionContextMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        final WijzigingANummerSignaalBericht input = new WijzigingANummerSignaalBericht();
        input.setOudANummer(1231231234L);
        input.setNieuwANummer(8768768765L);
        input.setDatumGeldigheid(new BrpDatum(19900404));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("0530")));

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
                5675675678L, "Piet", "Pietersen", 19770101, "0517", "6030", "M"), Lo3StapelHelper.lo3His(19770101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                new LeesUitBrpAntwoordBericht("2345354-wfsdf-23432", builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", input);
        parameters.put("leesUitBrpAntwoordBericht", leesUitBrpAntwoordBericht);
        // Doel gemeente staat in de token scope variabele
        Mockito.when(executionContextMock.getVariable("doelGemeente")).thenReturn("0430");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNull(result);

        final ArgumentCaptor<Wa01Bericht> argument = ArgumentCaptor.forClass(Wa01Bericht.class);
        Mockito.verify(executionContextMock).setVariable(Matchers.eq("wa01Bericht"), argument.capture());
        final Wa01Bericht wa01Bericht = argument.getValue();

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
