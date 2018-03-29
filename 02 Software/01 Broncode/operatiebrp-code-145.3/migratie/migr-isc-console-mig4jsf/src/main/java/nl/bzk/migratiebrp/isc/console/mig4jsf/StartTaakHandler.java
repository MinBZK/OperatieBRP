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

/**
 * Handler voor het starten van een taak.
 */
@TldTag(
        name = "startTaak",
        description = "Mark a task as started.",
        attributes = {@TldAttribute(name = "task", description = "The task to mark as started.", required = true, deferredType = Long.class),
                @TldAttribute(name = "actorId",
                        description = "The actor to assign the task to.  If not given, the current actor is not changed.",
                        deferredType = String.class)})
public final class StartTaakHandler extends AbstractHandler {
    private final TagAttribute instanceTagAttribute;
    private final TagAttribute actorIdTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * @param config Tag configuratie waarin o.a. migratie tags staan.
     */
    public StartTaakHandler(final TagConfig config) {
        super(config);
        instanceTagAttribute = getRequiredAttribute("task");
        actorIdTagAttribute = getAttribute("actorId");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new StartTaakActionListener(getValueExpression(instanceTagAttribute, ctx, Long.class), getValueExpression(
                actorIdTagAttribute,
                ctx,
                String.class));
    }
}
