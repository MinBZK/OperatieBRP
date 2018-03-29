/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class TimerAndAsyncIT extends AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() {
        LOG.info("Starting test");
        final PlatformTransactionManager transactionManager = getSubject().getContext().getBean(PlatformTransactionManager.class);

        // single TransactionTemplate shared amongst all methods in this instance
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        final Long processInstanceId = transactionTemplate.execute(status -> {
            final JbpmInvoker jbpmInvoker = getSubject().getContext().getBean(JbpmInvoker.class);
            return jbpmInvoker.executeInContext(jbpmContext -> {
                final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate("timer");

                // Go work
                processInstance.signal();

                // Check
                Assert.assertNull(processInstance.getEnd());
                return processInstance.getId();
            });
        });

        getSubject().stop();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (final InterruptedException e) {
            // Ignore
        }

        getSubject().start();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (final InterruptedException e) {
            // Ignore
        }

        transactionTemplate.execute(status -> {
            final JbpmInvoker jbpmInvoker = getSubject().getContext().getBean(JbpmInvoker.class);
            jbpmInvoker.executeInContext(jbpmContext -> {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);

                // Check
                Assert.assertNotNull(processInstance.getEnd());
                return null;
            });
            return null;
        });
    }

}
