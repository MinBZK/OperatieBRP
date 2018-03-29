/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Handler voor het unassignen van een taak.
 */
@TldTag(name = "unassignTask", description = "Unassign a task.", attributes = {@TldAttribute(name = "task",
        description = "The task instance to unassign.", required = true, deferredType = TaskInstance.class)})
public final class UnassignTaskHandler extends AbstractHandler {
    private final TagAttribute taskTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public UnassignTaskHandler(final TagConfig config) {
        super(config);
        taskTagAttribute = getRequiredAttribute("task");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new UnassignTaskActionListener(getValueExpression(taskTagAttribute, ctx, TaskInstance.class));
    }
}
