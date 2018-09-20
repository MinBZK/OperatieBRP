package scripts.teststeps

import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.ObjectSleutelHandler
import nl.bzk.brp.soapui.handlers.XmlHandler
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues

/**
 * Bouwt de SOAP request op. Dit script leest een xml template in en vervangt alle placeholders met
 * waarden uit de 'DataSource Values'.
 */

AssertionResults step_ASSERTION_RESULTS = AssertionResults.fromContext(context)
FileHandler fileHandler = FileHandler.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String STATUS = step_CONTROL_VALUES.getStatus()
String QUARANTAINE = 'QUARANTAINE'
String currentTimestamp = step_CONTROL_VALUES.getExecutionTimestamp()


// Input file
String PROJECT_PATH = ARTVariabelen.getScriptPath(context)
String REQUEST_TEMPLATE_FILE = ARTVariabelen.getRequestTemplateFile(context)

String requestTemplate = "${PROJECT_PATH}/${REQUEST_TEMPLATE_FILE}"

String altExcelPath = ARTVariabelen.getAlternativeScriptPath(context)
if (altExcelPath) {
    //overruled requestTemplate
    requestTemplate = "${altExcelPath}/${REQUEST_TEMPLATE_FILE}"
}

try {
    def templateText = ObjectSleutelHandler.vervangBsnsDoorObjectSleutels(context, new File(requestTemplate), log)
    String xml = XmlHandler.fromContext(context).leesEnParseTemplate(templateText, currentTimestamp)

    return xml
} catch (Exception e) {
    if (STATUS == QUARANTAINE) {
        step_ASSERTION_RESULTS.update('[Build Request]', QUARANTAINE, step_CONTROL_VALUES, e)
    } else {
        step_ASSERTION_RESULTS.update('[Build Request]', 'FAILED', step_CONTROL_VALUES, e)
        throw new Exception(e)
    }
}
