/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

import org.springframework.stereotype.Repository;

/**
 * JPA implementatie van {@link PersoonHisVolledigRepository}.
 */
@Repository
public final class PersoonHisVolledigJpaRepository implements PersoonHisVolledigRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager                em;

    @Override
    public PersoonHisVolledig leesGenormalizeerdModel(final Integer id) {
        return em.find(PersoonHisVolledigImpl.class, id);
    }

    @Override
    public void schrijfGenormalizeerdModel(final PersoonHisVolledigImpl persoon) {
        em.persist(persoon);
    }

    @Override
    public PersoonHisVolledigImpl opslaanNieuwPersoon(final PersoonHisVolledigImpl persoon) {
        // Persisteer de hele persoon his volledig in 1 keer.
        em.persist(persoon);
        return persoon;
    }

}
