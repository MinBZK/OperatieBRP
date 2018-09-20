/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonBericht;

/**
 * De Service Endpoint Interface voor bevragingen.
 */
public interface BevragingService {

    /**
     * Dit is de webservice methode voor het verwerken van bevragingen. De verwerking van de berichten begint
     * in deze methode.
     *
     * @param opvragenPersoonBericht Het bevragingsbericht wat ontvangen is.
     * @return Het resultaat van de bevraging.
     */
    BerichtResultaat opvragenPersoon(final OpvragenPersoonBericht opvragenPersoonBericht);

}
