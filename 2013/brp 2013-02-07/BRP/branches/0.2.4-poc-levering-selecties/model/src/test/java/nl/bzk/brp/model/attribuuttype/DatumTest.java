/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link Datum} class.
 */
public class DatumTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNaMetNullDatum() {
        Datum mijnDatum = new Datum(20120325);
        mijnDatum.na(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaMetNullWaardeInDatum() {
        Datum mijnDatum = new Datum(20120325);
        mijnDatum.na(new Datum((Integer)null));
    }

    @Test
    public void testNa() {
        Datum mijnDatum = new Datum(20120325);

        Assert.assertTrue(mijnDatum.na(new Datum(20120324)));
        Assert.assertFalse(mijnDatum.na(new Datum(20120325)));
        Assert.assertFalse(mijnDatum.na(new Datum(20120326)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoorMetNullDatum() {
        Datum mijnDatum = new Datum(20120325);
        mijnDatum.voor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoorMetNullWaardeInDatum() {
        Datum mijnDatum = new Datum(20120325);
        mijnDatum.voor(new Datum((Integer)null));
    }

    @Test
    public void testVoor() {
        Datum mijnDatum = new Datum(20120325);

        Assert.assertFalse(mijnDatum.voor(new Datum(20120324)));
        Assert.assertFalse(mijnDatum.voor(new Datum(20120325)));
        Assert.assertTrue(mijnDatum.voor(new Datum(20120326)));
    }
}
