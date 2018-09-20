/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De mogelijke reden voor opschorting van de bijhouding van een Persoon..
 */
public enum RedenOpschorting {

    /** DUMMY. */
    DUMMY("", ""),
    /** Overlijden. */
    OVERLIJDEN("O", "Overlijden"),
    /** Ministerieel besluit. */
    MINISTERIEEL_BESLUIT("M", "Ministerieel besluit"),
    /** Fout. */
    FOUT("F", "Fout"),
    /** Onbekend. */
    ONBEKEND("?", "Onbekend");

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
    private RedenOpschorting(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * De (functionele) code waarmee de Reden opschorting kan worden aangeduid..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * Omschrijving van de reden om de bijhouding op te schorten..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
