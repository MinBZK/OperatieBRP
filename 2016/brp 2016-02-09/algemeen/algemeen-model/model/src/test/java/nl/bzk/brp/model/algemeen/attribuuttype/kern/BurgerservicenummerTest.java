/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import nl.bzk.brp.model.basis.AbstractAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer} class, waarbij
 * specifiek de constructors worden getest en de
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern .Burgerservicenummer#equals(Object)} en
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer#hashCode()} methodes.
 */
public class BurgerservicenummerTest {

    @Test
    public void testStandaardConstructor() {
        BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(123456789);
        Assert.assertEquals("123456789", bsn.toString());
        Assert.assertFalse((bsn.isInOnderzoek()));
    }

    @Test
    public void testStandaardConstructorMetEigenlijkeOngeldigeBsnWaarde() {
        BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(123456789);
        Assert.assertEquals("123456789", bsn.toString());

        bsn = new BurgerservicenummerAttribuut(12345678);
        Assert.assertEquals("012345678", bsn.toString());
        bsn = new BurgerservicenummerAttribuut(1);
        Assert.assertEquals("000000001", bsn.toString());
    }

    @Test
    public void testMetNullWaardeVoorBurgerservicenummer() {
        BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut((Integer) null);
        Assert.assertNull(bsn.getWaarde());
        Assert.assertFalse(bsn.isInOnderzoek());
    }

    @Test
    public void testEqualsVanBurgerservicenummer() {
        BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(12345678);
        Assert.assertNotEquals(bsn, null);
        Assert.assertTrue(bsn.equals(new BurgerservicenummerAttribuut(Integer.parseInt("012345678"))));
        Assert.assertFalse(bsn.equals(new BurgerservicenummerAttribuut(23456789)));
        Assert.assertTrue(bsn.equals(new BurgerservicenummerAttribuut(12345678)));
        Assert.assertTrue(bsn.equals(bsn));
    }

    @Test
    public void testHashcodeVanBurgerservicenummer() {
        BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(12345678);
        Assert.assertFalse(bsn.hashCode() == (new AbstractAttribuut<String>("012345678") {

        }).hashCode());
        Assert.assertFalse(bsn.hashCode() == (new BurgerservicenummerAttribuut(23456789)).hashCode());
        Assert.assertTrue(bsn.hashCode() == (new BurgerservicenummerAttribuut(Integer.parseInt("012345678")))
                .hashCode());
        Assert.assertEquals(bsn.hashCode(), (new BurgerservicenummerAttribuut(12345678)).hashCode());
        Assert.assertEquals(bsn.hashCode(), bsn.hashCode());
    }

}
