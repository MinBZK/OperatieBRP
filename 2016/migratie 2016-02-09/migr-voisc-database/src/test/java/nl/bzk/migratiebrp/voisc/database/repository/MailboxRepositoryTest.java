/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private SimpleDateFormat dateFormat;;

    @Before
    public void setup() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void testNotFound() throws ParseException {
        Assert.assertNull(mailboxRepository.getMailboxByInstantiecode(475643385));
    }

    @Test
    @Transactional(value = "voiscTransactionManager")
    public void test() throws ParseException {

        em.flush();

        final Mailbox test = em.find(Mailbox.class, 1379L);
        System.out.println(test);

        final Mailbox mailBox = new Mailbox();
        mailBox.setInstantietype(Mailbox.INSTANTIETYPE_GEMEENTE);
        mailBox.setInstantiecode(9994);
        mailBox.setMailboxnr("8089994");
        mailBox.setMailboxpwd("secret");
        mailBox.setLimitNumber(10);
        mailBox.setStartBlokkering(new Date());
        mailBox.setEindeBlokkering(new Date());
        mailBox.setLaatsteWijzigingPwd(new Date());

        mailboxRepository.save(mailBox);

        em.flush();

        mailBox.setStartBlokkering(dateFormat.parse("01-01-2000"));
        mailBox.setEindeBlokkering(dateFormat.parse("01-01-2002"));
        mailBox.setLaatsteWijzigingPwd(dateFormat.parse("01-01-2012"));
        mailboxRepository.save(mailBox);

        em.flush();

        Assert.assertNotNull(mailboxRepository.getGemeenteMailboxes());

        final Mailbox viaGemeenteCode = mailboxRepository.getMailboxByInstantiecode(9994);
        final Mailbox viaMailboxnr = mailboxRepository.getMailboxByNummer("8089994");
        mailBoxEquals(mailBox, viaGemeenteCode);
        mailBoxEquals(mailBox, viaMailboxnr);
    }

    private void mailBoxEquals(final Mailbox mailBox, final Mailbox other) {
        Assert.assertNotNull(mailBox);
        Assert.assertNotNull(other);

        Assert.assertEquals(mailBox.getId(), other.getId());
        Assert.assertEquals(mailBox.getInstantiecode(), other.getInstantiecode());
        Assert.assertEquals(mailBox.getInstantietype(), other.getInstantietype());
        Assert.assertEquals(mailBox.getMailboxnr(), other.getMailboxnr());
        Assert.assertEquals(mailBox.getMailboxpwd(), other.getMailboxpwd());
        Assert.assertEquals(mailBox.getLimitNumber(), other.getLimitNumber());
        Assert.assertEquals(mailBox.getEindeBlokkering(), other.getEindeBlokkering());
        Assert.assertEquals(mailBox.getStartBlokkering(), other.getStartBlokkering());
        Assert.assertEquals(mailBox.getLaatsteWijzigingPwd(), other.getLaatsteWijzigingPwd());
    }
}
