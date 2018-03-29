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
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:/uc311-test-beans.xml"})
public class Uc311Test extends AbstractJbpmTest {

    public Uc311Test() {
        super("/uc311/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void setupPartijRegister() {
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("051701", "0517", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("056001", "0560", intToDate(2009_01_01), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("057001", "0570", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("058001", "0580", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    @Test
    public void testHappyFlow() throws Exception {
        // Setup gemeenteService

        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setMessageId("TstBer001");
        input.setBronPartijCode("123401");
        input.setOudAnummer("1231231234");
        input.setNieuwAnummer("5675675678");
        input.setDatumIngangGeldigheid(2013_02_02);
        System.out.println(input.format());

        // Start het process.
        startProcess(input);

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals("5675675678", leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon("5675675678",
                "Piet",
                "Pietersen",
                1977_01_01,
                "0517",
                "6030",
                "M"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(1977_01_01),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0560",
                1977_01_01,
                "W",
                null,
                1977_01_01,
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
                Lo3StapelHelper.lo3His(1977_01_01),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 2)),
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0570",
                        1977_01_01,
                        "W",
                        null,
                        1977_01_01,
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
                        Lo3StapelHelper.lo3His(1987_01_01),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_58, 0, 1)),
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0580",
                        1977_01_01,
                        "W",
                        null,
                        1977_01_01,
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
                        Lo3StapelHelper.lo3His(1997_01_01),
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
        final List<String> verwachteGemeenten = new ArrayList<>(Arrays.asList("051701", "057001", "058001"));

        for (int i = 0; i < 3; i++) {
            final Wa01Bericht wa01Bericht = getBericht(Wa01Bericht.class);
            Assert.assertNotNull(wa01Bericht.getDoelPartijCode());
            Assert.assertTrue(verwachteGemeenten.remove(wa01Bericht.getDoelPartijCode()));

            // Stuur null-bericht
            final NullBericht nullBericht = new NullBericht();
            nullBericht.setMessageId("TstBer004-" + i);
            nullBericht.setBronPartijCode(wa01Bericht.getDoelPartijCode());
            nullBericht.setDoelPartijCode(wa01Bericht.getBronPartijCode());
            nullBericht.setCorrelationId(wa01Bericht.getMessageId());

            signalVoisc(nullBericht);
        }
        Assert.assertTrue(verwachteGemeenten.isEmpty());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHappyFlowZonderWa01Berichten() {
        final AnummerWijzigingNotificatie input = new AnummerWijzigingNotificatie();
        input.setMessageId("TstBer001");
        input.setBronPartijCode("123401");
        input.setOudAnummer("1231231234");
        input.setNieuwAnummer("5675675678");
        input.setDatumIngangGeldigheid(2013_02_02);
        System.out.println(input);

        // Start het process.
        startProcess(input);

        // expect Opvragen PL
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals("5675675678", leesUitBrpVerzoekBericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, leesUitBrpVerzoekBericht.getAntwoordFormaat());

        // Maak PL met minimaal categorie 01 (persoon) en categorie 08 (verblijfplaats)
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon("5675675678",
                "Piet",
                "Pietersen",
                1977_01_01,
                "0560",
                "6030",
                "M"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(1977_01_01),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0560",
                1977_01_01,
                "W",
                null,
                1977_01_01,
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
                Lo3StapelHelper.lo3His(1997_01_01),
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
