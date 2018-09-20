/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskform.ui;

import org.jbpm.jsf.core.ui.UITaskForm;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.AbortProcessingException;
import javax.faces.FacesException;
import javax.el.MethodExpression;

/**
 *
 */
public abstract class UITaskFormButtonBase extends HtmlCommandButton {

    private UITaskForm enclosingForm;

    private UITaskForm getEnclosingForm() {
        if (enclosingForm != null) {
            return enclosingForm;
        }
        UIComponent c = getParent();
        do {
            if (c instanceof UITaskForm) {
                enclosingForm = (UITaskForm) c;
                return enclosingForm;
            }
            c = c.getParent();
        } while (c != null);
        throw new FacesException("Task form buttons must be includede within a task form");
    }

    public MethodExpression getActionExpression() {
        return getEnclosingForm().getActionExpression();
    }

    public void setActionExpression(MethodExpression actionExpression) {
        getEnclosingForm().setActionExpression(actionExpression);
    }

    public void addActionListener(ActionListener listener) {
        getEnclosingForm().addActionListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return getEnclosingForm().getActionListeners();
    }

    public void removeActionListener(ActionListener listener) {
        getEnclosingForm().removeActionListener(listener);
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        getEnclosingForm().broadcast(event);
    }

    public void queueEvent(FacesEvent e) {
        getEnclosingForm().queueEvent(e);
    }
}
