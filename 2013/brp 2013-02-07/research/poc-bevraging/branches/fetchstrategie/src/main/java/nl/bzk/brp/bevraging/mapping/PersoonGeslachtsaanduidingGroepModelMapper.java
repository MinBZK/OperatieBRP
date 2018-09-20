/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 */
public class PersoonGeslachtsaanduidingGroepModelMapper implements RowMapper<PersoonGeslachtsaanduidingGroepModel> {

    private Geslachtsaanduiding[] geslachtsaanduidingen = Geslachtsaanduiding.values();

    public PersoonGeslachtsaanduidingGroepModelMapper() {

    }

    @Override
    public PersoonGeslachtsaanduidingGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonGeslachtsaanduidingGroepModel model = new PersoonGeslachtsaanduidingGroepModel();
        model.setGeslachtsaanduiding(geslacht(rs, "geslachtsaand"));

        return model;
    }

    private Geslachtsaanduiding geslacht(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (geslachtsaanduidingen[rs.getInt(column)]) : null;
    }

}
