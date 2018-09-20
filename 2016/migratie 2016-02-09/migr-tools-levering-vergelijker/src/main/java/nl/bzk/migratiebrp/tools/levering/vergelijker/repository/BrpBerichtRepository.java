/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository;

import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.BrpBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieBrp;

/**
 * De BrpBericht Repository.
 */
public interface BrpBerichtRepository {

    /**
     * Haalt het BRP bijhoudingsbericht op.
     *
     * @param leveringsVergelijkingBerichtCorrelatieBrp
     *            Het leveringBericht met de id van het op te halen bericht.
     * @return Het opgehaalde bericht.
     */
    BrpBericht haalBrpBijhoudingsBerichtOp(LeveringsVergelijkingBerichtCorrelatieBrp leveringsVergelijkingBerichtCorrelatieBrp);

    /**
     * Haalt het BRP leveringbericht op.
     *
     * @param leveringsVergelijkingBerichtCorrelatieBrp
     *            Het leveringBericht met de id van het op te halen bericht.
     * @return Het opgehaalde bericht.
     */
    BrpBericht haalBrpLeveringBerichtOp(LeveringsVergelijkingBerichtCorrelatieBrp leveringsVergelijkingBerichtCorrelatieBrp);

}
