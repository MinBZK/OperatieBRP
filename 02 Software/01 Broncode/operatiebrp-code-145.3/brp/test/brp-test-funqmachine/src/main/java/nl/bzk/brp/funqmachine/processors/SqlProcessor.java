/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.funqmachine.configuratie.DatabaseConfig;
import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Class met alle logica om bewerkingen uit te voeren op een database.
 */
public final class SqlProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlProcessor.class);
    private static final int QUERY_TIMEOUT_VERWIJDEREN_ALLES = 300;
    private static final String KAN_QUERY_NIET_UITVOEREN = "Kan query niet uitvoeren: {}";
    private static final String STATEMENT_TIMEOUT_SQL = "set local statement_timeout = %d";
    private static final String DEFAULT_SCHEMA = "kern";
    private static Map<String, SqlProcessor> instances = new HashMap<>();
    private final String schema;
    private Connection connection;

    private SqlProcessor(final String schema) throws SQLException, ClassNotFoundException {
        this.schema = schema;
        connection = maakConnectie();
    }

    /**
     * Geeft een instantie terug van de {@link SqlProcessor} waarbij een connectie naar de database bestaat. Als de instantie nog niet bestaat of als de
     * verbinding gesloten is, dan wordt er een nieuwe instantie terug gegeven.
     * @return een instantie van {@link SqlProcessor} met een actieve verbinding naar een database.
     */
    public static synchronized SqlProcessor getInstance() {
        return getInstance(DEFAULT_SCHEMA);
    }

    /**
     * Geeft een instantie terug van de {@link SqlProcessor} waarbij een connectie naar de database bestaat. Als de instantie nog niet bestaat of als de
     * verbinding gesloten is, dan wordt er een nieuwe instantie terug gegeven.
     * @param schema database schema
     * @return een instantie van {@link SqlProcessor} met een actieve verbinding naar een database.
     */
    public static synchronized SqlProcessor getInstance(final String schema) {
        try {
            if (instances.get(schema) == null || instances.get(schema).connection == null || instances.get(schema).connection.isClosed()) {
                instances.put(schema, new SqlProcessor(schema));
            }
            return instances.get(schema);
        } catch (final SQLException | ClassNotFoundException e) {
            throw new ProcessorException(e);
        }
    }

    private Connection maakConnectie() throws ClassNotFoundException, SQLException {
        final DatabaseConfig databaseConfig = Omgeving.getOmgeving().getGetDatabaseConfig(schema);

        final Properties props = new Properties();
        props.put("user", databaseConfig.getUsername());
        props.put("password", databaseConfig.getPassword());
        props.put("allowMultiQueries", "true");

        loadDriver(databaseConfig.getDriverClassName());
        return DriverManager.getConnection(databaseConfig.getUrl(), props);
    }

    private Statement getStatement() throws SQLException {
        return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private PreparedStatement getPreparedStatement(final String query) throws SQLException {
        return connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private void loadDriver(final String driverClassName) throws ClassNotFoundException {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            LOGGER.debug("Fout tijdens laden database driver", e);
            SqlProcessor.class.getClassLoader().loadClass(driverClassName);
        }
    }

    /**
     * Voert een SQL statement uit.
     * @param query de SQL query die uitgevoerd moet worden
     * @return de {@link ResultSet} voor deze query
     * @throws SQLException als de query niet uitgevoerd kan worden
     */
    public ResultSet voerUit(final String query) throws SQLException {
        return getStatement().executeQuery(query);
    }

    /**
     * Voert een SQL statement uit met een {@link RowCallbackHandler}.
     * @param query de SQL query die uitgevoerd moet worden
     * @param rowCallbackHandler de {@link RowCallbackHandler}
     */
    public void voerQueryUit(final String query, final RowCallbackHandler rowCallbackHandler) {
        maakJdbcTemplate().query(query, rowCallbackHandler);
    }

    /**
     * Voert een SQL statement uit.
     * @param query de SQL query die uitgevoerd moet worden
     * @throws SQLException als de query niet uitgevoerd kan worden
     */
    public void voerUpdateUit(final String query) throws SQLException {
        getStatement().executeUpdate(query);
    }

    /**
     * Voert een batch update uit.
     * @param sql de SQL die uitgevoerd moet wordden
     */
    public void voerBatchUpdateUit(final String... sql) {
        maakJdbcTemplate().batchUpdate(sql);
    }

    /**
     * Geeft een lijst van resultaten terug voor de opgegeven query.
     * @param query de query die uitgevoerd
     * @return een lijst met daarin de gegevens uit de persoonscache
     */
    public List<Map<String, Object>> voerQueryUit(final String query) {
        return maakJdbcTemplate().queryForList(query);
    }

    /**
     * Schoont de blokkeringen op voor alle personen.
     */
    public void verwijderAlleBlokkeringen() {
        final JdbcTemplate jdbcTemplate = maakJdbcTemplate();
        jdbcTemplate.execute(String.format(STATEMENT_TIMEOUT_SQL, TimeUnit.SECONDS.toMillis(QUERY_TIMEOUT_VERWIJDEREN_ALLES)));
        jdbcTemplate.execute("TRUNCATE migblok.blokkering CASCADE;");
    }

    /**
     * Schoont de pers / relatie en betr tabellen.
     */
    public void verwijderAllePersonen() {
        final JdbcTemplate jdbcTemplate = maakJdbcTemplate();
        jdbcTemplate.execute(String.format(STATEMENT_TIMEOUT_SQL, TimeUnit.SECONDS.toMillis(QUERY_TIMEOUT_VERWIJDEREN_ALLES)));
        jdbcTemplate.execute("TRUNCATE kern.betr CASCADE;");
        jdbcTemplate.execute("TRUNCATE kern.relatie CASCADE;");
        jdbcTemplate.execute("TRUNCATE kern.onderzoek CASCADE;");
        jdbcTemplate.execute("TRUNCATE kern.pers CASCADE;");
        jdbcTemplate.execute("TRUNCATE verconv.lo3melding CASCADE;");
        jdbcTemplate.execute("TRUNCATE verconv.lo3voorkomen CASCADE;");
        jdbcTemplate.execute("TRUNCATE verconv.lo3ber CASCADE;");
        jdbcTemplate.execute("TRUNCATE ist.autorisatietabel CASCADE;");
        jdbcTemplate.execute("TRUNCATE ist.stapelrelatie CASCADE;");
        jdbcTemplate.execute("TRUNCATE ist.stapelvoorkomen CASCADE;");
        jdbcTemplate.execute("TRUNCATE ist.stapel CASCADE;");
    }

    /**
     * Schoont de blokkeringen op voor alle personen.
     */
    public void verwijderAlleArchivering() {
        final JdbcTemplate jdbcTemplate = maakJdbcTemplate();
        jdbcTemplate.execute(String.format(STATEMENT_TIMEOUT_SQL, TimeUnit.SECONDS.toMillis(QUERY_TIMEOUT_VERWIJDEREN_ALLES)));
        jdbcTemplate.execute("TRUNCATE ber.ber CASCADE;");
        jdbcTemplate.execute("TRUNCATE ber.berpers CASCADE;");
    }

    private JdbcTemplate maakJdbcTemplate() {
        return new JdbcTemplate(new SingleConnectionDataSource(connection, false));
    }

    /**
     * Geeft het aantal teruggegeven resultaten van de gegeven query.
     * @param query de query
     * @param bsn burgerservice nummer
     * @param soortBetrokkenheidId soort betrokkenheid id
     * @param soortRelatieId soort relatie id
     * @return het aantal rijen dat voor de opgegeven query terug komt.
     * @throws SQLException als de query niet uitgevoerd kan worden
     */
    public int geefAantal(final String query, final String bsn, final int soortBetrokkenheidId, final int soortRelatieId) throws SQLException {
        final int soortBetrokkenheidIdParamIndex = 2;
        final int soortRelatieIdParamIndex = 3;

        try (final PreparedStatement statement = getPreparedStatement(query)) {
            statement.setString(1, bsn);
            statement.setInt(soortBetrokkenheidIdParamIndex, soortBetrokkenheidId);
            statement.setInt(soortRelatieIdParamIndex, soortRelatieId);
            try (final ResultSet resultSet = statement.executeQuery()) {
                int rowCount = 0;
                if (resultSet.last()) {
                    rowCount = resultSet.getRow();
                }
                return rowCount;
            }
        } catch (SQLException e) {
            LOGGER.error(KAN_QUERY_NIET_UITVOEREN, query);
            throw e;
        }

    }

    /**
     * Geeft de eerste rij terug van het resultaat van de query.
     * @param query de query.
     * @return de eerste rij van de {@link ResultSet} van de query.
     * @throws SQLException als de query niet uitgevoerd kan worden
     */
    public ResultSet firstRow(final String query) throws SQLException {
        try {
            return voerUit(query);
        } catch (SQLException e) {
            LOGGER.error(KAN_QUERY_NIET_UITVOEREN, query);
            throw e;
        }
    }

    /**
     * Geeft het persoonID en versie terug behorende bij het opgegeven BSN of Anummer.
     * @param ident anummer of bsn van de persoon
     * @return een Integer array met daarin de persoonId en het versienummer.
     * @throws SQLException als er een fout optreedt tijdens het uitvoeren van de SQL
     */
    public Integer[] geeftPersoonId(String ident) throws SQLException {
        final int arrayLength = 2;
        final Integer[] result = new Integer[arrayLength];
        if (ident == null || ident.isEmpty()) {
            return result;
        }
        final String colonSplitter = ":";
        if (ident.startsWith("db:")) {
            result[0] = Integer.valueOf(ident.split(colonSplitter)[1]);
            result[1] = 0;
        } else {
            final boolean findPseudo = ident.startsWith("p:");
            final boolean findIngeschrevene = ident.startsWith("i:");

            ResultSet queryResult;
            if (ident.toLowerCase().startsWith("anr:")) {
                final String anr = ident.split(colonSplitter)[1];
                queryResult = firstRow(String.format("SELECT id, lockversie FROM kern.pers WHERE anr = '%s'", anr));
            } else {
                if (findPseudo) {
                    queryResult = firstRow(String.format("SELECT id, lockversie FROM kern.pers WHERE srt = 2 and bsn = '%s'", ident.split(colonSplitter)[1]));
                } else if (findIngeschrevene) {
                    queryResult = firstRow(String.format("SELECT id, lockversie FROM kern.pers WHERE srt = 1 and bsn = '%s'", ident.split(colonSplitter)[1]));
                } else {
                    queryResult = firstRow(String.format("SELECT id, lockversie FROM kern.pers WHERE bsn = '%s'", ident));
                }
            }

            try {
                if (!queryResult.first() || queryResult.next()) {
                    throw new ProcessorException("Geen of meerdere personen gevonden met BSN/Anr " + ident);
                }
                final int versienummerColumnId = 2;
                queryResult.first();
                result[0] = queryResult.getInt(1);
                result[1] = queryResult.getInt(versienummerColumnId);
            } finally {
                queryResult.close();

            }
        }
        return result;
    }
}
