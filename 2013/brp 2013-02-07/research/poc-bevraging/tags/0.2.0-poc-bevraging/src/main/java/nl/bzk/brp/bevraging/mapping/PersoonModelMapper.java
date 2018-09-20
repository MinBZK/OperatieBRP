/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 */
public class PersoonModelMapper implements RowMapper<PersoonModel> {

    private final SoortPersoonMapper soortPersoonMapper;
    private final PersoonIdentificatienummersGroepModelMapper identificatienummersMapper;
    private final PersoonGeslachtsaanduidingGroepModelMapper geslachtsaanduidingMapper;
    private final PersoonSamengesteldeNaamGroepModelMapper samengesteldeNaamMapper;
    private final PersoonGeboorteGroepModelMapper geboorteMapper;
    private final PersoonAanschrijvingGroepModelMapper aanschrijvingMapper;
    private final PersoonOpschortingGroepModelMapper opschortingMapper;
    private final PersoonBijhoudingsgemeenteGroepModelMapper bijhoudingsgemeenteMapper;
    private final PersoonOverlijdenGroepModelMapper overlijdenMapper;
    private final PersoonInschrijvingGroepModelMapper inschrijvingMapper;
    private final PersoonBijhoudingsverantwoordelijkheidGroepModelMapper bijhoudingsverantwoordelijkheidMapper;
    private final PersoonVerblijfsrechtGroepModelMapper verblijfsrechtMapper;
    private final PersoonskaartGroepModelMapper persoonskaartMapper;
    private final PersoonEUVerkiezingenGroepModelMapper euVerkiezingenMapper;
    private final PersoonUitsluitingNLKiesrechtGroepModelMapper uitsluitingNLKiesrechtMapper;
    private final PersoonImmigratieGroepModelMapper immigratieMapper;
    private final PersoonAfgeleidAdministratiefGroepModelMapper afgeleidAdministratiefMapper;


    public PersoonModelMapper() {
        soortPersoonMapper = new SoortPersoonMapper();
        identificatienummersMapper = new PersoonIdentificatienummersGroepModelMapper();
        geslachtsaanduidingMapper = new PersoonGeslachtsaanduidingGroepModelMapper();
        samengesteldeNaamMapper = new PersoonSamengesteldeNaamGroepModelMapper();
        geboorteMapper = new PersoonGeboorteGroepModelMapper();
        aanschrijvingMapper = new PersoonAanschrijvingGroepModelMapper();
        opschortingMapper = new PersoonOpschortingGroepModelMapper();
        bijhoudingsgemeenteMapper = new PersoonBijhoudingsgemeenteGroepModelMapper();
        overlijdenMapper = new PersoonOverlijdenGroepModelMapper();
        inschrijvingMapper = new PersoonInschrijvingGroepModelMapper();
        bijhoudingsverantwoordelijkheidMapper = new PersoonBijhoudingsverantwoordelijkheidGroepModelMapper();
        verblijfsrechtMapper = new PersoonVerblijfsrechtGroepModelMapper();
        persoonskaartMapper = new PersoonskaartGroepModelMapper();
        euVerkiezingenMapper = new PersoonEUVerkiezingenGroepModelMapper();
        uitsluitingNLKiesrechtMapper = new PersoonUitsluitingNLKiesrechtGroepModelMapper();
        immigratieMapper = new PersoonImmigratieGroepModelMapper();
        afgeleidAdministratiefMapper = new PersoonAfgeleidAdministratiefGroepModelMapper();
    }

    @Override
    public PersoonModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonModel model = new PersoonModel();

        model.setId(rs.getInt("id"));

        model.setSoort(soortPersoonMapper.mapRow(rs, rowNum));
        model.setIdentificatienummers(identificatienummersMapper.mapRow(rs, rowNum));
        model.setGeslachtsaanduiding(geslachtsaanduidingMapper.mapRow(rs, rowNum));
        model.setSamengesteldeNaam(samengesteldeNaamMapper.mapRow(rs, rowNum));

        model.setAanschrijving(aanschrijvingMapper.mapRow(rs, rowNum));
        model.setGeboorte(geboorteMapper.mapRow(rs, rowNum));
        model.setOpschorting(opschortingMapper.mapRow(rs, rowNum));

        if (rs.getObject("partij_bijhouding_id") != null) {
            model.setBijhoudenGemeente(bijhoudingsgemeenteMapper.mapRow(rs, rowNum));
        }

        if (rs.getObject("datoverlijden") != null) {
            model.setOverlijden(overlijdenMapper.mapRow(rs, rowNum));
        }

        if (rs.getObject("datinschr") != null) {
            model.setInschrijving(inschrijvingMapper.mapRow(rs, rowNum));
        }

        model.setBijhoudingVerantwoordelijke(bijhoudingsverantwoordelijkheidMapper.mapRow(rs, rowNum));

        if (rs.getObject("DatAanvVerblijfsr") != null) {
            model.setVerblijfsrecht(verblijfsrechtMapper.mapRow(rs, rowNum));
        }

        model.setPersoonskaart(persoonskaartMapper.mapRow(rs, rowNum));
        model.setEuVerkiezingen(euVerkiezingenMapper.mapRow(rs, rowNum));
        model.setUitsluitingNLKiesrecht(uitsluitingNLKiesrechtMapper.mapRow(rs, rowNum));
        model.setImmigratie(immigratieMapper.mapRow(rs, rowNum));
        model.setAfgeleidAdministratief(afgeleidAdministratiefMapper.mapRow(rs, rowNum));

        model.setGeboorteStatusHis(historie(rs, "geboortestatushis"));
        model.setOverlijdenStatusHis(historie(rs, "overlijdenstatushis"));
        model.setSamengesteldeNaamStatusHis(historie(rs, "samengesteldenaamstatushis"));
        model.setAanschrijvingStatusHis(historie(rs, "aanschrstatushis"));
        model.setIdentificatienrsStatusHis(historie(rs, "idsstatushis"));
        model.setGeslachtsaanduidingStatusHis(historie(rs, "geslachtsaandstatusHis"));
        model.setVerblijfsrechtStatusHis(historie(rs, "verblijfsrstatushis"));
        model.setUitsluitingNLKiesrechtStatusHis(historie(rs, "uitslnlkiesrstatushis"));
        model.seteUVerkiezingenStatusHis(historie(rs, "euverkiezingenstatushis"));
        model.setBijhoudingsverantwoordelijkheidStatusHis(historie(rs, "bijhverantwoordelijkheidstat"));
        model.setOpschortingStatusHis(historie(rs, "opschortingstatushis"));
        model.setBijhoudingsgemeenteStatusHis(historie(rs, "bijhgemstatushis"));
        model.setPersoonskaartStatusHis(historie(rs, "pkstatushis"));
        model.setImmigratieStatusHis(historie(rs, "immigratiestatushis"));
        model.setInschrijvingStatusHis(historie(rs, "inschrstatushis"));

        return model;
    }

    private StatusHistorie historie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (StatusHistorie.valueOf(rs.getString(column))) : null;
    }
}
