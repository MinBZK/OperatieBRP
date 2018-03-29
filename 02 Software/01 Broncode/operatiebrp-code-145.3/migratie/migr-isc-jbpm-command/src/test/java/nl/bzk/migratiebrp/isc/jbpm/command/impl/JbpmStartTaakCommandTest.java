/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class JbpmStartTaakCommandTest {

    @Test
    public void test() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Task instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        final String actor = "Beheerder";
        // Execute
        final JbpmStartTaakCommand subject = new JbpmStartTaakCommand(1l, actor);
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(task).start(actor);
    }

    @Test
    public void testLegeActor() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmStartTaakCommand subject = new JbpmStartTaakCommand(1l, "");
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(task).start();
    }

    @Test
    public void testGeenActor() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmStartTaakCommand subject = new JbpmStartTaakCommand(1l, null);
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(task).start();
    }
}
