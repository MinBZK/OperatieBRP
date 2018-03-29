/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Testclass.
 */
public class PredicaatControllerIntegratieIT extends AbstractIntegratieTest {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONTENT = "$.content";
    private static final String NUMBER_OF_ELEMENTS = "$.numberOfElements";
    private static final String FIRST = "$.first";
    private static final String PREDICAAT = "/predicaat/";
    private static final int DRIE = 3;
    private static final String STRING_1 = "1";
    private static final String PREDICAATID = "/predicaat/{id}";

    /**
     * test.
     * @throws Exception een fout
     */
    @Test
    public final void testList() throws Exception {
        final MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(PREDICAAT))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath(FIRST).value(true))
                        .andExpect(MockMvcResultMatchers.jsonPath(NUMBER_OF_ELEMENTS).value(DRIE))
                        .andExpect(MockMvcResultMatchers.jsonPath(CONTENT).isArray())
                        .andExpect(MockMvcResultMatchers.jsonPath(CONTENT, Matchers.hasSize(DRIE)))
                        .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * test.
     * @throws Exception een fout
     */
    @Test
    public final void testPagineren() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PREDICAAT).param("page", STRING_1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(FIRST).value(false))
                .andExpect(MockMvcResultMatchers.jsonPath(NUMBER_OF_ELEMENTS).value(0))
                .andReturn();
    }

    // /**
    // * test.
    // *
    // * @throws Exception
    // * een fout
    // */
    // @Test
    // public final void testFilter() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.get(PREDICAAT).param("filterId", STRING_1).param("filterCode", "K"))
    // .andExpect(MockMvcResultMatchers.status().isOk())
    // .andExpect(MockMvcResultMatchers.jsonPath(FIRST).value(true))
    // .andExpect(MockMvcResultMatchers.jsonPath(NUMBER_OF_ELEMENTS).value(1))
    // .andExpect(MockMvcResultMatchers.jsonPath(CONTENT).isArray())
    // .andExpect(MockMvcResultMatchers.jsonPath(CONTENT, Matchers.hasSize(1)))
    // .andReturn();
    // }

    /**
     * test.
     * @throws Exception een fout
     */
    @Test
    public final void testGet() throws Exception {
        final MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(PREDICAATID, 1))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();

        LOG.info(result.getResponse().getContentAsString());
    }

    /**
     * test.
     * @throws Exception een fout
     */
    @Test
    public final void testGetNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PREDICAATID, Short.MAX_VALUE)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
