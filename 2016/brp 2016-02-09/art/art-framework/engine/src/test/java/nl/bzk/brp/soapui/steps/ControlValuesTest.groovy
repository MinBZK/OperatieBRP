package nl.bzk.brp.soapui.steps

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.when

class ControlValuesTest {

    @Mock TestCaseRunContext context
    @Mock WsdlTestCase testCase
    WsdlPropertiesTestStep testStep

    def timestamp = '1394047676684'

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('Control Values')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)
    }

    @Test
    void executionTime() {
        ControlValues controlValues = ControlValues.fromContext(context)

        assert controlValues.getExecutionTime() == 0

        controlValues.setExecutionTimestamp(new Date(timestamp as long))

        assert controlValues.getExecutionTimestamp() == timestamp
        assert controlValues.getExecutionTime() == timestamp as long
    }

    @Test
    void clearProperties() {
        ControlValues controlValues = ControlValues.fromContext(context)

        assert controlValues.propertyList.size() == 0

        controlValues.setPropertyValue('foo', 'bar')
        assert controlValues.propertyList.size() == 1

        controlValues.clearProperties()
        assert controlValues.propertyList.isEmpty()
    }

    @Test
    void isSendRequestTest() {
        ControlValues controlValues = ControlValues.fromContext(context)

        assert !controlValues.isSendRequest()

        controlValues.setPropertyValue('SendRequest', '1')

        assert controlValues.isSendRequest()

        controlValues.clearProperties()

        assert !controlValues.isSendRequest()
    }

    @Test
    void maakTestId() {
        ControlValues controlValues = ControlValues.fromContext(context)

        controlValues.setPropertyValue('Testgeval', 'Foo')
        controlValues.setPropertyValue('VolgNr', null)

        assert controlValues.getTestID() == 'Foo-'

        controlValues.setPropertyValue('VolgNr', '')
        assert controlValues.getTestID() == 'Foo-'

        controlValues.setPropertyValue('VolgNr', '11')
        assert controlValues.getTestID() == 'Foo-11'
    }
}
