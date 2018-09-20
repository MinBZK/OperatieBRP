/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;


/**
 * Enumeratie voor het geslacht van een persoon.
 */
public enum GeslachtsAanduiding {

    /** Mannelijk. */
    MAN("M", "Man"),
    /** Vrouwelijk. */
    VROUW("V", "Vrouw"),
    /** Geslacht is onbekend. */
    ONBEKEND("O", "Onbekend");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor die de code en omschrijving  zet.
     * @param code de code van de geslachtsaanduiding.
     * @param omschrijving de omschrijving van de geslachtsaanduiding.
     */
    private GeslachtsAanduiding(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de code behorende bij de geslachtsaanduiding.
     * @return de code behorende bij de geslachtsaanduiding.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert de omschrijving behorende bij de geslachtsaanduiding.
     * @return de omschrijving behorende bij de geslachtsaanduiding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
