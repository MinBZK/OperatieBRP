/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import nl.bzk.brp.beheer.webapp.controllers.DummySecurityConfiguratie;
import nl.bzk.brp.beheer.webapp.test.CapturingMatcher;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test voor {@link LeveringsautorisatieController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class BijhoudingsautorisatieControllerIT extends AbstractIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsertBijhoudingsautorisatie() throws Exception {
        final String postData = "{\"indicatieModelAutorisatie\" : \"Nee\"," + "\"naam\" : \"TestInsertAutorisatie\"," + "\"datumIngang\" : 20120101,"
                + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> bijhoudingsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postData).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhoudingsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + bijhoudingsautorisatieIdCaptor.getValue());

        final Map<String, Object> bijhoudingsautorisatieAfter = getBijhoudingsautorisatie(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieAfter);
        Assert.assertEquals("TestInsertAutorisatie", bijhoudingsautorisatieAfter.get("naam"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateBijhoudingsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String postDataInsert = "{\"indicatieModelAutorisatie\" : \"Nee\"," + "\"naam\" : \"testUpdateAutorisatie\"," + "\"datumIngang\" : 20120101,"
                + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> bijhoudingsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhoudingsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        final Map<String, Object> bijhoudingsautorisatieInsert = getBijhoudingsautorisatie(bijhoudingsautorisatieIdCaptor.getValue());
        final List<Map<String, Object>> bijhoudingsautorisatieSrtAdmHndInsert =
                getBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieInsert);
        Assert.assertEquals("testUpdateAutorisatie", bijhoudingsautorisatieInsert.get("naam"));
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHndInsert);
        Assert.assertEquals(0, bijhoudingsautorisatieSrtAdmHndInsert.size());

        LOG.info("Start update");
        final String postDataUpdate = "{\"id\": " + bijhoudingsautorisatieIdCaptor.getValue() + ",\"indicatieModelAutorisatie\" : \"Nee\","
                + "\"naam\" : \"testUpdateAutorisatieAangepast\"," + "\"datumIngang\" : 20110101," + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final MvcResult resultUpdate = mockMvc.perform(
                MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postDataUpdate).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result: " + resultUpdate.getResponse().getContentAsString());

        final Map<String, Object> bijhoudingsautorisatieUpdate = getBijhoudingsautorisatie(bijhoudingsautorisatieIdCaptor.getValue());
        final List<Map<String, Object>> bijhoudingsautorisatieSrtAdmHndUpdate =
                getBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieUpdate);
        Assert.assertEquals("testUpdateAutorisatieAangepast", bijhoudingsautorisatieUpdate.get("naam"));
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHndUpdate);
        Assert.assertEquals(0, bijhoudingsautorisatieSrtAdmHndUpdate.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBijhoudingsautorisatieToevoegenSrtAdmHnd() throws Exception {
        LOG.info("Start insert");
        final String postDataInsert = "{\"indicatieModelAutorisatie\" : \"Nee\"," + "\"naam\" : \"testAutorisatieSrtAdmHnd\"," + "\"datumIngang\" : 20120101,"
                + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> bijhoudingsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhoudingsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        final Map<String, Object> bijhoudingsautorisatieInsert = getBijhoudingsautorisatie(bijhoudingsautorisatieIdCaptor.getValue());
        final List<Map<String, Object>> bijhoudingsautorisatieSrtAdmHndInsert =
                getBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieInsert);
        Assert.assertEquals("testAutorisatieSrtAdmHnd", bijhoudingsautorisatieInsert.get("naam"));
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHndInsert);
        Assert.assertEquals(0, bijhoudingsautorisatieSrtAdmHndInsert.size());

        LOG.info("Start toevoegen soort administratieve handeling");
        final String postDataUpdate = "{\"bijhoudingsautorisatie\": " + bijhoudingsautorisatieIdCaptor.getValue() + "," + "\"actief\" : \"Ja\","
                + "\"soortAdministratieHandeling\" : 5," + "\"naam\" : \"Geboorte in Nederland\"}";

        final MvcResult resultUpdate =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue()
                                        + "/bijhoudingsautorisatieSoortAdministratieveHandelingen")
                                .content(postDataUpdate).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result toevoegen: " + resultUpdate.getResponse().getContentAsString());

        final List<Map<String, Object>> bijhoudingsautorisatieSrtAdmHnd =
                getBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHnd);
        Assert.assertEquals(1, bijhoudingsautorisatieSrtAdmHnd.size());
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHnd.get(0).get("id"));
        Assert.assertEquals(bijhoudingsautorisatieIdCaptor.getValue(), bijhoudingsautorisatieSrtAdmHnd.get(0).get("bijhautorisatie"));
        Assert.assertEquals(5, bijhoudingsautorisatieSrtAdmHnd.get(0).get("srtadmhnd"));

        LOG.info("Start verwijderen soort administratieve handeling");
        final String postDataVerwijderen = "{\"bijhoudingsautorisatie\": " + bijhoudingsautorisatieIdCaptor.getValue() + "," + "\"id\" :"
                + bijhoudingsautorisatieSrtAdmHnd.get(0).get("id") + "," + "\"actief\" : \"Nee\"," + "\"soortAdministratieHandeling\" : 5,"
                + "\"naam\" : \"Geboorte in Nederland\"}";

        final MvcResult resultVerwijderen =
                mockMvc.perform(MockMvcRequestBuilders
                        .post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue()
                                + "/bijhoudingsautorisatieSoortAdministratieveHandelingen")
                        .content(postDataVerwijderen).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result verwijderen: " + resultVerwijderen.getResponse().getContentAsString());

        final List<Map<String, Object>> bijhoudingsautorisatieSrtAdmHndVerwijderen =
                getBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(bijhoudingsautorisatieSrtAdmHndVerwijderen);
    }

    @Test
    public void testInsertToegangBijhoudingsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String postDataInsert = "{\"indicatieModelAutorisatie\" : \"Nee\"," + "\"naam\" : \"testInsertAlleenAutorisatieToegang\","
                + "\"datumIngang\" : 20120101," + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> bijhoudingsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhoudingsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataToegang = "{\"bijhoudingsautorisatie\" : " + bijhoudingsautorisatieIdCaptor.getValue()
                + ",\"geautoriseerde\" : 367,\"ondertekenaar\" : 363,\"transporteur\" : 370,\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" "
                + ": \"Nee\"}";
        LOG.info("Start insert toegang\n" + postDataToegang);
        final CapturingMatcher<Integer> toegangIdCaptor = new CapturingMatcher<>();

        final MvcResult resultToegang = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue() + "/"
                                        + ControllerConstants.TOEGANGBIJHOUDINGAUTORISATIE_URI)
                                .content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", toegangIdCaptor)).andReturn();
        LOG.info("Result: " + resultToegang.getResponse().getContentAsString());
        LOG.info("Id: " + toegangIdCaptor.getValue());

        final Map<String, Object> toegangAfter = getToegangBijhoudingsautorisatie(toegangIdCaptor.getValue());
        Assert.assertNotNull(toegangAfter);
        Assert.assertEquals(367, toegangAfter.get("geautoriseerde"));
        Assert.assertEquals(363, toegangAfter.get("ondertekenaar"));
        Assert.assertEquals(370, toegangAfter.get("transporteur"));
    }

    @Test
    public void testOpvragenToegangBijhoudingsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String postDataInsert = "{\"indicatieModelAutorisatie\" : \"Nee\"," + "\"naam\" : \"testUpdateAutorisatieToegang\","
                + "\"datumIngang\" : 20120101," + "\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> bijhoudingsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhoudingsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataToegang = "{\"bijhoudingsautorisatie\" : " + bijhoudingsautorisatieIdCaptor.getValue()
                + ",\"geautoriseerde\" : 367,\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert toegang\n" + postDataToegang);
        final CapturingMatcher<Integer> toegangIdCaptor = new CapturingMatcher<>();

        final MvcResult resultToegang = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue() + "/"
                                        + ControllerConstants.TOEGANGBIJHOUDINGAUTORISATIE_URI)
                                .content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", toegangIdCaptor)).andReturn();
        LOG.info("Result: " + resultToegang.getResponse().getContentAsString());
        LOG.info("Id: " + toegangIdCaptor.getValue());

        final Map<String, Object> toegangAfter = getToegangBijhoudingsautorisatie(toegangIdCaptor.getValue());
        Assert.assertNotNull(toegangAfter);
        Assert.assertEquals(367, toegangAfter.get("geautoriseerde"));

        final MvcResult resultList =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue() + "/"
                                        + ControllerConstants.TOEGANGBIJHOUDINGAUTORISATIE_URI)
                                .content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("367"));

        final MvcResult resultObject = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.BIJHOUDINGSAUTORISATIE_URI + "/" + bijhoudingsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGBIJHOUDINGAUTORISATIE_URI + "/" + toegangIdCaptor.getValue())
                .content(postDataToegang).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultObject.getResponse().getContentAsString().indexOf("367"));
    }

}
