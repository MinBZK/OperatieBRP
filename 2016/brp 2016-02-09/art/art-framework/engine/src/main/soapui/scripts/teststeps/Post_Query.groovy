package scripts.teststeps

import groovy.sql.Sql
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.SqlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import org.postgresql.util.PSQLException

/**
 * Post Query, voert additionele data na het testgeval uit.
 */

ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String POST_QUERY = step_CONTROL_VALUES.getPostQuery()
String TEST_ID = step_CONTROL_VALUES.getTestID()

def sqlText = SqlHandler.leesSqlStatements(POST_QUERY, context, step_CONTROL_VALUES)

if (sqlText) {
    Sql sql = null
    try {
        // output file
        String EXECUTED_REQUEST_FILE = "data/request/${TEST_ID}-postquery.sql";

        FileHandler fileHandler = FileHandler.fromContext(context)
        fileHandler.schrijfFile(EXECUTED_REQUEST_FILE, sqlText)

        if (sqlText.contains('MQ::')) {
            sqlText = sqlText.replace('MQ::', '')
            sql = SqlHandler.makeActiveMqSql(context)
        } else {
            sql = SqlHandler.makeSql(context)
        }

        return sql.execute(sqlText)
    } catch (PSQLException e) {
        log.error "Fout in uitvoeren SQL statement: $e.message"
        throw e
    } catch (Exception e) {
        log.error "Fout in uitvoeren Post_Query stap: $e.message"
        throw e
    } finally {
        sql?.close()
    }
}

