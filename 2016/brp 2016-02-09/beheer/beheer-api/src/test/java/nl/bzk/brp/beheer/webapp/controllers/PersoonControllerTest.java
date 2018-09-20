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
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguratie.class, RepositoryConfiguratie.class, BlobifierConfiguratie.class, DummySecurityConfiguratie.class})
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class PersoonControllerTest extends AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testPersonenZonderZoekenIsLeeg() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(0))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpBsn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("bsn", "123456789"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpAnr() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("anr", "1234567890"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpAfgeleid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("afgeleid", "Ja"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpNamenreeks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("namenreeks", "Ja"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpPredicaat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("predicaat", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
                .andReturn();
    }

    @Test
    // Genegeerd. Zoeken op alles behalve Graaf lijkt goed te werken.
    public void testPersonenZoekOpAdellijkeTitel() throws Exception {
        LOG.info("adellijke titel");
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("adellijketitel", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
        LOG.info("einde");
    }

    @Test
    public void testPersonenZoekOpVoornamen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Immanuel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoorvoegsel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voorvoegsel", "de"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpScheidingsteken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("scheidingsteken", "-"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeslachtsnaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geslachtsnaamstam", "Bieren"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboortedatum() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortedatum", "12120205"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortegemeente", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboortewoonplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortewoonplaats", "1302"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteBuitenlandseplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortebuitenlandseplaats", "Buitenlandse plaats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteBuitenlandseregio() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortebuitenlandseregio", "Buitenladse regio"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteland() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboorteland", "9062"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoornamenZonderAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Maria"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoornamenMetAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Mariá"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andReturn();
    }
}
