/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import java.io.File;
import java.nio.charset.StandardCharsets;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("copy")
@Component
public class AfnemerindicatieCopyStrategy implements AfnemerindicatieDatabaseInteractieStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String WITH_CLAUSE = String.format("WITH DELIMITER ',' ENCODING '%s';", StandardCharsets.UTF_8);
    private static final String COPY_FORMAT = "COPY %s TO '%s' " + WITH_CLAUSE;
    private static final String COPY_FROM_FORMAT = "COPY %s FROM '%s' " + WITH_CLAUSE;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public void dumpAfnemerindicatieTabelNaarFile(final File outputFile) {
        LOGGER.info("Genereer afnemerindicatie rijen naar {}", outputFile.getAbsolutePath());
        final String copyClause = "(select * from autaut.persafnemerindicatie where levsautorisatie "
                + "IN (select id from autaut.levsautorisatie where dateinde is null))";
        final String copyAfnemerindicatieNaarFileCommand = String.format(COPY_FORMAT, copyClause,
                outputFile.getAbsolutePath());
        LOGGER.info(copyAfnemerindicatieNaarFileCommand);
        entityManager.createNativeQuery(copyAfnemerindicatieNaarFileCommand).executeUpdate();
    }

    @Override
    public void vulAfnemerindicatieTabel(final File inputFile) {
        LOGGER.info("Vul afnemerindicatie tabel");
        final String copyAfnemerindicatieNaarTabelCommand = String.format(COPY_FROM_FORMAT,
                "autaut.persafnemerindicatie", inputFile.getAbsolutePath());
        LOGGER.info(copyAfnemerindicatieNaarTabelCommand);
        entityManager.createNativeQuery(copyAfnemerindicatieNaarTabelCommand).executeUpdate();
    }

    @Override
    public void dumpHisAfnemerindicatieTabel(final File outputFile) {
        LOGGER.info("Genereer hisafnemerindicatie rijen naar {}", outputFile.getAbsolutePath());
        final String copyClause = "(select hpa.* from autaut.his_persafnemerindicatie hpa\n"
                + "inner join (select pa.id as afnemerindid \n"
                + "from autaut.persafnemerindicatie pa, autaut.levsautorisatie la where pa.levsautorisatie = la.id and la.dateinde is null) \n"
                + "as x on hpa.persafnemerindicatie = x.afnemerindid)";
        final String copyHisAfnemerindicatieNaarFileCommand = String.format(COPY_FORMAT, copyClause, outputFile.getAbsolutePath());
        LOGGER.info(copyHisAfnemerindicatieNaarFileCommand);
        entityManager.createNativeQuery(copyHisAfnemerindicatieNaarFileCommand).executeUpdate();
    }

    @Override
    public void vulHisAfnemerindicatieTabel(final File inputFile) {
        LOGGER.info("Vul his_afnemerindicatie tabel");
        final String copyHisAfnemerindicatieNaarTabelCommand =
                String.format(COPY_FROM_FORMAT, "autaut.his_persafnemerindicatie", inputFile.getAbsolutePath());
        LOGGER.info(copyHisAfnemerindicatieNaarTabelCommand);
        entityManager.createNativeQuery(copyHisAfnemerindicatieNaarTabelCommand).executeUpdate();
    }
}
