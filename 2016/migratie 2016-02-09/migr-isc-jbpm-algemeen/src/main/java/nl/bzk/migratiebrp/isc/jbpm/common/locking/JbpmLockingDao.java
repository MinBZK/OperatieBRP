/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.locking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.isc.jbpm.common.JbpmDao;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Locking DAO via JBPM connectie.
 */
public final class JbpmLockingDao extends JbpmDao implements LockingDao {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Long lock(final long processInstanceId, final Set<Long> aNummerLijst) {
        return execute(new MaakLockSql(processInstanceId, aNummerLijst), true);
    }

    @Override
    public void unlock(final long lockId) {
        execute(new Sql<Void>() {
            @Override
            public Void execute(final Connection connection) throws SQLException {
                try (final PreparedStatement statement = connection.prepareStatement("DELETE FROM mig_lock WHERE id = ?")) {
                    statement.setLong(JdbcConstants.COLUMN_1, lockId);
                    statement.execute();
                }
                return null;
            }
        });
    }

    /**
     * Zoek ANummerLocks op basis van het lock id.
     */
    private static final class MaakLockSql implements Sql<Long> {

        private static final String INSERT_LOCK_SQL = "INSERT INTO mig_lock(tijdstip, process_instance_id) VALUES(?, ?)";
        private static final String INSERT_ANUMMER_SQL = "INSERT INTO mig_lock_anummer(tijdstip, lock_id, anummer) VALUES(?, ?, ?)";

        private final long processInstanceId;
        private final Set<Long> aNummerLijst;

        private MaakLockSql(final long processInstanceId, final Set<Long> aNummerLijst) {
            this.processInstanceId = processInstanceId;
            this.aNummerLijst = aNummerLijst;
        }

        @Override
        @SuppressWarnings("checkstyle:illegalcatch")
        public Long execute(final Connection connection) throws SQLException {

            try {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("SAVEPOINT make_lock");
                }

                final Long lockId = creeerLock(connection);

                LOG.debug("Anummer lijst voor process instance: {} -> {}", processInstanceId, aNummerLijst);
                if (aNummerLijst != null) {
                    try (final PreparedStatement aNummerStatement = connection.prepareStatement(INSERT_ANUMMER_SQL)) {
                        for (final Long aNummer : aNummerLijst) {
                            aNummerStatement.setTimestamp(JdbcConstants.COLUMN_1, new Timestamp(System.currentTimeMillis()));
                            aNummerStatement.setLong(JdbcConstants.COLUMN_2, lockId);
                            aNummerStatement.setLong(JdbcConstants.COLUMN_3, aNummer);

                            LOG.debug("Creating lock (id={}) voor a-nummer: {}", lockId, aNummer);
                            aNummerStatement.executeUpdate();
                        }
                    }
                }

                LOG.debug("Lock verkregen.");

                return lockId;
            } catch (final Exception e /* Exception catched vanwege gebruik snapshot om constraint op te vangen. */) {
                LOG.debug("Lock niet verkregen.");
                // Rollback naar de save point
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("ROLLBACK TO SAVEPOINT make_lock");
                }
                return null;
            }
        }

        private Long creeerLock(final Connection connection) throws SQLException {
            Long lockId;
            try (final PreparedStatement lockStatement = connection.prepareStatement(INSERT_LOCK_SQL, Statement.RETURN_GENERATED_KEYS)) {

                lockStatement.setTimestamp(JdbcConstants.COLUMN_1, new Timestamp(System.currentTimeMillis()));
                lockStatement.setLong(JdbcConstants.COLUMN_2, processInstanceId);

                LOG.debug("Creating lock voor process instance id: {}", processInstanceId);
                lockStatement.executeUpdate();

                try (final ResultSet rs = lockStatement.getGeneratedKeys()) {
                    rs.next();
                    lockId = rs.getLong(JdbcConstants.COLUMN_1);
                }
            }
            return lockId;
        }
    }
}
