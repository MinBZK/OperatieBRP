/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class AddCommentActionListener implements JbpmActionListener {

    private final ValueExpression comment;
    private final ValueExpression target;

    public AddCommentActionListener(final ValueExpression comment, final ValueExpression target) {
        this.comment = comment;
        this.target = target;
    }

    public String getName() {
        return "addComment";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object commentValue = comment.getValue(elContext);
            if (commentValue != null) {
                final String commentAsString = commentValue.toString();
                if (commentAsString != null) {
                    final String commentText = commentAsString.trim();
                    if (!"".equals(commentText)) {
                        final Object targetValue = target.getValue(elContext);
                        if (targetValue instanceof Token) {
                            ((Token) targetValue).addComment(commentText);
                        } else if (targetValue instanceof TaskInstance) {
                            ((TaskInstance) targetValue).addComment(commentText);
                        } else {
                            context.setError("Failed to add comment", "The addComment action target refers to an invalid type");
                            return;
                        }
                        context.addSuccessMessage("Comment added successfully");
                        context.selectOutcome("success");
                    }
                }
            }
            context.getJbpmContext().getSession().flush();
        } catch (Exception ex) {
            context.setError("Failed to add comment", ex);
            return;
        }
    }
}
