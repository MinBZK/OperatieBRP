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
 * De wijze waarop het systeem het resultaat van een Regel dient af te handelen, c.q. wat het effect hiervan is of dient te zijn.
 * @version 1.0.18.
 */
public enum Regeleffect {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
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

    /** De naam. */
    private final String naam;
    /** De Omschrijving van de soort regelactie. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam.
     * @param omschrijving De Omschrijving van de soort regelactie.
     *
     */
    private Regeleffect(final String naam, final String omschrijving) {
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
     * @return De Omschrijving van de soort regelactie.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
