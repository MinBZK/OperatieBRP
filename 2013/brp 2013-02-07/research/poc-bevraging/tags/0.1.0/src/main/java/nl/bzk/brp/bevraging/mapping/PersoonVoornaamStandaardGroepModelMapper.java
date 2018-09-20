/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonVoornaamStandaardGroepModel;
import org.springframework.jdbc.core.RowMapper;

/**
 * De Class PersoonVoornaamStandaardGroepModelMapper.
 */
public class PersoonVoornaamStandaardGroepModelMapper implements RowMapper<PersoonVoornaamStandaardGroepModel> {

    /**
     * Instantieert een nieuwe persoon voornaam standaard groep model mapper.
     */
    public PersoonVoornaamStandaardGroepModelMapper() {

    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public PersoonVoornaamStandaardGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonVoornaamStandaardGroepModel model = new PersoonVoornaamStandaardGroepModel();
        model.setVoornaam(new Voornaam(rs.getString("naam")));

        return model;
    }
}
