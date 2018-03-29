/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules.conv;

import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, JsonConfiguratie.class }, loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class AbstractAanduidingInhoudingVermissingReisdocumentMixInTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpJsonObjectMapper subject;

    @Test
    public void testSerialize() throws IOException {
        final AanduidingInhoudingOfVermissingReisdocument aanduiding = new AanduidingInhoudingOfVermissingReisdocument('Q', "omschrijving");
        aanduiding.setId((short) 33);

        final AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument =
                new AanduidingInhoudingVermissingReisdocument('X', aanduiding);
        aanduidingInhoudingVermissingReisdocument.setId(55);

        final ObjectWriter writer = subject.writer();
        final String result = writer.writeValueAsString(aanduidingInhoudingVermissingReisdocument);
        LOG.info("Json string: {}", result);
        Assert.assertEquals(
            "{\"aanduidingInhoudingVermissingReisdocument\":33,\"id\":55,\"rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument\":\"X\"}",
            result);
    }

    @Test
    public void testDeserialize() throws IOException {
        final String json =
                "{\"aanduidingInhoudingVermissingReisdocument\":33,\"id\":55,\"rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument\":\"X\"}";

        final AanduidingInhoudingVermissingReisdocument result = subject.readValue(json, AanduidingInhoudingVermissingReisdocument.class);

        Assert.assertEquals('X', result.getLo3AanduidingInhoudingOfVermissingReisdocument());
        Assert.assertNotNull(result.getAanduidingInhoudingOfVermissingReisdocument());
        Assert.assertEquals(Integer.valueOf(55), result.getId());
        Assert.assertEquals(Short.valueOf((short) 33), result.getAanduidingInhoudingOfVermissingReisdocument().getId());
    }
}
