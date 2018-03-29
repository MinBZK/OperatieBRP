/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.junit.Assert;
import org.junit.Test;

public class Lo3OnderzoekTest {
    private static final Lo3Datum DATUM = new Lo3Datum(19990101);

    @Test
    public void testOmvatElementInCategorie() {
        Assert.assertFalse(new Lo3Onderzoek(null, null, null).omvatElementInCategorie(Lo3ElementEnum.ELEMENT_0120, Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10020), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10080), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10100), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10120), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10130), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(510120), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(110000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10220), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_01));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_8610,
                Lo3CategorieEnum.CATEGORIE_01));

        Assert.assertFalse(new Lo3Onderzoek(null, null, null).omvatElementInCategorie(Lo3ElementEnum.ELEMENT_0120, Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10020), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10080), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10100), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(10120), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10130), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertTrue(new Lo3Onderzoek(Lo3Integer.wrap(510120), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(110000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10220), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_0120,
                Lo3CategorieEnum.CATEGORIE_51));
        Assert.assertFalse(new Lo3Onderzoek(Lo3Integer.wrap(10000), DATUM, null).omvatElementInCategorie(
                Lo3ElementEnum.ELEMENT_8610,
                Lo3CategorieEnum.CATEGORIE_51));
    }
}
