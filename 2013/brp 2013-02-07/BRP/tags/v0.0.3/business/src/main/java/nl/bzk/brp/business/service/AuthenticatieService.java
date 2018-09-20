/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.security.cert.X509Certificate;

/**
 * Service die zorg draagt voor het authenticeren van bijhoudingspartijen op basis van het X509 certificaat dat zij
 * gebruiken.
 */
public interface AuthenticatieService {

    /**
     * Controleert of een partij geauthenticeerd is om de BRP Service te gebruiken.
     * Een partij is geauthenticeerd als ze met haar certificaat bekend is in de BRP.
     *
     * @param partijId Id van de bijhoudende partij.
     * @param certificaat X509 certificaat van de partij.
     * @return true indien de partij en het certificaat bekend zijn in de BRP, anders false.
     */
    boolean isPartijGeauthenticeerdInBRP(final Integer partijId, final X509Certificate certificaat);

}
