package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestProperty
import nl.bzk.brp.soapui.handlers.PropertiesHandler

/**
 * Wrapper voor de {@link WsdlPropertiesTestStep} 'DataSource Values'.
 */
class DataSourceValues {
    WsdlPropertiesTestStep step

    /**
     * Factory method.
     * @param context SoapUI context
     * @return new instantie
     */
    static DataSourceValues fromContext(def context) {
        new DataSourceValues(context.testCase.getTestStepByName('DataSource Values'))
    }

    /**
     * Protected constructor.
     * @param controlValues
     */
    protected DataSourceValues(WsdlPropertiesTestStep controlValues) {
        this.step = controlValues
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
