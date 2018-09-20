/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.util.List;

import nl.bzk.brp.preview.dataaccess.CorrectieAdresDao;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * Implementatie van de interface voor de DAO voor toegang tot administratieve handeling data (BRP).
 */
public class CorrectieAdresDaoImpl implements CorrectieAdresDao {

    /**
     * De brp jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate brpJdbcTemplate;

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao#haalOpVerhuizingPersoon(java.lang.Long)
     */
    @Override
    public Persoon haalOpPersoon(final Long handelingId) {
        final String sql = "SELECT "
                + "       kpe.datgeboorte geboortedatum, "
                + "       kpe.bsn bsn, "
                + "       gemgeb.code geboortegemeentecode, "
                + "       gemgeb.naam geboortegemeentenaam, "
                + "       kpe.voornamen voornamen, "
                + "       kpe.voorvoegsel voorvoegsel, "
                + "       kpe.geslnaam achternaam, "
                + "       gesl.naam geslacht\n"
                + "   FROM kern.admhnd kad\n"
                + "   JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "   JOIN kern.his_persadres khp ON (khp.actieinh = kac.id)\n"
                + "   JOIN kern.persadres kpa ON (kpa.id = khp.persadres)\n"
                + "   JOIN kern.pers kpe ON (kpe.id = kpa.pers)\n"
                + "   LEFT JOIN kern.partij gemgeb ON (gemgeb.id = kpe.gemgeboorte)\n"
                + "   JOIN kern.geslachtsaand gesl ON (gesl.id = kpe.geslachtsaand)\n"
                + "   WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.queryForObject(sql, namedParameters, new PersoonRowMapper());
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao#haalOpVerhuizingOudAdres(java.lang.Long)
     */
    @Override
    public List<Adres> haalOpAdressen(final Long handelingId) {

        final String sql = "SELECT "
                + "       kpl.code plaatscode, "
                + "       kpl.naam plaatsnaam, "
                + "       kpa.nor straatnaam, "
                + "       kpa.huisnr huisnr, "
                + "       kpa.huisnrtoevoeging huisnrtoevoeging, "
                + "       gem.code gemeentecode, "
                + "       gem.naam gemeentenaam, "
                + "       kpa.dataanvadresh datumaanvang\n"
                + "   FROM kern.admhnd kad\n"
                + "   JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "   JOIN kern.his_persadres khpa ON (khpa.actieinh = kac.id)\n"
                + "   JOIN kern.persadres kpa ON (kpa.id = khpa.persadres)\n"
                + "   JOIN kern.pers kpe ON (kpe.id = kpa.pers)\n"
                + "   JOIN kern.plaats kpl ON (kpl.id = kpa.wpl)\n"
                + "   JOIN kern.partij gem ON (kpa.gem = gem.id)\n"
                + "   WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.query(sql, namedParameters, new AdresRowMapper());
    }

}
