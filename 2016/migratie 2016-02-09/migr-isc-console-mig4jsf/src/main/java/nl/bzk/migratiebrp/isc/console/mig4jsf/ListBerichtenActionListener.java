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
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.BerichtMapper;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter.StartType;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Action listener voor list berichten tag.
 */
public final class ListBerichtenActionListener extends AbstractActionListener {

    private static final String FIND_ALL_BERICHTEN_SQL = "select " + BerichtMapper.COLUMNS + " from mig_bericht";
    private static final String SPACE = " ";
    private static final String ORDER_BERICHTEN = "order by tijdstip desc, id desc";
    private static final String COUNT_ALL_BERICHTEN_SQL = "select count(*) from mig_bericht";

    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    private final ValueExpression targetExpression;
    private final ValueExpression pagerExpression;
    private final ValueExpression filterExpression;

    /**
     * Constructor met expressie variabelen.
     *
     * @param pagerExpression
     *            Expressie waarin de {@link PagerBean pager bean} staat.
     * @param filterExpression
     *            Expressie waarin een {@link Filter filter} staat.
     * @param targetExpression
     *            Expressie waarin het resultaat komt te staan.
     */
    public ListBerichtenActionListener(
        final ValueExpression targetExpression,
        final ValueExpression pagerExpression,
        final ValueExpression filterExpression)
    {
        super("listBerichten");
        this.targetExpression = targetExpression;
        this.pagerExpression = pagerExpression;
        this.filterExpression = filterExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext jbpmJsfContext, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final PagerBean pager = (PagerBean) pagerExpression.getValue(elContext);
        if (pager == null) {
            jbpmJsfContext.setError("Error loading pager for berichten list", "The pager value is null");
            return;
        }

        final Filter filter = (Filter) filterExpression.getValue(elContext);

        // Jbpm Context
        final JbpmContext jbpmContext = jbpmJsfContext.getJbpmContext();

        final String whereClause = filter.getWhereClause(StartType.WHERE);
        final String limitClause = bepaalLimit(pager);

        // Count total number of results
        final Number count = JbpmSqlHelper.execute(jbpmContext, new Sql<Number>() {
            @Override
            public Number execute(final Connection connection) throws SQLException {
                try (final PreparedStatement statement = connection.prepareStatement(COUNT_ALL_BERICHTEN_SQL + SPACE + whereClause)) {
                    filter.setWhereClause(statement, JdbcConstants.COLUMN_1);
                    statement.execute();
                    try (final ResultSet rs = statement.getResultSet()) {
                        rs.next();
                        return rs.getLong(1);
                    }
                }
            }
        });

        pager.setNumberOfResults(count.intValue());

        // List
        final List<Bericht> berichtenList = JbpmSqlHelper.execute(jbpmContext, new Sql<List<Bericht>>() {
            @Override
            public List<Bericht> execute(final Connection connection) throws SQLException {
                final String sql = FIND_ALL_BERICHTEN_SQL + SPACE + whereClause + SPACE + ORDER_BERICHTEN + SPACE + limitClause;
                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                    filter.setWhereClause(statement, JdbcConstants.COLUMN_1);
                    statement.execute();
                    final List<Bericht> berichten = new ArrayList<>();
                    try (final ResultSet rs = statement.getResultSet()) {
                        while (rs.next()) {
                            berichten.add(BERICHT_MAPPER.map(rs));
                        }
                    }
                    return berichten;
                }
            }
        });

        targetExpression.setValue(elContext, berichtenList);
        jbpmJsfContext.selectOutcome("success");
    }

    private String bepaalLimit(final PagerBean pager) {
        return " limit " + pager.getPageSize() + " offset " + pager.getPageSize() * (pager.getPage() - 1);
    }

}
