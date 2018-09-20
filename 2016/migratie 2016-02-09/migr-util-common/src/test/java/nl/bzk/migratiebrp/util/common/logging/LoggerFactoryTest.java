/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

import static org.junit.Assert.assertEquals;

import junit.framework.Assert;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Test het contract van de migratie LoggerFactory.
 * 
 */
public class LoggerFactoryTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            final Constructor<?>[] constructors = LoggerFactory.class.getDeclaredConstructors();
            constructors[0].setAccessible(true);
            constructors[0].newInstance((Object[]) null);
            Assert.fail();
        }catch(InvocationTargetException e){
            Assert.assertTrue(e.getCause() instanceof AssertionError);
            throw e.getCause();
        }
    }

    @Test
    public void testNewLogger() {
        final Logger logger = LoggerFactory.getLogger();
        assertEquals(this.getClass().getName(), logger.getName());
        LOG.info("LOG doet het");
    }

    @Test
    public void testNewBerichtVerkeerLogger() {
        final Logger logger = LoggerFactory.getBerichtVerkeerLogger();
        assertEquals("BerichtVerkeer." + this.getClass().getName(), logger.getName());
        logger.warn("VERKEER_LOG doet het");
    }
}
