package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.core.Environment
import freemarker.template.TemplateDirectiveBody
import freemarker.template.TemplateDirectiveModel
import freemarker.template.TemplateException
import freemarker.template.TemplateModel
import groovy.sql.Sql
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import org.springframework.stereotype.Component

@Component
class PersoonIdLookupDirective implements TemplateDirectiveModel {

    private static final String PERSOON_ID_SQL = 'SELECT id FROM kern.pers WHERE bsn = '

    @Override
    void execute(
        final Environment env, final Map params, final TemplateModel[] loopVars, final TemplateDirectiveBody body) throws TemplateException, IOException {

        assert params.keySet().containsAll(['bsn'])

        def bsn = params.get('bsn')
        Sql sql = new SqlProcessor(Database.KERN).sql

        try {
            if (bsn == null) {
                throw new TemplateException("[Referentiedata] Geen BSN gevonden voor variabele", Environment.currentEnvironment)
            }

            def queryResult = sql.firstRow(PERSOON_ID_SQL + bsn)
            if (queryResult == null) {
                throw new IllegalStateException("[Referentiedata] Geen persoon gevonden met BSN $bsn")
            }
            def persoonId = queryResult[0] as String

            env.getOut().write(persoonId)
        } finally {
            sql?.close()
        }
    }
}
