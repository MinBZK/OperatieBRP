/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerificatieHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Inschrijving.
 */
public class MutatieCategorie07IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep67Opschorting() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwBijhoudingRecord(actie).bijhoudingspartij(51801).nadereBijhoudingsaard(NadereBijhoudingsaard.MINISTERIEEL_BESLUIT).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6710, "19400101", Lo3ElementEnum.ELEMENT_6720, "M");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6710, "", Lo3ElementEnum.ELEMENT_6720, "");
        Assert.assertEquals(4, resultaat.size());
    }

    @Test
    public void testGroep68Opname() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwInschrijvingRecord(actie).datumInschrijving(19950304).versienummer(1L).datumtijdstempel(new Date(123)).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6810, "19950304");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6810, "19200101");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep69GemeentePk() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwPersoonskaartRecord(actie).gemeentePersoonskaart(59901).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_6910, "0599");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_6910, "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep70Geheim() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder indicatieBuilder =
                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder();
        indicatieBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(indicatieBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_7010, "7");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_7010, "0");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep71Verificatie() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final Partij verificatiePartij = TestPartijBuilder.maker().metNaam("veri").metSoort(SoortPartij.GEMEENTE).metCode(999999).maak();

        final PersoonVerificatieHisVolledigImplBuilder verificatieBuilder =
                new PersoonVerificatieHisVolledigImplBuilder(verificatiePartij, new NaamEnumeratiewaardeAttribuut("verificatienaam"));
        verificatieBuilder.nieuwStandaardRecord(actie).datum(19500203).eindeRecord();
        builder.voegPersoonVerificatieToe(verificatieBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_07,
            true,
            Lo3ElementEnum.ELEMENT_7110,
            "19500203",
            Lo3ElementEnum.ELEMENT_7120,
            "verificatienaam",
            Lo3ElementEnum.ELEMENT_8810,
            "9999");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_57,
            true,
            Lo3ElementEnum.ELEMENT_7110,
            "",
            Lo3ElementEnum.ELEMENT_7120,
            "",
            Lo3ElementEnum.ELEMENT_8810,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep80Synchroniciteit() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        builder.nieuwInschrijvingRecord(actie).datumInschrijving(19200101).versienummer(2L).datumtijdstempel(new Date(2336239723L)).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_07,
            true,
            Lo3ElementEnum.ELEMENT_8010,
            "0002",
            Lo3ElementEnum.ELEMENT_8020,
            "19700128005719723");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_57,
            true,
            Lo3ElementEnum.ELEMENT_8010,
            "0001",
            Lo3ElementEnum.ELEMENT_8020,
            "19700101000000123");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep87PkConversie() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwPersoonskaartRecord(actie).indicatiePersoonskaartVolledigGeconverteerd(true).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_07, true, Lo3ElementEnum.ELEMENT_8710, "P");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_57, true, Lo3ElementEnum.ELEMENT_8710, "");
        Assert.assertEquals(2, resultaat.size());
    }
}
