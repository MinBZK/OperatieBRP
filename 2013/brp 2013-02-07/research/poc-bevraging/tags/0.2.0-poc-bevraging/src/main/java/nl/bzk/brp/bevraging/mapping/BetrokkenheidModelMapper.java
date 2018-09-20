/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


public class BetrokkenheidModelMapper extends AbstractRowMapper<BetrokkenheidModel> {

    private final SoortBetrokkenheid[]                        soortBetrokkenheden = SoortBetrokkenheid.values();

    private final BetrokkenheidOuderlijkGezagGroepModelMapper betrokkenheidOuderlijkGezagGroepModelMapper;
    private final BetrokkenheidOuderschapGroepModelMapper     betrokkenheidOuderschapGroepModelMapper;
    private final RelatieModelMapper                          relatieModelMapper;

    private Boolean mapRelatie = false;

    public BetrokkenheidModelMapper(Boolean mapRelatie) {
        super();
        this.mapRelatie = mapRelatie;
        betrokkenheidOuderlijkGezagGroepModelMapper = new BetrokkenheidOuderlijkGezagGroepModelMapper();
        betrokkenheidOuderschapGroepModelMapper = new BetrokkenheidOuderschapGroepModelMapper();
        relatieModelMapper = new RelatieModelMapper("relatie_");
    }

    public BetrokkenheidModelMapper() {
        this(true);
    }

    @Override
    public BetrokkenheidModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        BetrokkenheidModel model = new BetrokkenheidModel();

        model.setId(integerValue(rs, "id"));
        model.setRol(rol(rs, "rol"));
        model.setBetrokkenheidOuderlijkGezag(betrokkenheidOuderlijkGezagGroepModelMapper.mapRow(rs, rowNum));
        model.setBetrokkenheidOuderschap(betrokkenheidOuderschapGroepModelMapper.mapRow(rs, rowNum));
        model.setOuderlijkGezagStatusHistorie(historie(rs, "OuderlijkgezagstatusHis"));
        model.setOuderschapStatusHistorie(historie(rs, "Ouderschapstatushis"));

        if (this.mapRelatie && rs.getObject("relatie") != null) {
            model.setRelatie(relatieModelMapper.mapRow(rs, rowNum));
        }


        return model;
    }

    private StatusHistorie historie(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }

    private SoortBetrokkenheid rol(final ResultSet rs, final String column) throws SQLException {
        return rs.getObject(column) != null ? soortBetrokkenheden[integerValue(rs, column)] : null;
    }
}
