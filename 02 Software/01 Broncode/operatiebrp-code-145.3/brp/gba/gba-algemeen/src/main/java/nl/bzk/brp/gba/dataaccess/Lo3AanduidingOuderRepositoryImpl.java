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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de Lo3AanduidingOuderRepository.
 */
@Repository("gbaLo3AanduidingOuderRepository")
public class Lo3AanduidingOuderRepositoryImpl implements Lo3AanduidingOuderRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Override
    public final AanduidingOuder getOuderIdentificatie(final Long ouderBetrokkenheidId) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Lo3AanduidingOuder> criteria = criteriaBuilder.createQuery(Lo3AanduidingOuder.class);
        final Root<Lo3AanduidingOuder> criteriaRoot = criteria.from(Lo3AanduidingOuder.class);
        criteria.select(criteriaRoot);
        criteria.where(new Predicate[]{criteriaBuilder.equal(criteriaRoot.get("ouder").get("id"), ouderBetrokkenheidId)});

        final List<Lo3AanduidingOuder> result = em.createQuery(criteria).getResultList();
        return result.isEmpty() ? null : result.get(0).getOuderAanduiding();
    }

    @Override
    public final void setAanduidingOuderBijOuderBetrokkenheid(final Long ouderBetrokkenheidId, final AanduidingOuder aanduidingOuder) {
        final Betrokkenheid ouderBetrokkenheid = em.getReference(Betrokkenheid.class, ouderBetrokkenheidId);
        final Lo3AanduidingOuder lo3AanduidingOuderModel = new Lo3AanduidingOuder(aanduidingOuder, ouderBetrokkenheid);
        em.persist(lo3AanduidingOuderModel);
    }
}
