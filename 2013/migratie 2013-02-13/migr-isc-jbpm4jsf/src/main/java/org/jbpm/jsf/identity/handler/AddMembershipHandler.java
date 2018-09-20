/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.handler;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jbpm.identity.User;
import org.jbpm.identity.Group;
import org.jbpm.identity.Membership;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.identity.action.AddMembershipActionListener;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

/**
 *
 */
@TldTag (
    name = "addMembership",
    description = "Add a group membership to a user.",
    attributes = {
        @TldAttribute (
            name = "user",
            required = true,
            description = "The user to whom the membership is granted.",
            deferredType = User.class
        ),
        @TldAttribute (
            name = "group",
            required = true,
            description = "The group that the user should become a member of.",
            deferredType = Group.class
        ),
        @TldAttribute (
            name = "role",
            description = "The name of the role."
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the created membership should be stored.",
            deferredType = Membership.class
        )
    }
)
public final class AddMembershipHandler extends AbstractHandler {

    private final TagAttribute userTagAttribute;
    private final TagAttribute groupTagAttribute;
    private final TagAttribute roleTagAttribute;
    private final TagAttribute targetTagAttribute;

    public AddMembershipHandler(final TagConfig config) {
        super(config);
        userTagAttribute = getRequiredAttribute("user");
        groupTagAttribute = getRequiredAttribute("group");
        roleTagAttribute = getAttribute("role");
        targetTagAttribute = getAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new AddMembershipActionListener(
            getValueExpression(userTagAttribute, ctx, User.class),
            getValueExpression(groupTagAttribute, ctx, Group.class),
            getValueExpression(roleTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, Membership.class)
        );
    }
}
