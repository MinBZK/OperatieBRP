/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.moderniseringgba.isc.jbpm.JbpmDao;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * Correlatie store via JBPM connectie.
 */
public final class JbpmProcessCorrelatieStore extends JbpmDao implements ProcessCorrelatieStore {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void bewaarProcessCorrelatie(final String messageId, final ProcessData processData) {
        LOG.debug("bewaarProcessCorrelatie(messageId={}, processData={})", messageId, processData);

        execute(new BewaarProcessCorrelatieSql(processData, messageId));
    }

    @Override
    public ProcessData zoekProcessCorrelatie(final String messageId) {
        LOG.debug("zoekProcessCorrelatie(messageId={})", messageId);
        return execute(new ZoekProcessCorrelatieSql(messageId));
    }

    /**
     * Bewaar process correlatie sql.
     */
    private static final class BewaarProcessCorrelatieSql implements Sql<Void> {

        private static final String UPDATE_SQL =
                "update mig_correlatie set process_instance_id = ?, token_id = ?, node_id =?, counter_name =?, "
                        + "counter_value =?, bron_gemeente=?, doel_gemeente=? where message_id =?";

        private static final String INSERT_SQL =
                "insert into mig_correlatie(message_id, process_instance_id, token_id, node_id, counter_name, "
                        + "counter_value, bron_gemeente, doel_gemeente) values (?, ?, ?, ?, ?, ?, ?, ?)";

        private final ProcessData processData;
        private final String messageId;

        private BewaarProcessCorrelatieSql(final ProcessData processData, final String messageId) {
            this.processData = processData;
            this.messageId = messageId;
        }

        @Override
        public Void execute(final Connection connection) throws SQLException {

            // try update first
            final PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
            // CHECKSTYLE:OFF - Magic number
            updateStatement.setString(8, messageId);
            updateStatement.setLong(1, processData.getProcessInstanceId());
            updateStatement.setLong(2, processData.getTokenId());
            updateStatement.setLong(3, processData.getNodeId());
            updateStatement.setString(4, processData.getCounterName());
            updateStatement.setInt(5, processData.getCounterValue());
            updateStatement.setString(6, processData.getBronGemeente());
            updateStatement.setString(7, processData.getDoelGemeente());
            // CHECKSTYLE:ON
            if (updateStatement.executeUpdate() == 0) {
                final PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL);

                // CHECKSTYLE:OFF - Magic number
                insertStatement.setString(1, messageId);
                insertStatement.setLong(2, processData.getProcessInstanceId());
                insertStatement.setLong(3, processData.getTokenId());
                insertStatement.setLong(4, processData.getNodeId());
                insertStatement.setString(5, processData.getCounterName());
                insertStatement.setInt(6, processData.getCounterValue());
                insertStatement.setString(7, processData.getBronGemeente());
                insertStatement.setString(8, processData.getDoelGemeente());
                // CHECKSTYLE:ON

                insertStatement.execute();

                insertStatement.close();
            }
            updateStatement.close();

            return null;
        }
    }

    /**
     * Zoek process correlatie sql.
     */
    private static final class ZoekProcessCorrelatieSql implements Sql<ProcessData> {

        private static final String SELECT_SQL =
                "select process_instance_id, token_id, node_id, counter_name, counter_value, bron_gemeente, "
                        + "doel_gemeente from mig_correlatie where message_id = ?";

        private final String messageId;

        private ZoekProcessCorrelatieSql(final String messageId) {
            this.messageId = messageId;
        }

        @Override
        public ProcessData execute(final Connection connection) throws SQLException {

            final PreparedStatement statement = connection.prepareStatement(SELECT_SQL);

            statement.setString(1, messageId);

            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            final ProcessData processData =
                    new ProcessData(resultSet.getLong(1), resultSet.getLong(2), resultSet.getLong(3),
                            resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6),
                            resultSet.getString(7));

            statement.close();

            return processData;
        }
    }
}
