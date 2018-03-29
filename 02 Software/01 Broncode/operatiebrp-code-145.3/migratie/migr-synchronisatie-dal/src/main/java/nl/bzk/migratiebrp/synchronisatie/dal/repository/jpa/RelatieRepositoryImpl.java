/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.RelatieRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-relaties.
 */
@Repository
public final class RelatieRepositoryImpl implements RelatieRepository {

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelatie(final Relatie relatie) {
        if (relatie == null) {
            throw new NullPointerException("relatie mag niet null zijn");
        }
        verwijderBetrokkenhedenVanRelatie(relatie);
        if (relatie.getId() != null) {
            em.remove(relatie);
        }
    }

    private void verwijderBetrokkenhedenVanRelatie(final Relatie relatie) {
        final Set<Betrokkenheid> teVerwijderenBetrokkenheden = new HashSet<>(relatie.getBetrokkenheidSet());
        for (final Betrokkenheid teVerwijderenBetrokkenheid : teVerwijderenBetrokkenheden) {
            final Persoon gerelateerdePersoon = teVerwijderenBetrokkenheid.getPersoon();
            if (gerelateerdePersoon != null) {
                gerelateerdePersoon.removeBetrokkenheid(teVerwijderenBetrokkenheid);
            }
            relatie.removeBetrokkenheid(teVerwijderenBetrokkenheid);
            if (teVerwijderenBetrokkenheid.getId() != null) {
                em.remove(teVerwijderenBetrokkenheid);
            }
            if (gerelateerdePersoon != null
                    && gerelateerdePersoon.getId() != null
                    && SoortPersoon.PSEUDO_PERSOON.equals(gerelateerdePersoon.getSoortPersoon())) {
                em.remove(gerelateerdePersoon);
            }
        }
    }
}
