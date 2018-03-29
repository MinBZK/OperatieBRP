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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
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
public class VoorvoegselDeEnSerializerIT extends AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = NullPointerException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(Voorvoegsel.class);
        reader.<Voorvoegsel>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(Voorvoegsel.class);
        final Voorvoegsel value = reader.<Voorvoegsel>readValue("{\"voorvoegsel\":\"van der\",\"scheidingsteken\":\"-\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals("van der", value.getVoorvoegselSleutel().getVoorvoegsel());
        Assert.assertEquals('-', value.getVoorvoegselSleutel().getScheidingsteken());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledigTest() throws IOException {
        final ObjectReader reader = subject.readerFor(Voorvoegsel.class);
        final Voorvoegsel value = reader.<Voorvoegsel>readValue("{\"id\":2212,\"voorvoegsel\":\"van der\",\"scheidingsteken\":\"-\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Short.valueOf("2212"), value.getId());
        Assert.assertEquals("van der", value.getVoorvoegselSleutel().getVoorvoegsel());
        Assert.assertEquals('-', value.getVoorvoegselSleutel().getScheidingsteken());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Voorvoegsel heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);
        LOG.info("Json string: {}", json);

        final ObjectReader reader = subject.readerFor(Voorvoegsel.class);
        final Voorvoegsel weer = reader.<Voorvoegsel>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getVoorvoegselSleutel().getVoorvoegsel(), weer.getVoorvoegselSleutel().getVoorvoegsel());
        Assert.assertEquals(heen.getVoorvoegselSleutel().getScheidingsteken(), weer.getVoorvoegselSleutel().getScheidingsteken());
    }
}
