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
import org.jbpm.jsf.core.action.ListTokensActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "listTokens",
    description = "Read a token list from a process instance.",
    attributes = {
        @TldAttribute (
            name = "processInstance",
            description = "The process instalce to read the token list from.",
            required = true,
            deferredType = ProcessInstance.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the token list should be stored.",
            required = true,
            deferredType = List.class
        )
    }
)
public final class ListTokensHandler extends AbstractHandler {
    private final TagAttribute processInstanceTagAttribute;
    private final TagAttribute targetTagAttribute;

    public ListTokensHandler(final TagConfig config) {
        super(config);
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTokensActionListener(
            getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
