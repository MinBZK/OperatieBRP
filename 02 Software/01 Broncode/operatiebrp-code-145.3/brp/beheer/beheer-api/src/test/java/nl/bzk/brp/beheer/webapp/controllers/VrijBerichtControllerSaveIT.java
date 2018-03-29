/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Datas({@Data(resources = "classpath:/data/testdata_vrijbericht_leeg.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)})
public class VrijBerichtControllerSaveIT extends AbstractIntegratieTest {

    private static Endpoint endpoint;

    @Inject
    private BrpJsonObjectMapper brpJsonObjectMapper;

    @BeforeClass
    public static void beforeClass() {
        // TODO Beter: http://docs.spring.io/spring-ws/site/apidocs/org/springframework/ws/test/client/MockWebServiceServer.html
        endpoint = Endpoint.publish("http://localhost:8887/vrijbericht/VrijBerichtService/vrbStuurVrijBericht?wsdl", new VrijBerichtServiceStub());
    }

    @AfterClass
    public static void afterClass() {
        endpoint.stop();
    }

    @Test
    public void testSaveVrijBericht() throws Exception {
        assertTrue(endpoint.isPublished());

        mockMvc.perform(MockMvcRequestBuilders.post("/vrijbericht")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + "  \"soortvrijber\": \"1\",\n"
                        + "  \"data\": \"Een test vrij bericht\",\n"
                        + "  \"partijen\": [\"1\", \"2\"]\n"
                        + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateVrijBericht() throws Exception {
        assertTrue(endpoint.isPublished());

        mockMvc.perform(MockMvcRequestBuilders.post("/vrijbericht")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + "  \"soortvrijber\": \"1\",\n"
                        + "  \"data\": \"Een test vrij bericht\",\n"
                        + "  \"partijen\": [\"1\", \"2\"]\n"
                        + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/vrijbericht")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        + "  \"id\": \"1\",\n"
                        + "  \"soortvrijber\": \"1\",\n"
                        + "  \"data\": \"Een gewijzigde test vrij bericht\",\n"
                        + "  \"ongelezen\": \"Ja\",\n"
                        + "  \"partijen\": [\"1\", \"2\"]\n"
                        + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
