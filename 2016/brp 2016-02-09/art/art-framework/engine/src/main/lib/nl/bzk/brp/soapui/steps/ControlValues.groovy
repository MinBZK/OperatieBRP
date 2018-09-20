package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestProperty
import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.handlers.PropertiesHandler

import static nl.bzk.brp.soapui.excel.InputKolommen.*

/**
 * Wrapper voor de {@link WsdlPropertiesTestStep} 'Control Values'.
 */
class ControlValues {
    WsdlPropertiesTestStep step
    def context

    /**
     * Factory method.
     * @param context SoapUI context
     *
     * @return new instantie
     */
    static ControlValues fromContext(def context) {
        new ControlValues(context)
    }

    /**
     * Protected constructor.
     * @param ctx
     */
    protected ControlValues(def ctx) {
        this.step = ctx.testCase.getTestStepByName('Control Values')
        this.context = ctx
    }

    String getSoapInterface() { step.getPropertyValue(SOAP_Interface.naam) }
    String getSoapOperation() { step.getPropertyValue(SOAP_Operation.naam) }
    String getSoapEndpoint() { step.getPropertyValue(SOAP_Endpoint.naam) }
    String getRequestTemplate() { step.getPropertyValue(Request_Template.naam) }
    String getAltTestProperties() { step.getPropertyValue(Alternatieve_Properties.naam) }

    boolean isSendRequest() { step.getPropertyValue(Send_Request.naam) == '1' }
    String getSoapResponseQuery() { step.getPropertyValue(Soap_Response_Query.naam) }
    String getDbQuery() { step.getPropertyValue(DB_Query.naam) }
    int getDbQueryDelay() { step.getPropertyValue(DB_Query_Delay.naam)?.asType(Integer.class) ?: 0 }
    String getDbResponseQuery() { step.getPropertyValue(DB_Response_Query.naam)}
    String getPrepareData() { step.getPropertyValue(Prepare_Data.naam) }
    String getPreQuery() { step.getPropertyValue(Pre_Query.naam) }
    String getPostQuery() { step.getPropertyValue(Post_Query.naam) }
    String getStatus() { step.getPropertyValue(Status.naam)?.toUpperCase() }
    String getBeschrijving() { step.getPropertyValue(Beschrijving.naam)}

    String getOverschrijfVariabelen() { step.getPropertyValue(Overschrijf_Variabelen.naam)}
    boolean heeftOverschrijfVariabelen() { step.getPropertyValue(Overschrijf_Variabelen.naam) != null}

    String getTestGeval() { step.getPropertyValue(TestGeval.naam) }
    String getVolgNr() { step.getPropertyValue(Volgnummer.naam) }

    String getTestID() { "${step.getPropertyValue(TestGeval.naam)}-${step.getPropertyValue(Volgnummer.naam) ?: ''}"}

    void setExecutionTimestamp(Date d) { step.setPropertyValue('ART-executionTimestamp', d.time as String) }
    long getExecutionTime() { step.getPropertyValue('ART-executionTimestamp')?.asType(Long.class) ?: 0 }
    String getExecutionTimestamp() { step.getPropertyValue('ART-executionTimestamp') }

    void setLeveringTsRegistratie(String tsReg) { step.setPropertyValue('LEV-tijdstipRegistratie', tsReg) }
    String getLeveringTsRegistratie() { step.getPropertyValue('LEV-tijdstipRegistratie') }

    /**
     * Kijkt of er een redirection naar een andere ART is.
     * Kan alleen bepaald worden na de 'Read Excel and Write Control Values' stap.
     *
     * @return
     */
    boolean isTestCaseRedirected() {
        return ARTVariabelen.getAlternativeScriptPath(context)
    }

    /**
     * Maakt alle properties van de stap leeg.
     */
    void clearProperties() {
        PropertiesHandler.clearTestProperties(step)
    }

    // delegate to step
    void setPropertyValue(String key, String value) { step.setPropertyValue(key, value) }
    String getPropertyValue(String key) { step.getPropertyValue(key) }
    List<TestProperty> getPropertyList() { step.getPropertyList() }

}
