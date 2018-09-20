package scripts.teststeps

import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.Counters

/**
 * Einde van loop, en ga naar Loop Begin als er nog regels zijn om uit te voeren.
 */

Counters step_COUNTERS = Counters.fromContext(context)
int executeRow = step_COUNTERS.getExecuteRow()
int maxRows = step_COUNTERS.getMaxRows()

if (executeRow > 0 && executeRow < (maxRows)) {
    executeRow++
    step_COUNTERS.setExecuteRow(executeRow)

    testRunner.gotoStepByName('Loop Begin')
} else {
    testRunner.gotoStepByName('Create Excel Report')
}

String status = AssertionResults.fromContext(context).getStatus()
String testId = ControlValues.fromContext(context).getTestID()
log.info "[Loop End] Einde ${testId}, stap ${executeRow}/${maxRows}, Resultaat: ${status}"

return true
