/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.groep.operationeel.actueel.BetrokkenheidOuderlijkGezagGroepModel;


/**
 * De Class BetrokkenheidOuderlijkGezagGroepModelMapper.
 */
public class BetrokkenheidOuderlijkGezagGroepModelMapper extends
        AbstractRowMapper<BetrokkenheidOuderlijkGezagGroepModel>
{

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public BetrokkenheidOuderlijkGezagGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        BetrokkenheidOuderlijkGezagGroepModel model = null;

        if (jaNee(rs, "indouderheeftgezag") != null) {
            model = new BetrokkenheidOuderlijkGezagGroepModel();
            model.setIndOuderlijkGezag(jaNee(rs, "indouderheeftgezag"));
        }
        return model;
    }
}
