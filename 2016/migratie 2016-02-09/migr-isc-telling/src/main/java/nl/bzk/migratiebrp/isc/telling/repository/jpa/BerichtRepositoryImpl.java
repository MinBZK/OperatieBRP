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
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Bericht Repository.
 */
@Repository
public final class BerichtRepositoryImpl extends AbstractJpaRepository implements BerichtRepository {

    private static final String TELLING_TRANSACTION_MANAGER = "tellingTransactionManager";
    private static final String PARAMETER_DATUM_TOT = "datumTot";
    // private static final String PARAMETER_PROCES_INSTANTIE_LIJST = "teVerwijderenProcesInstantiesLijst";
    private static final String PARAMETER_TE_UPDATE_IDS = "teUpdatenIds";
    private static final String SELECT_DEEL_DATUM = "SELECT b FROM Bericht b WHERE b.kanaal is not null AND " + "b.tijdstip < (:datumTot) ";
    private static final String COUNT_QUERY_BERICHTEN = "SELECT count(*) FROM Bericht b WHERE "
                                                        + "b.kanaal is not null AND b.tijdstip < (:datumTot) AND (b.indicatieGeteld = false OR "
                                                        + "b.indicatieGeteld is null)";

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Bericht save(final Bericht bericht) {
        if (bericht.getId() == null) {
            em.persist(bericht);
            em.refresh(bericht);
            return bericht;
        } else {
            return em.merge(bericht);
        }
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Long telInTellingTeVerwerkenBerichten(final Timestamp datumTot) {

        final Query countQuery = em.createQuery(COUNT_QUERY_BERICHTEN);
        countQuery.setParameter(PARAMETER_DATUM_TOT, datumTot);

        return (Long) countQuery.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public List<Bericht> selecteerInTellingTeVerwerkenBerichten(final Timestamp datumTot, final Integer limit) {
        final Query selectQuery = em.createQuery(SELECT_DEEL_DATUM + "AND (b.indicatieGeteld = false OR b.indicatieGeteld is null)");

        if (limit != null) {
            selectQuery.setMaxResults(limit);
        }
        selectQuery.setParameter(PARAMETER_DATUM_TOT, datumTot);

        return selectQuery.getResultList();
    }

    @Override
    @Transactional(value = TELLING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public boolean updateIndicatieGeteldBerichten(final List<Long> teUpdatenIds) {
        if (teUpdatenIds != null && teUpdatenIds.size() > 0) {
            int beginIndex = 0;
            final int maxIndex = teUpdatenIds.size();
            int aantalGeupdate = 0;

            do {

                final int eindIndex = beginIndex + MAX_BATCH_SIZE <= maxIndex ? beginIndex + MAX_BATCH_SIZE : maxIndex;
                final List<Long> subLijstTeUpdatenIds = teUpdatenIds.subList(beginIndex, eindIndex);

                final Query updateQuery = em.createQuery("UPDATE Bericht b SET b.indicatieGeteld = true WHERE b.id IN " + "(:teUpdatenIds) ");
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
