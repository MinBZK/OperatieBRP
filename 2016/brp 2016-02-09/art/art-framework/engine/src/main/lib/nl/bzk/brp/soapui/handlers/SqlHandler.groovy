package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext
import groovy.sql.Sql
import groovy.transform.CompileStatic
import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil

/**
 * Afhandeling van SQL zaken.
 */
@CompileStatic
class SqlHandler {

    /**
     * Maakt een {@link Sql} instantie voor de BRP database.
     *
     * @param context de SoapUI context
     * @return een SQL instantie
     */
    static Sql makeSql(def context) {
        String USERNAME = ARTVariabelen.getDbUser(context)
        String PASSWORD = ARTVariabelen.getDbPassword(context)
        String URL = ARTVariabelen.getDbUrl(context)
        String DRIVER = ARTVariabelen.getDbDriver(context)

        def props = [user: USERNAME, password: PASSWORD, allowMultiQueries: 'true'] as Properties

        Sql.newInstance(URL, props, DRIVER)
    }

    /**
     * Maakt een {@link Sql} instantie voor de ActiveMQ database.
     *
     * @param context de SoapUI context
     * @return een SQL instantie
     */
    static Sql makeActiveMqSql(def context) {
        String USERNAME = ARTVariabelen.getDbUser(context)
        String PASSWORD = ARTVariabelen.getDbPassword(context)
        String URL = ARTVariabelen.getMqUrl(context)
        String DRIVER = ARTVariabelen.getDbDriver(context)

        def props = [user: USERNAME, password: PASSWORD, allowMultiQueries: 'true'] as Properties

        Sql.newInstance(URL, props, DRIVER)
    }

    /**
     * Maakt een connection string, voor gebruik door SoapUI.
     *
     * @param context de SoapUI context
     * @param sql de uit te voeren SQL statements
     * @return een connection string, naar de juiste database
     */
    static String buildConnectionString(def context, String sql) {
        String connection = '${#Project#ds_url}?user=${#Project#ds_user}&password=${#Project#ds_password}'

        if (sql.contains('MQ::')) {
            connection = connection.replace('ds_url', 'mq_url')
        }

        connection
    }

    /**
     * Neemt de gegeven waarde, en kijkt of dit een SQL statement is, of een verwijzing naar een .sql bestand.
     * Het SQL statement of de inhoud van het bestand wordt verwerkt, de mogelijke placeholders vervangen.
     *
     * @param veld de waarde, SQL statement of referentie
     * @param context de SoapUI context
     * @return een sql statement.
     *
     * @see DateSyntaxTranslatorUtil#formatTijd(java.lang.Long, java.lang.String)
     */
    static String leesSqlStatements(String veld, PropertyExpansionContext context, ControlValues controlValues) {
        def sqlText = context.expand(veld)

        // Gebruik waarde rechstreeks als Query of
        if (veld?.endsWith('.sql')) {
            // Lees uit bestand
            File file = FileHandler.fromContext(context).geefAfhankelijkheid("SQL/${veld}")
            sqlText = context.expand(file.text)
        }

        long timestamp = ControlValues.fromContext(context).getExecutionTime()
        sqlText = DateSyntaxTranslatorUtil.formatTijd(timestamp, sqlText ?: '')

        sqlText.replace('= []', 'is null').replace('[tijdstipregistratie]', "'${controlValues.getLeveringTsRegistratie()}'")
    }
}
