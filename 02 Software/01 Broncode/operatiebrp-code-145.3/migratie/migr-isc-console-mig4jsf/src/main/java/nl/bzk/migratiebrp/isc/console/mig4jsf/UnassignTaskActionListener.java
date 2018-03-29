/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.console.mig4jsf.JbpmSqlHelper.Sql;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Unassign de meegeven taak voor de meegeven actor.
 */
public final class UnassignTaskActionListener extends AbstractActionListener {

    private static final String ERROR_MESSAGE = "Fout opgetreden bij het ongedaan maken van de toewijzing van de taak";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ValueExpression taskInstanceExpression;

    /**
     * Constructor.
     * @param taskInstanceExpression task instance expression
     */
    public UnassignTaskActionListener(final ValueExpression taskInstanceExpression) {
        super("unassignTask");
        this.taskInstanceExpression = taskInstanceExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final JbpmContext jbpmContext = context.getJbpmContext();
        final ELContext elContext = facesContext.getELContext();
        final Object taskInstanceValue = taskInstanceExpression.getValue(elContext);

        boolean isFoutOpgetreden = false;

        if (taskInstanceValue == null) {
            context.setError(ERROR_MESSAGE, "De taak instantie heeft geen inhoud.");
            isFoutOpgetreden = true;
        }

        if (!isFoutOpgetreden && !(taskInstanceValue instanceof TaskInstance)) {
            context.setError(ERROR_MESSAGE, "Meegegeven taak instantie is geen geldige taak instantie.");
            isFoutOpgetreden = true;
        }

        if (!isFoutOpgetreden && unassignTask(jbpmContext, (TaskInstance) taskInstanceValue)) {
            context.addSuccessMessage("Toewijzing taak is ongedaan gemaakt.");
            final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            hibernateSession.flush();
            context.selectOutcome("success");
        } else {
            context.selectOutcome("failure");
        }
    }

    /**
     * Unassigned de meegegeven actor van de meegegeven taak en maakt de taak weer beschikbaar voor andere beheerders.
     * @param jbpmContext De Jbpm context.
     * @param taskInstance De instantie van de taak die geunassigned dient te worden.
     * @return true indien de taak geunassigned kon worden, false in alle andere gevallen.
     */
    private static Boolean unassignTask(final JbpmContext jbpmContext, final TaskInstance taskInstance) {
        return JbpmSqlHelper.execute(jbpmContext, new UnassignTaskSql(taskInstance));
    }

    /**
     * Unassign taak sql.
     */
    private static final class UnassignTaskSql implements Sql<Boolean> {
        private static final String SQL = "update jbpm_taskinstance set actorid_ = null, start_ = null " + "where id_ = ? and actorid_ = ?";

        private final TaskInstance taskInstance;

        private UnassignTaskSql(final TaskInstance taskInstance) {
            this.taskInstance = taskInstance;
        }

        @Override
        public Boolean execute(final Connection connection) {
            try (final PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setLong(JdbcConstants.COLUMN_1, taskInstance.getId());
                statement.setString(JdbcConstants.COLUMN_2, taskInstance.getActorId());

                statement.executeUpdate();

                return true;
            } catch (final SQLException exception) {
                LOGGER.debug("Er is een fout opgetreden bij het uitvoeren van 'unassign'.", exception);
                return false;
            }
        }
    }
}
