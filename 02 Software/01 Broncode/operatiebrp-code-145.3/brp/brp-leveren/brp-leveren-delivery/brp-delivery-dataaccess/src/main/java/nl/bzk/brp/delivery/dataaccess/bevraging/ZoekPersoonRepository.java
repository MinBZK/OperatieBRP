/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.sql.SQLException;
import java.util.List;
import nl.bzk.brp.service.dalapi.QueryCancelledException;

/**
 * ZoekPersoonRepository.
 */
public interface ZoekPersoonRepository {

    /**
     * @param sql sql
     * @param postgres postgres
     * @return lijst met persoon id's
     * @throws QueryCancelledException query cancelled
     */
    List<Long> zoekPersonen(SqlStamementZoekPersoon sql, boolean postgres) throws QueryCancelledException;

    /**
     * @param sql sql
     * @return het query plan als json string
     */
    String bepaalQueryPlan(SqlStamementZoekPersoon sql);

    /**
     * Is de db postgres.
     * @return is postgres
     * @throws SQLException sql fout bij bepalen db.
     */
    boolean isPostgres() throws SQLException;
}
