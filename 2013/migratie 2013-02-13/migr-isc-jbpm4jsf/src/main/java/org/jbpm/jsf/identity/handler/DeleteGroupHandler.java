/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.identity.Group;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.identity.action.DeleteGroupActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "deleteGroup",
    description = "Delete a group from the database.",
    attributes = {
        @TldAttribute (
            name = "group",
            description = "The group to delete.",
            required = true,
            deferredType = Group.class
        )
    }
)
public final class DeleteGroupHandler extends AbstractHandler {
    private final TagAttribute groupAttribute;

    public DeleteGroupHandler(final TagConfig config) {
        super(config);
        groupAttribute = getRequiredAttribute("group");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new DeleteGroupActionListener(
            getValueExpression(groupAttribute, ctx, Group.class)
        );
    }
}
