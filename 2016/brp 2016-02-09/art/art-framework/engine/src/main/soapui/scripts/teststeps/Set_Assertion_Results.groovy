package scripts.teststeps

import com.eviware.soapui.model.testsuite.TestStepResult
import nl.bzk.brp.soapui.Resultaten
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues

/**
 * Leest eventuele testStep script errors uit en stop deze in de "Assertion Results" properties stap.
 */

// DataSource Values
AssertionResults step_ASSERTION_RESULTS = AssertionResults.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)

//Controleer errors in alle stappen
checkStepErrors(step_ASSERTION_RESULTS, step_CONTROL_VALUES.getStatus())

// opslaan resultaten
Resultaten resultaten = new Resultaten(context.get('statusList'))
resultaten.voegToe(step_CONTROL_VALUES, step_ASSERTION_RESULTS)
context.put('statusList', resultaten.getResultaat())


// -- Private methods --------------------------------------------------------
/*
 * Controleert errors in alle stappen.
 * Dit block kan niet als individueel stap uitgevoerd worden, het moet uitgevoerd worden door de testRunner, dus de TestSteps in zijn geheel.
 */
private String checkStepErrors(AssertionResults assertionResults, String status) {
    String debugMessage = ''

    testRunner.results.each { r ->
        if (r.status == TestStepResult.TestStepStatus.FAILED) {
            String error = "[${r.testStep.name}] - Status: ${r.status} - Error: ${r.error} - Message: "
            r.messages?.each { msg ->
                error << msg
            }
            debugMessage = "${error}\n"
        }
    }

    assertionResults.setDebugLog(debugMessage)
    if (debugMessage) {
        assertionResults.setStatus(TestStepResult.TestStepStatus.FAILED)
    }
    if (assertionResults.getAssertion()) {
        assertionResults.setAssertion(status)
    }

    testRunner.getResults().clear()

    return debugMessage
}
