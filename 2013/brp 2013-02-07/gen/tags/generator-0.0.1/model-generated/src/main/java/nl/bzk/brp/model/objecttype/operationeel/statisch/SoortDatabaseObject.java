/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Typologie van database object..
 */
public enum SoortDatabaseObject {

    /** DUMMY. */
    DUMMY(""),
    /** Tabel. */
    TABEL("Tabel"),
    /** Kolom. */
    KOLOM("Kolom");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private SoortDatabaseObject(final String naam) {
        this.naam = naam;
    }

    /**
     * De naam van het Databaseobject..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
