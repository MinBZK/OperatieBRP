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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.lev.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DienstDeEnSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Ignore
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.reader(Dienst.class);
        final Dienst value = reader.<Dienst>readValue("{}");

        Assert.assertNotNull(value);
        Assert.assertEquals(null, value.getID());
        Assert.assertEquals(null, value.getAttenderingscriterium());
        Assert.assertEquals(null, value.getDatumIngang());
        Assert.assertEquals(null, value.getDatumEinde());
        Assert.assertEquals(null, value.getEersteSelectiedatum());
        Assert.assertEquals(null, value.getSelectieperiodeInMaanden());

        controleerHeenEnWeer(value);
    }

    @Ignore
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.reader(Dienst.class);
        final Dienst value =
                reader.<Dienst>readValue("{\"iD\":2,\"abonnement\":\"42\",\"catalogusoptie\":4,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"toestand\":2,\"eersteSelectiedatum\":\"19730303\",\"selectieperiodeInMaanden\":3}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getID());
        Assert.assertEquals(new AttenderingscriteriumAttribuut("att"), value.getAttenderingscriterium());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(19710101), value.getDatumIngang());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(19720202), value.getDatumEinde());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(19730303), value.getEersteSelectiedatum());
        Assert.assertEquals(new SelectieperiodeInMaandenAttribuut((short) 3), value.getSelectieperiodeInMaanden());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Dienst heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.reader(Dienst.class);
        final Dienst weer = reader.<Dienst>readValue(json);

        assertGelijk(heen, weer);
    }

    static void assertGelijk(final Dienst heen, final Dienst weer) {
        Assert.assertEquals(heen.getID(), weer.getID());
        Assert.assertEquals(heen.getAttenderingscriterium(), weer.getAttenderingscriterium());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getEersteSelectiedatum(), weer.getEersteSelectiedatum());
        Assert.assertEquals(heen.getSelectieperiodeInMaanden(), weer.getSelectieperiodeInMaanden());
    }
}
