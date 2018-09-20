/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De wijze waarop het systeem het resultaat van een Regel dient af te handelen, c.q. wat het effect hiervan is of dient te zijn..
 */
public enum Regeleffect {

    /** DUMMY. */
    DUMMY("", ""),
    /** Hard verbieden. */
    HARD_VERBIEDEN("Hard verbieden", "Verbieden zonder dat er sprake kan zijn van een overrule."),
    /** Zacht verbieden. */
    ZACHT_VERBIEDEN("Zacht verbieden", "Verbieden, maar met mogelijkheid om te overrulen."),
    /** Waarschuwen. */
    WAARSCHUWEN("Waarschuwen", "Waarschuwen dat er iets speciaals aan de hand is."),
    /** Afleiden. */
    AFLEIDEN("Afleiden", "??"),
    /** Informeren. */
    INFORMEREN("Informeren", "???");

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
    private Regeleffect(final String naam, final String omschrijving) {
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
     * De Omschrijving van de soort regelactie..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
