/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class VerwerkingssoortComparatorTest {

    @Test
    public void testKleiner() {

        Assert.assertEquals(-1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.IDENTIFICATIE, Verwerkingssoort.REFERENTIE));
        Assert.assertEquals(-1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.REFERENTIE, Verwerkingssoort.TOEVOEGING));
        Assert.assertEquals(-1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.TOEVOEGING, Verwerkingssoort.WIJZIGING));
        Assert.assertEquals(-1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.WIJZIGING, Verwerkingssoort.VERVAL));
        Assert.assertEquals(-1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.VERVAL, Verwerkingssoort.VERWIJDERING));
    }

    @Test
    public void testGroter() {
        Assert.assertEquals(1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.REFERENTIE, Verwerkingssoort.IDENTIFICATIE));
        Assert.assertEquals(1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.TOEVOEGING, Verwerkingssoort.REFERENTIE));
        Assert.assertEquals(1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.WIJZIGING, Verwerkingssoort.TOEVOEGING));
        Assert.assertEquals(1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.VERVAL, Verwerkingssoort.WIJZIGING));
        Assert.assertEquals(1, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.VERWIJDERING, Verwerkingssoort.VERVAL));
    }

    @Test
    public void testGelijk() {
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.IDENTIFICATIE, Verwerkingssoort.IDENTIFICATIE));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.REFERENTIE, Verwerkingssoort.REFERENTIE));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.TOEVOEGING, Verwerkingssoort.TOEVOEGING));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.WIJZIGING, Verwerkingssoort.WIJZIGING));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.VERVAL, Verwerkingssoort.VERVAL));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.VERWIJDERING, Verwerkingssoort.VERWIJDERING));
    }

    @Test
    public void testNullWaarden() {
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(null, Verwerkingssoort.IDENTIFICATIE));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(Verwerkingssoort.IDENTIFICATIE, null));
        Assert.assertEquals(0, VerwerkingssoortComparator.INSTANCE.compare(null, null));
    }
}
