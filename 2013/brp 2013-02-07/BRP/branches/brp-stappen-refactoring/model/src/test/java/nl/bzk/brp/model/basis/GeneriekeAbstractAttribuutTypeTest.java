/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import org.junit.Test;

/**
 * Test klasse voor de {@link AbstractAttribuutType} en alle afgeleide daarvan. Dit wordt gedaan middels de
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer} klasse, die eerder genoemde abstracte klasse
 * implementeert.
 */
public class GeneriekeAbstractAttribuutTypeTest {

    @Test
    public void testEquals() {
        ANummer anummer = new ANummer("12345");

        Assert.assertFalse(anummer.equals(null));
        Assert.assertFalse(anummer.equals(new ANummer("123456")));
        Assert.assertFalse(anummer.equals(new Burgerservicenummer("12345")));

        Assert.assertTrue(anummer.equals(anummer));
        Assert.assertTrue(anummer.equals(new ANummer("12345")));
    }

    @Test
    public void testCompare() {
        ANummer anummer = new ANummer("12345");

        Assert.assertEquals(0, anummer.compareTo(anummer));
        Assert.assertEquals(0, anummer.compareTo(new ANummer("12345")));
        Assert.assertEquals(-1, anummer.compareTo(new ANummer("12346")));
        Assert.assertEquals(1, anummer.compareTo(new ANummer("1234")));
        Assert.assertEquals(-1, anummer.compareTo(null));
    }

}
