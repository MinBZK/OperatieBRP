/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.ActieModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortActie;

public class ActieModelMapper extends AbstractRowMapper<ActieModel> {
    SoortActie[] acties = SoortActie.values();

    public ActieModelMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public ActieModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final ActieModel actie = new ActieModel();

        long id = longValue(rs, "id");
        if (id != -1) {
            actie.setPartij(new PartijMapper(prefix+"partij_").mapRow(rs, rowNum));
//            actie.setVerdrag(new VerdragMapper(prefix).mapRow(rs, rowNum));
            actie.setTijdstipOntlening(datumTijdValue(rs, "tijdstipontlening"));
            actie.setTijdstipRegistratie(datumTijdValue(rs, "tijdstipreg"));
            actie.setSoort(soortActie(rs, "srt"));
        }
        return actie;
    }

    private SoortActie soortActie(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column) != null ? acties[integerValue(rs, column)] : null;
    }

}
