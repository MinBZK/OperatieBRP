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
 * Resume proces instance handler.
 */
@TldTag(name = "resumeProcessInstance", description = "Resume a process instance.", attributes = {@TldAttribute(name = "processInstance",
        description = "The process instance to resume.", required = true, deferredType = ProcessInstance.class) })
public final class ResumeProcessInstanceHandler extends AbstractHandler {
    private final TagAttribute processInstanceTagAttribute;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     */
    public ResumeProcessInstanceHandler(final TagConfig config) {
        super(config);
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ResumeProcessInstanceActionListener(getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class));
    }
}
