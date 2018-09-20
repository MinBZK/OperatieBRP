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

import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatieStapel;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.runtime.repository.InitVullingLogRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

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
    public InitVullingLog findInitVullingLogPersoon(final Long anummer) {
        try {
            return em.createNamedQuery("InitVullingLog.selectLogByAnummer", InitVullingLog.class).setParameter(PARAM_ANUMMER, anummer).getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    @Override
    public InitVullingAutorisatie findInitVullingAutorisatie(final Integer afnemerCode) {
        return em.find(InitVullingAutorisatie.class, afnemerCode);
    }

    @Override
    public void saveInitVullingAutorisatie(final InitVullingAutorisatie initVullingAutorisatie) {
        LOG.info(
            "Opslaan autorisatie met afnemercode {} en resultaat {}.",
            initVullingAutorisatie.getAfnemerCode(),
            initVullingAutorisatie.getConversieResultaat());
        final InitVullingAutorisatie opgeslagenInitVullingAutorisatie = em.merge(initVullingAutorisatie);
        LOG.info(
            "Autorisatie met afnemercode {} opgeslagen met resultaat {}.",
            opgeslagenInitVullingAutorisatie.getAfnemerCode(),
            opgeslagenInitVullingAutorisatie.getConversieResultaat());
    }

    @Override
    public InitVullingAfnemersindicatie findInitVullingAfnemersindicatie(final Long administratienummer) {
        final List<InitVullingAfnemersindicatie> regels =
                em.createQuery(
                      "SELECT r FROM InitVullingAfnemersindicatie r " + " where r.administratienummer = :administratienummer ",
                      InitVullingAfnemersindicatie.class)
                  .setParameter("administratienummer", administratienummer)
                  .getResultList();

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
        LOG.info(
            "Opslaan afnemersindicatie met id {}, anr {} en resultaat {}.",
            initVullingAfnemersindicatie.getPlId(),
            initVullingAfnemersindicatie.getAdministratienummer(),
            initVullingAfnemersindicatie.getConversieResultaat());
        final InitVullingAfnemersindicatie opgeslagenInitVullingAfnemersindicatie = em.merge(initVullingAfnemersindicatie);
        if (opgeslagenInitVullingAfnemersindicatie.getStapels() != null) {
            LOG.info("Stapels om op te slaan: {}", opgeslagenInitVullingAfnemersindicatie.getStapels().size());
            for (final InitVullingAfnemersindicatieStapel stapel : opgeslagenInitVullingAfnemersindicatie.getStapels()) {
                LOG.info("Opgeslagen waarde van stapel-{}: {}", stapel.getStapelPk().getStapelNr(), stapel.getConversieResultaat());
            }
        } else {
            LOG.info("Geen stapels om op te slaan");
        }
        LOG.info(
            "Afnemersindicatie met id {}, anr {} opgeslagen met resultaat {}.",
            opgeslagenInitVullingAfnemersindicatie.getPlId(),
            opgeslagenInitVullingAfnemersindicatie.getAdministratienummer(),
            opgeslagenInitVullingAfnemersindicatie.getConversieResultaat());
    }
}
