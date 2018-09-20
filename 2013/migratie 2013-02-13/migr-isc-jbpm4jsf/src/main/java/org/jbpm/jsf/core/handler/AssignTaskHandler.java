/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.AssignTaskActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "assignTask",
    description = "Assign a task to an actor.",
    attributes = {
        @TldAttribute(
            name = "task",
            description = "The task instance to assign.",
            required = true,
            deferredType = TaskInstance.class
        ),
        @TldAttribute(
            name = "actorId",
            description = "The actor ID to which the task should be assigned.",
            required = true,
            deferredType = String.class
        ),
        @TldAttribute(
            name = "overwriteSwimlane",
            description = "A flag that indicates whether the swimlane should be overwritten.",
            required = false,
            deferredType = boolean.class
        )
    }
)
public final class AssignTaskHandler extends AbstractHandler {
    private final TagAttribute taskTagAttribute;
    private final TagAttribute actorIdTagAttribute;
    private final TagAttribute overwriteSwimlaneTagAttribute;

    public AssignTaskHandler(final TagConfig config) {
        super(config);
        taskTagAttribute = getRequiredAttribute("task");
        actorIdTagAttribute = getRequiredAttribute("actorId");
        overwriteSwimlaneTagAttribute = getAttribute("overwriteSwimlane");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new AssignTaskActionListener(
            getValueExpression(taskTagAttribute, ctx, TaskInstance.class),
            getValueExpression(actorIdTagAttribute, ctx, String.class),
            getValueExpression(overwriteSwimlaneTagAttribute, ctx, Boolean.class)
        );
    }
}
