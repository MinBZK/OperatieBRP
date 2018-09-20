/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;

/**
 * Utility methoden voor gebruik bij de foutafhandeling.
 */
public final class FoutUtil {

    private FoutUtil() {
        // Niet instantieerbaar
    }

    /**
     * Converteer een lo3 bericht voor de afhandeling van een technische fout.
     * 
     * @param bericht
     *            bericht
     * @return bericht
     */
    public static Lo3Bericht technischeFout(final Lo3Bericht bericht) {
        if (bericht == null || bericht instanceof OnbekendBericht || bericht instanceof OngeldigBericht) {
            return bericht;
        } else {
            return new OnbekendBericht(bericht);
        }
    }
}
