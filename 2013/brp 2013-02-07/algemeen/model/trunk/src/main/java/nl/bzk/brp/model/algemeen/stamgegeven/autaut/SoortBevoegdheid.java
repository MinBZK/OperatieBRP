/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

/**
 * De typering van de bevoegdheid rondom bijhouding.
 *
 * In kader van bijhouding zijn er verschillende soorten bevoegdheden in het kader van bijhoudingen, zoals het (direct)
 * bijhouden van persoonsgegevens, of het doen van een voorstel hiervoor.
 *
 * Soort bevoegdheid is een beknopte naamgeving van het gegeven. Een langere naam zou bijvoorbeeld soort
 * bijhoudingsbevoegdheid kunnen zijn. In de praktijk zijn thans echter geen andere bevoegdheden onderkend dan die in
 * kader van bijhouden. De voorkeur voor de beknoptere naam geeft daarom de doorslag: het wordt soort bevoegdheid ipv
 * soort bijhoudingsbevoegdheid. RvdP 17 april 2012.
 *
 *
 *
 */
public enum SoortBevoegdheid {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Bijhouden vanuit de bijhoudingsverantwoordelijkheid.
     */
    BIJHOUDEN("Bijhouden", "Bijhouden vanuit de bijhoudingsverantwoordelijkheid"),
    /**
     * Het mogen doen van bijhoudingsvoorstellen, overneembaar door degene die verantwoordelijk is voor de bijhouding..
     */
    BIJHOUDINGSVOORSTEL_DOEN("Bijhoudingsvoorstel doen",
            "Het mogen doen van bijhoudingsvoorstellen, overneembaar door degene die verantwoordelijk is voor de bijhouding."),
    /**
     * Bijhoudingsvoorstel met fiattering vooraf.
     */
    AUTOMATISCH_FIATTEREN("Automatisch fiatteren", "Bijhoudingsvoorstel met fiattering vooraf"),
    /**
     * Gemandateerd zijn om de bijhouding te verrichten..
     */
    MANDAAT("Mandaat", "Gemandateerd zijn om de bijhouding te verrichten.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortBevoegdheid
     * @param omschrijving Omschrijving voor SoortBevoegdheid
     */
    private SoortBevoegdheid(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort bevoegdheid.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort bevoegdheid.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
