/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.repository.InitVullingLogRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent sync logging.
 */
@Repository
public final class InitVullingLogRepositoryImpl implements InitVullingLogRepository {

    @PersistenceContext(name = "loggingEntityManagerFactory", unitName = "loggingEntityManagerFactory")
    private EntityManager em;

    @Override
    public void persistLog(final InitVullingLog log) {
        em.merge(log);
    }

    @Override
    public List<Long> findLogs(final Date vanaf, final Date tot, final String gemeentecode) {
        final String query =
                "select anummer from InitVullingLog where datumTijdOpnameGbav >= :vanaf "
                        + "and datumTijdOpnameGbav < :tot "
                        + "and (:gemeentecode = -1 or gemeenteVanInschrijving = :gemeentecode) ";

        final List<Long> anummers =
                em.createQuery(query, Long.class).setParameter("vanaf", vanaf).setParameter("tot", tot)
                        .setParameter("gemeentecode", gemeentecode != null ? Integer.parseInt(gemeentecode) : -1)
                        .getResultList();
        return anummers;
    }

    @Override
    public InitVullingLog findLog(final Long anummer) {
        try {
            return em
                    .createQuery("select ivl from InitVullingLog ivl where ivl.anummer = :anummer ",
                            InitVullingLog.class).setParameter("anummer", anummer).getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }
}
