/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Assert;
import org.junit.Test;

public class Lo3Lg01BerichtWaardeTest {

    @Test
    public void testIsAnummerWijziging() throws Exception {
        List<Lo3CategorieWaarde> waardeList = new ArrayList<>();
        Lo3CategorieWaarde waarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        Lo3CategorieWaarde waarde2 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 1, 1);
        waardeList.add(waarde);
        waardeList.add(waarde2);
        Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde = new Lo3Lg01BerichtWaarde(waardeList);
        Assert.assertFalse(lo3Lg01BerichtWaarde.isAnummerWijziging());
        String newA = "123456";
        String oudA = "653214";
        lo3Lg01BerichtWaarde = new Lo3Lg01BerichtWaarde(waardeList, newA, oudA);
        Assert.assertTrue(lo3Lg01BerichtWaarde.isAnummerWijziging());
        Assert.assertEquals("123456", lo3Lg01BerichtWaarde.getAnummer());
        Assert.assertEquals("653214", lo3Lg01BerichtWaarde.getOudAnummer());
        Assert.assertEquals(2, lo3Lg01BerichtWaarde.getLo3CategorieWaardeList().size());
    }
}
