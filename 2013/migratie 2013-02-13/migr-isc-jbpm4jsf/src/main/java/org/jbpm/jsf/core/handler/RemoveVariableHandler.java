/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.RemoveVariableActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

@TldTag(
    name = "removeVariable",
    description = "Remove a process variable.  Optionally store the old value into an EL expression.",
    attributes = {
        @TldAttribute (
            name = "name",
            description = "The name of the variable to remove.",
            required = true
        ),
        @TldAttribute (
            name = "target",
            description = "An optional EL expression into which the old variable value will be stored.",
            deferredType = Object.class
        ),
        @TldAttribute (
            name = "entity",
            description = "The token, task, or process instance from which the variable should be removed.",
            deferredType = Object.class,
            required = true
        )
    }
)
public final class RemoveVariableHandler extends AbstractHandler {
    private final TagAttribute nameTagAttribute;
    private final TagAttribute oldValueTargetTagAttribute;
    private final TagAttribute entityTagAttribute;

    public RemoveVariableHandler(final TagConfig config) {
        super(config);
        nameTagAttribute = getRequiredAttribute("name");
        oldValueTargetTagAttribute = getAttribute("target");
        entityTagAttribute = getRequiredAttribute("entity");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new RemoveVariableActionListener(
            getValueExpression(nameTagAttribute, ctx, String.class),
            getValueExpression(oldValueTargetTagAttribute, ctx, Object.class),
            getValueExpression(entityTagAttribute, ctx, Object.class)
        );
    }
}
