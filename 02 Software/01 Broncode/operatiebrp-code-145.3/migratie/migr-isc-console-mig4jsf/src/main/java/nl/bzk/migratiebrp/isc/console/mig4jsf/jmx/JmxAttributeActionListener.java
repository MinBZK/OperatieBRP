/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Haal een waarde op via jmx.
 */
public final class JmxAttributeActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final ValueExpression targetExpression;
    private final ValueExpression serverExpression;
    private final ValueExpression objectNameExpression;
    private final ValueExpression attributeNameExpression;
    private final ValueExpression ignoreErrorsExpression;

    /**
     * Constructor.
     * @param targetExpression expression voor target
     * @param serverExpression expression voor jmx server
     * @param objectNameExpression expression voor object name
     * @param attributeNameExpression expression voor attribute name
     * @param ignoreErrorsExpression expression voor indicatie errors negeren
     */
    JmxAttributeActionListener(
            final ValueExpression targetExpression,
            final ValueExpression serverExpression,
            final ValueExpression objectNameExpression,
            final ValueExpression attributeNameExpression,
            final ValueExpression ignoreErrorsExpression) {
        this.targetExpression = targetExpression;
        this.serverExpression = serverExpression;
        this.objectNameExpression = objectNameExpression;
        this.attributeNameExpression = attributeNameExpression;
        this.ignoreErrorsExpression = ignoreErrorsExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final JmxServer server = (JmxServer) serverExpression.getValue(elContext);
        final String objectName = (String) objectNameExpression.getValue(elContext);
        final String attributeName = (String) attributeNameExpression.getValue(elContext);

        try {
            targetExpression.setValue(elContext, server.getAttribute(objectName, attributeName));
        } catch (final JmxServerException e) {
            final boolean ignoreErrors;
            if (ignoreErrorsExpression == null) {
                ignoreErrors = true;
            } else {
                final Boolean ignoreErrorsValue = (Boolean) ignoreErrorsExpression.getValue(elContext);
                ignoreErrors = ignoreErrorsValue == null ? true : ignoreErrorsValue;
            }
            if (!ignoreErrors) {
                throw new AbortProcessingException("JMX Attribute kan niet worden opgehaald.", e);
            }
        }
    }

}
