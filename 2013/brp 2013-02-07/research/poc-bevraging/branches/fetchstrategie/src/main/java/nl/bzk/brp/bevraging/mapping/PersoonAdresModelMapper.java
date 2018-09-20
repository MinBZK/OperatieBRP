/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public class PersoonAdresModelMapper implements RowMapper<PersoonAdresModel> {

    private PersoonAdresStandaardGroepModelMapper persoonAdresStandaardGroepModelMapper;

    public PersoonAdresModelMapper() {
        this.persoonAdresStandaardGroepModelMapper = new PersoonAdresStandaardGroepModelMapper();
    }

    @Override
    public PersoonAdresModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonAdresModel model = new PersoonAdresModel();

        model.setId(rs.getInt("id"));
        model.setGegevens(persoonAdresStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setStatusHistorie(historie(rs, "PersAdresStatusHis"));

        return model;
    }

    private StatusHistorie historie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }
}
