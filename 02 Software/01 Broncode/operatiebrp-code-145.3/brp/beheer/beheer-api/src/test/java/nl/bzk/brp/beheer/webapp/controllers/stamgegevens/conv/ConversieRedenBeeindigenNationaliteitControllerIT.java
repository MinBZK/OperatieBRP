/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test ConversieLO3RubriekController.
 */
public class ConversieRedenBeeindigenNationaliteitControllerIT extends AbstractIntegratieTest {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * init env.
     */
    @Before
    public final void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test filter.
     * @throws Exception ex
     */
    @Test
    public final void testConversieLO3RubriekFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/conversieredenbeeindigennationaliteit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(10)))
                .andReturn();
    }

}
