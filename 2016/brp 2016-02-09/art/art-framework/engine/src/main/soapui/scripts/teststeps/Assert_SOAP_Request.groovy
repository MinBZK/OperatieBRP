package scripts.teststeps

import com.eviware.soapui.model.testsuite.AssertionException
import groovy.xml.XmlUtil
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.XmlHandler
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.TestRequest

/**
 * Deze stap vergelijkt het ontvangen antwoord met het verwachte antwoord.
 */

AssertionResults step_ASSERTION_RESULTS = AssertionResults.fromContext(context)
FileHandler fileHandler = FileHandler.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String TEST_ID = step_CONTROL_VALUES.getTestID()
String STATUS = step_CONTROL_VALUES.getStatus()
String QUARANTAINE = 'QUARANTAINE'

try {
    // Gegevens voor assertions
    String SOAP_RESULT_XPATH = step_CONTROL_VALUES.getSoapResponseQuery()

    File EXPECTED_RESPONSE = fileHandler.geefVerwachtBestand("response/${TEST_ID}-soapresponse.xml")

    String ACTUAL_RESPONSE_FILE = "soap/response/${TEST_ID}-soapresponse.xml"
    String PROCESSED_EXPECTED_RESPONSE = "soap/expected_response/${TEST_ID}-soapresponse.xml"

    // Lees de expected results
    long currentTimestamp = step_CONTROL_VALUES.getExecutionTime()
    String xml = fileHandler.processExpectedFile(EXPECTED_RESPONSE, currentTimestamp)
    fileHandler.schrijfFile(PROCESSED_EXPECTED_RESPONSE, xml)
    fileHandler.schrijfDiff(PROCESSED_EXPECTED_RESPONSE, ACTUAL_RESPONSE_FILE, "soap/response/${TEST_ID}-soapresponse.diff", true)

    // Vraag gedeelte op van de expected results om de vergelijking erop los te laten
    def node = haalNodeOpUitXml(xml, SOAP_RESULT_XPATH)

    // Maak de assertions aan
    if (node == null) {
        throw new Exception("De node kon niet gevonden worden in de expected result [${EXPECTED_RESPONSE.absolutePath}] met de opgegeven soap_result_tag: $SOAP_RESULT_XPATH")
    } else {
        XmlHandler xmlHandler = XmlHandler.fromContext(context)
        def response = xmlHandler.prettyfyXml(TestRequest.fromContext(context).getSoapBody())

        try {
            // maak tijdstipregistratie uit response beschikbaar voor later
            def tsReg = haalNodeOpUitXml(response, '//brp:tijdstipRegistratie')?.textContent
            step_CONTROL_VALUES.setLeveringTsRegistratie(tsReg)
        } catch (Exception e) {
            log.warn 'Geen tijdstipRegistratie beschikbaar'
        }

        try {
            xmlHandler.vergelijk(SOAP_RESULT_XPATH, xml, response)
            setAssertionResults(step_ASSERTION_RESULTS, 'OK', new AssertionException())
        } catch (Exception e) {
            //assertion exceptions
            logAssertionException(SOAP_RESULT_XPATH, node, response)
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


// -- Private methods --------------------------------
/**
 * Haal node op uit de expected xml
 */
private def haalNodeOpUitXml(String xml, String xpath) {
    return XmlHandler.fromContext(context).haalNodeUitXml(xml, xpath)
}

/*
 * Werkt de stap "Assertion Results" bij.
 */
private void setAssertionResults(AssertionResults assertionResults, String status, Exception e) {
    assertionResults.update('[SOAP]', status, ControlValues.fromContext(context), e)
}

/*
 * Indien een exceptie is opgetreden, log deze naar de logger.
 */
private void logAssertionException(String result_tag, def expectedNode, def actualXml) {
    log.info 'Expected node :'
    log.info XmlUtil.serialize(expectedNode);
    def actualNode = haalNodeOpUitXml(actualXml, result_tag);
    log.info 'Actual node :'
    if (actualNode == null) {
        log.info "De node kon niet gevonden worden in de actual result met de opgegeven soap_result_tag: $result_tag"
    } else {
        log.info XmlUtil.serialize(actualNode);
    }
}
