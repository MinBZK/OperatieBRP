package nl.bzk.brp.funqmachine.processors

import org.junit.Test

class TemplateProcessorTest {
    TemplateProcessor processor = new TemplateProcessor()

    @Test
    void processesOk() {
        def template = '/test-template.ftl'
        def data = ['veld': 'waarde', 'getal': 32456]

        assert processor.vulDataIn(template, data) == '<xml>waarde, 32456</xml>'
    }

    @Test
    void nabewerkingMetDatumOk() {
        def template = '''${vandaag()}, ${timestamp?string('yyyy')}'''
        def datum = new Date()

        assert processor.voerNabewerkingUit(template, [:]) == datum.format('yyyy-MM-dd') + ', ' + datum.format('yyyy')
    }

    @Test
    void rapporteertOptiesVoorNietBestaandeSleutel() {
        def template = '/test-template.ftl'
        def data = [veld: 1, getol: 2]

        assert processor.vulDataIn(template, data) == '<xml>1, '
    }

    @Test
    void rapporteertOptiesVoorNietBestaandeComplexeSleutel() {
        def template = '/test-template2.ftl'
        def data = [veld: [naam:'foo'], getal: [waard:2]]

        assert processor.vulDataIn(template, data) == '<xml>foo, '
    }
}
