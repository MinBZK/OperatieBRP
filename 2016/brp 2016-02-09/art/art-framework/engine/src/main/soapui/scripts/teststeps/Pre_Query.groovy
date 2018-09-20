package scripts.teststeps

import groovy.sql.Sql
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.SqlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import org.postgresql.util.PSQLException

/**
 * Pre Query, voert additionele data prepare uit.
 */

ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String PRE_QUERY = step_CONTROL_VALUES.getPreQuery()
String TEST_ID = step_CONTROL_VALUES.getTestID()

def sqlText = SqlHandler.leesSqlStatements(PRE_QUERY, context, step_CONTROL_VALUES)

if (sqlText) {
    Sql sql = null
    try {
        // output file
        String EXECUTED_REQUEST_FILE = "data/request/${TEST_ID}-prequery.sql"

        FileHandler fileHandler = FileHandler.fromContext(context)
        fileHandler.schrijfFile(EXECUTED_REQUEST_FILE, sqlText)

        if (sqlText.contains('MQ::')) {
            sqlText = sqlText.replace('MQ::', '')
            sql = SqlHandler.makeActiveMqSql(context)
        } else {
            sql = SqlHandler.makeSql(context)
        }

        if (sqlText.toUpperCase().startsWith('SELECT')) {
            //alleen een select query geeft een return waarde
            def result = sql.firstRow(sqlText);
            if (result == null) {
                throw new IllegalStateException("Pre query leverde geen resultaat op: " + sqlText)
            }
            return result[0]
        } else {
            return sql.execute(sqlText)
        }
    } catch (PSQLException e) {
        log.error "Fout in uitvoeren SQL statement: $e.message"
        throw e
    } catch (Exception e) {
        log.error "Fout in uitvoeren Pre_Query stap: $e.message"
        throw e
    } finally {
        sql?.close()
    }
}

