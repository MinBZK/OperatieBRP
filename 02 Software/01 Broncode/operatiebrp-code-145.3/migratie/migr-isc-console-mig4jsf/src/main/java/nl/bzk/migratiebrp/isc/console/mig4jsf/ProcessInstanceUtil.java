/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * Process instance helper.
 */
public final class ProcessInstanceUtil {

    private ProcessInstanceUtil() {
        // Niet instantieerbaar
    }

    /**
     * Geef de 'root' process instance voor een process instance.
     * @param processInstance process instance
     * @return 'root' process instance
     */
    public static ProcessInstance getRootProcessInstance(final ProcessInstance processInstance) {
        if (processInstance == null) {
            return null;
        }

        ProcessInstance result = processInstance;
        while (result.getSuperProcessToken() != null) {
            Token superToken = result.getSuperProcessToken();
            while (superToken.getParent() != null) {
                superToken = superToken.getParent();
            }
            result = superToken.getProcessInstance();
        }
        return result;
    }

    /**
     * Geef alle process instances (inclusief de gegeven instance, sub instances en gerelateerde instances) voor een
     * proces instance.
     * @param context context
     * @param processInstance proces instance
     * @return lijst van proces instances
     */
    public static List<ProcessInstance> getProcessInstances(final JbpmContext context, final ProcessInstance processInstance) {
        final Session hibernateSession = (Session) context.getServices().getPersistenceService().getCustomSession(Session.class);

        return getProcessInstances(context.getGraphSession(), hibernateSession, processInstance);
    }

    /**
     * Geef alle process instances (inclusief de gegeven instance, sub instances en gerelateerde instances) voor een
     * proces instance.
     * @param graphSession GraphSession
     * @param session Session
     * @param processInstance proces instance
     * @return lijst van proces instances
     */
    public static List<ProcessInstance> getProcessInstances(final GraphSession graphSession, final Session session, final ProcessInstance processInstance) {
        final Set<Long> instanceIds = new HashSet<>();

        // find related sub processes from this root process
        addAllSubProcessInstances(graphSession, session, processInstance.getId(), instanceIds);

        // find related processes from migr_proces_relatie
        addAllRelatedProcessInstances(graphSession, session, instanceIds);

        // wrap into ProcessInstance
        return wrapProcessInstance(graphSession, instanceIds);
    }

    private static void addAllSubProcessInstances(
            final GraphSession graphSession,
            final Session session,
            final Long processInstanceId,
            final Set<Long> instanceIds) {
        // alleen toevoegen en sub verwerken als dat al niet gedaan is
        if (instanceIds.add(processInstanceId)) {
            final ProcessInstance processInstance = graphSession.getProcessInstance(processInstanceId);

            @SuppressWarnings("unchecked") final List<Token> tokens = processInstance.findAllTokens();
            for (final Token token : tokens) {
                final List<Long> subProcessInstanceIds = findSubProcessInstanceIds(session, token.getId());
                for (final Long subProcessInstanceId : subProcessInstanceIds) {
                    // recursive
                    addAllSubProcessInstances(graphSession, session, subProcessInstanceId, instanceIds);
                }
            }
        }
    }

    private static void addAllRelatedProcessInstances(final GraphSession graphSession, final Session session, final Set<Long> instanceIds) {
        final List<Long> instancesToLoop = new ArrayList<>();
        instancesToLoop.addAll(instanceIds);
        for (final Long instanceId : instancesToLoop) {
            final List<Long> relatedInstanceIds = findRelationProcessInstanceIds(session, instanceId);
            for (final Long relatedInstanceId : relatedInstanceIds) {
                final ProcessInstance relatedProcesInstance = getRootProcessInstance(graphSession.getProcessInstance(relatedInstanceId));
                if (relatedProcesInstance != null) {
                    addAllSubProcessInstances(graphSession, session, relatedProcesInstance.getId(), instanceIds);
                }
            }
        }
    }

    private static List<ProcessInstance> wrapProcessInstance(final GraphSession graphSession, final Set<Long> instanceIds) {
        final List<ProcessInstance> result = new ArrayList<>();
        for (final Long instanceId : instanceIds) {
            result.add(graphSession.getProcessInstance(instanceId));
        }
        return result;
    }

    /**
     * Find process instance id's that have the given token (id) and super process token.
     * @return list of process instance id's (empty list if nothing found)
     */
    private static List<Long> findSubProcessInstanceIds(final Session session, final Long tokenId) {
        try {

            final List<Long> result = new ArrayList<>();

            final Work gerelateeerdeProcesInstantiesWork = new Work() {

                @Override
                public void execute(final Connection connection) throws SQLException {
                    final String sql = "select id_ from jbpm_processinstance where superprocesstoken_ = ?";
                    try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setLong(JdbcConstants.COLUMN_1, tokenId);
                        verwerkResultaat(statement);
                    }
                }

                private void verwerkResultaat(final PreparedStatement statement) throws SQLException {
                    try (final ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            result.add(resultSet.getLong(JdbcConstants.COLUMN_1));
                        }
                    }
                }
            };

            session.doWork(gerelateeerdeProcesInstantiesWork);
            return result;

        } catch (final HibernateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Find process instance id's that have the given process instance id as proces relation.
     * @param processInstanceId process instance id
     * @return list of related process instance id's (empty list if nothing found)
     */
    private static List<Long> findRelationProcessInstanceIds(final Session session, final Long processInstanceId) {
        try {
            final String sql =
                    "select process_instance_id_een, process_instance_id_twee from mig_proces_relatie where "
                            + "process_instance_id_een = ? OR process_instance_id_twee = ?";

            final List<Long> result = new ArrayList<>();

            final Work gerelateeerdeProcesInstantiesWork = new GerelateerdeProcesInstantiesWork(sql, processInstanceId, result);
            session.doWork(gerelateeerdeProcesInstantiesWork);
            return result;
        } catch (final HibernateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Work implementatie voor gerelateerde proces instanties.
     */
    private static class GerelateerdeProcesInstantiesWork implements Work {
        private final String sql;
        private final Long processInstanceId;
        private final List<Long> result;

        /**
         * Constructor.
         * @param sql sql
         * @param processInstanceId process instance id
         * @param result resultaat
         */
        public GerelateerdeProcesInstantiesWork(final String sql, final Long processInstanceId, final List<Long> result) {
            this.sql = sql;
            this.processInstanceId = processInstanceId;
            this.result = result;
        }

        @Override
        public void execute(final Connection connection) throws SQLException {
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(JdbcConstants.COLUMN_1, processInstanceId);
                statement.setLong(JdbcConstants.COLUMN_2, processInstanceId);
                verwerkResultaat(statement);
            }
        }

        private void verwerkResultaat(final PreparedStatement statement) throws SQLException {
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final Long relationProcessInstanceId1 = resultSet.getObject(1) != null ? resultSet.getLong(1) : null;
                    final Long relationProcessInstanceId2 = resultSet.getObject(2) != null ? resultSet.getLong(2) : null;
                    if (relationProcessInstanceId1 != null && !relationProcessInstanceId1.equals(processInstanceId)) {
                        result.add(relationProcessInstanceId1);
                    } else if (relationProcessInstanceId2 != null && !relationProcessInstanceId2.equals(processInstanceId)) {
                        result.add(relationProcessInstanceId2);
                    }
                }
            }
        }
    }
}
