/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501;

import java.lang.reflect.Constructor;
import org.junit.Assert;
import org.junit.Test;

/**
 * contructor test voor constanten.
 */
public class VrijBerichtConstantenTest {

    @Test
    public void constructor() throws Exception {
        Constructor<VrijBerichtConstanten> clazz = VrijBerichtConstanten.class.getDeclaredConstructor();
        clazz.setAccessible(true);
        VrijBerichtConstanten vrijBerichtConstanten = clazz.newInstance();
        Assert.assertNotNull(vrijBerichtConstanten);
    }
}