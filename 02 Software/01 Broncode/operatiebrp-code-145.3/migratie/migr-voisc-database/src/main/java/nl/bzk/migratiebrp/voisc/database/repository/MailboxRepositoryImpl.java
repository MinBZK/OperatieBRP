/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mailbox repository implementatie.
 */
@Repository
public class MailboxRepositoryImpl implements MailboxRepository {

    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Override
    @Cacheable(value = "mailboxenOpPartijcode", key = "#partijcode")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox getMailboxByPartijcode(final String partijcode) {
        if ("199902".equals(partijcode)) {
            throw new CentraleMailboxException();
        }
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where partijcode = :partijcode", Mailbox.class)
                        .setParameter("partijcode", partijcode)
                        .getResultList();
        if(mailboxes.isEmpty()) {
            return null;
        } else if(mailboxes.size() > 1) {
            throw new IllegalArgumentException("Geen unieke mailbox aan te wijzen voor partijcode " + partijcode);
        } else {
            return mailboxes.get(0);
        }
    }

    @Override
    @Cacheable(value = "mailboxenOpMailboxnr", key = "#mailboxnr")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox getMailboxByNummer(final String mailboxnr) {
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where mailboxnr = :mailboxnr", Mailbox.class).setParameter("mailboxnr", mailboxnr).getResultList();
        if (!mailboxes.isEmpty()) {
            return mailboxes.get(0);
        }
        return null;
    }

    @Override
    @Caching(put = {@CachePut(value = "mailboxenOpPartijcode", key = "#mailbox.partijcode"),
            @CachePut(value = "mailboxenOpMailboxnr", key = "#mailbox.mailboxnr")})
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox save(final Mailbox mailbox) {
        if (mailbox.getId() == null) {
            em.persist(mailbox);
            return mailbox;
        } else {
            return em.merge(mailbox);
        }
    }

    @Override
    public Collection<Mailbox> getAllMailboxen() {
        return  em.createQuery("from Mailbox", Mailbox.class).getResultList();
    }
}
