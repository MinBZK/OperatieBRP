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
import org.jbpm.jsf.identity.action.LoadGroupByNameActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "loadGroupByName",
    description = "Read a group from the database by name.",
    attributes = {
        @TldAttribute (
            name = "groupName",
            description = "The name of the group to load.",
            required = true
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the group should be stored.",
            required = true,
            deferredType = Group.class
        )
    }
)
public final class LoadGroupByNameHandler extends AbstractHandler {
    private final TagAttribute groupNameTagAttribute;
    private final TagAttribute targetTagAttribute;

    public LoadGroupByNameHandler(final TagConfig config) {
        super(config);
        groupNameTagAttribute = getRequiredAttribute("groupName");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadGroupByNameActionListener(
            getValueExpression(groupNameTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, Group.class)
        );
    }
}
