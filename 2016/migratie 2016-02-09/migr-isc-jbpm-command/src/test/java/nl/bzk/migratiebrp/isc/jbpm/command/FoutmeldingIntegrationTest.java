/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmResumeCommand;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmStartTaakCommand;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSuspendCommand;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmTaakCommand;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;

public class FoutmeldingIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() throws CommandException {
        // Start foutmelding proces
        final Long processInstanceId = jbpmInvoker.executeInContext(new JbpmExecution<Long>() {
            @Override
            public Long doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.newProcessInstance("foutafhandeling");
                processInstance.getContextInstance().setVariable(FoutafhandelingConstants.FOUT, "fout");
                processInstance.getContextInstance().setVariable(FoutafhandelingConstants.FOUTMELDING, "foutmelding");
                processInstance.getContextInstance().setVariable(FoutafhandelingConstants.INDICATIE_BEHEERDER, true);
                final FoutafhandelingPaden paden = new FoutafhandelingPaden();
                paden.put("end", "end", false, false, false);
                processInstance.getContextInstance().setVariable(FoutafhandelingConstants.PADEN, paden);
                Assert.assertFalse(processInstance.isSuspended());

                return processInstance.getId();
            }
        });

        // Suspend
        commandClient.executeCommand(new JbpmSuspendCommand(processInstanceId));
        jbpmInvoker.executeInContext(new JbpmExecution<Void>() {
            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                Assert.assertTrue(processInstance.isSuspended());
                return null;
            }
        });

        // Resume
        commandClient.executeCommand(new JbpmResumeCommand(processInstanceId));
        jbpmInvoker.executeInContext(new JbpmExecution<Void>() {
            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                Assert.assertFalse(processInstance.isSuspended());
                return null;
            }
        });

        // Signal
        final Long taskId = jbpmInvoker.executeInContext(new JbpmExecution<Long>() {
            @Override
            public Long doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);

                LOG.info("Node voor signal: " + processInstance.getRootToken().getNode().getName());
                processInstance.signal();
                LOG.info("Node na signal: " + processInstance.getRootToken().getNode().getName());

                final Collection<?> tasks = processInstance.getTaskMgmtInstance().getTaskInstances();
                Assert.assertEquals(1, tasks.size());
                final TaskInstance taskInstance = (TaskInstance) tasks.iterator().next();
                Assert.assertNull(taskInstance.getActorId());

                return taskInstance.getId();
            }
        });

        // Start taak
        commandClient.executeCommand(new JbpmStartTaakCommand(taskId, "admin"));
        jbpmInvoker.executeInContext(new JbpmExecution<Void>() {
            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                final Collection<?> tasks = processInstance.getTaskMgmtInstance().getTaskInstances();
                Assert.assertEquals(1, tasks.size());
                final TaskInstance taskInstance = (TaskInstance) tasks.iterator().next();
                Assert.assertEquals("admin", taskInstance.getActorId());
                Assert.assertNotNull(taskInstance.getStart());

                return null;
            }
        });

        // Verwerk taak
        final Map<String, Object> taakVariabelenGewijzigd = new HashMap<>();
        taakVariabelenGewijzigd.put("restart", "end");
        commandClient.executeCommand(new JbpmTaakCommand(taskId, null, taakVariabelenGewijzigd, "no comment", "ok"));
        jbpmInvoker.executeInContext(new JbpmExecution<Void>() {
            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                Assert.assertNotNull(processInstance.getEnd());
                return null;
            }
        });
    }
}
