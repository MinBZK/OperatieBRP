/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import junit.framework.Assert;

import org.junit.Test;

public class VerdragTest {
    private static final String OMSCHRIJVING = "omschrijving";
    private final Verdrag verdrag = new Verdrag();
    private final Verdrag verdrag2 = new Verdrag();

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(verdrag.isInhoudelijkGelijkAan(verdrag2));

        verdrag2.setOmschrijving(OMSCHRIJVING);
        verdrag.setOmschrijving(OMSCHRIJVING);

        Assert.assertTrue(verdrag.isInhoudelijkGelijkAan(verdrag2));
        Assert.assertTrue(verdrag.isInhoudelijkGelijkAan(verdrag));
    }

    @Test
    public void isInhoudelijkGelijkNull() {
        Assert.assertFalse(verdrag.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkOmschrijving() {
        verdrag2.setOmschrijving(OMSCHRIJVING);
        Assert.assertFalse(verdrag.isInhoudelijkGelijkAan(verdrag2));

        verdrag.setOmschrijving(OMSCHRIJVING);
        verdrag2.setOmschrijving(null);
        Assert.assertFalse(verdrag.isInhoudelijkGelijkAan(verdrag2));
    }
}
