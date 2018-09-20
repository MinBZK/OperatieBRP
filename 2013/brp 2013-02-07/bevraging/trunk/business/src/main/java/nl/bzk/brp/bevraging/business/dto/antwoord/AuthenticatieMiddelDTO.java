/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

/**
 * DTO van een authenticatiemiddel.
 */
public class AuthenticatieMiddelDTO {

    private final Integer authenticatieMiddelId;
    private final Integer authenticatieMiddelPartijId;

    /**
     * DTO Constructor.
     *
     * @param authenticatieMiddelId id van het authenticatieMiddel.
     * @param authenticatieMiddelPartijId id van de partij van het authenticatieMiddel.
     */
    public AuthenticatieMiddelDTO(final Integer authenticatieMiddelId, final Integer authenticatieMiddelPartijId) {
        this.authenticatieMiddelId = authenticatieMiddelId;
        this.authenticatieMiddelPartijId = authenticatieMiddelPartijId;
    }

    public Integer getAuthenticatieMiddelId() {
        return authenticatieMiddelId;
    }

    public Integer getAuthenticatieMiddelPartijId() {
        return authenticatieMiddelPartijId;
    }

}
