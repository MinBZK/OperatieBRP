package nl.bzk.brp.soapui.reporters

import groovy.xml.MarkupBuilder
import nl.bzk.brp.soapui.Resultaat
import nl.bzk.brp.soapui.Resultaten

import static nl.bzk.brp.soapui.excel.OutputKolommen.Debug_Log
import static nl.bzk.brp.soapui.excel.OutputKolommen.Status

/**
 * Schrijft de rapportage in JUnit XML report format.
 */
class JUnitXmlWriter implements ReportWriter {

    File resultFile
    String testnaam

    /**
     * Constructor.
     *
     * @param file
     */
    JUnitXmlWriter(File file) {
        this(file, '')
    }

    /**
     * Constructor.
     *
     * @param file
     * @param testNaam
     */
    JUnitXmlWriter(File file, String testNaam) {
        this.resultFile = file
        this.testnaam = testNaam
    }

    @Override
    void writeReport(final List<Properties> results) {
        resultFile.write maakXml([:] as Properties, results).toString()
    }

    void writeReport(final Properties settings, final List<Properties> results) {
        resultFile.write maakXml(settings, results).toString()
    }

    private Writer maakXml(Properties settings, List<Properties> testCases) {
        StringWriter writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        Resultaten resultaten = new Resultaten(testCases)

        xml.testsuite(name:testnaam, tests: resultaten.size(), errors: resultaten.errors(), skipped: resultaten.skipped(), failures: resultaten.failed()) {
            if (settings) {
                properties {
                    settings.each { k, v  ->
                        property(name: k, value: v)
                    }
                }
            }
            resultaten.each { Resultaat r ->
                testcase(name: "${r['Testgeval']}-${r['VolgNr']}", classname: "${r['Testgeval']}") {
                    if (r.isFailed()) {
                        error(message: r[Debug_Log.naam].asType(String.class).substring(0, 30), type: 'java.lang.AssertionError') {
                            xml.mkp.yieldUnescaped("<![CDATA[${r[Debug_Log.naam]}]]>")
                        }
                        //'system-out'() { mkp.yieldUnescaped("<![CDATA[${r['debug_log']}]]>") }
                    } else if (r.isSkipped()) {
                        skipped()
                    } else if (r.isError()) {
                        failure(message: r[Debug_Log.naam].asType(String.class).substring(0, 30), type: 'java.lang.Exception') {
                            xml.mkp.yieldUnescaped("<![CDATA[${r[Debug_Log.naam]}]]>")
                        }
                    }
                }
            }
        }

        writer
    }

    /**
     * get the local hostname
     * @return the name of the local host, or "localhost" if we cannot work it out
     */
    private String getHostname()  {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }
}
