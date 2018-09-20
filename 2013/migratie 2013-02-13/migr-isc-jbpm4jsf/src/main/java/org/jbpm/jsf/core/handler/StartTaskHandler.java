/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.StartTaskActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "startTask",
    description = "Mark a task as started.",
    attributes = {
        @TldAttribute (
            name = "task",
            description = "The task to mark as started.",
            required = true,
            deferredType = TaskInstance.class
        ),
        @TldAttribute (
            name = "actorId",
            description = "The actor to assign the task to.  If not given, the current actor is not changed.",
            deferredType = String.class
        ),
        @TldAttribute (
            name = "overwriteSwimlane",
            description = "A flag specifying whether the swimlane will be overwritten.",
            deferredType = boolean.class
        )
    }
)
public final class StartTaskHandler extends AbstractHandler {
    private final TagAttribute instanceTagAttribute;
    private final TagAttribute actorIdTagAttribute;
    private final TagAttribute overwriteSwimlaneTagAttribute;

    public StartTaskHandler(final TagConfig config) {
        super(config);
        instanceTagAttribute = getRequiredAttribute("task");
        actorIdTagAttribute = getAttribute("actorId");
        overwriteSwimlaneTagAttribute = getAttribute("overwriteSwimlane");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new StartTaskActionListener(
            getValueExpression(instanceTagAttribute, ctx, TaskInstance.class),
            getValueExpression(actorIdTagAttribute, ctx, String.class),
            getValueExpression(overwriteSwimlaneTagAttribute, ctx, Boolean.class)
        );
    }
}

