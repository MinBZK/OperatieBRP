/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import nl.bzk.brp.preview.model.Bericht;

/**
 * Service voor toegang tot adminstratieve handeling.
 */
public interface AdministratieveHandelingService {

    /**
     * Gegeven de ID van een administratieve handeling, maak een (dashboard) bericht.
     *
     * @param handelingId de identificatie van een AH
     * @return een bericht dat de gegevens van een AH bevat
     */
    Bericht maakBericht(Long handelingId);

    /**
     * Sla een bericht op.
     *
     * @param bericht het bericht om op te slaan
     */
    void opslaan(Bericht bericht);

}
