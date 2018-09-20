/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import nl.moderniseringgba.isc.jbpm.JbpmDao;

/**
 * Foute dao.
 */
public final class JbpmFoutenDao extends JbpmDao implements FoutenDao {

    @Override
    public long registreerFout(
            final String foutcode,
            final String foutmelding,
            final String proces,
            final long processId,
            final String bronGemeente,
            final String doelGemeente) {
        return execute(new InsertSql(foutmelding, proces, foutcode, processId, bronGemeente, doelGemeente));
    }

    @Override
    public void voegResolutieToe(final long id, final String resolutie) {
        execute(new Sql<Void>() {
            @Override
            public Void execute(final Connection connection) throws SQLException {
                final PreparedStatement statement =
                        connection.prepareStatement("update mig_fouten set resolutie = ? where id = ?");

                statement.setString(1, resolutie);
                statement.setLong(2, id);
                statement.execute();
                statement.close();

                return null;
            }
        });
    }

    /**
     * Insert sql.
     */
    private static final class InsertSql implements Sql<Long> {
        private final String foutmelding;
        private final String proces;
        private final String foutcode;
        private final long processId;
        private final String bronGemeente;
        private final String doelGemeente;

        private InsertSql(
                final String foutmelding,
                final String proces,
                final String foutcode,
                final long processId,
                final String bronGemeente,
                final String doelGemeente) {
            this.foutmelding = foutmelding;
            this.proces = proces;
            this.foutcode = foutcode;
            this.processId = processId;
            this.bronGemeente = bronGemeente;
            this.doelGemeente = doelGemeente;
        }

        @Override
        public Long execute(final Connection connection) throws SQLException {
            final PreparedStatement statement =
                    connection.prepareStatement("insert into mig_fouten(tijdstip, proces, process_instance_id, "
                            + "proces_init_gemeente, proces_doel_gemeente, code, melding) "
                            + "values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            // CHECKSTYLE:OFF - Magic number
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setString(2, proces);
            statement.setLong(3, processId);
            statement.setString(4, bronGemeente);
            statement.setString(5, doelGemeente);
            statement.setString(6, foutcode);
            statement.setString(7, foutmelding);
            // CHECKSTYLE:ON

            statement.executeUpdate();

            final ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            final Long id = rs.getLong(1);

            statement.close();
            return id;
        }
    }
}
