/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * 
 */
public class BerichtDto {

    private String[] headers;
    private List<Lo3CategorieWaarde> categorieLijst;

    /**
     * 
     */
    public BerichtDto() {
        headers = null;
        categorieLijst = new ArrayList<Lo3CategorieWaarde>();
    }

    /**
     * 
     * @return LO3 headers of a LO3 message
     */
    public final String[] getHeaders() {
        return headers;
    }

    /**
     * 
     * @param headers
     *            LO3 headers of a LO3 message
     */
    public final void setHeaders(final String[] headers) {
        this.headers = headers;
    }

    /**
     * 
     * @return The contents of a LO3 message
     */
    public final List<Lo3CategorieWaarde> getCategorieLijst() {
        return categorieLijst;
    }

    /**
     * 
     * @param categorieLijst
     *            The contents of a LO3 message
     */
    public final void setCategorieWaarden(final List<Lo3CategorieWaarde> categorieLijst) {
        this.categorieLijst = categorieLijst;
    }

}
