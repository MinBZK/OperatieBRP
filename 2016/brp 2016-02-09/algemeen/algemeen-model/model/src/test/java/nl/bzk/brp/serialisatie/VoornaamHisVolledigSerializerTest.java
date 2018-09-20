/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;


public class VoornaamHisVolledigSerializerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer =
        new PersoonHisVolledigStringSerializer();

    @Test
    public void testBackreferenceNaarVolgnummer() throws IOException {
        PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        PersoonHisVolledigImpl persoonHisVolledigImpl = persoonHisVolledigImplBuilder.build();

        PersoonVoornaamHisVolledigImplBuilder persoonVoornaamHisVolledigImplBuilder =
            new PersoonVoornaamHisVolledigImplBuilder(persoonHisVolledigImpl, new VolgnummerAttribuut(1));

        ActieModel actieInhoud =
            new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null,
                new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
        ReflectionTestUtils.setField(actieInhoud, "iD", 1L);
        PersoonVoornaamHisVolledigImpl persoonVoornaamHisVolledigImpl =
            persoonVoornaamHisVolledigImplBuilder.nieuwStandaardRecord(actieInhoud).naam("Peter").eindeRecord()
                .build();

        final SortedSet<PersoonVoornaamHisVolledigImpl> voornamen =
            new TreeSet<PersoonVoornaamHisVolledigImpl>(new VolgnummerComparator());
        voornamen.add(persoonVoornaamHisVolledigImpl);
        persoonHisVolledigImpl.setVoornamen(voornamen);

        assertBackreferenceNaarVolgnummerBestaat(persoonHisVolledigImpl);

        byte[] jsonByteArray = serializer.serialiseer(persoonHisVolledigImpl);
        PersoonHisVolledigImpl persoonVanuitJson = serializer.deserialiseer(jsonByteArray);

        LOGGER.info(new String(jsonByteArray));

        assertBackreferenceNaarVolgnummerBestaat(persoonVanuitJson);
    }

    private void assertBackreferenceNaarVolgnummerBestaat(final PersoonHisVolledigImpl persoonHisVolledigImpl) {
        Assert.assertNotNull(persoonHisVolledigImpl.getVoornamen().iterator().next().getPersoonVoornaamHistorie()
                .getHistorie().iterator().next().getPersoonVoornaam().getVolgnummer());
    }

}
