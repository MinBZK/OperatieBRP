/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * Mailbox repository implementatie.
 */
@Repository
public class MailboxRepositoryImpl implements MailboxRepository {

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    @Cacheable("mailboxen")
    public final Mailbox getMailboxByGemeentecode(final String gemeentecode) {
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where gemeentecode = :gemeentecode")
                        .setParameter("gemeentecode", gemeentecode).getResultList();
        if (mailboxes.size() > 0) {
            return mailboxes.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Cacheable("mailboxen")
    public final Mailbox getMailboxByNummer(final String mailboxnummer) {
        final List<Mailbox> mailboxes =
                em.createQuery("from Mailbox where mailboxnr = :mailboxnr").setParameter("mailboxnr", mailboxnummer)
                        .getResultList();
        if (mailboxes.size() > 0) {
            return mailboxes.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<Mailbox> getMailboxes() {
        return em.createQuery("from Mailbox").getResultList();
    }

    @Override
    public final LogboekRegel saveLogboekEntry(final LogboekRegel entry) {
        if (entry.getId() == null) {
            em.persist(entry);
            return entry;
        } else {
            return em.merge(entry);
        }

    }

    @Override
    public final void save(final Mailbox mailbox) {
        if (mailbox.getId() == null) {
            em.persist(mailbox);
        } else {
            em.merge(mailbox);
        }
    }
}
