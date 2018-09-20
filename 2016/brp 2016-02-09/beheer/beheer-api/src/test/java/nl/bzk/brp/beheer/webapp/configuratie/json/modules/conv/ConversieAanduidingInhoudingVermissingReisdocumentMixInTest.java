/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules.conv;

import java.io.IOException;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.beheer.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.beheer.kern.AanduidingInhoudingVermissingReisdocument;
import org.junit.Assert;
import org.junit.Test;

public class ConversieAanduidingInhoudingVermissingReisdocumentMixInTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void testSerialize() throws IOException {
        final ConversieAanduidingInhoudingVermissingReisdocument conversieAanduidingInhoudingVermissingReisdocument =
                new ConversieAanduidingInhoudingVermissingReisdocument();
        conversieAanduidingInhoudingVermissingReisdocument.setID(33);
        conversieAanduidingInhoudingVermissingReisdocument.setRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument(new LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut(
                "Q"));

        final AanduidingInhoudingVermissingReisdocument aanduiding =
                new AanduidingInhoudingVermissingReisdocument();
        aanduiding.setID((short) 55);
        aanduiding.setCode(new AanduidingInhoudingVermissingReisdocumentCodeAttribuut("X"));
        aanduiding.setNaam(new NaamEnumeratiewaardeAttribuut("omschrijving"));
        conversieAanduidingInhoudingVermissingReisdocument.setAanduidingInhoudingVermissingReisdocument(aanduiding);

        final String result = subject.writeValueAsString(conversieAanduidingInhoudingVermissingReisdocument);
        Assert.assertEquals(
            "{\"aanduidingInhoudingVermissingReisdocument\":55,\"iD\":33,\"rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument\":\"Q\"}",
            result);
    }

    @Test
    public void testDeserialize() throws IOException {
        final String json =
                "{\"aanduidingInhoudingVermissingReisdocument\":55,\"iD\":33,\"rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument\":\"Q\"}";

        final ConversieAanduidingInhoudingVermissingReisdocument result =
                subject.readValue(json, ConversieAanduidingInhoudingVermissingReisdocument.class);

        Assert.assertNotNull(result.getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument());
        Assert.assertEquals("Q", result.getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument().getWaarde());
        Assert.assertEquals(Integer.valueOf(33), result.getID());
        Assert.assertNotNull(result.getAanduidingInhoudingVermissingReisdocument());
        Assert.assertEquals(
            Short.valueOf((short) 55),
                result.getAanduidingInhoudingVermissingReisdocument().getID());
    }
}
