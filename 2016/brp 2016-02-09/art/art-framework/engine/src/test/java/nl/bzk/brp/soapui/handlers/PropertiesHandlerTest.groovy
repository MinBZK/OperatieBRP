package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.config.TestStepConfig
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.arrayContaining
import static org.hamcrest.Matchers.is

@RunWith(MockitoJUnitRunner.class)
class PropertiesHandlerTest {
    @Mock WsdlTestCase testCase

    def testStep = new WsdlPropertiesTestStep(testCase, TestStepConfig.Factory.newInstance(), false)

    @Test
    void propertiesToevoegen_uit_File() {
        File f = new File(getClass().getResource('/test.properties').toURI())

        PropertiesHandler.addPropertiesTo(f, testStep)

        assertThat(testStep.getPropertyList().size(), is(3))
        assertThat(testStep.propertyNames, arrayContaining('bar', 'demo', 'foo'))
    }

    @Test
    void propertiesToevoegen_uit_Path_en_Bestandsnaam() {
        String path = new File(getClass().getResource('/').toURI()).absolutePath

        PropertiesHandler.addPropertiesTo(path, 'test.properties', testStep)

        assertThat(testStep.getPropertyList().size(), is(3))
        assertThat(testStep.propertyNames, arrayContaining('bar', 'demo', 'foo'))
    }

    @Test
    void propertiesLaden_uit_File() {
        File f = new File(getClass().getResource('/test.properties').toURI())

        def prop = PropertiesHandler.loadProperties(f)

        assertThat(prop.size(), is(3))
    }

    @Test
    void propertiesLaden_uit_Path_en_Bestandsnaam() {
        String path = new File(getClass().getResource('/').toURI()).absolutePath

        def prop = PropertiesHandler.loadProperties(path, 'test.properties')

        assertThat(prop.size(), is(3))
    }

    @Test
    void zetProperties() {
        def prop = new Properties()
        new File(getClass().getResource('/test.properties').toURI()).withInputStream { s ->
            prop.load(s)
        }

        PropertiesHandler.setProperties(prop, testStep)

        assertThat(testStep.propertyList.size(), is(3))

        PropertiesHandler.clearTestProperties(testStep)

        assertThat(testStep.propertyList.isEmpty(), is(true))
    }
}
