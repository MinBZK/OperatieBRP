/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;

/**
 * Met deze service kan de synchronisatie strategie worden bepaald. Hiermee kan bijvoorbeeld worden bepaald of o.b.v.
 * van een synchronisatieverzoek een nieuw persoon in de BRP moet worden toegevoegd of een bestaand persoon moet worden
 * vervangen.
 */
public interface SynchronisatieStrategieService {

    /**
     * Verwerkt het verzoek tot het bepalen van de synchronisatie strategie en retourneerd het antwoord.
     * 
     * @param synchronisatieStrategieVerzoekBericht
     *            het bericht maar daarin de gegevens nodig om de strategie te bepalen
     * @return het antwoordbericht met daarin de te volgen synchronisatie strategie
     */
    SynchronisatieStrategieAntwoordBericht verwerkSynchronisatieStrategieVerzoek(
            SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht);
}
