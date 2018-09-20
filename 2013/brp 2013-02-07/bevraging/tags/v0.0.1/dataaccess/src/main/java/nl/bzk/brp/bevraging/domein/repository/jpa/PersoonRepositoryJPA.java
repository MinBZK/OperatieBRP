/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA-implementatie van {@link PersoonRepository} interface.
 */
@Repository("persoonRepository")
public class PersoonRepositoryJPA implements PersoonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Persoon zoekOpBSN(Integer bsn) {
        TypedQuery<Persoon> query = em.createQuery("from Persoon where bsn = :bsn", Persoon.class);
        query.setParameter("bsn", bsn);
        List<Persoon> result = query.getResultList();
        if (result.size() > 1) {
            throw new RuntimeException("Meer dan één persoon gevonden voor dit BSN: " + bsn.toString());
        }
        return result.size() == 0 ? null : result.get(0);
    }
    
    @Override
    public void create(Persoon persoon) {
        em.persist(persoon);
    }
}
