package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateException
import groovy.sql.Sql
import java.text.SimpleDateFormat
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Matchers

class ObjectsleutelDirectiveTest {
    private def format = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss')
    private Template template = getTemplate('<xml><@objectsleutel var=bsn partij=zendendePartij/></xml>')

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Before
    void maakTestPersoonAan() {
        Sql sql = new SqlProcessor(Database.KERN).sql
        sql.executeUpdate("delete from kern.pers where id = 1003")
        sql.executeUpdate("insert into kern.pers (id, bsn, srt) values (1003, 677080426, 1)")
    }

    @Test
    void kanDirectiveUitvoeren_SleutelEnPartij() {
        def data = ['bsn': '677080426', 'zendendePartij': '12345', timestamp: format.parse('2014-10-13 16:15:34')]

        Writer out = new StringWriter()
        template.process(data, out)

        assert out.toString().equals('<xml>NBZy4+h1Ky7ipk5s7dWbhn8I2Lnlf9Rb</xml>')
    }

    @Test
    void kanDirectiveUitvoeren_OverridePartij() {
        def data = ['bsn': '677080426', 'overruleObjectSleutelPartij': '98765', timestamp: format.parse('2013-01-01 23:54:56')]

        Writer out = new StringWriter()
        template.process(data, out)

        assert out.toString().equals('<xml>NBZy4+h1Ky7j1UdCIaSQhvGi72AOw7KU</xml>')
    }

    @Test
    void kanDirectiveUitvoeren_OverrideTimestamp() {
        def data = ['bsn': '677080426', zendendePartij: '12345', 'overruleObjectSleutelTijdstip': format.parse('2014-10-10 13:00:00'), timestamp: new Date()]

        Writer out = new StringWriter()
        template.process(data, out)

        assert out.toString().equals('<xml>NBZy4+h1Ky5YpW5cdxgz16Uzn53WNImW</xml>')
    }

    @Test
    void exceptieAlsBsnOntbreekt() {
        thrown.expect(TemplateException)
        thrown.expectMessage('attribuut bsn van de directive @objectsleutel is null')

        def data = [zendendePartij: '12345', timestamp: new Date()]

        Writer out = new StringWriter()
        template.process(data, out)
    }

    @Test
    void exceptieAlsPartijOntbreekt() {
        thrown.expect(TemplateException)
        thrown.expectMessage('attribuut partij van de directive @objectsleutel is null')

        def data = [bsn: '123345', timestamp: new Date()]

        Writer out = new StringWriter()
        template.process(data, out)
    }

    @Test
    void okAlsBlokhakenWordenGebruikt() {
        def data = [bsn: '[]', zendendePartij: '12342', timestamp: new Date()]

        Writer out = new StringWriter()
        template.process(data, out)

        assert out.toString() == '<xml>[]</xml>'
    }

    private Template getTemplate(String text) {
        Configuration cfg = new Configuration()
        cfg.setClassForTemplateLoading(getClass(), '/')
        cfg.setSharedVariable('objectsleutel', new ObjectsleutelDirective())

        return new Template('temporary', new StringReader(text), cfg)
    }
}
