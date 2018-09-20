/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De typering van de bevoegdheid rondom bijhouding..
 */
public enum SoortBevoegdheid {

    /** DUMMY. */
    DUMMY("", ""),
    /** Bijhouden. */
    BIJHOUDEN("Bijhouden", "Bijhouden vanuit de bijhoudingsverantwoordelijkheid"),
    /** Bijhoudingsvoorstel doen. */
    BIJHOUDINGSVOORSTEL_DOEN("Bijhoudingsvoorstel doen", "Het mogen doen van bijhoudingsvoorstellen, overneembaar door degene die verantwoordelijk is voor de bijhouding."),
    /** Automatisch fiatteren. */
    AUTOMATISCH_FIATTEREN("Automatisch fiatteren", "Bijhoudingsvoorstel met fiattering vooraf"),
    /** Mandaat. */
    MANDAAT("Mandaat", "Gemandateerd zijn om de bijhouding te verrichten.");

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
    private SoortBevoegdheid(final String naam, final String omschrijving) {
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
     * De omschrijving..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
