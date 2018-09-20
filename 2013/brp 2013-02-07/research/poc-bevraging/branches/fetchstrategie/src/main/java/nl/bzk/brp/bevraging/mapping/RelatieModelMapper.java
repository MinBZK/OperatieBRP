/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.RelatieModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


public class RelatieModelMapper extends AbstractRowMapper<RelatieModel> {

    private final SoortRelatie[] soortRelaties = SoortRelatie.values();

    private final RelatieStandaardGroepModelMapper relatieStandaardGroepModelMapper;

    public RelatieModelMapper(final String prefix) {
    	super(prefix);
        relatieStandaardGroepModelMapper = new RelatieStandaardGroepModelMapper(prefix);
    }

    @Override
    public RelatieModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RelatieModel model = new RelatieModel();

        model.setId(integerValue(rs, "id"));
        model.setGegevens(relatieStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setSoort(srt(rs, "srt"));
        model.setStatusHistorie(historie(rs, "relatiestatushis"));

        return model;
    }

    private SoortRelatie srt(final ResultSet rs, final String column) throws SQLException {
        String name = prefix + column;
        return rs.getObject(name) != null ? soortRelaties[rs.getInt(name)] : null;
    }

    private StatusHistorie historie(final ResultSet rs, final String column) throws SQLException {
        String name = prefix + column;
        return rs.getObject(name) != null ? (StatusHistorie.valueOf(rs.getString(name))) : null;
    }

}
