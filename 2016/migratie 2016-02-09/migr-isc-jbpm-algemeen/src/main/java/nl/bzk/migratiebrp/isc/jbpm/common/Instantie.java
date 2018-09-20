/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

/**
 * Instantie helper.
 */
public final class Instantie {

    private static final int LENGTE_GEMEENTE = 4;
    private static final int LENGTE_AFNEMER = 6;
    private static final int LENGTE_CENTRALE = 7;

    private Instantie() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal instantie type.
     *
     * @param code
     *            instantie code
     * @return type
     */
    public static Type bepaalInstantieType(final String code) {
        final int length = code == null ? 0 : code.length();

        final Type result;
        switch (length) {
            case LENGTE_CENTRALE:
                result = Type.CENTRALE;
                break;
            case LENGTE_AFNEMER:
                result = Type.AFNEMER;
                break;
            case LENGTE_GEMEENTE:
                result = Type.GEMEENTE;
                break;
            default:
                result = null;
        }

        return result;
    }

    /**
     * Instantie type.
     */
    public static enum Type {

        /** Gemeente. */
        GEMEENTE,

        /** Afnemer. */
        AFNEMER,

        /** Centrale voorziening. */
        CENTRALE;
    }

}
