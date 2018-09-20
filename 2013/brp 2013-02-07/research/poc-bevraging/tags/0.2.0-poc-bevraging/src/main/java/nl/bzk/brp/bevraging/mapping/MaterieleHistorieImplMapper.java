/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.basis.MaterieleHistorieImpl;

public class MaterieleHistorieImplMapper extends AbstractRowMapper<MaterieleHistorieImpl> {
    @Override
    public MaterieleHistorieImpl mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        MaterieleHistorieImpl result = new MaterieleHistorieImpl();
        result.setDatumAanvangGeldigheid(datumValue(rs, "dataanvgel"));
        result.setDatumEindeGeldigheid(datumValue(rs, "dateindegel"));
        result.setActieAanpassingGeldigheid(new ActieModelMapper(prefix + "actieaanpgel_").mapRow(rs, rowNum));
        new FormeleHistorieImplMapper().mapRow(result, rs, rowNum);
        return result;
    }

}
