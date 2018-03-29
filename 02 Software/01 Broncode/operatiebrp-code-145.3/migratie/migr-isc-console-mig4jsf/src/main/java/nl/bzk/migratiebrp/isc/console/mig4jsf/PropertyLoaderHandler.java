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
 * Lees property uit config bestand handler.
 */
@TldTag(name = "propertyLoader", description = "Read a property.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the information should be stored.", required = true,
                deferredType = String.class),
                @TldAttribute(name = "property", description = "An EL expression of the property to be read.", required = true,
                        deferredType = String.class)})
public final class PropertyLoaderHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute propertyTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public PropertyLoaderHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        propertyTagAttribute = getRequiredAttribute("property");
    }

    @Override
    protected JbpmActionListener getListener(final FaceletContext ctx) {
        final ValueExpression propertyExpression = getValueExpression(propertyTagAttribute, ctx, String.class);
        final ValueExpression targetExpression = getValueExpression(targetTagAttribute, ctx, String.class);
        return new PropertyLoaderActionListener(propertyExpression, targetExpression);
    }
}
