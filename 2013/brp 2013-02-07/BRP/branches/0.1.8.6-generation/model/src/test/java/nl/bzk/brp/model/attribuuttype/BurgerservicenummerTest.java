/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import junit.framework.Assert;
import nl.bzk.brp.model.basis.SoortNull;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test class voor de {@link nl.bzk.brp.model.attribuuttype.Burgerservicenummer} class, waarbij specifiek de
 * constructors worden getest en de {@link nl.bzk.brp.model.attribuuttype.Burgerservicenummer#equals(Object)} en
 * {@link nl.bzk.brp.model.attribuuttype.Burgerservicenummer#hashCode()} methodes.
 */
@Ignore
public class BurgerservicenummerTest {

    @Test
    public void testStandaardConstructor() {
        Burgerservicenummer bsn = new Burgerservicenummer("123456789");
        Assert.assertFalse((bsn.isInOnderzoek()));
        Assert.assertNull(bsn.getNullWaarde());
        Assert.assertEquals(new Integer(123456789), bsn.getWaarde());
    }

    @Test
    public void testStandaardConstructorMetEigenlijkeOngeldigeBsnWaarde() {
        Burgerservicenummer bsn = new Burgerservicenummer("12345678");
        Assert.assertEquals("12345678", bsn.getWaarde());

        bsn = new Burgerservicenummer("1");
        Assert.assertEquals("1", bsn.getWaarde());
        bsn = new Burgerservicenummer("A");
        Assert.assertEquals("A", bsn.getWaarde());
    }

    @Test
    public void testMetNullWaardeVoorBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer(null);
        Assert.assertNull(bsn.getWaarde());
        Assert.assertFalse(bsn.isInOnderzoek());
        Assert.assertNull(bsn.getNullWaarde());
        Assert.assertNull(bsn.getWaarde());
    }

    @Test
    public void testConstructorMetOverigeParameters() {
        Burgerservicenummer bsn = new Burgerservicenummer(Integer.valueOf("123456789"), true, SoortNull.NIET_ONDERSTEUND);
        Assert.assertEquals("123456789", bsn.getWaarde());
        Assert.assertTrue(bsn.isInOnderzoek());
        Assert.assertEquals(SoortNull.NIET_ONDERSTEUND, bsn.getNullWaarde());
    }

   /* @Test
    public void testEqualsVanBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer("12345678", false, SoortNull.NIET_GEAUTORISEERD);
        Assert.assertFalse(bsn.equals(null));
        Assert.assertFalse(bsn.equals(new AbstractGegevensAttribuutType<String>("012345678") {
        }));
        Assert.assertFalse(bsn.equals(new Burgerservicenummer("23456789", false, SoortNull.NIET_GEAUTORISEERD)));

        Assert.assertFalse(bsn.equals(new Burgerservicenummer("012345678", false, SoortNull.NIET_GEAUTORISEERD)));
        Assert.assertTrue(bsn.equals(new Burgerservicenummer("12345678", true, SoortNull.NIET_ONDERSTEUND)));
        Assert.assertTrue(bsn.equals(bsn));
    }*/

   /* @Test
    public void testHashcodeVanBurgerservicenummer() {
        Burgerservicenummer bsn = new Burgerservicenummer("12345678", false, SoortNull.NIET_GEAUTORISEERD);
        Assert.assertFalse(bsn.hashCode()
            == (new AbstractGegevensAttribuutType<String>("012345678") { }).hashCode());
        Assert.assertFalse(bsn.hashCode()
            == (new Burgerservicenummer("23456789", false, SoortNull.NIET_GEAUTORISEERD)).hashCode());
        Assert.assertFalse(bsn.hashCode()
            == (new Burgerservicenummer("012345678", false, SoortNull.NIET_GEAUTORISEERD)).hashCode());
        Assert.assertEquals(bsn.hashCode(),
            (new Burgerservicenummer("12345678", true, SoortNull.NIET_ONDERSTEUND)).hashCode());
        Assert.assertEquals(bsn.hashCode(), bsn.hashCode());
    }*/

    /*@Test
    public void testWaardeAlsInteger() {
        Burgerservicenummer bsn = new Burgerservicenummer("123456789");
        Assert.assertEquals(123456789, bsn.getWaarde().intValue());

        bsn.setWaardeAlsInteger(23456789);
        Assert.assertEquals(23456789, bsn.getWaarde.intValue());
        Assert.assertEquals("023456789", bsn.getWaarde());
    }

    @Test(expected = NumberFormatException.class)
    public void testNietNumeriekeBsnMetGetWaardeAlsInteger() {
        Burgerservicenummer bsn = new Burgerservicenummer("A");
        bsn.getWaarde();
    }

    @Test
    public void testGetWaardeAlsIntegerMetVoorloopNul() {
        Burgerservicenummer bsn = new Burgerservicenummer("012345678");
        Assert.assertEquals(12345678, bsn.getWaardeAlsInteger().intValue());
    }*/
}
