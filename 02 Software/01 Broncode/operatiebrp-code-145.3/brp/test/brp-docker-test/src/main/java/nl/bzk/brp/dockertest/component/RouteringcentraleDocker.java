/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;


/**
 * Dockercomponent voor de routeringcentrale.
 */
@DockerInfo(
        image = "brp/message-broker",
        logischeNaam = DockerNaam.ROUTERINGCENTRALE,
        afhankelijkheden = {DockerNaam.ACTIVEMQDB},
        internePoorten = {Poorten.APPSERVER_PORT, Poorten.JMS_POORT, Poorten.JMX_POORT},
        bootLevel = 50
)
final class RouteringcentraleDocker extends AbstractJmsDocker {

    @Override
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = super.getEnvironment();
        map.put("ROUTERINGCENTRALE_ENV_PORT", String.valueOf(Poorten.JMS_POORT));
        return map;
    }

    @Override
    protected Map<String, String> getEnvironmentVoorDependency() {
        final Map<String, String> map = super.getEnvironment();
        map.put("ROUTERINGCENTRALE_ENV_HOSTNAME", getOmgeving().getDockerHostname());
        map.put("ROUTERINGCENTRALE_ENV_PORT", String.valueOf(getPoortMap().get(Poorten.JMS_POORT)));
        return map;
    }


//    @Override
//    protected List<String> geefOverigeRunCommandos() {
//        final List<String> strings = Lists.newArrayList(super.geefOverigeRunCommandos());
//        //default is 1024
//        strings.add("-c");
//        strings.add("10240");
//        strings.add("--blkio-weight");
//        //default is 500
//        strings.add("1000");
//        return strings;
//    }
}
