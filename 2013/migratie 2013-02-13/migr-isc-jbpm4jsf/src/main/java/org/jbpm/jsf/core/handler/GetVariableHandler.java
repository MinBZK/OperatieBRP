/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.GetVariableActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "getVariable",
    description = "Get the value of a process variable.",
    attributes = {
        @TldAttribute (
            name = "name",
            description = "The name of the variable to remove.",
            required = true
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the variable value will be stored.",
            deferredType = Object.class,
            required = true
        ),
        @TldAttribute (
            name = "entity",
            description = "The token, task, or process instance from which the variable value should be retrieved.",
            deferredType = Object.class,
            required = true
        )
    }
)
public final class GetVariableHandler extends AbstractHandler {
    private final TagAttribute nameTagAttribute;
    private final TagAttribute targetTagAttribute;
    private final TagAttribute entityTagAttribute;

    public GetVariableHandler(final TagConfig config) {
        super(config);
        nameTagAttribute = getRequiredAttribute("name");
        targetTagAttribute = getRequiredAttribute("target");
        entityTagAttribute = getRequiredAttribute("entity");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new GetVariableActionListener(
            getValueExpression(nameTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, Object.class),
            getValueExpression(entityTagAttribute, ctx, Object.class)
        );
    }
}
