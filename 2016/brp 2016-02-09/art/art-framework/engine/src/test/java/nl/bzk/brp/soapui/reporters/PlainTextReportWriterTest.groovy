package nl.bzk.brp.soapui.reporters

import nl.bzk.brp.soapui.Resultaten
import org.junit.Before
import org.junit.Test
import org.xml.sax.SAXException

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class PlainTextReportWriterTest {

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
    void maaktRapport() {
        File file = new File(new File(getClass().getResource('/').toURI()), 'plaintext.txt')
        PlainTextReportWriter writer = new PlainTextReportWriter(file, 'Foo comment')

        // act
        writer.writeReport(resultaten.resultaat)

        // assert
        def report = file.text

        assert report.contains('some very long string exceeding thirty characters to not fail the test')
        assert report.contains('Foo comment')

        assert file.size() < 500
    }
}
