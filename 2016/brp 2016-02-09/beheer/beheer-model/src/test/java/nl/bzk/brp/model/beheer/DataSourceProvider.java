/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer;

import javax.sql.DataSource;

/**
 * Tagging interface om te vertellen dat instanties een datasource beschikbaar stellen.
 */
public interface DataSourceProvider {
    /**
     * Geeft een DataSource.
     *
     * @return datasource instantie
     */
    DataSource getDataSource();
}
