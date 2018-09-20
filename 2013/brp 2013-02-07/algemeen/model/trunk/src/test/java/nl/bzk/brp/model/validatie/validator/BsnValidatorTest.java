/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link BsnValidator} class. */
public class BsnValidatorTest {

    private final BsnValidator validator = new BsnValidator();

    @Test
    public void testGeldigeBsnValidatie() {
        Assert.assertTrue(validator.isValid(null, null));
        Assert.assertTrue(validator.isValid(new Burgerservicenummer(111222333), null));
        Assert.assertTrue(validator.isValid(new Burgerservicenummer(123456782), null));
        Assert.assertTrue(validator.isValid(new Burgerservicenummer(882134061), null));
        Assert.assertTrue(validator.isValid(new Burgerservicenummer(Integer.parseInt("089444917")), null));
        Assert.assertTrue(validator.isValid(new Burgerservicenummer(89444917), null));
    }

    @Test
    public void testOnGeldigeBsnValidatie() {
        Assert.assertFalse(validator.isValid(new Burgerservicenummer((Integer) null), null));
        Assert.assertFalse(validator.isValid(new Burgerservicenummer(1), null));
        Assert.assertFalse(validator.isValid(new Burgerservicenummer(11122233), null));
    }

    /**
     * Gooit exceptie omdat de constructor van {@link Burgerservicenummer} geen "A" accepteert.
     */
    @Test(expected = NumberFormatException.class)
    public void testIllegaleBsnWaarde() {
        Assert.assertFalse(validator.isValid(new Burgerservicenummer(Integer.parseInt("A")), null));
    }
}
