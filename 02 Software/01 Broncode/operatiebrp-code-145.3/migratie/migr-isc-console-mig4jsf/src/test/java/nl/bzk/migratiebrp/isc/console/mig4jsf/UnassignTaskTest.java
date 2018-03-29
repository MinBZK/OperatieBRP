/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.ResultSet;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class UnassignTaskTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final TaskInstance taskInstance = Mockito.mock(TaskInstance.class);
        Mockito.when(taskInstance.getActorId()).thenReturn("jaap");
        Mockito.when(taskInstance.getId()).thenReturn(4567L);
        addTagAttribute("task", taskInstance);

        setupDatabase(
                "/sql/mig-drop.sql",
                "/sql/jbpm-drop.sql",
                "/sql/jbpm-create.sql",
                "/sql/mig-create.sql",
                "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-jbpm_taskinstance.sql");

        final String SQL = "SELECT actorId_, start_ FROM jbpm_taskinstance WHERE id_ = 4567";

        final ResultSet rsBefore = dataSource.getConnection().createStatement().executeQuery(SQL);
        Assert.assertTrue(rsBefore.next());
        Assert.assertEquals("jaap", rsBefore.getString(1));
        Assert.assertNotNull(rsBefore.getTimestamp(2));

        // Execute
        final JbpmActionListener subject = initializeSubject(UnassignTaskHandler.class);

        Assert.assertEquals("unassignTask", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ResultSet rsAfter = dataSource.getConnection().createStatement().executeQuery(SQL);
        Assert.assertTrue(rsAfter.next());
        Assert.assertEquals(null, rsAfter.getString(1));
        Assert.assertEquals(null, rsAfter.getTimestamp(2));
    }
}
