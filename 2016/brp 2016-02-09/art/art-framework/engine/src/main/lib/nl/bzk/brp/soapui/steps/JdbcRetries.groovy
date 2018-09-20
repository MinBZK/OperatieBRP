package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep

/**
 * Wrapper voor de {@link WsdlPropertiesTestStep} 'JDBC retries'.
 */
class JdbcRetries {
    WsdlPropertiesTestStep step

    /**
     * Factory method.
     * @param context SoapUI context
     * @return new instantie
     */
    static JdbcRetries fromContext(def context) {
        new JdbcRetries(context.testCase.getTestStepByName('JDBC retries'))
    }

    /**
     * Protected constructor.
     * @param jdbcRetries
     */
    protected JdbcRetries(WsdlPropertiesTestStep jdbcRetries) {
        this.step = jdbcRetries
    }

    void setRetries(int count) { step.setPropertyValue('retries', count as String) }
    int getRetries() { step.getPropertyValue('retries')?.asType(Integer.class) ?: 0 }
}
