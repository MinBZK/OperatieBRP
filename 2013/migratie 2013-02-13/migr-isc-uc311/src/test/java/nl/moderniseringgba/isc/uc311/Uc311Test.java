/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.math.BigDecimal;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Wa01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:/uc311-test-beans.xml" })
public class Uc311Test extends AbstractJbpmTest {

    @Inject
    private GemeenteService gemeenteService;

    public Uc311Test() {
        super("/uc311/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @Before
    public void setup() {
        Mockito.reset(gemeenteService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHappyFlow() throws Exception {
        // Setup gemeenteService
        Mockito.when(gemeenteService.geefStelselVoorGemeente(517)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(560)).thenReturn(Stelsel.BRP);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(570)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(580)).thenReturn(Stelsel.GBA);

        final WijzigingANummerSignaalBericht input = new WijzigingANummerSignaalBericht();
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1234")));
        input.setOudANummer(1231231234L);
        input.setNieuwANummer(5675675678L);
        input.setDatumGeldigheid(new BrpDatum(20130202));
        System.out.println(input.format());

        // Start het process.
        startProcess(input);

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(Long.valueOf(5675675678L), leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        //@formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(5675675678L, "Piet", "Pietersen", 19770101, "0517", "6030", "M"), 
                        Lo3StapelHelper.lo3His(19770101), Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))
                ));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0560", 19770101, "W", null, 19770101, "Blastraat", null, 55, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "M", null),
                        Lo3StapelHelper.lo3His(19770101), null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 2)),
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0570", 19770101, "W", null, 19770101, "Dingesstraat", null, 43, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "M", null),
                        Lo3StapelHelper.lo3His(19870101), null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 1)),
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0580", 19770101, "W", null, 19770101, "Huppeldepuplaan", null, 22, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "I", null),
                        Lo3StapelHelper.lo3His(19970101), null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))
                ));
        //@formatter:on

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), builder.build());
        System.out.println(leesUitBrpAntwoordBericht.format());
        signalSync(leesUitBrpAntwoordBericht);

        // Nu zouden drie berichten (voor gemeenten 0517, 0570 en 0580 (niet 0560 want dat is een BRP gemeenet)
        // verstuurd moeten zijn.
        checkBerichten(0, 0, 3, 0);

        for (int i = 0; i < 3; i++) {
            final Wa01Bericht wa01Bericht = getBericht(Wa01Bericht.class);
            Assert.assertTrue(wa01Bericht.getDoelGemeente() != null);

            // Stuur null-bericht
            final NullBericht nullBericht = new NullBericht();
            nullBericht.setBronGemeente(wa01Bericht.getDoelGemeente());
            nullBericht.setDoelGemeente(wa01Bericht.getBronGemeente());
            nullBericht.setCorrelationId(wa01Bericht.getMessageId());

            signalVospg(nullBericht);
        }

        Assert.assertTrue(processEnded());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHappyFlowZonderWa01Berichten() {
        // Setup gemeenteService
        Mockito.when(gemeenteService.geefStelselVoorGemeente(517)).thenReturn(Stelsel.BRP);

        final WijzigingANummerSignaalBericht input = new WijzigingANummerSignaalBericht();
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1234")));
        input.setOudANummer(1231231234L);
        input.setNieuwANummer(5675675678L);
        input.setDatumGeldigheid(new BrpDatum(20130202));
        System.out.println(input);

        // Start het process.
        startProcess(input);

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(Long.valueOf(5675675678L), leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        //@formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(5675675678L, "Piet", "Pietersen", 19770101, "0517", "6030", "M"), 
                        Lo3StapelHelper.lo3His(19770101), Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))
                ));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0517", 19770101, "W", null, 19770101, "Huppeldepuplaan", null, 22, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "I", null),
                        Lo3StapelHelper.lo3His(19970101), null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))
                ));
        //@formatter:on

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), builder.build());
        signalSync(leesUitBrpAntwoordBericht);

        // Nu zouden geen berichten verstuurd moeten zijn.
        checkBerichten(0, 0, 0, 0);

        Assert.assertTrue(processEnded());
    }
}
