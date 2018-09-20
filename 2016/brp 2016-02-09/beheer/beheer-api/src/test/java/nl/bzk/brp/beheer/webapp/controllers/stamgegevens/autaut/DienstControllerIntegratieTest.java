/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Map;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractControllerIntegratieTest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test voor {@link DienstController}.
 */
@Ignore
public class DienstControllerIntegratieTest extends AbstractControllerIntegratieTest {

    @Test
    public void testUpdate() throws Exception {
        final Map<String, Object> abonnementBefore = getAbonnement(9004);
        final Map<String, Object> dienstBefore = getDienst(9001);
        Assert.assertEquals(Integer.valueOf(1), dienstBefore.get("catalogusoptie"));
        Assert.assertEquals(Integer.valueOf(9004), dienstBefore.get("abonnement"));

        final String json =
                "{\"iD\":9001,\"abonnement\":\"9004\",\"catalogusoptie\":4,\"naderePopulatieBeperking\":\"aangepastePopBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\",\"selectieperiodeInMaanden\":3}";
        mockMvc.perform(
                MockMvcRequestBuilders.post(ControllerConstants.LEVERINGSAUTORISATIE_URI + "/9004/diensten")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        final Map<String, Object> abonnementAfter = getAbonnement(9004);
        Assert.assertEquals(abonnementBefore, abonnementAfter);
        final Map<String, Object> dienstAfter = getDienst(9001);
        // A-laag mag niet aangeapst worden.
        Assert.assertEquals(Integer.valueOf(9004), dienstAfter.get("abonnement"));
        Assert.assertEquals(Integer.valueOf(1), dienstAfter.get("catalogusoptie"));
        // C/D-laag wel
        Assert.assertEquals("aangepastePopBeperk", dienstAfter.get("naderepopulatiebeperking"));
    }

}
