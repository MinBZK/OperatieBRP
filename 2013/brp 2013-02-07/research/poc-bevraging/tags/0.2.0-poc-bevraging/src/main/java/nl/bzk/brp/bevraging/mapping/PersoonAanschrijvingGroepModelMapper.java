/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.copy.model.attribuuttype.Scheidingsteken;
import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.attribuuttype.Voorvoegsel;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonAanschrijvingGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;

/**
 * TODO: [sasme] Add documentation
 */
public class PersoonAanschrijvingGroepModelMapper extends AbstractRowMapper<PersoonAanschrijvingGroepModel> {

    private final AdellijkeTitelMapper adellijkeTitelMapper;
    private final PredikaatMapper predikaatMapper;

    private final WijzeGebruikGeslachtsnaam[] wijzeGebruikGeslachtsnamen = WijzeGebruikGeslachtsnaam.values();

    public PersoonAanschrijvingGroepModelMapper() {
        super();
        adellijkeTitelMapper = new AdellijkeTitelMapper("titel_aanschrijving_");
        predikaatMapper = new PredikaatMapper("predikaat_aanschrijving_");
    }

    @Override
    public PersoonAanschrijvingGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonAanschrijvingGroepModel model = null;

        if (true) {
            model = new PersoonAanschrijvingGroepModel();
            model.setGebruikGeslachtsnaam( gebruik(rs, "gebrgeslnaamegp"));
            model.setIndAanschrijvenMetAdellijkeTitel( jaNee(rs, "indaanschrmetadellijketitels"));
            model.setIndAanschrijvingAlgorthmischAfgeleid( jaNee(rs, "indaanschralgoritmischafgele"));
            model.setPredikaat( predikaatMapper.mapRow(rs, rowNum));
            model.setVoornamen(voornaam(rs, "voornamenaanschr"));
            model.setVoorvoegsel(voorvoegsel(rs, "voorvoegselaanschr"));
            model.setScheidingsteken(scheidingsteken(rs, "scheidingstekenaanschr"));
            model.setGeslachtsnaam(geslachtsnaam(rs, "geslnaamaanschr"));
            model.setAdellijkeTitel( adellijkeTitelMapper.mapRow(rs, rowNum));
        }

        return model;
    }

    private WijzeGebruikGeslachtsnaam gebruik(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (wijzeGebruikGeslachtsnamen[rs.getInt(column)]) : null;
    }
    private Geslachtsnaamcomponent geslachtsnaam(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Geslachtsnaamcomponent(rs.getString(column)) : null;
    }
    private Scheidingsteken scheidingsteken(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Scheidingsteken(rs.getString(column)) : null;
    }
    private Voornaam voornaam(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Voornaam(rs.getString(column)) : null;
    }
    private Voorvoegsel voorvoegsel(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Voorvoegsel(rs.getString(column)) : null;
    }
}
