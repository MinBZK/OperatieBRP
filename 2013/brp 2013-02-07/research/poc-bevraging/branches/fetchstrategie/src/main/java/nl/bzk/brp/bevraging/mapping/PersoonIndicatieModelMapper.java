/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;

/**
 */
public class PersoonIndicatieModelMapper extends AbstractRowMapper<PersoonIndicatieModel> {

    private PersoonIndicatieStandaardGroepModelMapper persoonIndicatieStandaardGroepModelMapper;

    public PersoonIndicatieModelMapper() {
        persoonIndicatieStandaardGroepModelMapper = new PersoonIndicatieStandaardGroepModelMapper();
    }

    @Override
    public PersoonIndicatieModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonIndicatieModel model = new PersoonIndicatieModel();

        model.setId(integerValue(rs, "id"));
        model.setGegevens(persoonIndicatieStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setStatusHistorie(historie(rs, "persindicatiestatushis"));

        return model;
    }

    private StatusHistorie historie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }
}
