/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.BrpBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieBrp;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.BrpBerichtRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de BrpBericht Repository.
 */
@Repository("BrpBerichtRepository")
public final class BrpBerichtRepositoryImpl implements BrpBerichtRepository {

    private static final String PARAM_BERICHT_ID = "id";

    @PersistenceContext(name = "leveringVergelijkerEntityManagerFactory", unitName = "LeveringsVergelijkingEntities")
    private EntityManager em;

    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    private BrpBericht haalBerichtOp(final Long id) {
        final TypedQuery<BrpBericht> selectQuery = em.createQuery("select brp from BrpBericht brp where id = :id", BrpBericht.class);

        selectQuery.setParameter(PARAM_BERICHT_ID, id);

        return selectQuery.getSingleResult();
    }

    @Override
    public BrpBericht haalBrpBijhoudingsBerichtOp(final LeveringsVergelijkingBerichtCorrelatieBrp leveringsVergelijkingBerichtCorrelatieBrp) {

        if (leveringsVergelijkingBerichtCorrelatieBrp.getBijhoudingBerichtId() == null) {
            return null;
        }

        return haalBerichtOp(leveringsVergelijkingBerichtCorrelatieBrp.getBijhoudingBerichtId());
    }

    @Override
    public BrpBericht haalBrpLeveringBerichtOp(final LeveringsVergelijkingBerichtCorrelatieBrp leveringsVergelijkingBerichtCorrelatieBrp) {

        if (leveringsVergelijkingBerichtCorrelatieBrp.getLeveringBerichtId() == null) {
            return null;
        }

        return haalBerichtOp(leveringsVergelijkingBerichtCorrelatieBrp.getLeveringBerichtId());
    }
}
