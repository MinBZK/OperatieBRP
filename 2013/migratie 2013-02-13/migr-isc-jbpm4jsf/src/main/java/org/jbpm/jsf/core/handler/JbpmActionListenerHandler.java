/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.LazyJbpmActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;

/**
 *
 */
@TldTag (
    name = "jbpmActionListener",
    description = "Call a user-defined action, providing the action with a JbpmJsfContext.",
    attributes = {
        @TldAttribute (
            name = "type",
            description = "The type of the action.  Can be an EL expression which resolves to a literal Class object, "
                + "or the name of a class as a String.  Either this or the 'listener' attribute must be given.",
            deferredType = Object.class
        ),
        @TldAttribute (
            name = "listener",
            description = "An EL expression that resolves to the listener to execute.  Either this or the "
                + "'type' attribute must be given.",
            deferredType = JbpmActionListener.class
        )
    }
)
public final class JbpmActionListenerHandler extends AbstractHandler {
    private final TagAttribute typeTagAttribute;
    private final TagAttribute listenerTagAttribute;

    public JbpmActionListenerHandler(final TagConfig config) {
        super(config);
        typeTagAttribute = getAttribute("type");
        listenerTagAttribute = getAttribute("listener");
        if ((typeTagAttribute == null) == (listenerTagAttribute == null)) {
            throw new TagException(tag, "Exactly one of the \"type\" or \"listener\" attributes must be given");
        }
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LazyJbpmActionListener(
            getValueExpression(typeTagAttribute, ctx, Object.class),
            getValueExpression(typeTagAttribute, ctx, JbpmActionListener.class)
        );
    }
}
