/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.LoadProcessActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "loadProcess",
    description = "Read a process definition from the database.",
    attributes = {
        @TldAttribute (
            name = "id",
            description = "The ID of the process definition to load.",
            required = true,
            deferredType = long.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the process definition should be stored.",
            required = true,
            deferredType = ProcessDefinition.class
        )
    }
)
public final class LoadProcessHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute targetTagAttribute;

    public LoadProcessHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadProcessActionListener(
            getValueExpression(idTagAttribute, ctx, Long.class),
            getValueExpression(targetTagAttribute, ctx, ProcessDefinition.class)
        );
    }
}
