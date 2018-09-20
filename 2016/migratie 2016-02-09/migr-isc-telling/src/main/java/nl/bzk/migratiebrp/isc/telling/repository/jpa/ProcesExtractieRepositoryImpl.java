/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesExtractieRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Proces Extractie Repository.
 */
@Repository
public final class ProcesExtractieRepositoryImpl extends AbstractJpaRepository implements ProcesExtractieRepository {

    private static final String TELLING_TRANSACTION_MANAGER = "tellingTransactionManager";

    private static final String PARAMETER_TIJDSTIP = "tijdstip";
    private static final String PARAMETER_TE_UPDATE_IDS = "teUpdatenIds";
    private static final String SELECT_DEEL_TIJDSTIP = "SELECT pe FROM ProcesExtractie pe WHERE pe.startDatum <= :tijdstip ";
    private static final String COUNT_QUERY_GESTARTE_PROCESSEN = "SELECT count(*) FROM ProcesExtractie pe "
                                                                 + "WHERE pe.startDatum < :tijdstip AND (pe.indicatieGestartGeteld = false "
                                                                 + "OR pe.indicatieGestartGeteld is null)";
    private static final String COUNT_QUERY_BEEINDIGDE_PROCESSEN =
            "SELECT count(*) FROM ProcesExtractie pe WHERE pe.startDatum < :tijdstip AND pe.eindDatum is not null "
                    + "AND (pe.indicatieBeeindigdGeteld = false OR pe.indicatieBeeindigdGeteld is null)";

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public ProcesExtractie save(final ProcesExtractie procesExtractie) {
        if (procesExtractie.getProcesnaam() == null) {
            em.persist(procesExtractie);
            em.refresh(procesExtractie);
            return procesExtractie;
        } else {
            return em.merge(procesExtractie);
        }
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Long telInTellingTeVerwerkenGestarteProcessen(final Timestamp datumTot) {

        final Query countQuery = em.createQuery(COUNT_QUERY_GESTARTE_PROCESSEN);
        countQuery.setParameter(PARAMETER_TIJDSTIP, datumTot);

        return (Long) countQuery.getSingleResult();
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Long telInTellingTeVerwerkenBeeindigdeProcessen(final Timestamp datumTot) {

        final Query countQuery = em.createQuery(COUNT_QUERY_BEEINDIGDE_PROCESSEN);
        countQuery.setParameter(PARAMETER_TIJDSTIP, datumTot);

        return (Long) countQuery.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public List<ProcesExtractie> selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(final Timestamp datumTot, final Integer limit) {
        if (datumTot != null) {
            final Query selectQuery =
                    em.createQuery(SELECT_DEEL_TIJDSTIP
                                   + " AND pe.eindDatum is not null "
                                   + "AND (pe.indicatieBeeindigdGeteld = false or pe.indicatieBeeindigdGeteld is null)");

            if (limit != null) {
                selectQuery.setMaxResults(limit);
            }
            selectQuery.setParameter(PARAMETER_TIJDSTIP, datumTot);

            return selectQuery.getResultList();
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public List<ProcesExtractie> selecteerInTellingTeVerwerkenGestarteProcesInstanties(final Timestamp datumTot, final Integer limit) {
        if (datumTot != null) {
            final Query selectQuery =
                    em.createQuery(SELECT_DEEL_TIJDSTIP + "AND (pe.indicatieGestartGeteld = false or pe.indicatieGestartGeteld is null)");

            if (limit != null) {
                selectQuery.setMaxResults(limit);
            }
            selectQuery.setParameter(PARAMETER_TIJDSTIP, datumTot);

            return selectQuery.getResultList();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public boolean updateIndicatieGestartGeteldProcesExtracties(final List<Long> teUpdatenIds) {
        if (teUpdatenIds != null && teUpdatenIds.size() > 0) {
            int beginIndex = 0;
            final int maxIndex = teUpdatenIds.size();
            int aantalGeupdate = 0;

            do {

                final int eindIndex = beginIndex + MAX_BATCH_SIZE <= maxIndex ? beginIndex + MAX_BATCH_SIZE : maxIndex;
                final List<Long> subLijstTeUpdatenIds = teUpdatenIds.subList(beginIndex, eindIndex);

                final Query updateQuery =
                        em.createQuery("UPDATE ProcesExtractie pe SET pe.indicatieGestartGeteld = true WHERE "
                                       + "pe.procesInstantieId IN (:teUpdatenIds) ");
                updateQuery.setParameter(PARAMETER_TE_UPDATE_IDS, subLijstTeUpdatenIds);

                aantalGeupdate += updateQuery.executeUpdate();
                beginIndex = eindIndex;
            } while (beginIndex < maxIndex);
            return aantalGeupdate == teUpdatenIds.size();
        } else {
            return true;
        }
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public boolean updateIndicatieBeeindigdGeteldProcesExtracties(final List<Long> teUpdatenIds) {
        if (teUpdatenIds != null && teUpdatenIds.size() > 0) {

            int beginIndex = 0;
            final int maxIndex = teUpdatenIds.size();
            int aantalGeupdate = 0;

            do {

                final int eindIndex = beginIndex + MAX_BATCH_SIZE <= maxIndex ? beginIndex + MAX_BATCH_SIZE : maxIndex;
                final List<Long> subLijstTeUpdatenIds = teUpdatenIds.subList(beginIndex, eindIndex);

                final Query updateQuery =
                        em.createQuery("UPDATE ProcesExtractie pe SET pe.indicatieBeeindigdGeteld = true "
                                       + "WHERE pe.procesInstantieId IN (:teUpdatenIds) ");
                updateQuery.setParameter(PARAMETER_TE_UPDATE_IDS, subLijstTeUpdatenIds);

                aantalGeupdate += updateQuery.executeUpdate();
                beginIndex = eindIndex;
            } while (beginIndex < maxIndex);
            return aantalGeupdate == teUpdatenIds.size();
        } else {
            return true;
        }
    }
}
