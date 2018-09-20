/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionListener;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;

/**
 * Handler voor berichten filter tag.
 */
@TldTag(
        name = "berichtenFilter",
        description = "Create a filter for berichten.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the filter should be stored.",
                              required = true, deferredType = Filter.class),
                      @TldAttribute(name = "kanaal", description = "The kanaal to be filtered.", required = false,
                              deferredType = String.class),
                      @TldAttribute(name = "richting", description = "The richting to be filtered.", required = false,
                              deferredType = String.class),
                      @TldAttribute(name = "bron", description = "The bron to be filtered.", required = false, deferredType = String.class),
                      @TldAttribute(name = "doel", description = "The doel to be filtered.", required = false, deferredType = String.class),
                      @TldAttribute(name = "type", description = "The type to be filtered.", required = false, deferredType = String.class),
                      @TldAttribute(name = "berichtId", description = "The bericht id to be filtered.", required = false,
                              deferredType = String.class),
                      @TldAttribute(name = "correlatieId", description = "The correlatie id to be filtered.", required = false,
                              deferredType = String.class) })
public final class BerichtenFilterHandler extends TagHandler {
    private final TagAttribute targetTagAttribute;

    private final TagAttribute kanaalTagAttribute;
    private final TagAttribute richtingTagAttribute;
    private final TagAttribute bronTagAttribute;
    private final TagAttribute doelTagAttribute;
    private final TagAttribute typeTagAttribute;
    private final TagAttribute berichtIdTagAttribute;
    private final TagAttribute correlatieIdTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * 
     * @param config
     *            Tag configuratie waarin o.a. migratie tags staan.
     */
    public BerichtenFilterHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        kanaalTagAttribute = getAttribute("kanaal");
        richtingTagAttribute = getAttribute("richting");
        bronTagAttribute = getAttribute("bron");
        doelTagAttribute = getAttribute("doel");
        typeTagAttribute = getAttribute("type");
        berichtIdTagAttribute = getAttribute("berichtId");
        correlatieIdTagAttribute = getAttribute("correlatieId");
    }

    @Override
    public void apply(final FaceletContext ctx, final UIComponent parent) {
        if (!(parent instanceof ActionSource)) {
            throw new TagException(tag, "Parent component is not an ActionSource");
        }
        if (ComponentSupport.isNew(parent)) {
            ((ActionSource) parent).addActionListener(getListener(ctx));
        }
    }

    private ActionListener getListener(final FaceletContext ctx) {
        return new BerichtenFilterActionListener(
            getValueExpression(targetTagAttribute, ctx, Filter.class),
            getValueExpression(kanaalTagAttribute, ctx, String.class),
            getValueExpression(richtingTagAttribute, ctx, String.class),
            getValueExpression(bronTagAttribute, ctx, String.class),
            getValueExpression(doelTagAttribute, ctx, String.class),
            getValueExpression(typeTagAttribute, ctx, String.class),
            getValueExpression(berichtIdTagAttribute, ctx, String.class),
            getValueExpression(correlatieIdTagAttribute, ctx, String.class));
    }

    private ValueExpression getValueExpression(final TagAttribute tagAttribute, final FaceletContext context, final Class<?> clazz) {
        return tagAttribute == null ? null : tagAttribute.getValueExpression(context, clazz);
    }

}
