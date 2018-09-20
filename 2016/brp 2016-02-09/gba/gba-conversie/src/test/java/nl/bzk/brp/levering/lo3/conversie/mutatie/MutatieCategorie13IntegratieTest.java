/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Kiesrecht.
 */
public class MutatieCategorie13IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep31EuropeesKiesrecht() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwDeelnameEUVerkiezingenRecord(actie)
               .indicatieDeelnameEUVerkiezingen(false)
               .datumAanleidingAanpassingDeelnameEUVerkiezingen(19400101)
               .datumVoorzienEindeUitsluitingEUVerkiezingen(19450101)
               .eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_13,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "1",
            Lo3ElementEnum.ELEMENT_3120,
            "19400101",
            Lo3ElementEnum.ELEMENT_3130,
            "19450101");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_63,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "",
            Lo3ElementEnum.ELEMENT_3120,
            "",
            Lo3ElementEnum.ELEMENT_3130,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep38UitsluitingKiesrecht() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwUitsluitingKiesrechtRecord(actie).indicatieUitsluitingKiesrecht(Ja.J).datumVoorzienEindeUitsluitingKiesrecht(19450101).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_13, true, Lo3ElementEnum.ELEMENT_3810, "A", Lo3ElementEnum.ELEMENT_3820, "19450101");
        assertElementen(resultaat, Lo3CategorieEnum.CATEGORIE_63, true, Lo3ElementEnum.ELEMENT_3810, "", Lo3ElementEnum.ELEMENT_3820, "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testAlleInhoudelijkeGroepen() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwDeelnameEUVerkiezingenRecord(actie)
               .indicatieDeelnameEUVerkiezingen(false)
               .datumAanleidingAanpassingDeelnameEUVerkiezingen(19400101)
               .datumVoorzienEindeUitsluitingEUVerkiezingen(19450101)
               .eindeRecord();
        builder.nieuwUitsluitingKiesrechtRecord(actie).indicatieUitsluitingKiesrecht(Ja.J).datumVoorzienEindeUitsluitingKiesrecht(19450101).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_13,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "1",
            Lo3ElementEnum.ELEMENT_3120,
            "19400101",
            Lo3ElementEnum.ELEMENT_3130,
            "19450101",
            Lo3ElementEnum.ELEMENT_3810,
            "A",
            Lo3ElementEnum.ELEMENT_3820,
            "19450101");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_63,
            true,
            Lo3ElementEnum.ELEMENT_3110,
            "",
            Lo3ElementEnum.ELEMENT_3120,
            "",
            Lo3ElementEnum.ELEMENT_3130,
            "",
            Lo3ElementEnum.ELEMENT_3810,
            "",
            Lo3ElementEnum.ELEMENT_3820,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

}
