/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.CompleteTaskActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "completeTask",
    description = "Complete a task.",
    attributes = {
        @TldAttribute (
            name = "task",
            description = "An EL expression that resolves to the task instance to complete.",
            required = true,
            deferredType = TaskInstance.class
        ),
        @TldAttribute (
            name = "transition",
            description = "An EL expression that resolves to either the name of a transition, or the transition object to take upon completing this task.",
            deferredType = Object.class
        )
    }
)
public final class CompleteTaskHandler extends AbstractHandler {
    private final TagAttribute instanceTagAttribute;
    private final TagAttribute transitionTagAttribute;

    public CompleteTaskHandler(final TagConfig config) {
        super(config);
        instanceTagAttribute = getRequiredAttribute("task");
        transitionTagAttribute = getAttribute("transition");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new CompleteTaskActionListener(
            getValueExpression(instanceTagAttribute, ctx, TaskInstance.class),
            getValueExpression(transitionTagAttribute, ctx, Object.class)
        );
    }
}
