/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;


import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Postcode;
import org.junit.Test;

/**
 * Test klasse voor de AbstractAttribuutType en alle afgeleide daarvan.
 */
public class GenericAttibuutTypeTest {

    @Test
    public void testBsnNotNull() {
        Burgerservicenummer bsn = new Burgerservicenummer("132");
        Assert.assertFalse("null: Zou ongelijk moeten zijn", bsn.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", bsn.equals(new Integer(132)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", bsn.equals(new String("132")));
        Assert.assertFalse("Postcode: Zou ongelijk moeten zijn", bsn.equals(new Postcode("132")));


        Assert.assertFalse("null bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer(null)));
        Assert.assertTrue("exacte bsn: Zou gelijk moeten zijn", bsn.equals(new Burgerservicenummer("132")));
    }

    @Test
    public void testBsnNull() {
        Burgerservicenummer bsn = new Burgerservicenummer(null);
        Assert.assertFalse("null: Zou ongelijk moeten zijn", bsn.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", bsn.equals(new Integer(132)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", bsn.equals(new String("132")));
        Assert.assertFalse("Postcode: Zou ongelijk moeten zijn", bsn.equals(new Postcode("132")));

        Assert.assertTrue("null bsn: Zou gelijk moeten zijn", bsn.equals(new Burgerservicenummer(null)));
        Assert.assertFalse("exacte bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer("132")));
    }

    @Test
    public void testBsnSoortNull() {
        Burgerservicenummer bsn = new Burgerservicenummer("132", SoortNull.NIET_GEAUTORISEERD);
        Assert.assertFalse("null: Zou ongelijk moeten zijn", bsn.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", bsn.equals(new Integer(132)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", bsn.equals(new String("132")));
        Assert.assertFalse("Postcode: Zou ongelijk moeten zijn", bsn.equals(new Postcode("132")));

        Assert.assertFalse("null bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer(null)));
        Assert.assertFalse("123 bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer("132")));
        Assert.assertFalse("123, geenwaarde bsn: Zou ongelijk moeten zijn",
                bsn.equals(new Burgerservicenummer("132", SoortNull.GEEN_WAARDE)));
        Assert.assertTrue("123, niet auth bsn: Zou gelijk moeten zijn",
                bsn.equals(new Burgerservicenummer("132", SoortNull.NIET_GEAUTORISEERD)));
    }

    @Test
    public void testBsnBoolean() {
        Burgerservicenummer bsn = new Burgerservicenummer("132", SoortNull.NIET_GEAUTORISEERD);
        Assert.assertFalse("null: Zou ongelijk moeten zijn", bsn.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", bsn.equals(new Integer(132)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", bsn.equals(new String("132")));
        Assert.assertFalse("Postcode: Zou ongelijk moeten zijn", bsn.equals(new Postcode("132")));

        Assert.assertFalse("null bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer(null)));
        Assert.assertFalse("123 bsn: Zou ongelijk moeten zijn", bsn.equals(new Burgerservicenummer("132")));
        Assert.assertFalse("123, geenwaarde bsn: Zou ongelijk moeten zijn",
                bsn.equals(new Burgerservicenummer("132", SoortNull.GEEN_WAARDE)));
        Assert.assertTrue("123, niet auth bsn: Zou gelijk moeten zijn",
                bsn.equals(new Burgerservicenummer("132", SoortNull.NIET_GEAUTORISEERD)));
    }
}
