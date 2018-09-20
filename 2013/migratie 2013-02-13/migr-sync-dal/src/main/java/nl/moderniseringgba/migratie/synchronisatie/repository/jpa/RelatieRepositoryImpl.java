/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.repository.RelatieRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-relaties.
 * 
 */
@Repository
public final class RelatieRepositoryImpl implements RelatieRepository {

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelatie(final Relatie relatie) {
        if (relatie == null) {
            throw new NullPointerException("relatie mag niet null zijn");
        }
        verwijderMultiRealiteitRegelsVoorRelatie(relatie);
        verwijderMultiRealiteitRegelsVoorBetrokkenhedenVanRelatie(relatie);
        verwijderBetrokkenhedenVanRelatie(relatie);
        if (relatie.getId() != null) {
            em.remove(relatie);
        }
    }

    private void verwijderMultiRealiteitRegelsVoorBetrokkenhedenVanRelatie(final Relatie relatie) {
        final Set<MultiRealiteitRegel> teVerwijderenMultiRealiteitRegelsVoorBetrokkenen =
                new HashSet<MultiRealiteitRegel>();
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            teVerwijderenMultiRealiteitRegelsVoorBetrokkenen.addAll(betrokkenheid.getMultiRealiteitRegelSet());
        }
        for (final MultiRealiteitRegel multiRealiteitRegel : teVerwijderenMultiRealiteitRegelsVoorBetrokkenen) {
            multiRealiteitRegel.getGeldigVoorPersoon()
                    .removeMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
            multiRealiteitRegel.getBetrokkenheid().removeMultiRealiteitRegel(multiRealiteitRegel);
            if (multiRealiteitRegel.getId() != null) {
                em.remove(multiRealiteitRegel);
            }
        }
    }

    private void verwijderMultiRealiteitRegelsVoorRelatie(final Relatie relatie) {
        final Set<MultiRealiteitRegel> teVerwijderenMultiRealiteitRegelsVoorRelatie =
                new HashSet<MultiRealiteitRegel>(relatie.getMultiRealiteitRegelSet());
        for (final MultiRealiteitRegel multiRealiteitRegel : teVerwijderenMultiRealiteitRegelsVoorRelatie) {
            multiRealiteitRegel.getGeldigVoorPersoon()
                    .removeMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
            relatie.removeMultiRealiteitRegel(multiRealiteitRegel);
            if (multiRealiteitRegel.getId() != null) {
                em.remove(multiRealiteitRegel);
            }
        }
    }

    private void verwijderBetrokkenhedenVanRelatie(final Relatie relatie) {
        final Set<Betrokkenheid> teVerwijderenBetrokkenheden =
                new HashSet<Betrokkenheid>(relatie.getBetrokkenheidSet());
        for (final Betrokkenheid teVerwijderenBetrokkenheid : teVerwijderenBetrokkenheden) {
            final Persoon gerelateerdePersoon = teVerwijderenBetrokkenheid.getPersoon();
            if (gerelateerdePersoon != null) {
                gerelateerdePersoon.removeBetrokkenheid(teVerwijderenBetrokkenheid);
            }
            relatie.removeBetrokkenheid(teVerwijderenBetrokkenheid);
            if (teVerwijderenBetrokkenheid.getId() != null) {
                em.remove(teVerwijderenBetrokkenheid);
            }
            if (gerelateerdePersoon != null && gerelateerdePersoon.getId() != null
                    && SoortPersoon.NIET_INGESCHREVENE.equals(gerelateerdePersoon.getSoortPersoon())) {
                em.remove(gerelateerdePersoon);
            }
        }
    }
}
