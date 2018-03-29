/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BerichtTest {

    private SimpleDateFormat dateFormat;
    ;

    @Before
    public void setup() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void test() throws ParseException {
        final Bericht bericht = new Bericht();
        Assert.assertNull(bericht.getId());
        Assert.assertNull(bericht.getOriginator());
        Assert.assertNull(bericht.getRecipient());
        Assert.assertNull(bericht.getMessageId());
        Assert.assertNull(bericht.getCorrelationId());
        Assert.assertNull(bericht.getTijdstipOntvangst());
        Assert.assertNull(bericht.getTijdstipInVerwerking());
        Assert.assertNull(bericht.getTijdstipVerzonden());
        Assert.assertNull(bericht.getDispatchSequenceNumber());
        Assert.assertNull(bericht.getBerichtInhoud());
        Assert.assertNull(bericht.getStatus());

        bericht.setId(1234L);
        bericht.setOriginator("orig");
        bericht.setRecipient("recip");
        bericht.setMessageId("msgId");
        bericht.setCorrelationId("corrId");
        bericht.setTijdstipOntvangst(new Timestamp(dateFormat.parse("01-01-2000").getTime()));
        bericht.setTijdstipInVerwerking(new Timestamp(dateFormat.parse("02-02-2001").getTime()));
        bericht.setTijdstipVerzonden(new Timestamp(dateFormat.parse("03-03-2003").getTime()));
        bericht.setDispatchSequenceNumber(123);
        bericht.setBerichtInhoud("berichtInhoud");
        bericht.setStatus(StatusEnum.SENT_TO_ISC);

        Assert.assertEquals(Long.valueOf(1234L), bericht.getId());
        Assert.assertEquals("orig", bericht.getOriginator());
        Assert.assertEquals("recip", bericht.getRecipient());
        Assert.assertEquals("msgId", bericht.getMessageId());
        Assert.assertEquals("corrId", bericht.getCorrelationId());
        Assert.assertEquals(new Timestamp(dateFormat.parse("01-01-2000").getTime()), bericht.getTijdstipOntvangst());
        Assert.assertEquals(new Timestamp(dateFormat.parse("02-02-2001").getTime()), bericht.getTijdstipInVerwerking());
        Assert.assertEquals(new Timestamp(dateFormat.parse("03-03-2003").getTime()), bericht.getTijdstipVerzonden());
        Assert.assertEquals(Integer.valueOf(123), bericht.getDispatchSequenceNumber());
        Assert.assertEquals("berichtInhoud", bericht.getBerichtInhoud());
        Assert.assertEquals(StatusEnum.SENT_TO_ISC, bericht.getStatus());
    }
}
