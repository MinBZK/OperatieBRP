/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * Dockercomponent voor de Protocolleringdatabase.
 */
@DockerInfo(
        image = "brp/database-prot",
        logischeNaam = DockerNaam.PROTOCOLLERINGDB,
        internePoorten = {Poorten.DB_POORT_5432},
        bootLevel = 0)
final class ProtocolleringDatabaseDocker extends AbstractDatabaseDocker {

    @Override
    protected Map<String, String> getEnvironmentVoorDependency() {
        final Map<String, String> map = super.getEnvironment();
        map.put("PROTOCOLLERINGDB_ENV_HOSTNAME", getOmgeving().getDockerHostname());
        map.put("PROTOCOLLERINGDB_ENV_PORT", String.valueOf(getPoortMap().get(Poorten.DB_POORT_5432)));
        map.put("PROTOCOLLERINGDB_ENV_DBNAME", "brp");
        map.put("PROTOCOLLERINGDB_ENV_DBUSER", "brp");
        map.put("PROTOCOLLERINGDB_ENV_DBPASS", "brp");
        return map;
    }
}
