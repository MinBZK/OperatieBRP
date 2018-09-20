package scripts.teststeps

import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.steps.ControlValues

/**
 * Schrijft het uitgevoerde JDBC Request en het ontvangen antwoord naar
 * de resultaten directory.
 */

FileHandler fileHandler = FileHandler.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)

String TEST_ID = step_CONTROL_VALUES.getTestID()

String ACTUAL_RESPONSE_FILE = "data/response/${TEST_ID}-dataresponse.xml";
String ACTUAL_REQUEST_FILE = "data/request/${TEST_ID}-datarequest.sql";

// request
fileHandler.schrijfFile(ACTUAL_REQUEST_FILE, testRunner.testCase.getTestStepByName("JDBC Request").getQuery() as String)

// response
String xmlJDBCResponse = context.expand('${JDBC Request#ResponseAsXml}').replace("&lt;", "<").replace("&gt;", ">")
fileHandler.schrijfFile(ACTUAL_RESPONSE_FILE, xmlJDBCResponse)
