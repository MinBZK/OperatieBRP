package scripts.teststeps

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import org.junit.Test
import scripts.AbstractTestStepTester

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.arrayContaining
import static org.mockito.Matchers.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests <code>{@link Loop_Begin}.groovy</code> .
 */
class LoopBeginTest extends AbstractTestStepTester {

    @Test
    void defaultTest() {
        // arrange
        def testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName(eq('Assertion Results'))).thenReturn(testStep)
        when(testCase.getTestStepByName(eq('JDBC retries'))).thenReturn(mock(WsdlPropertiesTestStep.class))

        // act
        shell.evaluate(new File(scriptPath, 'scripts/teststeps/Loop_Begin.groovy'))

        // assert
        assertThat(testStep.propertyNames, arrayContaining('Status', 'Assertion', 'debug_log'))
    }
}
