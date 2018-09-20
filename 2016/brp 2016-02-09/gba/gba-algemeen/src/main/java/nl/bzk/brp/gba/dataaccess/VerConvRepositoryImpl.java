/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.verconv.LO3VoorkomenModel;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de VerConvRepositoryImpl interface.
 */
@Repository("gbaVerConvRepository")
public class VerConvRepositoryImpl implements VerConvRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager em;

    @Override
    public final LO3Voorkomen zoekLo3VoorkomenVoorActie(final Actie actie) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<LO3VoorkomenModel> criteria = criteriaBuilder.createQuery(LO3VoorkomenModel.class);
        final Root<LO3VoorkomenModel> criteriaRoot = criteria.from(LO3VoorkomenModel.class);
        criteria.select(criteriaRoot);
        criteria.where(new Predicate[] {criteriaBuilder.equal(criteriaRoot.get("mapping").get("actie"), actie) });

        final List<LO3VoorkomenModel> result = em.createQuery(criteria).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
