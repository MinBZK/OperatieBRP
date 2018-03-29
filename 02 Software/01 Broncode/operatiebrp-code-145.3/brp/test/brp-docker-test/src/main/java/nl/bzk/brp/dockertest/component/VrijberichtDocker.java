/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;

import java.util.Map;

/**
 * Component voor Vrijbericht.
 */
@DockerInfo(
        image = "brp/vrijbericht",
        logischeNaam = DockerNaam.VRIJBERICHT,
        afhankelijkheden = {DockerNaam.BRPDB, ARCHIVERINGDB, DockerNaam.ROUTERINGCENTRALE, DockerNaam.SLEUTELBOS},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class VrijberichtDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "vrijbericht");
    }

    @Override
    protected Map<String, String> getEnvironmentVoorDependency() {
        final Map<String, String> map = super.getEnvironmentVoorDependency();
        map.put("VRIJBERICHT_SERVICE_URL",
                String.format("http://${DOCKER_IP}:%d/vrijbericht/VrijBerichtService/vrbStuurVrijBericht?wsdl", getPoortMap().get(Poorten.APPSERVER_PORT)));
        return map;
    }

    @Override
    public String getJmxDomain() {
        return "vrijbericht";
    }
}
