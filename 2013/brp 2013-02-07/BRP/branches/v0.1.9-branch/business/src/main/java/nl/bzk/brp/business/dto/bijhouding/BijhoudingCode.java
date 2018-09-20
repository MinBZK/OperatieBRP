/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

/**
 * Enumeratie die aangeeft of de bijhouding direct is verwerkt of dat de verwerking (in verband met bijvoorbeeld
 * fiatering) is uitgesteld.
 */
public enum BijhoudingCode {

    /** Bijhouding direct verwerkt (mogelijk I/W/O-meldingen en/of 'Overrules' in response opgenomen. */
    DIRECT_VERWERKT("V"),
    /**
     * Bijhouding uitgesteld(mogelijk I/W/O-meldingen en/of'Overrules';altijd uitgezette bijhoudingsverzoeken
     * in response opgenomen.
     */
    UITGESTELD("U");


    private final String code;

    /**
     * Standaard constructor die de code van de enumeratie initialiseert.
     *
     * @param code de code van de enumeratie.
     */
    private BijhoudingCode(final String code) {
        this.code = code;
    }

    /**
     * Retourneert de code van de enumeratie.
     *
     * @return de code van de enumeratie.
     */
    public String getCode() {
        return code;
    }
}
