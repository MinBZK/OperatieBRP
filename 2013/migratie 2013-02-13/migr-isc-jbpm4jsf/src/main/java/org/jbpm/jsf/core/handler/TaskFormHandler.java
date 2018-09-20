/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jbpm.jsf.core.ui.UITaskForm;
import org.jbpm.jsf.core.impl.JbpmActionListenerWrapper;
import org.jbpm.jsf.core.action.TaskFormButtonActionListener;
import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;

import com.sun.facelets.tag.jsf.ComponentHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.FaceletContext;

import javax.faces.component.UIComponent;
import javax.el.ValueExpression;

/**
 *
 */
@TldTag (
    name = "taskForm",
    description = "Represents the task form action source.",
    attributes = {
        @TldAttribute (
            name = "transitionTarget",
            description = "An EL expression into which the submitted transition name will be stored (if any)."
        ),
        @TldAttribute (
            name = "buttonTarget",
            description = "An EL expression into which the type of button clicked will be stored.  It will be one " +
                "of: <ul><li><code>cancel</code></li><li><code>save</code></li><li><code>transition</code></li></ul>"
        )
    }
)
public final class TaskFormHandler extends ComponentHandler {
    private final TagAttribute transitionTargetTagAttribute;
    private final TagAttribute buttonTargetTagAttribute;

    public TaskFormHandler(final ComponentConfig config) {
        super(config);
        transitionTargetTagAttribute = getAttribute("transitionTarget");
        buttonTargetTagAttribute = getAttribute("buttonTarget");
    }

    protected void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
        final ValueExpression transitionTargetValueExpression;
        if (transitionTargetTagAttribute != null) {
            transitionTargetValueExpression = transitionTargetTagAttribute.getValueExpression(ctx, String.class);
        } else {
            transitionTargetValueExpression = null;
        }
        final ValueExpression buttonTargetValueExpression;
        if (buttonTargetTagAttribute != null) {
            buttonTargetValueExpression = buttonTargetTagAttribute.getValueExpression(ctx, String.class);
        } else {
            buttonTargetValueExpression = null;
        }
        UITaskForm taskForm = ((UITaskForm)c);
        taskForm.addActionListener(new JbpmActionListenerWrapper(
            new TaskFormButtonActionListener(transitionTargetValueExpression, buttonTargetValueExpression),
            null,
            null,
            null
        ));
    }
}
