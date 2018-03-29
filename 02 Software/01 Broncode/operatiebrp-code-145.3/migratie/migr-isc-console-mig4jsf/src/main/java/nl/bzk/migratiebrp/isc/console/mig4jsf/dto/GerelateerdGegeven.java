/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

/**
 * DTO Gerelateerd gegeven.
 */
public final class GerelateerdGegeven {

    private String soort;
    private String gegeven;

    /**
     * Geef de waarde van soort.
     * @return soort
     */
    public String getSoort() {
        return soort;
    }

    /**
     * Zet de waarde van soort.
     * @param soort soort
     */
    public void setSoort(final String soort) {
        this.soort = soort;
    }

    /**
     * Geef de waarde van gegeven.
     * @return gegeven
     */
    public String getGegeven() {
        return gegeven;
    }

    /**
     * Zet de waarde van gegeven.
     * @param gegeven gegeven
     */
    public void setGegeven(final String gegeven) {
        this.gegeven = gegeven;
    }
}
