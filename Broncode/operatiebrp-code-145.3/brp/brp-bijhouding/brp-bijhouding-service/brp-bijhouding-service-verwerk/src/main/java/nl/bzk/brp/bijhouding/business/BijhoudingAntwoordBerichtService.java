/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;

/**
 * Interface voor de BRP Bijhouding Antwoordbericht service.
 */
@FunctionalInterface
public interface BijhoudingAntwoordBerichtService {

    /**
     * Maakt het {@link BijhoudingAntwoordBericht}.
     * @param verzoekBericht het verzoek bericht
     * @param meldingen de gevonden meldingen
     * @param administratieveHandeling de administratieve handeling die uit het verzoek is ontstaan
     * @param bijhoudingsplanContext het bijhoudingsplan
     * @return een gevuld antwoordbericht
     */
    BijhoudingAntwoordBericht maakAntwoordBericht(BijhoudingVerzoekBericht verzoekBericht, final List<MeldingElement> meldingen, final
        AdministratieveHandeling administratieveHandeling, BijhoudingsplanContext bijhoudingsplanContext);
}
