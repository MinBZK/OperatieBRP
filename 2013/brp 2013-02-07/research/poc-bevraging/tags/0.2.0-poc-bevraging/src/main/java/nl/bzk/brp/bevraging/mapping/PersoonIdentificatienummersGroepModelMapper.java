/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Administratienummer;
import nl.bzk.copy.model.attribuuttype.Burgerservicenummer;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonIdentificatienummersGroepModel;

/**
 *
 */
public class PersoonIdentificatienummersGroepModelMapper
        extends AbstractRowMapper<PersoonIdentificatienummersGroepModel>
{

    @Override
    public PersoonIdentificatienummersGroepModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        PersoonIdentificatienummersGroepModel model = new PersoonIdentificatienummersGroepModel();

        model.setAdministratienummer(adminnr(rs, "anr"));
        model.setBurgerservicenummer(bsn(rs, "bsn"));

        return model;
    }

    private Administratienummer adminnr(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Administratienummer(longValue(rs, column)) : null;
    }
    private Burgerservicenummer bsn(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? new Burgerservicenummer(String.valueOf(longValue(rs, column))) : null;
    }

}
