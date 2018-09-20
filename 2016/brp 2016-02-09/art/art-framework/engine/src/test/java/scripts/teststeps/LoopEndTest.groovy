package scripts.teststeps

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import org.junit.Before
import org.junit.Test
import scripts.AbstractTestStepTester

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.*

/**
 * Tests <code>{@link Loop_End}.groovy</code> .
 */
class LoopEndTest extends AbstractTestStepTester {

    def stepControlValues = mock(WsdlPropertiesTestStep.class)
    def stepCounters = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)
    def stepAssertionResults = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

    @Before
    void setUpStep() {
        when(testCase.getTestStepByName(eq('Control Values'))).thenReturn(stepControlValues)
        when(testCase.getTestStepByName(eq('Counters'))).thenReturn(stepCounters)
        when(testCase.getTestStepByName(eq('Assertion Results'))).thenReturn(stepAssertionResults)
    }

    @Test
    void testVolgendeStap() {
        // arrange
        stepCounters.setPropertyValue('executeRow', '3')
        stepCounters.setPropertyValue('maxRows', '5')

        // act
        shell.evaluate(new File(scriptPath, 'scripts/teststeps/Loop_End.groovy'))

        // assert
        assert stepCounters.getPropertyValue('executeRow') == '4'
        verify(testCaseRunner).gotoStepByName(eq('Loop Begin'))
    }

    @Test
    void testBijLaatsteStap() {
        // arrange
        stepCounters.setPropertyValue('executeRow', '5')
        stepCounters.setPropertyValue('maxRows', '5')

        // act
        shell.evaluate(new File(scriptPath, 'scripts/teststeps/Loop_End.groovy'))

        // assert
        assert stepCounters.getPropertyValue('executeRow') == '5'
        verify(testCaseRunner).gotoStepByName(eq('Create Excel Report'))
    }
}
