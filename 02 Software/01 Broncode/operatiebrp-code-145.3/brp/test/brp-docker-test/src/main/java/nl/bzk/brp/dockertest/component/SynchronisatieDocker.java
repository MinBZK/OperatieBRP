/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;

/**
 * Dockercomponent voor Synchronisatie.
 */
@DockerInfo(
        image = "brp/synchronisatie",
        logischeNaam = DockerNaam.SYNCHRONISATIE,
        afhankelijkheden = {DockerNaam.BRPDB, ARCHIVERINGDB, DockerNaam.ROUTERINGCENTRALE, DockerNaam.SLEUTELBOS},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMX_POORT}
)
final class SynchronisatieDocker extends AbstractDocker implements CacheSupport {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "synchronisatie");
    }

    @Override
    public String getJmxDomain() {
        return "synchronisatie";
    }
}
