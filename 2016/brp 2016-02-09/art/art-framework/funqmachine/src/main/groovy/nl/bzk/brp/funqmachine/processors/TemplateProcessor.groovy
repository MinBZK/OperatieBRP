package nl.bzk.brp.funqmachine.processors

import freemarker.core.InvalidReferenceException
import freemarker.template.Configuration
import freemarker.template.DefaultObjectWrapper
import freemarker.template.Template
import freemarker.template.TemplateException
import freemarker.template.TemplateExceptionHandler
import freemarker.template.Version
import nl.bzk.brp.funqmachine.processors.freemarker.MomentMethod
import nl.bzk.brp.funqmachine.processors.freemarker.MomentVolgenMethod
import nl.bzk.brp.funqmachine.processors.freemarker.ObjectsleutelDirective
import nl.bzk.brp.funqmachine.processors.freemarker.PersoonIdLookupDirective
import nl.bzk.brp.funqmachine.processors.freemarker.VandaagMethod
import nl.bzk.brp.funqmachine.processors.freemarker.VandaagSqlMethod
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TemplateProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TemplateProcessor.class)

    private ObjectsleutelDirective objectsleutelDirective = new ObjectsleutelDirective()

    private PersoonIdLookupDirective persoonIdLookupDirective = new PersoonIdLookupDirective()

    private static final String PARAM_TIMESTAMP = 'timestamp'

    Configuration cfg
    Date timestamp

    TemplateProcessor() {
        this(new Date())
    }

    TemplateProcessor(Date date) {
        this.timestamp = date
        createFreemarkerConfig()
    }

    String verwerkTemplateBestand(String templateName, Map data) {
        def tmp = vulDataIn(templateName, data)
        return voerNabewerkingUit(tmp, data)
    }

    String verwerkTemplateString(String template, Map data = [:]) {
        return voerNabewerkingUit(template, data)
    }

    protected String vulDataIn(String templateName, Map data) {
        Template template = cfg.getTemplate(templateName)
        data.put(PARAM_TIMESTAMP, this.timestamp)

        Writer out = new StringWriter()
        try {
            template.process(data, out)
        } catch (InvalidReferenceException ire) {
            def error, opties
            (error, opties) = geefMogelijkeSleutels(ire, data)
            logger.error 'Template gebruikt onbekende referentie [{}],\n  mogelijk bedoelde je: {}', error, opties.join(', ')
        } catch (TemplateException te) {
            logger.error 'fout in template: {}', te.message
        }
        return out.toString()
    }

    private def geefMogelijkeSleutels(InvalidReferenceException e, Map data) {
        def matcher = e.getFTLInstructionStack() =~ /\$\{(.*)\}/

        if (matcher.iterator().hasNext()) {
            String sleutel = matcher[0][1]

            def result = []
            if (sleutel) {
                def sleutels = getKeys(data)

                sleutels.each {String k ->
                    if (StringUtils.getJaroWinklerDistance(sleutel, k) > 0.9) {
                        result << k
                    }
                }
            }

            return [sleutel, result]
        } else {
            return ['???', []]
        }
    }

    private List<String> getKeys(Map hash, String prefix = '') {
        def list = []
        hash.each { it ->
            list << "${prefix}${it.key}"

            if (it.value instanceof Map) {
                list << getKeys((Map) it.value, "${it.key}.")
            }
        }

        return list.flatten()
    }

    protected String voerNabewerkingUit(String text, Map data) {
        Template template = new Template('temporary', new StringReader(text), cfg)

        Writer out = new StringWriter()
        try {
            data.put(PARAM_TIMESTAMP, this.timestamp)
            template.process(data, out)
        } catch (InvalidReferenceException ire) {
            def error, opties
            (error, opties) = geefMogelijkeSleutels(ire, data)
            logger.error 'Template gebruikt onbekende referentie [{}],\n  mogelijk bedoelde je: {}', error, opties.join(', ')
        } catch (TemplateException te) {
            logger.error 'fout in template: {}', te.message
        }
        def xml = out.toString()

        return xml
    }

    private createFreemarkerConfig() {
        cfg = new Configuration()
        cfg.setClassForTemplateLoading(this.getClass(), '/')
        cfg.setObjectWrapper(new DefaultObjectWrapper())
        cfg.setDefaultEncoding('UTF-8')
        cfg.setLocale(new Locale('nl', 'NL'))
        cfg.setNumberFormat('####.##')
        cfg.setWhitespaceStripping(true)
        cfg.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX)
        cfg.setStrictSyntaxMode(true)
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
        cfg.setIncompatibleImprovements(new Version(2, 3, 20))

        cfg.setSharedVariable('vandaag', new VandaagMethod())
        cfg.setSharedVariable('vandaagsql', new VandaagSqlMethod())
        cfg.setSharedVariable('moment', new MomentMethod())
        cfg.setSharedVariable('moment_volgen', new MomentVolgenMethod())
        cfg.setSharedVariable('objectsleutel', objectsleutelDirective)
        cfg.setSharedVariable('persoon_id_voor', persoonIdLookupDirective)
    }
}
