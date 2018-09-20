/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import java.util.List;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ListTasksForProcessInstanceActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "listTasksForProcessInstance",
    description = "Read a list of task instances from a process instance.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the task instance list should be stored.",
            required = true,
            deferredType = List.class
        ),
        @TldAttribute (
            name = "processInstance",
            description = "The process instance whose task instances are to be read.",
            required = true,
            deferredType = ProcessInstance.class
        )
    }
)
public final class ListTasksForProcessInstanceHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processInstanceTagAttribute;

    public ListTasksForProcessInstanceHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTasksForProcessInstanceActionListener(
            getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
