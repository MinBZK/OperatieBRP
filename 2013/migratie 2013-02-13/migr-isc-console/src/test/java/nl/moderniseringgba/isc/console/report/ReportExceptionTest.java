/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report;

import org.junit.Assert;
import org.junit.Test;

public class ReportExceptionTest {

    @Test
    public void test() {
        final ReportException exception = new ReportException("Snort");

        Assert.assertEquals("Snort", exception.getMessage());
        Assert.assertNull(exception.getCause());

        final ReportException exceptionWithCause = new ReportException("Bla", exception);
        Assert.assertEquals("Bla", exceptionWithCause.getMessage());
        Assert.assertEquals(exception, exceptionWithCause.getCause());
    }
}
