/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;

import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de Lo3BerichtRepository.
 */
@Repository
public final class Lo3BerichtRepositoryImpl implements Lo3BerichtRepository {

    private static final String PARAM_TOT = "tot";
    private static final String PARAM_VANAF = "vanaf";
    private static final String PARAM_TYPE = "isBerichtsoortOnderdeelLo3Stelsel";

    private static final String LAATSTE_LO3BERICHT_ANRS_QUERY_ZONDER_GEM_CODE =
            "select anummer FROM Lo3Bericht "
                    + "WHERE tijdstipConversie >= :vanaf "
                    + "AND tijdstipConversie < :tot "
                    + "AND isBerichtsoortOnderdeelLo3Stelsel = :isBerichtsoortOnderdeelLo3Stelsel";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Lo3Bericht findLaatsteLo3PersoonslijstBerichtVoorANummer(final String administratienummer) {
        // where referentie != 'referentie' and referentie not like 'IV.AUT%' and referentie not like 'IV.IND%'
        final String laatsteLo3BerichtQuery =
                "FROM Lo3Bericht WHERE anummer = :anummer AND isBerichtsoortOnderdeelLo3Stelsel = true "
                        + "AND referentie != 'referentie' AND referentie NOT LIKE 'IV.AUT%' AND referentie NOT LIKE 'IV.IND%'"
                        + "ORDER BY tijdstipConversie DESC";
        final List<Lo3Bericht> resultList =
                entityManager.createQuery(laatsteLo3BerichtQuery, Lo3Bericht.class)
                        .setMaxResults(1)
                        .setParameter("anummer", administratienummer)
                        .getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> findLaatsteBerichtLogAnrs(final Date vanaf, final Date tot) {
        return new HashSet<>(
                entityManager.createQuery(LAATSTE_LO3BERICHT_ANRS_QUERY_ZONDER_GEM_CODE, String.class)
                        .setParameter(PARAM_VANAF, vanaf)
                        .setParameter(PARAM_TOT, tot)
                        .setParameter(PARAM_TYPE, true)
                        .getResultList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lo3Bericht save(final Lo3Bericht bericht) {
        if (bericht == null) {
            throw new IllegalArgumentException("bericht mag niet null zijn");
        }
        if (bericht.getId() == null) {
            entityManager.persist(bericht);
            return bericht;
        } else {
            return entityManager.merge(bericht);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        entityManager.flush();
    }
}
