/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonNationaliteitStandaardGroepModel;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public class PersoonNationaliteitStandaardGroepModelMapper
        implements RowMapper<PersoonNationaliteitStandaardGroepModel>
{

    private RedenVerkrijgingNLNationaliteitMapper redenVerkrijgingNLNationaliteitMapper;
    private RedenVerliesNLNationaliteitMapper redenVerliesNLNationaliteitMapper;

    public PersoonNationaliteitStandaardGroepModelMapper() {
        redenVerkrijgingNLNationaliteitMapper = new RedenVerkrijgingNLNationaliteitMapper("verkrijgen_");
        redenVerliesNLNationaliteitMapper = new RedenVerliesNLNationaliteitMapper("verlies_");

    }

    @Override
    public PersoonNationaliteitStandaardGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonNationaliteitStandaardGroepModel model = new PersoonNationaliteitStandaardGroepModel();

        model.setRedenVerkregenNlNationaliteit(redenVerkrijgingNLNationaliteitMapper.mapRow(rs, rowNum));
        model.setRedenVerliesNlNationaliteit(redenVerliesNLNationaliteitMapper.mapRow(rs, rowNum));

        return model;
    }
}
