/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PartijControllerIT extends AbstractIntegratieTest {

    @Test
    public void testValidatie() throws Exception {
        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/partij/").content("{\"Code\":19000101,\"Datum ingang\":19000101,\"Datum einde\":19000102}").contentType(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
    }

    @Test
    public void testOpslag() throws Exception {
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/partij/")
                .content(
                        "{\"code\":602699,\"naam\":\"'t Lange Land Ziekenhuis\",\"oin\":\"oin123\",\"datumIngang\":20010601,\"Automatisch "
                                + "automatischFiatteren\":\"Ja\",\"verstrekkingsbeperkingMogelijk\":\"Ja\",\"datumOvergangNaarBrp\":20150101}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void testNietExactZoeken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterNaam", "amsterDAM"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
                .andReturn();
    }

    @Test
    public void testSorteren() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterNaam", "amsterDAM").param("sort", "code"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterCode", "123456")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void zoekenOpDatumIngang() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterDatumIngang", "20160101"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpDatumEinde() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterDatumEinde", "20160101"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpDatumOvergangNaarBrp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterDatumOvergangNaarBrp", "20160101"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpIndicatie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterIndicatie", "Ja"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpIndicatieAutomatischFiatteren() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterIndicatieAutomatischFiatteren", "Ja"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpNaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterNaam", "Gemeente Amsterdam"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void zoekenOpOin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterOin", "12")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void zoekenOpPartijRol() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterPartijRol", "1")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void zoekenOpSoortPartij() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/partij/").param("filterSoortPartij", "De Goede"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
