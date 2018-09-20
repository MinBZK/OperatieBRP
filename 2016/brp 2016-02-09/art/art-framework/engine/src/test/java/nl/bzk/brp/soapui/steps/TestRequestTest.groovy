package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SchemaComplianceAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.soap.SoapResponseAssertion
import com.eviware.soapui.model.testsuite.TestAssertion
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

/**
 *
 */
class TestRequestTest {
    @Mock TestCaseRunContext context
    @Mock WsdlTestCase testCase
    WsdlTestRequestStep testStep

    @Mock SchemaComplianceAssertion schemaAssertion
    @Mock SoapResponseAssertion responseAssertion

    Map<String, TestAssertion> assertionMap = new HashMap<>(1)

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        fillAssertions()

        testStep = mock(WsdlTestRequestStep.class)
        when(testStep.getAssertions()).thenReturn(assertionMap)

        when(testCase.getTestStepByName('Test Request')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)
    }

    private void fillAssertions() {
        assertionMap.put('Schema Compliance (voldoet aan XSD)', schemaAssertion)
        assertionMap.put('SOAP Response', responseAssertion)
    }

    @Test
    void kanRequestOphalen() {
        TestRequest testRequest = TestRequest.fromContext(context)

        assert null == testRequest.getRequest()
        assert null == testRequest.getResponse()
    }

    @Test
    void kanAssertionsZetten() {
        TestRequest testRequest = TestRequest.fromContext(context)

        testRequest.setSchemaValidation(false)
        testRequest.disableSoapResponseAssert()

        verify(schemaAssertion).setDisabled(true)
        verify(responseAssertion).setDisabled(true)

        testRequest.setSchemaValidation(true)
        verify(schemaAssertion).setDisabled(false)
    }


}
