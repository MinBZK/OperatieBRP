/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
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

public class FoutmeldingIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() {
        LOG.info("Starting test");
        final PlatformTransactionManager transactionManager = Main.getContext().getBean(PlatformTransactionManager.class);

        // single TransactionTemplate shared amongst all methods in this instance
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallback<Void>() {

            // the code in this method executes in a transactional context
            @Override
            public Void doInTransaction(final TransactionStatus status) {
                final JbpmService jbpmService = Main.getContext().getBean(JbpmService.class);
                final long processInstanceId = jbpmService.startFoutmeldingProces(null, null, null, null, "test.fout", "test foutmelding", false, false);

                final JbpmInvoker jbpmInvoker = Main.getContext().getBean(JbpmInvoker.class);
                jbpmInvoker.executeInContext(new JbpmExecution<Void>() {

                    @Override
                    public Void doInContext(final JbpmContext jbpmContext) {
                        final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                        Assert.assertNotNull(processInstance.getEnd());
                        return null;
                    }
                });
                return null;
            }
        });
    }

}
