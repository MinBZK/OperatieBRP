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
 * De mogelijke wijzen om Betrokken te zijn in een Relatie.
 * @version 1.0.18.
 */
public enum SoortBetrokkenheid {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Kind. */
    KIND("K", "Kind"),
    /** Ouder. */
    OUDER("O", "Ouder"),
    /** Partner. */
    PARTNER("P", "Partner");

    /** De (functionele) code waarmee de Soort betrokkenheid kan worden aangeduid. */
    private final String code;
    /** Omschrijving van de soort betrokkenheid. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param code De (functionele) code waarmee de Soort betrokkenheid kan worden aangeduid.
     * @param naam Omschrijving van de soort betrokkenheid.
     *
     */
    private SoortBetrokkenheid(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * @return De (functionele) code waarmee de Soort betrokkenheid kan worden aangeduid.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Omschrijving van de soort betrokkenheid.
     */
    public String getNaam() {
        return naam;
    }

}
