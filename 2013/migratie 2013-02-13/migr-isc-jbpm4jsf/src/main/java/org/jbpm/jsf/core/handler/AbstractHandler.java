/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import java.io.IOException;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.impl.JbpmActionListenerWrapper;
import org.jboss.gravel.common.annotation.TldAttributes;
import org.jboss.gravel.common.annotation.TldAttribute;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;

/**
 *
 */
@TldAttributes ({
    @TldAttribute (
        name = "unless",
        description = "A boolean expression that, if true, will prevent this action from being run.",
        deferredType = boolean.class
    ),
    @TldAttribute (
        name = "navigate",
        description = "A boolean expression that can be used to control whether navigation will be attempted after this " +
            "action completes.",
        deferredType = boolean.class
    ),
    @TldAttribute (
        name = "name",
        description = "A different name to use for this action.  Action names are used by some navigation handlers."
    )
})
public abstract class AbstractHandler extends TagHandler {
    public static final String JBPM_JSF_CONTEXT_KEY = "org.jbpm.jsf.CONTEXT";

    private final TagAttribute unlessAttribute;
    private final TagAttribute navigateAttribute;
    private final TagAttribute nameAttribute;

    public AbstractHandler(final TagConfig config) {
        super(config);
        unlessAttribute = getAttribute("unless");
        navigateAttribute = getAttribute("navigate");
        nameAttribute = getAttribute("name");
    }

    public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, ELException {
        if (! (parent instanceof ActionSource)) {
            throw new TagException(tag, "Parent component is not an ActionSource");
        }
        if (ComponentSupport.isNew(parent)) {
            ((ActionSource) parent).addActionListener(
                new JbpmActionListenerWrapper(
                    getListener(ctx),
                    getValueExpression(unlessAttribute, ctx, Boolean.class),
                    getValueExpression(navigateAttribute, ctx, Boolean.class),
                    getValueExpression(nameAttribute, ctx, String.class)
                )
            );
        }
    }

    protected ValueExpression getValueExpression(TagAttribute tagAttribute, FaceletContext context, Class<?> type) {
        return tagAttribute == null ? null : tagAttribute.getValueExpression(context, type);
    }

    protected abstract JbpmActionListener getListener(final FaceletContext ctx);
}
