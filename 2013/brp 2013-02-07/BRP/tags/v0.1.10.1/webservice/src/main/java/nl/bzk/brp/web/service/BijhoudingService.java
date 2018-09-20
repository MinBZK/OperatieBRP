/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkEnGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;


/** De Service Endpoint Interface voor bijhoudingen. */
public interface BijhoudingService {

    /**
     * Dit is de webservice methode voor het verwerken van een verhuizings bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    VerhuizingAntwoordBericht verhuizing(VerhuizingBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een verhuizings bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    CorrectieAdresNLAntwoordBericht correctieAdresNL(CorrectieAdresBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een inschrijving geboorte bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    InschrijvingGeboorteAntwoordBericht inschrijvingGeboorte(InschrijvingGeboorteBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van huwelijk en geregistreerd partnerschap.
     *
     * @param bericht het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding
     */
    HuwelijkEnGeregistreerdPartnerschapAntwoordBericht registreerHuwelijkEnPartnerschap(
            HuwelijkEnGeregistreerdPartnerschapBericht bericht);

}
