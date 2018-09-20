/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.brm;

/**
 * Effect van het {@link RegelGedrag}.
 */
public enum RegelEffect {
    /**
     * Ordinal van een enum begint bij 0, maar de id van tuples van statische tabellen bij 1.
     */
    ORDINAL_NUL_NIET_GEBRUIKEN(null),
    /**
     * Verbieden, geen overrule.
     */
    VERBIEDEN_GEEN_OVERRULE("Verbieden, geen overrule"),
    /**
     * Verbieden, overrule mogelijk.
     */
    VERBIEDEN_OVERRULE_MOGELIJK("Verbieden, overrule mogelijk"),
    /**
     * Waarschuwen.
     */
    WAARSCHUWEN("Waarschuwen"),
    /**
     * Informeren.
     */
    INFORMEREN("Informeren"),
    /**
     * Afleiden.
     */
    AFLEIDEN("Afleiden");

    private String naam;

    public String getNaam() {
        return naam;
    }

    /**
     * Private constructor ten behoeve van eenmalige initialisatie.
     *
     * @param naam de naam van het effect.
     */
    private RegelEffect(final String naam) {
        this.naam = naam;
    }
}
