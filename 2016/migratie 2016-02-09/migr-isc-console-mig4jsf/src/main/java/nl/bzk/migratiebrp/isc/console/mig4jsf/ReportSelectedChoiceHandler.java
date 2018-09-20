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
import org.jbpm.jsf.core.impl.UpdatesHashMap;

/**
 * Rapporteerd geselecteerd keuze handler.
 */
@TldTag(name = "reportSelectedChoice", description = "Report the selected option from a task", attributes = {@TldAttribute(
        name = "variableMap", description = "The variable map", required = true, deferredType = UpdatesHashMap.class) })
public final class ReportSelectedChoiceHandler extends AbstractHandler {
    private final TagAttribute variableMapTagAttribute;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     */
    public ReportSelectedChoiceHandler(final TagConfig config) {
        super(config);
        variableMapTagAttribute = getRequiredAttribute("variableMap");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ReportSelectedChoiceActionListener(getValueExpression(variableMapTagAttribute, ctx, UpdatesHashMap.class));
    }
}
