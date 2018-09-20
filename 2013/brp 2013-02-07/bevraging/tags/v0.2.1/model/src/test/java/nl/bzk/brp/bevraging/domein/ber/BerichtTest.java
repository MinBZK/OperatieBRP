/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link Bericht} class.
 */
public class BerichtTest {

    @Test
    public void testBerichtStringCalendarRichting() {
        Bericht bericht = new Bericht("Test", Richting.INGAAND);
        Assert.assertEquals("Test", bericht.getData());
        Assert.assertEquals(Richting.INGAAND, bericht.getRichting());

        Date now = new Date();
        Long diff = now.getTime() - bericht.getOntvangst().getTimeInMillis();
        Assert.assertTrue(diff >= 0 && diff <= 100);
    }

    @Test
    public void testBerichtStringRichting() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Bericht bericht = new Bericht("Test2", cal, Richting.UITGAAND);
        Assert.assertEquals("Test2", bericht.getData());
        Assert.assertEquals(Richting.UITGAAND, bericht.getRichting());
        Assert.assertEquals(cal, bericht.getOntvangst());
    }

}
