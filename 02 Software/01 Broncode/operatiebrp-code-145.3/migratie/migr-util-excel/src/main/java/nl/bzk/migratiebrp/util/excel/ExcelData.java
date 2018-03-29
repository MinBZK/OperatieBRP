/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.excel;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Excel data.
 */
public final class ExcelData {
    private String[] headers;
    private List<Lo3CategorieWaarde> categorieLijst;

    /**
     * Get headers.
     * @return LO3 headers of a LO3 message
     */
    public String[] getHeaders() {
        return ArrayUtils.clone(headers);
    }

    /**
     * Set headers.
     * @param headers LO3 headers of a LO3 message
     */
    public void setHeaders(final String[] headers) {
        this.headers = ArrayUtils.clone(headers);
    }

    /**
     * Get contents.
     * @return The contents of a LO3 message
     */
    public List<Lo3CategorieWaarde> getCategorieLijst() {
        return categorieLijst;
    }

    /**
     * Set contents.
     * @param categorieWaardeLijst The contents of a LO3 message
     */
    public void setCategorieWaarden(final List<Lo3CategorieWaarde> categorieWaardeLijst) {
        categorieLijst = categorieWaardeLijst;
    }

    /**
     * Is empty.
     * @return true if empty, else false
     */
    public boolean isEmpty() {
        return headers.length == 0 && categorieLijst.isEmpty();
    }

}
