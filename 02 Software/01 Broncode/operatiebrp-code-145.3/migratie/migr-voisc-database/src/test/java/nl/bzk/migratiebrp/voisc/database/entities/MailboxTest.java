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

public class MailboxTest {

    private SimpleDateFormat dateFormat;
    ;

    @Before
    public void setup() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void test() throws ParseException {
        final Mailbox mailbox = new Mailbox();
        Assert.assertNull(mailbox.getId());
        Assert.assertNull(mailbox.getMailboxnr());
        Assert.assertNull(mailbox.getVerzender());
        Assert.assertNull(mailbox.getPartijcode());

        Assert.assertNull(mailbox.getEindeBlokkering());
        Assert.assertNull(mailbox.getStartBlokkering());

        Assert.assertNull(mailbox.getMailboxpwd());
        Assert.assertEquals(0, mailbox.getLimitNumber());
        Assert.assertNull(mailbox.getLaatsteWijzigingPwd());
        Assert.assertNull(mailbox.getLaatsteMsSequenceNumber());


        mailbox.setId(123123L);
        mailbox.setMailboxnr("mailnr");
        mailbox.setVerzender("verzender");
        mailbox.setPartijcode("190401");

        mailbox.setEindeBlokkering(new Timestamp(dateFormat.parse("01-01-2000").getTime()));
        mailbox.setStartBlokkering(new Timestamp(dateFormat.parse("04-02-2002").getTime()));

        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(123456);
        mailbox.setLaatsteWijzigingPwd(new Timestamp(dateFormat.parse("13-11-2008").getTime()));
        mailbox.setLaatsteMsSequenceNumber(42);

        Assert.assertEquals(Long.valueOf(123123L), mailbox.getId());
        Assert.assertEquals("mailnr", mailbox.getMailboxnr());
        Assert.assertEquals("verzender", mailbox.getVerzender());
        Assert.assertEquals("190401", mailbox.getPartijcode());

        Assert.assertEquals(new Timestamp(dateFormat.parse("01-01-2000").getTime()), mailbox.getEindeBlokkering());
        Assert.assertEquals(new Timestamp(dateFormat.parse("04-02-2002").getTime()), mailbox.getStartBlokkering());

        Assert.assertEquals("pwd", mailbox.getMailboxpwd());
        Assert.assertEquals(123456, mailbox.getLimitNumber());
        Assert.assertEquals(new Timestamp(dateFormat.parse("13-11-2008").getTime()), mailbox.getLaatsteWijzigingPwd());
        Assert.assertEquals(Integer.valueOf(42), mailbox.getLaatsteMsSequenceNumber());
    }

    @Test
    public void testCompareTo() {
        final Mailbox mailboxA = new Mailbox();
        mailboxA.setMailboxnr("518");

        final Mailbox mailboxB = new Mailbox();
        mailboxB.setMailboxnr("519");

        final Mailbox mailboxC = new Mailbox();
        mailboxC.setMailboxnr("519");

        Assert.assertTrue(mailboxA.compareTo(mailboxB) < 0);
        Assert.assertTrue(mailboxB.compareTo(mailboxC) == 0);
        Assert.assertTrue(mailboxC.compareTo(mailboxA) > 0);
    }
}
