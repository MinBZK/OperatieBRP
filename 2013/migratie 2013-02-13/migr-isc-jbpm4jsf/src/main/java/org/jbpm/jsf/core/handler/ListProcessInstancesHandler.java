/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ListProcessInstancesActionListener;
import java.util.List;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

@TldTag (
    name = "listProcessInstances",
    description = "Read a list of process instances from a process definition.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the process instance list should be stored.",
            required = true,
            deferredType = List.class
        ),
        @TldAttribute (
            name = "processId",
            description = "The numeric ID of the process definition whose instances are to be read.",
            required = true,
            deferredType = long.class
        )
    }
)
public final class ListProcessInstancesHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processIdTagAttribute;

    public ListProcessInstancesHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processIdTagAttribute = getRequiredAttribute("processId");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListProcessInstancesActionListener(
            getValueExpression(processIdTagAttribute, ctx, Long.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
