/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    private static final String PARAMETER_INSTANTIE_TYPE = "instantietype";

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Override
    @Cacheable(value = "mailboxenOpInstantiecode", key = "#instantiecode")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox getMailboxByInstantiecode(final int instantiecode) {
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where instantiecode = :instantiecode", Mailbox.class)
                .setParameter("instantiecode", instantiecode)
                .getResultList();
        if (mailboxes.size() > 0) {
            return mailboxes.get(0);
        }
        return null;
    }

    @Override
    @Cacheable(value = "mailboxenOpMailboxnr", key = "#mailboxnr")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox getMailboxByNummer(final String mailboxnr) {
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where mailboxnr = :mailboxnr", Mailbox.class).setParameter("mailboxnr", mailboxnr).getResultList();
        if (mailboxes.size() > 0) {
            return mailboxes.get(0);
        }
        return null;
    }

    @Override
    @Caching(put = {@CachePut(value = "mailboxenOpInstantiecode", key = "#mailbox.instantiecode"),
                    @CachePut(value = "mailboxenOpMailboxnr", key = "#mailbox.mailboxnr") })
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public final Mailbox save(final Mailbox mailbox) {

        if (mailbox.getId() == null) {
            em.persist(mailbox);
            return mailbox;
        } else {
            return em.merge(mailbox);
        }
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository#getGemeenteMailboxes()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final List<Mailbox> getGemeenteMailboxes() {
        return getMailboxes(Mailbox.INSTANTIETYPE_GEMEENTE);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository#getCentraleMailboxes()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final List<Mailbox> getCentraleMailboxes() {
        return getMailboxes(Mailbox.INSTANTIETYPE_CENTRALE_VOORZIENING);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository#getAfnemerMailboxes()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = VOISC_TRANSACTION_MANAGER)
    public final List<Mailbox> getAfnemerMailboxes() {
        return getMailboxes(Mailbox.INSTANTIETYPE_AFNEMER);
    }

    private List<Mailbox> getMailboxes(final String instantieType) {
        final TypedQuery<Mailbox> query = em.createQuery("from Mailbox where instantietype = :instantietype", Mailbox.class);
        query.setParameter(PARAMETER_INSTANTIE_TYPE, instantieType);
        return query.getResultList();
    }
}
