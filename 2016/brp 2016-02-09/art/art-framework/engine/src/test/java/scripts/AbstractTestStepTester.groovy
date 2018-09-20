package scripts

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext
import com.eviware.soapui.support.types.StringToObjectMap
import org.apache.log4j.Logger
import org.junit.Before

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy

/**
 * Extensie van {@link AbstractScriptTester} dat meer van de SoapUI omgeving mockt.
 */
abstract class AbstractTestStepTester extends AbstractScriptTester {
    // soapui environment
    WsdlTestCase testCase
    WsdlTestCaseRunner testCaseRunner
    WsdlTestRunContext context

    @Before
    void setUpSoapUI() {
        testCase = mock(WsdlTestCase.class)
        testCaseRunner = spy(new WsdlTestCaseRunner(testCase, StringToObjectMap.newInstance()))
        context = spy(new WsdlTestRunContext(testCaseRunner, StringToObjectMap.newInstance(), testCase))

        binding.testRunner = testCaseRunner
        binding.context = context
        binding.log = Logger.getLogger('testLogger')
    }
}
