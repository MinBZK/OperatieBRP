/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.repository.StamtabelRepository;

import org.springframework.stereotype.Repository;

/**
 * De implementatie van de StamtabelRepository interface.
 */
@Repository
public class StamtabelRepositoryImpl implements StamtabelRepository {

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public final Collection<BigDecimal> findAllPartijCodes() {
        return em.createQuery("select gemeentecode from Partij", BigDecimal.class).getResultList();
    }

    @Override
    public final Collection<BigDecimal> findAllGemeenteCodes() {
        return em.createQuery("select p.gemeentecode from Partij p where p.soortPartijId = 3", BigDecimal.class)
                .getResultList();
    }

    @Override
    public final Collection<BigDecimal> findAllLandCodes() {
        return em.createQuery("select landcode from Land", BigDecimal.class).getResultList();
    }

    @Override
    public final Collection<BigDecimal> findAllNationaliteitCodes() {
        return em.createQuery("select nationaliteitcode from Nationaliteit", BigDecimal.class).getResultList();
    }

    @Override
    public final Collection<String> findAllPlaatsnamen() {
        return em.createQuery("select naam from Plaats", String.class).getResultList();
    }

    @Override
    public final Collection<String> findAllNamenOpenbareRuimte() {
        throw new UnsupportedOperationException("BAG Lijst naam openbare ruimte nog niet beschikbaar");
    }
}
