/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

/**
 * De wijze waarop het systeem het resultaat van een Regel dient af te handelen, c.q. wat het effect hiervan is of dient
 * te zijn.
 *
 * Naar aanleiding van het controleren van een Regel zijn er verschillende wijzen waarop 'het systeem' de Regel kan
 * afhandelen: afkeuren van het bericht, waarschuwen etc etc.
 *
 *
 *
 */
public enum Regeleffect {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Verbieden zonder dat er sprake kan zijn van een overrule..
     */
    HARD_VERBIEDEN("Hard verbieden", "Verbieden zonder dat er sprake kan zijn van een overrule."),
    /**
     * Verbieden, maar met mogelijkheid om te overrulen..
     */
    ZACHT_VERBIEDEN("Zacht verbieden", "Verbieden, maar met mogelijkheid om te overrulen."),
    /**
     * Waarschuwen dat er iets speciaals aan de hand is..
     */
    WAARSCHUWEN("Waarschuwen", "Waarschuwen dat er iets speciaals aan de hand is."),
    /**
     * ??.
     */
    AFLEIDEN("Afleiden", "??"),
    /**
     * ???.
     */
    INFORMEREN("Informeren", "???");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Regeleffect
     * @param omschrijving Omschrijving voor Regeleffect
     */
    private Regeleffect(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Regeleffect.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Regeleffect.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
