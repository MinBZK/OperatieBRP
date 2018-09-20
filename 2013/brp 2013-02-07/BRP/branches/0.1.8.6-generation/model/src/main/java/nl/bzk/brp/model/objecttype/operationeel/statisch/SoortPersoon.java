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
 * Classificatie van Personen.
 * @version 1.0.18.
 */
public enum SoortPersoon {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", ""),
    /** Ingeschrevene. */
    INGESCHREVENE("I", "Ingeschrevene", ""),
    /** Niet ingeschrevene. */
    NIET_INGESCHREVENE("N", "Niet ingeschrevene", ""),
    /** Alternatieve realiteit. */
    ALTERNATIEVE_REALITEIT("A", "Alternatieve realiteit", "");

    /** De (functionele) code voor het Soort persoon. */
    private final String code;
    /** De naam van het Soort persoon. */
    private final String naam;
    /** De omschrijving van de Soort persoon. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code De (functionele) code voor het Soort persoon.
     * @param naam De naam van het Soort persoon.
     * @param omschrijving De omschrijving van de Soort persoon.
     *
     */
    private SoortPersoon(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De (functionele) code voor het Soort persoon.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return De naam van het Soort persoon.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van de Soort persoon.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
