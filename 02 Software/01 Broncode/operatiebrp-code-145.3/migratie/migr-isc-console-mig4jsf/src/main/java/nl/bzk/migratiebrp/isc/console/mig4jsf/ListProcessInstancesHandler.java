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
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * Handler voor list process instances tag.
 */
@TldTag(name = "listProcessInstances", description = "Read a list of process instances from a process definition.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the process instance list should be stored.",
                required = true, deferredType = List.class),
                @TldAttribute(name = "processId", description = "The numeric ID of the process definition whose instances are to be read.",
                        required = false, deferredType = long.class),
                @TldAttribute(name = "pager", description = "An EL expression of the PagerBean to be used.", required = true,
                        deferredType = PagerBean.class),
                @TldAttribute(name = "filter", description = "An EL expression of the filter to be used.", required = true,
                        deferredType = Filter.class)})
public final class ListProcessInstancesHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processIdTagAttribute;
    private final TagAttribute pagerTagAttribute;
    private final TagAttribute filterTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * @param config Tag configuratie waarin o.a. migratie tags staan.
     */
    public ListProcessInstancesHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processIdTagAttribute = getAttribute("processId");
        pagerTagAttribute = getRequiredAttribute("pager");
        filterTagAttribute = getAttribute("filter");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        final ValueExpression processIdExpression = getValueExpression(processIdTagAttribute, ctx, Long.class);
        final ValueExpression pagerExpression = getValueExpression(pagerTagAttribute, ctx, PagerBean.class);
        final ValueExpression targetExpression = getValueExpression(targetTagAttribute, ctx, List.class);
        final ValueExpression filterExpression = getValueExpression(filterTagAttribute, ctx, Filter.class);
        return new ListProcessInstancesActionListener(processIdExpression, pagerExpression, targetExpression, filterExpression);
    }
}
