/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieGbav;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingBerichtCorrelatieGbavRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de LeveringsVergelijkingBerichtCorrelatieGbav repository.
 */
@Repository("leveringsVergelijkingBerichtCorrelatieGbavRepository")
public final class LeveringsVergelijkingBerichtCorrelatieGbavRepositoryImpl implements LeveringsVergelijkingBerichtCorrelatieGbavRepository {

    @PersistenceContext(name = "gbaEntityManagerFactory", unitName = "GbaEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "gbaTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<LeveringsVergelijkingBerichtCorrelatieGbav> haalLeveringVergelijkingBerichtenGbavOp() {
        final TypedQuery<LeveringsVergelijkingBerichtCorrelatieGbav> selectQuery =
                em.createQuery(
                        "select l from LeveringsVergelijkingBerichtCorrelatieGbav l order by bijhoudingBerichtId,afnemerCode",
                        LeveringsVergelijkingBerichtCorrelatieGbav.class);

        return selectQuery.getResultList();
    }

}
