/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Versienummer;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonInschrijvingGroepModel;

/**
 */
public class PersoonInschrijvingGroepModelMapper extends AbstractRowMapper<PersoonInschrijvingGroepModel> {
    @Override
    public PersoonInschrijvingGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonInschrijvingGroepModel model = new PersoonInschrijvingGroepModel();

        model.setVersienummer(new Versienummer(longValue(rs, "versienr")));
        model.setVorigePersoon(null);
        model.setVolgendePersoon(null);
        model.setDatumInschrijving(datumValue(rs, "datinschr"));

        return model;
    }

}
