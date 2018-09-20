/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;


/**
 * BRP verantwoordelijke. Dit is een enum omdat 'Verantwoordelijke' een statische stamtabel is in de BRP.
 */
public enum BrpVerantwoordelijkeCode {

    /** College van burgemeester en wethouders. */
    COLLEGE_B_EN_W("C"),
    /** Minister. */
    MINISTER("M");

    private final String code;

    private BrpVerantwoordelijkeCode(final String code) {
        if (code == null) {
            throw new NullPointerException();
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Vertaalt de gegeven BRP code in een BrpVerantwoordelijkeCode.
     * 
     * @param code
     *            code
     * @return een BrpVerantwoordelijkeCode object
     */
    public static final BrpVerantwoordelijkeCode valueOfCode(final String code) {
        if (code == null) {
            return null;
        }

        for (final BrpVerantwoordelijkeCode value : values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Onbekende BRP code: " + code);
    }
}
