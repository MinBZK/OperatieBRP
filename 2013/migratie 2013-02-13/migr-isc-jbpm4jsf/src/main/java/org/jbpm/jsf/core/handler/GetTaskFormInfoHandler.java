/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.GetTaskFormInfoActionListener;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

import java.util.Map;

/**
 *
 */
@TldTag (
    name = "getTaskFormInfo",
    description = "Read process definition task form information from the database.",
    attributes = {
        @TldAttribute (
            name = "process",
            description = "The the process definition to read task form information for.",
            required = true,
            deferredType = ProcessDefinition.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the task form information should be stored.  This will be " +
                "in the form of a map whose keys are task names and whose values are the name of the task form file, " +
                "if any.",
            required = true,
            deferredType = Map.class
        )
    }
)
public final class GetTaskFormInfoHandler extends AbstractHandler {
    private final TagAttribute processTagAttribute;
    private final TagAttribute targetTagAttribute;

    public GetTaskFormInfoHandler(final TagConfig config) {
        super(config);
        processTagAttribute = getRequiredAttribute("process");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new GetTaskFormInfoActionListener(
            getValueExpression(processTagAttribute, ctx, ProcessDefinition.class),
            getValueExpression(targetTagAttribute, ctx, Object.class)
        );
    }
}
