/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import org.apache.commons.lang3.StringUtils;

/**
 * BsnValidatie.
 */
public final class BsnValidator {

    private static final int BSN_LENGTE = 9;
    private static final int ELF = 11;

    private BsnValidator() {

    }

    /**
     * Controleert of het bsn een geldig BSN nummer is.
     * @param bsn de bsn
     * @return true als het een geldige bsn is
     */
    public static boolean isGeldigeBsn(final String bsn) {
        boolean resultaat = false;
        if (bsn.length() == BSN_LENGTE && StringUtils.isNumeric(bsn)) {
            int som = 0;

            for (int i = 0; i < bsn.length() - 1; i++) {
                som += Character.getNumericValue(bsn.charAt(i)) * (BSN_LENGTE - i);
            }

            som += Character.getNumericValue(bsn.charAt(bsn.length() - 1)) * -1;

            if (som % ELF == 0) {
                resultaat = true;
            }
        }

        return resultaat;
    }
}
