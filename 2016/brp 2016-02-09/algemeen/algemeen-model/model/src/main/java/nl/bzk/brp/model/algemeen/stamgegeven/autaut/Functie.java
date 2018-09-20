/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;

/**
 * Functie is een door een Partij uitvoerbare activiteit die ten dienste staat aan het vervullen van ��n of meer rollen
 * van die Partij.
 *
 * Definitie is nog wat warrig, dit komt ook omdat er nog geen goede invulling van is.
 *
 * Er is een 'dummy record' in geplaatst: een 'functie' met ID gelijk aan 1. De verwachting is dat er tenminste ��n
 * functie zal zijn, de naam hiervan is nog onbekend; hiervoor de naam 'dummy' gebruikt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Functie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Dummy vulling.
     */
    DUMMY_VULLING("Dummy vulling");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Functie
     */
    private Functie(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Functie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
