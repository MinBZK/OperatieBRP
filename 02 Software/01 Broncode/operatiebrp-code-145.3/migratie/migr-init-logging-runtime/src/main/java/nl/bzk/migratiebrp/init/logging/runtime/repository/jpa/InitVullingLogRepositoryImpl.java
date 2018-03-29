/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingProtocollering;
import nl.bzk.migratiebrp.init.logging.runtime.repository.InitVullingLogRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent sync logging.
 */
@Repository
public final class InitVullingLogRepositoryImpl implements InitVullingLogRepository {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String PARAM_ANUMMER = "anummer";

    @PersistenceContext(name = "loggingEntityManagerFactory", unitName = "LogEntities")
    private EntityManager em;

    @Override
    public void saveInitVullingLogPersoon(final InitVullingLog log) {
        em.merge(log);
    }

    @Override
    public InitVullingLog findInitVullingLogPersoon(final String anummer) {
        try {
            return em.createNamedQuery("InitVullingLog.selectLogByAnummer", InitVullingLog.class).setParameter(PARAM_ANUMMER, anummer).getSingleResult();
        } catch (final NoResultException nre) {
            LOG.debug("Geen resultaten gevonden", nre);
            return null;
        }
    }

    @Override
    public InitVullingAutorisatie findInitVullingAutorisatie(final Long autorisatieId) {
        return em.find(InitVullingAutorisatie.class, autorisatieId);
    }

    @Override
    public void saveInitVullingAutorisatie(final InitVullingAutorisatie initVullingAutorisatie) {
        em.merge(initVullingAutorisatie);
    }

    @Override
    public InitVullingProtocollering findInitVullingProtocollering(final long activiteitId) {
        return em.find(InitVullingProtocollering.class, activiteitId);
    }

    @Override
    public void saveInitVullingProtocollering(final InitVullingProtocollering initVullingProtocollering) {
        em.merge(initVullingProtocollering);
    }

    @Override
    public InitVullingAfnemersindicatie findInitVullingAfnemersindicatie(final String administratienummer) {
        final List<InitVullingAfnemersindicatie> regels =
                em.createQuery("SELECT r FROM InitVullingAfnemersindicatie r " + " where r.administratienummer = :administratienummer ",
                        InitVullingAfnemersindicatie.class).setParameter("administratienummer", administratienummer).getResultList();

        if (regels.isEmpty()) {
            return null;
        } else if (regels.size() == 1) {
            return regels.get(0);
        } else {
            throw new IllegalStateException("Meerdere init vulling afnemersindicatie regels gevonden voor administratienummer " + administratienummer);
        }
    }

    @Override
    public void saveInitVullingAfnemersindicatie(final InitVullingAfnemersindicatie initVullingAfnemersindicatie) {
        LOG.info("Opslaan afnemersindicatie met id {}, anr {} en resultaat {}.", initVullingAfnemersindicatie.getPlId(),
                initVullingAfnemersindicatie.getAdministratienummer(), initVullingAfnemersindicatie.getBerichtResultaat());
        final InitVullingAfnemersindicatie opgeslagenInitVullingAfnemersindicatie = em.merge(initVullingAfnemersindicatie);
        LOG.info("Afnemersindicatie met id {}, anr {} opgeslagen met resultaat {}.", opgeslagenInitVullingAfnemersindicatie.getPlId(),
                opgeslagenInitVullingAfnemersindicatie.getAdministratienummer(), opgeslagenInitVullingAfnemersindicatie.getBerichtResultaat());
    }
}
