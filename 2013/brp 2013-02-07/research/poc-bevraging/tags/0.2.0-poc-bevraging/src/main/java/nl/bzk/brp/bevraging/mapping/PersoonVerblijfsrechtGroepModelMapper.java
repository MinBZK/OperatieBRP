/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonVerblijfsrechtGroepModel;

/**
 */
public class PersoonVerblijfsrechtGroepModelMapper extends AbstractRowMapper<PersoonVerblijfsrechtGroepModel> {

    private final VerblijfsrechtMapper verblijfsrechtMapper;

    public PersoonVerblijfsrechtGroepModelMapper() {
        verblijfsrechtMapper = new VerblijfsrechtMapper("verblijfsrecht_");
    }

    @Override
    public PersoonVerblijfsrechtGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonVerblijfsrechtGroepModel model = new PersoonVerblijfsrechtGroepModel();

        model.setDatumAanvangAaneensluitendVerblijfsrecht(datumValue(rs, "DatAanvAaneenslVerblijfsr"));
        model.setDatumAanvangVerblijfsrecht(datumValue(rs, "DatAanvVerblijfsr"));
        model.setDatumVoorzienEindeVerblijfsrecht(datumValue(rs, "DatVoorzEindeVerblijfsr"));

        model.setVerblijfsrecht(verblijfsrechtMapper.mapRow(rs, rowNum));

        return model;
    }

}
