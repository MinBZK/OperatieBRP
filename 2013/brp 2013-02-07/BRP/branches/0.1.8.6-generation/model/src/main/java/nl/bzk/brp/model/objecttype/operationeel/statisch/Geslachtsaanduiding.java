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
 * De mogelijke aanduiding van het geslacht van een Persoon.
 * @version 1.0.18.
 */
public enum Geslachtsaanduiding {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", ""),
    /** Man. */
    MAN("M", "Man", ""),
    /** Vrouw. */
    VROUW("V", "Vrouw", ""),
    /** Onbekend. */
    ONBEKEND("O", "Onbekend", "");

    /** De (functionele) code waarmee het Geslacht kan worden aangeduid. */
    private final String code;
    /** De naam waarmee het Geslacht kan worden aangeduid. */
    private final String naam;
    /** De omschrijving waarmee het Geslacht kan worden omschreven. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code De (functionele) code waarmee het Geslacht kan worden aangeduid.
     * @param naam De naam waarmee het Geslacht kan worden aangeduid.
     * @param omschrijving De omschrijving waarmee het Geslacht kan worden omschreven.
     *
     */
    private Geslachtsaanduiding(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De (functionele) code waarmee het Geslacht kan worden aangeduid.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return De naam waarmee het Geslacht kan worden aangeduid.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving waarmee het Geslacht kan worden omschreven.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
