/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;

/**
 * Specificatie van de services die geboden worden door de SynchronisatieService.
 */
public interface SynchronisatieService {

    /**
     * Verwerkt het SynchroniseerNaarBrpVerzoek en retourneerd het antwoord.
     * 
     * @param synchroniseerNaarBrpVerzoekBericht
     *            het te verwerken verzoek
     * @param bron
     *            de bron van het synchronisatie verzoek, bijv. de queue naam
     * @param isInitieelVullingProces
     *            true als deze service draait als onderdeel van het initieel vullen proces
     * @return het SynchroniseerNaarBrpAntwoordBericht als antwoord op het verzoek
     */
    SynchroniseerNaarBrpAntwoordBericht verwerkSynchroniseerNaarBrpVerzoek(
            SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht,
            String bron,
            boolean isInitieelVullingProces);
}
