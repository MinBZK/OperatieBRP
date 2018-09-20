package nl.bzk.brp.funqmachine.configuratie

import groovy.text.GStringTemplateEngine
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Abstractie voor de omgevingsconfiguratie van de FunqMachine.
 * Deze klasse gebruik een groovy {@link ConfigSlurper} om het bestand
 * {@code landscape.groovy} in te lezen met de configuratie.
 */
class Environment {
    private static final Logger LOGGER = LoggerFactory.getLogger('nl.bzk.brp.Configuratie')

    def dockerProperties = new Properties()
    def configuration
    def omgeving
    boolean isDockerOmgeving
    boolean isDockerOmgevingLokaal
    private static Environment env


    public Environment(String omgeving) {
        this.omgeving = omgeving
        LOGGER.info "-[ OMGEVING: $omgeving ]--".padRight(80, '-')

        this.isDockerOmgeving = omgeving.startsWith("docker-")
        if (isDockerOmgeving) {
            LOGGER.info("Docker omgeving actief")
            dockerProperties.load(Environment.class.getResourceAsStream('/' + omgeving + '.properties'))
            isDockerOmgevingLokaal = Boolean.valueOf(dockerProperties.getProperty("docker.lokaal"))
        } else {
            configuration = createConfig(omgeving).parse(getClass().getResource('/landscape.groovy').toURI().toURL())
            printOut(configuration)
        }

        LOGGER.debug '-'*80
        LOGGER.debug '\n'
    }

    /**
     * Geeft een instantie van {@link Environment}. De gewenste omgeving moet
     * beschikbaar zijn als system property met de naam {@code omgeving}. Bij
     * afwezigheid van deze property wordt de default waarde localhost gebruikt.
     *
     * @return een instantie van {@link Environment}
     */
    static Environment instance() {
        String omgeving = System.getProperty('omgeving', 'localhost')
        if (env == null) {
            env = new Environment(omgeving)
        }
        return env
    }

    private def createConfig = { String env ->
        def configSlurper = new ConfigSlurper(env)
        configSlurper
    }

    private void printOut(Map waardes, int indent = 1) {
        waardes.each { k, v ->
            if (v instanceof Map) {
                LOGGER.debug "${'  ' * indent}$k"
                printOut(v, indent + 1)
            } else {
                LOGGER.debug "${'  ' * indent}$k = $v"
            }
        }
    }

    /**
     * Geeft de mogelijkheid om configuratie waardes in een template te laten
     * plaatsen.
     *
     * @param template de template met keys die in de configuratie bekend zijn
     * @return een geparsde template
     */
    String parse(String template) {
        def engine = new GStringTemplateEngine()
        def result = engine.createTemplate(template).make([applications: env.configuration.applications, database: env.configuration.database])

        return result.toString()
    }

    DatabaseConfig getGetDatabaseConfig(final Database database) {

        String driverClassName, username, password, url

        if (isDockerOmgeving) {
            url = dockerProperties.get('database.kern.url')
            username = dockerProperties.get('database.kern.username')
            password = dockerProperties.get('database.kern.password')
            driverClassName = dockerProperties.get('database.kern.driverClassName')
        } else {
            def configurationDb = configuration.database
            String databaseKleineLetters = database.toString().toLowerCase()
            driverClassName = configurationDb.getAt(databaseKleineLetters).driverClassName
            username = configurationDb.getAt(databaseKleineLetters).username
            password = configurationDb.getAt(databaseKleineLetters).password
            url = configurationDb.getAt(databaseKleineLetters).url
        }
        DatabaseConfig config = new DatabaseConfig(driverClassName, username, password, url)
        return config;

    }

    String getBrokerURL() {
        if (isDockerOmgeving) {
            return dockerProperties.get('routeringcentrale.broker.url')
        } else {
            return configuration.queues.broker
        }
    }

    String getHostURL() {
        if (isDockerOmgeving) {
            // Dient nog ingevuld te worden
            return ""
        } else {
            return configuration.applications.host
        }
    }

    String getAdmhQueue() {
        if (isDockerOmgeving) {
            return dockerProperties.get('routeringcentrale.admhnd.queue')
        } else {
            return configuration.queues.admhnd;
        }
    }

    SoapParameters getSoapParameters(final String endpoint, final String namespace) {
        String endpointMetHostIngevuld
        if (isDockerOmgeving) {
            String host;
            String port;

            String [] ops = ['bijhouding', 'bevraging', 'afnemerindicaties', 'synchronisatie']
            for (String op : ops) {
                if (endpoint.contains("/${op}")) {
                    host = dockerProperties.get(op + ".host")
                    port = dockerProperties.get(op + ".port")
                    break;
                }
            }
            endpointMetHostIngevuld = "http://${host}:${port}" + endpoint.substring(endpoint.indexOf("/"));
        } else {
            endpointMetHostIngevuld = parse(endpoint)

        }
        SoapParameters soapParameters = new SoapParameters(endpointMetHostIngevuld, namespace)
        return soapParameters
    }

    JmxCommand getApplicationBrokerObject() {
        String mutatieleveringJmxUrl

        if (isDockerOmgeving) {
            mutatieleveringJmxUrl = dockerProperties.getProperty('mutatielevering.jmxUrl')
        } else {
            mutatieleveringJmxUrl = configuration.jmx.url
        }

        return new JmxCommand(mutatieleveringJmxUrl, '', "nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker", null)
    }

    List<JmxCommand> getApplicationCacheObjects() {
        String mutatieleveringJmxUrl
        String synchronisatieJmxUrl
        String afnemerindicatiesJmxUrl
        String verzenderJmxUrl
        String bevragingJmxUrl

        if (isDockerOmgeving) {
            mutatieleveringJmxUrl = dockerProperties.getProperty('mutatielevering.jmxUrl')
            synchronisatieJmxUrl = dockerProperties.getProperty('synchronisatie.jmxUrl')
            afnemerindicatiesJmxUrl = dockerProperties.getProperty('afnemerindicaties.jmxUrl')
            bevragingJmxUrl = dockerProperties.getProperty('bevraging.jmxUrl')
            verzenderJmxUrl = dockerProperties.getProperty('verzending.jmxUrl')
        } else {
            mutatieleveringJmxUrl = configuration.jmx.url
            synchronisatieJmxUrl = configuration.jmx.url
            afnemerindicatiesJmxUrl = configuration.jmx.url
            verzenderJmxUrl = configuration.jmx.url
            bevragingJmxUrl = configuration.jmx.url
        }

        return Arrays.asList(
                new JmxCommand(mutatieleveringJmxUrl, 'levering', JmxCommand.OBJECT_NAME_BASIS_AUTAUT_CACHE, 'herlaadViaJmx'),
                new JmxCommand(synchronisatieJmxUrl, 'synchronisatie', JmxCommand.OBJECT_NAME_BASIS_AUTAUT_CACHE, 'herlaadViaJmx'),
                new JmxCommand(afnemerindicatiesJmxUrl, 'onderhoud-afnermerindicaties', JmxCommand.OBJECT_NAME_BASIS_AUTAUT_CACHE, 'herlaadViaJmx'),
                new JmxCommand(bevragingJmxUrl, 'bevraging', JmxCommand.OBJECT_NAME_BASIS_AUTAUT_CACHE, 'herlaadViaJmx'),
                new JmxCommand(verzenderJmxUrl, 'verzending', JmxCommand.OBJECT_NAME_VERZENDER, 'updateAfnemers'),

                new JmxCommand(mutatieleveringJmxUrl, 'levering', JmxCommand.OBJECT_NAME_BASIS_STAMTABEL_CACHE, 'herlaadViaJmx'),
                new JmxCommand(synchronisatieJmxUrl, 'synchronisatie', JmxCommand.OBJECT_NAME_BASIS_STAMTABEL_CACHE, 'herlaadViaJmx'),
                new JmxCommand(afnemerindicatiesJmxUrl, 'onderhoud-afnermerindicaties', JmxCommand.OBJECT_NAME_BASIS_STAMTABEL_CACHE, 'herlaadViaJmx'),
                new JmxCommand(bevragingJmxUrl, 'bevraging', JmxCommand.OBJECT_NAME_BASIS_STAMTABEL_CACHE, 'herlaadViaJmx')
        );
    }
}
