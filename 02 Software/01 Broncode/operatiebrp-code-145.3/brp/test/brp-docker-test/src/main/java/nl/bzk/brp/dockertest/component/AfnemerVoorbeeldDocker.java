/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * Dockercomponent voor AfnemerVoorbeeld.
 *
 * NB : Om database persistentie te gebruiken moet afhankelijkheid voor afnemervoorbeelddb worden toegevoegd.
 */
@DockerInfo(
        image = "brp/afnemervoorbeeld",
        logischeNaam = DockerNaam.AFNEMERVOORBEELD,
        afhankelijkheden = {DockerNaam.ROUTERINGCENTRALE},
        internePoorten = {Poorten.APPSERVER_PORT}
)
final class AfnemerVoorbeeldDocker extends AbstractDocker implements LogfileAware {

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "afnemervoorbeeld");
    }

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        map.put("brp.afnemervoorbeeld.isqueuepersistent", "true");
        map.put("brp.afnemervoorbeeld.ispersistent", "false");
        return map;
    }
}
