/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Unit Test voor de {@link AuthenticatieMiddelDTO} class.
 */
public class AuthenticatieMiddelDTOTest {

    @Test
    public void test() {
        AuthenticatieMiddelDTO dto = new AuthenticatieMiddelDTO(2L, 3L);
        assertEquals(new Long(2), dto.getAuthenticatieMiddelId());
        assertEquals(new Long(3), dto.getAuthenticatieMiddelPartijId());
    }

}
