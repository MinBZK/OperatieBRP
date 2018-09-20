/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao.partij;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.beheer.web.dao.BasisDao;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Deze class verzorgd voor het ophalen en opslaan van de Partij.
 *
 */
@Repository("partijDao")
@Transactional
public class PartijDaoImpl implements BasisDao<Partij> {

    private static final String QUERY = " from PersistentPartij p where lower(p.naam) like lower(:waarde)";
    // left join p.soort as srt "
    // + "left join p.sector as sec where lower(p.naam) "
    // + "like lower(:waarde) or lower(srt.naam) "
    // + "like lower(:waarde) or lower(sec.naam) like lower(:waarde)";

    @PersistenceContext
    private EntityManager       entityManager;

    @Override
    public Partij getById(final int id) {
        Partij object = entityManager.find(PersistentPartij.class, id);

        if (object == null) {
            throw new ObjectNotFoundException(id, PersistentPartij.class.toString());
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Partij> findObject(final String zoekterm, final int startPosition, final int maxResult) {
        Query query = entityManager.createQuery("select p" + QUERY + " order by p.id", PersistentPartij.class);
        query.setParameter("waarde", "%" + zoekterm + "%");

        return query.setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
    }

    @Override
    public Long tellObjecten(final String zoekterm) {
        Query query = entityManager.createQuery("select count(p)" + QUERY);
        query.setParameter("waarde", "%" + zoekterm + "%");
        return (Long) query.getSingleResult();
    }

    @Override
    public Partij save(final Partij partij) {
        return entityManager.merge(partij);
    }
}
