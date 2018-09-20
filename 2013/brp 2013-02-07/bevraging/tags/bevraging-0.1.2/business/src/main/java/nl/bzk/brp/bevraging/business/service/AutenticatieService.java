/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import java.math.BigInteger;

import nl.bzk.brp.bevraging.domein.Partij;

/**
 * Implementatie van de AutenticatieService, deze zoekt de partij op basis van de identificerende certificaat gegevens.
 */

public interface AutenticatieService {


    /**
     * Zoek een partij o.b.v. certificaat parameters, signature komt binnen als byte[] en wordt geconverteerd naar
     * String met commons-codec.
     *
     * @param serial Serial nummer van het certificaat
     * @param signature Het signature van het certificaat
     * @param subject Subject van het certificaat
     * @return gevonden partij
     */

    Partij zoekPartijMetOndertekeningCertificaat(BigInteger serial, byte[] signature, String subject);
}
