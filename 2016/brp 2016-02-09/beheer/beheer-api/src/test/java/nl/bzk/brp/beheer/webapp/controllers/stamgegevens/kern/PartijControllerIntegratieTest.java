/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.brp.beheer.webapp.controllers.AbstractControllerIntegratieTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PartijControllerIntegratieTest extends AbstractControllerIntegratieTest {

    @Test
    public void testValidatie() throws Exception {
        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/partij/")
                        .content("{\"Code\":19000101,\"Datum ingang\":19000101,\"Datum einde\":19000102}")
                        .contentType(MediaType.APPLICATION_JSON);

        final MvcResult result = mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testOpslag() throws Exception {
        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/partij/")
                    .content("{\"iD\":2212,\"Code\":602601,\"Naam\":\"'t Lange Land Ziekenhuis\",\"Datum ingang\":20040601,\"Automatisch fiatteren?\":\"Nee\",\"Verstrekkingsbeperking mogelijk?\":\"Ja\",\"Datum overgang naar BRP\":20150101}")
                    .contentType(MediaType.APPLICATION_JSON);
        final MvcResult result = mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testNietExactZoeken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterNaam", "amsterDAM"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(9))
                .andReturn();
    }

    @Test
    public void testSorteren() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterNaam", "amsterDAM").param("sort", "code"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
