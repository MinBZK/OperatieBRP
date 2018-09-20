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
import org.jbpm.jsf.core.action.LoadTokenActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "loadToken",
    description = "Read a token from the database.",
    attributes = {
        @TldAttribute (
            name = "id",
            description = "The ID of the token to load.",
            required = true,
            deferredType = long.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the token should be stored.",
            required = true,
            deferredType = Token.class
        ),
        @TldAttribute (
            name = "forUpdate",
            description = "A boolean that controls whether the token is to be read for update. " +
                "This typically entails using a <code>SELECT ... FOR UPDATE</code> statement, when database persistence is " +
                "involved.  By default, this flag is <code>false</code> if the action is invoked during the " +
                "<code>RENDER_RESPONSE</code> phase, and <code>true</code> otherwise.",
            deferredType = boolean.class
        )
    }
)
public final class LoadTokenHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute targetTagAttribute;
    private final TagAttribute forUpdateTagAttribute;

    public LoadTokenHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        targetTagAttribute = getRequiredAttribute("target");
        forUpdateTagAttribute = getAttribute("forUpdate");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadTokenActionListener(
            getValueExpression(idTagAttribute, ctx, Long.class),
            getValueExpression(targetTagAttribute, ctx, Token.class),
            getValueExpression(forUpdateTagAttribute, ctx, Boolean.class)
        );
    }
}
