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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.junit.Assert;
import org.junit.Test;

public class PartijDeEnSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.reader(Partij.class);
        final Partij value = reader.<Partij>readValue("{}");

        Assert.assertNotNull(value);
        Assert.assertEquals(null, value.getID());
        Assert.assertEquals(null, value.getCode());
        Assert.assertEquals(null, value.getNaam());
        Assert.assertEquals(null, value.getSoort());
        Assert.assertEquals(null, value.getDatumIngang());
        Assert.assertEquals(null, value.getDatumEinde());
        Assert.assertEquals(null, value.getIndicatieVerstrekkingsbeperkingMogelijk());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.reader(Partij.class);
        final Partij value =
                reader.<Partij>readValue("{\"Code\":19700101,\"Naam\":\"Naam\",\"Soort\":1,\"Datum ingang\":19710101,\"Datum einde\":19720102,\"Verstrekkingsbeperking mogelijk?\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(null, value.getID());
        Assert.assertEquals(new PartijCodeAttribuut(19700101), value.getCode());
        Assert.assertEquals(new NaamEnumeratiewaardeAttribuut("Naam"), value.getNaam());
        Assert.assertEquals(Short.valueOf("1"), value.getSoort().getID());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(19710101), value.getDatumIngang());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(19720102), value.getDatumEinde());
        Assert.assertEquals(new JaNeeAttribuut(Boolean.TRUE), value.getIndicatieVerstrekkingsbeperkingMogelijk());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledigTest() throws IOException {
        final ObjectReader reader = subject.reader(Partij.class);
        final Partij value =
                reader.<Partij>readValue("{\"iD\":2212,\"Code\":602601,\"Naam\":\"'t Lange Land Ziekenhuis\",\"Datum ingang\":20040601,\"Automatisch fiatteren?\":\"Nee\",\"Verstrekkingsbeperking mogelijk?\":\"Ja\",\"Datum overgang naar BRP\":20150101}");
        Assert.assertNotNull(value);
        Assert.assertEquals(Short.valueOf("2212"), value.getID());
        Assert.assertEquals(new PartijCodeAttribuut(602601), value.getCode());
        Assert.assertEquals(new NaamEnumeratiewaardeAttribuut("'t Lange Land Ziekenhuis"), value.getNaam());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(20040601), value.getDatumIngang());
        Assert.assertEquals(new JaNeeAttribuut(Boolean.TRUE), value.getIndicatieVerstrekkingsbeperkingMogelijk());
        Assert.assertEquals(new DatumAttribuut(20150101), value.getDatumOvergangNaarBRP());
    }

    private void controleerHeenEnWeer(final Partij heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.reader(Partij.class);
        final Partij weer = reader.<Partij>readValue(json);

        Assert.assertEquals(heen.getID(), weer.getID());
        Assert.assertEquals(heen.getCode(), weer.getCode());
        Assert.assertEquals(heen.getNaam(), weer.getNaam());
        if (heen.getSoort() != null) {
            Assert.assertEquals(heen.getSoort().getID(), weer.getSoort().getID());
        }
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getIndicatieVerstrekkingsbeperkingMogelijk(), weer.getIndicatieVerstrekkingsbeperkingMogelijk());
    }
}
