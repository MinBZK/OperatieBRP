/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import junit.framework.Assert;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import org.junit.Test;

/**
 * Unit test class voor de {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer} class, waarbij
 * specifiek de constructors worden getest en de {@link nl.bzk.brp.model.algemeen.attribuuttype.kern
 * .Burgerservicenummer#equals(Object)} en
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer#hashCode()} methodes.
 */
public class BurgerservicenummerTest {

    @Test
    public void testStandaardConstructor() {
        Burgerservicenummer bsn = new Burgerservicenummer("123456789");
        Assert.assertEquals("123456789", bsn.toString());
        Assert.assertFalse((bsn.isInOnderzoek()));
        Assert.assertNull(bsn.getNullWaarde());
    }

    @Test
    public void testStandaardConstructorMetEigenlijkeOngeldigeBsnWaarde() {
        Burgerservicenummer bsn = new Burgerservicenummer("123456789");
        Assert.assertEquals("123456789", bsn.toString());

        bsn = new Burgerservicenummer("12345678");
        Assert.assertEquals("012345678", bsn.toString());
        bsn = new Burgerservicenummer("1");
        Assert.assertEquals("000000001", bsn.toString());
    }

    @Test
    public void testMetNullWaardeVoorBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer(null);
        Assert.assertNull(bsn.getWaarde());
        Assert.assertFalse(bsn.isInOnderzoek());
        Assert.assertNull(bsn.getNullWaarde());
    }

    @Test
    public void testEqualsVanBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer("12345678");
        Assert.assertFalse(bsn.equals(null));
        Assert.assertTrue(bsn.equals(new Burgerservicenummer("012345678")));
        Assert.assertFalse(bsn.equals(new Burgerservicenummer("23456789")));
        Assert.assertTrue(bsn.equals(new Burgerservicenummer("12345678")));
        Assert.assertTrue(bsn.equals(bsn));
    }

    @Test
    public void testHashcodeVanBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer("12345678");
        Assert.assertFalse(bsn.hashCode() == (new AbstractGegevensAttribuutType<String>("012345678") {
        }).hashCode());
        Assert.assertFalse(bsn.hashCode() == (new Burgerservicenummer("23456789")).hashCode());
        Assert.assertTrue(bsn.hashCode() == (new Burgerservicenummer("012345678")).hashCode());
        Assert.assertEquals(bsn.hashCode(), (new Burgerservicenummer("12345678")).hashCode());
        Assert.assertEquals(bsn.hashCode(), bsn.hashCode());
    }

}
