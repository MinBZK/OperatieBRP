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
import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonGeboorteGroepModel;

/**
 *
 */
public class PersoonGeboorteGroepModelMapper extends AbstractRowMapper<PersoonGeboorteGroepModel> {
    private final PartijMapper partijMapper;
    private final PlaatsMapper plaatsMapper;
    private final LandMapper landMapper;

    public PersoonGeboorteGroepModelMapper() {
        super();
        partijMapper = new PartijMapper("partij_geboorte_");
        plaatsMapper = new PlaatsMapper("plaats_geboorte_");
        landMapper = new LandMapper("land_geboorte_");
    }

    @Override
    public PersoonGeboorteGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonGeboorteGroepModel model = new PersoonGeboorteGroepModel();

        model.setDatumGeboorte(datumValue(rs, "datgeboorte"));
        model.setGemeenteGeboorte(partijMapper.mapRow(rs, rowNum));
        model.setWoonplaatsGeboorte(plaatsMapper.mapRow(rs, rowNum));
        model.setBuitenlandseGeboortePlaats(buitenlandsePlaats(rs, "blgeboorteplaats"));
        model.setBuitenlandseRegioGeboorte(buitenlandseRegio(rs, "blregiogeboorte"));
        model.setLandGeboorte(landMapper.mapRow(rs, rowNum));
        model.setOmschrijvingGeboorteLocatie(omschrijving(rs, "omsgeboorteloc"));

        return model;
    }

    private BuitenlandsePlaats buitenlandsePlaats(ResultSet rs, String column) throws SQLException {
        return rs.getString(column) != null ? new BuitenlandsePlaats(rs.getString(column)) : null;
    }

    private BuitenlandseRegio buitenlandseRegio(ResultSet rs, String column) throws SQLException {
        return rs.getString(column) != null ? new BuitenlandseRegio(rs.getString(column)) : null;
    }

    private Omschrijving omschrijving(ResultSet rs, String column) throws SQLException {
        return rs.getString(column) != null ? new Omschrijving(rs.getString(column)) : null;
    }
}
