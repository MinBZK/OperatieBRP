/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonskaartGroepModel;

/**
 */
public class PersoonskaartGroepModelMapper extends AbstractRowMapper<PersoonskaartGroepModel> {
    private PartijMapper partijMapper;

    public PersoonskaartGroepModelMapper() {
        partijMapper = new PartijMapper("partij_persoonskaart_");
    }

    @Override
    public PersoonskaartGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonskaartGroepModel model = null;

        if(jaNee(rs, "indpkvollediggeconv") != null) {
            model = new PersoonskaartGroepModel();
            if (rs.getObject("partij_persoonskaart_id") != null) {
                model.setGemeentePersoonskaart(partijMapper.mapRow(rs, rowNum));
            }
            model.setIndicatiePersoonskaartVolledigGeconverteerd(jaNee(rs, "indpkvollediggeconv"));
        }

        return model;
    }

}
