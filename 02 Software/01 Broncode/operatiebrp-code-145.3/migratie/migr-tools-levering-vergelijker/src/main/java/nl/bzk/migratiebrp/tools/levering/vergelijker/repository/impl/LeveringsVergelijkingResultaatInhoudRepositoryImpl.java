/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatInhoud;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingResultaatInhoudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de LeveringsVergelijkingResultaatInhoud repository.
 */
@Repository("leveringVergelijkerResultaatRepository")
public final class LeveringsVergelijkingResultaatInhoudRepositoryImpl implements LeveringsVergelijkingResultaatInhoudRepository {

    @PersistenceContext(name = "leveringVergelijkerEntityManagerFactory", unitName = "LeveringsVergelijkingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public LeveringsVergelijkingResultaatInhoud opslaanLeveringsVergelijkingInhoudResultaat(
            final LeveringsVergelijkingResultaatInhoud leveringsVergelijkingResultaatInhoud) {
        if (leveringsVergelijkingResultaatInhoud.getId() == null) {
            em.persist(leveringsVergelijkingResultaatInhoud);
            return leveringsVergelijkingResultaatInhoud;
        } else {
            return em.merge(leveringsVergelijkingResultaatInhoud);
        }
    }

    @Override
    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<LeveringsVergelijkingResultaatInhoud> opslaanLeveringsVergelijkingInhoudResultaten(
            final List<LeveringsVergelijkingResultaatInhoud> vergelijkingResultaten) {

        final List<LeveringsVergelijkingResultaatInhoud> opgeslagenVergelijkingResultaten = new ArrayList<>();

        for (final LeveringsVergelijkingResultaatInhoud leveringsVergelijkingResultaatInhoud : vergelijkingResultaten) {
            opgeslagenVergelijkingResultaten.add(opslaanLeveringsVergelijkingInhoudResultaat(leveringsVergelijkingResultaatInhoud));
        }

        return opgeslagenVergelijkingResultaten;
    }

}
