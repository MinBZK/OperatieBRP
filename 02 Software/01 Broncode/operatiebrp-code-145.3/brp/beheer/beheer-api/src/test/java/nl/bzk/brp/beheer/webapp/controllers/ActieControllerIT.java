/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class })
@Datas({@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER),
        @Data(resources = "classpath:/data/ber.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_ARCHIEVERING) })
public class ActieControllerIT extends AbstractDatabaseTest {

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
     * Test acties.
     *
     * @throws Exception
     *             ex
     */
    @Test
    public final void testActies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actie/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(4))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(4)))
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/actie/").param("sort", "ID,desc").param("page", "3"))
               .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    /**
     * Test filters.
     *
     * @throws Exception
     *             ex
     */
    @Test
    public final void testActiesFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actie").param("administratieveHandeling", "1000").param("partij", "1").param("soort", "1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(4))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(4)))
               .andReturn();
    }

    /**
     * test actie.
     *
     * @throws Exception
     *             ex
     */
    @Test
    public final void testActie() throws Exception {
        final MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/actie/{id}", 101))
                       .andExpect(MockMvcResultMatchers.status().isOk())
                       .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                       .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(101))
                       .andReturn();
        LoggerFactory.getLogger().info(result.getResponse().getContentAsString());
    }

    /**
     * test actie niet gevonden.
     *
     * @throws Exception
     *             ex
     */
    @Test
    public final void testActieNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actie/{id}", 99999L)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
