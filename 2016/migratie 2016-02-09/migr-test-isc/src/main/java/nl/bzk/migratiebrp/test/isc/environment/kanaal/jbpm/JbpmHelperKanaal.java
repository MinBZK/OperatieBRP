/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * JBPM Helper kanaal (om de JBPM Helper operaties te 'exposen').
 */
public final class JbpmHelperKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "jbpmHelper";

    /**
     * Constructor.
     */
    protected JbpmHelperKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-isc.xml",
            "classpath:infra-jms-isc.xml",
            "classpath:infra-jbpm.xml"));
    }

    /**
     * Clean jobs.
     */
    public void cleanJobs() {
        ((Worker) getWorker()).getJbpmHelper().cleanJobs();
    }

    /**
     * Clean locks.
     */
    public void cleanLocks() {
        ((Worker) getWorker()).getJbpmHelper().cleanLocks();
    }

    /**
     * Bepaal alle processen.
     *
     * @param messageIds
     *            message ids
     * @return lijst van proces ids
     */
    public Set<Long> bepaalAlleProcessen(final List<String> messageIds) {
        return ((Worker) getWorker()).getJbpmHelper().bepaalAlleProcessen(messageIds);
    }

    /**
     * Controleer dat een proces beeindigd is.
     *
     * @param procesId
     *            proces id
     * @return true, als proces beeindigd is
     */
    public boolean controleerProcesBeeindigd(final Long procesId) {
        return ((Worker) getWorker()).getJbpmHelper().controleerProcesBeeindigd(procesId);
    }

    /**
     * Worker.
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

        /**
         * Geef de waarde van jbpm helper.
         *
         * @return jbpm helper
         */
        public JbpmHelper getJbpmHelper() {
            return jbpmHelper;
        }

    }
}
