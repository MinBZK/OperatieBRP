/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van de LeveringPersoonRepository.
 *
 * @brp.bedrijfsregel R1996
 */
@Regels(Regel.R1996)
@Repository
public class LeveringPersoonRepositoryImpl implements LeveringPersoonRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.protocollering")
    private EntityManager em;

    @Override
    public final LeveringPersoonModel opslaanNieuweLeveringPersoon(final LeveringPersoonModel leveringPersoonModel) {
        em.persist(leveringPersoonModel);
        return leveringPersoonModel;
    }

}
