package scripts.teststeps

import groovy.sql.Sql
import nl.bzk.brp.soapui.excel.DataSheets
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.SqlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.DataSourceValues
import org.postgresql.util.PSQLException

/**
 * Leest data voor de xml request uit een excel sheet (worksheet = 'Data')
 * De inhoud is verticaal, dwz. alle attribuutnamen staan in de 1e kolom, op de 1e rij staat de testGeval naam
 */

Sql sql = null
try {
    // maak datasource values leeg voordat er nieuwe waardes worden ingelezen
    DataSourceValues step_DATASOURCE_VALUES = DataSourceValues.fromContext(context)
    step_DATASOURCE_VALUES.clearProperties()

    // ga testkolom laden
    sql = SqlHandler.makeSql(context)
    String testGeval = ControlValues.fromContext(context).getTestGeval()
    DataSheets dataSheets = FileHandler.fromContext(context).geefDataSheets()
    log.info 'ART sheet geladen'

    dataSheets.laadKolomVanTestGeval(testGeval, sql, step_DATASOURCE_VALUES)
    log.info 'Datasheet kolom geladen in \'Datasource Values\''

} catch (PSQLException e) {
    log.error "Fout in laden sql-data vanuit datasheet: $e.message"
    throw e
}  catch (Exception e) {
    log.error "Fout in laden datasheet: $e.message"
    throw e
} finally {
    sql?.close()
}
