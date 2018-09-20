/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao.bericht;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.beheer.web.dao.BasisDao;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.persistent.PersistentBericht;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Deze class verzorgd voor het ophalen en opslaan van de Partij.
 *
 */
@Repository("berichtDao")
@Transactional
public class BerichtDaoImpl implements BasisDao<Bericht> {

    private static final String QUERY = " from PersistentBericht b where lower(b.data) like lower(:waarde)";
//    left join p.soort as srt "
//                                          + "left join p.sector as sec where lower(p.naam) "
//                                          + "like lower(:waarde) or lower(srt.naam) "
//                                          + "like lower(:waarde) or lower(sec.naam) like lower(:waarde)";

    @PersistenceContext
    private EntityManager       entityManager;

    @Override
    public Bericht getById(final long id) {
        Bericht object = entityManager.find(PersistentBericht.class, id);

        if (object == null) {
            throw new ObjectNotFoundException(id, PersistentBericht.class.toString());
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Bericht> findObject(final String zoekterm, final int startPosition, final int maxResult) {
        Query query = entityManager.createQuery("select b" + QUERY + " order by b.id", PersistentBericht.class);
        query.setParameter("waarde", "%" + zoekterm + "%");

        return query.setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
    }

    @Override
    public Long tellObjecten(final String zoekterm) {
        Query query = entityManager.createQuery("select count(b)" + QUERY);
        query.setParameter("waarde", "%" + zoekterm + "%");
        return (Long) query.getSingleResult();
    }

    @Override
    public Bericht save(final Bericht bericht) {
        return entityManager.merge(bericht);
    }
}
