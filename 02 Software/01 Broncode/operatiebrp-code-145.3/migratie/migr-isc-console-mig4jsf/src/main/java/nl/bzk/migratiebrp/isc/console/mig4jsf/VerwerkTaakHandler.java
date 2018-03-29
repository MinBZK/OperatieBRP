/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import javax.el.ValueExpression;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.core.impl.UpdatesHashMap;

/**
 * Handler voor het verwerken van een taak.
 */
@TldTag(
        name = "verwerkTaak",
        description = "Verwerk een taak.",
        attributes = {@TldAttribute(name = "id", description = "The id of the task to complete.", required = true, deferredType = Long.class),
                @TldAttribute(name = "transition", description = "The transition to take.", required = true, deferredType = String.class),
                @TldAttribute(name = "comment", description = "An EL expression that returns the comment text to add.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "variableMap", description = "The variable map to apply.", required = true, deferredType = UpdatesHashMap.class)})
public final class VerwerkTaakHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute transitionTagAttribute;
    private final TagAttribute commentTagAttribute;
    private final TagAttribute variableMapTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * @param config Tag configuratie waarin o.a. migratie tags staan.
     */
    public VerwerkTaakHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        transitionTagAttribute = getRequiredAttribute("transition");
        commentTagAttribute = getAttribute("comment");
        variableMapTagAttribute = getRequiredAttribute("variableMap");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        final ValueExpression idExpression = getValueExpression(idTagAttribute, ctx, Long.class);
        final ValueExpression transitionExpression = getValueExpression(transitionTagAttribute, ctx, String.class);
        final ValueExpression commentExpression = getValueExpression(commentTagAttribute, ctx, String.class);
        final ValueExpression variableMapExpression = getValueExpression(variableMapTagAttribute, ctx, UpdatesHashMap.class);
        return new VerwerkTaakActionListener(idExpression, transitionExpression, commentExpression, variableMapExpression);
    }
}
