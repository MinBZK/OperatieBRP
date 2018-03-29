/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * Component voor Beheer.
 */
@DockerInfo(
        image = "brp/beheer",
        logischeNaam = DockerNaam.BEHEER,
        afhankelijkheden = {DockerNaam.BRPDB, DockerNaam.ARCHIVERINGDB, DockerNaam.PROTOCOLLERINGDB, DockerNaam.VRIJBERICHT},
        internePoorten = {Poorten.APPSERVER_PORT}
)
final class BeheerDocker extends AbstractDocker {

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        //dummy
        map.put("ISC_CONSOLE_HOST", "localhost");
        return map;
    }

    @Override
    protected Integer geefPoortVoorkeur(int internepoort) {
        if (internepoort == Poorten.APPSERVER_PORT) {
            return Poorten.APPSERVER_PORT + 1;
        }
        return super.geefPoortVoorkeur(internepoort);
    }
}
