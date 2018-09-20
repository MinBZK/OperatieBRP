/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie} class.
 */
public class ObjectReedsBestaandExceptieTest {

    private static final String WAARDE = "waarde";

    @Test
    public void testConstructorEnGettersEnSetters() {
        final ObjectReedsBestaandExceptie exceptie = new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, WAARDE, null);

        Assert.assertNotNull(exceptie);
        Assert.assertNotNull(exceptie.getMessage());
        Assert.assertEquals("BSN", exceptie.getReferentieVeldNaam());
        Assert.assertEquals(WAARDE, exceptie.getReferentieWaarde());
    }

    @Test
    public void testMetWaardeIsNull() {
        final ObjectReedsBestaandExceptie exceptie = new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, null, null);
        Assert.assertNull(exceptie.getReferentieWaarde());
    }

}
