package nl.bzk.brp.soapui.excel

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.DataSourceValues

import static org.mockito.Mockito.*

abstract class AbstractInputExcelTest {

    ControlValues buildControlValues() {
        TestCaseRunContext context = mock(TestCaseRunContext.class)
        WsdlTestCase testCase = mock(WsdlTestCase.class)

        def testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('Control Values')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)

        return spy(ControlValues.fromContext(context))
    }

    DataSourceValues buildDataSourceValues() {
        TestCaseRunContext context = mock(TestCaseRunContext.class)
        WsdlTestCase testCase = mock(WsdlTestCase.class)

        def testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        when(testCase.getTestStepByName('DataSource Values')).thenReturn(testStep)
        when(context.getTestCase()).thenReturn(testCase)

        return spy(DataSourceValues.fromContext(context))
    }
}
