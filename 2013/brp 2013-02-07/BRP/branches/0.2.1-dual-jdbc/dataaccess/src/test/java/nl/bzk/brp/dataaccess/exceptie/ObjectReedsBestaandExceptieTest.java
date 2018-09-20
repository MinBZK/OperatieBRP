/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie} class.
 */
public class ObjectReedsBestaandExceptieTest {

    @Test
    public void testConstructorEnGettersEnSetters() {
        ObjectReedsBestaandExceptie exceptie = new ObjectReedsBestaandExceptie(
            ObjectReedsBestaandExceptie.ReferentieVeld.BSN, "waarde", null);

        Assert.assertNotNull(exceptie);
        Assert.assertNotNull(exceptie.getMessage());
        Assert.assertEquals("BSN", exceptie.getReferentieVeldNaam());
        Assert.assertEquals("waarde", exceptie.getReferentieWaarde());
    }

}
