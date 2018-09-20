/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.util.List;

import nl.bzk.brp.preview.dataaccess.GeboorteDao;
import nl.bzk.brp.preview.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Implementatie van de interface voor de DAO voor toegang tot geboorte data (BRP).
 */
public class GeboorteDaoImpl implements GeboorteDao {

    /**
     * De brp jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate brpJdbcTemplate;

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.dataaccess.GeboorteDao#haalOpGeboorteNieuwGeborene(java.lang.Long)
     */
    @Override
    public Persoon haalOpGeboorteNieuwGeborene(final Long handelingId) {

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
                + "   JOIN kern.his_persgeboorte khp ON (khp.actieinh = kac.id)\n"
                + "   JOIN kern.pers kpe ON (kpe.id = khp.pers)\n"
                + "   LEFT JOIN kern.partij gemgeb ON (gemgeb.id = kpe.gemgeboorte)\n"
                + "   JOIN kern.geslachtsaand gesl ON (gesl.id = kpe.geslachtsaand)\n"
                + "   WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.queryForObject(sql, namedParameters, new PersoonRowMapper());
    }

    /**
     * Haal op geboorte ouders.
     *
     * @param handelingId de handeling id
     * @return de list
     * @see nl.bzk.brp.preview.dataaccess.GeboorteDao#haalOpGeboorteOuders(Long)
     */
    @Override
    public List<Persoon> haalOpGeboorteOuders(final Long handelingId) {
        final String sql = "SELECT\n"
                + "       oudpers.datgeboorte geboortedatum, \n"
                + "       oudpers.bsn bsn, \n"
                + "       oudpers.voornamen voornamen, \n"
                + "       oudpers.voorvoegsel voorvoegsel, \n"
                + "       oudpers.geslnaam achternaam,\n"
                + "       gesl.naam geslacht,\n"
                + "       null geboortegemeentecode, "
                + "       null geboortegemeentenaam "
                + "   FROM kern.admhnd kad\n"
                + "   JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "   JOIN kern.his_persgeboorte khp ON (khp.actieinh = kac.id)\n"
                + "   JOIN kern.pers kindpers ON (kindpers.id = khp.pers)\n"
                + "   JOIN kern.betr btr ON (btr.pers = kindpers.id)\n"
                + "   JOIN kern.relatie rel ON (btr.relatie = rel.id)\n"
                + "   JOIN kern.srtrelatie srtrel ON (srtrel.id = rel.srt)\n"
                + "   JOIN kern.betr betrouder ON (betrouder.relatie = rel.id)\n"
                + "   JOIN kern.pers oudpers ON (oudpers.id = betrouder.pers)\n"
                + "   JOIN kern.geslachtsaand gesl ON (gesl.id = oudpers.geslachtsaand)\n"
                + "   WHERE srtrel.code = 'F' \n"
                + "   AND betrouder.indouder = TRUE\n"
                + "   AND kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.query(sql, namedParameters, new PersoonRowMapper());
    }

}
