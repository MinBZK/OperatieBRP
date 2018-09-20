/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class LazyJbpmActionListener implements JbpmActionListener {

    private final ValueExpression typeExpression;
    private final ValueExpression listenerExpression;

    public LazyJbpmActionListener(final ValueExpression typeExpression, final ValueExpression listenerExpression) {
        this.typeExpression = typeExpression;
        this.listenerExpression = listenerExpression;
    }

    public String getName() {
        return "jbpmActionListener";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final JbpmActionListener listener;
        if (typeExpression != null) {
            final Object typeValue = typeExpression.getValue(elContext);
            if (typeValue == null) {
                context.setError("Error calling action listener", "The type value is null");
                return;
            }
            final Class<?> type;
            if (typeValue instanceof Class<?>) {
                type = (Class<?>) typeValue;
            } else {
                final String className = typeValue.toString();
                try {
                    type = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    context.setError("Error calling action listener", "The class '" + className + "' was not found");
                    return;
                }
            }
            if (! JbpmActionListener.class.isAssignableFrom(type)) {
                context.setError("Error calling action listener", "The class '" + type.getName() + "' is not a valid JbpmActionListener");
                return;
            }
            try {
                listener = (JbpmActionListener) type.newInstance();
            } catch (Exception e) {
                context.setError("Error calling action listener", e);
                return;
            }
        } else if (listenerExpression != null) {
            final Object listenerValue = listenerExpression.getValue(elContext);
            if (listenerValue == null) {
                context.setError("Error calling action listener", "The listener value is null");
                return;
            }
            if (! (listenerValue instanceof JbpmActionListener)) {
                context.setError("Error calling action listener", "The listener value given is not a valid JbpmActionListener");
                return;
            }
            listener = (JbpmActionListener) listenerValue;
        } else {
            context.setError("Error calling action listener", "Either a type or a listener must be given");
            return;
        }
        listener.handleAction(context, event);
    }
}
