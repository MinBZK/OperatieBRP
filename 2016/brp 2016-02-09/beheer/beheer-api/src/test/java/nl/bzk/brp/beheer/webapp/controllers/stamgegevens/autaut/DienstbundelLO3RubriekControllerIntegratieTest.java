/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Map;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractControllerIntegratieTest;
import nl.bzk.brp.beheer.webapp.test.CapturingMatcher;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * Test voor {@link DienstbundelLO3RubriekController}.
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Ignore
public class DienstbundelLO3RubriekControllerIntegratieTest extends AbstractControllerIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsert() throws Exception {
        final String jsonAbonnement = "{\"abonnement\":\"9002\",\"rubriek\":3,\"actief\":\"Ja\"}";

        final CapturingMatcher<Integer> abonnementLO3RubriekIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> abonnementIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> rubriekIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> actiefIdCaptor = new CapturingMatcher<>();

        final MvcResult result =
            mockMvc.perform(
                    MockMvcRequestBuilders.post(ControllerConstants.LO3_FILTER_RUBRIEK).content(jsonAbonnement)
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.iD", abonnementLO3RubriekIdCaptor))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.abonnement", abonnementIdCaptor))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.rubriek", rubriekIdCaptor))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.actief", actiefIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + abonnementLO3RubriekIdCaptor.getValue());

        final Map<String, Object> abonnementLO3RubriekAfter =
            getAbonnementLO3Rubriek(abonnementLO3RubriekIdCaptor.getValue());
        Assert.assertNotNull(abonnementLO3RubriekAfter);
        Assert.assertEquals(Integer.valueOf(9002), getAbonnement(abonnementIdCaptor.getValue()).get("iD"));
        Assert.assertEquals(Integer.valueOf(3), getConversieLO3Rubriek(rubriekIdCaptor.getValue()).get("iD"));
        Assert.assertEquals(true, (Boolean) abonnementLO3RubriekAfter.get("indactief"));

    }

    @Test
    public void testUpdate() throws Exception {
        LOG.info("Start update");
        final String postData = "{\"iD\":9876,\"abonnement\":\"9001\",\"rubriek\":3,\"actief\":\"Nee\"}";
        final MvcResult result =
            mockMvc.perform(
                    MockMvcRequestBuilders.post(ControllerConstants.LO3_FILTER_RUBRIEK).content(postData)
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testVervallenHistorie() throws Exception {
        final String sql =
            "select count(*) as counts, count(tsreg) as tsregs, count(tsverval) as tsvervals from autaut.his_abonnementlo3rubriek where abonnementlo3rubriek = ?";

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        final Map<String, Object> before = jdbcTemplate.queryForMap(sql, 9876);
        System.out.println("BEFORE:\n"
            + jdbcTemplate.queryForList("select * from autaut.his_abonnementlo3rubriek where abonnementlo3rubriek = ?",
                    9876));
        Assert.assertEquals(Long.valueOf(1), before.get("counts"));
        Assert.assertEquals(Long.valueOf(1), before.get("tsregs"));
        Assert.assertEquals(Long.valueOf(0), before.get("tsvervals"));

        // Update (met data, dus nieuw his record en oude zou moeten vervallen)
        final String updateMetData = "{\"iD\":9876,\"abonnement\":\"9001\",\"rubriek\":3,\"actief\":\"Ja\"}";
        final MvcResult result =
            mockMvc.perform(
                    MockMvcRequestBuilders.post(ControllerConstants.LO3_FILTER_RUBRIEK).content(updateMetData)
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        LOG.info("Result testVervallenHistory: " + result.getResponse().getContentAsString());

        final Map<String, Object> afterUpdateMetData = jdbcTemplate.queryForMap(sql, 9876);
        System.out.println("AFTER MET DATA:\n"
            + jdbcTemplate.queryForList("select * from autaut.his_abonnementlo3rubriek where abonnementlo3rubriek = ?",
                    9876));
        Assert.assertEquals(Long.valueOf(1), afterUpdateMetData.get("counts"));
        Assert.assertEquals(Long.valueOf(1), afterUpdateMetData.get("tsregs"));
        Assert.assertEquals(Long.valueOf(0), afterUpdateMetData.get("tsvervals"));

        final String updateZonderData = "{\"iD\":9876,\"abonnement\":9001,\"rubriek\":3,\"actief\":\"Nee\"}";
        final MvcResult updateZonderDataResult =
            mockMvc.perform(
                    MockMvcRequestBuilders.post(ControllerConstants.LO3_FILTER_RUBRIEK).content(updateZonderData)
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        System.out.println("Result updateZonderData: " + updateZonderDataResult.getResponse().getContentAsString());
        Assert.assertFalse(updateZonderDataResult.getResponse().getContentAsString().contains("\"actief\" : \"Ja\""));

        final Map<String, Object> afterUpdateZonderData = jdbcTemplate.queryForMap(sql, 9876);
        System.out.println("AFTER ZONDER DATA:\n"
            + jdbcTemplate.queryForList("select * from autaut.his_abonnementlo3rubriek where abonnementlo3rubriek = ?",
                    9876));
        Assert.assertEquals(Long.valueOf(1), afterUpdateZonderData.get("counts"));
        Assert.assertEquals(Long.valueOf(1), afterUpdateZonderData.get("tsregs"));
        Assert.assertEquals(Long.valueOf(1), afterUpdateZonderData.get("tsvervals"));

        final String weerActiefMaken = "{\"iD\":9876,\"abonnement\":9001,\"rubriek\":3,\"actief\":\"Ja\"}";
        final MvcResult weerActiefMakenResult =
            mockMvc.perform(
                    MockMvcRequestBuilders.post(ControllerConstants.LO3_FILTER_RUBRIEK).content(weerActiefMaken)
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        System.out.println("Result weeractiefmaken: " + weerActiefMakenResult.getResponse().getContentAsString());
        Assert.assertTrue(weerActiefMakenResult.getResponse().getContentAsString().contains("\"actief\" : \"Ja\""));
    }
}
