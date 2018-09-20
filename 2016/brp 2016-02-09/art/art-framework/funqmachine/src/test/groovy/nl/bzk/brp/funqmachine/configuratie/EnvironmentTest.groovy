package nl.bzk.brp.funqmachine.configuratie

import org.junit.Test

class EnvironmentTest {

    @Test
    void localEnvironment() {
        Environment env = new Environment("localhost");
        assert env.getGetDatabaseConfig(Database.KERN).getUrl().endsWith('target/db;shutdown=true;')
        assert env.getGetDatabaseConfig(Database.PROT).getUrl().endsWith('target/db;shutdown=true;')
    }

    @Test
    void dockerEnvironment() {
        Environment env = new Environment("docker-localhost");
        assert env.isDockerOmgeving
        assert env.getGetDatabaseConfig(Database.KERN).getUrl() == 'jdbc:postgresql://localhost/art-brp'
        assert env.getSoapParameters('${applications.host}/bijhouding', '/bijhoudingService').wsdlURL.toString() == 'http://node4:8180/bijhouding?wsdl'
        assert env.getSoapParameters('${applications.host}/synchronisatie', '/synchronisatieService').wsdlURL.toString() == 'http://node3:8480/synchronisatie?wsdl'
        assert env.getSoapParameters('${applications.host}/afnemerindicaties', '/afnemerindicatiesService').wsdlURL.toString() == 'http://node1:8280/afnemerindicaties?wsdl'
        assert env.getSoapParameters('${applications.host}/bevraging', '/bevragingService').wsdlURL.toString() ==  'http://node2:8380/bevraging?wsdl'
    }
}
