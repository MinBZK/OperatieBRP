package scripts.teststeps

import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.reporters.ExcelReportWriter
import nl.bzk.brp.soapui.reporters.JUnitXmlWriter
import nl.bzk.brp.soapui.reporters.PlainTextReportWriter

/**
 * Deze stap haalt alle asserts uit de context en schrijft naar een 'resultaten.xsl' bestand. De xsl bestand is een 'kopie'
 * van een leeg 'result_template.xsl'
 * Als de result_template.xls niet bestaat of als er iets fout gaat, wordt alle informatie weggeschreven naar een text bestand
 * 		'report.txt'
 * Als een van de kolomnamen onbekend is, wordt ook een fout gegenererrd en alles wordt naar txt file weggeschreven.
 */

FileHandler fileHandler = FileHandler.fromContext(context)

File xlsFile = FileHandler.vindVolgendeBestand("${fileHandler.getOutputDir().absolutePath}/report", 'resultaten.xls')
File voorbeeldXls = fileHandler.geefProjectFile('result_template.xls')

if (voorbeeldXls.exists()) {
    try {
        writeExcelReport(voorbeeldXls, xlsFile)
        writeJUnitReport(fileHandler.geefOutputFile("report/TEST_${ARTVariabelen.getInputFile(context)}.xml"), ARTVariabelen.getInputFile(context))
    } catch (Exception e) {
        writeFileReport(fileHandler.geefOutputFile('report/report.txt'), "[Create Excel Report] $e.message")
        log.error "fout bij schrijven report: ${e.message}"
        throw e;
    }
} else {
    log.error "Kan resultTemplate niet vinden op  ${voorbeeldXls.absolutePath}"
    writeFileReport(fileHandler.geefOutputFile('report/report.txt'), null)
}

// -- Private methods -----------------------------
/*
 * Schrijft de resultaten naar een nieuw excel bestand (afgeleid uit een template excel).
 */
private void writeExcelReport(File voorbeeldXls, File xlsFile) throws Exception {
    List statusList = context.get("statusList")
    new ExcelReportWriter(voorbeeldXls, xlsFile).writeReport(statusList)
}

/*
 * Schrijft de resultaten naar een nieuw xunit xml bestand.
 */
private void writeJUnitReport(File reportFile, String testnaam) throws Exception {
    List statusList = context.get("statusList")
    new JUnitXmlWriter(reportFile, testnaam).writeReport(statusList)
}

/*
 * Schijf de rapportage weg naar een bestand, aangezien we geen excel kunnen aanmaken.
 */
private void writeFileReport(File txtFile, String extraText) {
    List statusList = context.get("statusList");
    new PlainTextReportWriter(txtFile, extraText).writeReport(statusList)
}
