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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
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
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
@Transactional(transactionManager = RepositoryConfiguratie.TRANSACTION_MANAGER)
public class PartijRolDeEnSerializerIT extends AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(PartijRol.class);
        reader.<PartijRol>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(PartijRol.class);
        final PartijRol value = reader.readValue("{\"partij\":630,\"rol\":\"2\",\"datumIngang\":19710101,\"datumEinde\":19720102}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals("062601", value.getPartij().getCode());
        Assert.assertEquals(Short.valueOf("630"), value.getPartij().getId());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19720102), value.getDatumEinde());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledigTest() throws IOException {
        final ObjectReader reader = subject.readerFor(PartijRol.class);
        final PartijRol value = reader.<PartijRol>readValue("{\"id\":2212,\"partij\":630,\"rol\":\"2\",\"datumIngang\":20040601}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf("2212"), value.getId());
        Assert.assertEquals("062601", value.getPartij().getCode());
        Assert.assertEquals(Short.valueOf("630"), value.getPartij().getId());
        Assert.assertEquals(Integer.valueOf(20040601), value.getDatumIngang());
        Assert.assertEquals(Rol.BIJHOUDINGSORGAAN_COLLEGE, value.getRol());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final PartijRol heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);
        LOG.info("Json string: {}", json);

        final ObjectReader reader = subject.readerFor(PartijRol.class);
        final PartijRol weer = reader.readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getPartij().getCode(), weer.getPartij().getCode());
        Assert.assertEquals(heen.getPartij().getId(), weer.getPartij().getId());
        Assert.assertEquals(heen.getRol(), weer.getRol());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
    }
}
