/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusReportTest {

    private StatusReport subject() {
        return StatusReport.fromOperationItems("recipie01122334455660");
    }

    @Test
    public void fromOperationItems() {
        assertThat("should have correct type", subject(), instanceOf(StatusReport.class));
        assertEquals("spdString should be identical", "recipie01122334455660", subject().toSpdString());
    }

    @Test
    public void toSpdString() {
        assertEquals("recipie01122334455660", subject().toSpdString());
    }

    @Test
    public void notificationType() {
        final StatusReport record = StatusReport.fromOperationItems("recipie11122334455660");
        assertEquals("recipie11122334455660", record.toSpdString());
    }

    @Test
    public void length() {
        assertEquals(24, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00024", subject().lengthField());
    }

    @Test
    public void operationCode() {
        assertEquals(270, subject().operationCode());
    }
}
