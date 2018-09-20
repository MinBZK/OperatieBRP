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
 * List tasks handler.
 */
@TldTag(name = "listTasks", description = "Read a list of task instances from the database.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the task instance list should be stored.",
                              required = true, deferredType = List.class),
                      @TldAttribute(name = "includeEnded", description = "A flag specifying whether ended tasks should be included.",
                              deferredType = boolean.class) })
public final class ListTasksHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute includeEndedTagAttribute;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     */
    public ListTasksHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        includeEndedTagAttribute = getAttribute("includeEnded");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTasksActionListener(getValueExpression(includeEndedTagAttribute, ctx, Boolean.class), getValueExpression(
            targetTagAttribute,
            ctx,
            List.class));
    }
}
