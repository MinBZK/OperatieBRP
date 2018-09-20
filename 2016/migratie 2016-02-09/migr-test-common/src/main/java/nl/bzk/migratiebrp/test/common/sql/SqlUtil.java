/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

/**
 * Utility class voor sql communicatie direct met een database.
 */
public final class SqlUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private SqlUtil() {
    }

    /**
     * Voert het sql-script uit op de opgegeven datasource. Als er een fout optreedt tijdens het uitvoeren, dan gaat het
     * script door.
     *
     * @param dataSource
     *            de datasource waar het script op uitgevoerd moet worden
     * @param sqlScript
     *            het uit te voeren sql script. Dit kan bv "SELECT 41+1" zijn. Dit kunnen meerdere statements zijn
     *            gescheiden met een ';'. Een statement kan ook meerdere regels bevatten, zolang deze maar met een ';'
     *            wordt beeindigd.
     * @param continueOnError
     *            true als er door gegaan moet worden als er een fout optreedt
     */
    public static void executeSqlScript(final DataSource dataSource, final String sqlScript, final boolean continueOnError) {
        LOG.info("Executing SQL script");
        final List<String> statements = new LinkedList<String>();
        ScriptUtils.splitSqlScript(sqlScript, ';', statements);
        int lineNumber = 0;
        try (final Connection sqlConnection = dataSource.getConnection()) {
            sqlConnection.setAutoCommit(false);
            try (final Statement sqlStatement = sqlConnection.createStatement()) {
                for (final String statement : statements) {
                    lineNumber++;
                    sqlStatement.executeUpdate(statement);

                }
            }
            sqlConnection.commit();
        } catch (final DataAccessException dae) {
            if (continueOnError) {
                LOG.warn("Failed to execute SQL script statement at line " + lineNumber, dae);
            } else {
                throw dae;
            }
        } catch (final SQLException se) {
            if (continueOnError) {
                LOG.warn("Failed to execute SQL script statement at line " + lineNumber, se);
            } else {
                throw new UncategorizedSQLException("Uitvoeren SQL script", "<unknown>", se);
            }
        }

    }

    /**
     * Voert de query uit op de meegegeven datasource. Het resultaat wordt middels een lijst van key-value paren terug
     * gegeven.
     *
     * @param dataSource
     *            de datasource waar de query uitgevoerd moet worden
     * @param query
     *            de uit te voeren query. Dit kan bv "SELECT 41+1" zijn.
     *
     * @return resultset van de sql
     */
    public static List<Map<String, Object>> executeQuery(final DataSource dataSource, final String query) {
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.queryForList(query);
    }

    /**
     * Voert de sql in het opgegeven bestand uit op de opgegeven datasource. Als er een fout optreedt tijdens het
     * uitvoeren, dan gaat het script door.
     *
     * @param dataSource
     *            de datasource waar de SQL uitgevoerd moet worden
     * @param sqlFileLocation
     *            de locatie en bestandsnaam van het uit te voeren SQL-bestand
     */
    public static void executeSqlFile(final DataSource dataSource, final String sqlFileLocation) {
        try (Connection connection = dataSource.getConnection()) {
            final ResourceLoader resourceLoader = new DefaultResourceLoader();
            ScriptUtils.executeSqlScript(
                connection,
                new EncodedResource(resourceLoader.getResource(sqlFileLocation)),
                true,
                false,
                ScriptUtils.DEFAULT_COMMENT_PREFIX,
                ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
                ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("Kan SQL niet uitvoeren", e);
        }

    }
}
