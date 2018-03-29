/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pager;

import java.io.Serializable;

/**
 * Pager bean.
 */
public final class PagerBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int page;
    private final int pageSize;

    private int totalPages;
    private int thisPageSize;

    /**
     * Constructor.
     * @param page pagina
     * @param pageSize pagina grootte
     */
    public PagerBean(final int page, final int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * Geef de waarde van page.
     * @return page
     */
    public int getPage() {
        return page;
    }

    /**
     * Geef de waarde van page size.
     * @return page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /* TO BE SET BY QUERY COMPONENT */

    /**
     * Zet het aantal resultaten uit de query (om aantal paginas en grootte van deze pagina te bereken).
     * @param numberOfResults aantal resultaten
     */
    public void setNumberOfResults(final int numberOfResults) {
        totalPages = (numberOfResults + pageSize - 1) / pageSize;
        thisPageSize = page == totalPages ? (numberOfResults - 1) % pageSize + 1 : pageSize;
    }

    /* USED BY t_pager.xhtml */

    /**
     * Geef de waarde van total pages.
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Geef de first page.
     * @return true als dit de eerste pagina is
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * Geef de last page.
     * @return true als dit de laatste pagina is
     */
    public boolean isLastPage() {
        return page == totalPages;
    }

    /* USED BY t_processinstances.xhtml */

    /**
     * Geef de waarde van first.
     * @return eerste index in de list voor deze pagina (=0)
     */
    public int getFirst() {
        return 0;
    }

    /**
     * Geef de waarde van limit.
     * @return laatste index in de list voor deze pagina
     */
    public int getLimit() {
        return thisPageSize;
    }

}
