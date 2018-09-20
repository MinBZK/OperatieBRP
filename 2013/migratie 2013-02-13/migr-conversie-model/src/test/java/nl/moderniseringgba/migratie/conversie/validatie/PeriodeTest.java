/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.validatie;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

import org.junit.Assert;
import org.junit.Test;

public class PeriodeTest {

    @Test
    public void test() {
        Assert.assertEquals(new Periode(0, 100), new Periode(100, 0));

        Assert.assertFalse(new Periode(100, 200).heeftOverlap(new Periode(50, 50)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(100, 100)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(150, 150)));
        Assert.assertFalse(new Periode(100, 200).heeftOverlap(new Periode(200, 200)));
        Assert.assertFalse(new Periode(100, 200).heeftOverlap(new Periode(250, 250)));

        Assert.assertFalse(new Periode(100, 200).heeftOverlap(new Periode(200, 300)));
        Assert.assertFalse(new Periode(100, 200).heeftOverlap(new Periode(50, 100)));

        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(50, 150)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(50, 250)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(125, 175)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(150, 250)));
        Assert.assertTrue(new Periode(100, 200).heeftOverlap(new Periode(100, 200)));
    }

    @Test
    public void testBrpDatum() {
        final BrpDatum begin1 = new BrpDatum(20130101);
        final BrpDatum einde1 = new BrpDatum(20130201);

        final BrpDatum begin2 = new BrpDatum(20130301);
        final BrpDatum einde2 = new BrpDatum(20130401);

        Assert.assertFalse(new Periode(begin1, einde1).heeftOverlap(new Periode(begin2, einde2)));
        Assert.assertTrue(new Periode(begin1, einde2).heeftOverlap(new Periode(begin1, einde2)));
        Assert.assertTrue(new Periode(null, einde2).heeftOverlap(new Periode(begin1, einde2)));
        Assert.assertTrue(new Periode(begin1, einde2).heeftOverlap(new Periode(begin1, null)));

    }

}
