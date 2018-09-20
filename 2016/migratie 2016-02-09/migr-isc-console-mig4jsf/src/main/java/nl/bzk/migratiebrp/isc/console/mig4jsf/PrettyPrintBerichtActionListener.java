/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pretty.PrettyPrint;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Pretty print.
 */
public final class PrettyPrintBerichtActionListener extends AbstractActionListener {

    private final ValueExpression berichtExpression;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     *
     * @param berichtExpression
     *            bericht expression
     * @param targetExpression
     *            target expression
     */
    public PrettyPrintBerichtActionListener(final ValueExpression berichtExpression, final ValueExpression targetExpression) {
        super("prettyPrintBericht");
        this.berichtExpression = berichtExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Bericht berichtValue = (Bericht) berichtExpression.getValue(elContext);
        if (berichtValue != null) {
            final String html = new PrettyPrint().prettyPrint(berichtValue);
            targetExpression.setValue(elContext, html);
        }
    }
}
