/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.GetVariableMapActionListener;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

import java.util.Map;

/**
 *
 */
@TldTag (
    name = "getVariableMap",
    description = "Get the variable map from a token, process instance, or task.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the variable map will be stored.",
            deferredType = Map.class,
            required = true
        ),
        @TldAttribute (
            name = "value",
            description = "The entity from which the variable should be retrieved.",
            deferredType = Object.class,
            required = true
        )
    }
)
public final class GetVariableMapHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute valueTagAttribute;

    public GetVariableMapHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        valueTagAttribute = getRequiredAttribute("value");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new GetVariableMapActionListener(
            getValueExpression(valueTagAttribute, ctx, Object.class),
            getValueExpression(targetTagAttribute, ctx, Map.class)
        );
    }
}
