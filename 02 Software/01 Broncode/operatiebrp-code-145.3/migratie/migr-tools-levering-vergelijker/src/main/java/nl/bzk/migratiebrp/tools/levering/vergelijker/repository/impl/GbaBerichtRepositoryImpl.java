/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.GbaBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieGbav;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.GbaBerichtRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de BrpBericht Repository.
 */
@Repository("gbaBerichtRepository")
public final class GbaBerichtRepositoryImpl implements GbaBerichtRepository {

    private static final String PARAM_BERICHT_ID = "lo3BerichtId";

    @PersistenceContext(name = "gbaEntityManagerFactory", unitName = "GbaEntities")
    private EntityManager em;

    @Transactional(value = "gbaTransactionManager", propagation = Propagation.REQUIRES_NEW)
    private GbaBericht haalBerichtOp(final Long id) {
        final TypedQuery<GbaBericht> selectQuery = em.createQuery("select gba from GbaBericht gba where lo3BerichtId = :lo3BerichtId", GbaBericht.class);

        selectQuery.setParameter(PARAM_BERICHT_ID, id);

        return selectQuery.getSingleResult();
    }

    @Override
    public GbaBericht haalGbaBijhoudingsBerichtOp(final LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav) {

        if (leveringsVergelijkingBerichtCorrelatieGbav.getBijhoudingBerichtId() == null) {
            return null;
        }

        return haalBerichtOp(leveringsVergelijkingBerichtCorrelatieGbav.getBijhoudingBerichtId());
    }

    @Override
    public GbaBericht haalGbaLeveringBerichtOp(final LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav) {

        if (leveringsVergelijkingBerichtCorrelatieGbav.getLeveringBerichtId() == null) {
            return null;
        }
        return haalBerichtOp(leveringsVergelijkingBerichtCorrelatieGbav.getLeveringBerichtId());
    }
}
