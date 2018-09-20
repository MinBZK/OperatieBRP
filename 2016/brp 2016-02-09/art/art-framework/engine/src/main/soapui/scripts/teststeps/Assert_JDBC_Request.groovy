package scripts.teststeps

import groovy.xml.XmlUtil
import nl.bzk.brp.soapui.AssertionMisluktExceptie
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.XmlHandler
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues

/**
 * Controleert de response van de stap "JDBC Request".
 */

// Gegevens voor assertions
AssertionResults step_ASSERTION_RESULTS = AssertionResults.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String TEST_ID = step_CONTROL_VALUES.getTestID()
String STATUS = step_CONTROL_VALUES.getStatus()
String QUARANTAINE = 'QUARANTAINE'

try {
    // Lees de expected results
    FileHandler fileHandler = FileHandler.fromContext(context)
    File EXPECTED_RESULT = fileHandler.geefVerwachtBestand("data/${TEST_ID}-dataresponse.xml")
    String PROCESSED_EXPECTED_RESULT = "data/expected_response/${TEST_ID}-dataresponse.xml"
    String ACTUAL_RESPONSE_FILE = "data/response/${TEST_ID}-dataresponse.xml";

    // Gegevens voor assertions
    String DB_RESULT_XPATH = step_CONTROL_VALUES.getDbResponseQuery() ?: '//Results'
    String RESPONSE_AS_XML = XmlHandler.cleanupDatabaseResult(context.expand('${JDBC Request#ResponseAsXml}'))

    long currentTimestamp = step_CONTROL_VALUES.getExecutionTime()
    String xml = fileHandler.processExpectedFile(EXPECTED_RESULT, currentTimestamp)

    fileHandler.schrijfFile(PROCESSED_EXPECTED_RESULT, xml)
    fileHandler.schrijfDiff(PROCESSED_EXPECTED_RESULT, ACTUAL_RESPONSE_FILE, "data/response/${TEST_ID}-dataresponse.diff", true)

    //Controlleer of xml niet leeg is.
    assert xml: "Expected result mag niet leeg zijn."

    // Vraag gedeelte op van de expected results om de vergelijking erop los te laten
    def node = XmlHandler.fromContext(context).haalNodeUitXml(xml, DB_RESULT_XPATH)

    // Maak de assertions aan
    if (node == null) {
        throw new Exception("De node kon niet gevonden worden in de expected result met de opgegeven DB_result_tag: $DB_RESULT_XPATH")
    } else {

        try {
            XmlHandler.fromContext(context).vergelijk(DB_RESULT_XPATH, xml, RESPONSE_AS_XML)
            setAssertionResults(step_ASSERTION_RESULTS, 'OK', new AssertionMisluktExceptie())
        } catch (Exception e) {
            //assertion exceptions
            logAssertionException(DB_RESULT_XPATH, node, RESPONSE_AS_XML)
            if (STATUS == QUARANTAINE) {
                setAssertionResults(step_ASSERTION_RESULTS, QUARANTAINE, e)
            } else {
                setAssertionResults(step_ASSERTION_RESULTS, 'FAILED', e)
                throw new Exception(e)
            }
        }
    }
} catch (Exception e) {
    //other exceptions
    if (STATUS == QUARANTAINE) {
        setAssertionResults(step_ASSERTION_RESULTS, QUARANTAINE, e)
    } else {
        throw new Exception(e)
    }
} finally {
}

// -- Private methods -----------------------------
/*
 * Haal node op uit de expected xml
 */

private def haalNodeOpUitXml(String xml, String xpath) {
    return XmlHandler.fromContext(context).haalNodeUitXml(xml, xpath)
}

/*
 * Werkt de stap "Assertion Results" bij.
 */

private void setAssertionResults(AssertionResults assertionResults, String status, Exception e) {
    assertionResults.update('[JDBC]', status, ControlValues.fromContext(context), e)
}

/*
 * Als er een exceptie is opgetreden, log de informatie naar de logger.
 */

private void logAssertionException(String result_tag, def expectedNode, def actualXml) {
    log.info 'Expected node :'
    log.info XmlUtil.serialize(expectedNode);
    def actualNode = haalNodeOpUitXml(actualXml, result_tag);
    log.info 'Actual node :'
    if (actualNode != null) {
        log.info XmlUtil.serialize(actualNode);
    } else {
        log.info "result_tag : $result_tag' niet gevonden"
    }
}
