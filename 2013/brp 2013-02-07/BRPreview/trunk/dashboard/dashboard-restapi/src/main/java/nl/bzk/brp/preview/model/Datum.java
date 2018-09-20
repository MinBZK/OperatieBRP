/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class Datum.
 */
public class Datum {

    private static final int HONDERD = 100;
    private static final int TIEN_DUIZEND = 10000;

    /** De decimalen. */
    private int decimalen;

    /**
     * Instantieert een nieuwe datum.
     */
    public Datum() {
    }

    /**
     * Instantieert een nieuwe datum.
     *
     * @param decimalen de decimalen
     */
    public Datum(final int decimalen) {
        this.decimalen = decimalen;
    }

    /**
     * Haalt een decimalen op.
     *
     * @return decimalen
     */
    public int getDecimalen() {
        return decimalen;
    }

    /**
     * Instellen van decimalen.
     *
     * @param decimalen de nieuwe decimalen
     */
    public void setDecimalen(final int decimalen) {
        this.decimalen = decimalen;
    }

    /**
     * Haalt een tekst op.
     *
     * @return tekst
     */
    public String getTekst() {

        int verhuisDag = decimalen % HONDERD;
        int verhuisMaand = (decimalen - verhuisDag) / HONDERD % HONDERD;
        int verhuisJaar = decimalen / TIEN_DUIZEND;
        String template = "%02d-%02d-%04d";

        return String.format(template, verhuisDag, verhuisMaand, verhuisJaar);
    }

}
