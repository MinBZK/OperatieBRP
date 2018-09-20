/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository;

import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.GbaBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieGbav;

/**
 * De BrpBericht Repository.
 */
public interface GbaBerichtRepository {

    /**
     * Haalt het GBA bijhoudingsbericht op.
     *
     * @param leveringsVergelijkingBerichtCorrelatieGbav
     *            Het leveringBericht met de id van het op te halen bericht.
     * @return Het opgehaalde bericht.
     */
    GbaBericht haalGbaBijhoudingsBerichtOp(LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav);

    /**
     * Haalt het GBA leveringbericht op.
     *
     * @param leveringsVergelijkingBerichtCorrelatieGbav
     *            Het leveringBericht met de id van het op te halen bericht.
     * @return Het opgehaalde bericht.
     */
    GbaBericht haalGbaLeveringBerichtOp(LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav);

}
