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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
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
public class LeveringsautorisatieDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = NullPointerException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(Leveringsautorisatie.class);
        reader.<Leveringsautorisatie>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(Leveringsautorisatie.class);
        final Leveringsautorisatie value = reader.readValue(
                "{\"id\":2,\"naam\":\"naampje\",\"stelsel\":2,\"populatieBeperking\":\"popBeperk\",\"protocolleringsniveau\":2,"
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":3,\"indicatieAliasLeveren\":\"Nee\",\"diensten\":["
                        + "{\"id\":42,\"abonnement\":\"2\",\"catalogusoptie\":4,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\","
                        + "\"selectieperiodeInMaanden\":3},"
                        + "{\"id\":43,\"abonnement\":\"2\",\"catalogusoptie\":5,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\","
                        + "\"selectieperiodeInMaanden\":3}"
                        + "]}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getId());
        Assert.assertEquals("naampje", value.getNaam());
        Assert.assertEquals("popBeperk", value.getPopulatiebeperking());
        Assert.assertEquals(Protocolleringsniveau.GEHEIM, value.getProtocolleringsniveau());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19720202), value.getDatumEinde());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieAliasSoortAdministratieveHandelingLeveren());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(Leveringsautorisatie.class);
        final Leveringsautorisatie value =
                reader.<Leveringsautorisatie>readValue("{\"naam\":\"naampje2\",\"stelsel\":2,\"populatieBeperking\":\"popBeperkt\",\"protocolleringsniveau\":2,"
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":3}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals("naampje2", value.getNaam());
        Assert.assertEquals("popBeperkt", value.getPopulatiebeperking());
        Assert.assertEquals(Protocolleringsniveau.GEHEIM, value.getProtocolleringsniveau());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19720202), value.getDatumEinde());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieAliasSoortAdministratieveHandelingLeveren());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Leveringsautorisatie heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(Leveringsautorisatie.class);
        final Leveringsautorisatie weer = reader.readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getNaam(), weer.getNaam());
        Assert.assertEquals(heen.getPopulatiebeperking(), weer.getPopulatiebeperking());
        Assert.assertEquals(heen.getProtocolleringsniveau(), weer.getProtocolleringsniveau());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getIndicatieAliasSoortAdministratieveHandelingLeveren(), weer.getIndicatieAliasSoortAdministratieveHandelingLeveren());

    }
}
