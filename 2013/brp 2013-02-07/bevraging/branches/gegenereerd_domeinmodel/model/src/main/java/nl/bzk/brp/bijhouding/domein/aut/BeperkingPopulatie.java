/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.aut;

/**
 * De beperking op de populatie waarop Bijhoudingsautorisaties van toepassing kunnen zijn.
 */
public enum BeperkingPopulatie {

    /**
     * Dummy waarde om ordinal 0 niet te gebruiken.
     */
    DUMMY("dummy", "dummy"),

    /**
     * De populatie is beperkt tot personen waarvan de bijhoudingsverantwoordelijkheid bij het College van B&W ligt
     * van de (autorisatie ontvangende) gemeente.
     */
    ONTVANGER("Ontvanger", "Personen waarvan de bijhoudingsverantwoordelijkheid bij het College van B&W ligt van de "
        + "(autorisatie ontvangende) gemeente."),

    /**
     * De populatie is beperkt tot personen waarvan de bijhoudingsverantwoordelijkheid ligt bij de Partij die de
     * autorisatie verstrekt.
     */
    VERSTREKKER("Verstrekker",
            "Personen waarvan de bijhoudingsverantwoordelijkheid ligt bij de Partij die de autorisatie verstrekt.");

    private String naam;
    private String omschrijving;

    /**
     * Constructor.
     * @param naam Naam van de populatie beperking.
     * @param omschrijving Omschrijving voor de populatie beperking.
     */
    BeperkingPopulatie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
