package nl.bzk.brp.soapui.steps

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.mockito.Mockito.when

class CountersTest {

    @Mock TestCaseRunContext context
    @Mock WsdlTestCase testCase
    WsdlPropertiesTestStep testStep

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('Counters')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)
    }

    @Test
    void setValues() {
        Counters counters = Counters.fromContext(context)

        counters.setExecuteRow(3)
        counters.setMaxRows(5)

        assertThat(testStep.getPropertyValue(counters.EXECUTE_ROW), is('3'))
        assertThat(testStep.getPropertyValue(counters.MAX_ROWS), is('5'))
    }

    @Test
    void getValues() {
        Counters counters = Counters.fromContext(context)

        testStep.setPropertyValue(counters.EXECUTE_ROW, '10')
        testStep.setPropertyValue(counters.MAX_ROWS, '1000')

        assertThat(counters.getExecuteRow(), is(10))
        assertThat(counters.getMaxRows(), is(1000))
    }

    @Test
    void withNullValues() {
        Counters counters = Counters.fromContext(context)

        assertThat(counters.getMaxRows(), is(-1))
        assertThat(counters.getExecuteRow(), is(-1))
    }

}
