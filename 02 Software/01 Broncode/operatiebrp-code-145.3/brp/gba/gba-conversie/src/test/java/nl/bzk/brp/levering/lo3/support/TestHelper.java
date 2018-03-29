/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.support;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;

public class TestHelper {
    public static void assertElementen(
        final List<Lo3CategorieWaarde> resultaat,
        final Lo3CategorieEnum categorie,
        final boolean volledig,
        final Object... elementen)
    {
        Assert.assertTrue(elementen.length % 2 == 0);
        boolean found = false;
        for (final Lo3CategorieWaarde categorieWaarde : resultaat) {
            if (categorie == categorieWaarde.getCategorie()) {
                found = true;

                for (int elementIndex = 0; elementIndex < elementen.length; elementIndex = elementIndex + 2) {
                    final Lo3ElementEnum element = (Lo3ElementEnum) elementen[elementIndex];
                    final String elementWaarde = (String) elementen[elementIndex + 1];

                    Assert.assertTrue("Element "
                                      + element.getElementNummer(true)
                                      + " ("
                                      + element.getLabel()
                                      + ") komt niet voor in categorie "
                                      + categorie
                                      + " ("
                                      + categorie.getLabel()
                                      + ").",
                        categorieWaarde.getElementen().containsKey(element));

                    Assert.assertEquals("Element "
                                        + element.getElementNummer(true)
                                        + " ("
                                        + element.getLabel()
                                        + ") in categorie "
                                        + categorie
                                        + " ("
                                        + categorie.getLabel()
                                        + ") heeft niet de verwachte waarde.",
                        elementWaarde,
                        categorieWaarde.getElement(element));
                }

                if (volledig) {
                    Assert.assertEquals("Ander aantal elementen dan verwacht in categorie.", elementen.length / 2, categorieWaarde.getElementen().size());
                }
            }
        }

        if (elementen.length != 0) {
            Assert.assertTrue("Categorie " + categorie + " niet gevonden in resultaat", found);
        } else {
            Assert.assertFalse("Categorie " + categorie + " niet verwacht in resultaat", found);
        }
    }
}
