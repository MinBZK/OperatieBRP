/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * List gerelateerd process instances voor proces instance handler.
 */
public final class ListProcessInstancesForProcessInstanceActionListener extends AbstractActionListener {

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    /**
     * Constructor.
     * @param processInstanceExpression process instance expression
     * @param targetExpression target expression
     */
    public ListProcessInstancesForProcessInstanceActionListener(final ValueExpression processInstanceExpression, final ValueExpression targetExpression) {
        super("listProcessInstancesForProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final ProcessInstance selectedProcessInstance = (ProcessInstance) processInstanceExpression.getValue(elContext);
        // get root instance
        final ProcessInstance rootProcessInstance = ProcessInstanceUtil.getRootProcessInstance(selectedProcessInstance);
        // get all related and sub instances
        final List<ProcessInstance> allProcessInstances = ProcessInstanceUtil.getProcessInstances(context.getJbpmContext(), rootProcessInstance);
        // filter selected instance
        final List<ProcessInstance> allProcessInstancesFiltered = new ArrayList<>();
        for (final ProcessInstance processInstance : allProcessInstances) {
            if (processInstance != null && processInstance.getId() != selectedProcessInstance.getId()) {
                allProcessInstancesFiltered.add(processInstance);
            }
        }
        // result
        targetExpression.setValue(elContext, Collections.unmodifiableCollection(allProcessInstancesFiltered));
        context.selectOutcome("success");
    }

}
