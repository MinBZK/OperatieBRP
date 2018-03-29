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
import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import nl.bzk.brp.beheer.webapp.test.CapturingMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test voor {@link DienstbundelLo3RubriekController}.
 */
public class DienstbundelLo3RubriekControllerIT extends AbstractIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsert() throws Exception {
        final String jsonAbonnement = "{\"dienstbundel\":\"9001\",\"rubriek\":6,\"rubrieknaam\":\"01.01.10\",\"actief\":\"Ja\"}";

        final CapturingMatcher<Integer> dienstbundelLO3RubriekIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> dienstbundelIdCaptor = new CapturingMatcher<>();
        final CapturingMatcher<Integer> rubriekIdCaptor = new CapturingMatcher<>();
        new CapturingMatcher<>();

        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                ControllerConstants.LEVERINGSAUTORISATIE_URI + "/9001" + ControllerConstants.DIENSTBUNDEL_URI + "/9001/lo3rubrieken")
                                .content(jsonAbonnement)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id", dienstbundelLO3RubriekIdCaptor))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.dienstbundel", dienstbundelIdCaptor))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.rubriek", rubriekIdCaptor))
                        .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + dienstbundelLO3RubriekIdCaptor.getValue());

        final Map<String, Object> abonnementLO3RubriekAfter = getDienstbundelLO3Rubriek(dienstbundelLO3RubriekIdCaptor.getValue());
        Assert.assertNotNull(abonnementLO3RubriekAfter);
        Assert.assertEquals(Integer.valueOf(9001), getLeveringsautorisatie(dienstbundelIdCaptor.getValue()).get("id"));
        Assert.assertEquals(Integer.valueOf(6), getConversieLO3Rubriek(rubriekIdCaptor.getValue()).get("id"));
    }

    @Test
    public void testUpdate() throws Exception {
        LOG.info("Start update");
        final String postData = "{\"id\":9876,\"dienstbundel\":\"9001\",\"rubriek\":3}";
        final MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                ControllerConstants.LEVERINGSAUTORISATIE_URI + "/9001" + ControllerConstants.DIENSTBUNDEL_URI + "/9001/lo3Rubrieken")
                                .content(postData)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
    }
}
