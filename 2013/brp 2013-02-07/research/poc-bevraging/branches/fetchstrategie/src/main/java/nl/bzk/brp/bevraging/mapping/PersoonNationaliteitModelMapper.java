/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;

/**
 */
public class PersoonNationaliteitModelMapper extends AbstractRowMapper<PersoonNationaliteitModel> {

    private PersoonNationaliteitStandaardGroepModelMapper persoonNationaliteitStandaardGroepModelMapper;
    private NationaliteitMapper nationaliteitMapper;

    public PersoonNationaliteitModelMapper() {
        this.persoonNationaliteitStandaardGroepModelMapper = new PersoonNationaliteitStandaardGroepModelMapper();
        this.nationaliteitMapper = new NationaliteitMapper("nation_");
    }

    @Override
    public PersoonNationaliteitModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonNationaliteitModel model = new PersoonNationaliteitModel();

        model.setStatusHistorie(historie(rs, "persnationstatushis"));
        model.setGegevens(persoonNationaliteitStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setNationaliteit(nationaliteitMapper.mapRow(rs, rowNum));
        model.setId(integerValue(rs, "id"));

        return model;
    }

    private StatusHistorie historie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }
}
