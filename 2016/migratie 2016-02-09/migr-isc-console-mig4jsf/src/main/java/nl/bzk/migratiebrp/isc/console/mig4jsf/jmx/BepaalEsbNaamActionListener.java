/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.management.JMException;

/**
 * Haal een waarde op via jmx.
 */
public final class BepaalEsbNaamActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final ValueExpression targetExpression;
    private final ValueExpression serverExpression;
    private final ValueExpression prefixExpression;

    /**
     * Constructor.
     *
     * @param targetExpression
     *            expression voor target
     * @param serverExpression
     *            expression voor jmx server
     * @param prefixExpression
     *            expression voor deployment name prefix
     */
    public BepaalEsbNaamActionListener(
        final ValueExpression targetExpression,
        final ValueExpression serverExpression,
        final ValueExpression prefixExpression)
    {
        this.targetExpression = targetExpression;
        this.serverExpression = serverExpression;
        this.prefixExpression = prefixExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final JmxServer server = (JmxServer) serverExpression.getValue(elContext);
        final String prefix = (String) prefixExpression.getValue(elContext);

        try {
            final List<String> deployments = server.listEsbDeployments(prefix);
            final String result = deployments.isEmpty() ? "Geen ESB deployment gevonden met prefix '" + prefix + "'." : deployments.get(0);

            targetExpression.setValue(elContext, result);
        } catch (final
            IOException
            | JMException e)
        {
            throw new AbortProcessingException("JMX Attribute kan niet worden opgehaald.", e);
        }
    }
}
