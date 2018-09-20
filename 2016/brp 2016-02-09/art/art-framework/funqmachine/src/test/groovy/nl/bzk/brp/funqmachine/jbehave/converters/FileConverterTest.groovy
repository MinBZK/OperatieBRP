package nl.bzk.brp.funqmachine.jbehave.converters

import org.jbehave.core.steps.ParameterConverters
import org.junit.Test

class FileConverterTest {
    private FileConverter converter = new FileConverter()

    @Test
    void kanConverteren() {
        converter.convertFile('/xml/1-actual.xml')
    }

    @Test(expected = ParameterConverters.ParameterConvertionFailed)
    void fouteConversie() {
        converter.convertFile('/foo.bar')
    }
}
