/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test ConversieLO3RubriekController.
 */
public class ConversieLo3RubriekControllerIT extends AbstractIntegratieTest {

    /**
     * Test filter.
     *
     * @throws Exception
     *             ex
     */
    @Test
    public final void testConversieLO3RubriekFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/conversielo3rubriek").param("naam", "01.20.10"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
               .andReturn();
    }

}
