/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class VoaUtilTest {
    /**
     * Test the conversion method convertSpdTimeStringToDate(String) of the MailboxProxyServer class.
     * 
     */
    @Test
    public void testConvertSpdTime() {
        // Try to convert string 0408301059Z to Date
        final Calendar cal = Calendar.getInstance();
        cal.set(2004, // year
                7, // month
                30, // day
                10, // hour
                59 // minute
        );
        final Date expectedTime = cal.getTime();
        final Date convertedTime = VoaUtil.convertSpdTimeStringToDate("0408301059Z");
        Assert.assertEquals("expectedTime is ongelijk aan convertedTime", expectedTime.toString(),
                convertedTime.toString());
    }

    @Test
    public void testConvertSpdTimeNa1950Voor2000() {
        // Try to convert string 8008301059Z to Date
        final Calendar cal = Calendar.getInstance();
        cal.set(1980, // year
                7, // month
                30, // day
                10, // hour
                59 // minute
        );
        final Date expectedTime = cal.getTime();
        final Date convertedTime = VoaUtil.convertSpdTimeStringToDate("8008301059Z");
        Assert.assertEquals("expectedTime is ongelijk aan convertedTime", expectedTime.toString(),
                convertedTime.toString());
    }

    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        // get the constructor that takes no parameters
        final Constructor<?> constructor = VoaUtil.class.getDeclaredConstructor((Class<?>[]) null);

        // the modifiers int can tell us metadata about the constructor
        final int constructorModifiers = constructor.getModifiers();

        // but we're only interested in knowing that it's private
        Assert.assertTrue(Modifier.isPrivate(constructorModifiers));

        // constructor is private so we first have to make it accessible
        constructor.setAccessible(true);

        // now construct an instance
        constructor.newInstance((Object[]) null);
    }
}
