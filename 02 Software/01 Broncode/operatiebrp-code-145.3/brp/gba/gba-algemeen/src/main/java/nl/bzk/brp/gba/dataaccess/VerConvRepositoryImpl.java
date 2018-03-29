/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de VerConvRepositoryImpl interface.
 */
@Repository("gbaVerConvRepository")
public class VerConvRepositoryImpl implements VerConvRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Override
    public final Lo3Voorkomen zoekLo3VoorkomenVoorActie(final Long actieId) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Lo3Voorkomen> criteria = criteriaBuilder.createQuery(Lo3Voorkomen.class);
        final Root<Lo3Voorkomen> criteriaRoot = criteria.from(Lo3Voorkomen.class);
        criteria.select(criteriaRoot);
        criteria.where(new Predicate[] {criteriaBuilder.equal(criteriaRoot.get("actie").get("id"), actieId) });

        final List<Lo3Voorkomen> result = em.createQuery(criteria).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
