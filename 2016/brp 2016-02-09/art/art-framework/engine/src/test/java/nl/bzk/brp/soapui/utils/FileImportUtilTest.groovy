package nl.bzk.brp.soapui.utils

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext
import com.eviware.soapui.support.types.StringToObjectMap
import nl.bzk.brp.soapui.handlers.FileHandler
import org.junit.Test

import static org.mockito.Matchers.anyString
import static org.mockito.Mockito.*

class FileImportUtilTest {

    @Test
    void kanImporteren() {
        String text = """
<foo>
    <import-xml>[snip.xml]</import-xml>
    <import-sql>[snip.sql]</import-sql>
    <noimport>[snip.bak]</noimport>
</foo>"""
        def result = FileImportUtil.importFiles(maakFileHandler(), text)

        assert result.contains('[snip.bak]')
        assert result.contains('<import-xml><insert>text</insert></import-xml>')
        assert result.contains('<import-sql>SELECT * FROM dual;</import-sql>')
    }

    @Test(expected = FileNotFoundException.class)
    void exceptionAlsBestandNietBestaat() {
        String text = '<templ>[snoop.xml]</templ>'

        FileImportUtil.importFiles(maakFileHandler(), text)
    }

    private FileHandler maakFileHandler() {
        WsdlTestCase testCase = mock(WsdlTestCase.class)
        WsdlTestCaseRunner testCaseRunner = spy(new WsdlTestCaseRunner(testCase, StringToObjectMap.newInstance()))
        def context = spy(new WsdlTestRunContext(testCaseRunner, StringToObjectMap.newInstance(), testCase))

        File path = new File(getClass().getResource('/').toURI())

        when(context.expand(anyString())).thenReturn(path.absolutePath).thenReturn('./output')

        return FileHandler.fromContext(context)
    }
}
