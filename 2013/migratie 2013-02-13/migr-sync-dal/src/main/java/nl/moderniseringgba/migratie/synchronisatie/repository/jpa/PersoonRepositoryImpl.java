/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.repository.PersoonRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-personen.
 */
@Repository
public final class PersoonRepositoryImpl implements PersoonRepository {
    private static final String SOORT_PERSOON_NULL_MELDING = "soortPersoon mag niet null zijn";
    private static final String ADMINISTRATIENUMMER_NULL_MELDING = "administratienummer mag niet null zijn";
    private static final String SOORT_PERSOON_ID_PARAM = "soortPersoonId";
    private static final String ADMINISTRATIENUMMER_PARAM = "administratienummer";
    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummer(
            final BigDecimal administratienummer,
            final SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        final List<Long> persoonIds =
                em.createQuery(
                        "SELECT p.id FROM Persoon p WHERE p.administratienummer = :administratienummer "
                                + "AND p.soortPersoonId = :soortPersoonId ORDER BY p.id", Long.class)
                        .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                        .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId()).getResultList();

        /*
         * Onderstaande constructie is bedoeld om de fetching strategie te gebruiken die op de Persoon entity is
         * geconfigureerd m.b.v. annotaties.
         */
        final List<Persoon> result = new ArrayList<Persoon>();
        for (final Long persoonId : persoonIds) {
            result.add(em.find(Persoon.class, persoonId));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummerHistorisch(
            final BigDecimal administratienummer,
            final SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em
                .createQuery(
                        "select distinct his.persoon from PersoonIDHistorie his "
                                + "where his.administratienummer = :administratienummer "
                                + "and his.datumEindeGeldigheid is not null and his.datumTijdVerval is null "
                                + "and his.persoon.soortPersoonId = :soortPersoonId", Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId()).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(final Persoon persoon) {
        em.detach(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final Persoon persoon) {
        em.remove(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGroepenEnHistorie(final Persoon persoon) {
        final Set<Object> teVerwijderenEntities = persoon.verwijderAlleRelatiesAfhankelijkVanPersoon();
        for (final Object entity : teVerwijderenEntities) {
            em.remove(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Persoon save(final Persoon persoon) {
        if (persoon.getId() == null) {
            em.persist(persoon);
            return persoon;
        } else {
            return em.merge(persoon);
        }
    }
}
