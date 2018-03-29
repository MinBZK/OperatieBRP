/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
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

    private final Map<FilterEnum, ValueExpression> waarden;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     * @param waarden de benodigde value expressions
     * @param targetExpression target expression
     */
    public BerichtenFilterActionListener(final Map<FilterEnum, ValueExpression> waarden,
                                         final ValueExpression targetExpression) {
        super();
        this.waarden = waarden;
        this.targetExpression = targetExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Map<FilterEnum, String> stringWaarden = new EnumMap<>(FilterEnum.class);
        waarden.forEach((sleutel, waarde) -> stringWaarden.put(sleutel, ExpressionHelper.getString(waarde, elContext)));
        targetExpression.setValue(elContext, new BerichtenFilter(stringWaarden));
    }

}
