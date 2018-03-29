/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Datas({@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER),
        @Data(resources = "classpath:/data/ber.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_ARCHIEVERING)})
public class LeveringsautorisatieControllerIT extends AbstractDatabaseTest {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testLeveringsautorisaties() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpStelsel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterStelsel", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpModelautorisatie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterModelAutorisatie", "Ja"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpDatumIngang() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterDatumIngang", "20150101"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(4)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpDatumEinde() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterDatumEinde", "20161231"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpGeautoriseerde() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterGeautoriseerde", "20"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpOndertekenaar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterOndertekenaar", "19"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpTransporteur() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterTransporteur", "19"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpDienstbundel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterDienstbundel", "Spontaan"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpDienstbundelHoofdletters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterDienstbundel", "SpOnT"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpGeblokkeerd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterGeblokkeerd", "Nee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void filterLeveringsautorisatieOpSoortDienst() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leveringsautorisaties/").param("filterSoortDienst", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }
}
