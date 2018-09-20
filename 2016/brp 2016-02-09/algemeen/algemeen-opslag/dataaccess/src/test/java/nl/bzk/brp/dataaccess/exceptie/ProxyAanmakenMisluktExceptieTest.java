/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.dataaccess.exceptie.ProxyAanmakenMisluktExceptie} class.
 */
public class ProxyAanmakenMisluktExceptieTest {

    public static final String TEST = "Test";

    @Test
    public void testProxyAanmakenMisluktExceptieConstructorMetBericht() {
        final ProxyAanmakenMisluktExceptie exceptie = new ProxyAanmakenMisluktExceptie(TEST, null);
        Assert.assertEquals(TEST, exceptie.getMessage());
        Assert.assertNull(exceptie.getCause());
    }

}
