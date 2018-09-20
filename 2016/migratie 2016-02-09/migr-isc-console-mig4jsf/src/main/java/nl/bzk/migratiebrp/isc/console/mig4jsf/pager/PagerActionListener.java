/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pager;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Action listener voor pager tag.
 */
public final class PagerActionListener implements ActionListener, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_PAGESIZE = 10;

    private final ValueExpression pageExpression;
    private final ValueExpression pageSizeExpression;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     * 
     * @param pageExpression
     *            expressie voor page (pagina)
     * @param pageSizeExpression
     *            expressie voor pagesize (aantal resultaten per pagina)
     * @param targetExpression
     *            expressie voor target
     */
    public PagerActionListener(
        final ValueExpression pageExpression,
        final ValueExpression pageSizeExpression,
        final ValueExpression targetExpression)
    {
        this.pageExpression = pageExpression;
        this.pageSizeExpression = pageSizeExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void processAction(final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Object pageValue = pageExpression == null ? null : pageExpression.getValue(elContext);
        final int pageParameter =
                pageValue == null ? 1 : pageValue instanceof Number ? ((Number) pageValue).intValue()
                                                                   : Integer.parseInt(pageValue.toString());
        final int page = pageParameter < 1 ? 1 : pageParameter;

        final Object pageSizeValue = pageSizeExpression == null ? null : pageSizeExpression.getValue(elContext);
        final int pageSize =
                pageSizeValue == null ? DEFAULT_PAGESIZE : pageSizeValue instanceof Number ? ((Number) pageSizeValue).intValue()
                                                                                          : Integer.parseInt(pageSizeValue.toString());

        targetExpression.setValue(elContext, new PagerBean(page, pageSize));
    }

}
