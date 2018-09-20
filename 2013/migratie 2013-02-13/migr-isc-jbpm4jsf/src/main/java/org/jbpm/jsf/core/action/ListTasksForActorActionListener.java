/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.JbpmContext;
import org.jbpm.identity.Group;
import org.jbpm.identity.Membership;
import org.jbpm.identity.User;
import org.jbpm.identity.hibernate.IdentitySession;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 *
 */
public final class ListTasksForActorActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;

    public ListTasksForActorActionListener(
            final ValueExpression actorExpression,
            final ValueExpression targetExpression) {
        this.targetExpression = targetExpression;
    }

    @Override
    public String getName() {
        return "listTasksForActorActionListener";
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            final JbpmContext jbpmContext = context.getJbpmContext();

            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();

            final IdentitySession identitySession = new IdentitySession(jbpmContext.getSession());
            final User user = identitySession.getUserByName(jbpmContext.getActorId());

            final List<String> actorIds = new ArrayList<String>();
            actorIds.add(user.getName());

            final Set<Membership> memberships = user.getMemberships();
            for (final Membership membership : memberships) {
                addGroup(actorIds, membership.getGroup());
            }

            final Set<Long> taskIds = new HashSet<Long>();

            final List<TaskInstance> tasks = jbpmContext.getTaskList(user.getName());
            for (final TaskInstance task : tasks) {
                taskIds.add(task.getId());
            }

            final List<TaskInstance> groupTasks = jbpmContext.getGroupTaskList(actorIds);
            for (final TaskInstance groupTask : groupTasks) {
                if (!taskIds.contains(groupTask.getId())) {
                    tasks.add(groupTask);
                    taskIds.add(groupTask.getId());
                }
            }

            final List<TaskInstance> taskList = Collections.unmodifiableList(tasks);
            targetExpression.setValue(elContext, taskList);
            context.selectOutcome("success");
        } catch (final Exception ex) {
            context.setError("Error loading task list", ex);
            return;
        }
    }

    @SuppressWarnings("unchecked")
    private void addGroup(final List<String> actorIds, final Group group) {
        if (group == null) {
            return;
        }

        actorIds.add(group.getName());
        if (group.getChildren() != null) {
            for (final Group child : (Set<Group>) group.getChildren()) {
                addGroup(actorIds, child);
            }
        }

    }
}
