/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.*;

public class KopieerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            final Constructor<?>[] constructors = Kopieer.class.getDeclaredConstructors();
            constructors[0].setAccessible(true);
            constructors[0].newInstance((Object[]) null);
            Assert.fail();
        } catch (InvocationTargetException e) {
            Assert.assertTrue(e.getCause() instanceof UnsupportedOperationException);
            throw e.getCause();
        }
    }

    @Test
    public void testTimestamp() throws Exception {
        Calendar now = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(now.getTimeInMillis());
        Timestamp result = Kopieer.timestamp(timestamp);
        assertEquals(result.getTime(), timestamp.getTime());
        assertNotSame(result, timestamp);
        assertNull(Kopieer.timestamp(null));

    }

    @Test
    public void testSqlDate() throws Exception {
        Date now = new Date(Calendar.getInstance().getTimeInMillis());
        Date result = Kopieer.sqlDate(now);
        assertEquals(result.getTime(), now.getTime());
        assertNotSame(result, now);
        assertNull(Kopieer.sqlDate(null));


    }

    @Test
    public void testUtilDate() throws Exception {
        java.util.Date now = new java.util.Date(Calendar.getInstance().getTimeInMillis());
        java.util.Date result = Kopieer.utilDate(now);
        assertEquals(result.getTime(), now.getTime());
        assertNotSame(result, now);
        assertNull(Kopieer.utilDate(null));
    }
}
