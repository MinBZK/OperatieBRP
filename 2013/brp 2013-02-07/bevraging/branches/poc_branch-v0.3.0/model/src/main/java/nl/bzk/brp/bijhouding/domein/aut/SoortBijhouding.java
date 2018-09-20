/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.aut;

/**
 * In kader van bijhouding zijn er verschillende soorten bijhoudingen zoals het (direct) bijhouden van persoonsgegevens,
 * of het doen van een voorstel hiervoor.
 */
public enum SoortBijhouding {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0.
     */
    DUMMY(null, null),

    /**
     * Het daadwerkelijk bijhouden van de gegevens.
     */
    BIJHOUDEN("Bijhouden", "Bijhouden vanuit de bijhoudingsverantwoordelijkheid"),

    /**
     * Een bijhoudingsvoorstel doen, is dus niet definitief.
     */
    BIJHOUDINGSVOORSTEL_DOEN("Bijhoudingsvoorstel doen", "Het mogen doen van bijhoudingsvoorstellen, "
        + "overneembaar door degene die verantwoordelijk is voor de bijhouding"),

    /**
     * Bijhoudingsvoorstel met fiattering vooraf.
     */
    AUTOMATISCH_FIATTEREN("Automatisch fiatteren", "Bijhoudingsvoorstel met fiattering vooraf"),

    /**
     * Gemandateerd zijn om de bijhouding te verrichten.
     */
    MANDAAT("Mandaat", "Gemandateerd zijn om de bijhouding te verrichten");

    private String naam;
    private String omschrijving;

    /**
     * Constructor voor de enumeratie.
     *
     * @param naam Naam van de soort bijhouding.
     * @param omschrijving Omschrijving van de soort bijhouding.
     */
    SoortBijhouding(final String naam, final String omschrijving) {
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
