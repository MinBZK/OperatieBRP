/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.util.List;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/** 
 *
 */
@TldTag(name = "listTransitionsForProcessInstance",
        description = "Read a log of transitions from a process instance.", attributes = {
                @TldAttribute(name = "target",
                        description = "An EL expression into which the transition log should be stored.",
                        required = true, deferredType = List.class),
                @TldAttribute(name = "processInstance",
                        description = "The process instance whose task instances are to be read.", required = true,
                        deferredType = ProcessInstance.class) })
public final class ListTransitionsForProcessInstanceHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processInstanceTagAttribute;

    public ListTransitionsForProcessInstanceHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTransitionsForProcessInstanceActionListener(getValueExpression(processInstanceTagAttribute,
                ctx, ProcessInstance.class), getValueExpression(targetTagAttribute, ctx, List.class));
    }
}
