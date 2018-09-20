/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import javax.inject.Inject;

/**
 *
 */
public abstract class AbstractConverteerder {

    @Inject
    private Lo3AttribuutConverteerder lo3AttribuutConverteerder;
    @Inject
    private ConverteerderUtils utils;

    /**
     * Geef de waarde van lo3 attribuut converteerder.
     *
     * @return lo3 attribuut converteerder
     */
    public final Lo3AttribuutConverteerder getLo3AttribuutConverteerder() {
        return lo3AttribuutConverteerder;
    }

    /**
     * Geef de waarde van utils.
     *
     * @return utils
     */
    public final ConverteerderUtils getUtils() {
        return utils;
    }
}
