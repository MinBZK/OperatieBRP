package nl.bzk.brp.soapui.reporters

import groovy.transform.CompileStatic

/**
 * Schrijft de rapportage in een plain text format.
 */
@CompileStatic
class PlainTextReportWriter implements ReportWriter {

    final File report
    final String comment

    /**
     * Constructor.
     *
     * @param resultFile
     * @param extra
     */
    PlainTextReportWriter(File resultFile, String extra) {
        report = resultFile
        comment = extra
    }

    @Override
    public void writeReport(final List<Properties> results) {
        report.write("");
        if (results) {
            results.each { prop ->
                report.append("${prop}\n")
            }
        }
        if (comment) {
            report.append("\n${comment}\n")
        }
    }
}
