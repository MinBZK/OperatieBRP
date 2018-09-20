/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class MoveTokenActionListener implements JbpmActionListener {
    private final ValueExpression tokenExpression;
    private final ValueExpression nodeExpression;

    public MoveTokenActionListener(final ValueExpression tokenExpression, final ValueExpression nodeExpression) {
        this.tokenExpression = tokenExpression;
        this.nodeExpression = nodeExpression;
    }

    public String getName() {
        return "moveToken";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object tokenValue = tokenExpression.getValue(elContext);
            if (tokenValue == null) {
                context.setError("Error moving token", "The token value is null");
                return;
            }
            if (! (tokenValue instanceof Token)) {
                context.setError("Error moving token", "Attempted to move something other than a token");
                return;
            }
            final Token token = (Token) tokenValue;
            final Node node;
            final Object nodeValue = nodeExpression.getValue(elContext);
            if (nodeValue == null) {
                context.setError("Error moving token", "Node value is null");
                return;
            }
            if (nodeValue instanceof Node) {
                node = (Node) nodeValue;
            } else {
                final String nodeName = nodeValue.toString();
                node = token.getProcessInstance().getProcessDefinition().getNode(nodeName);
                if (node == null) {
                    context.setError("Error moving token", "No node found by name of '" + nodeName + "'");
                    return;
                }
            }
            token.setNode(node);
            context.addSuccessMessage("Token moved to node '" + node.getName() + "'");
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error moving token", ex);
            return;
        }
    }
}
