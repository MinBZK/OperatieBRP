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

/**
 * synchronisatievraag handler.
 */
@TldTag(name = "synchronisatievraag", description = "Versturen synchronisatievraag.",
        attributes = {@TldAttribute(name = "gemeente", description = "The gemeente to which the synchronisatievraag should be sent."),
                @TldAttribute(name = "aNummer", description = "The anummer of the persoon to request a synchonisatievraag for."),
                @TldAttribute(name = "bulkBestand", description = "The bulk bestand containing multiple synchronisatievragen."),
                @TldAttribute(name = "target", description = "The target for the synchronisatievraag.")})
public final class SynchronisatievraagHandler extends AbstractHandler {
    private final TagAttribute gemeenteTagAttribute;
    private final TagAttribute aNummerTagAttribute;
    private final TagAttribute bulkBestandTagAttribute;
    private final TagAttribute targetTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public SynchronisatievraagHandler(final TagConfig config) {
        super(config);
        gemeenteTagAttribute = getAttribute("gemeente");
        aNummerTagAttribute = getAttribute("aNummer");
        bulkBestandTagAttribute = getAttribute("bulkBestand");
        targetTagAttribute = getAttribute("target");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        final ValueExpression gemeenteExpression = getValueExpression(gemeenteTagAttribute, ctx, String.class);
        final ValueExpression aNummerExpression = getValueExpression(aNummerTagAttribute, ctx, String.class);
        final ValueExpression bulkBestandExpression = getValueExpression(bulkBestandTagAttribute, ctx, Object.class);
        final ValueExpression targetExpression = getValueExpression(targetTagAttribute, ctx, Object.class);
        return new SynchronisatievraagActionListener(gemeenteExpression, aNummerExpression, bulkBestandExpression, targetExpression);
    }
}
