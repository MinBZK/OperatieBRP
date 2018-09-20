package scripts.teststeps

import groovy.sql.Sql
import nl.bzk.brp.soapui.handlers.SqlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import org.postgresql.util.PSQLException

/**
 * Herstelt de opgegeven persoon(en) door middel van een sql opgeslagen in dezelfde database.
 */

Sql sql = null
try {
    sql = SqlHandler.makeSql(context)
    String DB_RESET_PERSONEN = ControlValues.fromContext(context).getPrepareData()

    // PrepareData
    DB_RESET_PERSONEN.tokenize(",").each {
        def bsn_anr = Long.valueOf(it.trim())
        def sqlRows = sql.rows("select sql from test.referentie where bsn = ${bsn_anr}")

        def sqlStatements = []
        if (sqlRows.size()) {
            sqlRows.each { row ->
                sqlStatements << row.sql
            }
        } else {
            sqlStatements << """
                DELETE FROM kern.relatie r
                USING kern.betr b, kern.pers p
                WHERE r.id = b.relatie
                AND b.pers = p.id
                AND (p.bsn IN (${bsn_anr}) OR p.anr IN (${bsn_anr}));
                DELETE FROM kern.pers p WHERE (p.bsn IN (${bsn_anr}) or p.anr IN (${bsn_anr}));"""
        }

        sql.execute sqlStatements.join('\n')
    }
} catch (Exception e) {
    log.error "Fout in uitvoeren SQL statement: $e.message"
    throw e
} finally {
    sql?.close()
}
