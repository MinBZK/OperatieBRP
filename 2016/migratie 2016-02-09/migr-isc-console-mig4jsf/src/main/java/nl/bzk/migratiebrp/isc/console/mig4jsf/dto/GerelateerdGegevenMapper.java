/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gerelateerd gegeven mapper.
 */
public final class GerelateerdGegevenMapper {

    /**
     * Map een results et naar een gerelateerd gegeven.
     *
     * @param rs
     *            result set
     * @return bericht
     * @throws SQLException
     *             bij sql fouten
     */
    public GerelateerdGegeven map(final ResultSet rs) throws SQLException {
        final GerelateerdGegeven gerelateerdGegeven = new GerelateerdGegeven();

        gerelateerdGegeven.setSoort(rs.getString(1));
        gerelateerdGegeven.setGegeven(rs.getString(2));

        return gerelateerdGegeven;
    }
}
