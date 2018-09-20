/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import javax.inject.Inject;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Check SQL BRP kanaal.
 */
public final class CheckSqlVoiscKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "check_sql_voisc";

    /**
     * Constructor.
     */
    public CheckSqlVoiscKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-brp.xml",
            "classpath:infra-db-gbav.xml",
            "classpath:infra-db-isc.xml",
            "classpath:infra-db-sync.xml",
            "classpath:infra-db-voisc.xml",
                "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private SqlHelper sqlHelper;

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
            sqlHelper.checkSql(bericht.getBerichtReferentie(), "VOISC", bericht.getInhoud());
        }
    }
}
