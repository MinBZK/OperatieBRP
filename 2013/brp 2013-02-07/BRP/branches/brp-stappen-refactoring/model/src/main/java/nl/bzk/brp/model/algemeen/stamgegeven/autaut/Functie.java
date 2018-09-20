/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

/**
 * Functie is een door een Partij uitvoerbare activiteit die ten dienste staat aan het vervullen van ��n of meer rollen
 * van die Partij.
 *
 * Definitie is nog wat warrig, dit komt ook omdat er nog geen goede invulling van is.
 * RvdP 13 oktober 2011.
 *
 * Er is een 'dummy record' in geplaatst: een 'functie' met ID gelijk aan 1.
 * De verwachting is dat er tenminste ��n functie zal zijn, de naam hiervan is nog onbekend; hiervoor de naam 'dummy'
 * gebruikt.
 * RvdP 23 november 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
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
