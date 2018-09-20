/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wa01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:/uc311-test-beans.xml" })
public class Uc311Test extends AbstractJbpmTest {

    public Uc311Test() {
        super("/uc311/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @Before
    public void setupGemeenteRegister() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("0517", "580517", null));
        gemeenten.add(new Gemeente("0560", "580560", intToDate(20090101)));
        gemeenten.add(new Gemeente("0570", "580570", null));
        gemeenten.add(new Gemeente("0580", "580580", null));
        setGemeenteRegister(new GemeenteRegisterImpl(gemeenten));
    }

    @Test
    public void testHappyFlow() throws Exception {
        // Setup gemeenteService

        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setMessageId("TstBer001");
        input.setBronGemeente("1234");
        input.setOudAnummer(1231231234L);
        input.setNieuwAnummer(5675675678L);
        input.setDatumIngangGeldigheid(20130202);
        System.out.println(input.format());

        // Start het process.
        startProcess(input);

        // // GEMEENTEN
        // controleerBerichten(0, 0, 1);
        // getBericht(LeesGemeenteRegisterVerzoekBericht.class);
        // signalSync(maakLeesGemeenteRegisterAntwoordBericht());

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(Long.valueOf(5675675678L), leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(5675675678L,
                                                                                                          "Piet",
                                                                                                          "Pietersen",
                                                                                                          19770101,
                                                                                                          "0517",
                                                                                                          "6030",
                                                                                                          "M"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19770101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0560",
                                                                                                                        19770101,
                                                                                                                        "W",
                                                                                                                        null,
                                                                                                                        19770101,
                                                                                                                        "Blastraat",
                                                                                                                        null,
                                                                                                                        55,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        "M",
                                                                                                                        null),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19770101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 2)),
                                                               Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0570",
                                                                                                                        19770101,
                                                                                                                        "W",
                                                                                                                        null,
                                                                                                                        19770101,
                                                                                                                        "Dingesstraat",
                                                                                                                        null,
                                                                                                                        43,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        "M",
                                                                                                                        null),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19870101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 1)),
                                                               Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0580",
                                                                                                                        19770101,
                                                                                                                        "W",
                                                                                                                        null,
                                                                                                                        19770101,
                                                                                                                        "Huppeldepuplaan",
                                                                                                                        null,
                                                                                                                        22,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        "I",
                                                                                                                        null),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19970101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))));
        // @formatter:on

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), builder.build());
        leesUitBrpAntwoordBericht.setMessageId("TstBer003");
        System.out.println("LEESUITBRPANTWOORD:\n" + leesUitBrpAntwoordBericht.format());
        signalSync(leesUitBrpAntwoordBericht);

        // Nu zouden drie berichten (voor gemeenten 0517, 0570 en 0580 (niet 0560 want dat is een BRP gemeenet)
        // verstuurd moeten zijn.
        controleerBerichten(0, 3, 0);
        final List<String> verwachteGemeenten = new ArrayList<>(Arrays.asList("0517", "0570", "0580"));

        for (int i = 0; i < 3; i++) {
            final Wa01Bericht wa01Bericht = getBericht(Wa01Bericht.class);
            Assert.assertNotNull(wa01Bericht.getDoelGemeente());
            Assert.assertTrue(verwachteGemeenten.remove(wa01Bericht.getDoelGemeente()));

            // Stuur null-bericht
            final NullBericht nullBericht = new NullBericht();
            nullBericht.setMessageId("TstBer004-" + i);
            nullBericht.setBronGemeente(wa01Bericht.getDoelGemeente());
            nullBericht.setDoelGemeente(wa01Bericht.getBronGemeente());
            nullBericht.setCorrelationId(wa01Bericht.getMessageId());

            signalVospg(nullBericht);
        }
        Assert.assertTrue(verwachteGemeenten.isEmpty());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHappyFlowZonderWa01Berichten() {
        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setMessageId("TstBer001");
        input.setBronGemeente("1234");
        input.setOudAnummer(1231231234L);
        input.setNieuwAnummer(5675675678L);
        input.setDatumIngangGeldigheid(20130202);
        System.out.println(input);

        // Start het process.
        startProcess(input);
        //
        // // GEMEENTEN
        // controleerBerichten(0, 0, 1);
        // getBericht(LeesGemeenteRegisterVerzoekBericht.class);
        // signalSync(maakLeesGemeenteRegisterAntwoordBericht());

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(Long.valueOf(5675675678L), leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(5675675678L,
                                                                                                          "Piet",
                                                                                                          "Pietersen",
                                                                                                          19770101,
                                                                                                          "0560",
                                                                                                          "6030",
                                                                                                          "M"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19770101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0560",
                                                                                                                        19770101,
                                                                                                                        "W",
                                                                                                                        null,
                                                                                                                        19770101,
                                                                                                                        "Huppeldepuplaan",
                                                                                                                        null,
                                                                                                                        22,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        null,
                                                                                                                        "I",
                                                                                                                        null),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19970101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))));
        // @formatter:on

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), builder.build());
        leesUitBrpAntwoordBericht.setMessageId("TstBer003");
        signalSync(leesUitBrpAntwoordBericht);

        // Nu zouden geen berichten verstuurd moeten zijn.
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

}
