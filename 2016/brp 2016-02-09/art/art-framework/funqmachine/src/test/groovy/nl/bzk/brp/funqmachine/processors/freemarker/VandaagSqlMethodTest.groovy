package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.template.SimpleNumber
import freemarker.template.TemplateException
import java.text.SimpleDateFormat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class VandaagSqlMethodTest {

    VandaagSqlMethod method = new VandaagSqlMethod()

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void valideertVerkeerdeTypeParameters() {
        thrown.expect(TemplateException.class)

        method.valideerParameters([new SimpleNumber(12), '123'])
    }

    @Test
    void valideertGeenParameters() {
        method.valideerParameters([])

        assert true
    }

    @Test
    void valideertTeveelParameters() {
        thrown.expect(TemplateException.class)

        method.valideerParameters([new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12)])
    }

    @Test
    void valideParameters() {
        method.valideerParameters([new SimpleNumber(543), new SimpleNumber(543), new SimpleNumber(543)])
        assert true
    }

    @Test
    void correcteFormat() {
        assert method.getFormat() == 'yyyyMMdd'
    }

    @Test
    void correcteOutput() {
        def df = new SimpleDateFormat(method.format)
        def params = [new SimpleNumber(-2), new SimpleNumber(3), new SimpleNumber(14)]
        def datum = df.parse('20140101')

        assert df.format(method.bereken(datum, params).time) == '20120415'
    }
}
