/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

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
    public void testBestaandeCategorie() {
        try {
            assertEquals(Lo3CategorieEnum.CATEGORIE_01, Lo3CategorieEnum.getLO3Categorie("01"));
            assertEquals(Lo3CategorieEnum.CATEGORIE_51, Lo3CategorieEnum.getLO3Categorie("51"));
            assertEquals(Lo3CategorieEnum.CATEGORIE_01, Lo3CategorieEnum.getLO3Categorie(1));
            assertEquals(Lo3CategorieEnum.CATEGORIE_51, Lo3CategorieEnum.getLO3Categorie(51));
        } catch (final Lo3SyntaxException lse) {
            fail("Geen Lo3SyntaxException verwacht");
        }
    }

    @Test
    public void testBepaalHistorischeCategorie() {
        assertEquals(null, Lo3CategorieEnum.bepaalHistorischeCategorie(Lo3CategorieEnum.CATEGORIE_07));
        assertEquals(Lo3CategorieEnum.CATEGORIE_57, Lo3CategorieEnum.bepaalHistorischeCategorie(Lo3CategorieEnum.CATEGORIE_57));
        assertEquals(Lo3CategorieEnum.CATEGORIE_57, Lo3CategorieEnum.bepaalHistorischeCategorie(Lo3CategorieEnum.CATEGORIE_07, true));
    }

    @Test
    public void testNietBestaandeCategorie() {
        try {
            Lo3CategorieEnum.getLO3Categorie("99");
            fail("Lo3SyntaxException verwacht");
        } catch (final Lo3SyntaxException lse) {
            assertNotNull(lse.getCause());
            assertNotNull(lse.getMessage());
        }
        try {
            Lo3CategorieEnum.getLO3Categorie(99);
            fail("Lo3SyntaxException verwacht");
        } catch (final IllegalArgumentException lse) {
            assertNotNull(lse.getMessage());
        }
    }
}
