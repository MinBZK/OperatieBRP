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
 * Een in de Database voorkomend object, waarvan kennis noodzakelijk is voor het kunnen uitvoeren van de functionaliteit.
 * @version 1.0.18.
 */
public enum DatabaseObject {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", "", "");

    /** De naam van het database object. */
    private final String naam;
    /** Het soort database object. */
    private final String soort;
    /** De 'ouder' van het databaseobject. */
    private final String ouder;
    /** De identificatie ('naam') voor het databaseobject zoals die in de code terecht komt. */
    private final String javaIdentifier;

    /**
     * Constructor.
     *
     * @param naam De naam van het database object.
     * @param soort Het soort database object.
     * @param ouder De 'ouder' van het databaseobject.
     * @param javaIdentifier De identificatie ('naam') voor het databaseobject zoals die in de code terecht komt.
     *
     */
    private DatabaseObject(final String naam, final String soort, final String ouder, final String javaIdentifier) {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
        this.javaIdentifier = javaIdentifier;
    }

    /**
     * @return De naam van het database object.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return Het soort database object.
     */
    public String getSoort() {
        return soort;
    }

    /**
     * @return De 'ouder' van het databaseobject.
     */
    public String getOuder() {
        return ouder;
    }

    /**
     * @return De identificatie ('naam') voor het databaseobject zoals die in de code terecht komt.
     */
    public String getJavaIdentifier() {
        return javaIdentifier;
    }

}
