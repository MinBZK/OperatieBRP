/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LogSeverityTest {

    @Test
    public void testLogSeverity() {
        assertEquals(400, LogSeverity.CRITICAL.getSeverity());
        assertEquals(300, LogSeverity.ERROR.getSeverity());
        assertEquals(250, LogSeverity.SUPPRESSED.getSeverity());
        assertEquals(200, LogSeverity.WARNING.getSeverity());
        assertEquals(100, LogSeverity.INFO.getSeverity());
    }

    @Test
    public void testCompareSeverity() {
        assertTrue(LogSeverity.ERROR.compareTo(LogSeverity.CRITICAL) < 0);
        assertTrue(LogSeverity.SUPPRESSED.compareTo(LogSeverity.ERROR) < 0);
        assertTrue(LogSeverity.WARNING.compareTo(LogSeverity.SUPPRESSED) < 0);
        assertTrue(LogSeverity.INFO.compareTo(LogSeverity.WARNING) < 0);
    }

    @Test
    public void testValueOfSeverity() {
        assertEquals(LogSeverity.CRITICAL, LogSeverity.valueOfSeverity(400));
        assertEquals(LogSeverity.ERROR, LogSeverity.valueOfSeverity(300));
        assertEquals(LogSeverity.SUPPRESSED, LogSeverity.valueOfSeverity(250));
        assertEquals(LogSeverity.WARNING, LogSeverity.valueOfSeverity(200));
        assertEquals(LogSeverity.INFO, LogSeverity.valueOfSeverity(100));
    }

}
