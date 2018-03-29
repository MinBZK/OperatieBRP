/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004;

import java.lang.reflect.Constructor;
import org.junit.Assert;
import org.junit.Test;

/**
 * contructor test voor constanten.
 */
public class AdHocVragenConstantenTest {

    @Test
    public void constructor() throws Exception {
        Constructor<AdHocVragenConstanten> clazz = AdHocVragenConstanten.class.getDeclaredConstructor();
        clazz.setAccessible(true);
        AdHocVragenConstanten adHocVragenConstanten = clazz.newInstance();
        Assert.assertNotNull(adHocVragenConstanten);
    }
}