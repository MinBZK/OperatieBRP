/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.SuspendActionListener;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

/**
 *
 */
@TldTag (
    name = "suspend",
    description = "Suspend a running task, token, or process instance.",
    attributes = {
        @TldAttribute (
            name = "value",
            description = "The item to suspend.",
            required = true,
            deferredType = Object.class
        )
    }
)
public final class SuspendHandler extends AbstractHandler {
    private final TagAttribute valueTagAttribute;

    public SuspendHandler(final TagConfig config) {
        super(config);
        valueTagAttribute = getRequiredAttribute("value");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new SuspendActionListener(
            getValueExpression(valueTagAttribute, ctx, Object.class)
        );
    }
}
