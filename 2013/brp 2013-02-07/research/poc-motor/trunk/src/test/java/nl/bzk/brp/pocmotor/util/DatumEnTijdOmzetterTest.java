/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.util;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link DatumEnTijdUtil} class.
 */
public class DatumEnTijdOmzetterTest {
    
    @Test
    public void testStringNaarTijdstip() {
        String waarde = "2012-02-02T10:04:23";
        Calendar resultaat = Calendar.getInstance();
        resultaat.setTime(DatumEnTijdUtil.zetDatumEnTijdOmNaarDate(waarde));

        Assert.assertEquals(2012, resultaat.get(Calendar.YEAR));
        Assert.assertEquals(1, resultaat.get(Calendar.MONTH));
        Assert.assertEquals(2, resultaat.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(10, resultaat.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(4, resultaat.get(Calendar.MINUTE));
        Assert.assertEquals(23, resultaat.get(Calendar.SECOND));
    }

    @Test
    public void testStringNaarTijdstipMetNull() {
        Assert.assertNull(DatumEnTijdUtil.zetDatumEnTijdOmNaarDate(null));
    }

    @Test
    public void testTijdstipNaarString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2012);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 4);
        calendar.set(Calendar.SECOND, 23);
        
        Assert.assertEquals("2012-02-02T10:04:23", DatumEnTijdUtil.zetDateOmNaarDatumEnTijd(calendar.getTime()));
    }

    @Test
    public void testTijdstipNaarStringMetNull() {
        Assert.assertEquals("", DatumEnTijdUtil.zetDateOmNaarDatumEnTijd(null));
    }

}
