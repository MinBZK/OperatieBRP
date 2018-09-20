/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Volgnummer;
import nl.bzk.copy.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * De Class PersoonGeslachtsnaamcomponentGroepModelMapper.
 */
public class PersoonGeslachtsnaamcomponentGroepModelMapper
        extends AbstractRowMapper<PersoonGeslachtsnaamcomponentModel>
{

    private final PersoonGeslachtsnaamcomponentStandaardGroepModelMapper geslachtsnaamStandaardGroepModelMapper;

    /**
     * Instantieert een nieuwe persoon geslachtsnaamcomponent groep model mapper.
     */
    public PersoonGeslachtsnaamcomponentGroepModelMapper() {
        geslachtsnaamStandaardGroepModelMapper = new PersoonGeslachtsnaamcomponentStandaardGroepModelMapper();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public PersoonGeslachtsnaamcomponentModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonGeslachtsnaamcomponentModel model = new PersoonGeslachtsnaamcomponentModel();

        model.setId(rs.getInt("id"));
        model.setGeslachtsnaamcomponent(geslachtsnaamStandaardGroepModelMapper.mapRow(rs, rowNum));
        model.setStatusHistorie(historie(rs, "PersGeslnaamcompStatusHis"));
        model.setVolgnummer(new Volgnummer(integerValue(rs, "volgnr")));

        return model;
    }

    private StatusHistorie historie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }
}
