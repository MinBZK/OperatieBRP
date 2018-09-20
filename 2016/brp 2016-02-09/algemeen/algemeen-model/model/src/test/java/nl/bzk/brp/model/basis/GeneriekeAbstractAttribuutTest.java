/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test klasse voor de {@link AbstractAttribuutType} en alle afgeleide daarvan. Dit wordt gedaan middels de
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer} klasse, die eerder genoemde abstracte klasse
 * implementeert.
 */
public class GeneriekeAbstractAttribuutTest {

    @Test
    public void testEquals() {
        AdministratienummerAttribuut anummer = new AdministratienummerAttribuut(12345L);

        Assert.assertNotEquals(anummer, null);
        Assert.assertFalse(anummer.equals(new AdministratienummerAttribuut(123456L)));
        Assert.assertFalse(anummer.equals(new BurgerservicenummerAttribuut(12345)));

        Assert.assertTrue(anummer.equals(anummer));
        Assert.assertTrue(anummer.equals(new AdministratienummerAttribuut(12345L)));
    }

    @Test
    public void testCompare() {
        AdministratienummerAttribuut anummer = new AdministratienummerAttribuut(12345L);

        Assert.assertEquals(0, anummer.compareTo(anummer));
        Assert.assertEquals(0, anummer.compareTo(new AdministratienummerAttribuut(12345L)));
        Assert.assertEquals(-1, anummer.compareTo(new AdministratienummerAttribuut(12346L)));
        Assert.assertEquals(1, anummer.compareTo(new AdministratienummerAttribuut(1234L)));
        Assert.assertEquals(-1, anummer.compareTo(null));
    }

}
