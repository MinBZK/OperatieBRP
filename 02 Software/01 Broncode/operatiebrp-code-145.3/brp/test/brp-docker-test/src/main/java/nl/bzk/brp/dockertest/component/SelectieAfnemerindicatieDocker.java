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
        image = "brp/selectie-afnemerindicatie",
        logischeNaam = DockerNaam.SELECTIE_AFNEMERINDICATIE,
        afhankelijkheden = {DockerNaam.BRPDB, DockerNaam.SELECTIE_ROUTERINGCENTRALE, DockerNaam.ROUTERINGCENTRALE},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class SelectieAfnemerindicatieDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "selectie-afnemerindicatie");
    }

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        map.put("SELECTIEBESTAND_FOLDER", "/dummy1");
        map.put("SELECTIE_RESULTAAT_FOLDER", "/dummy2");
        return map;
    }

    @Override
    public String getJmxDomain() {
        return "selectie-afnemerindicatie";
    }
}

