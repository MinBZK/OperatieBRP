/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.util.regex.Pattern;

/**
 * LO3 header veld.
 */
public enum Lo3HeaderVeld {

    /** (Nieuwe) A-nummer. */
    A_NUMMER(10, Type.NUMERIEK),
    /** Berichtnummer. */
    BERICHTNUMMER(4, Type.ALPHANUMERIEK),
    /** Datum. */
    DATUM(8, Type.NUMERIEK),
    /** Datum/Tijd. */
    DATUM_TIJD(17, Type.NUMERIEK),
    /** Foutreden. */
    FOUTREDEN(1, Type.ALPHANUMERIEK),
    /** Gemeente. */
    GEMEENTE(4, Type.NUMERIEK),
    /** Herhaling. */
    HERHALING(1, Type.NUMERIEK),
    /** Oud A-nummer. */
    OUD_A_NUMMER(10, Type.NUMERIEK),
    /** Random key. */
    RANDOM_KEY(8, Type.NUMERIEK),
    /** Status. */
    STATUS(1, Type.ALPHANUMERIEK),
    /** Gezochte persoon. */
    GEZOCHTE_PERSOON(1, Type.ALPHANUMERIEK),
    /** Aktenummer. */
    AKTENUMMER(7, Type.ALPHANUMERIEK),
    /** Lengte bericht (Vrij bericht alleen). */
    LENGTE_BERICHT(5, Type.NUMERIEK),
    /** Berichtinhoud (Vrij bericht alleen). */
    BERICHT(99999, Type.ALPHANUMERIEK),
    /** Aantal (Tb02 bericht). */
    AANTAL(3, Type.NUMERIEK),
    /** Identificerende rubrieken (Tb02 bericht). */
    IDENTIFICERENDE_RUBRIEKEN(48, Type.ALPHANUMERIEK);

    private final int size;
    private final Type type;

    private Lo3HeaderVeld(final int size, final Type type) {
        this.size = size;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    /**
     * Controleer of de header syntactisch valide is (geen lengte controle).
     * 
     * @param value
     *            waarde
     * @return true als de header syntactisch valide is, anders false
     */
    public boolean isValide(final String value) {
        return type.isValide(value);
    }

    /**
     * Formateer een header veld.
     * 
     * @param value
     *            veld waarde
     * @return geformateerd veld waarde (vaste lengte)
     */
    public String format(final String value) {
        return type.format(value, size);
    }

    /**
     * Header veld type.
     */
    private enum Type {
        /** Numeriek. */
        NUMERIEK(Pattern.compile("^[0-9]*$")) {
            @Override
            String format(final String value, final int size) {
                if (value != null && value.length() > size) {
                    throw new IllegalArgumentException("Te lange waarde voor numeriek header veld.");
                }

                final StringBuilder result = new StringBuilder(size);
                if (value != null) {
                    result.append(value);
                }

                while (result.length() < size) {
                    result.insert(0, '0');
                }

                return result.toString();
            }
        },

        /** Alphanumeriek. */
        ALPHANUMERIEK(Pattern.compile("^[A-Za-z0-9 ]*$")) {

            @Override
            String format(final String value, final int size) {
                if (value != null && value.length() > size) {
                    throw new IllegalArgumentException("Te lange waarde voor alphanumeriek header veld (lengte="
                            + size + ", waarde=" + value + ").");
                }

                final StringBuilder result = new StringBuilder(size);
                if (value != null) {
                    result.append(value);
                }

                while (result.length() < size) {
                    result.append(' ');
                }

                return result.toString();
            }
        };

        private final Pattern pattern;

        private Type(final Pattern pattern) {
            this.pattern = pattern;
        }

        abstract String format(final String value, final int size);

        /**
         * Geeft aan of de waarde geldig is voor dit headerveld.
         * 
         * @param value
         *            De waarde die gecontroleerd dient te worden.
         * @return True indien de waarde geldig is, false in alle andere gevallen.
         */
        public boolean isValide(final String value) {
            return pattern.matcher(value).matches();
        }
    }
}
