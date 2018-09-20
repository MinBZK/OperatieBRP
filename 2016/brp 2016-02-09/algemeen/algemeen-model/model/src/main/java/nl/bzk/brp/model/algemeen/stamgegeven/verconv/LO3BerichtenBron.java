/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.verconv;

import javax.annotation.Generated;

/**
 * De bron van een LO3 bericht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum LO3BerichtenBron {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Synchronisatie.
     */
    SYNCHRONISATIE("Synchronisatie"),
    /**
     * Initiële vulling.
     */
    INITIELE_VULLING("Initiële vulling");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor LO3BerichtenBron
     */
    private LO3BerichtenBron(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van LO3 Berichten bron.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
