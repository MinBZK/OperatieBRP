/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm;

import org.jbpm.JbpmContext;

/**
 * Entry point naar JBPM.
 */
public interface JbpmInvoker {

    /**
     * Voer arbitaire code uit binnen een Jbpm context.
     * @param execution uit te voeren code
     * @param <T> return type
     * @return result
     */
    <T> T executeInContext(final JbpmExecution<T> execution);

    /**
     * Code om uit te voeren binnen een Jbpm context.
     * @param <T> return type
     */
    interface JbpmExecution<T> {

        /**
         * Uit te voeren code.
         * @param jbpmContext jbpm context
         * @return resultaat
         */
        T doInContext(JbpmContext jbpmContext);
    }
}
