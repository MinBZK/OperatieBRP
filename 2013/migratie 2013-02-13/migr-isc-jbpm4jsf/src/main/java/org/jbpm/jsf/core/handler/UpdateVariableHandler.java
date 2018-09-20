/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.UpdateVariableActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

@TldTag (
    name = "updateVariable",
    description = "Update a process variable.  Optionally store the old value into an EL expression.",
    attributes = {
        @TldAttribute (
            name = "name",
            description = "The name of the variable to update.",
            required = true
        ),
        @TldAttribute (
            name = "value",
            description = "The new variable value.",
            required = true,
            deferredType = Object.class
        ),
        @TldAttribute (
            name = "target",
            description = "An optional EL expression into which the old variable value will be stored.",
            deferredType = Object.class
        ),
        @TldAttribute (
            name = "entity",
            description = "The token, task, or process instance for which the variable should be updated.",
            deferredType = Object.class,
            required = true
        )
    }
)
public final class UpdateVariableHandler extends AbstractHandler {
    private final TagAttribute nameTagAttribute;
    private final TagAttribute valueTagAttribute;
    private final TagAttribute oldValueTargetTagAttribute;
    private final TagAttribute entityTagAttribute;

    public UpdateVariableHandler(final TagConfig config) {
        super(config);
        nameTagAttribute = getRequiredAttribute("name");
        valueTagAttribute = getRequiredAttribute("value");
        oldValueTargetTagAttribute = getAttribute("target");
        entityTagAttribute = getRequiredAttribute("entity");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new UpdateVariableActionListener(
            getValueExpression(nameTagAttribute, ctx, String.class),
            getValueExpression(valueTagAttribute, ctx, Object.class),
            getValueExpression(oldValueTargetTagAttribute, ctx, Object.class),
            getValueExpression(entityTagAttribute, ctx, Object.class)
        );
    }
}
