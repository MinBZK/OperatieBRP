/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

/**
 * De richting van een bericht (vanuit het zichtpunt van ISC).
 */
public enum Direction {
    /** Inkomend. */
    INKOMEND("I"),
    /** Uitgaand. */
    UITGAAND("U");

    private final String code;

    /**
     * Constructor.
     * 
     * @param code
     *            code
     */
    Direction(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Zoek de richting obv de code.
     * 
     * @param code
     *            code
     * @return richting, of null als niet gevonden
     */
    public static Direction valueOfCode(final String code) {
        for (final Direction direction : values()) {
            if (code.equals(direction.getCode())) {
                return direction;
            }
        }

        return null;
    }
}
