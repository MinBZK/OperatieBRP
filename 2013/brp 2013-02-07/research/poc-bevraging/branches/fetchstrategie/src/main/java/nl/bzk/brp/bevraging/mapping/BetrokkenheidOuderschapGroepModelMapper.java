/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Ja;
import nl.bzk.copy.model.groep.operationeel.actueel.BetrokkenheidOuderschapGroepModel;


/**
 * De Class BetrokkenheidOuderschapGroepModelMapper.
 */
public class BetrokkenheidOuderschapGroepModelMapper extends AbstractRowMapper<BetrokkenheidOuderschapGroepModel> {

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public BetrokkenheidOuderschapGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        BetrokkenheidOuderschapGroepModel model = null;

        if (ja(rs, "indOuder") != null) {
            model = new BetrokkenheidOuderschapGroepModel();
            model.setIndOuder(ja(rs, "indOuder"));
//            model.setDatumAanvangOuderschap(integerValue(rs, "datumAanvangOuderschap"));
//            model.setDatumEindeGeldigheid(integerValue(rs, "datumEindeGeldigheid"));
        }
        return model;
    }

    /**
     * Ja.
     *
     * @param rs     de rs
     * @param column de column
     * @return de ja
     * @throws SQLException de sQL exception
     */
    private Ja ja(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column) != null ? Ja.Ja : null;
    }
}
