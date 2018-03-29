/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

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
public class LeveringsautorisatieControllerIT extends AbstractIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsertLeveringsautorisatie() throws Exception {
        final String
                postData =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestInsertAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postData).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + leveringsautorisatieIdCaptor.getValue());

        final Map<String, Object> abonnementAfter = getLeveringsautorisatie(leveringsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(abonnementAfter);
        Assert.assertEquals("TestInsertAutorisatie", abonnementAfter.get("naam"));
    }

    @Test
    public void testUpdateLeveringsautorisatieMetToegangEnDienstbundel() throws Exception {
        final String
                postData =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestInsertMetToegangEnDienstbundelAutorisatie\","
                        + "\"protocolleringsniveau\" : 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postData).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + leveringsautorisatieIdCaptor.getValue());

        final Map<String, Object> abonnementAfter = getLeveringsautorisatie(leveringsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(abonnementAfter);
        Assert.assertEquals("TestInsertMetToegangEnDienstbundelAutorisatie", abonnementAfter.get("naam"));

        final String postDataToegang = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ",\"geautoriseerde\" : 367,\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert toegang\n" + postDataToegang);
        final CapturingMatcher<Integer> toegangIdCaptor = new CapturingMatcher<>();

        final MvcResult resultToegang = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGABONNEMENT_URI).content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", toegangIdCaptor)).andReturn();
        LOG.info("Result: " + resultToegang.getResponse().getContentAsString());
        LOG.info("Id: " + toegangIdCaptor.getValue());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        LOG.info("Start update");
        final String postDataUpdate = "{\"id\": " + leveringsautorisatieIdCaptor.getValue()
                + ", \"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestInsertMetToegangEnDienstbundelAutorisatieGewijzigd\","
                + "\"protocolleringsniveau\" : 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final MvcResult resultUpdate = mockMvc.perform(
                MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataUpdate).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result: " + resultUpdate.getResponse().getContentAsString());

        final Map<String, Object> gewijzigdAbonnementAfter = getLeveringsautorisatie(leveringsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(gewijzigdAbonnementAfter);
        Assert.assertEquals("TestInsertMetToegangEnDienstbundelAutorisatieGewijzigd", gewijzigdAbonnementAfter.get("naam"));
    }

    @Test
    public void testUpdateLeveringsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestUpdateAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        LOG.info("Start update");
        final String postDataUpdate = "{\"id\": " + leveringsautorisatieIdCaptor.getValue()
                + ", \"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestUpdateAutorisatie2\",\"protocolleringsniveau\" : 1,"
                + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final MvcResult resultUpdate = mockMvc.perform(
                MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataUpdate).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result: " + resultUpdate.getResponse().getContentAsString());

        final Map<String, Object> abonnementAfter = getLeveringsautorisatie(leveringsautorisatieIdCaptor.getValue());
        Assert.assertNotNull(abonnementAfter);
        Assert.assertEquals("TestUpdateAutorisatie2", abonnementAfter.get("naam"));
    }

    @Test
    public void testInsertToegangLeveringsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestToegangAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataToegang = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ",\"geautoriseerde\" : 367,\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert toegang\n" + postDataToegang);
        final CapturingMatcher<Integer> toegangIdCaptor = new CapturingMatcher<>();

        final MvcResult resultToegang = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGABONNEMENT_URI).content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", toegangIdCaptor)).andReturn();
        LOG.info("Result: " + resultToegang.getResponse().getContentAsString());
        LOG.info("Id: " + toegangIdCaptor.getValue());

        final Map<String, Object> toegangAfter = getToegangLeveringsautorisatie(toegangIdCaptor.getValue());
        Assert.assertNotNull(toegangAfter);
        Assert.assertEquals(367, toegangAfter.get("geautoriseerde"));
    }

    @Test
    public void testOpvragenToegangLeveringsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestOpvragenToegangAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataToegang = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ",\"geautoriseerde\" : 367,\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert toegang\n" + postDataToegang);
        final CapturingMatcher<Integer> toegangIdCaptor = new CapturingMatcher<>();

        final MvcResult resultToegang = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGABONNEMENT_URI).content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", toegangIdCaptor)).andReturn();
        LOG.info("Result: " + resultToegang.getResponse().getContentAsString());
        LOG.info("Id: " + toegangIdCaptor.getValue());

        final Map<String, Object> toegangAfter = getToegangLeveringsautorisatie(toegangIdCaptor.getValue());
        Assert.assertNotNull(toegangAfter);
        Assert.assertEquals(367, toegangAfter.get("geautoriseerde"));

        final MvcResult resultList = mockMvc
                .perform(MockMvcRequestBuilders.get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGABONNEMENT_URI).content(postDataToegang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("367"));

        final MvcResult resultObject = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.TOEGANGABONNEMENT_URI + "/" + toegangIdCaptor.getValue())
                .content(postDataToegang).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultObject.getResponse().getContentAsString().indexOf("367"));
    }

    @Test
    public void testInsertDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstbundelAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));
    }

    @Test
    public void testVraagOpDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestVraagOpDienstbundelAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final MvcResult resultList = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("Ad hoc"));

        final MvcResult resultObject =
                mockMvc.perform(MockMvcRequestBuilders
                        .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue())
                        .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultObject.getResponse().getContentAsString().indexOf("Ad hoc"));
    }

    @Test
    public void testInsertDienstDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstDienstbundelAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final String postDataDienst = "{\"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"soort\" : 4, \"effectAfnemerindicaties\" : 1, \"datumIngang\" : 20110101, \"datumEinde\" : 20993112, \"indicatieGeblokkeerd\" : "
                + "\"Nee\", \"attenderingscriterium\" : \"WAAR\", \"eersteSelectiedatum\" : 20120101, \"soortSelectie\" : 1, \"historieVorm\": 3, "
                + "\"indicatieResultaatControleren\": \"ONWAAR\"}";
        final CapturingMatcher<Integer> dienstIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienst = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENST_URI)
                        .content(postDataDienst).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienst.getResponse().getContentAsString());
        LOG.info("Id: " + dienstIdCaptor.getValue());

        final Map<String, Object> dienstAfter = getDienst(dienstIdCaptor.getValue());
        Assert.assertNotNull(dienstAfter);
    }

    @Test
    public void testVraagOpDienstDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestVraagOpDienstDienstbundelAutorisatie\",\"protocolleringsniveau\" :"
                        + " 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataDienst = "{\"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"soort\" : 4, \"effectAfnemerindicaties\" : 1, \"datumIngang\" : 20110101, \"datumEinde\" : 20993112, \"indicatieGeblokkeerd\" : "
                + "\"Nee\", \"attenderingscriterium\" : \"WAAR\", \"eersteSelectiedatum\" : 20120101, \"soortSelectie\" : 1, \"historieVorm\": 3, "
                + "\"indicatieResultaatControleren\": \"ONWAAR\"}";
        final CapturingMatcher<Integer> dienstIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienst = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENST_URI)
                        .content(postDataDienst).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienst.getResponse().getContentAsString());
        LOG.info("Id: " + dienstIdCaptor.getValue());

        final Map<String, Object> dienstAfter = getDienst(dienstIdCaptor.getValue());
        Assert.assertNotNull(dienstAfter);

        mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENST_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENST_URI + "/" + dienstIdCaptor.getValue())
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void testInsertLo3RubriekenDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestLo3RubriekenDienstbundelAutorisatie\",\"protocolleringsniveau\" : "
                        + "1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final String postDataLo3Rubriek = "{\"actief\" : \"Ja\", \"dienstbundel\" : " + dienstbundelIdCaptor.getValue() + ", \"rubriek\" : 2}";
        final CapturingMatcher<Integer> rubriekIdCaptor = new CapturingMatcher<>();

        final MvcResult resultRubriek = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/lo3rubrieken")
                        .content(postDataLo3Rubriek).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", rubriekIdCaptor)).andReturn();
        LOG.info("Result: " + resultRubriek.getResponse().getContentAsString());
        LOG.info("Id: " + rubriekIdCaptor.getValue());

        final Map<String, Object> rubriekAfter = getDienstbundelLO3Rubriek(rubriekIdCaptor.getValue());
        Assert.assertNotNull(rubriekAfter);
        Assert.assertEquals(2, rubriekAfter.get("rubr"));

        final MvcResult resultList =
                mockMvc.perform(MockMvcRequestBuilders
                        .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/lo3rubrieken")
                        .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("rubr"));

        final String postDataOnbekendeLo3Rubriek = "{\"actief\" : \"Ja\", \"dienstbundel\" : " + dienstbundelIdCaptor.getValue() + ", \"rubriek\" : 2}";

        final MvcResult resultOnbekendeRubriek = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/lo3rubrieken")
                        .content(postDataOnbekendeLo3Rubriek).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result: " + resultOnbekendeRubriek.getResponse().getContentAsString());
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("rubr"));

        final String postDataLo3RubriekWijzigen = "{\"id\": " + rubriekIdCaptor.getValue() + ", \"actief\" : \"Nee\", \"dienstbundel\" : "
                + dienstbundelIdCaptor.getValue() + ", \"rubriek\" : 2}";

        final MvcResult resultRubriekWijzigen = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/lo3rubrieken")
                        .content(postDataLo3RubriekWijzigen).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", rubriekIdCaptor)).andReturn();
        LOG.info("Result: " + resultRubriekWijzigen.getResponse().getContentAsString());
        LOG.info("Id: " + rubriekIdCaptor.getValue());

        final Map<String, Object> rubriekWijzigenAfter = getDienstbundelLO3Rubriek(rubriekIdCaptor.getValue());
        Assert.assertNull(rubriekWijzigenAfter);
    }

    @Test
    public void testVraagOpGroepDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 1,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestVraagOpGroepDienstbundelAutorisatie\",\"protocolleringsniveau\" : "
                        + "1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 3673, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        final CapturingMatcher<Integer> groepIdCaptor = new CapturingMatcher<>();

        final MvcResult resultGroep = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroep).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroep.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

        final Map<String, Object> groepAfter = getDienstbundelGroep(groepIdCaptor.getValue());
        Assert.assertNotNull(groepAfter);
        Assert.assertEquals(3673, groepAfter.get("groep"));

        final MvcResult resultList = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                .content(postDataGroep).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("groep"));

        final MvcResult resultObject = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue())
                .content(postDataGroep).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultObject.getResponse().getContentAsString().indexOf("groep"));
    }

    @Test
    public void testVerwijderenGroepDienstbundelOpAutorisatie() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 1,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestVerwijderenGroepDienstbundelAutorisatie\","
                        + "\"protocolleringsniveau\" : 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        final CapturingMatcher<Integer> groepIdCaptor = new CapturingMatcher<>();

        final MvcResult resultGroep = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroep).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroep.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

        final Map<String, Object> groepAfter = getDienstbundelGroep(groepIdCaptor.getValue());
        Assert.assertNotNull(groepAfter);
        Assert.assertEquals(13109, groepAfter.get("groep"));

        final MvcResult resultList = mockMvc.perform(MockMvcRequestBuilders
                .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                .content(postDataGroep).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("groep"));

        final String
                postDataAttribuut =
                "{ \"actief\": \"Ja\", \"attribuut\": 13120, \"attribuutNaam\": \"GerelateerdeGeregistreerdePartner.Persoon.Geboorte.GemeenteCode\", "
                        + "\"dienstbundelGroep\": 4, \"soort\": 7}";

        final CapturingMatcher<Integer> attribuutIdCaptor = new CapturingMatcher<>();

        System.out.println(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue() + ControllerConstants.ATTRIBUUT_URI);

        final MvcResult resultAttribuut = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue() + ControllerConstants.ATTRIBUUT_URI)
                        .content(postDataAttribuut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id", attribuutIdCaptor)).andReturn();
        LOG.info("Result: " + resultAttribuut.getResponse().getContentAsString());
        LOG.info("Id: " + attribuutIdCaptor.getValue());

        final Map<String, Object> attribuutAfter = getDienstbundelGroepAttribuut(attribuutIdCaptor.getValue());
        Assert.assertNotNull(attribuutAfter);
        Assert.assertEquals(13120, attribuutAfter.get("attr"));

        final String postDataGroepWijzigen = "{ \"id\": " + groepIdCaptor.getValue() + ", \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Ja\"}";

        final MvcResult resultGroepWijzigen = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroepWijzigen).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroepWijzigen.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

        final Map<String, Object> groepWijzigingAfter = getDienstbundelGroep(groepIdCaptor.getValue());
        Assert.assertNotNull(groepWijzigingAfter);
        Assert.assertEquals(true, groepWijzigingAfter.get("indverantwoording"));

        final MvcResult resultObject =
                mockMvc.perform(MockMvcRequestBuilders
                        .delete(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue())
                        .content(postDataGroepWijzigen).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(-1, resultObject.getResponse().getContentAsString().indexOf("groep"));
    }

    @Test
    public void testDienstbundelGroep() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 1,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstbundelGroepAutorisatie\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        final CapturingMatcher<Integer> groepIdCaptor = new CapturingMatcher<>();

        final MvcResult resultGroep = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroep).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroep.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

        final Map<String, Object> groepAfter = getDienstbundelGroep(groepIdCaptor.getValue());
        Assert.assertNotNull(groepAfter);
        Assert.assertEquals(13109, groepAfter.get("groep"));

        final String postDataGroepOpnieuw = "{ \"id\": " + groepIdCaptor.getValue() + ", \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        final MvcResult resultGroepOpnieuw = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroepOpnieuw).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroepOpnieuw.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

    }

    @Test
    public void testDienstbundelGroepFout() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstbundelGroepAutorisatieFout\",\"protocolleringsniveau\" : 1,"
                        + "\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        new CapturingMatcher<>();

        mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                .content(postDataGroep).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
    }

    @Test
    public void testDienstbundelGroepAttribuut() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 1,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstbundelGroepAttribuutAutorisatie\",\"protocolleringsniveau\" "
                        + ": 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        final CapturingMatcher<Integer> groepIdCaptor = new CapturingMatcher<>();

        final MvcResult resultGroep = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                        + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                        .content(postDataGroep).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", groepIdCaptor)).andReturn();
        LOG.info("Result: " + resultGroep.getResponse().getContentAsString());
        LOG.info("Id: " + groepIdCaptor.getValue());

        final Map<String, Object> groepAfter = getDienstbundelGroep(groepIdCaptor.getValue());
        Assert.assertNotNull(groepAfter);
        Assert.assertEquals(13109, groepAfter.get("groep"));

        final String postDataAttribuut = "{ \"actief\": \"Ja\", \"attribuut\": 13119, \"attribuutNaam\": \"testattribuut\", \"dienstbundelGroep\": "
                + groepIdCaptor.getValue() + ", \"soort\": 1}";

        final CapturingMatcher<Integer> attribuutIdCaptor = new CapturingMatcher<>();

        final MvcResult resultAttribuut = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue() + "/" + ControllerConstants.ATTRIBUUT_URI)
                        .content(postDataAttribuut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id", attribuutIdCaptor)).andReturn();
        LOG.info("Result: " + resultAttribuut.getResponse().getContentAsString());
        LOG.info("Id: " + attribuutIdCaptor.getValue());

        final Map<String, Object> attribuutAfter = getDienstbundelGroepAttribuut(attribuutIdCaptor.getValue());
        Assert.assertNotNull(attribuutAfter);
        Assert.assertEquals(13119, attribuutAfter.get("attr"));

        final MvcResult resultList = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue() + "/" + ControllerConstants.ATTRIBUUT_URI)
                        .content(postDataAttribuut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("attribuut"));

        final String postDataAttribuutDeactiveren = "{ \"id\": " + attribuutIdCaptor.getValue()
                + ", \"actief\": \"Nee\", \"attribuut\": 13835, \"attribuutNaam\": \"GerelateerdeErkenner.Persoon.Geboorte.VoorkomenSleutel\", "
                + "\"dienstbundelGroep\": 4, \"soort\": 7}";

        final MvcResult resultAttribuutDeactiveren = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_URI + "/" + dienstbundelIdCaptor.getValue() + "/"
                                + ControllerConstants.DIENSTBUNDEL_GROEP_URI + "/" + groepIdCaptor.getValue() + "/" + ControllerConstants.ATTRIBUUT_URI)
                        .content(postDataAttribuutDeactiveren).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id", attribuutIdCaptor)).andReturn();
        LOG.info("Result: " + resultAttribuutDeactiveren.getResponse().getContentAsString());
        Assert.assertNotEquals(-1, resultList.getResponse().getContentAsString().indexOf("Nee"));

    }

    @Test
    public void testDienstbundelGroepAttribuutFout() throws Exception {
        LOG.info("Start insert");
        final String
                postDataInsert =
                "{\"stelsel\" : 2,\"indicatieModelAutorisatie\" : \"Nee\",\"naam\" : \"TestDienstbundelGroepAttribuutAutorisatieFout\","
                        + "\"protocolleringsniveau\" : 1,\"indicatieAliasLeveren\" : \"Nee\",\"datumIngang\" : 20110101,\"indicatieGeblokkeerd\" : \"Nee\"}";
        final CapturingMatcher<Integer> leveringsautorisatieIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", leveringsautorisatieIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());

        final String postDataDienstbundel = "{\"leveringsautorisatie\" : " + leveringsautorisatieIdCaptor.getValue()
                + ", \"naam\" : \"Ad hoc\",\"datumIngang\" : 20110101,\"datumEinde\" : 20993112, \"naderePopulatiebeperking\" : \"WAAR\", "
                + "\"indicatieNaderePopulatieBeperkingVolledigGeconverteerd\" : \"Ja\", \"toelichting\" : \"Geen\", \"indicatieGeblokkeerd\" : \"Nee\"}";
        LOG.info("Start insert dienstbundel\n" + postDataDienstbundel);
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();

        final MvcResult resultDienstbundel = mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI)
                .content(postDataDienstbundel).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelIdCaptor)).andReturn();
        LOG.info("Result: " + resultDienstbundel.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelIdCaptor.getValue());

        final Map<String, Object> dienstbundelAfter = getDienstbundel(dienstbundelIdCaptor.getValue());
        Assert.assertNotNull(dienstbundelAfter);
        Assert.assertEquals("Ad hoc", dienstbundelAfter.get("naam"));

        final String postDataGroep = "{ \"dienstbundel\" : " + dienstbundelIdCaptor.getValue()
                + ", \"groepId\" : 13109, \"indicatieFormeleHistorie\" : \"Nee\", \"indicatieMaterieleHistorie\" : \"Nee\", \"indicatieVerantwoording\" : "
                + "\"Nee\"}";

        new CapturingMatcher<>();

        mockMvc.perform(MockMvcRequestBuilders
                .post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/" + leveringsautorisatieIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_URI
                        + "/" + dienstbundelIdCaptor.getValue() + "/" + ControllerConstants.DIENSTBUNDEL_GROEP_URI)
                .content(postDataGroep).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
    }

}
