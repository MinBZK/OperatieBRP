package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.template.SimpleNumber
import freemarker.template.TemplateException
import java.text.SimpleDateFormat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class MomentMethodTest {

    MomentMethod method = new MomentMethod()

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

        method.valideerParameters([new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12)])
    }

    @Test
    void valideParameters() {
        method.valideerParameters([new SimpleNumber(543), new SimpleNumber(543), new SimpleNumber(543), new SimpleNumber(543), new SimpleNumber(543)])
        assert true
    }

    @Test
    void correcteFormat() {
        assert method.getFormat() == 'yyyy-MM-dd\'T\'HH:mm:ss.SSSXXX'
    }

    @Test
    void correcteOutput() {
        def df = new SimpleDateFormat(method.format)
        def params = [new SimpleNumber(-2), new SimpleNumber(3), new SimpleNumber(14), new SimpleNumber(0), new SimpleNumber(31)]
        def datum = df.parse('2014-01-01T13:00:00.0+01:00')

        assert df.format(method.bereken(datum, params).time) == '2012-04-15T13:31:00.000+02:00'
    }
}
