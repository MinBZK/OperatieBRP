/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Component voor Beheer.
 */
@DockerInfo(
        image = "brp/beheer-ng2",
        logischeNaam = DockerNaam.BEHEER_SELECTIE_FRONTEND,
        afhankelijkheden = {DockerNaam.BEHEER_SELECTIE},
        internePoorten = {Poorten.HTTP_POORT},
        bootLevel = DockerInfo.DEFAULT_BOOTLEVEL + 10
)
final class BeheerSelectieFrontendDocker extends AbstractDocker {

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = Maps.newHashMap();
        map.put("DOCKER_IP", getOmgeving().getDockerHostname());
        final Docker docker = getOmgeving().geefDocker(DockerNaam.BEHEER_SELECTIE);
        map.put("API_URL", String.format("http://%s:%s/api", getOmgeving().getDockerHostname(), String.valueOf(docker.getPoortMap().get(Poorten.APPSERVER_PORT))));
        return map;
    }

    @Override
    protected Integer geefPoortVoorkeur(int internepoort) {
        if (internepoort == Poorten.HTTP_POORT) {
            return Poorten.HTTP_POORT + 1;
        }
        return super.geefPoortVoorkeur(internepoort);
    }
}
