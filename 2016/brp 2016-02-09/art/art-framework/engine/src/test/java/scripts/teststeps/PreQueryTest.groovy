package scripts.teststeps

import com.eviware.soapui.DefaultSoapUICore
import com.eviware.soapui.SoapUI
import com.eviware.soapui.SoapUICore
import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import nl.bzk.brp.soapui.excel.InputKolommen
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import scripts.AbstractTestStepTester

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@RunWith(PowerMockRunner.class)
@PrepareForTest(SoapUI.class)
class PreQueryTest extends AbstractTestStepTester {

    @Before
    void setUpStep() {
        PowerMockito.mockStatic(SoapUI.class)

        SoapUICore core = new DefaultSoapUICore()
        when(SoapUI.getSoapUICore()).thenReturn(core)
        when(SoapUI.getFactoryRegistry()).thenReturn(core.getFactoryRegistry())
    }

    @Ignore('Begin van een test met een GroovyTestStep, mocking van objecten is nog (lang) niet compleet')
    @Test
    void defaultTest() {
        // arrange
        def testStep = new WsdlGroovyScriptTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

        def propertiesStep = mock(WsdlPropertiesTestStep.class)
        when(propertiesStep.getPropertyValue(InputKolommen.Pre_Query.naam)).thenReturn('SELECT count(id) FROM kern.pers;')

        when(testCase.getTestStepByName(eq('Control Values'))).thenReturn(propertiesStep)

        // act
        shell.evaluate(new File(scriptPath, 'scripts/teststeps/Pre_Query.groovy'))

        // assert
        assert testStep.getPropertyValue('result') == null
    }
}
