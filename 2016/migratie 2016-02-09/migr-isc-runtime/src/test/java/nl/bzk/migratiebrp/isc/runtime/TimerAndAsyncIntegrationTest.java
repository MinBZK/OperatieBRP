/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class TimerAndAsyncIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() {
        LOG.info("Starting test");
        final PlatformTransactionManager transactionManager = Main.getContext().getBean(PlatformTransactionManager.class);

        // single TransactionTemplate shared amongst all methods in this instance
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        final Long processInstanceId = transactionTemplate.execute(new TransactionCallback<Long>() {

            // the code in this method executes in a transactional context
            @Override
            public Long doInTransaction(final TransactionStatus status) {
                final JbpmInvoker jbpmInvoker = Main.getContext().getBean(JbpmInvoker.class);
                return jbpmInvoker.executeInContext(new JbpmExecution<Long>() {

                    @Override
                    public Long doInContext(final JbpmContext jbpmContext) {
                        final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate("timer");

                        // Go work
                        processInstance.signal();

                        // Check
                        Assert.assertNull(processInstance.getEnd());
                        return processInstance.getId();
                    }
                });
            }
        });

        Main.getContext().close();

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            // Ignore
        }

        Main.main(null);

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            // Ignore
        }

        transactionTemplate.execute(new TransactionCallback<Void>() {

            // the code in this method executes in a transactional context
            @Override
            public Void doInTransaction(final TransactionStatus status) {
                final JbpmInvoker jbpmInvoker = Main.getContext().getBean(JbpmInvoker.class);
                jbpmInvoker.executeInContext(new JbpmExecution<Void>() {

                    @Override
                    public Void doInContext(final JbpmContext jbpmContext) {
                        final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);

                        // Check
                        Assert.assertNotNull(processInstance.getEnd());
                        return null;
                    }
                });
                return null;
            }
        });
    }

}
