/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.GetDiagramInfoActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "getDiagramInfo",
    description = "Read process definition diagram information from the database.",
    attributes = {
        @TldAttribute (
            name = "process",
            description = "The the process definition to read diagram information for.",
            required = true,
            deferredType = ProcessDefinition.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the diagram information should be stored.",
            required = true,
            deferredType = Object.class
        )
    }
)
public final class GetDiagramInfoHandler extends AbstractHandler {
    private final TagAttribute processTagAttribute;
    private final TagAttribute targetTagAttribute;

    public GetDiagramInfoHandler(final TagConfig config) {
        super(config);
        processTagAttribute = getRequiredAttribute("process");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new GetDiagramInfoActionListener(
            getValueExpression(processTagAttribute, ctx, ProcessDefinition.class),
            getValueExpression(targetTagAttribute, ctx, Object.class)
        );
    }
}
