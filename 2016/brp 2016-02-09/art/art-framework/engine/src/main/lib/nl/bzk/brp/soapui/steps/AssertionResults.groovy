package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.AssertionException
import com.eviware.soapui.model.testsuite.TestStepResult
import nl.bzk.brp.soapui.AssertionMisluktExceptie
import nl.bzk.brp.soapui.handlers.PropertiesHandler

import static nl.bzk.brp.soapui.excel.OutputKolommen.*

/**
 * Wrapper voor de {@link WsdlPropertiesTestStep} 'Assertion Results'.
 */
class AssertionResults {
    WsdlPropertiesTestStep step

    /**
     * Factory method.
     *
     * @param context SoapUI context
     * @return new instantie
     */
    static AssertionResults fromContext(def context) {
        new AssertionResults(context.testCase.getTestStepByName('Assertion Results'))
    }

    /**
     * Protected constructor.
     * @param assertResults
     */
    protected AssertionResults(WsdlPropertiesTestStep assertResults) {
        this.step = assertResults
    }

    /**
     * Update de waardes in deze stap.
     *
     * @param soort de soort
     * @param status de status
     * @param controlValues control values met waardes
     * @param e de exceptie
     */
    void update(String soort, String status, ControlValues controlValues, Exception e) {
        String QUARANTAINE = 'QUARANTAINE'
        String STATUS = controlValues.getStatus()

        if (e instanceof AssertionException) {
            def msg = soort << ' ' << e.errors.join('\n')
            setAssertion(msg.toString())
        } else if (e instanceof AssertionMisluktExceptie) {
            setAssertion(e.message)
        } else {
            setDebugLog(e.message)
        }
        if (STATUS == QUARANTAINE) {
            status = QUARANTAINE
        }
        setStatus(status)
        setOpenstaandeIssues(controlValues.getPropertyValue(Openstaande_Issues.naam))
        setOpmerkingen(controlValues.getPropertyValue(Opmerkingen.naam))
    }

    /**
     * Maakt alle properties van de stap leeg.
     */
    void clearProperties() {
        PropertiesHandler.clearTestProperties(step)
        setStatus(TestStepResult.TestStepStatus.OK)
        setAssertion('')
        setDebugLog('')
    }

    String getStatus() { step.getPropertyValue(Status.naam) }

    void setStatus(TestStepResult.TestStepStatus status) { setStatus(status.toString()) }

    void setStatus(String string) { step.setPropertyValue(Status.naam, string) }

    String getAssertion() { step.getPropertyValue(Assertion.naam) }

    void setAssertion(String assertion) { step.setPropertyValue(Assertion.naam, assertion) }

    String getDebugLog() { step.getPropertyValue(Debug_Log.naam) }

    void setDebugLog(String log) { step.setPropertyValue(Debug_Log.naam, log) }

    String getOpenstaandeIssues() { step.getPropertyValue(Openstaande_Issues.naam) }

    void setOpenstaandeIssues(final String s) { step.setPropertyValue(Openstaande_Issues.naam, s) }

    String getOpmerkingen() { step.getPropertyValue(Opmerkingen.naam) }

    void setOpmerkingen(final String s) { step.setPropertyValue(Opmerkingen.naam, s) }
}
