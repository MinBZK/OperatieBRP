/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;
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
 * Huwelijk.
 */
public class MutatieCategorie05IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    final ActieModel actieHuwelijk =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
    private PersoonHisVolledigImplBuilder partnerBuilder;
    private HuwelijkHisVolledigImplBuilder relatieBuilder;

    @Before
    public void setupHuwelijk() {
        partnerBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        partnerBuilder.nieuwGeboorteRecord(actieHuwelijk)
                      .datumGeboorte(19200202)
                      .gemeenteGeboorte((short) 599)
                      .landGebiedGeboorte((short) 6030)
                      .eindeRecord();
        partnerBuilder.nieuwGeslachtsaanduidingRecord(actieHuwelijk).geslachtsaanduiding(Geslachtsaanduiding.VROUW).eindeRecord();
        partnerBuilder.nieuwIdentificatienummersRecord(actieHuwelijk).administratienummer(1231231234L).burgerservicenummer(345345345).eindeRecord();
        partnerBuilder.nieuwInschrijvingRecord(actieHuwelijk)
                      .datumInschrijving(actieHuwelijk.getDatumAanvangGeldigheid())
                      .versienummer(1L)
                      .datumtijdstempel(new Date(123456))
                      .eindeRecord();
        partnerBuilder.nieuwNaamgebruikRecord(actieHuwelijk).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        partnerBuilder.nieuwAfgeleidAdministratiefRecord(actieHuwelijk)
                      .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
                      .tijdstipLaatsteWijziging(actieHuwelijk.getTijdstipRegistratie())
                      .tijdstipLaatsteWijzigingGBASystematiek(actieHuwelijk.getTijdstipRegistratie())
                      .eindeRecord();
        partnerBuilder.nieuwSamengesteldeNaamRecord(actieHuwelijk)
                      .geslachtsnaamstam("Pallo")
                      .voorvoegsel("los")
                      .voornamen("Maria")
                      .scheidingsteken(" ")
                      .eindeRecord();

        relatieBuilder = new HuwelijkHisVolledigImplBuilder();
        ReflectionTestUtils.setField(ReflectionTestUtils.getField(relatieBuilder, "hisVolledigImpl"), "iD", 12354);

        relatieBuilder.nieuwStandaardRecord(actieHuwelijk)
                      .datumAanvang(actieHuwelijk.getDatumAanvangGeldigheid())
                      .gemeenteAanvang((short) 433)
                      .landGebiedAanvang((short) 6030)
                      .eindeRecord();
    }

    private PersoonHisVolledigImpl maakHuwelijk(final ActieModel... acties) {
        final PersoonHisVolledigImpl partner = partnerBuilder.build();
        final PartnerHisVolledigImplBuilder partnerBetrokkendheidBuilder = new PartnerHisVolledigImplBuilder(null, partner);
        final PartnerHisVolledigImpl partnerBetrokkendheid = partnerBetrokkendheidBuilder.build();

        final HuwelijkHisVolledigImpl relatie = relatieBuilder.build();

        relatie.getBetrokkenheden().add(partnerBetrokkendheid);

        final PartnerHisVolledigImplBuilder mijnBetrokkenheidBuilder = new PartnerHisVolledigImplBuilder(relatie, null);
        builder.voegBetrokkenheidToe(mijnBetrokkenheidBuilder.build());
        final PersoonHisVolledigImpl ik = builder.build();

        return ik;
    }

    @Test
    public void testSluitingHuwelijk() {
        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0110,
            "1231231234",
            Lo3ElementEnum.ELEMENT_0120,
            "345345345",
            Lo3ElementEnum.ELEMENT_0210,
            "Maria",
            Lo3ElementEnum.ELEMENT_0230,
            "los",
            Lo3ElementEnum.ELEMENT_0240,
            "Pallo",
            Lo3ElementEnum.ELEMENT_0310,
            "19200202",
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_1510,
            "H",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
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
            Lo3ElementEnum.ELEMENT_0410,
            "",
            Lo3ElementEnum.ELEMENT_0610,
            "",
            Lo3ElementEnum.ELEMENT_0620,
            "",
            Lo3ElementEnum.ELEMENT_0630,
            "",
            Lo3ElementEnum.ELEMENT_1510,
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

        partnerBuilder.nieuwIdentificatienummersRecord(actie).administratienummer(1231231234L).burgerservicenummer(543543543).eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "543543543",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
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

        partnerBuilder.nieuwSamengesteldeNaamRecord(actie)
                      .voornamen("Voornaam3")
                      .adellijkeTitel(new AdellijkeTitelCodeAttribuut("P"))
                      .voorvoegsel("het")
                      .scheidingsteken(" ")
                      .geslachtsnaamstam("achternaam")
                      .eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
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
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0210,
            "Maria",
            Lo3ElementEnum.ELEMENT_0220,
            "",
            Lo3ElementEnum.ELEMENT_0230,
            "los",
            Lo3ElementEnum.ELEMENT_0240,
            "Pallo",
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

        partnerBuilder.nieuwGeboorteRecord(actie).datumGeboorte(19590101).gemeenteGeboorte((short) 222).landGebiedGeboorte((short) 6030).eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0310,
            "19590101",
            Lo3ElementEnum.ELEMENT_0320,
            "0222",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0310,
            "19200202",
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep03GeboorteBuiteland() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        partnerBuilder.nieuwGeboorteRecord(actie)
                      .datumGeboorte(19200202)
                      .landGebiedGeboorte((short) 5010)
                      .buitenlandsePlaatsGeboorte("Brussel")
                      .eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0320,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0330,
            "5010",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0320,
            "0599",
            Lo3ElementEnum.ELEMENT_0330,
            "6030",
            // Lo3ElementEnum.ELEMENT_8510,
            // "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep04Geslachtsaanduiding() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        partnerBuilder.nieuwGeslachtsaanduidingRecord(actie).geslachtsaanduiding(Geslachtsaanduiding.ONBEKEND).eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "O",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0410,
            "V",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep06Sluiting() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        relatieBuilder.nieuwStandaardRecord(actie)
                      .datumAanvang(actie.getDatumAanvangGeldigheid())
                      .buitenlandsePlaatsAanvang("Brussel")
                      .landGebiedAanvang((short) 5002)
                      .eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19600101",
            Lo3ElementEnum.ELEMENT_0620,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0630,
            "5002",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep07Ontbinding() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        relatieBuilder.nieuwStandaardRecord(actie)
                      .datumAanvang(actieHuwelijk.getDatumAanvangGeldigheid())
                      .gemeenteAanvang((short) 433)
                      .landGebiedAanvang((short) 6030)
                      .datumEinde(actie.getDatumAanvangGeldigheid())
                      .buitenlandsePlaatsEinde("Brussel")
                      .landGebiedEinde((short) 5002)
                      .eindeRecord();

        maakHuwelijk();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie, actieHuwelijk);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_05,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "",
            Lo3ElementEnum.ELEMENT_0620,
            "",
            Lo3ElementEnum.ELEMENT_0630,
            "",
            Lo3ElementEnum.ELEMENT_0710,
            "19600101",
            Lo3ElementEnum.ELEMENT_0720,
            "Brussel",
            Lo3ElementEnum.ELEMENT_0730,
            "5002",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_55,
            true,
            Lo3ElementEnum.ELEMENT_0610,
            "19400101",
            Lo3ElementEnum.ELEMENT_0620,
            "0433",
            Lo3ElementEnum.ELEMENT_0630,
            "6030",
            Lo3ElementEnum.ELEMENT_0710,
            "",
            Lo3ElementEnum.ELEMENT_0720,
            "",
            Lo3ElementEnum.ELEMENT_0730,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");

        Assert.assertEquals(2, resultaat.size());
    }
}
