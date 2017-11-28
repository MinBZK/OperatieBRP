/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;

/**
 * Interface voor de BRP Bijhouding Business service.
 */
public interface BijhoudingService {

    /**
     * Verwerkt het bijhoudingsbericht afkomstig uit het BRP-stelsel.
     *
     * @param bericht
     *            het bericht
     * @return het antwoordbericht
     */
    BijhoudingAntwoordBericht verwerkBrpBericht(BijhoudingVerzoekBericht bericht);

    /**
     * Verwerkt het bijhoudingsbericht afkomstig uit het GBA-stelsel.
     *
     * @param bericht
     *            het bericht
     * @return het antwoordbericht
     */
    BijhoudingAntwoordBericht verwerkGbaBericht(BijhoudingVerzoekBericht bericht);
}
