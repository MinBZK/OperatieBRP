/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.util.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.isc.telling.util.DBUnitUtil;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.springframework.stereotype.Component;

/**
 * DBUnit
 */
@Component
public class DBUnitTellingenUtil extends DBUnitUtil {

    private static final String SCHEMA = "public";

    private static final TruncateTellingenTableTellingen TRUNCATE_TABLE_OPERATION = new TruncateTellingenTableTellingen();

    private static final String[] SOA_TABELLEN = {"mig_lock_anummer",
                                                  "mig_bericht",
                                                  "mig_configuratie",
                                                  "mig_correlatie",
                                                  "mig_extractie_proces",
                                                  "mig_runtime",
                                                  "mig_fout",
                                                  "mig_lock",
                                                  "mig_proces_relatie",
                                                  "mig_telling_bericht",
                                                  "mig_telling_proces", };

    private static final String[] JBPM_TABELLEN = {"jbpm_processdefinition", "jbpm_processinstance", };

    @Inject
    private DataSource dataSource;

    private List<String> soaTabellen;

    private List<String> jbpmTabellen;

    @PostConstruct
    public void init() throws SQLException, DatabaseUnitException {
        initTabellen();
    }

    @Override
    protected Connection createSqlConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Leegt de database en reset de stamgegevens.
     * 
     * @param testClass
     *            testclass waaruit deze methode wordt aangeroepen
     * @param log
     *            logger waarin gelogd kan worden
     */
    public final void resetDB(final Class<?> testClass, final Logger log) {
        resetDB(testClass, log, true);
    }

    /**
     * Leegt de database en indien gewenst wordt de stamgegevens ook gereset.
     * 
     * @param testClass
     *            testclass waaruit deze methode wordt aangeroepen
     * @param log
     *            logger waarin gelogd kan worden
     * @param resetStamgegevens
     *            true als de stamgegevens gereset moet worden
     */
    public final void resetDB(final Class<?> testClass, final Logger log, final boolean resetStamgegevens) {
        log.info("Preparing database");
        try {
            if (resetStamgegevens) {
                log.info("Truncating all tables");
                truncateTables();
                log.info("Inserting SOA data");
                insert(testClass, new String[] {"/sql/data/soa_data.xml", });
                log.info("Inserting JBPM data");
                insert(testClass, new String[] {"/sql/data/jbpm_data.xml", });
            } else {
                log.info("Truncating tables");
                truncateTables();
            }
        } catch (final
            DatabaseUnitException
            | SQLException e)
        {
            throw new RuntimeException("Kan database niet intialiseren.", e);
        }
    }

    /**
     * Truncate alle tabellen met uitzondering van de statische stamtabellen.
     * 
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    @Override
    public void truncateTables() throws SQLException, DatabaseUnitException {
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), soaTabellen);
    }

    private void initTabellen() throws DataSetException {
        soaTabellen = new ArrayList<>();
        jbpmTabellen = new ArrayList<>();

        for (final String tableName : SOA_TABELLEN) {
            final String qualifiedTableName = SCHEMA + '.' + tableName;
            soaTabellen.add(qualifiedTableName);
        }

        for (final String tableName : JBPM_TABELLEN) {
            final String qualifiedTableName = SCHEMA + '.' + tableName;
            jbpmTabellen.add(qualifiedTableName);
        }

    }
}
