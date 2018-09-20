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
 * Classificatie van de Relatie.
 * @version 1.0.18.
 */
public enum SoortRelatie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", ""),
    /** Huwelijk. */
    HUWELIJK("H", "Huwelijk", ""),
    /** Geregistreerd partnerschap. */
    GEREGISTREERD_PARTNERSCHAP("G", "Geregistreerd partnerschap", ""),
    /** Familierechtelijke betrekking. */
    FAMILIERECHTELIJKE_BETREKKING("F", "Familierechtelijke betrekking", "Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind.");

    /** De (functionele) code waarmee de Soort relatie kan worden aangeduid. */
    private final String code;
    /** Omschrijving van de soort relatie. */
    private final String naam;
    /** De omschrijving van de Soort relatie. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code De (functionele) code waarmee de Soort relatie kan worden aangeduid.
     * @param naam Omschrijving van de soort relatie.
     * @param omschrijving De omschrijving van de Soort relatie.
     *
     */
    private SoortRelatie(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De (functionele) code waarmee de Soort relatie kan worden aangeduid.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Omschrijving van de soort relatie.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van de Soort relatie.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
