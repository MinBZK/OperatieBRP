/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Test;

/**
 * Lo3CategorieWaardeTest.
 */
public class Lo3CategorieWaardeTest {
    Lo3CategorieWaarde waarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    Lo3CategorieWaarde waarde2 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, 0, 0);
    Lo3CategorieWaarde waarde3 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 0, 0);

    @Test
    public void testIsGevuld() throws Exception {
        waarde.getElementen().put(Lo3ElementEnum.ANUMMER, "123123");
        waarde2.getElementen().put(Lo3ElementEnum.ANUMMER, null);
        waarde3.getElementen().put(Lo3ElementEnum.ANUMMER, "");
        Assert.assertTrue(waarde.isGevuld());
        Assert.assertFalse(waarde2.isGevuld());
        Assert.assertFalse(waarde3.isGevuld());

        Map<Lo3ElementEnum, String> elementen = new HashMap<>();
        elementen.put(Lo3ElementEnum.ANUMMER, "123123");
        Lo3CategorieWaarde waarde4 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 0, 0, elementen);
        Assert.assertTrue(waarde4.isGevuld());
        Lo3CategorieWaarde waarde5 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 0, 0, null);
        Assert.assertFalse(waarde5.isGevuld());
    }

    @Test
    public void testRemoveElement() {
        Map<Lo3ElementEnum, String> elementen = new HashMap<>();
        elementen.put(Lo3ElementEnum.ANUMMER, "123123");
        Lo3CategorieWaarde waarde4 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 0, 0, elementen);
        Assert.assertTrue(waarde4.isGevuld());
        waarde4.addElement(Lo3ElementEnum.ANUMMER, null);
        Assert.assertTrue(waarde4.isEmpty());
    }

    @Test
    public void Testherkomst() {
        Lo3Herkomst lo3Herkomst = waarde.getLo3Herkomst();
        Assert.assertTrue(lo3Herkomst.getCategorie().equals(Lo3CategorieEnum.CATEGORIE_04));
    }

    @Test
    public void testCompare() {
        Assert.assertFalse(waarde.equals(null));
        Assert.assertFalse(waarde.equals(""));
        Assert.assertFalse(waarde.equals(waarde2));
        Assert.assertTrue(waarde.equals(waarde));

    }
}
