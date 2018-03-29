/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Properties;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Omgeving configuratie.
 */
final class OmgevingConfiguratie {
    // Default QueueNames
    private static final String ADMINISTRATIEVE_HANDELING_QUEUE_NAME = "AdministratieveHandelingen";
    private static final String NOTIFICATIE_BERICHT_QUEUE_NAME = "NotificatieBerichten";
    private static final Map<String, EndpointDefinitie> XML_ROOT_NAAR_ENDPOINT_DEFINITIE = Maps.newHashMap();

    static {
        // bevraging
        final EndpointDefinitie
                endpointDefinitieBevraging =
                new EndpointDefinitie("bevraging", "LeveringBevragingService", "lvgBevraging", "http://www.bzk.nl/brp/levering/bevraging/service");
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_bvgGeefDetailsPersoon", endpointDefinitieBevraging);
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_bvgGeefMedebewoners", endpointDefinitieBevraging);
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_bvgZoekPersoon", endpointDefinitieBevraging);
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_bvgZoekPersoonOpAdres", endpointDefinitieBevraging);
        // synchronisatie
        final EndpointDefinitie
                endpointDefinitieSynchronisatie =
                new EndpointDefinitie("synchronisatie", "SynchronisatieService", "lvgSynchronisatie", "http://www.bzk.nl/brp/levering/synchronisatie/service");
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_synGeefSynchronisatiePersoon", endpointDefinitieSynchronisatie);
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_synGeefSynchronisatieStamgegeven", endpointDefinitieSynchronisatie);
        // afnemerindicaties
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("lvg_synRegistreerAfnemerindicatie",
                new EndpointDefinitie("afnemerindicaties", "AfnemerindicatiesService", "lvgAfnemerindicaties",
                        "http://www.bzk.nl/brp/levering/afnemerindicaties/service"));
        // vrijbericht
        XML_ROOT_NAAR_ENDPOINT_DEFINITIE.put("vrb_vrbStuurVrijBericht",
                new EndpointDefinitie("vrijbericht", "VrijBerichtService", "vrbStuurVrijBericht",
                        "http://www.bzk.nl/brp/vrijbericht/vrijbericht/service"));
    }

    // Database
    private final Map<String, DatabaseConfig> databaseConfig;

    // SOAP Endpoint configuratie
    private final SoapEndpointConfig soapEndpointConfig;

    // Queue
    private final String brokerUrl;

    // JMX
    private final String jmxUrl;

    // Application
    private final String applicationHost;

    // QueueNames
    private final String administratieveHandelingQueue;
    private final String notificatieBerichtQueue;

    private OmgevingConfiguratie(
            final Map<String, DatabaseConfig> databaseConfig,
            final SoapEndpointConfig soapEndpointConfig,
            final String brokerUrl,
            final String jmxUrl,
            final String applicationHost,
            final String administratieveHandelingQueue,
            final String notificatieBerichtQueue) {
        this.databaseConfig = databaseConfig;
        this.soapEndpointConfig = soapEndpointConfig;
        this.brokerUrl = brokerUrl;
        this.jmxUrl = jmxUrl;
        this.applicationHost = applicationHost;
        this.administratieveHandelingQueue = administratieveHandelingQueue;
        this.notificatieBerichtQueue = notificatieBerichtQueue;
    }

    /**
     * Maakt een configuratie voor een klassieke omgeving (servers).
     * @param databaseConfig de configuratie voor de database.
     * @return de configuratie
     */
    static OmgevingConfiguratie maakConfiguratieVoorKlassiekeOmgeving(final DatabaseConfig databaseConfig) {
        final String host = "localhost";
        final String brokerUrl = String.format("tcp://%s:61616?jms.useAsyncSend=true", host);
        final String jmxUrl = String.format("service:jmx:rmi://%s/jndi/rmi://%s:1099/jmxrmi", host, host);
        final SoapEndpointConfig soapEndpointConfig = new SoapEndpointConfig(host, "8080");
        return new OmgevingConfiguratie(
                ImmutableMap.of("kern", databaseConfig, "ber", databaseConfig, "prot", databaseConfig),
                soapEndpointConfig,
                brokerUrl,
                jmxUrl,
                host,
                ADMINISTRATIEVE_HANDELING_QUEUE_NAME,
                NOTIFICATIE_BERICHT_QUEUE_NAME);
    }

    /**
     * Maakt een configuratie voor een Docker omgeving.
     * @param dockerProperties properties voor de docker omgeving
     * @return de configuratie
     */
    static OmgevingConfiguratie maakOmgevingConfiguratieVoorDockerOmgeving(final Properties dockerProperties) {
        final String bijhoudingJmxUrl = dockerProperties.getProperty("bijhouding.jmxUrl");

        final SoapEndpointConfig soapEndpointConfig = new SoapEndpointConfig(dockerProperties);
        return new OmgevingConfiguratie(
                mapSchemaNaarConfig(dockerProperties),
                soapEndpointConfig,
                dockerProperties.getProperty("routeringcentrale.broker.url"),
                bijhoudingJmxUrl,
                "",
                dockerProperties.getProperty("routeringcentrale.admhnd.queue"),
                dockerProperties.getProperty("routeringcentrale.notificatiebericht.queue"));
    }

    private static Map<String, DatabaseConfig> mapSchemaNaarConfig(final Properties dockerProperties) {
        return ImmutableMap.<String, DatabaseConfig>builder().put(
                "kern", new DatabaseConfig(
                        dockerProperties.getProperty("database.kern.driverClassName"),
                        dockerProperties.getProperty("database.kern.username"),
                        dockerProperties.getProperty("database.kern.password"),
                        dockerProperties.getProperty("database.kern.url")
                )).put(
                "ber", new DatabaseConfig(
                        dockerProperties.getProperty("database.ber.driverClassName"),
                        dockerProperties.getProperty("database.ber.username"),
                        dockerProperties.getProperty("database.ber.password"),
                        dockerProperties.getProperty("database.ber.url")
                )).put(
                "prot", new DatabaseConfig(
                        dockerProperties.getProperty("database.prot.driverClassName"),
                        dockerProperties.getProperty("database.prot.username"),
                        dockerProperties.getProperty("database.prot.password"),
                        dockerProperties.getProperty("database.prot.url")
                ))
                .build();
    }

    public SoapParameters soapParameters(final String xmlRootElement) {
        final EndpointDefinitie endpointDefinitie = XML_ROOT_NAAR_ENDPOINT_DEFINITIE.get(xmlRootElement);
        if (endpointDefinitie != null) {
            String endpoint = "%s/%s/%s";
            final String hostPort = getSoapEndpointConfig().getSoapEndpoint("/" + endpointDefinitie.getContext());
            endpoint =
                    String.format(endpoint, hostPort, endpointDefinitie.getServiceNaam(), endpointDefinitie.getServicePort());
            return new SoapParameters(endpoint, endpointDefinitie.getNamespace());
        }
        throw new UnsupportedOperationException("Geen endpoint gevonden voor root element " + xmlRootElement);
    }

    /**
     * Geeft de naam van de administratieve handeling queue terug.
     * @return de naam van de queue
     */
    String getAdministratieveHandelingQueueName() {
        return ADMINISTRATIEVE_HANDELING_QUEUE_NAME;
    }

    /**
     * Geeft de naam van de notificatie bericht queue terug.
     * @return de naam van de queue
     */
    String getNotificatieBerichtQueueName() {
        return NOTIFICATIE_BERICHT_QUEUE_NAME;
    }

    /**
     * Geeft de database configuratie terug.
     * @return database configuratie
     */
    Map<String, DatabaseConfig> getDatabaseConfig() {
        return databaseConfig;
    }

    /**
     * Geeft de URL van de broker terug.
     * @return URL van de broker
     */
    String getBrokerUrl() {
        return brokerUrl;
    }

    /**
     * Geeft de URL terug voor JMX.
     * @return de url voor JMX
     */
    String getJmxUrl() {
        return jmxUrl;
    }

    /**
     * Geeft de hostnaam terug van de application server.
     * @return de hostnaam
     */
    String getApplicationHost() {
        return applicationHost;
    }

    /**
     * Geeft de Queue naam terug voor de administratieve handeling queue terug.
     * @return queue naam
     */
    String getAdministratieveHandelingQueue() {
        return administratieveHandelingQueue;
    }

    /**
     * Geeft de Queue naam terug voor de notificatie bericht queue terug.
     * @return queue naam
     */
    String getNotificatieBerichtQueue() {
        return notificatieBerichtQueue;
    }

    SoapEndpointConfig getSoapEndpointConfig() {
        return soapEndpointConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("databaseConfig", databaseConfig)
                .append("soapEndpointConfig", soapEndpointConfig)
                .append("brokerUrl", brokerUrl)
                .append("jmxUrl", jmxUrl)
                .append("applicationHost", applicationHost)
                .append("administratieveHandelingQueue", administratieveHandelingQueue)
                .append("notificatieBerichtQueue", notificatieBerichtQueue)
                .toString();
    }

    private static final class EndpointDefinitie {
        private final String context;
        private final String serviceNaam;
        private final String servicePort;
        private final String namespace;

        public EndpointDefinitie(final String context, final String serviceNaam, final String servicePort, final String namespace) {
            this.context = context;
            this.serviceNaam = serviceNaam;
            this.servicePort = servicePort;
            this.namespace = namespace;
        }

        public String getContext() {
            return context;
        }

        public String getServiceNaam() {
            return serviceNaam;
        }

        public String getServicePort() {
            return servicePort;
        }

        public String getNamespace() {
            return namespace;
        }
    }
}
