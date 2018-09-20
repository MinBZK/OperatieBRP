/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
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
 * Overlijden.
 */
public class MutatieCategorie06IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void testGroep08Overlijden() {
        final ActieModel actie =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        builder.nieuwOverlijdenRecord(actie).datumOverlijden(19400101).gemeenteOverlijden((short) 52).landGebiedOverlijden((short) 6030).eindeRecord();

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_06,
            true,
            Lo3ElementEnum.ELEMENT_0810,
            "19400101",
            Lo3ElementEnum.ELEMENT_0820,
            "0052",
            Lo3ElementEnum.ELEMENT_0830,
            "6030",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_56,
            true,
            Lo3ElementEnum.ELEMENT_0810,
            "",
            Lo3ElementEnum.ELEMENT_0820,
            "",
            Lo3ElementEnum.ELEMENT_0830,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "");
        Assert.assertEquals(2, resultaat.size());
    }

}
