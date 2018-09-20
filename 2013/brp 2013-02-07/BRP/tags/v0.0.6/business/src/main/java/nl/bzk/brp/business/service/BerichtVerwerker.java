/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtContext;


/**
 * Interface voor de standaard bericht verwerking.
 */
public interface BerichtVerwerker {

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param bericht Het bericht dat verwerkt dient te worden.
     * @param context De context waarbinnen het bericht wordt verwerkt.
     * @param resultaat Het resultaat van de bericht verwerking.
     */
    void verwerkBericht(final BRPBericht bericht, final BerichtContext context, final BerichtResultaat resultaat);

}
