/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bericht mapper.
 */
public class BerichtMapper {

    public Bericht map(final ResultSet rs) throws SQLException {
        final Bericht bericht = new Bericht();

        // CHECKSTYLE:OFF - Magic number
        bericht.setId(rs.getLong(1));
        bericht.setTijdstip(rs.getTimestamp(2));
        bericht.setKanaal(rs.getString(3));
        bericht.setRichting(Bericht.Direction.valueOfCode(rs.getString(4)));
        bericht.setMessageId(rs.getString(5));
        bericht.setCorrelationId(rs.getString(6));
        bericht.setBericht(rs.getString(7));
        bericht.setNaam(rs.getString(8));
        bericht.setProcessInstanceId(rs.getLong(9));
        // CHECKSTYLE:ON

        return bericht;
    }
}
