/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;

/**
 * De Service Endpoint Interface voor bijhoudingen.
 */
public interface BijhoudingService {

    /**
     * Dit is de webservice methode voor het verwerken van bijhoudings berichten. De verwerking van de berichten begint
     * in deze methode.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    BerichtResultaat bijhouden(BRPBericht bericht);

}
