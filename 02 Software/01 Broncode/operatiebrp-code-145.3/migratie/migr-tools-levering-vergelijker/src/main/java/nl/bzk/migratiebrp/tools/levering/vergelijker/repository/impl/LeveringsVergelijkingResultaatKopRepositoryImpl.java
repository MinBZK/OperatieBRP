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
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatKop;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingResultaatKopRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de LeveringsVergelijkingResultaatKop repository.
 */
@Repository("leveringsVergelijkingResultaatKopRepository")
public final class LeveringsVergelijkingResultaatKopRepositoryImpl implements LeveringsVergelijkingResultaatKopRepository {

    @PersistenceContext(name = "leveringVergelijkerEntityManagerFactory", unitName = "LeveringsVergelijkingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public LeveringsVergelijkingResultaatKop opslaanLeveringsVergelijkingKopResultaat(
            final LeveringsVergelijkingResultaatKop leveringsVergelijkingResultaatKop) {
        if (leveringsVergelijkingResultaatKop.getId() == null) {
            em.persist(leveringsVergelijkingResultaatKop);
            return leveringsVergelijkingResultaatKop;
        } else {
            return em.merge(leveringsVergelijkingResultaatKop);
        }
    }

    @Override
    @Transactional(value = "leveringVergelijkerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<LeveringsVergelijkingResultaatKop> opslaanLeveringsVergelijkingKopResultaten(
            final List<LeveringsVergelijkingResultaatKop> vergelijkingResultatenKop) {

        final List<LeveringsVergelijkingResultaatKop> opgeslagenVergelijkingResultaten = new ArrayList<>();

        for (final LeveringsVergelijkingResultaatKop leveringsVergelijkingResultaatKop : vergelijkingResultatenKop) {
            opgeslagenVergelijkingResultaten.add(opslaanLeveringsVergelijkingKopResultaat(leveringsVergelijkingResultaatKop));
        }

        return opgeslagenVergelijkingResultaten;
    }

}
