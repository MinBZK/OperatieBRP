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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
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
public class ToegangLeveringsautorisatieDeEnSerializerIT extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(ToegangLeveringsAutorisatie.class);
        reader.<ToegangLeveringsAutorisatie>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(ToegangLeveringsAutorisatie.class);
        final ToegangLeveringsAutorisatie value = reader.<ToegangLeveringsAutorisatie>readValue(
                "{\"id\":2,\"leveringsautorisatie\":\"2\",\"geautoriseerde\":630,\"ondertekenaar\":628,\"transporteur\":2000,\"datumIngang\":19710101,"
                        + "\"datumEinde\":\"29720202\","
                        + "\"afleverpunt\":\"https://afleverpunt\",\"naderePopulatiebeperking\":\"WAAR\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getId());
        Assert.assertEquals(Integer.valueOf(2), value.getLeveringsautorisatie().getId());
        Assert.assertEquals(Integer.valueOf("630"), value.getGeautoriseerde().getId());
        Assert.assertEquals(Short.valueOf("628"), value.getOndertekenaar().getId());
        Assert.assertEquals(Short.valueOf("2000"), value.getTransporteur().getId());
        Assert.assertEquals("https://afleverpunt", value.getAfleverpunt());
        Assert.assertEquals("WAAR", value.getNaderePopulatiebeperking());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(29720202), value.getDatumEinde());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(ToegangLeveringsAutorisatie.class);
        final ToegangLeveringsAutorisatie value =
                reader.<ToegangLeveringsAutorisatie>readValue("{\"leveringsautorisatie\":\"2\",\"geautoriseerde\":630,\"datumIngang\":19710101}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals(Integer.valueOf("630"), value.getGeautoriseerde().getId());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final ToegangLeveringsAutorisatie heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(ToegangLeveringsAutorisatie.class);
        final ToegangLeveringsAutorisatie weer = reader.<ToegangLeveringsAutorisatie>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getLeveringsautorisatie(), weer.getLeveringsautorisatie());
        Assert.assertEquals(heen.getGeautoriseerde(), weer.getGeautoriseerde());
        Assert.assertEquals(heen.getOndertekenaar(), weer.getOndertekenaar());
        Assert.assertEquals(heen.getTransporteur(), weer.getTransporteur());
        Assert.assertEquals(heen.getAfleverpunt(), weer.getAfleverpunt());
        Assert.assertEquals(heen.getNaderePopulatiebeperking(), weer.getNaderePopulatiebeperking());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());

    }
}
