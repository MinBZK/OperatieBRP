package nl.bzk.brp.soapui.reporters

import nl.bzk.brp.soapui.Resultaten
import nl.bzk.brp.soapui.reporters.JUnitXmlWriter
import org.junit.Before
import org.junit.Test
import org.xml.sax.SAXException

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class JUnitXmlWriterTest {

    Resultaten resultaten = new Resultaten([])

    @Before
    void setUp() {
        Properties test1 = ['Testgeval':'TG1', 'VolgNr':'1', 'Status':'OK', 'Assertion':'', 'debug_log':''] as Properties
        Properties test2 = ['Testgeval':'TG2', 'VolgNr':'1', 'Status':'FAILED', 'Assertion':'', 'debug_log':'some very long string exceeding thirty characters to not fail the test'] as Properties
        Properties test3 = ['Testgeval':'TG3', 'VolgNr':'1', 'Status':'QUARANTAINE', 'Assertion':'QUARANTAINE', 'debug_log':''] as Properties
        Properties test4 = ['Testgeval':'TG4', 'VolgNr':'1', 'Status':'TODO', 'Assertion':'', 'debug_log':'log statement that is not as long as the other debug'] as Properties

        resultaten.voegToe(test1)
        resultaten.voegToe(test2)
        resultaten.voegToe(test3)
        resultaten.voegToe(test4)
    }

    @Test
    void maaktXMLvanResultaten() {
        File file = new File(new File(getClass().getResource('/').toURI()), 'xunit.xml')
        JUnitXmlWriter writer = new JUnitXmlWriter(file)

        // act
        writer.writeReport(resultaten.resultaat)

        // assert
        XmlSlurper slurper = new XmlSlurper()
        def testsuite = slurper.parse(file)

        assert isValid(file.text)
        assert testsuite.testcase.size() == 4
    }

    @Test
    void maaktXMLvanResultatenEnSettings() {
        File file = new File(new File(getClass().getResource('/').toURI()), 'xunit.xml')
        JUnitXmlWriter writer = new JUnitXmlWriter(file)

        Properties settings = new Properties()
        settings.put('demo', 'test waarde')

        // act
        writer.writeReport(settings, resultaten.resultaat)

        // assert
        XmlSlurper slurper = new XmlSlurper()
        def testsuite = slurper.parse(file)

        assert isValid(file.text)
        assert testsuite.properties.size() == 1
    }


    private boolean isValid(String xmlString) {
        def xsdString = new File(getClass().getResource('/junit-4.xsd').toURI()).text

        def factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        def schema = factory.newSchema(new StreamSource(new StringReader(xsdString)))
        def validator = schema.newValidator()

        try {
            validator.validate(new StreamSource(new StringReader(xmlString)))
            return true
        } catch (SAXException | IOException e) {
            println e
            return false
        }
    }
}
