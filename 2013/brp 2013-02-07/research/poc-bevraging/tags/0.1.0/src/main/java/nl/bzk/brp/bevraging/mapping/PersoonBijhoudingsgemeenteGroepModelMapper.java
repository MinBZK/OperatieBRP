/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonBijhoudingsgemeenteGroepModel;

/**
 */
public class PersoonBijhoudingsgemeenteGroepModelMapper
        extends AbstractRowMapper<PersoonBijhoudingsgemeenteGroepModel>
{
    private final PartijMapper partijMapper;

    public PersoonBijhoudingsgemeenteGroepModelMapper() {
        super();
        this.partijMapper = new PartijMapper("partij_bijhouding_");
    }

    @Override
    public PersoonBijhoudingsgemeenteGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonBijhoudingsgemeenteGroepModel model = new PersoonBijhoudingsgemeenteGroepModel();

        model.setBijhoudingsgemeente(partijMapper.mapRow(rs, rowNum));
        model.setDatumInschrijvingInGemeente(datumValue(rs, "datinschringem"));
        model.setIndOnverwerktDocumentAanwezig(jaNee(rs, "indonverwdocaanw"));

        return model;
    }

}
