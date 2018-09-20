/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.entities;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

public class MailboxTest {

    private static final SimpleDateFormat DATE = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void test() throws Exception {
        final Mailbox mailbox = new Mailbox();
        Assert.assertNull(mailbox.getId());
        Assert.assertNull(mailbox.getGemeentecode());
        Assert.assertNull(mailbox.getMailboxnr());
        Assert.assertNull(mailbox.getMailboxpwd());
        Assert.assertEquals(0, mailbox.getLimitNumber());
        Assert.assertNull(mailbox.getEindeBlokkering());
        Assert.assertNull(mailbox.getStartBlokkering());
        Assert.assertNull(mailbox.getLaatsteWijzigingPwd());

        mailbox.setId(123123L);
        mailbox.setGemeentecode("gem");
        mailbox.setMailboxnr("mailnr");
        mailbox.setMailboxpwd("pwd");
        mailbox.setLimitNumber(123456);
        mailbox.setEindeBlokkering(DATE.parse("01-01-2000"));
        mailbox.setStartBlokkering(DATE.parse("04-02-2002"));
        mailbox.setLaatsteWijzigingPwd(DATE.parse("13-11-2008"));

        Assert.assertEquals(Long.valueOf(123123L), mailbox.getId());
        Assert.assertEquals("gem", mailbox.getGemeentecode());
        Assert.assertEquals("mailnr", mailbox.getMailboxnr());
        Assert.assertEquals("pwd", mailbox.getMailboxpwd());
        Assert.assertEquals(123456, mailbox.getLimitNumber());
        Assert.assertEquals(DATE.parse("01-01-2000"), mailbox.getEindeBlokkering());
        Assert.assertEquals(DATE.parse("04-02-2002"), mailbox.getStartBlokkering());
        Assert.assertEquals(DATE.parse("13-11-2008"), mailbox.getLaatsteWijzigingPwd());

    }
}
