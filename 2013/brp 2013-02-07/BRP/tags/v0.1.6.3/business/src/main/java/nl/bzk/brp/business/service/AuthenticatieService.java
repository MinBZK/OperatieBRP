/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.security.cert.X509Certificate;
import java.util.List;

import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;

/**
 * Service die zorgdraagt voor het authenticeren van bijhoudingspartijen op basis van het X509 certificaat dat zij
 * gebruiken.
 */
public interface AuthenticatieService {

    /**
     * Haalt de in het systeem bekende authenticatie middelen op op basis van het opgegeven certificaat en de opgegeven
     * partij id. In principe controleert deze methode dus of er wel een authenticatiemiddel bekend is met de opgegeven
     * parameters.
     *
     * @param partijId Id van de bijhoudende partij.
     * @param certificaat X509 certificaat van de partij.
     * @return true indien de partij en het certificaat bekend zijn in de BRP, anders false.
     */
    List<AuthenticatieMiddelModel> haalAuthenticatieMiddelenOp(final Integer partijId,
        final X509Certificate certificaat);

}
