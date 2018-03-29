/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.validatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import org.junit.Assert;
import org.junit.Test;

public class PeriodeTest {

    @Test
    public void test() {
        Assert.assertEquals(new Periode(0L, 100L), new Periode(100L, 0L));

        Assert.assertFalse(new Periode(100L, 200L).heeftOverlap(new Periode(50L, 50L)));
        Assert.assertFalse(new Periode(50L, 50L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(100L, 100L)));
        Assert.assertTrue(new Periode(100L, 100L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(150L, 150L)));
        Assert.assertTrue(new Periode(150L, 150L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertFalse(new Periode(100L, 200L).heeftOverlap(new Periode(200L, 200L)));
        Assert.assertFalse(new Periode(200L, 200L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertFalse(new Periode(100L, 200L).heeftOverlap(new Periode(250L, 250L)));
        Assert.assertFalse(new Periode(250L, 250L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertFalse(new Periode(100L, 200L).heeftOverlap(new Periode(200L, 300L)));
        Assert.assertFalse(new Periode(200L, 300L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertFalse(new Periode(100L, 200L).heeftOverlap(new Periode(50L, 100L)));
        Assert.assertFalse(new Periode(50L, 100L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(50L, 150L)));
        Assert.assertTrue(new Periode(50L, 150L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(50L, 250L)));
        Assert.assertTrue(new Periode(50L, 250L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(125L, 175L)));
        Assert.assertTrue(new Periode(125L, 275L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(150L, 250L)));
        Assert.assertTrue(new Periode(150L, 250L).heeftOverlap(new Periode(100L, 200L)));

        Assert.assertTrue(new Periode(100L, 200L).heeftOverlap(new Periode(100L, 200L)));

    }

    @Test
    public void testCompare() {
        Periode periode_1 = new Periode(100L, 200L);
        Periode periode_2 = new Periode(100L, 200L);
        Periode periode_3 = new Periode(99L, 200L);
        Periode periode_4 = new Periode(101L, 200L);
        Periode periode_5 = new Periode(100L, 199L);
        Periode periode_6 = new Periode(100L, 201L);

        assertEquals(0, periode_1.compareTo(periode_2));
        assertEquals(1, periode_1.compareTo(periode_3));
        assertEquals(-1, periode_1.compareTo(periode_4));
        assertEquals(1, periode_1.compareTo(periode_5));
        assertEquals(-1, periode_1.compareTo(periode_6));

    }

    @Test
    public void testEquals() {
        Periode periode_1 = new Periode(100L, 200L);
        assertTrue(periode_1.equals(periode_1));
        assertFalse(periode_1.equals("Something else"));

    }

    @Test
    public void testToString() {
        Periode periode_1 = new Periode(100L, 200L);
        assertEquals("Periode[begin=100,einde=200]", periode_1.toString());
    }

    @Test
    public void testBrpDatum() {
        final BrpDatum begin1 = new BrpDatum(20130101, null);
        final BrpDatum einde1 = new BrpDatum(20130201, null);

        final BrpDatum begin2 = new BrpDatum(20130301, null);
        final BrpDatum einde2 = new BrpDatum(20130401, null);

        Assert.assertFalse(BrpDatum.createPeriode(begin1, einde1).heeftOverlap(BrpDatum.createPeriode(begin2, einde2)));
        Assert.assertTrue(BrpDatum.createPeriode(begin1, einde2).heeftOverlap(BrpDatum.createPeriode(begin1, einde2)));
        Assert.assertTrue(BrpDatum.createPeriode(null, einde2).heeftOverlap(BrpDatum.createPeriode(begin1, einde2)));
        Assert.assertTrue(BrpDatum.createPeriode(begin1, einde2).heeftOverlap(BrpDatum.createPeriode(begin1, null)));

    }

    @Test
    public void testPeriode() {
        Timestamp ts_5l = new Timestamp(5L);
        Timestamp ts_10l = new Timestamp(10L);
        final Periode periode = new Periode(ts_5l, ts_10l);
        assertEquals(5L, periode.getBegin());
        assertEquals(10L, periode.getEinde());
        assertEquals(5L, periode.lengte());
    }

    @Test
    public void testRemove1() {
        final Periode periode1a = new Periode(5L, 10L);
        final Periode periode1b = new Periode(5L, 10L);
        final Set<Periode> result = periode1a.remove(periode1b);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRemove2() {
        final Periode periode2a = new Periode(5L, 10L);
        final Periode periode2b = new Periode(5L, 8L);
        final Set<Periode> result = periode2a.remove(periode2b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(8L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove3() {
        final Periode periode3a = new Periode(5L, 10L);
        final Periode periode3b = new Periode(7L, 10L);
        final Set<Periode> result = periode3a.remove(periode3b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(7L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove4() {
        final Periode periode4a = new Periode(5L, 10L);
        final Periode periode4b = new Periode(7L, 8L);
        final Set<Periode> result = periode4a.remove(periode4b);
        assertEquals(2, result.size());
        final Iterator<Periode> resultIterator = result.iterator();
        final Periode resultPeriode1 = resultIterator.next();
        assertEquals(5L, resultPeriode1.getBegin());
        assertEquals(7L, resultPeriode1.getEinde());
        final Periode resultPeriode2 = resultIterator.next();
        assertEquals(8L, resultPeriode2.getBegin());
        assertEquals(10L, resultPeriode2.getEinde());
    }

    @Test
    public void testRemove5() {
        final Periode periode5a = new Periode(5L, 10L);
        final Periode periode5b = new Periode(4L, 11L);
        final Set<Periode> result = periode5a.remove(periode5b);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRemove6() {
        final Periode periode6a = new Periode(5L, 10L);
        final Periode periode6b = new Periode(3L, 8L);
        final Set<Periode> result = periode6a.remove(periode6b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(8L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove7() {
        final Periode periode7a = new Periode(5L, 10L);
        final Periode periode7b = new Periode(7L, 12L);
        final Set<Periode> result = periode7a.remove(periode7b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(7L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove8() {
        final Periode periode8a = new Periode(5L, 10L);
        final Periode periode8b = new Periode(7L, 7L);
        final Set<Periode> result = periode8a.remove(periode8b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove9() {
        final Periode periode9a = new Periode(5L, 10L);
        final Periode periode9b = new Periode(12L, 14L);
        final Set<Periode> result = periode9a.remove(periode9b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove10() {
        final Periode periode10a = new Periode(5L, 10L);
        final Periode periode10b = new Periode(2L, 4L);
        final Set<Periode> result = periode10a.remove(periode10b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove11() {
        final Periode periode11a = new Periode(5L, 10L);
        final Periode periode11b = new Periode(10L, 14L);
        final Set<Periode> result = periode11a.remove(periode11b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemove12() {
        final Periode periode12a = new Periode(5L, 10L);
        final Periode periode12b = new Periode(2L, 5L);
        final Set<Periode> result = periode12a.remove(periode12b);
        assertEquals(1, result.size());
        final Periode resultPeriode = result.iterator().next();
        assertEquals(5L, resultPeriode.getBegin());
        assertEquals(10L, resultPeriode.getEinde());
    }

    @Test
    public void testRemoveFromSet1() {
        final Set<Periode> testSet = new HashSet<>();
        testSet.add(new Periode(6L, 10L));
        testSet.add(new Periode(4L, 8L));
        testSet.add(new Periode(2L, 6L));
        final Periode anderePeriode = new Periode(3L, 9L);
        final Set<Periode> testResult = Periode.removeFromSet(testSet, anderePeriode);
        assertEquals(2, testResult.size());
        final List<Periode> testLijst = new ArrayList<>(testResult);
        Collections.sort(testLijst, new Comparator<Periode>() {
            @Override
            public int compare(final Periode o1, final Periode o2) {
                return Long.valueOf(o1.getBegin()).compareTo(o2.getBegin());
            }
        });
        final Iterator<Periode> resultIterator = testLijst.iterator();
        final Periode resultPeriode1 = resultIterator.next();
        assertEquals(2L, resultPeriode1.getBegin());
        assertEquals(3L, resultPeriode1.getEinde());
        final Periode resultPeriode2 = resultIterator.next();
        assertEquals(9L, resultPeriode2.getBegin());
        assertEquals(10L, resultPeriode2.getEinde());
    }

    @Test
    public void testRemoveFromSet2() {
        final Set<Periode> testSet = new HashSet<>();
        testSet.add(new Periode(8L, 14L));
        testSet.add(new Periode(4L, 12L));
        testSet.add(new Periode(2L, 8L));

        final Set<Periode> andereTestSet = new HashSet<>();
        andereTestSet.add(new Periode(4L, 6L));
        andereTestSet.add(new Periode(6L, 8L));
        andereTestSet.add(new Periode(10L, 12L));

        final List<Periode> testResult = new ArrayList<>(Periode.removeFromSet(testSet, andereTestSet));
        Collections.sort(testResult);

        assertEquals(3, testResult.size());
        final Iterator<Periode> resultIterator = testResult.iterator();
        final Periode resultPeriode1 = resultIterator.next();
        assertEquals(2L, resultPeriode1.getBegin());
        assertEquals(4L, resultPeriode1.getEinde());
        final Periode resultPeriode2 = resultIterator.next();
        assertEquals(8L, resultPeriode2.getBegin());
        assertEquals(10L, resultPeriode2.getEinde());
        final Periode resultPeriode3 = resultIterator.next();
        assertEquals(12L, resultPeriode3.getBegin());
        assertEquals(14L, resultPeriode3.getEinde());
    }

    @Test
    public void testRemoveFromSet3() {
        final Set<Periode> testSet = new HashSet<>();
        testSet.add(new Periode(8L, 14L));
        testSet.add(new Periode(4L, 12L));
        testSet.add(new Periode(2L, 8L));

        final Set<Periode> andereTestSet = new HashSet<>();
        andereTestSet.add(new Periode(2L, 6L));
        andereTestSet.add(new Periode(5L, 10L));
        andereTestSet.add(new Periode(10L, 12L));
        andereTestSet.add(new Periode(9L, 14L));

        final Set<Periode> testResult = Periode.removeFromSet(testSet, andereTestSet);
        assertTrue(testResult.isEmpty());
    }
}
