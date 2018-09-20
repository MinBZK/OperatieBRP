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
import nl.bzk.copy.model.attribuuttype.Voorvoegsel;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonGeslachtsnaamcomponentStandaardGroepModel;

/**
 * De Class PersoonVoornaamStandaardGroepModelMapper.
 */
public class PersoonGeslachtsnaamcomponentStandaardGroepModelMapper
        extends AbstractRowMapper<PersoonGeslachtsnaamcomponentStandaardGroepModel>
{

    private final AdellijkeTitelMapper adellijkeTitelMapper;
    private final PredikaatMapper predikaatMapper;

    /**
     * Instantieert een nieuwe persoon voornaam standaard groep model mapper.
     */
    public PersoonGeslachtsnaamcomponentStandaardGroepModelMapper() {
        adellijkeTitelMapper = new AdellijkeTitelMapper("titel_geslnaamcomp_");
        predikaatMapper = new PredikaatMapper("predikaat_geslnaamcomp_");
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public PersoonGeslachtsnaamcomponentStandaardGroepModel mapRow(final ResultSet rs, final int rowNum) throws
            SQLException
    {
        PersoonGeslachtsnaamcomponentStandaardGroepModel model = new PersoonGeslachtsnaamcomponentStandaardGroepModel();

        model.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent(stringValue(rs, "naam")));
        model.setScheidingsteken(new Scheidingsteken(stringValue(rs, "scheidingsteken")));
        model.setVoorvoegsel(new Voorvoegsel(stringValue(rs, "voorvoegsel")));
        model.setAdellijkeTitel(adellijkeTitelMapper.mapRow(rs, rowNum));
        model.setPredikaat(predikaatMapper.mapRow(rs, rowNum));

        return model;
    }
}
