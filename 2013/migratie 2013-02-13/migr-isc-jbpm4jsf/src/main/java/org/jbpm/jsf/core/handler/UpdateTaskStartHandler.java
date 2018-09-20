/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.UpdateTaskStartActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagAttribute;

import java.util.Date;

/**
 *
 */
@TldTag (
    name = "updateTaskStart",
    description = "Update the start date of a started task.",
    attributes = {
        @TldAttribute (
            name = "task",
            description = "The task instance to update.",
            required = true,
            deferredType = TaskInstance.class
        ),
        @TldAttribute (
            name = "startDate",
            description = "The start date to assign.  If not given, the current date will be used.",
            deferredType = Date.class
        )
    }
)
public final class UpdateTaskStartHandler extends AbstractHandler {
    private final TagAttribute taskTagAttribute;
    private final TagAttribute startDateTagAttribute;

    public UpdateTaskStartHandler(final TagConfig config) {
        super(config);
        taskTagAttribute = getRequiredAttribute("task");
        startDateTagAttribute = getAttribute("startDate");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new UpdateTaskStartActionListener(
            getValueExpression(taskTagAttribute, ctx, TaskInstance.class),
            getValueExpression(startDateTagAttribute, ctx, Date.class)
        );
    }
}
