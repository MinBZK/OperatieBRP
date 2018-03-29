/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import java.util.Arrays;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

/**
 * ANummerValidator.
 */
public final class ANummerValidator {

    private static final int ANUMMER_LENGTE = 10;
    private static final int ELF = 11;
    private static final int REST_NUL = 0;
    private static final int REST_VIJF = 5;
    private static final int MACHT_2 = 2;

    private ANummerValidator() {
    }

    /**
     * Controleert of administratienummer geldig is (definitieregel R1585).
     * @param aNummer administratienummer
     * @return true als anummer geldig is
     */
    public static boolean isGeldigANummer(final String aNummer) {
        return formaatIsGeldig(aNummer) && deelRegelsZijnGeldig(aNummer);
    }

    private static boolean formaatIsGeldig(final String aNummer) {
        return aNummer != null && aNummer.length() == ANUMMER_LENGTE && StringUtils.isNumeric(aNummer);
    }

    private static boolean deelRegelsZijnGeldig(final String aNummer) {
        return deelregel1IsGeldig(aNummer)
                && deelregel2IsGeldig(aNummer)
                && deelregel3IsGeldig(aNummer)
                && deelregel4IsGeldig(aNummer);
    }

    // a[0] <> 0
    private static boolean deelregel1IsGeldig(final String aNummer) {
        return Character.getNumericValue(aNummer.charAt(0)) != 0;
    }

    // getal[i] <> getal[i+1]
    private static boolean deelregel2IsGeldig(final String aNummer) {

        boolean resultaat = true;
        for (int i = 0; i < (ANUMMER_LENGTE - 1); i++) {
            resultaat =
                    Character.getNumericValue(aNummer.charAt(i)) != Character.getNumericValue(aNummer.charAt(i + 1));
            if (!resultaat) {
                break;
            }
        }
        return resultaat;
    }

    //a[0]+a[1]+.+a[9] gedeeld door 11 geeft rest 0 of 5
    private static boolean deelregel3IsGeldig(final String aNummer) {

        boolean resultaat = true;
        int som = 0;
        for (int i = 0; i < ANUMMER_LENGTE; i++) {
            som += Character.getNumericValue(aNummer.charAt(i));
        }
        if (!(som % ELF == REST_NUL || som % ELF == REST_VIJF)) {
            resultaat = false;
        }
        return resultaat;
    }

    //(1*a[0])+(2*a[1])+(4*a[2])+.+(512*a[9]) is deelbaar door 11
    private static boolean deelregel4IsGeldig(final String aNummer) {
        boolean resultaat = true;
        int som = 0;
        for (int i = 0; i < ANUMMER_LENGTE; i++) {
            som += Math.pow(MACHT_2, i) * Character.getNumericValue(aNummer.charAt(i));
        }
        if (som % ELF != 0) {
            resultaat = false;
        }
        return resultaat;
    }

}
