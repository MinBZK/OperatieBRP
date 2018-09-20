/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import java.math.BigInteger;


/**
 * Implementatie van de AutenticatieService, deze zoekt het authenticatiemiddel waarmee een partij zich authenticeert
 * op basis van de identificerende certificaat gegevens.
 */
public interface AutenticatieService {

    /**
     * DTO van de class.
     * @author bhuism
     *
     */

    public class AuthenticatieMiddelDTO {

        private final Long authenticatieMiddelId;
        private final Long authenticatieMiddelPartijId;

        /**
         * DTO Constructor.
         *
         * @param authenticatieMiddelId id van het authenticatieMiddel.
         * @param authenticatieMiddelPartijId id van de partij van het authenticatieMiddel.
         */

        public AuthenticatieMiddelDTO(final Long authenticatieMiddelId, final Long authenticatieMiddelPartijId) {
            this.authenticatieMiddelId = authenticatieMiddelId;
            this.authenticatieMiddelPartijId = authenticatieMiddelPartijId;
        }

        public Long getAuthenticatieMiddelId() {
            return authenticatieMiddelId;
        }

        public Long getAuthenticatieMiddelPartijId() {
            return authenticatieMiddelPartijId;
        }

    }

    /**
     * Zoek een authenticatiemiddel, met daarin de partij o.b.v. certificaat parameters, signature komt binnen als
     * byte[] en wordt geconverteerd naar String met commons-codec.
     *
     * @param serial Serial nummer van het certificaat
     * @param signature Het signature van het certificaat
     * @param subject Subject van het certificaat
     * @return gevonden authenticatiemiddel
     */
    AuthenticatieMiddelDTO zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(BigInteger serial,
            byte[] signature, String subject);

}
