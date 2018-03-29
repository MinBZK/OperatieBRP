/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.migratiebrp.conversie.model.validatie.Periode;
import org.junit.Assert;
import org.junit.Test;

public class Lo3DatumTest {

    /**
     * test Onbekend
     */
    @Test
    public void testOnbekend() {
        Assert.assertTrue("Volledig onbekend", new Lo3Datum(Integer.parseInt("00000000")).isOnbekend());
        Assert.assertTrue("Jaar onbekend", new Lo3Datum(Integer.parseInt("00001122")).isOnbekend());
        Assert.assertTrue("Maand onbekend", new Lo3Datum(12340022).isOnbekend());
        Assert.assertTrue("Dag onbekend", new Lo3Datum(12341100).isOnbekend());
        Assert.assertFalse("Volledig bekend", new Lo3Datum(12341122).isOnbekend());
    }

    @Test
    public void testIsInhoudelijkGelijkOfLeeg() {
        Assert.assertTrue(Lo3Datum.equalsWaarde(null, null));

        final Lo3Datum datum1 = new Lo3Datum(20150101);
        final Lo3Datum datum2 = new Lo3Datum(20140101);
        final Lo3Datum datum1MetOnderzoek = new Lo3Datum("20150101", new Lo3Onderzoek());
        final Lo3Datum datum2MetOnderzoek = new Lo3Datum("20140101", new Lo3Onderzoek());
        final Lo3Datum geenDatum1MetOnderzoek = new Lo3Datum(null, new Lo3Onderzoek());
        final Lo3Datum geenDatum2MetOnderzoek = new Lo3Datum(null, new Lo3Onderzoek());

        Assert.assertTrue(Lo3Datum.equalsWaarde(null, null));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum1, datum1));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum2, datum2));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum1MetOnderzoek, datum1MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum2MetOnderzoek, datum2MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum1, datum1MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(datum2, datum2MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(geenDatum1MetOnderzoek, geenDatum1MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(geenDatum1MetOnderzoek, geenDatum2MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(geenDatum1MetOnderzoek, null));
        Assert.assertTrue(Lo3Datum.equalsWaarde(null, geenDatum2MetOnderzoek));
        Assert.assertTrue(Lo3Datum.equalsWaarde(geenDatum2MetOnderzoek, geenDatum2MetOnderzoek));

        Assert.assertFalse(Lo3Datum.equalsWaarde(datum1, datum2));
        Assert.assertFalse(Lo3Datum.equalsWaarde(datum2, datum1));
        Assert.assertFalse(Lo3Datum.equalsWaarde(datum1, null));
        Assert.assertFalse(Lo3Datum.equalsWaarde(null, datum2));
        Assert.assertFalse(Lo3Datum.equalsWaarde(datum1MetOnderzoek, datum2MetOnderzoek));
    }

    @Test(expected = NullPointerException.class)
    public void testConverteerWaardeNaarDatumNP() {
        Lo3Datum lo3 = new Lo3Datum(null, null);
        lo3.isOnbekend();

    }

    @Test
    public void testPeriode() {
        Lo3Datum lo3a = new Lo3Datum(10);
        Lo3Datum lo3b = new Lo3Datum(25);
        Periode periode = Lo3Datum.createPeriode(lo3a, lo3b);
        Assert.assertEquals(15, periode.lengte());
    }

    @Test
    public void testMaximaliseerDatum() {
        Lo3Datum lo3a = new Lo3Datum(10);
        int lo3b = lo3a.maximaliseerOnbekendeDatum();
        Assert.assertEquals(9999_99_10, lo3b);
        Lo3Datum lo3c = new Lo3Datum(22181313);
        int lo3d = lo3c.maximaliseerOnbekendeDatum();
        Assert.assertEquals(2218_13_13, lo3d);
    }

    @Test(expected = NullPointerException.class)
    public void testCompare() {
        Lo3Datum lo3a = new Lo3Datum(10);
        Lo3Datum lo3b = new Lo3Datum(null, null);
        lo3a.compareTo(lo3b);
    }

}
