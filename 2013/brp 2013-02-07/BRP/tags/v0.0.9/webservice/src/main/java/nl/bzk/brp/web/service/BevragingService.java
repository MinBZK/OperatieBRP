/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.web.bevraging.BevragingAntwoordBericht;


/** De Service Endpoint Interface voor bevragingen. */
public interface BevragingService {

    /**
     * Dit is de webservice methode voor het verwerken van bevragingen. De verwerking van de berichten begint
     * in deze methode.
     *
     * @param opvragenPersoonBericht Het bevragingsbericht wat ontvangen is.
     * @return Het resultaat van de bevraging.
     */
    BevragingAntwoordBericht opvragenPersoon(final OpvragenPersoonBericht opvragenPersoonBericht);

}
