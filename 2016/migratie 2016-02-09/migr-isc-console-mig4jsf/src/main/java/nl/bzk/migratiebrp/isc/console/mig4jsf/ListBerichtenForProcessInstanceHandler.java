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
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * List berichten voor proces instance handler.
 */
@TldTag(name = "listBerichtenForProcessInstance", description = "Read a list of berichten from a process instance.",
        attributes = {@TldAttribute(name = "target",
                              description = "An EL expression into which the berichten instance list should be stored.", required = true,
                              deferredType = List.class),
                      @TldAttribute(name = "processInstance", description = "The process instance whose task instances are to be read.",
                              required = true, deferredType = ProcessInstance.class),
                      @TldAttribute(name = "pager", description = "An EL expression of the PagerBean to be used.", required = true,
                              deferredType = PagerBean.class),
                      @TldAttribute(name = "filter", description = "An EL expression of the filter to be used.", required = true,
                              deferredType = Filter.class) })
public final class ListBerichtenForProcessInstanceHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processInstanceTagAttribute;
    private final TagAttribute pagerTagAttribute;
    private final TagAttribute filterTagAttribute;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     */
    public ListBerichtenForProcessInstanceHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processInstanceTagAttribute = getRequiredAttribute("processInstance");
        pagerTagAttribute = getRequiredAttribute("pager");
        filterTagAttribute = getAttribute("filter");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListBerichtenForProcessInstanceActionListener(
            getValueExpression(processInstanceTagAttribute, ctx, ProcessInstance.class),
            getValueExpression(targetTagAttribute, ctx, List.class),
            getValueExpression(pagerTagAttribute, ctx, PagerBean.class),
            getValueExpression(filterTagAttribute, ctx, Filter.class));
    }
}
