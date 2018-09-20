/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service;


import java.util.List;

/**
 * De interface voor het maken van JMS berichten.
 */
public interface JmsService {

    /**
     * Creeer en publiceer JMS berichten voor personen.
     *
     * @param persoonIdLijst De lijst met persoon ids.
     */
    void creeerEnPubliceerJmsBerichtenVoorPersonen(List<Integer> persoonIdLijst);

    /**
     * Creeer en publiceer JMS berichten voor alle personen. De database zal bevraagd worden in batches.
     *
     * @param aantalIdsPerBatch           Het aantal persoon id's per batch.
     * @param tijdInSecondenTussenBatches De wachttijd aan het einde van iedere batch.
     * @param vanafPersoonId Het persoon id vanaf waar te beginnen.
     */
    void creeerEnPubliceerJmsBerichtenVoorAllePersonen(Integer aantalIdsPerBatch, Integer tijdInSecondenTussenBatches,
                                                       Integer vanafPersoonId);

}
