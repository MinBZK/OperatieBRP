/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

import java.sql.Timestamp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class BerichtComparatorTest {

    private Bericht bericht1;
    private Bericht bericht2;

    private Timestamp timestamp1;
    private Timestamp timestamp2;

    public void setup() {
        bericht1 = new Bericht();
        bericht1.setId(1L);
        timestamp1 = new Timestamp(1L);
        bericht1.setTijdstip(timestamp1);
        bericht2 = new Bericht();
        bericht2.setId(2L);
        timestamp2 = new Timestamp(2L);
        bericht2.setTijdstip(timestamp2);
    }

    @Test
    public void testAscending() {
        setup();
        final BerichtComparator comparator = new BerichtComparator(true);
        Assert.assertEquals(1L, comparator.compare(bericht1, bericht2));
    }

    @Test
    public void testDescending() {
        setup();
        final BerichtComparator comparator = new BerichtComparator(false);
        Assert.assertEquals(-1L, comparator.compare(bericht1, bericht2));
    }

    @Test
    public void testAscendingZelfdeTijdstip() {
        bericht1 = new Bericht();
        bericht1.setId(1L);
        timestamp1 = new Timestamp(3L);
        timestamp1.setNanos(10);
        bericht1.setTijdstip(timestamp1);
        bericht2 = new Bericht();
        bericht2.setId(2L);
        timestamp2 = new Timestamp(3L);
        timestamp2.setNanos(10);
        bericht2.setTijdstip(timestamp2);
        timestamp1.setNanos(10);
        final BerichtComparator comparator = new BerichtComparator(true);
        Assert.assertEquals(1L, comparator.compare(bericht1, bericht2));
    }
}