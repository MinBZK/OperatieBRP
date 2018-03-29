/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.sql.SqlUtil;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * SQL Helper.
 */
public final class SqlHelper {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int CHECK_SQL_EXISTS_HERHALINGEN = 10;
    private static final int CHECK_SQL_EXISTS_TIMEOUT = 1000;

    private static final Map<String, SqlData> CHECK_SQL_MAP = new HashMap<>();

    /**
     * Uitvoeren SQL.
     * @param datasource database datasource
     * @param script sql
     * @param continueOnError doorgaan bij SQL fouten
     */
    public void uitvoerenSql(final DataSource datasource, final String script, final boolean continueOnError) {
        SqlUtil.executeSqlScript(datasource, script, continueOnError);
    }

    /**
     * Registreer een sql om later te checken.
     * @param berichtReferentie bericht referentie
     * @param dataSource database dataSource
     * @param sql sql
     */
    public void checkSql(final String berichtReferentie, final DataSource dataSource, final String sql) {
        CHECK_SQL_MAP.put(berichtReferentie, new SqlData(dataSource, sql));
    }

    /**
     * Lees een resultset uit.
     * @param bericht verwacht bericht (gebruikt voor referentie om sql geregistreerd met {@link #checkSql} op te zoeken)
     * @return ontvangen bericht
     * @throws KanaalException bij fouten
     */
    public Bericht checkSqlResult(final Bericht bericht) throws KanaalException {
        if (!CHECK_SQL_MAP.containsKey(bericht.getCorrelatieReferentie())) {
            throw new KanaalException("check_sql_result resultaat verwachting niet gecorreleerd aan check_sql_xxx sql bericht");
        }

        final SqlData check = CHECK_SQL_MAP.get(bericht.getCorrelatieReferentie());
        final String result = readSql(check.database, check.sql);

        final Bericht ontvangenBericht = new Bericht();
        ontvangenBericht.setInhoud(result);

        return ontvangenBericht;
    }

    /**
     * Lees iets uit een database obv de gegeven SQL query.
     *
     * Geef het resultaat terug in het volgende xml formaat:
     *
     * {@literal
     * <resultaat count="2">
     * <rij><kolomA>kolomAWaarde</kolomA><kolomB>kolomBWaarde</kolomB><rij>
     * <rij><kolomA>kolomAWaarde</kolomA><kolomB>kolomBWaarde</kolomB><rij>
     * </resultaat>
     * }
     *
     * Waarbij het attribuut count wordt gevuld met het totaal aan gelezen rijen en per rij een rij element wordt
     * getoegevoegd met per kolom een element met de kolomnaam en de waarde.
     * @param dataSource database dataSource
     * @param sql uit te voeren sql
     * @return resultaat in gegeven XML formaat.
     * @throws KanaalException In het geval er iets misgaat.
     */
    public String readSql(final DataSource dataSource, final String sql) throws KanaalException {
        // @formatter:off
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            // @formatter:on
            final StringBuilder dataXml = new StringBuilder();
            int count = 0;
            while (resultSet.next()) {
                count++;

                dataXml.append("<rij>");
                for (int columnIndex = 1; columnIndex <= resultSet.getMetaData().getColumnCount(); columnIndex++) {
                    final String columnName = resultSet.getMetaData().getColumnName(columnIndex);
                    dataXml.append('<').append(columnName).append('>');

                    final Object columnValue = resultSet.getObject(columnIndex);
                    if (columnValue != null) {
                        dataXml.append(StringEscapeUtils.escapeXml11(columnValue.toString()));
                    }

                    dataXml.append("</").append(columnName).append('>');
                }

                dataXml.append("</rij>\n");
            }

            final StringBuilder completeXml = new StringBuilder("<?xml version=\"1.0\"?>\n");
            completeXml.append("<result count=\"").append(count).append("\">\n");
            completeXml.append(dataXml);
            completeXml.append("</result>\n");

            return completeXml.toString();
        } catch (final SQLException e) {
            throw new KanaalException("Probleem met lezen uit database", e);
        }
    }

    /**
     * Leest een object uit de database op basis van een select query (bijvoorbeeld een ID). Let op: Deze methode lees
     * alleen het resultaat uit de eerst kolom van de eerste rij!
     * @param dataSource De te gebruiken database dataSource.
     * @param sql De uit te voeren sql.
     * @return Het opgehaalde Object.
     * @throws KanaalException Exceptie op het moment dat er iets misgaat.
     */
    public Object readSingleSelectSqlReturnObject(final DataSource dataSource, final String sql) throws KanaalException {

        // @formatter:off
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getObject(1);
            } else {
                return null;
            }

        } catch (final SQLException e) {
            throw new KanaalException("Probleem met lezen (select) uit database", e);
        }
    }

    /**
     * Controleer dat een SQL resultaat geeft. Loopt een aantal keer als er geen resultaat wordt gevonden
     * @param bericht verwacht bericht (gebruikt voor referentie om sql geregistreerd met {@link #checkSql} op te zoeken)
     * @return true, als er een resultaat is
     * @throws KanaalException bij fouten
     */
    public boolean checkSqlExists(final Bericht bericht) throws KanaalException {
        if (!CHECK_SQL_MAP.containsKey(bericht.getCorrelatieReferentie())) {
            throw new KanaalException("check_sql_exists resultaat verwachting niet gecorreleerd aan check_sql_xxx sql bericht");
        }
        final SqlData check = CHECK_SQL_MAP.get(bericht.getCorrelatieReferentie());

        int count = 0;
        while (count < CHECK_SQL_EXISTS_HERHALINGEN) {
            count++;
            try {
                if (readSqlExists(check.database, check.sql)) {
                    return true;
                }

                TimeUnit.MILLISECONDS.sleep(CHECK_SQL_EXISTS_TIMEOUT * count);
            } catch (final InterruptedException e1) {
                LOG.debug("sleep() interrupted.", e1);
            }
        }

        return false;
    }

    private boolean readSqlExists(final DataSource dataSource, final String sql) throws KanaalException {
        // @formatter:off
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            // @formatter:on
            return resultSet.next();
        } catch (final SQLException e) {
            throw new KanaalException("Probleem met lezen (exists) uit database", e);
        }
    }

    /**
     * Clean database.
     * @param dataSource database dataSource
     * @param scriptResource uit te voeren resource
     */
    void opschonenDatabase(final DataSource dataSource, final String scriptResource) {
        SqlUtil.executeSqlFile(dataSource, scriptResource);
    }

    /**
     * SQL Data.
     */
    private static final class SqlData {
        private final DataSource database;
        private final String sql;

        /**
         * Constructor.
         * @param database database
         * @param sql sql
         */
        SqlData(final DataSource database, final String sql) {
            this.database = database;
            this.sql = sql;
        }
    }

}
