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
 * De typering van de bevoegdheid rondom bijhouding.
 * @version 1.0.18.
 */
public enum SoortBevoegdheid {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Bijhouden. */
    BIJHOUDEN("Bijhouden", "Bijhouden vanuit de bijhoudingsverantwoordelijkheid"),
    /** Bijhoudingsvoorstel doen. */
    BIJHOUDINGSVOORSTEL_DOEN("Bijhoudingsvoorstel doen", "Het mogen doen van bijhoudingsvoorstellen, overneembaar door degene die verantwoordelijk is voor de bijhouding."),
    /** Automatisch fiatteren. */
    AUTOMATISCH_FIATTEREN("Automatisch fiatteren", "Bijhoudingsvoorstel met fiattering vooraf"),
    /** Mandaat. */
    MANDAAT("Mandaat", "Gemandateerd zijn om de bijhouding te verrichten.");

    /** De naam. */
    private final String naam;
    /** De omschrijving. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam.
     * @param omschrijving De omschrijving.
     *
     */
    private SoortBevoegdheid(final String naam, final String omschrijving) {
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
     * @return De omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
