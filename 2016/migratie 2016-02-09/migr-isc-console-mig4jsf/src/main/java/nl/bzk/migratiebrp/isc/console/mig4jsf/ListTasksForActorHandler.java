/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import java.util.List;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * List tasks voor actor handler.
 */
@TldTag(name = "listTasksForActor", description = "Read a list of task instances assigned to an actor ID.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the task instance list should be stored.",
                              required = true, deferredType = List.class),
                      @TldAttribute(name = "actorId", description = "The actor ID whose task instances are to be read.", required = true) })
public final class ListTasksForActorHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute actorIdTagAttribute;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     */
    public ListTasksForActorHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        actorIdTagAttribute = getRequiredAttribute("actorId");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTasksForActorActionListener(getValueExpression(
            targetTagAttribute,
            ctx,
            List.class));
    }
}
