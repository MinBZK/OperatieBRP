/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonAdresModel;
import org.springframework.jdbc.core.RowMapper;


public class PersoonAdresHisModelMapper implements RowMapper<PersoonAdresHisModel> {
    private final PersoonAdresModel persoonAdres;
    private static final PersoonAdresStandaardGroepModelMapper persoonAdresStandaardGroepModelMapper =
            new PersoonAdresStandaardGroepModelMapper();

    public PersoonAdresHisModelMapper(final PersoonAdresModel persoonAdres) {
        this.persoonAdres = persoonAdres;
    }

    @Override
    public PersoonAdresHisModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonAdresStandaardGroepModel
                adresStandaardGroep = persoonAdresStandaardGroepModelMapper.mapRow(rs, rowNum);

        final PersoonAdresHisModel persoonAdresHisModel = new PersoonAdresHisModel(adresStandaardGroep, persoonAdres);
        persoonAdresHisModel.setHistorie(new MaterieleHistorieImplMapper().mapRow(rs,rowNum));
        return persoonAdresHisModel;
    }
}
