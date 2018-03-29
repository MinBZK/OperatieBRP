/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

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
 * Handler voor bepaalEsbNaam tag.
 */
@TldTag(name = "bepaalEsbNaam", description = "Bepaal de ESB naam",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the filter should be stored.",
                required = true, deferredType = String.class),
                @TldAttribute(name = "server", description = "An EL expression referring to the jmx server", required = true,
                        deferredType = JmxServer.class),
                @TldAttribute(name = "prefix", description = "An EL expression containing the prefix", required = true,
                        deferredType = String.class)})
public final class BepaalEsbNaamHandler extends TagHandler {

    private final TagAttribute targetTagAttribute;
    private final TagAttribute serverTagAttribute;
    private final TagAttribute prefixTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * @param config Tag configuratie waarin o.a. migratie tags staan.
     */
    public BepaalEsbNaamHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        serverTagAttribute = getRequiredAttribute("server");
        prefixTagAttribute = getRequiredAttribute("prefix");
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
        return new BepaalEsbNaamActionListener(getValueExpression(targetTagAttribute, ctx, Object.class), getValueExpression(
                serverTagAttribute,
                ctx,
                JmxServer.class), getValueExpression(prefixTagAttribute, ctx, String.class));
    }

    private ValueExpression getValueExpression(final TagAttribute tagAttribute, final FaceletContext context, final Class<?> clazz) {
        return tagAttribute == null ? null : tagAttribute.getValueExpression(context, clazz);
    }

}
