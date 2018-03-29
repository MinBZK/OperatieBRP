/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Datas({@Data(resources = "classpath:/data/testdata_vrijbericht.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)})
public class VrijBerichtControllerIT extends AbstractIntegratieTest {

    @Test
    public void testVrijBerichtZonderFilters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(0))
                .andReturn();

//        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht").param("sort", "ID,desc").param("page", "3"))
//               .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void testVrijBerichtMetSoortBericht() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht").param("soortBericht", "2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].soortBericht").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].datumTijdRegistratie").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].partijCode").value("199900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].soortVrijBericht").value("1"))
                .andReturn();
    }

    @Test
    public void testVrijBerichtPartij() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht/1/vrijberichtpartij"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].partijNaam").value("Onbekend"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].partijCode").value("000000"))
                .andReturn();
    }

    @Test
    public void testGeldigePartijen() throws Exception {
        // Nu nog geen geldige partijen voor vrij bericht. Moeten in de toekomst uit het BMR wel gaan komen.
        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht/geldigepartijen"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGeldigeSoortVrijBer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vrijbericht/geldigesoortvrijber"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(11)))
                .andReturn();
    }
}
