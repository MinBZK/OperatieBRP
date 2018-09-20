package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.template.SimpleNumber
import freemarker.template.TemplateException
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class MomentVolgenMethodTest {

    MomentVolgenMethod method = new MomentVolgenMethod()

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void valideertVerkeerdeTypeParameters() {
        thrown.expect(TemplateException.class)

        method.valideerParameters([new SimpleNumber(12), '123'])
    }

    @Test
    void valideertTeveelParameters() {
        thrown.expect(TemplateException.class)

        method.valideerParameters([new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12), new SimpleNumber(12)])
    }

    @Test
    void valideertGeenParameters() {
        method.valideerParameters([])

        assert true
    }

    @Test
    void valideParameters() {
        method.valideerParameters([new SimpleNumber(543), new SimpleNumber(543), new SimpleNumber(543)])
        assert true
    }

    @Test
    void correcteFormat() {
        assert method.getFormat() == 'yyyy-MM-dd\'T\'HH:mm:ss.SSSXXX'
    }
}
