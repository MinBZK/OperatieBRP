/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
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
import org.springframework.web.context.WebApplicationContext;

/**
 * Test voor {@link LeveringsautorisatieController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class BijhouderFiatteringsuitzonderingControllerIT extends AbstractIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testInsertBijhouderFiatteringsuitzondering() throws Exception {
        final String postData = "{\"bijhouder\": 365, \"bijhouderBijhoudingsvoorstel\": 601, \"datumEinde\": 20991231, \"datumIngang\": 20120101, "
                + "\"indicatieGeblokkeerd\": \"Nee\", \"soortAdministratieveHandeling\": 30, \"soortDocument\": 33}";
        final CapturingMatcher<Integer> bijhouderFiatteringsuitzonderingIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDER_FIATTERINGSUITZONDERING_URI).content(postData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhouderFiatteringsuitzonderingIdCaptor))
                .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        LOG.info("Id: " + bijhouderFiatteringsuitzonderingIdCaptor.getValue());

        final Map<String, Object> bijhouderFiatteringsuitzonderingAfter =
                getBijhouderFiatteringsuitzondering(bijhouderFiatteringsuitzonderingIdCaptor.getValue());
        Assert.assertNotNull(bijhouderFiatteringsuitzonderingAfter);
        Assert.assertEquals(365, bijhouderFiatteringsuitzonderingAfter.get("bijhouder"));
        Assert.assertEquals(601, bijhouderFiatteringsuitzonderingAfter.get("bijhouderbijhvoorstel"));
        Assert.assertEquals(20120101, bijhouderFiatteringsuitzonderingAfter.get("datingang"));
        Assert.assertEquals(20991231, bijhouderFiatteringsuitzonderingAfter.get("dateinde"));
        Assert.assertEquals(30, bijhouderFiatteringsuitzonderingAfter.get("srtadmhnd"));
        Assert.assertEquals(33, bijhouderFiatteringsuitzonderingAfter.get("srtdoc"));
        Assert.assertNull(bijhouderFiatteringsuitzonderingAfter.get("indblok"));
    }

    @Test
    public void testUpdateBijhoudingsautorisatie() throws Exception {
        LOG.info("Start insert");
        final String postDataInsert = "{\"bijhouder\": 365, \"bijhouderBijhoudingsvoorstel\": 601, \"datumEinde\": 20991231, \"datumIngang\": 20120101, "
                + "\"indicatieGeblokkeerd\": \"Nee\", \"soortAdministratieveHandeling\": 30, \"soortDocument\": 30}";
        final CapturingMatcher<Integer> bijhouderFiatteringsuitzonderingIdCaptor = new CapturingMatcher<>();

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDER_FIATTERINGSUITZONDERING_URI).content(postDataInsert)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id", bijhouderFiatteringsuitzonderingIdCaptor))
                .andReturn();
        LOG.info("Result: " + result.getResponse().getContentAsString());
        final Map<String, Object> bijhouderFiatteringsuitzonderingInsert =
                getBijhouderFiatteringsuitzondering(bijhouderFiatteringsuitzonderingIdCaptor.getValue());
        Assert.assertNotNull(bijhouderFiatteringsuitzonderingInsert);
        Assert.assertEquals(365, bijhouderFiatteringsuitzonderingInsert.get("bijhouder"));
        Assert.assertNull(bijhouderFiatteringsuitzonderingInsert.get("indblok"));

        LOG.info("Start update");
        final String postDataUpdate = "{\"id\": " + bijhouderFiatteringsuitzonderingIdCaptor.getValue()
                + ",\"bijhouder\": 365, \"bijhouderBijhoudingsvoorstel\": 601, \"datumEinde\": 20991231, \"datumIngang\": 20120101, "
                + "\"indicatieGeblokkeerd\": \"Ja\", \"soortAdministratieveHandeling\": 30, \"soortDocument\": 30}";
        final MvcResult resultUpdate = mockMvc.perform(MockMvcRequestBuilders.post(ControllerConstants.BIJHOUDER_FIATTERINGSUITZONDERING_URI)
                .content(postDataUpdate).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        LOG.info("Result: " + resultUpdate.getResponse().getContentAsString());

        final Map<String, Object> bijhouderFiatteringsuitzonderingUpdate =
                getBijhouderFiatteringsuitzondering(bijhouderFiatteringsuitzonderingIdCaptor.getValue());
        Assert.assertNotNull(bijhouderFiatteringsuitzonderingUpdate);
        Assert.assertEquals(Boolean.TRUE, bijhouderFiatteringsuitzonderingUpdate.get("indblok"));

    }
}
