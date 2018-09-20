/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * .
 * @version 1.0.18.
 */
public enum Verwerkingsresultaat {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Verwerking geslaagd. */
    VERWERKING_GESLAAGD("G", "Verwerking geslaagd"),
    /** Verwerking foutief. */
    VERWERKING_FOUTIEF("F", "Verwerking foutief");

    /** . */
    private final String code;
    /** . */
    private final String naam;

    /**
     * Constructor.
     *
     * @param code .
     * @param naam .
     *
     */
    private Verwerkingsresultaat(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * @return .
     */
    public String getCode() {
        return code;
    }

    /**
     * @return .
     */
    public String getNaam() {
        return naam;
    }

}
