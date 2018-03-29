/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

/**
 * Dockercomponent voor Administratievehandelingpublicatie.
 */
@DockerInfo(
        image = "brp/admhnd-publicatie",
        logischeNaam = DockerNaam.AH_PUBLICATIE,
        afhankelijkheden = {DockerNaam.ROUTERINGCENTRALE, DockerNaam.BRPDB},
        internePoorten = {Poorten.APPSERVER_PORT}
)
final class AdministratievehandelingPublicatieDocker extends AbstractDocker implements LogfileAware {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "admhnd-publicatie");
    }
}
