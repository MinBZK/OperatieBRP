/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;

public class TimestampTest {

    private static final SimpleDateFormat FORMAT_B = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void test() {
        //
        final Timestamp tijdstempelA = new Timestamp(116, 5, 9, 5, 18, 42, 660000000);
        Assert.assertEquals("2016-06-09 05:18:42.66", tijdstempelA.toString());
        Assert.assertEquals("2016-06-09 05:18:42.660", FORMAT_B.format(tijdstempelA));
        System.out.println(tijdstempelA);

        //
        final Timestamp tijdstempelB = new Timestamp(116, 5, 9, 5, 18, 42, 615000000);
        Assert.assertEquals("2016-06-09 05:18:42.615", tijdstempelB.toString());
        Assert.assertEquals("2016-06-09 05:18:42.615", FORMAT_B.format(tijdstempelB));
        System.out.println(tijdstempelB);
    }

}
