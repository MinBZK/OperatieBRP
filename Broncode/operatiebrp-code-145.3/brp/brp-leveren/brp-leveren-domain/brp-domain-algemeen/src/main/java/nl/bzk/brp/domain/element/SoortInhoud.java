/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

/**
 * SoortInhoud.
 */
public enum SoortInhoud {

    /**
     * Dynamisch.
     */
    D("D", "dynamisch"),
    /**
     * Overig.
     */
    X("X", "overig");

    private String code;
    private String omschrijving;

    /**
     * @param code         code
     * @param omschrijving omschrijving
     */
    SoortInhoud(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
