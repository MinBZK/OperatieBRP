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
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * Handler voor list jobs tag.
 */
@TldTag(name = "listJobs", description = "Read a list of jobs.",
        attributes = {@TldAttribute(name = "target",
                              description = "An EL expression into which the process instance list should be stored.", required = true,
                              deferredType = List.class),
                      @TldAttribute(name = "pager", description = "An EL expression of the pager to be used.", required = true,
                              deferredType = PagerBean.class),
                      @TldAttribute(name = "viewMode", description = "An EL expression of the viewMode to be used.", required = true,
                              deferredType = String.class) })
public final class ListJobsHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute pagerTagAttribute;
    private final TagAttribute viewModeTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * 
     * @param config
     *            Tag configuratie waarin o.a. migratie tags staan.
     */
    public ListJobsHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        pagerTagAttribute = getRequiredAttribute("pager");
        viewModeTagAttribute = getAttribute("viewMode");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListJobsActionListener(getValueExpression(pagerTagAttribute, ctx, PagerBean.class), getValueExpression(
            targetTagAttribute,
            ctx,
            List.class), getValueExpression(viewModeTagAttribute, ctx, String.class));
    }
}
