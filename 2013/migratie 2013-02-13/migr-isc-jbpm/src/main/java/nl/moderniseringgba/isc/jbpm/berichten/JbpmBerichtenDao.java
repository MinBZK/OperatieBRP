/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.jbpm.JbpmDao;

/**
 * Berichten DAO die gaat via de JBPM hibernate session.
 */
public final class JbpmBerichtenDao extends JbpmDao implements BerichtenDao {

    private static final String SELECT_BERICHT_SQL =
            "select id, tijdstip, kanaal, richting, message_id, correlation_id, bericht, naam, process_instance_id, "
                    + "herhaling, bron_gemeente, doel_gemeente ";

    private static final String FIND_BY_ID_SQL = SELECT_BERICHT_SQL + "from mig_berichten where id = ?";

    private static final String FIND_BY_PROCESS_ID_SQL = SELECT_BERICHT_SQL
            + "from mig_berichten where process_instance_id = ?";

    private static final String FIND_BY_MESSAGE_ID_SQL = SELECT_BERICHT_SQL
            + "from mig_berichten where message_id = ? and kanaal = ? and richting = ?";

    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    @Override
    public Long saveBericht(
            final String kanaal,
            final Direction direction,
            final String messageId,
            final String correlatieId,
            final String bericht) {
        return execute(new StoreBerichtSql(kanaal, direction, messageId, correlatieId, bericht));
    }

    @Override
    public void updateGemeenten(final Long berichtId, final String bronGemeente, final String doelGemeente) {
        execute(new UpdateGemeentenSql(berichtId, bronGemeente, doelGemeente));
    }

    @Override
    public void updateNaam(final Long berichtId, final String naam) {
        execute(new Sql<Void>() {
            @Override
            public Void execute(final Connection connection) throws SQLException {
                final PreparedStatement statement =
                        connection.prepareStatement("update mig_berichten set naam = ? where id = ?");

                statement.setString(1, naam == null ? "" : naam);
                statement.setLong(2, berichtId);
                statement.execute();
                statement.close();

                return null;
            }
        });
    }

    @Override
    public void updateProcessInstance(final Long berichtId, final Long processInstanceId) {
        execute(new Sql<Void>() {
            @Override
            public Void execute(final Connection connection) throws SQLException {
                final PreparedStatement statement =
                        connection.prepareStatement("update mig_berichten set process_instance_id = ? where id = ?");

                statement.setLong(1, processInstanceId);
                statement.setLong(2, berichtId);
                statement.execute();
                statement.close();

                return null;
            }
        });
    }

    @Override
    public void updateHerhaling(final Long berichtId, final Integer herhaling) {
        execute(new Sql<Void>() {
            @Override
            public Void execute(final Connection connection) throws SQLException {
                final PreparedStatement statement =
                        connection.prepareStatement("update mig_berichten set herhaling = ? where id = ?");

                if (herhaling == null) {
                    statement.setNull(1, Types.INTEGER);
                } else {
                    statement.setInt(1, herhaling);
                }
                statement.setLong(2, berichtId);
                statement.execute();
                statement.close();

                return null;
            }
        });
    }

    @Override
    public List<Bericht> findBerichtenByProcessInstanceId(final Long processInstanceId) {
        return execute(new Sql<List<Bericht>>() {
            @Override
            public List<Bericht> execute(final Connection connection) throws SQLException {

                final PreparedStatement statement = connection.prepareStatement(FIND_BY_PROCESS_ID_SQL);
                statement.setLong(1, processInstanceId);

                statement.execute();
                final ResultSet rs = statement.getResultSet();

                final List<Bericht> berichten = new ArrayList<Bericht>();
                while (rs.next()) {
                    berichten.add(BERICHT_MAPPER.map(rs));
                }
                statement.close();

                return berichten;
            }
        });
    }

    @Override
    public List<Bericht> findBerichtenByMessageId(
            final String messageId,
            final String kanaal,
            final Direction richting) {
        return execute(new FindBerichtenByMessageIdSql(kanaal, messageId, richting));
    }

    @Override
    public Bericht getBericht(final Long berichtId) {
        return execute(new Sql<Bericht>() {
            @Override
            public Bericht execute(final Connection connection) throws SQLException {
                final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);
                statement.setLong(1, berichtId);

                statement.execute();
                final ResultSet rs = statement.getResultSet();
                if (rs.next()) {
                    final Bericht bericht = BERICHT_MAPPER.map(rs);

                    statement.close();
                    return bericht;
                } else {
                    statement.close();
                    return null;
                }
            }
        });
    }

    /**
     * Store bericht sql.
     */
    private static final class StoreBerichtSql implements Sql<Long> {

        private static final String STORE_SQL =
                "insert into mig_berichten(tijdstip, kanaal, richting,  message_id, correlation_id, bericht)"
                        + " values(?, ?, ?, ?, ?, ?)";

        private final String kanaal;
        private final Direction direction;
        private final String messageId;
        private final String correlatieId;
        private final String bericht;

        private StoreBerichtSql(
                final String kanaal,
                final Direction direction,
                final String messageId,
                final String correlatieId,
                final String bericht) {
            super();
            this.kanaal = kanaal;
            this.direction = direction;
            this.messageId = messageId;
            this.correlatieId = correlatieId;
            this.bericht = bericht;
        }

        @Override
        public Long execute(final Connection connection) throws SQLException {

            final PreparedStatement statement =
                    connection.prepareStatement(STORE_SQL, Statement.RETURN_GENERATED_KEYS);

            // CHECKSTYLE:OFF - Magic number
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setString(2, kanaal);
            statement.setString(3, direction.getCode());
            statement.setString(4, messageId);
            statement.setString(5, correlatieId);
            statement.setString(6, bericht);
            // CHECKSTYLE:ON

            statement.executeUpdate();

            final ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            final Long id = rs.getLong(1);

            statement.close();
            return id;
        }
    }

    /**
     * Update gemeenten sql.
     */
    private static final class UpdateGemeentenSql implements Sql<Void> {
        private final Long berichtId;
        private final String bronGemeente;
        private final String doelGemeente;

        private UpdateGemeentenSql(final Long berichtId, final String bronGemeente, final String doelGemeente) {
            this.berichtId = berichtId;
            this.bronGemeente = bronGemeente;
            this.doelGemeente = doelGemeente;
        }

        @Override
        public Void execute(final Connection connection) throws SQLException {
            final PreparedStatement statement =
                    connection.prepareStatement("update mig_berichten "
                            + "set bron_gemeente = ?, doel_gemeente = ? where id = ?");

            // CHECKSTYLE:OFF - Magic number
            if (bronGemeente == null) {
                statement.setNull(1, Types.VARCHAR);
            } else {
                statement.setString(1, bronGemeente);
            }
            if (doelGemeente == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, doelGemeente);
            }
            statement.setLong(3, berichtId);

            // CHECKSTYLE:ON
            statement.execute();
            statement.close();

            return null;
        }
    }

    /**
     * Find berichten by message id.
     */
    private static final class FindBerichtenByMessageIdSql implements Sql<List<Bericht>> {
        private final String kanaal;
        private final String messageId;
        private final Direction richting;

        private FindBerichtenByMessageIdSql(final String kanaal, final String messageId, final Direction richting) {
            this.kanaal = kanaal;
            this.messageId = messageId;
            this.richting = richting;
        }

        @Override
        public List<Bericht> execute(final Connection connection) throws SQLException {

            final PreparedStatement statement = connection.prepareStatement(FIND_BY_MESSAGE_ID_SQL);
            // CHECKSTYLE:OFF - Magic number
            statement.setString(1, messageId);
            statement.setString(2, kanaal);
            statement.setString(3, richting.getCode());
            // CHECKSTYLE:ON

            statement.execute();
            final ResultSet rs = statement.getResultSet();

            final List<Bericht> berichten = new ArrayList<Bericht>();
            while (rs.next()) {
                berichten.add(BERICHT_MAPPER.map(rs));
            }
            statement.close();

            return berichten;
        }
    }

    /**
     * Bericht mapper.
     */
    private static class BerichtMapper {

        /**
         * Map een result set naar een bericht.
         * 
         * @param rs
         *            result set
         * @return bericht
         * @throws SQLException
         *             bij onderliggende sql fouten
         */
        public Bericht map(final ResultSet rs) throws SQLException {
            final Bericht bericht = new Bericht();

            // CHECKSTYLE:OFF - Magic number
            bericht.setId(rs.getLong(1));
            bericht.setTijdstip(rs.getTimestamp(2));
            bericht.setKanaal(rs.getString(3));
            bericht.setRichting(Direction.valueOfCode(rs.getString(4)));
            bericht.setMessageId(rs.getString(5));
            bericht.setCorrelationId(rs.getString(6));
            bericht.setBericht(rs.getString(7));
            bericht.setNaam(rs.getString(8));
            bericht.setProcessInstanceId(rs.getLong(9));
            bericht.setHerhaling(rs.getInt(10));
            bericht.setBronGemeente(rs.getString(11));
            bericht.setDoelGemeente(rs.getString(12));
            // CHECKSTYLE:ON

            return bericht;
        }
    }

}
