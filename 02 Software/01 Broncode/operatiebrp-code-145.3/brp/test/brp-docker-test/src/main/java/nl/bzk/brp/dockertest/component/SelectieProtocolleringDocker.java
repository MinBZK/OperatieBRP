/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * SelectieComponent.
 */
@DockerInfo(
        image = "brp/selectie-protocollering",
        logischeNaam = DockerNaam.SELECTIE_PROTOCOLLERING,
        afhankelijkheden = {DockerNaam.BRPDB, DockerNaam.PROTOCOLLERINGDB},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT},
        dynamicVolumes = {"/selectie"}
)
final class SelectieProtocolleringDocker extends AbstractDocker implements LogfileAware, JMXSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar()
                && VersieUrlChecker.check(this, "selectie-protocollering");
    }

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        map.put("SELECTIE_RESULTAAT_FOLDER", getDockerInfo().dynamicVolumes()[0]);
        map.put("SELECTIEBESTAND_FOLDER", "/dummy");
        return map;
    }

    @Override
    public String getJmxDomain() {
        return "selectie-protocollering";
    }
}

