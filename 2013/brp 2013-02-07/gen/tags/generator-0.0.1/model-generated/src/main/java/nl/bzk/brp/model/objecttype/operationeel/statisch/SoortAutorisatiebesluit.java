/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Classificatie van Autorisatiebesluit..
 */
public enum SoortAutorisatiebesluit {

    /** DUMMY. */
    DUMMY("", ""),
    /** Leveringsautorisatie. */
    LEVERINGSAUTORISATIE("Leveringsautorisatie", "Een autorisatie ten behoeve van het leveren van gegevens aan Partijen in de rol van Afnemer."),
    /** Bijhoudingsautorisatie. */
    BIJHOUDINGSAUTORISATIE("Bijhoudingsautorisatie", "Een autorisatie ten behoeve van het bijhouden.");

    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private SoortAutorisatiebesluit(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van het Soort autorisatiebesluit..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
