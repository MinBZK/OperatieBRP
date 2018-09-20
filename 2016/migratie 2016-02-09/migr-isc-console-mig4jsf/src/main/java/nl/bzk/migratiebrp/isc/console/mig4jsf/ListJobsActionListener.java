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
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Action listener voor list jobs tag.
 */
public final class ListJobsActionListener extends AbstractActionListener {

    private static final String VIEWMODE_TIMERS = "timers";
    private static final String VIEWMODE_ACTION = "action";
    private static final String VIEWMODE_NODE = "node";

    private static final String ERROR_MESSAGE = "Error loading process instance list";

    private final ValueExpression targetExpression;
    private final ValueExpression pagerExpression;
    private final ValueExpression viewModeExpression;

    /**
     * Constructor met expressie variabelen.
     *
     * @param pagerExpression
     *            Expressie waarin de {@link PagerBean pager bean} staat.
     * @param viewModeExpression
     *            Expressie waarin de viewMode staat.
     * @param targetExpression
     *            Expressie waarin het resultaat komt te staan.
     */
    public ListJobsActionListener(final ValueExpression pagerExpression, final ValueExpression targetExpression, final ValueExpression viewModeExpression)
    {
        super("listJobs");
        this.pagerExpression = pagerExpression;
        this.targetExpression = targetExpression;
        this.viewModeExpression = viewModeExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext jbpmJsfContext, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final PagerBean pager = (PagerBean) pagerExpression.getValue(elContext);
        if (pager == null) {
            jbpmJsfContext.setError(ERROR_MESSAGE, "The pager value is null");
            return;
        }

        final String viewMode = (String) viewModeExpression.getValue(elContext);
        if (viewMode == null) {
            jbpmJsfContext.setError(ERROR_MESSAGE, "The viewMode value is null");
            return;
        }

        final JbpmContext jbpmContext = jbpmJsfContext.getJbpmContext();
        final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);

        // Count total number of results
        final Criteria countCriteria = createCriteria(hibernateSession, viewMode);
        countCriteria.setProjection(Projections.rowCount());
        final Number count = (Number) countCriteria.uniqueResult();

        pager.setNumberOfResults(count.intValue());

        // Get acutal result
        final Criteria queryCriteria = createCriteria(hibernateSession, viewMode);
        queryCriteria.setFirstResult(pager.getPageSize() * (pager.getPage() - 1) + 1);
        queryCriteria.setMaxResults(pager.getPageSize());

        // Default ordering
        queryCriteria.addOrder(Order.desc("dueDate"));

        final List<?> processInstancesList = queryCriteria.list();

        targetExpression.setValue(elContext, processInstancesList);
        jbpmJsfContext.selectOutcome("success");
    }

    private Criteria createCriteria(final Session session, final String viewMode) {
        final String entityName;
        if (VIEWMODE_TIMERS.equals(viewMode)) {
            entityName = "org.jbpm.job.Timer";
        } else if (VIEWMODE_ACTION.equals(viewMode)) {
            entityName = "org.jbpm.job.ExecuteActionJob";
        } else if (VIEWMODE_NODE.equals(viewMode)) {
            entityName = "org.jbpm.job.ExecuteNodeJob";
        } else {
            entityName = "org.jbpm.job.Job";
        }

        // Default selection criteria
        return session.createCriteria(entityName);
    }

}
