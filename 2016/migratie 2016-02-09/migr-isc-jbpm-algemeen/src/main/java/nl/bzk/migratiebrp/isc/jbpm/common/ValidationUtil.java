/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

/**
 * Utilities for validations.
 */
public final class ValidationUtil {

    private static final int REST_NUL = 0;
    private static final int REST_VIJF = 5;
    private static final int MODULO_ELF = 11;
    private static final Integer LENGTE_ANUMMER = 10;

    private ValidationUtil() {
        // Niet instantieerbaar
    }

    /**
     * Valideert het aNummer op de elf proef.
     *
     * @param aNummer
     *            String
     * @return boolean true als het aNummer correct, false als het een foutief aNummer betreft.
     */
    public static boolean valideerANummer(final String aNummer) {

        if (!checkAnummerInhoud(aNummer)) {
            return false;
        }

        final byte[] asBytes = new byte[LENGTE_ANUMMER];
        for (int i = 0; i < LENGTE_ANUMMER; i++) {
            asBytes[i] = Byte.valueOf(aNummer.substring(i, i + 1));
        }

        boolean checksumsOk = true;

        int som = 0;
        int multiplySom = 0;

        for (int i = 0; i < LENGTE_ANUMMER; i++) {
            if (i > 0 && asBytes[i] == asBytes[i - 1]) {
                // 2 opeenvolgende cijfers zijn ongelijk
                checksumsOk = false;
            }
            // a0+a1+...+a9 is deelbaar door 11; rest 0 of 5
            som += asBytes[i];

            // (1*a0)+(2*a1)+(4*a2)+...+(512*a9) is deelbaar door 11
            multiplySom += Math.pow(2, i) * asBytes[i];

        }

        if (som % MODULO_ELF != REST_NUL && som % MODULO_ELF != REST_VIJF) {
            checksumsOk = false;
        }

        if (multiplySom % MODULO_ELF != REST_NUL) {
            checksumsOk = false;
        }

        return checksumsOk;
    }

    private static boolean checkAnummerInhoud(final String aNummer) {
        boolean heeftGeldigeInhoud = true;

        if (LENGTE_ANUMMER != aNummer.length()) {
            heeftGeldigeInhoud = false;
        }
        try {
            final Long asLong = Long.valueOf(aNummer);
            if (asLong < 0) {
                heeftGeldigeInhoud = false;
            }
        } catch (final NumberFormatException exceptie) {
            heeftGeldigeInhoud = false;
        }

        return heeftGeldigeInhoud;
    }
}
