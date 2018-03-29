/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;
import nl.bzk.brp.dockertest.service.gbasync.PersoonUitExcelHelper;

/**
 * Dockercomponent voor de Selectie database.
 */
@DockerInfo(
        image = "brp/database-kern",
        logischeNaam = DockerNaam.SELECTIEBLOB_DATABASE,
        internePoorten = {Poorten.DB_POORT_5432},
        bootLevel = 0
)
final class SelectieBlobDatabaseDocker extends AbstractDatabaseDocker implements DatabaseDocker.ExcelSupport {

    private final PersoonUitExcelHelper persoonUitExcelHelper = new PersoonUitExcelHelper(this);

    @Override
    protected Map<String, String> getEnvironmentVoorDependency() {
        final Map<String, String> map = super.getEnvironment();
        map.put("SELECTIEDB_ENV_HOSTNAME", getOmgeving().getDockerHostname());
        map.put("SELECTIEDB_ENV_PORT", String.valueOf(getPoortMap().get(Poorten.DB_POORT_5432)));
        map.put("SELECTIEDB_ENV_DBNAME", "brp");
        map.put("SELECTIEDB_ENV_DBUSER", "brp");
        map.put("SELECTIEDB_ENV_DBPASS", "brp");
        return map;
    }

    @Override
    protected void afterStop() {
        super.afterStop();
        persoonUitExcelHelper.close();
    }

    @Override
    protected Integer geefPoortVoorkeur(int internepoort) {
        if (internepoort == Poorten.DB_POORT_5432) {
            return Poorten.DB_POORT_5432 + 1;
        }
        return super.geefPoortVoorkeur(internepoort);
    }

    @Override
    public PersoonUitExcelHelper getPersoonUitExcelHelper() {
        return persoonUitExcelHelper;
    }
}
