/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.webservice;


import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;

/**
 * De Synchronisatie webservice interface voor het synchroniseren van personen en stamtabellen van de BRP.
 */
public interface SynchronisatieService {

    /**
     * Verwerk het verzoek tot synchronisatie van een persoon.
     *
     * @param vraag het request bericht
     * @return het antwoord op de vraag
     */
    GeefSynchronisatiePersoonAntwoordBericht geefSynchronisatiePersoon(GeefSynchronisatiePersoonBericht vraag);

    /**
     * Verwerk het verzoek tot synchronisatie van een stamgegeven.
     *
     * @param vraag het request bericht
     * @return antwoord bericht
     */
    GeefSynchronisatieStamgegevenAntwoordBericht geefSynchronisatieStamgegeven(
            GeefSynchronisatieStamgegevenBericht vraag);
}
