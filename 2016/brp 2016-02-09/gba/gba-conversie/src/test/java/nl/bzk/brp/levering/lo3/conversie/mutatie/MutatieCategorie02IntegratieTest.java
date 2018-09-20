/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

/**
 * Ouder 1.
 */
public class MutatieCategorie02IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    private PersoonHisVolledigImplBuilder ouder1Builder;
    private PersoonHisVolledigImplBuilder ouder2Builder;

    private OuderHisVolledigImplBuilder ouder1BetrokkendheidBuilder;
    private OuderHisVolledigImplBuilder ouder2BetrokkendheidBuilder;

    @Before
    public void setupOuders() {
        ouder1Builder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        ouder1Builder.nieuwGeboorteRecord(actieGeboorte)
                     .datumGeboorte(18800505)
                     .gemeenteGeboorte((short) 588)
                     .landGebiedGeboorte((short) 6030)
                     .eindeRecord();
        ouder1Builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.VROUW).eindeRecord();
        ouder1Builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(8888888888L).burgerservicenummer(888888888).eindeRecord();
        ouder1Builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
                     .geslachtsnaamstam("Pallo")
                     .voorvoegsel("los")
                     .voornamen("Mama")
                     .scheidingsteken(" ")
                     .eindeRecord();

        ouder2Builder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        ouder2Builder.nieuwGeboorteRecord(actieGeboorte)
                     .datumGeboorte(18800606)
                     .gemeenteGeboorte((short) 577)
                     .landGebiedGeboorte((short) 6030)
                     .eindeRecord();
        ouder2Builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        ouder2Builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(7777777777L).burgerservicenummer(777777777).eindeRecord();
        ouder2Builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
                     .geslachtsnaamstam("Pippos")
                     .voorvoegsel("la")
                     .voornamen("Papa")
                     .scheidingsteken(" ")
                     .eindeRecord();

        ouder1BetrokkendheidBuilder = new OuderHisVolledigImplBuilder(null, null);
        ouder1BetrokkendheidBuilder.nieuwOuderschapRecord(actieGeboorte).indicatieOuder(Ja.J).eindeRecord();

        ouder2BetrokkendheidBuilder = new OuderHisVolledigImplBuilder(null, null);
        ouder2BetrokkendheidBuilder.nieuwOuderschapRecord(actieGeboorte).indicatieOuder(Ja.J).eindeRecord();
    }

    private void maakOuders() {

        final PersoonHisVolledigImpl ouder1 = ouder1Builder.build();

        final OuderHisVolledigImpl ouder1Betrokkenheid = ouder1BetrokkendheidBuilder.build();
        ReflectionTestUtils.setField(ouder1Betrokkenheid, "iD", 1);
        ouder1Betrokkenheid.setPersoon(ouder1);

        final PersoonHisVolledigImpl ouder2 = ouder2Builder.build();

        final OuderHisVolledigImpl ouder2Betrokkenheid = ouder2BetrokkendheidBuilder.build();
        ReflectionTestUtils.setField(ouder2Betrokkenheid, "iD", 2);
        ouder2Betrokkenheid.setPersoon(ouder2);

        final FamilierechtelijkeBetrekkingHisVolledigImplBuilder relatieBuilder = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder();
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = relatieBuilder.build();
        relatie.getBetrokkenheden().add(ouder1Betrokkenheid);
        relatie.getBetrokkenheden().add(ouder2Betrokkenheid);

        final KindHisVolledigImplBuilder kindBetrokkenheidBuilder = new KindHisVolledigImplBuilder(relatie, null);
        builder.voegBetrokkenheidToe(kindBetrokkenheidBuilder.build());
    }

    @Test
    public void testGroep01Identificatienummers() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        ouder1Builder.nieuwIdentificatienummersRecord(actie).administratienummer(8888888888L).burgerservicenummer(123123123).eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_02,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_52,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "888888888",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep02Naam() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        ouder1Builder.nieuwSamengesteldeNaamRecord(actie)
                     .voornamen("Voornaam3")
                     .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
                     .voorvoegsel("het")
                     .scheidingsteken(" ")
                     .geslachtsnaamstam("achternaam")
                     .eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_02,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam3",
            Lo3ElementEnum.ELEMENT_0220,
            "P",
            Lo3ElementEnum.ELEMENT_0230,
            "het",
            Lo3ElementEnum.ELEMENT_0240,
            "achternaam",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_52,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Mama",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "los",
            Lo3ElementEnum.ELEMENT_0240,
            "Pallo",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        ouder1Builder.nieuwGeboorteRecord(actie).datumGeboorte(19330202).gemeenteGeboorte((short) 222).landGebiedGeboorte((short) 6030).eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_02, true, Lo3ElementEnum.ELEMENT_0310, "19330202", Lo3ElementEnum.ELEMENT_0320, "0222",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_52, true, Lo3ElementEnum.ELEMENT_0310, "18800505", Lo3ElementEnum.ELEMENT_0320, "0588",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboorteBuiteland() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        ouder1Builder.nieuwGeboorteRecord(actie)
                     .datumGeboorte(18800505)
                     .landGebiedGeboorte((short) 5010)
                     .buitenlandsePlaatsGeboorte("Brussel")
                     .eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_02, true, Lo3ElementEnum.ELEMENT_0320, "Brussel", Lo3ElementEnum.ELEMENT_0330, "5010",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_52, true, Lo3ElementEnum.ELEMENT_0320, "0588", Lo3ElementEnum.ELEMENT_0330, "6030",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04Geslacht() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        ouder1Builder.nieuwGeslachtsaanduidingRecord(actie).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_02,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "M",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_52,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

}
