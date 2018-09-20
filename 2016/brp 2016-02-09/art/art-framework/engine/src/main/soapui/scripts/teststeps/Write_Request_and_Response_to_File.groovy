package scripts.teststeps

import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.XmlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.TestRequest

/**
 * Schrijft het uitgevoerde SOAP request en het ontvangen antwoord naar
 * de resultaten directory.
 */

ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String TEST_ID = step_CONTROL_VALUES.getTestID()

String ACTUAL_RESPONSE_FILE = "soap/response/${TEST_ID}-soapresponse.xml"
String ACTUAL_REQUEST_FILE  = "soap/request/${TEST_ID}-soaprequest.xml"

try {
    TestRequest step_TEST_REQUEST = TestRequest.fromContext(context)

    FileHandler fileHandler = FileHandler.fromContext(context)
    fileHandler.schrijfFile(ACTUAL_REQUEST_FILE, step_TEST_REQUEST.getRequest())

    String xmlResponse = step_TEST_REQUEST.getSoapBody()

    if (xmlResponse.isEmpty()) {
        xmlResponse = step_TEST_REQUEST.getResponse()
        step_TEST_REQUEST.disableSoapResponseAssert()
    }

    if (xmlResponse) {
	xmlResponse = XmlHandler.fromContext(context).prettyfyXml(xmlResponse)
    }

    fileHandler.schrijfFile(ACTUAL_RESPONSE_FILE, xmlResponse)
} catch (Exception e) {
    if ('QUARANTAINE' == step_CONTROL_VALUES.getStatus()) {
        log.info "${e.message}"
    } else {
        throw e
    }
} finally {
}
