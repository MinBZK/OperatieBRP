/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Reisdocument.
 */
public class MutatieCategorie12IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep35NederlandsReisdocument() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final SoortNederlandsReisdocument soortNederlandsReisdocument =
                new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut("PN"), null, null, null);
        final PersoonReisdocumentHisVolledigImplBuilder reisdocumentBuilder = new PersoonReisdocumentHisVolledigImplBuilder(soortNederlandsReisdocument);
        reisdocumentBuilder.nieuwStandaardRecord(actie)
                           .nummer("123")
                           .datumUitgifte(actie.getDatumAanvangGeldigheid())
                           .datumIngangDocument(19400202)
                           .autoriteitVanAfgifte("1234")
                           .datumEindeDocument(19550101)
                           .eindeRecord();
        builder.voegPersoonReisdocumentToe(reisdocumentBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_12,
            true,
            Lo3ElementEnum.ELEMENT_3510,
            "PN",
            Lo3ElementEnum.ELEMENT_3520,
            "123",
            Lo3ElementEnum.ELEMENT_3530,
            "19400101",
            Lo3ElementEnum.ELEMENT_3540,
            "1234",
            Lo3ElementEnum.ELEMENT_3550,
            "19550101",
            Lo3ElementEnum.ELEMENT_8510,
            "19400202",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_62,
            true,
            Lo3ElementEnum.ELEMENT_3510,
            "",
            Lo3ElementEnum.ELEMENT_3520,
            "",
            Lo3ElementEnum.ELEMENT_3530,
            "",
            Lo3ElementEnum.ELEMENT_3540,
            "",
            Lo3ElementEnum.ELEMENT_3550,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep36Signalering() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder signaleringBuilder =
                new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder();
        signaleringBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();

        builder.voegPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentToe(signaleringBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_12,
            true,
            Lo3ElementEnum.ELEMENT_3610,
            "1",
            Lo3ElementEnum.ELEMENT_8510,
            "19400102",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_62,
            true,
            Lo3ElementEnum.ELEMENT_3610,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        // Reisdocument
        final SoortNederlandsReisdocument soortNederlandsReisdocument =
                new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut("PN"), null, null, null);
        final PersoonReisdocumentHisVolledigImplBuilder reisdocumentBuilder = new PersoonReisdocumentHisVolledigImplBuilder(soortNederlandsReisdocument);
        reisdocumentBuilder.nieuwStandaardRecord(actie)
                           .nummer("123")
                           .datumUitgifte(19450101)
                           .autoriteitVanAfgifte("1234")
                           .datumIngangDocument(19400202)
                           .datumEindeDocument(19550101)
                           .eindeRecord();
        builder.voegPersoonReisdocumentToe(reisdocumentBuilder.build());

        // Signalering
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder signaleringBuilder =
                new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder();
        signaleringBuilder.nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord();
        builder.voegPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentToe(signaleringBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);
        Assert.assertEquals(4, resultaat.size());
    }

}
