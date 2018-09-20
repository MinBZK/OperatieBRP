/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.impl;

import java.io.Serializable;
import java.util.Map;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 */
public final class JbpmActionListenerWrapper implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final JbpmActionListener listener;

    private final ValueExpression unlessExpression;
    private final ValueExpression navigateExpression;
    private final ValueExpression nameExpression;

    public JbpmActionListenerWrapper(final JbpmActionListener listener, final ValueExpression unlessExpression, final ValueExpression navigateExpression, final ValueExpression nameExpression) {
        this.listener = listener;
        this.unlessExpression = unlessExpression;
        this.navigateExpression = navigateExpression;
        this.nameExpression = nameExpression;
    }

    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        final ELContext elContext = context.getELContext();
        final Map<String,Object> requestMap = context.getExternalContext().getRequestMap();
        final JbpmJsfContextImpl jbpmJsfContext;
        if (requestMap.containsKey(AbstractHandler.JBPM_JSF_CONTEXT_KEY)) {
            jbpmJsfContext = (JbpmJsfContextImpl) requestMap.get(AbstractHandler.JBPM_JSF_CONTEXT_KEY);
        } else {
            jbpmJsfContext = new JbpmJsfContextImpl();
            requestMap.put(AbstractHandler.JBPM_JSF_CONTEXT_KEY, jbpmJsfContext);
        }
        if (unlessExpression != null) {
            final Object unlessValue = unlessExpression.getValue(elContext);
            if (unlessValue != null) {
                final Boolean unless;
                if (unlessValue instanceof Boolean) {
                    unless = (Boolean) unlessValue;
                } else {
                    unless = Boolean.valueOf(unlessValue.toString());
                }
                if (unless.booleanValue()) {
                    // do not run action
                    return;
                }
            }
        }
        boolean enableNavigation = true;
        if (navigateExpression != null) {
            final Object navigateValue = navigateExpression.getValue(elContext);
            if (navigateValue != null) {
                final Boolean navigate;
                if (navigateValue instanceof Boolean) {
                    navigate = (Boolean) navigateValue;
                } else {
                    navigate = Boolean.valueOf(navigateValue.toString());
                }
                enableNavigation = navigate.booleanValue();
            }
        }
        String name = listener.getName();
        if (nameExpression != null) {
            final Object nameValue = nameExpression.getValue(elContext);
            if (nameValue != null) {
                name = nameValue.toString();
            }
        }
        jbpmJsfContext.setEnableNavigation(enableNavigation);
        jbpmJsfContext.setActionName(name);
        listener.handleAction(jbpmJsfContext, actionEvent);
    }
}
