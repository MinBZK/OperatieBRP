/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import org.springframework.stereotype.Repository;


/**
 * Standaard repository implementatie van de {@link PersoonOpschortingHistorieRepository} class.
 */
@Repository
public class PersoonOpschortingHistorieJpaRepository implements PersoonOpschortingHistorieRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Integer haalOpActueleDatumOpschorting(final Long  persId) {
        StringBuilder query =
            new StringBuilder(
                    "SELECT max(hisOpschorting.datumAanvangGeldigheid) FROM HisPersoonOpschorting hisOpschorting ");
        query.append("WHERE hisOpschorting.datumTijdVerval is null ");
        query.append("AND hisOpschorting.persoon.id = :persoonId");

        Query tQuery = em.createQuery(query.toString());
        tQuery.setParameter("persoonId", persId);

        Integer datumOpschorting;
        try {
            datumOpschorting = (Integer) tQuery.getSingleResult();
        } catch (NoResultException e) {
            datumOpschorting = null;
        }

        return datumOpschorting;
    }

}
