/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Lo3CategorieEnumTest {

    @Test
    public void testIsValidCategorieString() {
        assertTrue(Lo3CategorieEnum.isValidCategorie("01"));
        assertTrue(Lo3CategorieEnum.isValidCategorie("51"));
    }

    @Test
    public void testIsValidCategorieInt() {
        assertTrue(Lo3CategorieEnum.isValidCategorie(1));
        assertTrue(Lo3CategorieEnum.isValidCategorie(51));
    }

    @Test
    public void testValueOfCategorieString() {
        assertEquals(Lo3CategorieEnum.CATEGORIE_01, Lo3CategorieEnum.valueOfCategorie("01"));
        assertEquals(Lo3CategorieEnum.CATEGORIE_51, Lo3CategorieEnum.valueOfCategorie("51"));
        assertNull(Lo3CategorieEnum.valueOfCategorie("99"));
    }

    @Test
    public void testValueOfCategorieInt() {
        assertEquals(Lo3CategorieEnum.CATEGORIE_01, Lo3CategorieEnum.valueOfCategorie(1));
        assertEquals(Lo3CategorieEnum.CATEGORIE_51, Lo3CategorieEnum.valueOfCategorie(51));
        assertNull(Lo3CategorieEnum.valueOfCategorie(99));
    }
}
