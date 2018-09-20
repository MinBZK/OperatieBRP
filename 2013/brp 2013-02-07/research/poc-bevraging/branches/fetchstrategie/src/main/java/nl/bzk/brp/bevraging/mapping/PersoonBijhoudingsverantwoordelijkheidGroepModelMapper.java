/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonBijhoudingsverantwoordelijkheidGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verantwoordelijke;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public class PersoonBijhoudingsverantwoordelijkheidGroepModelMapper
        implements RowMapper<PersoonBijhoudingsverantwoordelijkheidGroepModel>
{
    private Verantwoordelijke[] values;

    public PersoonBijhoudingsverantwoordelijkheidGroepModelMapper() {
        values = Verantwoordelijke.values();
    }

    @Override
    public PersoonBijhoudingsverantwoordelijkheidGroepModel mapRow(final ResultSet rs, final int rowNum)
            throws SQLException
    {
        Verantwoordelijke verantw = verantwoordelijke(rs, "verantwoordelijke");
        PersoonBijhoudingsverantwoordelijkheidGroepModel model = null;

        if (verantw != null) {
            model = new PersoonBijhoudingsverantwoordelijkheidGroepModel();

            model.setVerantwoordelijke(verantwoordelijke(rs, "verantwoordelijke"));
        }

        return model;
    }

    private Verantwoordelijke verantwoordelijke(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (values[rs.getInt(column)]) : null;
    }
}
