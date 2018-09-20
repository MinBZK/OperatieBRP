/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Action listener voor process instances filter tag.
 */
public final class BerichtenFilterActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;

    private final ValueExpression targetExpression;

    private final ValueExpression kanaalExpression;
    private final ValueExpression richtingExpression;
    private final ValueExpression bronExpression;
    private final ValueExpression doelExpression;
    private final ValueExpression typeExpression;
    private final ValueExpression berichtIdExpression;
    private final ValueExpression correlatieIdExpression;

    /**
     * Constructor.
     * 
     * @param targetExpression
     *            expression voor target
     * @param kanaalExpression
     *            expression voor kanaal
     * @param richtingExpression
     *            expression voor richting
     * @param bronExpression
     *            expression voor bron
     * @param doelExpression
     *            expression voor doel
     * @param typeExpression
     *            expression voor type
     * @param berichtIdExpression
     *            expression voor bericht id
     * @param correlatieIdExpression
     *            expression voor correlatie id
     */
    public BerichtenFilterActionListener(
        final ValueExpression targetExpression,
        final ValueExpression kanaalExpression,
        final ValueExpression richtingExpression,
        final ValueExpression bronExpression,
        final ValueExpression doelExpression,
        final ValueExpression typeExpression,
        final ValueExpression berichtIdExpression,
        final ValueExpression correlatieIdExpression)
    {
        super();
        this.targetExpression = targetExpression;
        this.kanaalExpression = kanaalExpression;
        this.richtingExpression = richtingExpression;
        this.bronExpression = bronExpression;
        this.doelExpression = doelExpression;
        this.typeExpression = typeExpression;
        this.berichtIdExpression = berichtIdExpression;
        this.correlatieIdExpression = correlatieIdExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final String kanaal = ExpressionHelper.getString(kanaalExpression, elContext);
        final String richting = ExpressionHelper.getString(richtingExpression, elContext);
        final String bron = ExpressionHelper.getString(bronExpression, elContext);
        final String doel = ExpressionHelper.getString(doelExpression, elContext);
        final String type = ExpressionHelper.getString(typeExpression, elContext);
        final String berichtId = ExpressionHelper.getString(berichtIdExpression, elContext);
        final String correlatieId = ExpressionHelper.getString(correlatieIdExpression, elContext);

        targetExpression.setValue(elContext, new BerichtenFilter(kanaal, richting, bron, doel, type, berichtId, correlatieId));
    }

}
