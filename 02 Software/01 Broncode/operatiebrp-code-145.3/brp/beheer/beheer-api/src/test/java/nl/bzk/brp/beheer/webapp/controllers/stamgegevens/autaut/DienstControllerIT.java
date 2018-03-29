/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Map;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractIntegratieTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test voor {@link DienstController}.
 */
public class DienstControllerIT extends AbstractIntegratieTest {

    @Test
    public void testUpdate() throws Exception {
        final Map<String, Object> abonnementBefore = getLeveringsautorisatie(9004);
        final Map<String, Object> dienstbundelBefore = getDienstbundel(9004);
        final Map<String, Object> dienstBefore = getDienst(9001);
        Assert.assertEquals(Integer.valueOf(1), dienstBefore.get("srt"));
        Assert.assertEquals(Integer.valueOf(9004), dienstBefore.get("dienstbundel"));
        Assert.assertEquals(Integer.valueOf(9004), dienstbundelBefore.get("levsautorisatie"));

        final String json = "{\"id\":9001,\"dienstbundel\":\"9004\",\"soort\":4,\"datumIngang\":20150101,\"datumEinde\":\"20450101\"}";
        mockMvc.perform(
                MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/9004/dienstbundels/9004/diensten")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final Map<String, Object> abonnementAfter = getLeveringsautorisatie(9004);
        Assert.assertEquals(abonnementBefore, abonnementAfter);
        final Map<String, Object> dienstbundelAfter = getDienstbundel(9004);
        Assert.assertEquals(dienstbundelBefore, dienstbundelAfter);
        final Map<String, Object> dienstAfter = getDienst(9001);
        Assert.assertEquals(Integer.valueOf(9004), dienstAfter.get("dienstbundel"));
        Assert.assertEquals(Integer.valueOf(1), dienstAfter.get("srt"));
        Assert.assertNull(dienstAfter.get("attenderingscriterium"));
        Assert.assertEquals(Integer.valueOf(20450101), dienstAfter.get("dateinde"));
    }

}
