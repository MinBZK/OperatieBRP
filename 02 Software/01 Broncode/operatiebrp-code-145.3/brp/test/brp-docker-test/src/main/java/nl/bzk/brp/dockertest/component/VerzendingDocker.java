/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;

import java.util.Map;

/**
 * Dockercomponent voor Verzending.
 */
@DockerInfo(
        image = "brp/verzending",
        logischeNaam = DockerNaam.VERZENDING,
        afhankelijkheden = {DockerNaam.BRPDB, ARCHIVERINGDB, PROTOCOLLERINGDB, DockerNaam.ROUTERINGCENTRALE, DockerNaam.SLEUTELBOS},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class VerzendingDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> env = super.getEnvironment();
        env.put("KENNISGEVING_CLIENT_KEYSTORE", "/usr/share/tomcat/lib/client.jks");
        env.put("KENNISGEVING_CLIENT_PASSWORD", "client");
        env.put("KENNISGEVING_CLIENT_KEY_PASSWORD", "clientkey");
        env.put("KENNISGEVING_AFNEMER_TRUSTSTORE", "/usr/share/tomcat/lib/afnemers-trust.jks");
        env.put("KENNISGEVING_AFNEMER_TRUSTSTORE_PASSWORD", "afnemers");
        return env;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "verzending");
    }

    @Override
    public String getJmxDomain() {
        return "verzending";
    }
}
