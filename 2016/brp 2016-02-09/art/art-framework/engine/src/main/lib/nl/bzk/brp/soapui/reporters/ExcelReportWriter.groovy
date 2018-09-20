package nl.bzk.brp.soapui.reporters

import groovy.transform.CompileStatic
import nl.bzk.brp.soapui.excel.OutputExcel

/**
 * Schrijft de rapportage in een Excel format.
 */
@CompileStatic
class ExcelReportWriter implements ReportWriter {

    final String resultSheet = 'resultaat'
    final File sampleXls
    final File resultXls

    /**
     * Constructor.
     *
     * @param templateXls
     * @param resultXls
     */
    ExcelReportWriter(File templateXls, File resultXls) {
        this.sampleXls = templateXls
        this.resultXls = resultXls
    }

    @Override
    void writeReport(final List<Properties> results) {
        try {
            OutputExcel excel = new OutputExcel(resultXls, sampleXls, resultSheet)

            excel.schrijfResultaten(results)
        } finally {
        }
    }
}
