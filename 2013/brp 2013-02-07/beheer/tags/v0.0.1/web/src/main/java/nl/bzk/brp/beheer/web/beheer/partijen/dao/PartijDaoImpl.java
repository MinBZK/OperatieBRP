/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.beheer.model.Partij;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Deze class verzorgd voor het ophalen en opslaan van de Partij.
 *
 */
@Repository
@Transactional
public class PartijDaoImpl implements PartijDao {

    private static final String QUERY = " from Partij p left join p.soort as srt "
                                          + "left join p.sector as sec where lower(p.naam) "
                                          + "like lower(:waarde) or lower(srt.naam) "
                                          + "like lower(:waarde) or lower(sec.naam) like lower(:waarde)";

    @PersistenceContext
    private EntityManager       entityManager;

    @Override
    public Partij getById(final long id) {
        Partij object = entityManager.find(Partij.class, id);

        if (object == null) {
            throw new ObjectNotFoundException(id, Partij.class.toString());
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Partij> findPartij(final String waarde, final int startPosition, final int maxResult) {
        Query query = entityManager.createQuery("select p" + QUERY + " order by p.id", Partij.class);
        query.setParameter("waarde", "%" + waarde + "%");

        return query.setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
    }

    @Override
    public Long tellPartijen(final String waarde) {
        Query query = entityManager.createQuery("select count(p)" + QUERY);
        query.setParameter("waarde", "%" + waarde + "%");
        return (Long) query.getSingleResult();
    }

    @Override
    public Partij save(final Partij partij) {
        return entityManager.merge(partij);
    }
}
