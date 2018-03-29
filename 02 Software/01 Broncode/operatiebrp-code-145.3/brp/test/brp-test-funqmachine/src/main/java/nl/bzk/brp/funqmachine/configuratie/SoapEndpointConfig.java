/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import java.util.Properties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Configuratie voor de verschillende SOAP Endpoints.
 */
public final class SoapEndpointConfig {

    private static final String BIJHOUDING = "bijhouding";
    private static final String BEVRAGING = "bevraging";
    private static final String AFNEMERINDICATIES = "afnemerindicaties";
    private static final String SYNCHRONISATIE = "synchronisatie";
    private static final String VRIJBERICHT = "vrijbericht";
    private static final String ABSOLUTE_PATH_SEPERATOR = "/";

    private final String bijhoudingHost;
    private final String bevragingHost;
    private final String afnemerindicatiesHost;
    private final String synchronisatieHost;
    private final String vrijBerichtHost;

    private final String bijhoudingPort;
    private final String bevragingPort;
    private final String afnemerindicatiesPort;
    private final String synchronisatiePort;
    private final String vrijBerichtPort;

    /**
     * Vult de verschillende SOAP Endpoints mbv de properties voor Docker.
     *
     * @param dockerProperties de properties voor docker.
     */
    public SoapEndpointConfig(final Properties dockerProperties) {
        final String hostProperty = ".host";
        final String portProperty = ".port";
        bijhoudingHost = dockerProperties.getProperty(BIJHOUDING + hostProperty);
        bijhoudingPort = dockerProperties.getProperty(BIJHOUDING + portProperty);
        bevragingHost = dockerProperties.getProperty(BEVRAGING + hostProperty);
        bevragingPort = dockerProperties.getProperty(BEVRAGING + portProperty);
        afnemerindicatiesHost = dockerProperties.getProperty(AFNEMERINDICATIES + hostProperty);
        afnemerindicatiesPort = dockerProperties.getProperty(AFNEMERINDICATIES + portProperty);
        synchronisatieHost = dockerProperties.getProperty(SYNCHRONISATIE + hostProperty);
        synchronisatiePort = dockerProperties.getProperty(SYNCHRONISATIE + portProperty);
        vrijBerichtHost = dockerProperties.getProperty(VRIJBERICHT + hostProperty);
        vrijBerichtPort = dockerProperties.getProperty(VRIJBERICHT + portProperty);
    }

    /**
     * Vult de verschillende SOAP Endpoints met dezelfde host en port.
     *
     * @param host de host waar het endpoint draait
     * @param port de port waarop het endpoint te benaderen is
     */
    public SoapEndpointConfig(final String host, final String port) {
        bijhoudingHost = host;
        bijhoudingPort = port;
        bevragingHost = host;
        bevragingPort = port;
        afnemerindicatiesHost = host;
        afnemerindicatiesPort = port;
        synchronisatieHost = host;
        synchronisatiePort = port;
        vrijBerichtHost = host;
        vrijBerichtPort = port;
    }

    public String getBijhoudingAdres() {
        return bijhoudingHost + ":" + bijhoudingPort;
    }

    public String getBevragingAdres() {
        return bijhoudingHost + ":" + bevragingPort;
    }
    /**
     * Geeft de opgegeven endpoint terug waarbij deze is aangevuld met de specifieke host/port voor de operatie (bv
     * bijhouding, bevraging).
     *
     * @param endpoint endpoint waar de host/port aan wordt toegevoegd
     * @return een correct URL voor een SOAP endpoint
     */
    public String getSoapEndpoint(final String endpoint) {
        final String soapEndpointMetHost;
        final String absolutePath = getAbsolutePath(endpoint);
        if (absolutePath.contains(ABSOLUTE_PATH_SEPERATOR + BIJHOUDING)) {
            soapEndpointMetHost = maakCorrectUrl(bijhoudingHost, bijhoudingPort, absolutePath);
        } else if (absolutePath.contains(ABSOLUTE_PATH_SEPERATOR + BEVRAGING)) {
            soapEndpointMetHost = maakCorrectUrl(bevragingHost, bevragingPort, absolutePath);
        } else if (absolutePath.contains(ABSOLUTE_PATH_SEPERATOR + AFNEMERINDICATIES)) {
            soapEndpointMetHost = maakCorrectUrl(afnemerindicatiesHost, afnemerindicatiesPort, absolutePath);
        } else if (absolutePath.contains(ABSOLUTE_PATH_SEPERATOR + SYNCHRONISATIE)) {
            soapEndpointMetHost = maakCorrectUrl(synchronisatieHost, synchronisatiePort, absolutePath);
        } else if (absolutePath.contains(ABSOLUTE_PATH_SEPERATOR + VRIJBERICHT)) {
            soapEndpointMetHost = maakCorrectUrl(vrijBerichtHost, vrijBerichtPort, absolutePath);
        } else {
            throw new IllegalStateException("Geen ondersteunde operatie gevonden");
        }

        return soapEndpointMetHost;
    }

    private String getAbsolutePath(final String endpointMetPlaceholder) {
        final String replacedString = endpointMetPlaceholder.replaceAll("//", "[[");
        return endpointMetPlaceholder.substring(replacedString.indexOf(ABSOLUTE_PATH_SEPERATOR));
    }

    private String maakCorrectUrl(final String host, final String port, final String absolutePath) {
        return "http://" + host + ":" + port + absolutePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("bijhoudingHost", bijhoudingHost)
                                        .append("bevragingHost", bevragingHost)
                                        .append("afnemerindicatiesHost", afnemerindicatiesHost)
                                        .append("synchronisatieHost", synchronisatieHost)
                                        .append("vrijberichtHost", vrijBerichtHost)
                                        .append("bijhoudingPort", bijhoudingPort)
                                        .append("bevragingPort", bevragingPort)
                                        .append("afnemerindicatiesPort", afnemerindicatiesPort)
                                        .append("synchronisatiePort", synchronisatiePort)
                                        .append("vrijberichtPort", vrijBerichtPort)
                                        .toString();
    }
}
