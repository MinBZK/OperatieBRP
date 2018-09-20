/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze enum representeert een BRP Reden opschorting bijhouding. Dit is een statische stamtabel en daarom een enum en
 * geen class.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public enum BrpRedenOpschortingBijhoudingCode implements BrpAttribuut {

    /** Overlijden. */
    OVERLIJDEN("O"),

    /** Ministerieel besluit. */
    MINISTERIEEL_BESLUIT("M"),

    /** Fout. */
    FOUT("F"),

    /** Onbekend. */
    ONBEKEND("?");

    private final String brpCode;

    /**
     * Maakt een BrpRedenOpschortingBijhoudingCode.
     * 
     * @param brpCode
     *            BRP code
     */
    BrpRedenOpschortingBijhoudingCode(final String brpCode) {
        this.brpCode = brpCode;
    }

    /**
     * @return the brpCode
     */
    public String getCode() {
        return brpCode;
    }

    /**
     * Vertaalt de gegeven BRP code in een BrpRedenOpschortingBijhoudingCode.
     * 
     * @param code
     *            code
     * @return een BrpRedenOpschortingBijhoudingCode object
     */
    public static final BrpRedenOpschortingBijhoudingCode valueOfCode(final String code) {
        if (code == null) {
            return null;
        }

        for (final BrpRedenOpschortingBijhoudingCode value : values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Onbekende BRP code: " + code);
    }
}
