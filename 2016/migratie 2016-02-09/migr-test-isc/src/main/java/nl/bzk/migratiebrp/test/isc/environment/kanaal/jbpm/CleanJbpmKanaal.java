/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import javax.inject.Inject;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * JBPM Opschonen.
 */
public class CleanJbpmKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "clean_jbpm";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public CleanJbpmKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-isc.xml",
            "classpath:infra-jms-isc.xml",
            "classpath:infra-jbpm.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private JbpmHelper jbpmHelper;

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
        public void voorTestcase(final TestCasusContext testCasus) {
            LOG.info("Cleaning jobs");
            jbpmHelper.cleanJobs();

            LOG.info("Cleaning locks");
            jbpmHelper.cleanLocks();
        }
    }
}
