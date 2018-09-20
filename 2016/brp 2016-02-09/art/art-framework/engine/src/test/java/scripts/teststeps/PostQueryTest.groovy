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
class PostQueryTest extends AbstractTestStepTester {

    def stepControlValues = mock(WsdlPropertiesTestStep.class)

    @Before
    void setUpStep() {
        when(testCase.getTestStepByName(eq('Control Values'))).thenReturn(stepControlValues)

        doReturn('org.postgresql.Driver').when(context).expand('${#Project#jdbcDriver}')
    }

    @Test
    void testGeenPostQueryVoorLegeSQL() {
        // arrange
        when(stepControlValues.getPropertyValue('Post Query')).thenReturn('')

        // act
        def result = shell.evaluate(new File(scriptPath, 'scripts/teststeps/Post_Query.groovy'))

        // assert
        assert !result
    }
}
