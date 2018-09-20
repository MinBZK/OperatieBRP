/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.jsf.JbpmJsfContext;

/**
 * Lees property uit config bestand handler.
 */
public final class PropertyLoaderActionListener extends AbstractActionListener {

    private static final Properties PROPERTIES;
    static {
        PROPERTIES = new Properties();

        try (InputStream inputStream = PropertyLoaderActionListener.class.getResourceAsStream("/config.properties")) {

            if (inputStream == null) {
                throw new IOException("Kan config.properties niet vinden.");
            }

            PROPERTIES.load(inputStream);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Kan config.properties niet laden.", exception);
        }
    }
    private final ValueExpression targetExpression;
    private final ValueExpression propertyExpression;

    /**
     * Constructor.
     *
     * @param propertyExpression
     *            pager expression
     * @param targetExpression
     *            target expression
     */
    public PropertyLoaderActionListener(final ValueExpression propertyExpression, final ValueExpression targetExpression) {
        super("propertyLoader");
        this.propertyExpression = propertyExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext jbpmJsfContext, final ActionEvent event) throws IOException {

        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final String propertyKey = (String) propertyExpression.getValue(elContext);

        final String value = PROPERTIES.getProperty(propertyKey);
        targetExpression.setValue(elContext, value);

        jbpmJsfContext.selectOutcome("success");
    }
}
