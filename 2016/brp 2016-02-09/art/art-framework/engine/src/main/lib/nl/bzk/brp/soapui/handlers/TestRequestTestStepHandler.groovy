package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.model.testsuite.TestCaseRunner
import com.eviware.soapui.support.UISupport
import nl.bzk.brp.soapui.ARTVariabelen
import org.apache.log4j.Logger

/**
 * Afhandeling van het zetten/herzetten van de stap "Test Request".
 */
class TestRequestTestStepHandler {
    private static final String STEP_TEST_REQUEST = 'Test Request'
    private static final String STEP_TIJDELIJK_TEST_REQUEST = 'Tijdelijk Test Request'

    /**
     * Stel de interface en operation in van de soap request.
     */
    static void resetOperation(TestCaseRunner testRunner, def context, Logger log) {
        WsdlTestRequestStep testRequestStep = (WsdlTestRequestStep) testRunner.testCase.getTestStepByName(STEP_TEST_REQUEST)

        if (!testRequestStep) {
            UISupport.showErrorMessage("Kan de SOAP request stap '${STEP_TEST_REQUEST}' niet vinden, dit is een exceptionele fout. Heropen het ARTEngine project zonder wijzigingen op te slaan.")
        }

        String interfaceName = ARTVariabelen.getSoapInterface(context)
        String operationName = ARTVariabelen.getSoapOperation(context)
        String soap_endpoint = ARTVariabelen.getSoapEndpoint(context)
        String lastOperationName = "none"

        String endPoint = ARTVariabelen.makeProjectProperty(soap_endpoint)

        // Update de huidige request met de nieuwe operatie, dit is nodig om ook de auth settings mee te krijgen in de nieuwe request
        log.info operationName + " :: " + endPoint

        //soms kan SoapUI de getOperation van de vorige keer niet bevragen
        if (testRequestStep.operation) {
            lastOperationName = testRequestStep.operation.name
        }
        if ((!lastOperationName.equals(operationName) && operationName.length() > 5) || (!testRequestStep.properties['Endpoint'].value.equals(endPoint) && endPoint.length() > 5)) {
            // Haal de operatie op
            def myInterface = (WsdlInterface) testRunner.testCase.testSuite.project.getInterfaceByName(interfaceName)

            if (myInterface == null) {
                log.error 'Interfacename kan niet gevonden worden : ' + interfaceName
            }

            def myOperation = myInterface.getOperationByName(operationName)
            testRequestStep.operation = myOperation

            testRequestStep.properties['Endpoint'].value = endPoint;

            // Vervang Test Request. Simpelweg de nieuwe operatie zetten in de bestaande Test Request werkt niet, de Test Request moet in zijn geheel vervangen worden, anders kan de response
            // niet opgehaald worden.
            int currentLocationOfStep = testRunner.testCase.getIndexOfTestStep(testRequestStep)
            testRunner.testCase.importTestStep(testRequestStep, STEP_TIJDELIJK_TEST_REQUEST, currentLocationOfStep, true)
            // Een sleep omdat de UI raar kan doen
            sleep(100)
            testRunner.testCase.removeTestStep(testRequestStep)
            // Een sleep omdat de UI raar kan doen
            sleep(100)
            testRunner.testCase.getTestStepByName(STEP_TIJDELIJK_TEST_REQUEST).name = STEP_TEST_REQUEST
        } else {
            log.info 'geen set nodig van operation en endPoint'
        }
    }
}
