package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep

/**
 * Wrapper voor de {@link WsdlTestRequestStep} 'Test Request'.
 */
class TestRequest {
    def context
    WsdlTestRequestStep step

    /**
     * Factory method.
     * @param context SoapUI context
     * @return new instantie
     */
    static TestRequest fromContext(def context) {
        new TestRequest(context)
    }

    /**
     * Protected constructor.
     * @param ctx
     */
    protected TestRequest(def ctx) {
        this.context = ctx
        this.step = ctx.testCase.getTestStepByName('Test Request')
    }

    /**
     * Zet de schema validatie aan of uit.
     * @param val {@code true} voor aan, {@code false} voor uit
     */
    void setSchemaValidation(boolean val) {
        step.assertions['Schema Compliance (voldoet aan XSD)'].disabled = !val
    }

    /**
     * Disable de Soap Response validatie.
     */
    void disableSoapResponseAssert() {
        step.assertions["SOAP Response"].disabled = true
    }

    /**
     * Geeft de SOAP Body van een antwoord.
     * @return
     */
    String getSoapBody() {
        context.expand('${Test Request#Response#declare namespace soap=\'http://schemas.xmlsoap.org/soap/envelope/\'; //soap:Body[1]}') as String
    }

    /**
     * Geeft het volledige SOAP Bericht van een antwoord.
     * @return
     */
    String getResponse() {
        context.expand('${Test Request#Response}') as String
    }

    /**
     * Geeft het volledige SOAP Bericht van een verzoek.
     * @return
     */
    String getRequest() {
        context.expand('${Test Request#Request}') as String
    }
}
