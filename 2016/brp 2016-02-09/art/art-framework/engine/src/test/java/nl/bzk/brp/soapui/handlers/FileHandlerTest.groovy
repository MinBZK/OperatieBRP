package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext
import com.eviware.soapui.support.types.StringToObjectMap
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.hamcrest.Matchers.startsWith
import static org.mockito.Matchers.anyString
import static org.mockito.Mockito.*

class FileHandlerTest {
    WsdlTestCase testCase = mock(WsdlTestCase.class)
    WsdlTestCaseRunner testCaseRunner = spy(new WsdlTestCaseRunner(testCase, StringToObjectMap.newInstance()))
    def context = spy(new WsdlTestRunContext(testCaseRunner, StringToObjectMap.newInstance(), testCase))

    FileHandler fileHandler
    File path

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Before
    void setUp() {
        path = new File(getClass().getResource('/').toURI())

        when(context.expand(anyString())).thenReturn(path.absolutePath).thenReturn('./output')

        fileHandler = FileHandler.fromContext(context)
    }

    @Test
    void kanInputFileGeven() {
        File input = fileHandler.geefInputFile('xml/1-actual.xml')

        assert input.exists()
    }

    @Test
    void geeftInputOokAlsDezeNietBestaat() {
        thrown.expect FileNotFoundException.class
        thrown.expectMessage startsWith('Het opgegeven \'input\' bestand [Foo.bat] kan niet worden gevonden op (')

        File input = fileHandler.geefInputFile('Foo.bat')

        assert !input.exists()
    }

    @Test
    void geeftOutputBestand() {
        File output = fileHandler.geefOutputFile('not-1-expected.xml')

        assert !output.exists()
    }

    @Test
    void schrijftFileObvReferentie() {
        fileHandler.schrijfFile('output.txt', 'some context')

        File verwacht = fileHandler.geefOutputFile('output.txt')

        assert verwacht.exists()
        assert verwacht.text == 'some context'
    }

    @Test
    void schrijftFileObvFile() {
        File verwacht = fileHandler.geefOutputFile('output2.txt')
        fileHandler.schrijfFile(verwacht, 'some context')

        assert verwacht.exists()
        assert verwacht.text == 'some context'
    }

    @Test
    void geeftVerwachtBestand() {
        File verwacht = fileHandler.geefVerwachtBestand('snip.sql')

        assert verwacht.exists()
    }

    @Test
    void geeftVerwachtBestandDatNietBestaat() {
        thrown.expect FileNotFoundException.class
        thrown.expectMessage startsWith('Het opgegeven \'expected\' bestand [1-actual.xml] kan niet worden gevonden op (')

        File file = fileHandler.geefVerwachtBestand('1-actual.xml')

        assert !file.exists()
    }
}
