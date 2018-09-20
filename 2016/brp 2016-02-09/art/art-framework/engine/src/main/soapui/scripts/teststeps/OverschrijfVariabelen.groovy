package scripts.teststeps

import nl.bzk.brp.soapui.excel.InputKolommen
import nl.bzk.brp.soapui.handlers.OverschrijfVariabelenHandler
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.DataSourceValues

/**
 * Leest de data uit de overschrijf-variabelen kolom.
 */

try {
    DataSourceValues step_DATASOURCE_VALUES = DataSourceValues.fromContext(context)

    String overschijfVariabelenString = ControlValues.fromContext(context).getOverschrijfVariabelen()
    OverschrijfVariabelenHandler.laadOverschrijfVariabelen(step_DATASOURCE_VALUES, overschijfVariabelenString)

} catch (Exception e) {
    log.error "Fout bij het laden van de overschrijf-variabelen. Fout: $e.message"
    throw e
}
