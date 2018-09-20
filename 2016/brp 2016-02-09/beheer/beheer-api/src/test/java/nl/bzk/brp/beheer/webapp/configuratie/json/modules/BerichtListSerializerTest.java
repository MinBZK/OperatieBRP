/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.Date;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtResultaatGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BerichtListSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void empty() throws IOException {
        Assert.assertEquals("{}", write(new BerichtListView(null)));

        final BerichtModel berichtModel = new BerichtModel();
        Assert.assertEquals("{}", write(new BerichtListView(berichtModel)));
    }

    @Test
    public void complete() throws IOException {
        final BerichtModel berichtModel =
                new BerichtModel(new SoortBerichtAttribuut(SoortBericht.BHG_AFS_CORRIGEER_AFSTAMMING), new RichtingAttribuut(Richting.INGAAND));
        ReflectionTestUtils.setField(berichtModel, "iD", 2L);
        berichtModel.setStuurgegevens(new BerichtStuurgegevensGroepModel(
            (short) 123,
            new SysteemNaamAttribuut("zendsys"),
            (short) 543,
            new SysteemNaamAttribuut("ontvangsys"),
            new ReferentienummerAttribuut("ref"),
            new ReferentienummerAttribuut("cross"),
            new DatumTijdAttribuut(new Date(200000000)),
            new DatumTijdAttribuut(new Date(100000000))));

        berichtModel.setParameters(new BerichtParametersGroepModel(
            new VerwerkingswijzeAttribuut(Verwerkingswijze.BIJHOUDING),
            null, null,
            new SoortSynchronisatieAttribuut(SoortSynchronisatie.MUTATIEBERICHT),
            22344,
            null,
            null,
            null,
            null,
            null));

        berichtModel.setResultaat(new BerichtResultaatGroepModel(new SoortMeldingAttribuut(SoortMelding.WAARSCHUWING), new VerwerkingsresultaatAttribuut(
            Verwerkingsresultaat.FOUTIEF), new BijhoudingsresultaatAttribuut(Bijhoudingsresultaat.VERWERKT)));

        Assert.assertEquals(
            "{\"iD\":2,\"soort\":26,\"zendendePartij\":123,\"ontvangendePartij\":543,\"referentienummer\":\"ref\",\"crossReferentienummer\":\"cross\","
                    + "\"verzenddatum\":\"1970-01-03 08:33:20\",\"ontvangstdatum\":\"1970-01-02 04:46:40\",\"soortSynchronisatie\":1,"
                    + "\"verwerkingswijze\":1,\"bijhouding\":1}",
            write(new BerichtListView(berichtModel)));
    }

    private Object write(final BerichtListView object) throws JsonProcessingException {
        final ObjectWriter writer = subject.writer();
        return writer.writeValueAsString(object);
    }
}
