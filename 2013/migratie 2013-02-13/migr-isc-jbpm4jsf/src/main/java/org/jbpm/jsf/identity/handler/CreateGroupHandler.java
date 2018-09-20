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
import org.jbpm.jsf.identity.action.CreateGroupActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "createGroup",
    description = "Create a new group and save it in the database.",
    attributes = {
        @TldAttribute (
            name = "groupName",
            required = true,
            description = "The name of the group to create."
        ),
        @TldAttribute (
            name = "parentGroup",
            description = "The parent group, if any.",
            deferredType = Group.class
        ),
        @TldAttribute (
            name = "type",
            description = "The type of the new group."
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the created group should be stored.",
            deferredType = Group.class
        )
    }
)
public final class CreateGroupHandler extends AbstractHandler {
    private final TagAttribute groupNameTagAttribute;
    private final TagAttribute parentGroupTagAttribute;
    private final TagAttribute typeTagAttribute;
    private final TagAttribute targetTagAttribute;

    public CreateGroupHandler(final TagConfig config) {
        super(config);
        groupNameTagAttribute = getRequiredAttribute("groupName");
        parentGroupTagAttribute = getAttribute("parentGroup");
        typeTagAttribute = getRequiredAttribute("type");
        targetTagAttribute = getAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new CreateGroupActionListener(
            getValueExpression(groupNameTagAttribute, ctx, String.class),
            getValueExpression(parentGroupTagAttribute, ctx, Group.class),
            getValueExpression(typeTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, Group.class)
        );
    }
}
