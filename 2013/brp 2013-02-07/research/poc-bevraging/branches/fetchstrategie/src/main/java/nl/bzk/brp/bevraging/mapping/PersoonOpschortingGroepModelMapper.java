/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonOpschortingGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenOpschorting;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public class PersoonOpschortingGroepModelMapper implements RowMapper<PersoonOpschortingGroepModel> {
    private RedenOpschorting[] redenOpschortingen = RedenOpschorting.values();

    @Override
    public PersoonOpschortingGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RedenOpschorting reden = reden(rs, "rdnOpschortingBijhouding");
        PersoonOpschortingGroepModel model = null;

        if (reden != null) {
            model = new PersoonOpschortingGroepModel();

            model.setRedenOpschorting(reden);
        }
        return model;
    }

    private RedenOpschorting reden(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (redenOpschortingen[rs.getInt(column)]) : null;
    }
}
