/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link DatumAttribuut} class.
 */
public class DatumAttribuutTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNaMetNullDatum() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);
        mijnDatum.na(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaMetNullWaardeInDatum() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);
        mijnDatum.na(new DatumEvtDeelsOnbekendAttribuut((Integer) null));
    }

    @Test
    public void testNa() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);

        Assert.assertTrue(mijnDatum.na(new DatumEvtDeelsOnbekendAttribuut(20120324)));
        Assert.assertFalse(mijnDatum.na(new DatumEvtDeelsOnbekendAttribuut(20120325)));
        Assert.assertFalse(mijnDatum.na(new DatumEvtDeelsOnbekendAttribuut(20120326)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoorMetNullDatum() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);
        mijnDatum.voor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoorMetNullWaardeInDatum() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);
        mijnDatum.voor(new DatumEvtDeelsOnbekendAttribuut((Integer) null));
    }

    @Test
    public void testVoor() {
        final DatumEvtDeelsOnbekendAttribuut mijnDatum = new DatumEvtDeelsOnbekendAttribuut(20120325);

        Assert.assertFalse(mijnDatum.voor(new DatumEvtDeelsOnbekendAttribuut(20120324)));
        Assert.assertFalse(mijnDatum.voor(new DatumEvtDeelsOnbekendAttribuut(20120325)));
        Assert.assertTrue(mijnDatum.voor(new DatumEvtDeelsOnbekendAttribuut(20120326)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerschilMetNullWaardeInDatum() {
        final DatumAttribuut mijnDatum = new DatumAttribuut(20120325);
        mijnDatum.aantalDagenVerschil(new DatumAttribuut((Integer) null));
    }

    @Test
    public void testVerschil() {
        final DatumAttribuut mijnDatum = new DatumAttribuut(20120101);

        Assert.assertEquals(0, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20120101)));
        Assert.assertEquals(1, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20120102)));
        Assert.assertEquals(5, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20120106)));
        Assert.assertEquals(31, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20120201)));
        Assert.assertEquals(365, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20110101)));
        Assert.assertEquals(366, mijnDatum.aantalDagenVerschil(new DatumAttribuut(20130101)));
    }

    @Test
    public void testToString() {
        Assert.assertEquals("2011-03-09", new DatumEvtDeelsOnbekendAttribuut(20110309).toString());
        Assert.assertEquals("", new DatumEvtDeelsOnbekendAttribuut((Integer) null).toString());
    }

    @Test
    public void testIsDatumVolledigDatum() {
        assertTrue(new DatumAttribuut(19920407).isVolledigDatumWaarde());
    }

    @Test
    public void testIsGeldigOp() {
        assertTrue(new DatumAttribuut(20121003).isGeldigTussen(null, null));
        assertTrue(new DatumAttribuut(20121003).isGeldigTussen(new DatumEvtDeelsOnbekendAttribuut(0), null));
        assertFalse(new DatumAttribuut(20121003).isGeldigTussen(new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumAttribuut(20121003).isGeldigTussen(new DatumAttribuut(20121003), null));
        assertFalse(new DatumAttribuut(20121003).isGeldigTussen(new DatumAttribuut(20121004), null));
        assertFalse(new DatumAttribuut(20121003).isGeldigTussen(new DatumAttribuut(20121003), new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumAttribuut(20121003).isGeldigTussen(new DatumAttribuut(20121003), new DatumAttribuut(20121004)));
        assertFalse(new DatumAttribuut(20121003).isGeldigTussen(new DatumAttribuut(20121003), new DatumAttribuut(20121003)));
    }

    @Test
    public void testDatumToDate() {

        final Calendar datum = new GregorianCalendar();
        datum.clear();
        datum.set(Calendar.YEAR, 2006);
        datum.set(Calendar.MONTH, Calendar.MARCH);
        datum.set(Calendar.DAY_OF_MONTH, 25);

        assertEquals(datum.getTime(), new DatumAttribuut(20060325).toDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatumToDateMetOngeldigArgument() {
        new DatumAttribuut(0).toDate();
    }
}
