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
import org.jbpm.jsf.core.action.DeployProcessActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "deployProcess",
    description = "Deploy a new process archive.",
    attributes = {
        @TldAttribute(
            name = "archive",
            description = "The source of the archive, as an input stream or a byte array.",
            required = true,
            deferredType = Object.class
        ),
        @TldAttribute(
            name = "target",
            description = "An EL expression into which the new process definition will be inserted.",
            deferredType = ProcessDefinition.class
        )
    }
)
public final class DeployProcessHandler extends AbstractHandler {
    private final TagAttribute archiveTagAttribute;
    private final TagAttribute targetTagAttribute;

    public DeployProcessHandler(final TagConfig config) {
        super(config);
        archiveTagAttribute = getRequiredAttribute("archive");
        targetTagAttribute = getAttribute("target");

    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new DeployProcessActionListener(
            getValueExpression(archiveTagAttribute, ctx, Object.class),
            getValueExpression(targetTagAttribute, ctx, ProcessDefinition.class)
        );
    }
}
