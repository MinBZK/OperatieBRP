/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.jsf.core.impl.UpdatesHashMap;
import org.jbpm.jsf.core.action.ApplyVariableMapActionListener;
import org.jbpm.jsf.JbpmActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagAttribute;

/**
 *
 */
@TldTag (
    name = "applyVariableMap",
    description = "Apply a variable map to a token, task, or process instance",
    attributes = {
        @TldAttribute (
            name = "variableMap",
            description = "The variable map to apply.",
            required = true,
            deferredType = UpdatesHashMap.class
        ),
        @TldAttribute (
            name = "target",
            description = "The token, task, or process instance to apply the variables to.",
            required = true,
            deferredType = Object.class
        )
    }
)
public final class ApplyVariableMapHandler extends AbstractHandler {
    private final TagAttribute variableMapTagAttribute;
    private final TagAttribute targetTagAttribute;

    public ApplyVariableMapHandler(final TagConfig config) {
        super(config);
        variableMapTagAttribute = getRequiredAttribute("variableMap");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ApplyVariableMapActionListener(
            getValueExpression(variableMapTagAttribute, ctx, UpdatesHashMap.class),
            getValueExpression(targetTagAttribute, ctx, Object.class)
        );
    }
}
