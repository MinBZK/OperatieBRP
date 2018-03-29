/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Alle getters/setters worden getest in de GetterEnSetterTest klasse
 */
public class SoortDocumentTest {

    private final SoortDocument soortDoc = new SoortDocument();

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
}
