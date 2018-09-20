/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MailboxTest {

    private SimpleDateFormat dateFormat;;

    @Before
    public void setup() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void test() throws ParseException {
        final Mailbox mailbox = new Mailbox();
        Assert.assertNull(mailbox.getId());
        Assert.assertNull(mailbox.getInstantietype());
        Assert.assertNull(mailbox.getInstantiecode());
        Assert.assertNull(mailbox.getMailboxnr());
        Assert.assertNull(mailbox.getMailboxpwd());
        Assert.assertEquals(0, mailbox.getLimitNumber());
        Assert.assertNull(mailbox.getEindeBlokkering());
        Assert.assertNull(mailbox.getStartBlokkering());
        Assert.assertNull(mailbox.getLaatsteWijzigingPwd());

        mailbox.setId(123123L);
        mailbox.setInstantiecode(1904010);
        mailbox.setInstantietype("G");
        mailbox.setMailboxnr("mailnr");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(123456);
        mailbox.setEindeBlokkering(dateFormat.parse("01-01-2000"));
        mailbox.setStartBlokkering(dateFormat.parse("04-02-2002"));
        mailbox.setLaatsteWijzigingPwd(dateFormat.parse("13-11-2008"));

        Assert.assertEquals(Long.valueOf(123123L), mailbox.getId());
        Assert.assertEquals(Integer.valueOf(1904010), mailbox.getInstantiecode());
        Assert.assertEquals("G", mailbox.getInstantietype());
        Assert.assertEquals("mailnr", mailbox.getMailboxnr());
        Assert.assertEquals("pwd", mailbox.getMailboxpwd());
        Assert.assertEquals(123456, mailbox.getLimitNumber());
        Assert.assertEquals(dateFormat.parse("01-01-2000"), mailbox.getEindeBlokkering());
        Assert.assertEquals(dateFormat.parse("04-02-2002"), mailbox.getStartBlokkering());
        Assert.assertEquals(dateFormat.parse("13-11-2008"), mailbox.getLaatsteWijzigingPwd());
    }

    @Test
    public void testFormatCode() {
        final Mailbox gemeenteMailbox = new Mailbox();
        gemeenteMailbox.setInstantiecode(518);
        gemeenteMailbox.setInstantietype(Mailbox.INSTANTIETYPE_GEMEENTE);
        Assert.assertEquals("0518", gemeenteMailbox.getFormattedInstantiecode());

        final Mailbox afnemerMailbox = new Mailbox();
        afnemerMailbox.setInstantiecode(44444);
        afnemerMailbox.setInstantietype(Mailbox.INSTANTIETYPE_AFNEMER);
        Assert.assertEquals("044444", afnemerMailbox.getFormattedInstantiecode());

        final Mailbox mailbox = new Mailbox();
        mailbox.setInstantiecode(1);
        mailbox.setInstantietype(Mailbox.INSTANTIETYPE_CENTRALE_VOORZIENING);
        Assert.assertEquals("1", mailbox.getFormattedInstantiecode());
    }

    @Test
    public void testCompareTo() {
        final Mailbox mailboxA = new Mailbox();
        mailboxA.setInstantiecode(518);

        final Mailbox mailboxB = new Mailbox();
        mailboxB.setInstantiecode(519);

        final Mailbox mailboxC = new Mailbox();
        mailboxC.setInstantiecode(519);

        Assert.assertTrue(mailboxA.compareTo(mailboxB) < 0);
        Assert.assertTrue(mailboxB.compareTo(mailboxC) == 0);
        Assert.assertTrue(mailboxC.compareTo(mailboxA) > 0);
    }
}
