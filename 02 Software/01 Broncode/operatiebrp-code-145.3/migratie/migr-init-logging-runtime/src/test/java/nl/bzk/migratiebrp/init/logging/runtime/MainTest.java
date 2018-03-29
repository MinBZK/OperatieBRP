/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime;

import java.lang.reflect.Constructor;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    @Test
    public void test() throws ReflectiveOperationException {
        Main.stop();
        final Constructor constructor = Main.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Assert.assertNotNull(constructor.newInstance());
    }
}
