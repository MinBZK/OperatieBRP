/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public final class HisAfnemerindicatieBlobJpaRepository implements HisAfnemerindicatieBlobRepository {

    private static final String OPHALEN_INDICATIES_JPQL =
        "select pahv from PersoonAfnemerindicatieHisVolledigImpl pahv where pahv.persoon.id = :persoonId";
    private static final String PERSOON_ID              = "persoonId";

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager emAlleenLezen;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager emLezenSchrijven;

    @Override
    public Set<PersoonAfnemerindicatieHisVolledigImpl> leesGenormaliseerdModelVoorNieuweBlob(final Integer technischId) {
        final TypedQuery<PersoonAfnemerindicatieHisVolledigImpl> tQuery =
            emLezenSchrijven.createQuery(OPHALEN_INDICATIES_JPQL, PersoonAfnemerindicatieHisVolledigImpl.class).setParameter(PERSOON_ID, technischId);

        return new HashSet<>(tQuery.getResultList());
    }

    @Override
    public Set<PersoonAfnemerindicatieHisVolledigImpl> leesGenormaliseerdModelVoorInMemoryBlob(final Integer technischId) {
        final TypedQuery<PersoonAfnemerindicatieHisVolledigImpl> tQuery =
            emAlleenLezen.createQuery(OPHALEN_INDICATIES_JPQL, PersoonAfnemerindicatieHisVolledigImpl.class).setParameter(PERSOON_ID, technischId);

        return new HashSet<>(tQuery.getResultList());
    }
}
