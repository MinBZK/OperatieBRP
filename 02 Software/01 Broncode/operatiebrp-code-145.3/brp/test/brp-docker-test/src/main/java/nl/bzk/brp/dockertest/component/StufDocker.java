/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;

/**
 * Component voor Stuf.
 */
@DockerInfo(
        image = "brp/stuf",
        logischeNaam = DockerNaam.STUF,
        afhankelijkheden = {DockerNaam.BRPDB, ARCHIVERINGDB, DockerNaam.SLEUTELBOS},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class StufDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "stuf");
    }

    @Override
    public String getJmxDomain() {
        return "stuf";
    }
}
