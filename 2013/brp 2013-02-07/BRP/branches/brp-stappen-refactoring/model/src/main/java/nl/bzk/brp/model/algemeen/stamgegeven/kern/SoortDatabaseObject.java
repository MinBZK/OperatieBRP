/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Typologie van database object.
 *
 * De database kent verschillende soorten objecten, zoals tabellen, kolommen, indexen en constraints. In deze soort
 * tabel komen alleen d�e soorten voor die relevant zijn te kennen.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum SoortDatabaseObject {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Tabel.
     */
    TABEL("Tabel"),
    /**
     * Kolom.
     */
    KOLOM("Kolom");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortDatabaseObject
     */
    private SoortDatabaseObject(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort database object.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
