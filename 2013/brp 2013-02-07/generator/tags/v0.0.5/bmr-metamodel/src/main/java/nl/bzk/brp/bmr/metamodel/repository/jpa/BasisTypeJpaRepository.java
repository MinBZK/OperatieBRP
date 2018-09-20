/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bmr.metamodel.BasisType;
import nl.bzk.brp.bmr.metamodel.NamedModelElement;
import nl.bzk.brp.bmr.metamodel.repository.BasisTypeRepository;

import org.springframework.stereotype.Repository;


/**
 * Abstracte superclasse voor alle JPA repositories van de BMR generator.
 */
@Repository("basisTypeRepository")
public class BasisTypeJpaRepository implements BasisTypeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BasisType> findAll() {
        return em.createQuery("select bt from BasisType bt", BasisType.class).getResultList();
    }

    @Override
    public NamedModelElement findByNaam(final String naam) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BasisType findOne(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
