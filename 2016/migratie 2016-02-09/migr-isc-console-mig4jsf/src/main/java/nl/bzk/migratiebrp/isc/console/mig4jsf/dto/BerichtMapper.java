/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bericht mapper.
 */
public final class BerichtMapper {

    /**
     * De kolommen gemapped door deze mapper.
     */
    public static final String COLUMNS = "id, tijdstip, kanaal, richting, message_id, correlation_id, bericht, naam, process_instance_id, "
                                         + "verzendende_partij, ontvangende_partij, ms_sequence_number";

    private static final int COLUMN_1 = 1;
    private static final int COLUMN_2 = 2;
    private static final int COLUMN_3 = 3;
    private static final int COLUMN_4 = 4;
    private static final int COLUMN_5 = 5;
    private static final int COLUMN_6 = 6;
    private static final int COLUMN_7 = 7;
    private static final int COLUMN_8 = 8;
    private static final int COLUMN_9 = 9;
    private static final int COLUMN_10 = 10;
    private static final int COLUMN_11 = 11;
    private static final int COLUMN_12 = 12;

    /**
     * Map een results et naar een bericht.
     *
     * @param rs
     *            result set
     * @return bericht
     * @throws SQLException
     *             bij sql fouten
     */
    public Bericht map(final ResultSet rs) throws SQLException {
        final Bericht bericht = new Bericht();

        bericht.setId(rs.getLong(COLUMN_1));
        bericht.setTijdstip(rs.getTimestamp(COLUMN_2));
        bericht.setKanaal(rs.getString(COLUMN_3));
        bericht.setRichting(Bericht.Direction.valueOfCode(rs.getString(COLUMN_4)));
        bericht.setMessageId(rs.getString(COLUMN_5));
        bericht.setCorrelationId(rs.getString(COLUMN_6));
        bericht.setBericht(rs.getString(COLUMN_7));
        bericht.setNaam(rs.getString(COLUMN_8));
        bericht.setProcessInstanceId(rs.getLong(COLUMN_9));
        if (rs.wasNull()) {
            bericht.setProcessInstanceId(null);
        }
        bericht.setBronGemeente(rs.getString(COLUMN_10));
        bericht.setDoelGemeente(rs.getString(COLUMN_11));
        bericht.setMsSequenceNumber(rs.getString(COLUMN_12));

        return bericht;
    }
}
