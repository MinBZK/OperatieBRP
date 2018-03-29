/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * BsnValidatieTest.
 */
public class BsnValidatorTest {

    @Test
    public void bsnOngeldigeLengteVeelvoud11() {
        Assert.assertFalse(BsnValidator.isGeldigeBsn("135795"));
    }

    @Test
    public void bsnGeldigeLengteGeenVeelvoud11() {
        Assert.assertFalse(BsnValidator.isGeldigeBsn("672510329"));
    }

    @Test
    public void bsnGeldigeLengteVeelvoud11() {
        Assert.assertTrue(BsnValidator.isGeldigeBsn("736030281"));
    }

    @Test
    public void bsnGeenNumeriekeWaarde() {
        Assert.assertFalse(BsnValidator.isGeldigeBsn("abcdefgh"));
    }


}