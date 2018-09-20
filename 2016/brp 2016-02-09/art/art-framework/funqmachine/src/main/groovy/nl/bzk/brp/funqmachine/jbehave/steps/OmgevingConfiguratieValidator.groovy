package nl.bzk.brp.funqmachine.jbehave.steps

import groovyx.net.http.HTTPBuilder
import java.sql.SQLException
import java.util.jar.Attributes
import java.util.jar.Manifest
import javax.annotation.PostConstruct
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 */
@Component
class OmgevingConfiguratieValidator {
    private final Logger logger = LoggerFactory.getLogger('nl.bzk.brp.Configuratie')
    private final int VIJFSECONDEN = 5

    private Environment env = Environment.instance()
    private
    def apps = ['bijhouding', 'bevraging', 'afnemerindicaties', 'synchronisatie', 'mutatielevering', 'protocollering', 'routering-centrale', 'verzending']

    @PostConstruct
    void valideerDatabase() {
        try {
            new SqlProcessor(Database.KERN)
        } catch (SQLException e) {
            logger.error 'De database {} is niet bereikbaar: {}', env.getGetDatabaseConfig(Database.KERN).url, e.message
            throw new RuntimeException(e)
        }
    }

    //@PostConstruct
    void valideerApplicaties() {
        def http = new HTTPBuilder(env.configuration.applications.host)
        //HTTPBuilder has no direct methods to add timeouts.  We have to add them to the HttpParams of the underlying HttpClient
        http.client.params.setParameter("http.connection.timeout", new Integer(VIJFSECONDEN))
        http.client.params.setParameter("http.socket.timeout", new Integer(VIJFSECONDEN))

        def results = [:]
        apps.collectEntries(results) { app ->
            try {
                def html = http.get(path: "/$app/versie.html")
                def status = html.'**'.findAll { 'td'.equalsIgnoreCase(it.name()) }[1].text() ?: '???'
                [(app): status]
            } catch (IOException | MissingPropertyException e) {
                [(app): e.message]
            }
        }

        logger.info "-[ APPLICATIES: $env.omgeving ]---".padRight(80, '-')
        results.each { k, v ->
            logger.debug '  {} : {}', k, v
        }
        logger.debug '-'*80
    }

    void valideerModelVersies() {
        def sql = new SqlProcessor(Database.KERN).sql

        String implementationVersion = '?'

        Class<?> clazz = PersoonHisVolledigRepository.class;
        String classPath = clazz.getResource("${clazz.simpleName}.class").toString()
        if (classPath.startsWith('jar')) {
            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF"

            def manifest = new Manifest(new URL(manifestPath).openStream())
            Attributes attrs = manifest.getMainAttributes()

            implementationVersion = attrs.getValue("Implementation-Version")
        }

        def dbversion = sql.firstRow('SELECT releaseversion FROM public.dbversion')[0] as String
        def tagVersion = sql.firstRow('SELECT tag FROM public.databasechangelog WHERE description LIKE \'tagDatabase\'')[0].split(' ')[0] as String

        if (! (dbversion.startsWith(implementationVersion) && tagVersion.startsWith(implementationVersion))) {
            logger.error 'FunqMachine data versie {} en database versie {} lopen uiteen', implementationVersion, dbversion
        }
    }

}
