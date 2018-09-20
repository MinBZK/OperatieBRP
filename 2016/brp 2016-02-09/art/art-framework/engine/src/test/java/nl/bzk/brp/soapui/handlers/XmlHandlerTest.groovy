package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext
import com.eviware.soapui.support.types.StringToObjectMap
import nl.bzk.brp.soapui.AssertionMisluktExceptie
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static nl.bzk.brp.soapui.handlers.XmlHandler.fromContext
import static nl.bzk.brp.soapui.handlers.XmlHandler.log
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy

class XmlHandlerTest {
    def timestamp = '1394047676684'

    WsdlTestCase testCase = mock(WsdlTestCase.class)
    WsdlTestCaseRunner testCaseRunner = spy(new WsdlTestCaseRunner(testCase, StringToObjectMap.newInstance()))
    def context = new WsdlTestRunContext(testCaseRunner, StringToObjectMap.newInstance(), testCase)

    XmlHandler xmlHandler

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Before
    void setUp() {
        xmlHandler = fromContext(context)
    }

    @Test
    void parseRequestTemplate() {
        File template = new File(getClass().getResource('/VoorbeeldScript/request_template.xml').toURI())

        String resultaat = xmlHandler.leesEnParseTemplate(template.text, timestamp)

        assert !resultaat.contains('${')
    }

    @Test
    void geeftNodeUitXml() {
        File template = new File(getClass().getResource('/VoorbeeldScript/request_template.xml').toURI())

        org.w3c.dom.Node node = xmlHandler.haalNodeUitXml(template.text, '//soap:Body[1]')

        assert node != null
        assert node.nodeName == 'soap:Body'

        org.w3c.dom.Node node2 = xmlHandler.haalNodeUitXml(template.text, '//brp:organisatie[1]')

        assert node2 != null
        assert node2.nodeName == 'brp:organisatie'
    }

    @Test
    void vergelijkMetEenvoudigePlaceholder() {
        def xpath = '/brp:xml'
        def expected = xmlHandler.haalNodeUitXml(new File(getClass().getResource('/xml/1-expected.xml').toURI()).text, xpath)
        String actual = new File(getClass().getResource('/xml/1-actual.xml').toURI()).text

        xmlHandler.vergelijk(xpath, expected, actual)
    }

    @Test
    void vergelijkMetPlaceholderVoorNodes() {
        def xpath = '/brp:xml'
        def expected = xmlHandler.haalNodeUitXml(new File(getClass().getResource('/xml/1-expected.xml').toURI()).text, xpath)
        String actual = new File(getClass().getResource('/xml/2-actual.xml').toURI()).text

        xmlHandler.vergelijk(xpath, expected, actual)
    }

    @Test
    void vergelijkResultsXPath() {
        def xpath = '//Results'
        def expected = xmlHandler.haalNodeUitXml(new File(getClass().getResource('/xml/BRLV0018-TC00-3-expected.xml').toURI()).text, xpath)
        String actual = new File(getClass().getResource('/xml/BRLV0018-TC00-3-actual.xml').toURI()).text

        thrown.expect(AssertionMisluktExceptie.class)

        xmlHandler.vergelijk(xpath, expected, actual)
    }

    @Test
    void testCleanUpDatabaseResult() {
        final String xmlDatabaseResult =
            new File(getClass().getResource('/xml/archivering-resultaat.xml').toURI()).text

        // Resultaten gereed maken zoals de cleanUpMethode hem verwacht
        def xmlTextDatabaseResult = xmlDatabaseResult.replace('<', '&lt;').replace('>', '&gt;')

        final String cleandUpResult = XmlHandler.cleanupDatabaseResult(xmlTextDatabaseResult);

        assert !xmlDatabaseResult.equals(cleandUpResult);
        assert xmlDatabaseResult.contains("Deze tekst dient gefilterd te worden.")
        assert !cleandUpResult.contains("Deze tekst dient gefilterd te worden.")
    }

    @Test
    void testCleanUpDatabaseResultMetMeerdereBerichten() {
        final String xmlDatabaseResult =
            new File(getClass().getResource('/xml/dubbel-bericht.xml').toURI()).text

        // Resultaten gereed maken zoals de cleanUpMethode hem verwacht
        def xmlTextDatabaseResult = xmlDatabaseResult.replace('<', '&lt;').replace('>', '&gt;')

        final String cleandUpResult = XmlHandler.cleanupDatabaseResult(xmlTextDatabaseResult);

        assert !xmlDatabaseResult.equals(cleandUpResult);
        assert xmlDatabaseResult.contains("Uitgaand Bericht")
        assert !cleandUpResult.contains("Uitgaand Bericht")
        assert cleandUpResult.contains("<DATA>")
        cleandUpResult = cleandUpResult.replaceFirst("<DATA>", "");
        assert cleandUpResult.contains("<DATA>")

    }

    @Test
    void vergelijkMetExtremeWildcards() {
        def xpath = '//brp:bijgehoudenPersonen'
        def expected = new File(getClass().getResource('/xml/3-expected.xml').toURI()).text
        def actual = new File(getClass().getResource('/xml/3-actual.xml').toURI()).text

        log.debug(expected)
        log.debug(actual)

        xmlHandler.vergelijk(xpath, expected, actual)
    }

    @Test
    void vergelijkBijhoudingResponse() {
        def xpath = '//brp:resultaat/brp:verwerking'
        def expected = new File(getClass().getResource('/xml/4-expected.xml').toURI()).text
        def actual = new File(getClass().getResource('/xml/4-actual.xml').toURI()).text

        log.debug(expected)
        log.debug(actual)

        xmlHandler.vergelijk(xpath, expected, actual)
    }

    @Test
    void vergelijkSynchroniseerPersoonResponse() {
        def xpath = '//brp:synchronisatie'
        def expected = new File(getClass().getResource('/xml/5-expected.xml').toURI()).text
        def actual = new File(getClass().getResource('/xml/5-actual.xml').toURI()).text

        log.debug(expected)
        log.debug(actual)

        xmlHandler.vergelijk(xpath, expected, actual)
    }

}
