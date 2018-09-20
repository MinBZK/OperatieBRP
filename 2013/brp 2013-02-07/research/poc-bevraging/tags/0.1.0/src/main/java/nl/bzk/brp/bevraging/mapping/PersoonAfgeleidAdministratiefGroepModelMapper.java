/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.groep.operationeel.actueel.PersoonAfgeleidAdministratiefGroepModel;

/**
 */
public class PersoonAfgeleidAdministratiefGroepModelMapper
        extends AbstractRowMapper<PersoonAfgeleidAdministratiefGroepModel>
{
    @Override
    public PersoonAfgeleidAdministratiefGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonAfgeleidAdministratiefGroepModel model = null;

        if (jaNee(rs, "indgegevensinonderzoek") != null) {
            model = new PersoonAfgeleidAdministratiefGroepModel();

            model.setIndGegevensInOnderzoek(jaNee(rs, "indgegevensinonderzoek"));
            model.setTijdstipLaatsteWijziging(datumTijdValue(rs, "tijdstipLaatsteWijz"));
        }
        return model;
    }
}
