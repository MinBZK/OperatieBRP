package nl.bzk.brp.soapui.steps

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import com.eviware.soapui.model.testsuite.TestStepResult
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.arrayContaining
import static org.mockito.Mockito.when

class AssertionResultsTest {
    @Mock TestCaseRunContext context
    @Mock WsdlTestCase testCase
    WsdlPropertiesTestStep testStep

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this)

        testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('Assertion Results')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)
    }

    @Test
    public void kanWaardesZetten() {
        AssertionResults results = AssertionResults.fromContext(context)

        results.setStatus(TestStepResult.TestStepStatus.UNKNOWN)
        results.setDebugLog('debug')
        results.setAssertion('assert')

        assertThat(testStep.propertyNames, arrayContaining('Status', 'debug_log', 'Assertion'))
    }
}
