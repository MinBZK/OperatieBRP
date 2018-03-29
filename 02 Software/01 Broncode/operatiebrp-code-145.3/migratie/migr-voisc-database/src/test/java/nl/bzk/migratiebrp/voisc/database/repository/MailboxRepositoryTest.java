/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class MailboxRepositoryTest extends AbstractRepositoryTest {

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Inject
    private MailboxRepository mailboxRepository;

    private SimpleDateFormat dateFormat;
    ;

    @Before
    public void setup() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void testNotFound() {
        Assert.assertNull(mailboxRepository.getMailboxByPartijcode("xxxxxx"));
    }

    @Test(expected = CentraleMailboxException.class)
    public void zoekOpPartijCodeCentraleVoorziening() {
        mailboxRepository.getMailboxByPartijcode("199902");
    }

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void test() throws ParseException, CentraleMailboxException {

        em.flush();

        final Mailbox test = em.find(Mailbox.class, 1379L);
        System.out.println(test);

        final Mailbox mailBox = new Mailbox();
        mailBox.setPartijcode("808999");
        mailBox.setMailboxnr("8089994");
        mailBox.setVerzender("8089995");
        mailBox.setMailboxpwd("secret");
        mailBox.setLimitNumber(10);
        mailBox.setStartBlokkering(new Timestamp(System.currentTimeMillis()));
        mailBox.setEindeBlokkering(new Timestamp(System.currentTimeMillis()));
        mailBox.setLaatsteWijzigingPwd(new Timestamp(System.currentTimeMillis()));

        mailboxRepository.save(mailBox);

        em.flush();

        mailBox.setStartBlokkering(new Timestamp(dateFormat.parse("01-01-2000").getTime()));
        mailBox.setEindeBlokkering(new Timestamp(dateFormat.parse("01-01-2002").getTime()));
        mailBox.setLaatsteWijzigingPwd(new Timestamp(dateFormat.parse("01-01-2012").getTime()));
        mailboxRepository.save(mailBox);

        em.flush();

        final Mailbox viaGemeenteCode = mailboxRepository.getMailboxByPartijcode("808999");
        final Mailbox viaMailboxnr = mailboxRepository.getMailboxByNummer("8089994");
        mailBoxEquals(mailBox, viaGemeenteCode);
        mailBoxEquals(mailBox, viaMailboxnr);
    }

    private void mailBoxEquals(final Mailbox mailBox, final Mailbox other) {
        Assert.assertNotNull(mailBox);
        Assert.assertNotNull(other);

        Assert.assertEquals(mailBox.getId(), other.getId());
        Assert.assertEquals(mailBox.getPartijcode(), other.getPartijcode());
        Assert.assertEquals(mailBox.getVerzender(), other.getVerzender());
        Assert.assertEquals(mailBox.getMailboxnr(), other.getMailboxnr());
        Assert.assertEquals(mailBox.getMailboxpwd(), other.getMailboxpwd());
        Assert.assertEquals(mailBox.getLimitNumber(), other.getLimitNumber());
        Assert.assertEquals(mailBox.getEindeBlokkering(), other.getEindeBlokkering());
        Assert.assertEquals(mailBox.getStartBlokkering(), other.getStartBlokkering());
        Assert.assertEquals(mailBox.getLaatsteWijzigingPwd(), other.getLaatsteWijzigingPwd());
    }
}
