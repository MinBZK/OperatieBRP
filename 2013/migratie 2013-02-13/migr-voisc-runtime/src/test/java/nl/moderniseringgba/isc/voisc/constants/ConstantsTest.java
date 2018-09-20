/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

public class ConstantsTest {

    @Test
    public void testPrivateConstructorMessageCodes() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        final Constructor<?> constructor = MessagesCodes.class.getDeclaredConstructor((Class<?>[]) null);
        final int constructorModifiers = constructor.getModifiers();
        Assert.assertTrue(Modifier.isPrivate(constructorModifiers));
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }

    @Test
    public void testPrivateConstructorVoiscConstants() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        final Constructor<?> constructor = VoiscConstants.class.getDeclaredConstructor((Class<?>[]) null);
        final int constructorModifiers = constructor.getModifiers();
        Assert.assertTrue(Modifier.isPrivate(constructorModifiers));
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }

    @Test
    public void testPrivateConstructorSpdConstants() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        final Constructor<?> constructor = SpdConstants.class.getDeclaredConstructor((Class<?>[]) null);
        final int constructorModifiers = constructor.getModifiers();
        Assert.assertTrue(Modifier.isPrivate(constructorModifiers));
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }
}
