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
 * Typologie van database object.
 * @version 1.0.18.
 */
public enum SoortDatabaseObject {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(""),
    /** Tabel. */
    TABEL("Tabel"),
    /** Kolom. */
    KOLOM("Kolom");

    /** De naam van het Databaseobject. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam De naam van het Databaseobject.
     *
     */
    private SoortDatabaseObject(final String naam) {
        this.naam = naam;
    }

    /**
     * @return De naam van het Databaseobject.
     */
    public String getNaam() {
        return naam;
    }

}
