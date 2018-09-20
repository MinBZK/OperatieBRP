/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.taskform.ui.UITaskFormCancelButton;
import org.jbpm.jsf.taskform.ui.UITaskFormSaveButton;
import org.jbpm.jsf.taskform.ui.UITaskFormTransitionButton;

import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 */
public final class TaskFormButtonActionListener implements JbpmActionListener {
    private final ValueExpression transitionTargetValueExpression;
    private final ValueExpression buttonTargetValueExpression;

    public TaskFormButtonActionListener(final ValueExpression transitionTargetValueExpression, final ValueExpression buttonTargetValueExpression) {
        this.transitionTargetValueExpression = transitionTargetValueExpression;
        this.buttonTargetValueExpression = buttonTargetValueExpression;
    }

    public String getName() {
        return "taskFormButton";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        final UIComponent source = event.getComponent();
        final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        if (source instanceof UITaskFormCancelButton) {
            context.selectOutcome("cancel");
            if (buttonTargetValueExpression != null) {
                buttonTargetValueExpression.setValue(elContext, "cancel");
            }
            log.fine("Cancel button selected");
        } else if (source instanceof UITaskFormSaveButton) {
            context.selectOutcome("save");
            if (buttonTargetValueExpression != null) {
                buttonTargetValueExpression.setValue(elContext, "save");
            }
            log.fine("Save button selected");
        } else if (source instanceof UITaskFormTransitionButton) {
            if (transitionTargetValueExpression != null) {
                final String transitionName = ((UITaskFormTransitionButton) source).getTransition();
                transitionTargetValueExpression.setValue(elContext, transitionName);
                if (log.isLoggable(Level.FINE)) {
                    log.fine("Updating transition expression '" + transitionTargetValueExpression.getExpressionString() + "' with transition value '" + transitionName + "'");
                }
            }
            if (buttonTargetValueExpression != null) {
                buttonTargetValueExpression.setValue(elContext, "transition");
            }
            log.fine("Transition button selected");
            context.selectOutcome("transition");
        }
    }

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.action.TaskFormButtonActionListener");
}
