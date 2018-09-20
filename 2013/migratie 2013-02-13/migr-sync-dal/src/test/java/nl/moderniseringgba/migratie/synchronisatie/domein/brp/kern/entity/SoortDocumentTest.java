/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Alle getters/setters worden getest in de GetterEnSetterTest klasse
 */
public class SoortDocumentTest {

    private final SoortDocument soortDoc = new SoortDocument();

    @Test
    public void categorieSoortDocument() {
        soortDoc.setCategorieSoortDocument(CategorieSoortDocument.parseId(1));
        final CategorieSoortDocument catSoortDoc = soortDoc.getCategorieSoortDocument();
        Assert.assertEquals(CategorieSoortDocument.NEDERLANDSE_AKTE, catSoortDoc);
        Assert.assertNotNull(catSoortDoc.getNaam());
        Assert.assertNotNull(catSoortDoc.getOmschrijving());

        try {
            catSoortDoc.getCode();
            Assert.fail("Er wordt een UnsupportedOperationException verwacht");
        } catch (final UnsupportedOperationException e) {
            Assert.assertNotNull(e);
        }
        soortDoc.setCategorieSoortDocument(null);
        Assert.assertNull(soortDoc.getCategorieSoortDocument());
    }

    @Test
    public void isInhoudelijkGelijkAanGeenInhoud() {
        final SoortDocument soortDoc2 = new SoortDocument();
        Assert.assertTrue(soortDoc.isInhoudelijkGelijkAan(soortDoc2));
    }

    @Test
    public void isInhoudelijkGelijkAanInhoudelijk() {
        final String omschrijving = "omschrijving";
        final SoortDocument soortDoc2 = new SoortDocument();
        soortDoc2.setOmschrijving(omschrijving);
        soortDoc.setOmschrijving(omschrijving);
        soortDoc2.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        soortDoc.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        Assert.assertTrue(soortDoc.isInhoudelijkGelijkAan(soortDoc2));
    }

    @Test
    public void isInhoudelijkGelijkAanNull() {
        Assert.assertFalse(soortDoc.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkAanZelfdeInstantie() {
        Assert.assertTrue(soortDoc.isInhoudelijkGelijkAan(soortDoc));
    }

    @Test
    public void isInhoudelijkGelijkAanAnderCategorieSoortDocument() {
        final SoortDocument soortDoc2 = new SoortDocument();
        soortDoc2.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        Assert.assertFalse(soortDoc.isInhoudelijkGelijkAan(soortDoc2));

        soortDoc.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        soortDoc2.setCategorieSoortDocument(null);
        Assert.assertFalse(soortDoc.isInhoudelijkGelijkAan(soortDoc2));
    }

    @Test
    public void isInhoudelijkGelijkAanAnderOmschrijving() {
        final SoortDocument soortDoc2 = new SoortDocument();
        soortDoc2.setOmschrijving("omschrijving");
        Assert.assertFalse(soortDoc.isInhoudelijkGelijkAan(soortDoc2));

        soortDoc.setOmschrijving("omschrijving");
        soortDoc2.setOmschrijving(null);
        Assert.assertFalse(soortDoc.isInhoudelijkGelijkAan(soortDoc2));
    }
}
