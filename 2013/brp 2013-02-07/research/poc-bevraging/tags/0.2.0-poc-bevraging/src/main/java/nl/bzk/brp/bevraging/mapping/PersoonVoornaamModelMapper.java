/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Volgnummer;
import nl.bzk.copy.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;

/**
 * De Class PersoonVoornaamModelMapper.
 */
public class PersoonVoornaamModelMapper extends AbstractRowMapper<PersoonVoornaamModel> {

    private final PersoonVoornaamStandaardGroepModelMapper voornaamStandaardGroepModelMapper;

    /**
     * Instantieert een nieuwe persoon voornaam model mapper.
     */
    public PersoonVoornaamModelMapper() {
        super();
        voornaamStandaardGroepModelMapper = new PersoonVoornaamStandaardGroepModelMapper();
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public PersoonVoornaamModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonVoornaamModel model = new PersoonVoornaamModel();

        model.setId(rs.getInt("id"));
        model.setGegevens(voornaamStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setStatusHistorie(status(rs, "persvoornaamstatushis"));
        model.setVolgnummer(new Volgnummer(integerValue(rs, "volgnr")));

        return model;
    }

    private StatusHistorie status(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? StatusHistorie.valueOf(rs.getString(column)) : null;
    }

}
