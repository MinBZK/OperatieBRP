/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;

/**
 * Utility methoden voor gebruik bij de foutafhandeling.
 */
public final class FoutUtil {

    /**
     * Constante met melding voor onverwachte berichten.
     */
    public static final String ONVERWACHT_BERICHT_MELDING = "Bericht is niet verwacht binnen het proces (lo3 cyclus)";

    private static final int MAX_LENGTE_FOUTMELDING = 4000;
    private static final String AFKAP_MELDING = " ... <afgekapt>";

    private FoutUtil() {
        // Niet instantieerbaar
    }

    /**
     * Bepaalt of er sprake is van een cyclusfout.
     * @param bericht Het binnengekomen bericht.
     * @return True indien het bericht een cyclusfout veroorzaakt, false in alle andere gevallen.
     */
    public static Boolean bepaalIndicatieCyclusFout(final Lo3Bericht bericht) {
        return !(bericht instanceof OnbekendBericht) && !(bericht instanceof AbstractOngeldigLo3Bericht);
    }

    /**
     * Bepaalt op basis van het bericht welke foutmelding er getoond wordt.
     * @param bericht Het binnengekomen bericht.
     * @return De foutmelding.
     */
    public static String bepaalFoutmelding(final Lo3Bericht bericht) {
        final String result;
        if (bericht instanceof OnbekendBericht) {
            result = ((OnbekendBericht) bericht).getMelding();
        } else if (bericht instanceof AbstractOngeldigLo3Bericht) {
            result = ((AbstractOngeldigLo3Bericht) bericht).getMelding();
        } else {
            result = ONVERWACHT_BERICHT_MELDING;
        }
        return result;
    }

    /**
     * Beperk de gegeven foutmelding tot een lengte die JBPM accepteert.
     * @param foutmelding foutmelding
     * @return eventueel afgekapte foutmelding
     */
    public static String beperkFoutmelding(final String foutmelding) {

        final String foutmeldingDoubleByteEnters = foutmelding == null ? null : foutmelding.replaceAll("\n", "\r\n");

        if (foutmeldingDoubleByteEnters == null || foutmeldingDoubleByteEnters.length() <= MAX_LENGTE_FOUTMELDING) {
            return foutmelding;
        }

        return foutmeldingDoubleByteEnters.substring(0, MAX_LENGTE_FOUTMELDING - AFKAP_MELDING.length()) + AFKAP_MELDING;
    }

}
