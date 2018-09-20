/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

/**
 * Reden opschorting.
 */
public enum RedenOpschorting {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null, null),
    /** Opgeschort vanwege overlijden. */
    OVERLIJDEN("O", "Overlijden", ""),
    /** Opschorting vanwege ministerieel besluit. */
    MINISTER("M", "Ministerieel besluit", ""),
    /** fout geval. */
    FOUT("F", "Fout", ""),
    /** onbekende reden tot opschorting. */
    ONBEKEND("?", "Onbekend", "");

    private final String code;

    private final String naam;

    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code soort persoon code
     * @param naam soort persoon naam
     * @param omschrijving de omschrijving
     */
    private RedenOpschorting(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
