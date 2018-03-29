/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.impl.UpdatesHashMap;

/**
 * Rapporteerd geselecteerd keuze.
 */
public final class ReportSelectedChoiceActionListener extends AbstractActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ValueExpression variableMapExpression;

    /**
     * Constructor.
     * @param variableMapExpression variable map expression
     */
    public ReportSelectedChoiceActionListener(final ValueExpression variableMapExpression) {
        super("reportSelectedChoice");
        this.variableMapExpression = variableMapExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final UpdatesHashMap updatesHashMap = (UpdatesHashMap) variableMapExpression.getValue(elContext);

        final String restartVariable = (String) updatesHashMap.get("restart");
        final Object foutpaden = updatesHashMap.get("foutafhandelingPaden");

        final Map<String, String> items = getSelectItems(foutpaden);
        final String selected = getSelectedItem(items, restartVariable);

        context.addSuccessMessage("Gekozen voor optie '" + selected + "'.");
    }

    private String getSelectedItem(final Map<String, String> items, final String restartVariable) {
        if (restartVariable != null && items != null) {
            for (final Map.Entry<String, String> entry : items.entrySet()) {
                if (entry.getValue().equals(restartVariable)) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }

    private Map<String, String> getSelectItems(final Object foutpaden) {
        try {
            final Method method = foutpaden.getClass().getMethod("getSelectItems", new Class<?>[]{});
            return (Map<String, String>) method.invoke(foutpaden);
        } catch (final
        NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            LOGGER.warn("Kon select items niet ophalen", e);
            return null;

        }
    }
}
