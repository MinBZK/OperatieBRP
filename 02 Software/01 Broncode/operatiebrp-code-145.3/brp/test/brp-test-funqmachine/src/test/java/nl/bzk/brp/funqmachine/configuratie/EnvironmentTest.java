/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import java.net.MalformedURLException;
import java.util.Objects;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class EnvironmentTest {

    @Test
    void localEnvironment() {
        System.setProperty("omgeving", "localhost");
        Omgeving env = Omgeving.getOmgeving();
        assert env.getGetDatabaseConfig("kern").getUrl().endsWith("target/db;shutdown=true;");
    }

    @Test
    void dockerEnvironment() throws MalformedURLException {
        System.setProperty("omgeving", "docker-localhost");
        Omgeving env = Omgeving.getOmgeving();
        assert env.isDockerOmgeving();
        assert Objects.equals(env.getGetDatabaseConfig("kern").getUrl(), "jdbc:postgresql://localhost/art-brp");
        assert Objects.equals(env.getSoapParameters("${applications.host}/bijhouding", "/bijhoudingService").getWsdlURL().toString(),
                "http://node4:8180/bijhouding?wsdl");
        assert Objects.equals(env.getSoapParameters("${applications.host}/synchronisatie", "/synchronisatieService").getWsdlURL().toString(),
                "http://node3:8480/synchronisatie?wsdl");
        assert Objects.equals(env.getSoapParameters("${applications.host}/afnemerindicaties", "/afnemerindicatiesService").getWsdlURL().toString(),
                "http://node1:8280/afnemerindicaties?wsdl");
        assert Objects.equals(env.getSoapParameters("${applications.host}/bevraging", "/bevragingService").getWsdlURL().toString(),
                "http://node2:8380/bevraging?wsdl");
    }
}
