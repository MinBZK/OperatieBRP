/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonImmigratieGroepModel;

/**
 */
public class PersoonImmigratieGroepModelMapper extends AbstractRowMapper<PersoonImmigratieGroepModel> {
    private LandMapper landMapper;

    public PersoonImmigratieGroepModelMapper() {
        landMapper = new LandMapper("land_immigratie_");
    }

    @Override
    public PersoonImmigratieGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonImmigratieGroepModel model = null;

        if (datumValue(rs, "datvestiginginnederland") != null) {
            model = new PersoonImmigratieGroepModel();
            model.setDatumVestigingInNederland(datumValue(rs, "datvestiginginnederland"));
            model.setLandVanwaarGevestigd(landMapper.mapRow(rs, rowNum));
        }
        return model;
    }
}
