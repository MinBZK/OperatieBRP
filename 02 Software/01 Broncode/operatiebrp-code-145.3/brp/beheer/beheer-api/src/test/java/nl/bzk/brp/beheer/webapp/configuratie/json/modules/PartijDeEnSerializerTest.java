/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class PartijDeEnSerializerTest extends AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpJsonObjectMapper subject;

    @Test
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(Partij.class);
        final Partij value = reader.readValue("{}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals(null, value.getCode()); // Primitive waarde van int is 0.
        Assert.assertNull(value.getNaam());
        Assert.assertNull(value.getSoortPartij());
        Assert.assertNull(value.getDatumIngang());
        Assert.assertNull(value.getDatumEinde());
        Assert.assertNull(value.getIndicatieAutomatischFiatteren());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(Partij.class);
        final Partij value = reader.readValue(
                "{\"code\":\"197001\",\"naam\":\"Naam\",\"soort\":1,\"datumIngang\":19710101,\"datumEinde\":19720102,\"automatischFiatteren\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals("197001", value.getCode());
        Assert.assertEquals("Naam", value.getNaam());
        // Assert.assertEquals(Short.valueOf("1"), value.getSoortPartij().getId());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19720102), value.getDatumEinde());
        Assert.assertEquals(Boolean.TRUE, value.getIndicatieAutomatischFiatteren());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledigTest() throws IOException {
        final ObjectReader reader = subject.readerFor(Partij.class);
        final Partij value = reader.readValue(
                "{\"id\":2212,\"code\":\"602601\",\"naam\":\"'t Lange Land Ziekenhuis\",\"datumIngang\":20040601,\"automatischFiatteren\":\"Nee\","
                        + "\"datumOvergangNaarBrp\":20150101}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Short.valueOf("2212"), value.getId());
        Assert.assertEquals("602601", value.getCode());
        Assert.assertEquals("'t Lange Land Ziekenhuis", value.getNaam());
        Assert.assertEquals(Integer.valueOf(20040601), value.getDatumIngang());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieAutomatischFiatteren());
        Assert.assertEquals(Integer.valueOf(20150101), value.getDatumOvergangNaarBrp());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Partij heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);
        LOG.info("Json string: {}", json);

        final ObjectReader reader = subject.readerFor(Partij.class);
        final Partij weer = reader.readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getCode(), weer.getCode());
        Assert.assertEquals(heen.getNaam(), weer.getNaam());
        if (heen.getSoortPartij() != null) {
            Assert.assertEquals(heen.getSoortPartij().getId(), weer.getSoortPartij().getId());
        }
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getIndicatieAutomatischFiatteren(), weer.getIndicatieAutomatischFiatteren());
    }
}
