/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import java.util.List;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.GetProcessLogsActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "getProcessLogs",
    description = "Get the process logs from a process instance.",
    attributes = {
        @TldAttribute (
            name = "processInstance",
            description = "The process instance whose process logs are to be read.",
            required = true,
            deferredType = ProcessInstance.class
        ),
        @TldAttribute (
            name = "type",
            description = "The process log type.  This may be a literal Class object, or the name of a class, "
                + "or one of the following shortcut names:<ul>"
                + "<li><code>variable</code></li>"
                + "<li><code>variableUpdate</code></li>"
                + "<li><code>variableCreate</code></li>"
                + "<li><code>variableDelete</code></li>"
                + "<li><code>action</code></li>"
                + "<li><code>node</code></li>"
                + "<li><code>processInstanceCreate</code></li>"
                + "<li><code>processInstanceEnd</code></li>"
                + "<li><code>processState</code></li>"
                + "<li><code>signal</code></li>"
                + "<li><code>tokenCreate</code></li>"
                + "<li><code>tokenEnd</code></li>"
                + "<li><code>transition</code></li>"
                + "<li><code>composite</code></li>"
                + "<li><code>message</code></li>"
                + "<li><code>swimlane</code></li>"
                + "<li><code>swimlaneAssign</code></li>"
                + "<li><code>swimlaneCreate</code></li>"
                + "<li><code>task</code></li>"
                + "<li><code>taskAssign</code></li>"
                + "<li><code>taskCreate</code></li>"
                + "<li><code>taskEnd</code></li>"
                + "</ul>",
            deferredType = Object.class
        )
    }
)
public final class GetProcessLogsHandler extends AbstractHandler {

    private final TagAttribute processInstanceTagAttribute;
    private final TagAttribute typeTagAttribute;
    private final TagAttribute targetTagAttribute;

    public GetProcessLogsHandler(final TagConfig config) {
        super(config);
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
        typeTagAttribute = getAttribute("type");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new GetProcessLogsActionListener(
            getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class),
            getValueExpression(typeTagAttribute, ctx, Object.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
