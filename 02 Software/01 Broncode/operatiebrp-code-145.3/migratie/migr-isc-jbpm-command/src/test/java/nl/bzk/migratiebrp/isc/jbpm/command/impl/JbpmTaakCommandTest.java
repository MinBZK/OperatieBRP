/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class JbpmTaakCommandTest {

    public static final String COMMENTAAR = "commentaar";
    public static final String TRANSITIE = "transitie";
    public static final String TAAK_VARIABELE_VERWIJDERD = "taakVariabeleVerwijderd";
    public static final String TAAK_VARIABELE_GEWIJZIGD = "taakVariabeleGewijzigd";

    @Test
    public void testVolledig() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Task instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);
        Mockito.doNothing().when(task).deleteVariable(TAAK_VARIABELE_VERWIJDERD);

        Set<String> taakVariabeleVerwijderd = Collections.singleton(TAAK_VARIABELE_VERWIJDERD);
        Map<String, Serializable> taakVariableGewijzigd = Collections.singletonMap(TAAK_VARIABELE_GEWIJZIGD, "2");

        // Execute
        final JbpmTaakCommand subject = new JbpmTaakCommand(1l, taakVariabeleVerwijderd, taakVariableGewijzigd, COMMENTAAR, TRANSITIE);
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(task).addComment(COMMENTAAR);
        Mockito.verify(task).end(TRANSITIE);
    }

    @Test
    public void testLegeTransitieMetCommentaar() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmTaakCommand subject = new JbpmTaakCommand(1l, Collections.emptySet(), Collections.emptyMap(), COMMENTAAR, "");
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(task).addComment(COMMENTAAR);
    }

    @Test
    public void testLegeTransitieLeegCommentaar() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmTaakCommand subject = new JbpmTaakCommand(1l, Collections.emptySet(), Collections.emptyMap(), "", "");
        subject.doInContext(jbpmContext);
    }

    @Test
    public void testLegeTransitieMetNullCommentaar() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmTaakCommand subject = new JbpmTaakCommand(1l, Collections.emptySet(), Collections.emptyMap(), null, "");
        subject.doInContext(jbpmContext);

    }

    @Test
    public void testNullTransitieMetNullCommentaar() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final TaskInstance task = Mockito.mock(TaskInstance.class);
        Mockito.when(jbpmContext.getTaskInstanceForUpdate(Matchers.anyLong())).thenReturn(task);

        // Execute
        final JbpmTaakCommand subject = new JbpmTaakCommand(1l, Collections.emptySet(), Collections.emptyMap(), null, null);
        subject.doInContext(jbpmContext);

    }

}
