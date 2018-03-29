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
 * Handler voor process instances filter tag.
 */
@TldTag(name = "processInstancesFilter", description = "Create a filter for process instances.",
        attributes = {@TldAttribute(name = "target", description = "An EL expression into which the filter should be stored.",
                required = true, deferredType = Filter.class),
                @TldAttribute(name = "key", description = "The external key of the process instances to be filtered.",
                        required = false, deferredType = String.class),
                @TldAttribute(name = "startDate", description = "The start date of the process instances who are to be read.",
                        required = false, deferredType = String.class),
                @TldAttribute(name = "running", description = "True to display running process instances.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "suspended", description = "True to display suspended process instances.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "ended", description = "True to display ended process instances.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "anummer", description = "Gerelateerde a-nummer process instances.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "partij", description = "Gerelateerde partij process instances.", required = false,
                        deferredType = String.class),
                @TldAttribute(name = "administratieveHandelingId", description = "Administratieve handeling process instances.",
                        required = false, deferredType = String.class),
                @TldAttribute(name = "processInstanceId", description = "Id process instances.",
                        required = false, deferredType = String.class),
                @TldAttribute(name = "processDefinition", description = "Processdefinition of process instances.",
                        required = false, deferredType = String.class),
                @TldAttribute(name = "root", description = "True to display only root process instances.", required = false,
                        deferredType = Boolean.class)})
public final class ProcessInstancesFilterHandler extends TagHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute keyTagAttribute;
    private final TagAttribute startDateTagAttribute;
    private final TagAttribute runningTagAttribute;
    private final TagAttribute suspendedTagAttribute;
    private final TagAttribute endedTagAttribute;
    private final TagAttribute anummerTagAttribute;
    private final TagAttribute partijTagAttribute;
    private final TagAttribute ahIdTagAttribute;
    private final TagAttribute processInstanceIdTagAttribute;
    private final TagAttribute processDefinitionTagAttribute;
    private final TagAttribute rootTagAttribute;

    /**
     * Constructor waarbij de tag configuratie meegegeven kan worden.
     * @param config Tag configuratie waarin o.a. migratie tags staan.
     */
    public ProcessInstancesFilterHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");

        keyTagAttribute = getAttribute("key");
        startDateTagAttribute = getAttribute("startDate");
        runningTagAttribute = getAttribute("running");
        suspendedTagAttribute = getAttribute("suspended");
        endedTagAttribute = getAttribute("ended");
        anummerTagAttribute = getAttribute("anummer");
        partijTagAttribute = getAttribute("partij");
        ahIdTagAttribute = getAttribute("administratieveHandelingId");
        processInstanceIdTagAttribute = getAttribute("processInstanceId");
        processDefinitionTagAttribute = getAttribute("processDefinition");
        rootTagAttribute = getAttribute("root");
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
        return new ProcessInstancesFilterActionListener(
                getValueExpression(targetTagAttribute, ctx, Filter.class),
                getValueExpression(keyTagAttribute, ctx, String.class),
                getValueExpression(startDateTagAttribute, ctx, String.class),
                getValueExpression(runningTagAttribute, ctx, Boolean.class),
                getValueExpression(suspendedTagAttribute, ctx, Boolean.class),
                getValueExpression(endedTagAttribute, ctx, Boolean.class),
                getValueExpression(anummerTagAttribute, ctx, String.class),
                getValueExpression(partijTagAttribute, ctx, String.class),
                getValueExpression(ahIdTagAttribute, ctx, String.class),
                getValueExpression(processInstanceIdTagAttribute, ctx, String.class),
                getValueExpression(processDefinitionTagAttribute, ctx, String.class),
                getValueExpression(rootTagAttribute, ctx, Boolean.class));
    }

    private ValueExpression getValueExpression(final TagAttribute tagAttribute, final FaceletContext context, final Class<?> clazz) {
        return tagAttribute == null ? null : tagAttribute.getValueExpression(context, clazz);
    }

}
