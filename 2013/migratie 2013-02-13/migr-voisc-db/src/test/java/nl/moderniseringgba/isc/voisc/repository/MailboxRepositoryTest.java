/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;

import org.junit.Assert;
import org.junit.Test;

public class MailboxRepositoryTest extends AbstractDbTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Inject
    private MailboxRepository mailboxRepository;

    @Test
    public void testNotFound() throws ParseException {
        Assert.assertNull(mailboxRepository.getMailboxByGemeentecode("475643385732595734984"));
        Assert.assertNull(mailboxRepository.getMailboxByNummer("475643385732595734984"));
    }

    @Test
    public void test() throws ParseException {
        final Mailbox mailBox = new Mailbox();
        mailBox.setGemeentecode("9994");
        mailBox.setMailboxnr("8089994");
        mailBox.setMailboxpwd("secret");
        mailBox.setLimitNumber(10);
        mailBox.setStartBlokkering(new Date());
        mailBox.setEindeBlokkering(new Date());
        mailBox.setLaatsteWijzigingPwd(new Date());

        mailboxRepository.save(mailBox);
        mailBox.setStartBlokkering(DATE_FORMAT.parse("01-01-2000"));
        mailBox.setEindeBlokkering(DATE_FORMAT.parse("01-01-2002"));
        mailBox.setLaatsteWijzigingPwd(DATE_FORMAT.parse("01-01-2012"));
        mailboxRepository.save(mailBox);

        final LogboekRegel logboekRegel = new LogboekRegel();
        logboekRegel.setMailbox(mailBox);
        logboekRegel.setStartDatumTijd(new Date());
        logboekRegel.setEindDatumTijd(new Date());
        logboekRegel.setAantalVerzonden(1);
        logboekRegel.setAantalVerzondenNOK(2);
        logboekRegel.setAantalVerzondenOK(3);
        logboekRegel.setAantalOntvangen(4);
        logboekRegel.setAantalOntvangenNOK(5);
        logboekRegel.setAantalOntvangenOK(6);
        logboekRegel.setFoutmelding("Bla");
        mailboxRepository.saveLogboekEntry(logboekRegel);

        Assert.assertNotNull(mailboxRepository.getMailboxes());

        final Mailbox viaGemeenteCode = mailboxRepository.getMailboxByGemeentecode("1703");
        final Mailbox viaMailBoxNummer = mailboxRepository.getMailboxByNummer("1703010");

        mailBoxEquals(viaGemeenteCode, viaMailBoxNummer);
    }

    private void mailBoxEquals(final Mailbox mailBox, final Mailbox other) {
        Assert.assertNotNull(mailBox);
        Assert.assertNotNull(other);

        Assert.assertEquals(mailBox.getId(), other.getId());
        Assert.assertEquals(mailBox.getGemeentecode(), other.getGemeentecode());
        Assert.assertEquals(mailBox.getMailboxnr(), other.getMailboxnr());
        Assert.assertEquals(mailBox.getMailboxpwd(), other.getMailboxpwd());
        Assert.assertEquals(mailBox.getLimitNumber(), other.getLimitNumber());
        Assert.assertEquals(mailBox.getEindeBlokkering(), other.getEindeBlokkering());
        Assert.assertEquals(mailBox.getStartBlokkering(), other.getStartBlokkering());
        Assert.assertEquals(mailBox.getLaatsteWijzigingPwd(), other.getLaatsteWijzigingPwd());
    }
}
