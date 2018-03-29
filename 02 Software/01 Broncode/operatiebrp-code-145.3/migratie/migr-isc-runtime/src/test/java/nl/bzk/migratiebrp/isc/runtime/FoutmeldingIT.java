/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class FoutmeldingIT extends AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() {
        LOG.info("Starting test");
        final PlatformTransactionManager transactionManager = getSubject().getContext().getBean(PlatformTransactionManager.class);

        // single TransactionTemplate shared amongst all methods in this instance
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            final JbpmService jbpmService = getSubject().getContext().getBean(JbpmService.class);
            final long processInstanceId = jbpmService.startFoutmeldingProces(null, null, null, null, "test.fout", "test foutmelding", false, false);

            final JbpmInvoker jbpmInvoker = getSubject().getContext().getBean(JbpmInvoker.class);
            jbpmInvoker.executeInContext(jbpmContext -> {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                Assert.assertNotNull(processInstance.getEnd());
                return null;
            });
            return null;
        });
    }

}
