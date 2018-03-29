/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import org.jbpm.calendar.Duration;
import org.junit.Assert;
import org.junit.Test;

public class TimerUtilTest {

    @Test
    public void test() {
        Assert.assertNull(TimerUtil.getDueDate(null, null));
        Assert.assertNotNull(TimerUtil.getDueDate(new Duration(6000000L), null));
        Assert.assertTrue(TimerUtil.getDueDate(new Duration(6000000L), null).compareTo(TimerUtil.getDueDate(new Duration(7000000L), null)) < 0);
    }
}
