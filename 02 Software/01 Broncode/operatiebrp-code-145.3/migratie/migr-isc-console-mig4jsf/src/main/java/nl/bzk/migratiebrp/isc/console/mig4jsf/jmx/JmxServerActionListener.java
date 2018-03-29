/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import java.io.IOException;
import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Action listener voor process instances filter tag.
 */
public final class JmxServerActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final ValueExpression targetExpression;
    private final ValueExpression urlExpression;
    private final ValueExpression usernameExpression;
    private final ValueExpression passwordExpression;

    /**
     * Constructor.
     * @param targetExpression expression voor target
     * @param urlExpression expression voor url
     * @param usernameExpression expression voor username
     * @param passwordExpression expression voor password
     */
    public JmxServerActionListener(
            final ValueExpression targetExpression,
            final ValueExpression urlExpression,
            final ValueExpression usernameExpression,
            final ValueExpression passwordExpression) {
        this.targetExpression = targetExpression;
        this.urlExpression = urlExpression;
        this.usernameExpression = usernameExpression;
        this.passwordExpression = passwordExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final String url = (String) urlExpression.getValue(elContext);
        final String username = usernameExpression == null ? null : (String) usernameExpression.getValue(elContext);
        final String password = passwordExpression == null ? null : (String) passwordExpression.getValue(elContext);

        try {
            targetExpression.setValue(elContext, new JmxServer(url, username, password));
        } catch (final IOException e) {
            throw new AbortProcessingException("JMX Server kan niet bereikt worden.", e);
        }
    }

}
