/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.displaytag.pagination;

import java.util.Collections;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;


/**
 * Paginering class ten behoeve van weergeven van zoek resultaten per pagina met pagina nummers.
 *
 *
 * @param <T>
 */
public class PaginatedListImpl<T> implements PaginatedList {

    private static final int NUMMER_TIEN    = 10;
    private static final int NUMMER_EEN     = 1;

    private int              pageNumber     = NUMMER_EEN;
    private int              objectsPerPage = NUMMER_TIEN;
    private int              fullListSize;

    private List<T>          list;

    @Override
    public List<T> getList() {
        return list;
    }

    /**
     * Zet de lijst, null elementen worden verwijderd.
     *
     * @param list resultaat
     */
    public void setList(final List<T> list) {
        if (list != null) {
            // Zorgt ervoor dat de null items uit de lijst worden verwijderd anders werkt de display tag niet
            list.removeAll(Collections.singletonList(null));
        }
        this.list = list;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int getObjectsPerPage() {
        return objectsPerPage;
    }

    public void setObjectsPerPage(final int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    @Override
    public int getFullListSize() {
        return fullListSize;
    }

    public void setFullListSize(final int fullListSize) {
        this.fullListSize = fullListSize;
    }

    @Override
    public String getSortCriterion() {
        // Niet geimplementeerd
        return null;
    }

    @Override
    public SortOrderEnum getSortDirection() {
        // Niet geimplementeerd
        return null;
    }

    @Override
    public String getSearchId() {
        // Niet geimplementeerd
        return null;
    }

    /**
     * Check of huidige pagina de eerste pagina is.
     *
     * @return true als het de eerste pagina is.
     */
    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    /**
     * Check of huidige pagina de laatste pagina is.
     *
     * @return true als het de laatste pagina is.
     */
    public boolean isLastPage() {
        return fullListSize <= pageNumber * objectsPerPage;
    }

    /**
     * Haal de eerste pagina op van de huidige pagina.
     *
     * @return eerste item van de pagina.
     */
    public int getCurrentPageFirstItem() {
        return (pageNumber - 1) * objectsPerPage + 1;
    }

    /**
     * Haal de laatste item van de pagina.
     *
     * @return laatste item van de pagina.
     */
    public int getCurrentPageLastItem() {
        if (isLastPage()) {
            return fullListSize;
        } else {
            return pageNumber * objectsPerPage;
        }
    }
}
