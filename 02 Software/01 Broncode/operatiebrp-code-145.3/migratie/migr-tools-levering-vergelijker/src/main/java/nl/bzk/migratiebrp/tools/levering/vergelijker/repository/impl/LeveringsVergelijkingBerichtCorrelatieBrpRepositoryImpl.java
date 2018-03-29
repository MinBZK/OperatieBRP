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
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieBrp;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingBerichtCorrelatieBrpRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de LeveringsVergelijkingBerichtCorrelatieBrp repository.
 */
@Repository("leveringVergelijkingBerichtCorrelatieBrpRepository")
public final class LeveringsVergelijkingBerichtCorrelatieBrpRepositoryImpl implements LeveringsVergelijkingBerichtCorrelatieBrpRepository {

    private static final String PARAM_AFNEMER_CODE = "afnemerCode";
    private static final String PARAM_BIJHOUDING_BERICHT_MESSAGE_ID = "bijhoudingBerichtMessageId";

    @PersistenceContext(name = "leveringVergelijkerEntityManagerFactory", unitName = "LeveringsVergelijkingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<LeveringsVergelijkingBerichtCorrelatieBrp> haalLeveringsVergelijkingBerichtCorrelatiesBrpOp(
            final Long bijhoudingBerichtMessageId,
            final String afnemerCode) {
        final TypedQuery<LeveringsVergelijkingBerichtCorrelatieBrp> selectQuery =
                em.createQuery(
                        "select l from LeveringsVergelijkingBerichtCorrelatieBrp l "
                                + "where bijhoudingBerichtMessageId = :bijhoudingBerichtMessageId and afnemerCode = :afnemerCode",
                        LeveringsVergelijkingBerichtCorrelatieBrp.class);

        selectQuery.setParameter(PARAM_BIJHOUDING_BERICHT_MESSAGE_ID, bijhoudingBerichtMessageId);
        selectQuery.setParameter(PARAM_AFNEMER_CODE, afnemerCode);

        return selectQuery.getResultList();
    }
}
