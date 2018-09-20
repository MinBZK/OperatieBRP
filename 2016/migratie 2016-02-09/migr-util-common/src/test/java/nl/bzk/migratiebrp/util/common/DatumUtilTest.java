/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;

public class DatumUtilTest {
    private Date today = new Date();
    private Calendar gisteren = Calendar.getInstance();
    private Calendar morgen = Calendar.getInstance();
    private int datum_gisteren;
    private int datum_morgen;

    private FastDateFormat format = FastDateFormat.getInstance("yyyyMMdd");

    @Before
    public void setup() {
        gisteren.add(Calendar.DATE, -1);
        datum_gisteren = Integer.parseInt(format.format(gisteren.getTime()));
        morgen.add(Calendar.DATE, 1);
        datum_morgen = Integer.parseInt(format.format(morgen.getTime()));
    }

    @Test
    public void testVandaag() throws Exception {
        assertEquals(DatumUtil.vandaag().intValue(), Integer.parseInt(format.format(today)));
    }

    @Test
    public void testGisteren() throws Exception {
        int gisteren = DatumUtil.gisteren();
        assertEquals(datum_gisteren, gisteren);
    }

    @Test
    public void testMorgen() throws Exception {
        int morgen = DatumUtil.morgen();
        assertEquals(datum_morgen, morgen);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            final Constructor<?>[] constructors = DatumUtil.class.getDeclaredConstructors();
            constructors[0].setAccessible(true);
            constructors[0].newInstance((Object[]) null);
            Assert.fail();
        }catch(InvocationTargetException e){
            Assert.assertTrue(e.getCause() instanceof UnsupportedOperationException);
            throw e.getCause();
        }
    }
}