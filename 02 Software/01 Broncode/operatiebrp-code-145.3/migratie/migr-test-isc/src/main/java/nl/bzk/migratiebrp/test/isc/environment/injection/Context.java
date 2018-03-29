/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm.JbpmHelperKanaal;

/**
 * Context.
 */
public final class Context {
    private final Correlator correlator;
    private final JbpmHelperKanaal jbpmHelperKanaal;

    /**
     * Constructor.
     * @param correlator correlator
     * @param jbpmHelperKanaal jbpm helper kanaal
     */
    public Context(final Correlator correlator, final JbpmHelperKanaal jbpmHelperKanaal) {
        this.correlator = correlator;
        this.jbpmHelperKanaal = jbpmHelperKanaal;
    }

    /**
     * Geef de waarde van correlator.
     * @return correlator
     */
    public Correlator getCorrelator() {
        return correlator;
    }

    /**
     * Geef de waarde van jbpmHelperKanaal.
     * @return jbpmHelperKanaal
     */
    public JbpmHelperKanaal getJbpmHelperKanaal() {
        return jbpmHelperKanaal;
    }
}
