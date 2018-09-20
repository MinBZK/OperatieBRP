/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import java.util.List;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ListJobsActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "listJobs",
    description = "List all jobs in the database.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the list will be stored.",
            required = true,
            deferredType = List.class
        )
    }
)
public final class ListJobsHandler extends AbstractHandler {
    private final TagAttribute targetAttribute;

    public ListJobsHandler(final TagConfig config) {
        super(config);
        targetAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListJobsActionListener(
            getValueExpression(targetAttribute, ctx, List.class)
        );
    }
}
