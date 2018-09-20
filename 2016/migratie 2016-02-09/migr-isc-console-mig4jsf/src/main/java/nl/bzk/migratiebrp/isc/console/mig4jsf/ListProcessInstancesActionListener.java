/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Action listener voor list process instances tag.
 */
public final class ListProcessInstancesActionListener extends AbstractActionListener {

    private final ValueExpression targetExpression;
    private final ValueExpression processIdExpression;
    private final ValueExpression pagerExpression;
    private final ValueExpression filterExpression;

    /**
     * Constructor met expressie variabelen.
     *
     * @param processIdExpression
     *            Expressie waarin het id van de proces definitie staat.
     * @param pagerExpression
     *            Expressie waarin de {@link PagerBean pager bean} staat.
     * @param filterExpression
     *            Expressie waarin een {@link Filter filter} staat.
     * @param targetExpression
     *            Expressie waarin het resultaat komt te staan.
     */
    public ListProcessInstancesActionListener(
        final ValueExpression processIdExpression,
        final ValueExpression pagerExpression,
        final ValueExpression targetExpression,
        final ValueExpression filterExpression)
    {
        super("listProcessInstances");
        this.processIdExpression = processIdExpression;
        this.pagerExpression = pagerExpression;
        this.targetExpression = targetExpression;
        this.filterExpression = filterExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext jbpmJsfContext, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Long processDefinitionId = getProcessDefinitionId(elContext);
        final PagerBean pager = (PagerBean) pagerExpression.getValue(elContext);
        if (pager == null) {
            jbpmJsfContext.setError("Error loading pager for process instance list", "The pager value is null");
            return;
        }

        final Filter filter = filterExpression == null ? null : (Filter) filterExpression.getValue(elContext);

        final JbpmContext jbpmContext = jbpmJsfContext.getJbpmContext();
        final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);

        // Count total number of results
        final Criteria countCriteria = createCriteria(hibernateSession, processDefinitionId, filter);
        countCriteria.setProjection(Projections.rowCount());
        final Number count = (Number) countCriteria.uniqueResult();

        pager.setNumberOfResults(count.intValue());

        // Get acutal result
        final Criteria queryCriteria = createCriteria(hibernateSession, processDefinitionId, filter);
        final int firstResult = pager.getPageSize() * (pager.getPage() - 1);
        queryCriteria.setFirstResult(firstResult);
        queryCriteria.setMaxResults(pager.getPageSize());

        // Default ordering
        queryCriteria.addOrder(Order.desc("start"));
        queryCriteria.addOrder(Order.desc("id"));

        @SuppressWarnings("unchecked")
        final List<ProcessInstance> processInstancesList = queryCriteria.list();

        targetExpression.setValue(elContext, processInstancesList);
        jbpmJsfContext.selectOutcome("success");
    }

    private Criteria createCriteria(final Session session, final Long processDefinitionId, final Filter filter) {
        // Default selection criteria
        final Criteria criteria = session.createCriteria("org.jbpm.graph.exe.ProcessInstance");
        if (processDefinitionId != null) {
            criteria.add(Restrictions.eq("processDefinition.id", processDefinitionId));
        }

        // Add user defined criteria
        if (filter != null) {
            filter.applyFilter(criteria);
        }

        return criteria;
    }

    private Long getProcessDefinitionId(final ELContext elContext) {
        if (processIdExpression == null) {
            return null;
        }

        final Object idValue = processIdExpression.getValue(elContext);
        final Long id;
        if (idValue instanceof Number) {
            id = ((Number) idValue).longValue();
        } else if (idValue instanceof String) {
            id = Long.parseLong((String) idValue);
        } else if (idValue != null) {
            id = Long.parseLong(idValue.toString());
        } else {
            id = null;
        }
        return id;
    }

}
