/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;

/**
 * VrijBerichtBerichtFactory.
 */
interface VrijBerichtBerichtFactory {
    /**
     * Maak het {@link VrijBerichtAntwoordBericht}.
     * @param resultaat resultaat
     * @return het bericht
     */
    VrijBerichtAntwoordBericht maakAntwoordBericht(VrijBerichtResultaat resultaat);

    /**
     * Maak het {@link VrijBerichtVerwerkBericht}.
     * @param resultaat resultaat
     * @param ontvangendePartij ontvangende partij
     * @param zenderVrijBericht zender vrij bericht
     * @return het verwerk bericht
     */
    VrijBerichtVerwerkBericht maakVerwerkBericht(VrijBerichtResultaat resultaat, final Partij ontvangendePartij, final Partij zenderVrijBericht);
}
