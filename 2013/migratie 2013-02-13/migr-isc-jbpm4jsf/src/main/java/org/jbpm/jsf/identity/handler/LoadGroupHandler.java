/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.handler;

import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.identity.action.LoadGroupActionListener;
import org.jbpm.identity.Group;
import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;

/**
 *
 */
@TldTag (
    name = "loadGroup",
    description = "Read a group from the database.",
    attributes = {
        @TldAttribute (
            name = "id",
            description = "The ID of the group to load.",
            required = true,
            deferredType = long.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the group should be stored.",
            required = true,
            deferredType = Group.class
        )
    }
)
public final class LoadGroupHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute targetTagAttribute;

    public LoadGroupHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadGroupActionListener(
            getValueExpression(idTagAttribute, ctx, Long.class),
            getValueExpression(targetTagAttribute, ctx, Group.class)
        );
    }
}
