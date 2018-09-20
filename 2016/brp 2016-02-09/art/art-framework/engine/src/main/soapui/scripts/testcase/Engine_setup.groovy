package scripts.testcase

import com.eviware.soapui.support.UISupport
import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.PropertiesHandler
import nl.bzk.brp.soapui.handlers.TestRequestTestStepHandler

/**
 *
 */
String SCRIPT_LOCATION = ARTVariabelen.getScriptLocation(context)

if (!SCRIPT_LOCATION) {
    UISupport.showErrorMessage('Kan geen instellingen vinden voor ARTEngine, controleer de properties van het project en draai het setup script.')
    return false
}
log.info "---- Script path = ${SCRIPT_LOCATION}"

/* Zet properties correct */
PropertiesHandler.clearTestProperties(testRunner.testCase)
loadProperties(SCRIPT_LOCATION, 'test.properties')

/* Zet output directory / files correct */
FileHandler.fromContext(context).createOutputDirectories()

/* Zet 'Request Step' correct */
TestRequestTestStepHandler.resetOperation(testRunner, context, log)

//Zet variable klaar voor excel rapportage
context.put('statusList', new ArrayList<Properties>())

// -- Private methods --------------------------------------------------------
private void loadProperties(String dir, String filename) {
    Properties props = PropertiesHandler.loadProperties(dir, filename)
    log.info "[TestCase Setup Script] Goed geladen bestand: ${dir}, ${filename}"

    props.put(ARTVariabelen.TEST_SCRIPT_PATH, dir.replace('\\', '/'))
    PropertiesHandler.setProperties(props, testRunner.testCase)
}
