/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.copy.model.objecttype.operationeel.statisch.FunctieAdres;

/**
 */
public class PersoonAdresStandaardGroepModelMapper extends AbstractRowMapper<PersoonAdresStandaardGroepModel> {
    private LandMapper landMapper;
    private PlaatsMapper plaatsMapper;
    private RedenWijzigingAdresMapper redenMapper;

    private PartijMapper partijMapper;
    private FunctieAdres[] functieAdressen = FunctieAdres.values();
    private AangeverAdreshoudingIdentiteit[] aangeverAdreshoudingIdentiteiten = AangeverAdreshoudingIdentiteit.values();

    public PersoonAdresStandaardGroepModelMapper() {
        super();
        landMapper = new LandMapper("land_");
        plaatsMapper = new PlaatsMapper("plaats_");
        partijMapper = new PartijMapper("partij_");
        redenMapper = new RedenWijzigingAdresMapper("reden_");
    }

    @Override
    public PersoonAdresStandaardGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonAdresStandaardGroepModel model = new PersoonAdresStandaardGroepModel();

        model.setSoort(functie(rs, "Srt"));
        model.setDatumAanvangAdreshouding(datumValue(rs, "DatAanvAdresh"));

        if (rs.getObject("reden_id") != null) {
            model.setRedenwijziging(redenMapper.mapRow(rs, rowNum));
        }

        model.setAangeverAdreshouding(aangever(rs, "AangAdresh"));
        model.setAdresseerbaarObject(adresseerbaar(rs, "adresseerbaarObject"));
        model.setIdentificatiecodeNummeraanduiding(identificatie(rs, "IdentcodeNraand"));
        model.setGemeente(partijMapper.mapRow(rs, rowNum));

        model.setNaamOpenbareRuimte(new NaamOpenbareRuimte(rs.getString("NOR")));
        model.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte(rs.getString("AfgekorteNOR")));
        model.setGemeentedeel(gemeentedeel(rs, "Gemdeel"));
        model.setHuisnummer(huisnummer(rs, "Huisnr"));
        model.setHuisletter(huisletter(rs, "huisletter"));
        model.setPostcode(postcode(rs, "postcode"));
        model.setHuisnummertoevoeging(toevoeging(rs, "Huisnrtoevoeging"));
        if (rs.getObject("plaats_id") != null) {
            model.setWoonplaats(plaatsMapper.mapRow(rs, rowNum));
        }
        model.setLocatietovAdres(tovAdres(rs, "LoctovAdres"));

        model.setLocatieOmschrijving(omschrijving(rs, "LocOms"));
        model.setBuitenlandsAdresRegel1(adres(rs, "BLAdresRegel1"));
        model.setBuitenlandsAdresRegel2(adres(rs, "BLAdresRegel2"));
        model.setBuitenlandsAdresRegel3(adres(rs, "BLAdresRegel3"));
        model.setBuitenlandsAdresRegel4(adres(rs, "BLAdresRegel4"));
        model.setBuitenlandsAdresRegel5(adres(rs, "BLAdresRegel5"));
        model.setBuitenlandsAdresRegel6(adres(rs, "BLAdresRegel6"));

        if (rs.getObject("land_id") != null) {
            model.setLand(landMapper.mapRow(rs, rowNum));
        }
        model.setDatumVertrekUitNederland(datumValue(rs, "DatVertrekUitNederland"));

        return model;
    }

    private FunctieAdres functie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (functieAdressen[rs.getInt(column)]) : null;
    }
    private AangeverAdreshoudingIdentiteit aangever(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (aangeverAdreshoudingIdentiteiten[rs.getInt(column)]) : null;
    }

    private Adresseerbaarobject adresseerbaar(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Adresseerbaarobject(rs.getString(column)) : null;
    }

    private IdentificatiecodeNummeraanduiding identificatie(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new IdentificatiecodeNummeraanduiding(rs.getString(column)) : null;
    }

    private LocatieOmschrijving omschrijving(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new LocatieOmschrijving(rs.getString(column)) : null;
    }

    private Adresregel adres(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Adresregel(rs.getString(column)) : null;
    }

    private Gemeentedeel gemeentedeel(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Gemeentedeel(rs.getString(column)) : null;
    }
    private Huisnummer huisnummer(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Huisnummer(integerValue(rs, column)) : null;
    }
    private Huisletter huisletter(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Huisletter(rs.getString(column)) : null;
    }
    private Postcode postcode(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Postcode(rs.getString(column)) : null;
    }
    private Huisnummertoevoeging toevoeging(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Huisnummertoevoeging(rs.getString(column)) : null;
    }
    private LocatieTovAdres tovAdres(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new LocatieTovAdres(rs.getString(column)) : null;
    }
}
