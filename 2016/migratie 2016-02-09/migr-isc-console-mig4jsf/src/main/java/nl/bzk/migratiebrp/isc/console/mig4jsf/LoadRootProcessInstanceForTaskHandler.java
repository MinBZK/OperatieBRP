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
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * Laad root process instance voor taak handler.
 */
@TldTag(name = "loadProcessInstanceForTask", description = "Read the root process instance for a task.",
        attributes = {@TldAttribute(name = "taskId", description = "The ID of the task to load the root process instance for.", required = true,
                              deferredType = long.class),
                      @TldAttribute(name = "target", description = "An EL expression into which the process instance should be stored.", required = true,
                              deferredType = ProcessInstance.class) })
public final class LoadRootProcessInstanceForTaskHandler extends AbstractHandler {
    private final TagAttribute taskIdTagAttribute;
    private final TagAttribute targetTagAttribute;

    /**
     * Constructor.
     *
     * @param config
     *            config
     */
    public LoadRootProcessInstanceForTaskHandler(final TagConfig config) {
        super(config);
        taskIdTagAttribute = getRequiredAttribute("taskId");
        targetTagAttribute = getRequiredAttribute("target");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadRootProcessInstanceForTaskActionListener(getValueExpression(taskIdTagAttribute, ctx, Long.class), getValueExpression(
            targetTagAttribute,
            ctx,
            ProcessInstance.class));
    }
}
