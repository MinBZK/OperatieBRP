/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.BerichtComparator;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.BerichtMapper;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter.StartType;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * List berichten voor proces instance handler.
 */
public final class ListBerichtenForProcessInstanceActionListener extends AbstractActionListener {

    private static final String FIND_BY_PROCESS_SQL = "select " + BerichtMapper.COLUMNS + " from mig_bericht where process_instance_id = ?";
    private static final BerichtMapper BERICHT_MAPPER = new BerichtMapper();

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;
    private final ValueExpression pagerExpression;
    private final ValueExpression filterExpression;

    /**
     * Constructor.
     * @param processInstanceExpression process instance expression
     * @param targetExpression target expression
     * @param pagerExpression pager expression
     * @param filterExpression filter expression
     */
    public ListBerichtenForProcessInstanceActionListener(
            final ValueExpression processInstanceExpression,
            final ValueExpression targetExpression,
            final ValueExpression pagerExpression,
            final ValueExpression filterExpression) {
        super("listBerichtenForProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
        this.pagerExpression = pagerExpression;
        this.filterExpression = filterExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final PagerBean pager = (PagerBean) pagerExpression.getValue(elContext);
        if (pager == null) {
            context.setError("Error loading pager for berichten list", "The pager value is null");
            return;
        }

        final Filter filter = filterExpression == null ? null : (Filter) filterExpression.getValue(elContext);

        final ProcessInstance rootProcessInstance =
                ProcessInstanceUtil.getRootProcessInstance((ProcessInstance) processInstanceExpression.getValue(elContext));

        final List<ProcessInstance> allProcessInstances = ProcessInstanceUtil.getProcessInstances(context.getJbpmContext(), rootProcessInstance);

        final List<Bericht> berichten = new ArrayList<>();
        for (final ProcessInstance processInstance : allProcessInstances) {
            berichten.addAll(getBerichten(context.getJbpmContext(), processInstance.getId(), filter));
        }

        Collections.sort(berichten, new BerichtComparator());

        pager.setNumberOfResults(berichten.size());
        if (pager.getPage() > 1) {
            for (int i = 0; i < pager.getPageSize() * (pager.getPage() - 1); i++) {
                berichten.remove(0);
            }
        }

        targetExpression.setValue(elContext, Collections.unmodifiableCollection(berichten));
        context.selectOutcome("success");
    }

    private List<Bericht> getBerichten(final JbpmContext jbpmContext, final long processInstanceId, final Filter filter) {
        return JbpmSqlHelper.execute(jbpmContext, connection -> {
            final String sql = FIND_BY_PROCESS_SQL + " " + filter.getWhereClause(StartType.AND);
            List<Bericht> berichten;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(JdbcConstants.COLUMN_1, processInstanceId);
                filter.setWhereClause(statement, JdbcConstants.COLUMN_2);
                statement.execute();
                try (final ResultSet rs = statement.getResultSet()) {

                    berichten = new ArrayList<>();
                    while (rs.next()) {
                        berichten.add(BERICHT_MAPPER.map(rs));
                    }
                }
            }
            return berichten;
        });
    }
}
