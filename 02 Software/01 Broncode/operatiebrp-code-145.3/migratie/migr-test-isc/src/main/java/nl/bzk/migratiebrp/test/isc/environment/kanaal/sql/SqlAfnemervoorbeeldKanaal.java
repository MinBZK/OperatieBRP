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
 * SQL BRP kanaal.
 */
public final class SqlAfnemervoorbeeldKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "sql_afnemervoorbeeld";

    /**
     * Constructor.
     */
    public SqlAfnemervoorbeeldKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-afnemervoorbeeld.xml", "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        private SqlHelper sqlHelper;

        @Inject
        @Named("afnemervoorbeeldDataSource")
        private DataSource afnemervoorbeeldDataSource;


        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            sqlHelper.uitvoerenSql(afnemervoorbeeldDataSource, bericht.getInhoud(), false);
        }
    }
}
