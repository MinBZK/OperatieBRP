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
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {WebConfiguratie.class, RepositoryConfiguratie.class, BlobifierConfiguratie.class, DummySecurityConfiguratie.class})
@Data(resources = {"classpath:/data/administratievehandeling.xml"}, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class AdministratieveHandelingControllerTest extends AbstractDatabaseTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testAdministratieveHandelingen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andReturn();

    }

    @Test
    public void testAdministratieveHandelingFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("soort", "38").param("partij", "2000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(4)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterPartijCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("partijCode", "199902"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterPartijNaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("partijNaam", "Migratie"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumBegin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("tijdstipRegistratieBegin", "201505011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumEinde() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("tijdstipRegistratieEinde", "201506011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumBeginEnEinde() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/administratievehandelingen/")
                        .param("tijdstipRegistratieBegin", "201505011200")
                        .param("tijdstipRegistratieEinde", "201506011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(6)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumBeginLevering() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("tijdstipLeveringBegin", "201505011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumEindeLevering() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("tijdstipLeveringEinde", "201506011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingFilterDatumBeginEnEindeLevering() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/administratievehandelingen/")
                        .param("tijdstipLeveringBegin", "201505011200")
                        .param("tijdstipLeveringEinde", "201506011200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandeling() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/{id}", 5L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iD").value(5))
                .andReturn();
    }

    @Test
    public void testAdministratieveHandelingNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/{id}", 99999L)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testZoekenOpBSN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("bsn", "334801977"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void testZoekenOpAnummer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/").param("anummer", "7568727841"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    public void testHaalActieOp() throws Exception {
        final MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/administratievehandelingen/{aid}/acties/{id}", 2, 11))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        // .andExpect(MockMvcResultMatchers.jsonPath("$.wijzigingen").exists())
        // .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
