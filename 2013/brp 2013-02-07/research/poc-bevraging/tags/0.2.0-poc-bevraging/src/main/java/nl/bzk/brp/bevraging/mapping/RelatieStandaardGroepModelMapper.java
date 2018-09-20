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
import nl.bzk.copy.model.groep.operationeel.actueel.RelatieStandaardGroepModel;

public class RelatieStandaardGroepModelMapper extends AbstractRowMapper<RelatieStandaardGroepModel> {

    private final LandMapper landAanvangMapper;
    private final LandMapper landEindeMapper;
    private final PartijMapper partijAanvangMapper;
    private final PartijMapper partijEindeMapper;
    private final RedenBeeindigingRelatieMapper redenBeeindigingRelatieMapper;
    private final PlaatsMapper plaatsAanvangMapper;
    private final PlaatsMapper plaatsEindeMapper;

    public RelatieStandaardGroepModelMapper(final String prefix) {
        super(prefix);

        landAanvangMapper = new LandMapper("land_aanvang_");
        landEindeMapper = new LandMapper("land_einde_");
        partijAanvangMapper = new PartijMapper("partij_aanvang_");
        partijEindeMapper = new PartijMapper("partij_einde_");
        redenBeeindigingRelatieMapper = new RedenBeeindigingRelatieMapper("rdneinde_relatie_");
        plaatsAanvangMapper = new PlaatsMapper("plaats_aanvang_");
        plaatsEindeMapper = new PlaatsMapper("plaats_einde_");
    }

    @Override
    public RelatieStandaardGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RelatieStandaardGroepModel model = null;

        if (datumValue(rs, "dataanv") != null) {
            model = new RelatieStandaardGroepModel();
            model.setDatumAanvang(datumValue(rs, "dataanv"));
            model.setDatumEinde(datumValue(rs, "dateinde"));
            model.setLandAanvang(landAanvangMapper.mapRow(rs, rowNum));
            model.setLandEinde(landEindeMapper.mapRow(rs, rowNum));
            model.setGemeenteAanvang(partijAanvangMapper.mapRow(rs, rowNum));
            model.setGemeenteEinde(partijEindeMapper.mapRow(rs, rowNum));
            model.setRedenBeeindigingRelatie(redenBeeindigingRelatieMapper.mapRow(rs, rowNum));
            model.setOmschrijvingLocatieAanvang(new Omschrijving(stringValue(rs, "omslocaanv")));
            model.setOmschrijvingLocatieEinde(new Omschrijving(stringValue(rs, "omsloceinde")));
            model.setWoonPlaatsAanvang(plaatsAanvangMapper.mapRow(rs, rowNum));
            model.setWoonPlaatsEinde(plaatsEindeMapper.mapRow(rs, rowNum));
            model.setBuitenlandsePlaatsAanvang(new BuitenlandsePlaats(stringValue(rs, "blplaatsaanv")));
            model.setBuitenlandsePlaatsEinde(new BuitenlandsePlaats(stringValue(rs, "blplaatseinde")));
            model.setBuitenlandseRegioAanvang(new BuitenlandseRegio(stringValue(rs, "blregioaanv")));
            model.setBuitenlandseRegioEinde(new BuitenlandseRegio(stringValue(rs, "blregioeinde")));
        }

        return model;
    }

}
