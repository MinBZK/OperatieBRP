/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Persoon.
 */
public class MutatieCategorie01IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGeenWijzigingen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonVerstrekkingsbeperkingHisVolledigImplBuilder beperkingBuilder =
                new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(partij, null, null);
        builder.voegPersoonVerstrekkingsbeperkingToe(beperkingBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGroep01Identificatienummers() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
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
        builder.nieuwSamengesteldeNaamRecord(actie)
               .voornamen("Voornaam3")
               .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
               .voorvoegsel("het")
               .scheidingsteken(" ")
               .geslachtsnaamstam("achternaam")
               .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
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
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam1 Voornaam2",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "de",
            Lo3ElementEnum.ELEMENT_0240,
            "geslachtsnaam",
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
        builder.nieuwGeboorteRecord(actie).datumGeboorte(19330202).gemeenteGeboorte((short) 222).landGebiedGeboorte((short) 6030).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_01, true, Lo3ElementEnum.ELEMENT_0310, "19330202", Lo3ElementEnum.ELEMENT_0320, "0222",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_51, true, Lo3ElementEnum.ELEMENT_0310, "19200101", Lo3ElementEnum.ELEMENT_0320, "0518",
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
        builder.nieuwGeboorteRecord(actie).datumGeboorte(19200101).landGebiedGeboorte((short) 5010).buitenlandsePlaatsGeboorte("Brussel").eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_01, true, Lo3ElementEnum.ELEMENT_0320, "Brussel", Lo3ElementEnum.ELEMENT_0330, "5010",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_51, true, Lo3ElementEnum.ELEMENT_0320, "0518", Lo3ElementEnum.ELEMENT_0330, "6030",
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
        builder.nieuwGeslachtsaanduidingRecord(actie).geslachtsaanduiding(Geslachtsaanduiding.VROUW).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "M",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep20ANummerVerwijzingen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwNummerverwijzingRecord(actie).vorigeAdministratienummer(1234512345L).volgendeAdministratienummer(1234123412L).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_2010,
            "1234512345",
            Lo3ElementEnum.ELEMENT_2020,
            "1234123412",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_2010,
            "",
            Lo3ElementEnum.ELEMENT_2020,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep20BsnVerwijzingen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwNummerverwijzingRecord(actie).volgendeBurgerservicenummer(123412341).vorigeBurgerservicenummer(123451234).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_01, true, Lo3ElementEnum.ELEMENT_8510, "19400101", Lo3ElementEnum.ELEMENT_8610, "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_51, true, Lo3ElementEnum.ELEMENT_8510, "19200101", Lo3ElementEnum.ELEMENT_8610, "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep61Naamgebruik() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwNaamgebruikRecord(actie).naamgebruik(Naamgebruik.PARTNER).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_01, true, Lo3ElementEnum.ELEMENT_6110, "P",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_51, true, Lo3ElementEnum.ELEMENT_6110, "E",
        // Lo3ElementEnum.ELEMENT_8510,
        // "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();
        builder.nieuwSamengesteldeNaamRecord(actie)
               .voornamen("Voornaam3")
               .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
               .voorvoegsel("het")
               .scheidingsteken(" ")
               .geslachtsnaamstam("achternaam")
               .eindeRecord();
        builder.nieuwGeboorteRecord(actie).datumGeboorte(19200101).landGebiedGeboorte((short) 5010).buitenlandsePlaatsGeboorte("Brussel").eindeRecord();
        builder.nieuwGeslachtsaanduidingRecord(actie).geslachtsaanduiding(Geslachtsaanduiding.VROUW).eindeRecord();
        builder.nieuwNummerverwijzingRecord(actie).vorigeAdministratienummer(1234512345L).volgendeAdministratienummer(1234123412L).eindeRecord();
        builder.nieuwNaamgebruikRecord(actie).naamgebruik(Naamgebruik.PARTNER).eindeRecord();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam3",
            Lo3ElementEnum.ELEMENT_0220,
            "PS",
            Lo3ElementEnum.ELEMENT_0230,
            "het",
            Lo3ElementEnum.ELEMENT_0240,
            "achternaam",
            Lo3ElementEnum.ELEMENT_0320,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0330,
            "5010",
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_2010,
            "1234512345",
            Lo3ElementEnum.ELEMENT_2020,
            "1234123412",
            Lo3ElementEnum.ELEMENT_6110,
            "P",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_0210,
            "Voornaam1 Voornaam2",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "de",
            Lo3ElementEnum.ELEMENT_0240,
            "geslachtsnaam",
            Lo3ElementEnum.ELEMENT_0320,
            "0518",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_0410,
            "M",
            Lo3ElementEnum.ELEMENT_2010,
            "",
            Lo3ElementEnum.ELEMENT_2020,
            "",
            Lo3ElementEnum.ELEMENT_6110,
            "E",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }
}
