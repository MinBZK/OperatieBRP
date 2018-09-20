/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.DeleteActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "delete",
    description = "Delete a process, process instance, or job.",
    attributes = {
        @TldAttribute (
            name = "value",
            description = "The item to delete.",
            required = true,
            deferredType = Object.class
        )
    }
)
public final class DeleteHandler extends AbstractHandler {
    private final TagAttribute processTagAttribute;

    public DeleteHandler(final TagConfig config) {
        super(config);
        processTagAttribute = getRequiredAttribute("value");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new DeleteActionListener(
            getValueExpression(processTagAttribute, ctx, Object.class)
        );
    }
}
