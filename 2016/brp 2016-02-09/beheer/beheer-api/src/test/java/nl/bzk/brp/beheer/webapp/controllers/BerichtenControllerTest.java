/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@ContextConfiguration(classes = {WebConfiguratie.class, RepositoryConfiguratie.class, BlobifierConfiguratie.class, DummySecurityConfiguratie.class })
@Datas({@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER),
        @Data(resources = "classpath:/data/ber.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_ARCHIEVERING) })
@Ignore
public class BerichtenControllerTest extends AbstractDatabaseTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testBerichten() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bericht/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(10)))
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/bericht/").param("sort", "ID,desc").param("page", "3")).andExpect(
            MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void testBerichtenFilter() throws Exception {
        mockMvc.perform(
                   MockMvcRequestBuilders.get("/bericht")
                                         .param("filterId", "2001")
                                         .param(
                                             "filterSoort",
                                             Integer.toString(SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP.ordinal())))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/bericht").param("filterReferentienummer", "1234567890"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
               .andReturn();
    }

    @Test
    public void testBericht() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bericht/{id}", 2001L))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$.iD").value(2001))
               // .andExpect(MockMvcResultMatchers.jsonPath("$.richting.waarde").value("INGAAND"))
               .andReturn();
    }

    @Test
    public void testBerichtNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bericht/{id}", 99999L)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
