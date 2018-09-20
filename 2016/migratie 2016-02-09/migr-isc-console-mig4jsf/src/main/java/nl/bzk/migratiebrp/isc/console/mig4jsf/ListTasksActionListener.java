/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.Collections;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.util.TaskComparator;
import org.hibernate.Session;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 *
 */
public final class ListTasksActionListener extends AbstractActionListener {
    private final ValueExpression targetExpression;
    private final ValueExpression includeEndedExpression;

    /**
     * Constructor.
     *
     * @param includeEndedExpression
     *            includeEnded expression
     * @param targetExpression
     *            target expression
     */
    public ListTasksActionListener(final ValueExpression includeEndedExpression, final ValueExpression targetExpression) {
        super("listTasks");
        this.includeEndedExpression = includeEndedExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final Boolean includeEnded = (Boolean) includeEndedExpression.getValue(elContext);
        final Session hibernateSession = (Session) context.getJbpmContext().getServices().getPersistenceService().getCustomSession(Session.class);

        final List<TaskInstance> taskList;
        if (includeEnded != null && includeEnded) {
            taskList = hibernateSession.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti").list();
        } else {
            taskList = hibernateSession.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti where ti.end is not null").list();
        }

        Collections.sort(taskList, new TaskComparator());
        targetExpression.setValue(elContext, Collections.unmodifiableList(taskList));
        context.selectOutcome("success");
    }
}
