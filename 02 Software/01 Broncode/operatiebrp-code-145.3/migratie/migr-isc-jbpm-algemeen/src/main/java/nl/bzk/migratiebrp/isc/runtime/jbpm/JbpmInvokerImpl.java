/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JBPM invoker.
 */
@Service
public final class JbpmInvokerImpl implements JbpmInvoker {

    private JbpmConfiguration jbpmConfiguration;

    /**
     * Zet de JBPM configuratie.
     * @param jbpmConfiguration De te zetten configuratie.
     */
    public void setJbpmConfiguration(final JbpmConfiguration jbpmConfiguration) {
        this.jbpmConfiguration = jbpmConfiguration;
    }

    @Override
    @Transactional(transactionManager = "iscTransactionManager", propagation = Propagation.REQUIRED)
    public <T> T executeInContext(final JbpmExecution<T> execution) {
        final JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            return execution.doInContext(jbpmContext);
        } finally {
            jbpmContext.close();
        }
    }
}
