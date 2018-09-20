/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class JsonSerializerTest {

    public static final String JSON_STRING =
        "{\"administratieveHandelingId\":999,\"geleverdePersoonsIds\":[321,543,987],"
            + "\"stuurgegevens\":{\"datumTijdVerzending\":1374410304000,"
            + "\"ontvangendePartijId\":34,\"ontvangendeSysteem\":\"ONV\","
            + "\"referentienummer\":\"123123123123123\",\"zendendePartijId\":363,"
            + "\"zendendeSysteem\":\"TST\"},\"toegangLeveringsautorisatieId\":1234}";

    private final JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    @Test
    public void serialiseertLeegObject() {
        final SynchronisatieBerichtGegevens gegevens = new SynchronisatieBerichtGegevens();

        final String data = jsonStringSerialiseerder.serialiseerNaarString(gegevens);

        assertThat(data, equalTo("{}"));
    }

    @Test
    public void serialiseertEenVolledigObject() throws IOException {
        // given
        final Partij partij = TestPartijBuilder.maker()
            .metNaam("Gem. Test")
            .metSoort(SoortPartij.GEMEENTE)
            .metCode(34)
            .maak();

        ReflectionTestUtils.setField(partij, "iD", (short) 34);
        final PartijAttribuut partijAttribuut = new PartijAttribuut(partij);
        final SynchronisatieBerichtGegevens gegevens =
            new SynchronisatieBerichtGegevens(999L, 1234);
        gegevens.setGeleverdePersoonsIds(Arrays.asList(321, 543, 987));

        final BerichtStuurgegevensGroepBericht bericht = new BerichtStuurgegevensGroepBericht();
        bericht.setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        bericht.setZendendeSysteem(new SysteemNaamAttribuut("TST"));
        bericht.setReferentienummer(new ReferentienummerAttribuut("123123123123123"));
        bericht.setDatumTijdVerzending(DatumTijdAttribuut.bouwDatumTijd(2013, 7, 21, 14, 38, 24));
        bericht.setOntvangendePartij(partijAttribuut);
        bericht.setOntvangendeSysteem(new SysteemNaamAttribuut("ONV"));
        final BerichtStuurgegevensGroepModel berichtStuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(bericht);
        berichtStuurgegevensGroepModel.setZendendePartijId(bericht.getZendendePartij().getWaarde().getID());
        berichtStuurgegevensGroepModel.setOntvangendePartijId(bericht.getOntvangendePartij().getWaarde().getID());
        gegevens.setStuurgegevens(berichtStuurgegevensGroepModel);

        // when
        final String data = jsonStringSerialiseerder.serialiseerNaarString(gegevens);
        final SynchronisatieBerichtGegevens copyGegevens =
                jsonStringSerialiseerder.deserialiseerVanuitString(data);

        // then
        assertThat(data, equalTo(JSON_STRING));

        assertThat(copyGegevens.getAdministratieveHandelingId(), is(gegevens.getAdministratieveHandelingId()));
        assertThat(copyGegevens.getToegangLeveringsautorisatieId(), is(gegevens.getToegangLeveringsautorisatieId()));
    }

    @Test
    public void leestEenJsonStringNaarObject() throws IOException {
        final SynchronisatieBerichtGegevens gegevens =
                jsonStringSerialiseerder.deserialiseerVanuitString(JSON_STRING);

        assertThat(gegevens.getAdministratieveHandelingId(), is(999L));
        assertThat(gegevens.getToegangLeveringsautorisatieId(), is(1234));

        assertThat(gegevens.getStuurgegevens().getOntvangendePartijId(), is((short) 34));
    }


}
