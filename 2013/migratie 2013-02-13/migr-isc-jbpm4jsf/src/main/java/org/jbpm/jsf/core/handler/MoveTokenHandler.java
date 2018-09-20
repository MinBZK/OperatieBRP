/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.MoveTokenActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "moveToken",
    description = "Move a token to a new node.  Note that this does not affect any " +
        "tasks that may be active and associated with this token.",
    attributes = {
        @TldAttribute (
            name = "token",
            description = "An EL expression which resolves to the token that is to be moved.",
            required = true,
            deferredType = Token.class
        ),
        @TldAttribute(
            name = "node",
            description = "An EL expression which resolves to the node to move the token to, or " +
                "the name of the node.",
            required = true,
            deferredType = Object.class
        )
    }
)
public final class MoveTokenHandler extends AbstractHandler {
    private final TagAttribute tokenTagAttribute;
    private final TagAttribute nodeTagAttribute;

    public MoveTokenHandler(final TagConfig config) {
        super(config);
        tokenTagAttribute = getRequiredAttribute("token");
        nodeTagAttribute = getRequiredAttribute("node");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new MoveTokenActionListener(
            getValueExpression(tokenTagAttribute, ctx, Token.class),
            getValueExpression(nodeTagAttribute, ctx, Object.class)
        );
    }
}
