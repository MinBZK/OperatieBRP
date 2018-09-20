/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;

import org.springframework.stereotype.Repository;

/**
 * Voa repository implementatie.
 */
@Repository
public final class VoaRepositoryImpl implements VoaRepository {

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Override
    public Bericht save(final Bericht lo3Bericht) {
        if (lo3Bericht.getId() == null) {
            em.persist(lo3Bericht);
            return lo3Bericht;
        } else {
            return em.merge(lo3Bericht);
        }
    }

    @Override
    public Bericht getLo3Bericht(final long id) {
        return em.find(Bericht.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Bericht> getBerichtToSendMBS(final String originator) {
        final Query query =
                em.createQuery("from Bericht "
                        + "where originator = :originator and (status =:status1 or status =:status2)");
        query.setParameter("originator", originator);
        query.setParameter("status1", StatusEnum.QUEUE_RECEIVED);
        query.setParameter("status2", StatusEnum.MESG_SENDING);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Bericht> getBerichtToSendQueue(final int limit) {
        final Query query = em.createQuery("from Bericht where status =:status");
        query.setParameter("status", StatusEnum.MAILBOX_RECEIVED);
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Bericht getBerichtByEref(final String eref) {
        Bericht gevondenBericht = null;
        final Query query = em.createQuery("from Bericht where eref =:eref");
        query.setParameter("eref", eref);
        final List<Bericht> berichten = query.getResultList();
        if (berichten.size() > 0) {
            gevondenBericht = berichten.get(0);
        }
        return gevondenBericht;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Bericht getBerichtByBrefAndOrginator(final String bref, final String originator) {
        Bericht gevondenBericht = null;
        final Query query = em.createQuery("from Bericht where bref =:bref and originator =:originator");
        query.setParameter("bref", bref);
        query.setParameter("originator", originator);
        final List<Bericht> berichten = query.getResultList();
        if (berichten.size() > 0) {
            gevondenBericht = berichten.get(0);
        }
        return gevondenBericht;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Bericht getBerichtByDispatchSeqNr(final Integer dispatchSeqNr) {
        Bericht gevondenBericht = null;
        final Query query = em.createQuery("from Bericht where dispatchSequenceNumber =:dispatchSeqNr");
        query.setParameter("dispatchSeqNr", dispatchSeqNr);
        final List<Bericht> berichten = query.getResultList();
        if (berichten.size() > 0) {
            gevondenBericht = berichten.get(0);
        }
        return gevondenBericht;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Bericht getBerichtByEsbMessageId(final String messageId) {
        Bericht gevondenBericht = null;
        final Query query = em.createQuery("from Bericht where esbMessageId =:esbMessageId");
        query.setParameter("esbMessageId", messageId);
        final List<Bericht> berichten = query.getResultList();
        if (berichten.size() > 0) {
            gevondenBericht = berichten.get(0);
        }
        return gevondenBericht;
    }
}
