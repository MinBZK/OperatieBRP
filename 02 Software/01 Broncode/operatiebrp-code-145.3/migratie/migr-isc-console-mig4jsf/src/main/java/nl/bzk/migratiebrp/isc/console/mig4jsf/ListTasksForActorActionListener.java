/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.console.mig4jsf.util.TaskComparator;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.identity.Group;
import org.jbpm.identity.Membership;
import org.jbpm.identity.User;
import org.jbpm.identity.hibernate.IdentitySession;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.config.ConfigurationLocator;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * List tasks voor actor.
 */
public final class ListTasksForActorActionListener extends AbstractActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     * @param targetExpression target expression
     */
    ListTasksForActorActionListener(final ValueExpression targetExpression) {
        super("listTasksForActor");
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final JbpmContext jbpmContext = context.getJbpmContext();
        final ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        final String actor = jbpmContext.getActorId();
        final List<String> roles = getRoles(jbpmContext);

        final Set<Long> taskIds = new HashSet<>();
        @SuppressWarnings("unchecked")
        final List<TaskInstance> tasks = jbpmContext.getTaskList(actor);
        for (final TaskInstance task : tasks) {
            taskIds.add(task.getId());
        }

        @SuppressWarnings("unchecked")
        final List<TaskInstance> groupTasks = jbpmContext.getGroupTaskList(roles);
        for (final TaskInstance groupTask : groupTasks) {
            if (!taskIds.contains(groupTask.getId())) {
                tasks.add(groupTask);
                taskIds.add(groupTask.getId());
            }
        }

        final TaskComparator taakVergelijker = new TaskComparator();
        taakVergelijker.setCurrentActorId(actor);
        taakVergelijker.setOudsteEerst(true);
        tasks.sort(taakVergelijker);
        final List<TaskInstance> taskList = Collections.unmodifiableList(tasks);
        targetExpression.setValue(elContext, taskList);
        context.selectOutcome("success");
    }

    private List<String> getRoles(final JbpmContext jbpmContext) {
        final List<String> result = new ArrayList<>();

        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        final Principal userPrincipal = externalContext.getUserPrincipal();
        if (userPrincipal != null && ConfigurationLocator.getInstance().useJsfActorId()) {

            if (isTomcatPrincipal(userPrincipal)) {
                result.addAll(getTomcatRoles(userPrincipal));
            } else {
                // JBOSS VARIANT
                final Subject subject;
                try {
                    final InitialContext ic = new InitialContext();
                    subject = (Subject) ic.lookup("java:comp/env/security/subject");
                } catch (final NamingException e) {
                    throw new IllegalArgumentException(e);
                }
                result.addAll(addPrincipalRoles(subject));
            }
        } else {
            // Use JBPM identity session
            final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            final IdentitySession identitySession = new IdentitySession(hibernateSession);
            final User user = identitySession.getUserByName(jbpmContext.getActorId());
            if (user != null) {
                @SuppressWarnings("unchecked")
                final Set<Membership> memberships = user.getMemberships();
                for (final Membership membership : memberships) {
                    addGroup(result, membership.getGroup());
                }
            }
        }

        return result;
    }

    private List<String> addPrincipalRoles(Subject subject) {
        List<String> result = new LinkedList<>();
        for (final java.security.acl.Group principal : subject.getPrincipals(java.security.acl.Group.class)) {
            final Enumeration<? extends Principal> rolesEnumeration = principal.members();
            while (rolesEnumeration.hasMoreElements()) {
                final Principal role = rolesEnumeration.nextElement();
                result.add(role.getName());
            }
        }
        return result;
    }

    private boolean isTomcatPrincipal(final Principal userPrincipal) {
        Class<?> clazz;
        try {
            clazz = Class.forName("org.apache.catalina.User");
        } catch (final ClassNotFoundException e) {
            LOGGER.debug(e.getMessage(), e);
            return false;
        }

        return clazz.isAssignableFrom(userPrincipal.getClass());
    }

    private List<String> getTomcatRoles(final Principal userPrincipal) {
        final List<String> result = new ArrayList<>();

        try {
            final Method getRolesMethod = userPrincipal.getClass().getMethod("getRoles");
            final Iterator<?> rolesIterator = (Iterator<?>) getRolesMethod.invoke(userPrincipal);
            while (rolesIterator != null && rolesIterator.hasNext()) {
                final Object role = rolesIterator.next();
                final Method getRolenameMethod = role.getClass().getMethod("getRolename");
                final String rolename = (String) getRolenameMethod.invoke(role);
                result.add(rolename);
            }
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kon Tomcat rollen niet bepalen", e);
        }

        return result;
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
