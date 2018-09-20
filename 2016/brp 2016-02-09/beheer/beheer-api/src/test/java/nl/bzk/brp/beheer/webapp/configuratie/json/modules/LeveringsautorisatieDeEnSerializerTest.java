/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import org.junit.Assert;
import org.junit.Test;

public class LeveringsautorisatieDeEnSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.reader(Leveringsautorisatie.class);
        final Leveringsautorisatie value = reader.<Leveringsautorisatie>readValue("{}");

        Assert.assertNotNull(value);
        Assert.assertEquals(null, value.getID());
        Assert.assertEquals(null, value.getNaam());
        Assert.assertEquals(null, value.getPopulatiebeperking());
        Assert.assertEquals(null, value.getProtocolleringsniveau());
        Assert.assertEquals(null, value.getDatumIngang());
        Assert.assertEquals(null, value.getDatumEinde());
        Assert.assertEquals(new JaNeeAttribuut(Boolean.FALSE), value.getIndicatieAliasSoortAdministratieveHandelingLeveren());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.reader(Leveringsautorisatie.class);
        final Leveringsautorisatie value =
                reader.<Leveringsautorisatie>readValue("{\"iD\":2,\"naam\":\"naampje\",\"populatieBeperking\":\"popBeperk\",\"protocolleringsniveau\":2,"
                                             + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":3,\"indicatieAliasLeveren\":\"Nee\",\"diensten\":["
                                             + "{\"iD\":42,\"abonnement\":\"2\",\"catalogusoptie\":4,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                                             + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\",\"selectieperiodeInMaanden\":3},"
                                             + "{\"iD\":43,\"abonnement\":\"2\",\"catalogusoptie\":5,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                                             + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\",\"selectieperiodeInMaanden\":3}"
                                             + "]}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getID());
        Assert.assertEquals(new NaamEnumeratiewaardeAttribuut("naampje"), value.getNaam());
        Assert.assertEquals(new PopulatiebeperkingAttribuut("popBeperk"), value.getPopulatiebeperking());
        Assert.assertEquals(Protocolleringsniveau.GEHEIM, value.getProtocolleringsniveau());
        Assert.assertEquals(new DatumAttribuut(19710101), value.getDatumIngang());
        Assert.assertEquals(new DatumAttribuut(19720202), value.getDatumEinde());
        Assert.assertEquals(new JaNeeAttribuut(Boolean.FALSE), value.getIndicatieAliasSoortAdministratieveHandelingLeveren());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.reader(Leveringsautorisatie.class);
        final Leveringsautorisatie value =
                reader.<Leveringsautorisatie>readValue("{\"naam\":\"naampje2\",\"populatieBeperking\":\"popBeperkt\",\"protocolleringsniveau\":2,"
                                             + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":3}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getID());
        Assert.assertEquals(new NaamEnumeratiewaardeAttribuut("naampje2"), value.getNaam());
        Assert.assertEquals(new PopulatiebeperkingAttribuut("popBeperkt"), value.getPopulatiebeperking());
        Assert.assertEquals(Protocolleringsniveau.GEHEIM, value.getProtocolleringsniveau());
        Assert.assertEquals(new DatumAttribuut(19710101), value.getDatumIngang());
        Assert.assertEquals(new DatumAttribuut(19720202), value.getDatumEinde());
        Assert.assertEquals(new JaNeeAttribuut(Boolean.FALSE), value.getIndicatieAliasSoortAdministratieveHandelingLeveren());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Leveringsautorisatie heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.reader(Leveringsautorisatie.class);
        final Leveringsautorisatie weer = reader.<Leveringsautorisatie>readValue(json);

        Assert.assertEquals(heen.getID(), weer.getID());
        Assert.assertEquals(heen.getNaam(), weer.getNaam());
        Assert.assertEquals(heen.getPopulatiebeperking(), weer.getPopulatiebeperking());
        Assert.assertEquals(heen.getProtocolleringsniveau(), weer.getProtocolleringsniveau());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getIndicatieAliasSoortAdministratieveHandelingLeveren(), weer.getIndicatieAliasSoortAdministratieveHandelingLeveren());

    }
}
