/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;


/**
 * Interface voor de standaard bericht verwerking.
 */
public interface BerichtVerwerker {

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param bericht het bericht dat verwerkt dient te worden.
     * @return het resultaat van de bericht verwerking.
     */
    BerichtResultaat verwerkBericht(BRPBericht bericht);

}
