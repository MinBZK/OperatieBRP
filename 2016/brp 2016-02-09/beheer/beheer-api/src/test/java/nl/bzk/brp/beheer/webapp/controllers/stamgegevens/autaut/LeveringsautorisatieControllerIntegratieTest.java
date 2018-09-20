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
 * Test voor {@link LeveringsautorisatieController}.
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Ignore
public class LeveringsautorisatieControllerIntegratieTest extends AbstractControllerIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsert() throws Exception {
        final String jsonDienst1 =
                "{\"catalogusoptie\":4,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2}";
        final String jsonDienst2 =
                "{\"catalogusoptie\":5,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2}";
        final String jsonAbonnement =
                "{\"naam\":\"naampje\",\"populatieBeperking\":\"popBeperk\",\"protocolleringsniveau\":2,"
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":3,\"indicatieAliasLeveren\":\"Nee\",\"diensten\":["
                        + jsonDienst1 + "," + jsonDienst2 + "]}";

        final CapturingMatcher<Integer> abonnementIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> dienstIdCaptor = new CapturingMatcher<>();

        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(jsonAbonnement)
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.iD", abonnementIdCaptor))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.diensten..iD", dienstIdCaptor)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + abonnementIdCaptor.getValue());

        final Map<String, Object> abonnementAfter = getAbonnement(abonnementIdCaptor.getValue());
        Assert.assertNotNull(abonnementAfter);
        Assert.assertEquals("naampje", abonnementAfter.get("naam"));

        for (final Integer dienstId : dienstIdCaptor.getValues()) {
            final Map<String, Object> dienstAfter = getDienst(dienstId);
            Assert.assertNotNull(dienstAfter);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        LOG.info("Start update");
        final String postData =
                "{\"iD\":9004,\"naam\":\"vierdeeabonnement\",\"protocolleringsniveau\":2,\"datumIngang\":20150101,"
                        + "\"toestand\":4,\"indicatieAliasLeveren\":\"Ja\",\"diensten\":[{\"iD\":9001,\"abonnement\":9004,\"catalogusoptie\":1,"
                        + "\"datumIngang\":20150101,\"toestand\":4},{\"iD\":9004,\"abonnement\":9004,\"catalogusoptie\":2,\"datumIngang\":20150101,"
                        + "\"toestand\":1}]}";
        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(postData)
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testVervallenHistorie() throws Exception {
        final String sql =
                "select count(*) as counts, count(tsreg) as tsregs, count(tsverval) as tsvervals from autaut.his_abonnement where abonnement = ?";

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        final Map<String, Object> before = jdbcTemplate.queryForMap(sql, 9001);
        System.out.println("BEFORE:\n"
                + jdbcTemplate.queryForList("select * from autaut.his_abonnement where abonnement = ?", 9001));
        Assert.assertEquals(Long.valueOf(1), before.get("counts"));
        Assert.assertEquals(Long.valueOf(1), before.get("tsregs"));
        Assert.assertEquals(Long.valueOf(0), before.get("tsvervals"));

        // Update (met data, dus nieuw his record en oude zou moeten vervallen)
        final String updateMetData =
                "{\"iD\":9001,\"naam\":\"eersteabonnement\",\"protocolleringsniveau\":2,\"datumIngang\":20160101,"
                        + "\"toestand\":4,\"indicatieAliasLeveren\":\"Ja\"}";
        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(updateMetData)
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        LOG.info("Result testVervallenHistory: " + result.getResponse().getContentAsString());

        final Map<String, Object> afterUpdateMetData = jdbcTemplate.queryForMap(sql, 9001);
        System.out.println("AFTER MET DATA:\n"
                + jdbcTemplate.queryForList("select * from autaut.his_abonnement where abonnement = ?", 9001));
        Assert.assertEquals(Long.valueOf(2), afterUpdateMetData.get("counts"));
        Assert.assertEquals(Long.valueOf(2), afterUpdateMetData.get("tsregs"));
        Assert.assertEquals(Long.valueOf(1), afterUpdateMetData.get("tsvervals"));

        // Update (zonder data, dus alleen oude his record laten vervallen) TODO moet nog een keer worden gemaakt, maar
        // kan niet bij abonnement
        // final String updateZonderData = "{\"iD\":9001,\"naam\":\"eersteabonnement\"}";
        // mockMvc.perform(MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI).content(updateZonderData).contentType(MediaType.APPLICATION_JSON))
        // .andExpect(MockMvcResultMatchers.status().isOk())
        // .andReturn();
        //
        // final Map<String, Object> afterUpdateZonderData = jdbcTemplate.queryForMap(sql, 9001);
        // System.out.println("AFTER ZONDER DATA:\n" +
        // jdbcTemplate.queryForList("select * from autaut.his_abonnement where abonnement = ?", 9001));
        // Assert.assertEquals(Long.valueOf(2), afterUpdateZonderData.get("counts"));
        // Assert.assertEquals(Long.valueOf(2), afterUpdateZonderData.get("tsregs"));
        // Assert.assertEquals(Long.valueOf(2), afterUpdateZonderData.get("tsvervals"));
    }

    @Test
    public void testQueryCatalogusOptie() throws Exception {
        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.get(ControllerConstants.LEVERINGSAUTORISATIE_URI).param("filterDienstCatalogusOptie",
                                "2")).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testQueryToegangPartijCode() throws Exception {
        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.get(ControllerConstants.LEVERINGSAUTORISATIE_URI).param("filterToegangPartijCode",
                                "199902")).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2)).andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
    }
}
