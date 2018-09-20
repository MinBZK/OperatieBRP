/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Nationaliteit.
 */
public class MutatieCategorie04IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep04NationaliteitVerkrijging() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final Nationaliteit nationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut((short) 52), null, null, null);
        final PersoonNationaliteitHisVolledigImplBuilder nationaliteitBuilder = new PersoonNationaliteitHisVolledigImplBuilder(nationaliteit);
        nationaliteitBuilder.nieuwStandaardRecord(actie).eindeRecord();

        builder.voegPersoonNationaliteitToe(nationaliteitBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_04,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "0052",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_54,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Ignore("TODO FIX")
    @Test
    public void testGroep04NationaliteitBeeindiging() {
        final ActieModel actieVerkrijging =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        final ActieModel actieBeeindiging =
                PersoonHisVolledigUtil.maakActie(
                    3L,
                    SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                    SoortActie.CONVERSIE_G_B_A,
                    19600102,
                    19400101,
                    19600101,
                    partij);

        final Nationaliteit nationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut((short) 52), null, null, null);
        final PersoonNationaliteitHisVolledigImplBuilder nationaliteitBuilder = new PersoonNationaliteitHisVolledigImplBuilder(nationaliteit);
        nationaliteitBuilder.nieuwStandaardRecord(actieVerkrijging).eindeRecord(5001);
        builder.voegPersoonNationaliteitToe(nationaliteitBuilder.build());

        final PersoonHisVolledigImpl persoon = builder.build();

        final MaterieleHistorieSet<HisPersoonNationaliteitModel> nationaliteitHistorie =
                persoon.getNationaliteiten().iterator().next().getPersoonNationaliteitHistorie();
        debugHistorieSet(nationaliteitHistorie);

        final MaterieleHistorieImpl materieleHistorie =
                new MaterieleHistorieImpl(nationaliteitHistorie.getActueleRecord().getDatumAanvangGeldigheid(), actieBeeindiging.getDatumEindeGeldigheid());
        System.out.println("Datum aanvang: " + materieleHistorie.getDatumAanvangGeldigheid());
        System.out.println("Datum einde: " + materieleHistorie.getDatumEindeGeldigheid());
        materieleHistorie.setDatumTijdRegistratie(actieBeeindiging.getTijdstipRegistratie());
        nationaliteitHistorie.beeindig(materieleHistorie, actieBeeindiging);
        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actieVerkrijging, actieBeeindiging);

        debugHistorieSet(nationaliteitHistorie);

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(persoon, actieBeeindiging);
        debugResultaat(resultaat);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_04,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_54,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "0052",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep63VerkrijgingNL() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final Nationaliteit nationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut((short) 1), null, null, null);
        final PersoonNationaliteitHisVolledigImplBuilder nationaliteitBuilder = new PersoonNationaliteitHisVolledigImplBuilder(nationaliteit);
        nationaliteitBuilder.nieuwStandaardRecord(actie).redenVerkrijging((short) 1).eindeRecord();

        builder.voegPersoonNationaliteitToe(nationaliteitBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_04,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "0001",
            Lo3ElementEnum.ELEMENT_6310,
            "001",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_54,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "",
            Lo3ElementEnum.ELEMENT_6310,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Ignore("TODO FIX")
    @Test
    public void testGroep63VerliesNL() {
        final ActieModel actieVerkrijging =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);
        final ActieModel actieVerlies =
                PersoonHisVolledigUtil.maakActie(3L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19600101, partij);

        final Nationaliteit nationaliteit = new Nationaliteit(new NationaliteitcodeAttribuut((short) 1), null, null, null);
        final PersoonNationaliteitHisVolledigImplBuilder nationaliteitBuilder = new PersoonNationaliteitHisVolledigImplBuilder(nationaliteit);
        nationaliteitBuilder.nieuwStandaardRecord(actieVerkrijging).redenVerkrijging((short) 1).redenVerlies((short) 59).eindeRecord(5000);
        nationaliteitBuilder.nieuwStandaardRecord(actieVerlies).eindeRecord(5001);

        builder.voegPersoonNationaliteitToe(nationaliteitBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actieVerlies, actieVerkrijging);
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_04,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "",
            Lo3ElementEnum.ELEMENT_6310,
            "",
            Lo3ElementEnum.ELEMENT_6410,
            "059",
            Lo3ElementEnum.ELEMENT_8510,
            "19600101",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_54,
            true,
            Lo3ElementEnum.ELEMENT_0510,
            "0001",
            Lo3ElementEnum.ELEMENT_6310,
            "001",
            Lo3ElementEnum.ELEMENT_6410,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        Assert.assertEquals(2, resultaat.size());
    }

}
