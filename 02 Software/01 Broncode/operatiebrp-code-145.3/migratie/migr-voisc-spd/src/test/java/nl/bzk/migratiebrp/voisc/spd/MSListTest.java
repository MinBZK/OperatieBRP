/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import java.time.Instant;
import org.junit.Test;

public class MSListTest {
    private MSList subject() {
        return new MSList.Builder().addEntry()
                .msSequenceNumber(123456789)
                .msStatus(SpdConstants.MSStatus.NEW)
                .priority(SpdConstants.Priority.NORMAL)
                .deliveryTime(Instant.parse("2016-11-03T15:10:30Z"))
                .originatorOrName("origina")
                .build()
                .addEntry()
                .msSequenceNumber(234567890)
                .msStatus(SpdConstants.MSStatus.PROCESSED)
                .priority(SpdConstants.Priority.URGENT)
                .deliveryTime(Instant.parse("2016-12-03T15:10:30Z"))
                .originatorOrName("usernam")
                .build()
                .build();
    }

    @Test
    public void fromOperationItems() {
        final MSList record = MSList.fromOperationItems("00002123456789001611031510Zorigina234567890221612031510Zusernam");
        assertThat("should have correct type", record, instanceOf(MSList.class));
        assertEquals("should have correct number of lists", record.size(), 2);
        assertEquals("spdString should be identical", "00002123456789001611031510Zorigina234567890221612031510Zusernam", record.toSpdString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationItemsNoNSEntryies() {
        MSList.fromOperationItems("00000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationItemsWrongNoOfNSEntryies() {
        MSList.fromOperationItems("00001123456789001611031510Zorigina234567890221612031510Zusernam");
    }

    @Test
    public void length() {
        assertEquals(66, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00066", subject().lengthField());
    }

    @Test
    public void toSpdString() {
        assertEquals("00002123456789001611031510Zorigina234567890221612031510Zusernam", subject().toSpdString());
    }

    @Test
    public void operationCode() {
        assertEquals(411, subject().operationCode());
    }
}
