package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.core.Environment
import freemarker.template.SimpleDate
import freemarker.template.SimpleScalar
import freemarker.template.TemplateDirectiveBody
import freemarker.template.TemplateDirectiveModel
import freemarker.template.TemplateException
import freemarker.template.TemplateModel
import groovy.sql.Sql
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.xml.EncryptionUtil
import org.apache.commons.lang.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ObjectsleutelDirective implements TemplateDirectiveModel {
    private static final Logger logger = LoggerFactory.getLogger(ObjectsleutelDirective)

    private static final String PARAM_BSN = 'var'
    private static final String PARAM_PARTIJ = 'partij'

    public static final String OVERRIDE_PARTIJ = 'overruleObjectSleutelPartij'
    public static final String OVERRIDE_TIMESTAMP = 'overruleObjectSleutelTijdstip'

    @Override
    void execute(
        final Environment env, final Map params, final TemplateModel[] loopVars, final TemplateDirectiveBody body) throws TemplateException, IOException {

        final Writer out = env.out

        // hebben we 2 parameters?
        assert params.keySet().containsAll([PARAM_BSN, PARAM_PARTIJ])

        def data = env.dataModel

        def partijCode = data.get(OVERRIDE_PARTIJ) ?: params.get(PARAM_PARTIJ)
        if (partijCode == null) {
            throw new TemplateException('attribuut partij van de directive @objectsleutel is null', env)
        }

        partijCode = ((SimpleScalar) partijCode)?.asString as Integer

        def persoonId = ((SimpleScalar) params.get(PARAM_BSN))?.asString
        if (persoonId == null) {
            throw new TemplateException('attribuut bsn van de directive @objectsleutel is null', env)
        } else if (persoonId =~ /\[(-?[1-9])?\]/ || (StringUtils.isNumeric(persoonId) && persoonId.length() < 8)) {
            out.write(persoonId)
            return
        } else if (!StringUtils.isNumeric(persoonId)) {
            out.write(persoonId)
            return
        }

        persoonId = geeftPersoonId(persoonId)
        if (persoonId == null) {
            return
        }

        def timestamp = data.get(OVERRIDE_TIMESTAMP) ?: data.get('timestamp')
        timestamp = ((SimpleDate) timestamp)?.asDate?.time

        def sleutel = new EncryptionUtil().genereerObjectSleutelString(persoonId as Integer, partijCode, timestamp)

        out.write(sleutel)
    }

    private Integer geeftPersoonId(String bsn) {
        if (StringUtils.isBlank(bsn)) {
            return null
        }
        if (bsn.startsWith('db')) {
            return bsn.substring(2) as Integer
        }

        Sql sql = new SqlProcessor(Database.KERN).sql
        try {
            def queryResult = sql.firstRow("SELECT id FROM kern.pers WHERE bsn = ${bsn as int}")
            if (queryResult == null) {
                throw new IllegalStateException("Geen persoon gevonden met BSN $bsn")
            }
            return (Integer) queryResult[0]
        } finally {
            sql?.close()
        }

        return null
    }
}
