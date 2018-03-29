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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
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
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
@Transactional(transactionManager = RepositoryConfiguratie.TRANSACTION_MANAGER)
public class DienstbundelDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(Dienstbundel.class);
        reader.<Dienstbundel>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(Dienstbundel.class);
        final Dienstbundel value = reader.<Dienstbundel>readValue(
                "{\"id\":9001,\"leveringsautorisatie\":9001,\"naam\":\"Spontaan\",\"datumIngang\":19710101,\"datumEinde\":\"29720202\","
                        + "\"naderePopulatiebeperking\":\"WAAR\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(9001), value.getId());
        Assert.assertEquals("Spontaan", value.getNaam());
        Assert.assertNotNull(value.getLeveringsautorisatie());
        Assert.assertEquals(Integer.valueOf(9001), value.getLeveringsautorisatie().getId());
        Assert.assertEquals("WAAR", value.getNaderePopulatiebeperking());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(29720202), value.getDatumEinde());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(Dienstbundel.class);
        final Dienstbundel value =
                reader.<Dienstbundel>readValue("{\"leveringsautorisatie\":9001,\"naam\":\"Spontaan\",\"datumIngang\":19710101,\"datumEinde\":\"29720202\","
                        + "\"naderePopulatiebeperking\":\"WAAR\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals("Spontaan", value.getNaam());
        Assert.assertNotNull(value.getLeveringsautorisatie());
        Assert.assertEquals(Integer.valueOf(9001), value.getLeveringsautorisatie().getId());
        Assert.assertEquals("WAAR", value.getNaderePopulatiebeperking());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(29720202), value.getDatumEinde());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Dienstbundel heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(Dienstbundel.class);
        final Dienstbundel weer = reader.<Dienstbundel>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getNaam(), weer.getNaam());
        Assert.assertEquals(heen.getLeveringsautorisatie().getId(), weer.getLeveringsautorisatie().getId());
        Assert.assertEquals(heen.getNaderePopulatiebeperking(), weer.getNaderePopulatiebeperking());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
    }
}
