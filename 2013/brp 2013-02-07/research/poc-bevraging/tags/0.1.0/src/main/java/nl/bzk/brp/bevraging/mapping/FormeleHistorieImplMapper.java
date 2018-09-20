/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.basis.FormeleHistorieImpl;

public class FormeleHistorieImplMapper extends AbstractRowMapper<FormeleHistorieImpl> {
    @Override
    public FormeleHistorieImpl mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final FormeleHistorieImpl formeleHistorie = new FormeleHistorieImpl();
        mapRow(formeleHistorie, rs, rowNum);
        return formeleHistorie;
    }

    public void mapRow(FormeleHistorieImpl formeleHistorie, final ResultSet rs, final int rowNum) throws SQLException {
        formeleHistorie.setDatumTijdRegistratie(datumTijdValue(rs, "tsreg"));
        formeleHistorie.setDatumTijdVerval(datumTijdValue(rs, "tsverval"));
        formeleHistorie.setActieInhoud(new ActieModelMapper(prefix + "actieinh_").mapRow(rs, rowNum));
        formeleHistorie.setActieInhoud(new ActieModelMapper(prefix + "actieverval_").mapRow(rs, rowNum));
    }

}
