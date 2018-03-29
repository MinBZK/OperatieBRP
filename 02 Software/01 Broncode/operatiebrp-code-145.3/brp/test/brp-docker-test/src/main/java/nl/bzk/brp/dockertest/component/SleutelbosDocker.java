/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * Dockercomponent voor de Sleutelbos.
 *
 * Dit is een speciale container die dient als wrapper voor het volume dat
 * de keystores bevat.
 */
@DockerInfo(
        image = "brp/sleutel",
        logischeNaam = DockerNaam.SLEUTELBOS,
        bootLevel = 0,
        isVolume = true
)
final class SleutelbosDocker extends AbstractDocker {

    @Override
    protected Map<String, String> getEnvironmentVoorDependency() {
        final Map<String, String> map = super.getEnvironmentVoorDependency();
        map.put("SLEUTELBOS_ENV_PRIVATEFILE", "/keystores/privatestore.jks");
        map.put("SLEUTELBOS_ENV_PRIVATEKEY", "serverkey");
        map.put("SLEUTELBOS_ENV_PRIVATEPASSWORD", "privatestore");
        map.put("SLEUTELBOS_ENV_PUBLICFILE", "/keystores/publicstore.jks");
        map.put("SLEUTELBOS_ENV_PUBLICPASSWORD", "publicstore");
        map.put("SLEUTELBOS_ENV_PRIVATEKEYPASSWORD", "serverkeypassword");
        return map;
    }
}
