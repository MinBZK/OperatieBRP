/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.copy.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.copy.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonOverlijdenGroepModel;

/**
 *
 */
public class PersoonOverlijdenGroepModelMapper extends AbstractRowMapper<PersoonOverlijdenGroepModel> {
    private final PartijMapper partijMapper;
    private final PlaatsMapper plaatsMapper;
    private final LandMapper landMapper;

    public PersoonOverlijdenGroepModelMapper() {
        super();
        partijMapper = new PartijMapper("partij_overlijden_");
        plaatsMapper = new PlaatsMapper("plaats_overlijden_");
        landMapper = new LandMapper("land_overlijden_");
    }

    @Override
    public PersoonOverlijdenGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonOverlijdenGroepModel model = new PersoonOverlijdenGroepModel();

        model.setDatumOverlijden(datumValue(rs, "DatOverlijden"));
        model.setOverlijdenGemeente(partijMapper.mapRow(rs, rowNum));
        model.setWoonplaatsOverlijden(plaatsMapper.mapRow(rs, rowNum));
        model.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaats(rs.getString("BlPlaatsOverlijden")));
        model.setBuitenlandseRegioOverlijden(new BuitenlandseRegio(rs.getString("BlRegioOverlijden")));
        model.setLandOverlijden(landMapper.mapRow(rs, rowNum));
        model.setOmschrijvingLocatieOverlijden(new LocatieOmschrijving(rs.getString("OmsLocOverlijden")));

        return model;
    }

}
