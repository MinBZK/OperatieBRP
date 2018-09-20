/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

/**
 * Verblijfplaats.
 */
public class MutatieCategorie08IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep09Gemeente() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwBijhoudingRecord(actie).bijhoudingspartij(59901).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0910,
            "0599",
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0910,
            "0518",
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep10Adreshouding() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();
        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, "hisVolledigImpl", adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actie)
                    .aangeverAdreshouding("I")
                    .datumAanvangAdreshouding(19391228)
                    .gemeente((short) 518)
                    .gemeentedeel("links")
                    .huisletter("A")
                    .huisnummer(10)
                    .huisnummertoevoeging("B")
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("naam")
                    .postcode("2245HJ")
                    .redenWijziging("P")
                    .soort(FunctieAdres.BRIEFADRES)
                    .woonplaatsnaam("Rotterdam")
                    .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1010,
            "B",
            Lo3ElementEnum.ELEMENT_1020,
            "links",
            Lo3ElementEnum.ELEMENT_1030,
            "19391228",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1010,
            "W",
            Lo3ElementEnum.ELEMENT_1020,
            "deel vd gemeente",
            Lo3ElementEnum.ELEMENT_1030,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep11Adres() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();
        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, "hisVolledigImpl", adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actie)
                    .aangeverAdreshouding("I")
                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                    .gemeente((short) 518)
                    .gemeentedeel("deel vd gemeente")
                    .huisletter("V")
                    .huisnummer(123)
                    .huisnummertoevoeging("Y")
                    .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.BY)
                    .landGebied((short) 6030)
                    .afgekorteNaamOpenbareRuimte("straatnaam")
                    .naamOpenbareRuimte("openbareruimte")
                    .postcode("6643WE")
                    .redenWijziging("P")
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam("Rotjeknor")
                    .identificatiecodeAdresseerbaarObject("IDENT-001")
                    .identificatiecodeNummeraanduiding("IDENT-002")
                    .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1110,
            "straatnaam",
            Lo3ElementEnum.ELEMENT_1115,
            "openbareruimte",
            Lo3ElementEnum.ELEMENT_1120,
            "123",
            Lo3ElementEnum.ELEMENT_1130,
            "V",
            Lo3ElementEnum.ELEMENT_1140,
            "Y",
            Lo3ElementEnum.ELEMENT_1150,
            "by",
            Lo3ElementEnum.ELEMENT_1160,
            "6643WE",
            Lo3ElementEnum.ELEMENT_1170,
            "Rotjeknor",
            Lo3ElementEnum.ELEMENT_1180,
            "IDENT-001",
            Lo3ElementEnum.ELEMENT_1190,
            "IDENT-002",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1110,
            "",
            Lo3ElementEnum.ELEMENT_1115,
            "naam",
            Lo3ElementEnum.ELEMENT_1120,
            "10",
            Lo3ElementEnum.ELEMENT_1130,
            "A",
            Lo3ElementEnum.ELEMENT_1140,
            "B",
            Lo3ElementEnum.ELEMENT_1150,
            "",
            Lo3ElementEnum.ELEMENT_1160,
            "2245HJ",
            Lo3ElementEnum.ELEMENT_1170,
            "Rotterdam",
            Lo3ElementEnum.ELEMENT_1180,
            "",
            Lo3ElementEnum.ELEMENT_1190,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep12Locatie() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();
        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, "hisVolledigImpl", adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actie)
                    .aangeverAdreshouding("I")
                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                    .gemeente((short) 518)
                    .gemeentedeel("deel vd gemeente")
                    .huisletter("A")
                    .huisnummer(10)
                    .huisnummertoevoeging("B")
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("naam")
                    .postcode("2245HJ")
                    .redenWijziging("P")
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam("Rotterdam")
                    .locatieomschrijving("locatieomschrijving")
                    .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1210,
            "locatieomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1210,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep13Emigratie() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwMigratieRecord(actie)
               .soortMigratie(SoortMigratie.EMIGRATIE)
               .landGebiedMigratie((short) 5002)
               .buitenlandsAdresRegel1Migratie("regel1")
               .buitenlandsAdresRegel2Migratie("regel2")
               .buitenlandsAdresRegel3Migratie("regel3")
               .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1310,
            "5002",
            Lo3ElementEnum.ELEMENT_1320,
            "19400101",
            Lo3ElementEnum.ELEMENT_1330,
            "regel1",
            Lo3ElementEnum.ELEMENT_1340,
            "regel2",
            Lo3ElementEnum.ELEMENT_1350,
            "regel3",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1310,
            "",
            Lo3ElementEnum.ELEMENT_1320,
            "",
            Lo3ElementEnum.ELEMENT_1330,
            "",
            Lo3ElementEnum.ELEMENT_1340,
            "",
            Lo3ElementEnum.ELEMENT_1350,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep14Immigratie() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwMigratieRecord(actie).soortMigratie(SoortMigratie.IMMIGRATIE).landGebiedMigratie((short) 5002).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_1410,
            "5002",
            Lo3ElementEnum.ELEMENT_1420,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_1410,
            "",
            Lo3ElementEnum.ELEMENT_1420,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep72AdresaangifteAangever() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();
        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, "hisVolledigImpl", adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actie)
                    .aangeverAdreshouding("P")
                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                    .gemeente((short) 518)
                    .gemeentedeel("deel vd gemeente")
                    .huisletter("A")
                    .huisnummer(10)
                    .huisnummertoevoeging("B")
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("naam")
                    .postcode("2245HJ")
                    .redenWijziging("P")
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam("Rotterdam")
                    .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "P",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "I",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep72AdresaangifteReden() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();
        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, "hisVolledigImpl", adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actie)
                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                    .gemeente((short) 518)
                    .gemeentedeel("deel vd gemeente")
                    .huisletter("A")
                    .huisnummer(10)
                    .huisnummertoevoeging("B")
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("naam")
                    .postcode("2245HJ")
                    .redenWijziging("B")
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam("Rotterdam")
                    .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "T",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_7210,
            "I",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep75DocumentindicatieTrue() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwBijhoudingRecord(actie).bijhoudingspartij(51801).indicatieOnverwerktDocumentAanwezig(true).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_7510,
            "1",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_7510,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep75DocumentindicatieFalse() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwBijhoudingRecord(actie).bijhoudingspartij(51801).indicatieOnverwerktDocumentAanwezig(false).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_08,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19400101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_58,
            true,
            Lo3ElementEnum.ELEMENT_0920,
            "19200101",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }
}
