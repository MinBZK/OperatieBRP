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
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.JbpmSqlHelper.Sql;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.GerelateerdGegeven;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.GerelateerdGegevenMapper;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * List gerelateerd process instances voor proces instance handler.
 */
public final class ListGerelateerdeGegevensForProcessInstanceActionListener extends AbstractActionListener {

    private static final String SPACE = " ";

    private static final GerelateerdGegevenMapper GEGEVEN_MAPPER = new GerelateerdGegevenMapper();

    private final ValueExpression targetExpression;
    private final ValueExpression pagerExpression;
    private final ValueExpression processInstanceExpression;

    /**
     * Constructor.
     *
     * @param processInstanceExpression
     *            process instance expression
     * @param pagerExpression
     *            pager expression
     * @param targetExpression
     *            target expression
     */
    public ListGerelateerdeGegevensForProcessInstanceActionListener(
        final ValueExpression processInstanceExpression,
        final ValueExpression pagerExpression,
        final ValueExpression targetExpression)
    {
        super("listGerelateerdeGegevensForProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
        this.pagerExpression = pagerExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext jbpmJsfContext, final ActionEvent event) {

        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final PagerBean pager = (PagerBean) pagerExpression.getValue(elContext);
        if (pager == null) {
            jbpmJsfContext.setError("Error loading pager for gerelateerde gegevens list", "The pager value is null");
            return;
        }

        final ProcessInstance processInstance = (ProcessInstance) processInstanceExpression.getValue(elContext);

        // Jbpm Context
        final JbpmContext jbpmContext = jbpmJsfContext.getJbpmContext();

        final String whereClause = bepaalWhere(processInstance);
        final String limitClause = bepaalLimit(pager);

        // Count total number of results
        final Number count = JbpmSqlHelper.execute(jbpmContext, new CountSql(whereClause));

        pager.setNumberOfResults(count.intValue());

        // List
        final List<GerelateerdGegeven> berichtenList = JbpmSqlHelper.execute(jbpmContext, new ListSql(whereClause, limitClause));

        targetExpression.setValue(elContext, berichtenList);
        jbpmJsfContext.selectOutcome("success");
    }

    private String bepaalWhere(final ProcessInstance processInstance) {
        return "where process_instance_id = " + processInstance.getId();
    }

    private String bepaalLimit(final PagerBean pager) {
        return " limit " + pager.getPageSize() + " offset " + pager.getPageSize() * (pager.getPage() - 1);
    }

    /**
     * Sql om gerelateerde processen te tellen.
     */
    private static final class CountSql implements Sql<Number> {
        private static final String COUNT_SQL = "select count(*) from mig_proces_gerelateerd";

        private final String whereClause;

        /**
         * Constructor.
         *
         * @param whereClause
         *            where clause
         */
        public CountSql(final String whereClause) {
            this.whereClause = whereClause;
        }

        @Override
        public Number execute(final Connection connection) throws SQLException {
            final String sql = COUNT_SQL + SPACE + whereClause;
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.execute();
                try (ResultSet rs = statement.getResultSet()) {
                    rs.next();
                    return rs.getLong(1);
                }
            }
        }
    }

    /**
     * Sql om gerelateerde proces gegevens te laden.
     */
    private static final class ListSql implements Sql<List<GerelateerdGegeven>> {
        private static final String SELECT_SQL = "select soort_gegeven, gegeven from mig_proces_gerelateerd";
        private static final String ORDER_SQL = "order by soort_gegeven desc, gegeven asc";

        private final String whereClause;
        private final String limitClause;

        /**
         * Constructor.
         *
         * @param whereClause
         *            where clause
         * @param limitClause
         *            limit clause
         */
        public ListSql(final String whereClause, final String limitClause) {
            this.whereClause = whereClause;
            this.limitClause = limitClause;
        }

        @Override
        public List<GerelateerdGegeven> execute(final Connection connection) throws SQLException {
            final String sql = SELECT_SQL + SPACE + whereClause + SPACE + ORDER_SQL + SPACE + limitClause;
            List<GerelateerdGegeven> berichten;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.execute();
                try (ResultSet rs = statement.getResultSet()) {

                    berichten = new ArrayList<>();
                    while (rs.next()) {
                        berichten.add(GEGEVEN_MAPPER.map(rs));
                    }
                }
            }

            return berichten;
        }
    }
}
