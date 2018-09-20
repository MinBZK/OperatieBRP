/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

/**
 * Statisch object type Verwerkingsresultaat.
 */
public enum Verwerkingsresultaat {

    /**
     * Dummy waarde ivm ordinal 0.*
     */
    DUMMY("DUMMY", "DUMMY"),
    /**
     * Verwerking geslaagd.*
     */
    GOED("G", "Verwerking geslaagd"),
    /**
     * Verwerking foutief.*
     */
    FOUT("F", "Verwerking foutief");

    private String code;
    private String naam;

    /**
     * Constructor.
     *
     * @param code Code.
     * @param naam Naam.
     */
    Verwerkingsresultaat(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }
}
