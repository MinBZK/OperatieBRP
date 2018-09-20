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
 * Classificatie van Autorisatiebesluit.
 * @version 1.0.18.
 */
public enum SoortAutorisatiebesluit {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Leveringsautorisatie. */
    LEVERINGSAUTORISATIE("Leveringsautorisatie", "Een autorisatie ten behoeve van het leveren van gegevens aan Partijen in de rol van Afnemer."),
    /** Bijhoudingsautorisatie. */
    BIJHOUDINGSAUTORISATIE("Bijhoudingsautorisatie", "Een autorisatie ten behoeve van het bijhouden.");

    /** De naam. */
    private final String naam;
    /** De omschrijving van het Soort autorisatiebesluit. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam.
     * @param omschrijving De omschrijving van het Soort autorisatiebesluit.
     *
     */
    private SoortAutorisatiebesluit(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van het Soort autorisatiebesluit.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
