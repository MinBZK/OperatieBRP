/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import org.junit.Assert;
import org.junit.Test;

/**

 *
 */
public class StapelTest {

    @Test(expected = NullPointerException.class)
    public void testNullpointer() {
        new Lo3Stapel<Lo3NationaliteitInhoud>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorien = new ArrayList<>();
        new Lo3Stapel<>(categorien);
    }

    @Test
    public void testNavigationElements() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5 = getLo3NationaliteitInhoudLo3Categorie("5");
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorien = new ArrayList<>();
        final Lo3Categorie<Lo3NationaliteitInhoud> cat1 = getLo3NationaliteitInhoudLo3Categorie("1");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat2 = getLo3NationaliteitInhoudLo3Categorie("2");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat3 = getLo3NationaliteitInhoudLo3Categorie("3");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat4 = getLo3NationaliteitInhoudLo3Categorie("4");
        categorien.add(cat1);
        categorien.add(cat2);
        categorien.add(cat3);
        categorien.add(cat4);
        final Lo3Stapel stapel = new Lo3Stapel<>(categorien);
        Lo3Categorie<Lo3NationaliteitInhoud> vorige = (Lo3Categorie<Lo3NationaliteitInhoud>) stapel.getVorigElement(cat3);
        Assert.assertNotNull(vorige);
        Assert.assertEquals("2", vorige.getInhoud().getNationaliteitCode().getWaarde());
        vorige = (Lo3Categorie<Lo3NationaliteitInhoud>) stapel.getVorigElement(cat5);
        Assert.assertNull(vorige);
        vorige = (Lo3Categorie<Lo3NationaliteitInhoud>) stapel.get(2);
        Assert.assertNotNull(vorige);
        Assert.assertEquals("3", vorige.getInhoud().getNationaliteitCode().getWaarde());
        vorige = (Lo3Categorie<Lo3NationaliteitInhoud>) stapel.getLaatsteElement();
        Assert.assertNotNull(vorige);
        Assert.assertEquals("4", vorige.getInhoud().getNationaliteitCode().getWaarde());
        Assert.assertFalse(stapel.isEmpty());
        Assert.assertEquals(4, stapel.size());
        final Iterator<Lo3Categorie<Lo3NationaliteitInhoud>> iter = stapel.iterator();
        int count = 0;
        while (iter.hasNext()) {
            count++;
            iter.next();
        }
        Assert.assertEquals(4, count);
    }

    @Test
    public void compareMethods() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5 = getLo3NationaliteitInhoudLo3Categorie("5");
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorien = new ArrayList<>();
        final Lo3Categorie<Lo3NationaliteitInhoud> cat1 = getLo3NationaliteitInhoudLo3Categorie("1");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat2 = getLo3NationaliteitInhoudLo3Categorie("2");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat3 = getLo3NationaliteitInhoudLo3Categorie("3");
        final Lo3Categorie<Lo3NationaliteitInhoud> cat4 = getLo3NationaliteitInhoudLo3Categorie("4");
        categorien.add(cat1);
        categorien.add(cat2);
        categorien.add(cat3);
        categorien.add(cat4);
        final Stapel stapel = new Stapel<>(categorien);
        final Stapel stapel2 = new Stapel<>(categorien);
        Assert.assertFalse(stapel.equals(null));
        Assert.assertFalse(stapel.equals("niet gelijk"));
        Assert.assertTrue(stapel.equals(stapel2));
        Assert.assertTrue(stapel.hashCode() == stapel2.hashCode());

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorien2 = new ArrayList<>();
        categorien2.addAll(categorien);
        categorien2.add(cat5);
        final Stapel stapel3 = new Stapel<>(categorien2);
        Assert.assertFalse(stapel.equals(stapel3));
        Assert.assertFalse(stapel.hashCode() == stapel3.hashCode());
        Assert.assertEquals(stapel.toString(), stapel2.toString());
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> getLo3NationaliteitInhoudLo3Categorie(final String natCode) {
        final Lo3NationaliteitCode code1 = new Lo3NationaliteitCode(natCode);
        final Lo3NationaliteitInhoud nat1 = new Lo3NationaliteitInhoud(code1, null, null, null, null);
        final Lo3Historie history = new Lo3Historie(null, null, null);
        return new Lo3Categorie<>(nat1, null, history, null);
    }
}
