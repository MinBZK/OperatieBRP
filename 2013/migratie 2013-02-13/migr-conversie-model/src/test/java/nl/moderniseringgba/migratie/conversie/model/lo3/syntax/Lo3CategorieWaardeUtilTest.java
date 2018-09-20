/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.syntax;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

public class Lo3CategorieWaardeUtilTest {

    @Test
    public void test1() {
        final List<Lo3CategorieWaarde> categorieWaarden = new ArrayList<Lo3CategorieWaarde>();

        Lo3CategorieWaardeUtil.setElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 2, 5,
                Lo3ElementEnum.ELEMENT_0510, "5012");

        {
            final List<Lo3CategorieWaarde> expected = new ArrayList<Lo3CategorieWaarde>();
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 2, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 1));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 2));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 3));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 4));
            final Lo3CategorieWaarde target524 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 5);
            target524.addElement(Lo3ElementEnum.ELEMENT_0510, "5012");
            expected.add(target524);

            Assert.assertTrue(equal(expected, categorieWaarden));

            Assert.assertTrue(equal(expected, categorieWaarden));
            Assert.assertEquals(null, Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden,
                    Lo3CategorieEnum.CATEGORIE_04, 1, 3, Lo3ElementEnum.ELEMENT_6510));
            Assert.assertEquals("5012", Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden,
                    Lo3CategorieEnum.CATEGORIE_04, 2, 5, Lo3ElementEnum.ELEMENT_0510));
        }

        Lo3CategorieWaardeUtil.setElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 1, 3,
                Lo3ElementEnum.ELEMENT_6510, "B");

        {
            final List<Lo3CategorieWaarde> expected = new ArrayList<Lo3CategorieWaarde>();
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 1));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 2));
            final Lo3CategorieWaarde target13 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 3);
            target13.addElement(Lo3ElementEnum.ELEMENT_6510, "B");
            expected.add(target13);
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 2, 0));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 1));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 2));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 3));
            expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 4));
            final Lo3CategorieWaarde target25 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 5);
            target25.addElement(Lo3ElementEnum.ELEMENT_0510, "5012");
            expected.add(target25);

            Assert.assertTrue(equal(expected, categorieWaarden));
            Assert.assertEquals("B", Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden,
                    Lo3CategorieEnum.CATEGORIE_04, 1, 3, Lo3ElementEnum.ELEMENT_6510));
            Assert.assertEquals("5012", Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden,
                    Lo3CategorieEnum.CATEGORIE_04, 2, 5, Lo3ElementEnum.ELEMENT_0510));
        }
    }

    public boolean equal(final List<Lo3CategorieWaarde> categorieen1, final List<Lo3CategorieWaarde> categorieen2) {
        if (categorieen1.size() != categorieen2.size()) {
            return false;
        }

        for (int index = 0; index < categorieen1.size(); index++) {
            if (!equal(categorieen1.get(index), categorieen2.get(index))) {
                return false;
            }
        }

        return true;
    }

    public boolean equal(final Lo3CategorieWaarde categorie1, final Lo3CategorieWaarde categorie2) {
        return new EqualsBuilder().append(categorie1.getCategorie(), categorie2.getCategorie())
                .append(categorie1.getElementen(), categorie2.getElementen()).isEquals();
    }
}
