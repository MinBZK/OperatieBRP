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
 * Component voor Bevraging.
 */
@DockerInfo(
        image = "brp/bevraging",
        logischeNaam = DockerNaam.BEVRAGING,
        afhankelijkheden = {DockerNaam.BRPDB, ARCHIVERINGDB, PROTOCOLLERINGDB, DockerNaam.ROUTERINGCENTRALE, DockerNaam.SLEUTELBOS},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class BevragingDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar()
                && VersieUrlChecker.check(this, "bevraging");
    }

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        map.put("brp.bevraging.zoekpersoon.max.conc.request", "2");
        map.put("brp.bevraging.zoekpersoon.max.tussenresultaat", "12");
        map.put("brp.bevraging.zoekpersoon.max.costs.queryplan", "250");
        return map;
    }

    @Override
    public String getJmxDomain() {
        return "bevraging";
    }
}
