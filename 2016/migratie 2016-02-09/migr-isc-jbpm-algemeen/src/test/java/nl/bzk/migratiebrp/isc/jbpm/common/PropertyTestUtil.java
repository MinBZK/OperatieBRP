/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.Assert;

public class PropertyTestUtil {

    public static <T> void testMutableProperty(final Object object, final String property, final T initialValue, final T checkValue)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        final Class<?> clazz = object.getClass();
        final String camelCase = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        final Method getter = clazz.getMethod("get" + camelCase, (Class[]) null);

        final Object value = getter.invoke(object, (Object[]) null);
        Assert.assertEquals(initialValue, value);

        final Method setter = clazz.getMethod("set" + camelCase, new Class[] {getter.getReturnType() });
        setter.invoke(object, checkValue);

        final Object check = getter.invoke(object, (Object[]) null);
        Assert.assertEquals(checkValue, check);
    }
}
