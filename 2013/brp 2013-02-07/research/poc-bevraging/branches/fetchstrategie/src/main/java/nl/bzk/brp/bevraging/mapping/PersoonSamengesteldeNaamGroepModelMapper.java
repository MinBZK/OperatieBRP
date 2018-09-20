/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.attribuuttype.Scheidingsteken;
import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.attribuuttype.Voorvoegsel;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import org.springframework.jdbc.core.RowMapper;


public class PersoonSamengesteldeNaamGroepModelMapper extends AbstractRowMapper<PersoonSamengesteldeNaamGroepModel> {

    private final AdellijkeTitelMapper adellijkeTitelMapper;
    private final PredikaatMapper predikaatMapper;

    public PersoonSamengesteldeNaamGroepModelMapper() {
        super();
        adellijkeTitelMapper = new AdellijkeTitelMapper("titel_persoon_");
        predikaatMapper = new PredikaatMapper("predikaat_persoon_");
    }

    @Override
    public PersoonSamengesteldeNaamGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonSamengesteldeNaamGroepModel model = new PersoonSamengesteldeNaamGroepModel();

        if (rs.getObject("predikaat_persoon_id") != null) {
            model.setPredikaat(predikaatMapper.mapRow(rs, rowNum));
        }
        if (rs.getObject("titel_persoon_id") != null) {
            model.setAdellijkeTitel(adellijkeTitelMapper.mapRow(rs, rowNum));
        }
        model.setGeslachtsnaam(geslachtsnaam(rs,"geslnaam"));
        model.setIndAlgorithmischAfgeleid(jaNee(rs, "indalgoritmischafgeleid"));
        model.setIndNamenreeksAlsGeslachtsNaam(jaNee(rs, "indnreeksalsgeslnaam"));
        model.setScheidingsteken(scheidingsteken(rs, "scheidingsteken"));
        model.setVoornamen(voornaam(rs, "voornamen"));
        model.setVoorvoegsel(voorvoegsel(rs, "voorvoegsel"));

        return model;
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
