/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
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
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

/**
 * Gezagsverhouding.
 */
public class MutatieCategorie11IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

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
    public void testGroep32GezagMinderjarige1() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        ouder1BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(true).eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "1",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige2() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        ouder2BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(true).eindeRecord();

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "2",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarigeD() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder indicatieBuilder = new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder();
        indicatieBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieDerdeHeeftGezagToe(indicatieBuilder.build());

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "D",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige12D() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        ouder1BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(true).eindeRecord();
        ouder2BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(true).eindeRecord();

        final PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder indicatieBuilder = new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder();
        indicatieBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieDerdeHeeftGezagToe(indicatieBuilder.build());

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "12D",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep32GezagMinderjarige2naar1D() {

        ouder2BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actieGeboorte).indicatieOuderHeeftGezag(true).eindeRecord();

        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        ouder2BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(false).eindeRecord();
        ouder1BetrokkendheidBuilder.nieuwOuderlijkGezagRecord(actie).indicatieOuderHeeftGezag(true).eindeRecord();

        final PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder indicatieBuilder = new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder();
        indicatieBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieDerdeHeeftGezagToe(indicatieBuilder.build());

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "1D",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3210,
            "2",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep33Curatele() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonIndicatieOnderCurateleHisVolledigImplBuilder indicatieBuilder = new PersoonIndicatieOnderCurateleHisVolledigImplBuilder();
        indicatieBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieOnderCurateleToe(indicatieBuilder.build());

        maakOuders();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_11,
            true,
            Lo3ElementEnum.ELEMENT_3310,
            "1",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_61,
            true,
            Lo3ElementEnum.ELEMENT_3310,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }
}
