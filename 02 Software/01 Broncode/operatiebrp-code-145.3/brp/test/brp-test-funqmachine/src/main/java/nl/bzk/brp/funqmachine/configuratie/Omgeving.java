/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters;

/**
 * Interface waar de applicatie zijn configuratie voor de heersende omgeving kan opvragen.
 */
public final class Omgeving {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String DEFAULT_OMGEVING = "localhost";
    private static Omgeving omgeving;
    private final OmgevingConfiguratie omgevingConfiguratie;
    private final boolean isDockerOmgeving;
    private final boolean isDockerOmgevingLokaal;

    private Omgeving(final String omgevingIdentifier) throws IOException {
        isDockerOmgeving = omgevingIdentifier.contains("dok");
        if (isDockerOmgeving) {
            final Properties dockerProperties = new Properties();
            dockerProperties.load(Omgeving.class.getResourceAsStream("/" + omgevingIdentifier + ".properties"));
            omgevingConfiguratie = OmgevingConfiguratie.maakOmgevingConfiguratieVoorDockerOmgeving(dockerProperties);
            isDockerOmgevingLokaal = Boolean.valueOf(dockerProperties.getProperty("docker.lokaal"));
        } else {
            omgevingConfiguratie = OmgevingConfiguratie.maakConfiguratieVoorKlassiekeOmgeving(getDatabaseConfig(omgevingIdentifier));
            isDockerOmgevingLokaal = false;
        }
    }

    private DatabaseConfig getDatabaseConfig(final String omgevingIdentifier) {
        final DatabaseConfig databaseConfig;
        switch (omgevingIdentifier) {
            case "db09":
                databaseConfig = new DatabaseConfig("fac-db09.modernodam.nl", "brp");
                break;
            case DEFAULT_OMGEVING:
            default:
                if (!DEFAULT_OMGEVING.equals(omgevingIdentifier)) {
                    LOGGER.warn(String.format("Onbekende omgeving (%s). Valt terug naar %s", omgevingIdentifier, DEFAULT_OMGEVING));
                }
                databaseConfig = new DatabaseConfig(DEFAULT_OMGEVING, "art-brp");
        }
        return databaseConfig;
    }

    /**
     * Geeft een omgeving terug welke gekozen kan worden door aan het proces de optie -Domgeving=<omgeving>.
     * @return een implementatie van {@link Omgeving}
     */
    public static synchronized Omgeving getOmgeving() {
        if (omgeving == null) {
            final String omgevingNaam = System.getProperty("omgeving", DEFAULT_OMGEVING);
            try {
                omgeving = new Omgeving(omgevingNaam);
            } catch (IOException e) {
                LOGGER.error("Fout tijdens maken omgeving", e);
            }
        }
        return omgeving;
    }

    public String getBrokerUrl() {
        return omgevingConfiguratie.getBrokerUrl();
    }

    public DatabaseConfig getGetDatabaseConfig(final String schema) {
        final Map<String, DatabaseConfig> databaseConfig = omgevingConfiguratie.getDatabaseConfig();
        if (!databaseConfig.containsKey(schema)) {
            throw new UnsupportedOperationException("Kan geen configuratie vinden voor schema " + schema);
        }
        return databaseConfig.get(schema);
    }

    public String getHostURL() {
        return omgevingConfiguratie.getApplicationHost();
    }


    public String getNotificatieBerichtQueue() {
        return omgevingConfiguratie.getNotificatieBerichtQueueName();
    }

    /**
     * Geeft aan of het een docker omgeving is of niet.
     * @return true als het een docker omgeving is
     */
    public boolean isDockerOmgeving() {
        return isDockerOmgeving;
    }

    /**
     * Geeft aan of het een docker omgeving lokaal is of niet.
     * @return true als het een docker omgeving is
     */
    public boolean isDockerOmgevingLokaal() {
        return isDockerOmgevingLokaal;
    }

    /**
     * Geeft de {@link SoapParameters} terug voor opgegeven endpoint en namespace.
     * @param endpoint het endpoint
     * @param namespace de namespace
     * @return een {@link SoapParameters}
     */
    public SoapParameters getSoapParameters(final String endpoint, final String namespace) {
        return new SoapParameters(omgevingConfiguratie.getSoapEndpointConfig().getSoapEndpoint(endpoint), namespace);
    }

    public SoapEndpointConfig getSoapEndpointConfig() {
        return omgevingConfiguratie.getSoapEndpointConfig();
    }

    public SoapParameters soapParameters(final String xmlRootElement) {
        return omgevingConfiguratie.soapParameters(xmlRootElement);
    }

    /**
     * Geeft de JMX URL terug om te verbinden met de JMX port op de dokker server.
     *
     * @return de jmx url
     */
    public String getJmxUrl() {
        return omgevingConfiguratie.getJmxUrl();
    }
}
