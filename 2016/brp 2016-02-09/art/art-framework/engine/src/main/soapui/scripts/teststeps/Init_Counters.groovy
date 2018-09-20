package scripts.teststeps

import com.eviware.soapui.SoapUI
import nl.bzk.brp.soapui.excel.InputExcel
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.steps.Counters
import org.apache.commons.lang.StringUtils

/**
 * Deze stap initialiseert de stappen.
 * {@code Counters} is de stap die de tellers bevat die tellen welke rijen uit de ART Excel
 * worden uitgevoerd. De tellers komen overeen met de rij nummers in Excel.
 *
 * Kan er geen Excel-bestand geopend worden, is deze niet valide, of komt niet overeen
 * met de waardes in instellingen, dan wordt naar de laatste stap doorgegaan.
 */

Counters step_COUNTERS = Counters.fromContext(context)

// Stel de default waarde in van Counters
step_COUNTERS.setExecuteRow(2)
step_COUNTERS.setMaxRows(2)

// Lees excel sheet
InputExcel sheet = FileHandler.fromContext(context).geefArtExcel(true)

// Stel de counters in.
// Vanuit de POM kunnen de waarden voor startRow en endRow worden meegegeven. Deze worden hier dan ingesteld.
// Anders wordt de Excel gebruikt om de endRow te bepalen, de startRow is default 2.

def startRow = SoapUI.globalProperties.getPropertyValue("startRow")
def endRow = SoapUI.globalProperties.getPropertyValue("endRow")

if (StringUtils.isNotEmpty(startRow) || StringUtils.isNotEmpty(endRow)) {
    if (StringUtils.isNotEmpty(startRow) && startRow.isInteger()) {
        step_COUNTERS.setExecuteRow(startRow.toInteger())
    }
    if (StringUtils.isNotEmpty(endRow) && endRow.isInteger()) {
        step_COUNTERS.setMaxRows(endRow.toInteger())
    }

    log.info "Van stap: ${step_COUNTERS.getExecuteRow()} tot stap: ${step_COUNTERS.getMaxRows()}"
} else if (sheet) {
    step_COUNTERS.setMaxRows(sheet.laatsteRij())

    log.info "Aantal stappen: ${step_COUNTERS.getMaxRows()}"
} else{
    testRunner.gotoStepByName('Set Assertion Results')
}
