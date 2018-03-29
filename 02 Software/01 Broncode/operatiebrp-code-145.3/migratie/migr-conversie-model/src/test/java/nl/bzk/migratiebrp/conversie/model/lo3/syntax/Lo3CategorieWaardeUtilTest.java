/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

public class Lo3CategorieWaardeUtilTest {

    private static final String STRING_5012 = "5012";

    @Test
    public void testPrivateConstructor() throws IllegalAccessException, InstantiationException {
        try {
            final Constructor<?>[] constructors = Lo3CategorieWaardeUtil.class.getDeclaredConstructors();
            constructors[0].setAccessible(true);
            constructors[0].newInstance((Object[]) null);
            fail();
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void testDeepCopy() {
        final List<Lo3CategorieWaarde> lijst = new ArrayList<>();
        Lo3CategorieWaarde waarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
        waarde.getElementen().put(Lo3ElementEnum.ANUMMER, "123123");
        lijst.add(waarde);
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 0));
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 2, 0));
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 1));
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 2));
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 3));
        lijst.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 4));
        List<Lo3CategorieWaarde> result = Lo3CategorieWaardeUtil.deepCopy(lijst);
        assertEquals(1, result.get(0).getElementen().size());
        assertEquals("123123", result.get(0).getElementen().get(Lo3ElementEnum.ANUMMER));
        assertTrue(waarde.equals(result.get(0)));
        assertTrue(waarde.toString().equals(result.get(0).toString()));
        assertTrue(waarde.hashCode() == result.get(0).hashCode());
        assertTrue(result.get(1).getElementen().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsetElementWaarde() {
        final List<Lo3CategorieWaarde> categorieWaarden = new ArrayList<>();
        Lo3CategorieWaardeUtil.setElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_15, 2, 5, Lo3ElementEnum.ELEMENT_0510, STRING_5012);
    }

    @Test
    public void test1() {
        final List<Lo3CategorieWaarde> categorieWaarden = new ArrayList<>();

        Lo3CategorieWaardeUtil.setElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 2, 5, Lo3ElementEnum.ELEMENT_0510, STRING_5012);

        final List<Lo3CategorieWaarde> expected = new ArrayList<>();
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 0));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 2, 0));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 1));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 2));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 3));
        expected.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 4));
        final Lo3CategorieWaarde target524 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 5);
        target524.addElement(Lo3ElementEnum.ELEMENT_0510, STRING_5012);
        expected.add(target524);

        assertTrue(equal(expected, categorieWaarden));
        assertTrue(equal(expected, categorieWaarden));
        assertEquals(null,
                Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 1, 3, Lo3ElementEnum.ELEMENT_6510));
        assertEquals(STRING_5012,
                Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 2, 5, Lo3ElementEnum.ELEMENT_0510));
        Lo3CategorieWaardeUtil.setElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 1, 3, Lo3ElementEnum.ELEMENT_6510, "B");

        final List<Lo3CategorieWaarde> expected2 = new ArrayList<>();
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 0));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 1));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 2));
        final Lo3CategorieWaarde target13 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 1, 3);
        target13.addElement(Lo3ElementEnum.ELEMENT_6510, "B");
        expected2.add(target13);
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 2, 0));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 1));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 2));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 3));
        expected2.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 4));
        final Lo3CategorieWaarde target25 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_54, 2, 5);
        target25.addElement(Lo3ElementEnum.ELEMENT_0510, STRING_5012);
        expected2.add(target25);

        assertTrue(equal(expected2, categorieWaarden));
        assertEquals("B",
                Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 1, 3, Lo3ElementEnum.ELEMENT_6510));
        assertEquals(STRING_5012,
                Lo3CategorieWaardeUtil.getElementWaarde(categorieWaarden, Lo3CategorieEnum.CATEGORIE_04, 2, 5, Lo3ElementEnum.ELEMENT_0510));
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
                .append(categorie1.getElementen(), categorie2.getElementen())
                .isEquals();
    }
}
