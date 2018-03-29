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
import javax.el.ValueExpression;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * List gerelateerd informatie voor proces instance handler.
 */
@TldTag(name = "listGerelateerdeGegevensForProcessInstance", description = "Read a list of related information from a process instance.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the information list should be stored.", required = true,
                deferredType = List.class),
                @TldAttribute(name = "pager", description = "An EL expression of the PagerBean to be used.", required = true,
                        deferredType = PagerBean.class),
                @TldAttribute(name = "processInstance", description = "The process instance whose related information is to be read.",
                        required = true, deferredType = ProcessInstance.class)})
public final class ListGerelateerdeGegevensForProcessInstanceHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute pagerTagAttribute;
    private final TagAttribute processInstanceTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public ListGerelateerdeGegevensForProcessInstanceHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        pagerTagAttribute = getRequiredAttribute("pager");
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        final ValueExpression processInstanceExpression = getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class);
        final ValueExpression pagerExpression = getValueExpression(pagerTagAttribute, ctx, PagerBean.class);
        final ValueExpression targetExpression = getValueExpression(targetTagAttribute, ctx, List.class);
        return new ListGerelateerdeGegevensForProcessInstanceActionListener(processInstanceExpression, pagerExpression, targetExpression);
    }
}
