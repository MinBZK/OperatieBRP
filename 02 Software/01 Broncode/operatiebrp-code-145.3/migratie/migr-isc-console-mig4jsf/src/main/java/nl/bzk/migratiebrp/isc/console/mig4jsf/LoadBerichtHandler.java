/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;

/**
 * Laad bericht handler.
 */
@TldTag(
        name = "loadBericht",
        description = "Read a berihte from the database.",
        attributes = {@TldAttribute(name = "id", description = "The ID of the bericht to load.", required = true, deferredType = long.class),
                @TldAttribute(name = "target", description = "An EL expression into which the process instance should be stored.",
                        required = true, deferredType = Bericht.class)})
public final class LoadBerichtHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute targetTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public LoadBerichtHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        targetTagAttribute = getRequiredAttribute("target");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadBerichtActionListener(getValueExpression(idTagAttribute, ctx, Long.class), getValueExpression(
                targetTagAttribute,
                ctx,
                Bericht.class));
    }
}
