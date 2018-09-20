/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * .
 */
public enum Verwerkingsresultaat {

    /** DUMMY. */
    DUMMY("", ""),
    /** Verwerking geslaagd. */
    VERWERKING_GESLAAGD("G", "Verwerking geslaagd"),
    /** Verwerking foutief. */
    VERWERKING_FOUTIEF("F", "Verwerking foutief");

    /** code. */
    private String code;
    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     *
     */
    private Verwerkingsresultaat(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * .
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * .
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
