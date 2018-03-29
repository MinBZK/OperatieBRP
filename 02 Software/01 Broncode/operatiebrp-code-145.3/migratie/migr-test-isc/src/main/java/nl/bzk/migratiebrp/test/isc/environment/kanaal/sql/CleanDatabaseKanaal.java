/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Clean database kanaal.
 */
public final class CleanDatabaseKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "cleandb";

    private static final String DATABASE_ISC = "ISC";
    private static final String DATABASE_BRP = "BRP";

    /**
     * Constructor.
     */
    public CleanDatabaseKanaal() {
        super(new Worker(), new Configuration(
                "classpath:configuratie.xml",
                "classpath:infra-db-brp.xml",
                "classpath:infra-db-isc.xml",
                "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        private SqlHelper sqlHelper;

        @Inject
        @Named("iscDataSource")
        private DataSource iscDataSource;

        @Inject
        @Named("brpDataSource")
        private DataSource brpDataSource;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            switch (bericht.getInhoud().trim().toUpperCase()) {
                case DATABASE_BRP:
                    sqlHelper.opschonenDatabase(brpDataSource, "/sql/cleandb_brp.sql");
                    break;
                case DATABASE_ISC:
                    sqlHelper.opschonenDatabase(iscDataSource, "/sql/cleandb_isc.sql");
                    break;
                default:
                    throw new IllegalArgumentException("Optie '" + bericht.getInhoud().trim().toUpperCase() + "' voor cleandb niet ondersteund.");
            }
        }
    }
}
