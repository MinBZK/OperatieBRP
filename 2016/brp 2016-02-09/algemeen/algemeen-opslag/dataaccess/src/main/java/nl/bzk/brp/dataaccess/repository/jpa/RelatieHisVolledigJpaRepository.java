/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.RelatieHisVolledigRepository;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import org.springframework.stereotype.Repository;

/**
 * JPA implementatie van {@link RelatieHisVolledigRepository}.
 */
@Repository
public final class RelatieHisVolledigJpaRepository implements RelatieHisVolledigRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Override
    public RelatieHisVolledig leesGenormalizeerdModel(final Integer id) {
        return em.find(RelatieHisVolledigImpl.class, id);
    }

    @Override
    public void schrijfGenormalizeerdModel(final RelatieHisVolledig relatie) {
        em.persist(relatie);
    }

}
