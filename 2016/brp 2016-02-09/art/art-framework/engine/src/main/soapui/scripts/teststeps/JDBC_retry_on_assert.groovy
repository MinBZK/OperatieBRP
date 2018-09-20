package scripts.teststeps

import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.XmlHandler
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.JdbcRetries

/*
 * Deze teststap is bedoeld om retries te doen en is niet bedoeld om een assertion te geven.
 * Assert JDBC request geeft de assertion.
 */

FileHandler fileHandler = FileHandler.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
JdbcRetries step_JDBC_RETRIES = JdbcRetries.fromContext(context)
String TEST_ID = step_CONTROL_VALUES.getTestID()
String STATUS = step_CONTROL_VALUES.getStatus()
String QUARANTAINE = 'QUARANTAINE'
def retryOnAssertion = false

try {
    // Input file
    File expectedResultFile = fileHandler.geefVerwachtBestand("data/${TEST_ID}-dataresponse.xml")
    
    // Gegevens voor assertions
    String DB_RESULT_XPATH = step_CONTROL_VALUES.getDbResponseQuery() ?: '//Results'
    String RESPONSE_AS_XML = XmlHandler.cleanupDatabaseResult(context.expand('${JDBC Request#ResponseAsXml}'))
    
    //er wordt geen retry gedaan op testgevallen die in QUARANTAINE staan
    if (STATUS == QUARANTAINE) {
        return QUARANTAINE
    }
        
    // Lees de expected results
    long currentTimestamp = step_CONTROL_VALUES.getExecutionTime()
    String xml = fileHandler.processExpectedFile(expectedResultFile, currentTimestamp)

    xml = xml.replace("&lt;", "<").replace("&gt;", ">")

    //Controleer of xml niet leeg is.
    assert xml: "Expected result mag niet leeg zijn."

    // Vraag gedeelte op van de expected results om de vergelijking erop los te laten
    def node = XmlHandler.fromContext(context).haalNodeUitXml(xml, DB_RESULT_XPATH)

    // Maak de assertions aan
    if (node == null) {
        throw new Exception("De node kon niet gevonden worden in de expected result met de opgegeven DB_result_tag: $DB_RESULT_XPATH")
    } else {
        try {
            XmlHandler.fromContext(context).vergelijk(DB_RESULT_XPATH, xml, RESPONSE_AS_XML)
        } catch (Exception e) {
            log.info "JDBC retry on real assertion"
            retryOnAssertion = true
        }
    }
} catch (Exception e) {
    log.info "JDBC retry on assertion :: other expection :: ${e.message}"
    retryOnAssertion = true
}

if (retryOnAssertion) {
    //ongelijk xml bericht retry doen indien gewenst
    int retry = step_JDBC_RETRIES.getRetries()

    if (retry > 0) {
        Integer numberOfRetries = 4 - retry
        RESPONSE_AS_XML = context.expand('${JDBC Request#ResponseAsXml}').replace("&lt;", "<").replace("&gt;", ">")

        fileHandler.schrijfFile("data/response/${TEST_ID}-dataresponse_poging_${numberOfRetries}.xml", RESPONSE_AS_XML)
        step_JDBC_RETRIES.setRetries(--retry)

        // zet een delay aan
        def step = testRunner.testCase.testSteps["Delay ${numberOfRetries}"].disabled = false;
        //gehele testgeval nog een keer uitvoeren
        testRunner.gotoStepByName('Prepare Database Data')
    }
}
