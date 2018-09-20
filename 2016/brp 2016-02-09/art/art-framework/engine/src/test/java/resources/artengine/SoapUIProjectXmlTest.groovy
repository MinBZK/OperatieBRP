package resources.artengine

import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.WsdlTestSuite
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.*
import spock.lang.Specification
import spock.lang.Unroll

class SoapUIProjectXmlTest extends Specification {
    WsdlProject project = new WsdlProject(new File(getClass().getResource('/ART-soapui-project.xml').toURI()).absolutePath)

    def "project bevat WSDL interfaces"() {
        expect:
        project.interfaceList.size() == 13
    }

    def "project bevat TestSteps"() {
        WsdlTestSuite suite = project.getTestSuiteByName 'ARTengine'

        expect:
        suite.testCaseCount == 2
        suite.getTestCaseByName('Engine').testStepCount == 31
    }

    @Unroll
    def "project heeft #count keer een #klazz.simpleName stap"() {
        WsdlTestCase engine = project.getTestSuiteByName('ARTengine').getTestCaseByName 'Engine'

        expect:
        engine.getTestStepsOfType(klazz).size() == count

        where:
        klazz                          | count
        WsdlPropertiesTestStep.class   | 5
        WsdlGroovyScriptTestStep.class | 20
        WsdlTestRequestStep.class      | 1
        JdbcRequestTestStep.class      | 1
        WsdlDelayTestStep.class        | 4
    }
}
