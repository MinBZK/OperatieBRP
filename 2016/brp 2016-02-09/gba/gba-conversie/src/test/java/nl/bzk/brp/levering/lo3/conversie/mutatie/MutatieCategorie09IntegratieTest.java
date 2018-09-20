/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
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
import support.PersoonHisVolledigUtil;

/**
 * Kind.
 */
public class MutatieCategorie09IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    final ActieModel actieKindGeboorte = PersoonHisVolledigUtil.maakActie(
        2L,
        SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
        SoortActie.CONVERSIE_G_B_A,
        19400101,
        partij);
    private PersoonHisVolledigImplBuilder kindBuilder;

    @Before
    public void setupKind() {
        kindBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        kindBuilder.nieuwGeboorteRecord(actieKindGeboorte)
                   .datumGeboorte(actieKindGeboorte.getDatumAanvangGeldigheid())
                   .gemeenteGeboorte((short) 518)
                   .landGebiedGeboorte((short) 6030)
                   .eindeRecord();
        kindBuilder.nieuwGeslachtsaanduidingRecord(actieKindGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        kindBuilder.nieuwIdentificatienummersRecord(actieKindGeboorte).administratienummer(1231231234L).burgerservicenummer(345345345).eindeRecord();
        kindBuilder.nieuwInschrijvingRecord(actieKindGeboorte)
                   .datumInschrijving(actieKindGeboorte.getDatumAanvangGeldigheid())
                   .versienummer(1L)
                   .datumtijdstempel(new Date(123456))
                   .eindeRecord();
        kindBuilder.nieuwNaamgebruikRecord(actieKindGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        kindBuilder.nieuwAfgeleidAdministratiefRecord(actieKindGeboorte)
                   .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
                   .tijdstipLaatsteWijziging(actieKindGeboorte.getTijdstipRegistratie())
                   .tijdstipLaatsteWijzigingGBASystematiek(actieKindGeboorte.getTijdstipRegistratie())
                   .eindeRecord();
        kindBuilder.nieuwSamengesteldeNaamRecord(actieKindGeboorte)
                   .geslachtsnaamstam("Trommelen")
                   .voorvoegsel("van")
                   .voornamen("Pimmetje")
                   .scheidingsteken(" ")
                   .eindeRecord();
    }

    private PersoonHisVolledigImpl maakKind(final ActieModel... acties) {

        final PersoonHisVolledigImpl kind = kindBuilder.build();
        final KindHisVolledigImplBuilder gerelateerdeKindBetrokkendheidBuilder = new KindHisVolledigImplBuilder(null, kind);
        final KindHisVolledigImpl gerelateerdeKindBetrokkendheid = gerelateerdeKindBetrokkendheidBuilder.build();

        final FamilierechtelijkeBetrekkingHisVolledigImplBuilder relatieBuilder = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder();
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = relatieBuilder.build();
        relatie.getBetrokkenheden().add(gerelateerdeKindBetrokkendheid);

        final OuderHisVolledigImplBuilder mijnOuderBetrokkenheidBuilder = new OuderHisVolledigImplBuilder(relatie, null);
        builder.voegBetrokkenheidToe(mijnOuderBetrokkenheidBuilder.build());
        final PersoonHisVolledigImpl ik = builder.build();

        final List<ActieModel> alleActies = new ArrayList<ActieModel>();
        alleActies.add(actieGeboorte);
        alleActies.add(actieKindGeboorte);
        if (acties != null) {
            alleActies.addAll(Arrays.asList(acties));
        }

        PersoonHisVolledigUtil.maakVerantwoording(ik, alleActies.toArray(new ActieModel[] {}));

        return ik;
    }

    @Test
    public void testGeboorteKind() {
        final PersoonHisVolledigImpl ik = maakKind();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actieKindGeboorte);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "1231231234",
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_0310,
            "19400101",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "",
            Lo3ElementEnum.ELEMENT_0120,
            "",
            Lo3ElementEnum.ELEMENT_0210,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "",
            Lo3ElementEnum.ELEMENT_0240,
            "",
            Lo3ElementEnum.ELEMENT_0310,
            "",
            Lo3ElementEnum.ELEMENT_0320,
            "",
            Lo3ElementEnum.ELEMENT_0330,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep01Identificatienummers() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        kindBuilder.nieuwIdentificatienummersRecord(actie).administratienummer(1231231234L).burgerservicenummer(543543543).eindeRecord();

        final PersoonHisVolledigImpl ik = maakKind(actie);
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep02Naam() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        kindBuilder.nieuwSamengesteldeNaamRecord(actie)
                   .voornamen("Voornaam3")
                   .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
                   .voorvoegsel("het")
                   .scheidingsteken(" ")
                   .geslachtsnaamstam("achternaam")
                   .eindeRecord();

        final PersoonHisVolledigImpl ik = maakKind(actie);
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
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
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03Geboorte() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        kindBuilder.nieuwGeboorteRecord(actie).datumGeboorte(19590101).gemeenteGeboorte((short) 222).landGebiedGeboorte((short) 6030).eindeRecord();

        final PersoonHisVolledigImpl ik = maakKind(actie);
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_09, true, Lo3ElementEnum.ELEMENT_0310, "19590101", Lo3ElementEnum.ELEMENT_0320, "0222",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_59, true, Lo3ElementEnum.ELEMENT_0310, "19400101", Lo3ElementEnum.ELEMENT_0320, "0518",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboortenBuiteland() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        kindBuilder.nieuwGeboorteRecord(actie)
                   .datumGeboorte(19400101)
                   .landGebiedGeboorte((short) 5010)
                   .buitenlandsePlaatsGeboorte("Brussel")
                   .eindeRecord();

        final PersoonHisVolledigImpl ik = maakKind(actie);
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_09, true, Lo3ElementEnum.ELEMENT_0320, "Brussel", Lo3ElementEnum.ELEMENT_0330, "5010",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_59, true, Lo3ElementEnum.ELEMENT_0320, "0518", Lo3ElementEnum.ELEMENT_0330, "6030",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        kindBuilder.nieuwIdentificatienummersRecord(actie).administratienummer(1231231234L).burgerservicenummer(543543543).eindeRecord();

        kindBuilder.nieuwSamengesteldeNaamRecord(actie)
                   .voornamen("Voornaam3")
                   .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
                   .voorvoegsel("het")
                   .scheidingsteken(" ")
                   .geslachtsnaamstam("achternaam")
                   .eindeRecord();

        kindBuilder.nieuwGeboorteRecord(actie)
                   .datumGeboorte(19400101)
                   .landGebiedGeboorte((short) 5010)
                   .buitenlandsePlaatsGeboorte("Brussel")
                   .eindeRecord();

        final PersoonHisVolledigImpl ik = maakKind(actie);
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(ik, actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_09,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam3",
            Lo3ElementEnum.ELEMENT_0220,
            "P",
            Lo3ElementEnum.ELEMENT_0230,
            "het",
            Lo3ElementEnum.ELEMENT_0240,
            "achternaam",
            Lo3ElementEnum.ELEMENT_0320,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0330,
            "5010",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_59,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Pimmetje",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "van",
            Lo3ElementEnum.ELEMENT_0240,
            "Trommelen",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
    }

}
