/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Een in de Database voorkomend object, waarvan kennis noodzakelijk is voor het kunnen uitvoeren van de functionaliteit..
 */
public enum DatabaseObject {

    /** DUMMY. */
    DUMMY("", "", "", "");

    /** naam. */
    private String naam;
    /** soort. */
    private String soort;
    /** ouder. */
    private String ouder;
    /** javaIdentifier. */
    private String javaIdentifier;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param soort de soort
     * @param ouder de ouder
     * @param javaIdentifier de javaIdentifier
     *
     */
    private DatabaseObject(final String naam, final String soort, final String ouder, final String javaIdentifier) {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
        this.javaIdentifier = javaIdentifier;
    }

    /**
     * De naam van het database object..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Het soort database object..
     * @return String
     */
    public String getSoort() {
        return soort;
    }

    /**
     * De 'ouder' van het databaseobject..
     * @return String
     */
    public String getOuder() {
        return ouder;
    }

    /**
     * De identificatie ('naam') voor het databaseobject zoals die in de code terecht komt..
     * @return String
     */
    public String getJavaIdentifier() {
        return javaIdentifier;
    }

}
