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

class JdbcRetriesTest {
    @Mock TestCaseRunContext context
    @Mock WsdlTestCase testCase
    WsdlPropertiesTestStep testStep

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('JDBC retries')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)
    }

    @Test
    void kanRetriesZetten() {
        JdbcRetries jdbcRetries = JdbcRetries.fromContext(context)

        jdbcRetries.setRetries(10)

        assertThat(testStep.getPropertyValue('retries'), is('10'))
    }

    @Test
    void zonderWaarde() {
        JdbcRetries retries = JdbcRetries.fromContext(context)

        assertThat(retries.getRetries(), is(0))
    }
}
