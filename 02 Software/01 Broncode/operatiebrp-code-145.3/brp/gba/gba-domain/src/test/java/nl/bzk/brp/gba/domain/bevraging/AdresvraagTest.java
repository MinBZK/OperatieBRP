/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag.SoortIdentificatie;
import org.junit.Assert;
import org.junit.Test;

public class AdresvraagTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Test
    public void test() {
        final Adresvraag subject = new Adresvraag();
        assertGelijk(subject, reserialize(subject));
        subject.setPartijCode("007656");
        assertGelijk(subject, reserialize(subject));
        subject.setSoortIdentificatie(SoortIdentificatie.ADRES);
        assertGelijk(subject, reserialize(subject));
        subject.setZoekRubrieken(Collections.singletonList("lijst van rubrieken"));
        assertGelijk(subject, reserialize(subject));
        subject.setGevraagdeRubrieken(Collections.singletonList("lijst van andere rubrieken"));
        assertGelijk(subject, reserialize(subject));

        subject.setZoekCriteria(new ArrayList<>());
        assertGelijk(subject, reserialize(subject));

        final ZoekCriterium crit1 = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        subject.getZoekCriteria().add(crit1);
        assertGelijk(subject, reserialize(subject));
        assertGelijk(subject, reserialize(subject));
        crit1.setWaarde("waarde");
        assertGelijk(subject, reserialize(subject));

        final ZoekCriterium crit1of = new ZoekCriterium(Element.PERSOON_VOORNAAM_NAAM.getNaam());
        crit1.setOf(crit1of);
        assertGelijk(subject, reserialize(subject));
        crit1of.setWaarde("ofWaarde");

        final ZoekCriterium crit2 = new ZoekCriterium(Element.PERSOON_VOORNAAM_NAAM.getNaam());
        subject.getZoekCriteria().add(crit2);
        assertGelijk(subject, reserialize(subject));
        crit2.setWaarde("waarde2");
        assertGelijk(subject, reserialize(subject));
    }

    @Test
    public void toMedebewonersAdresvraag() {
        final Adresvraag subject = new Adresvraag();
        final ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("1234567890");
        subject.setSoortIdentificatie(SoortIdentificatie.PERSOON);
        subject.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        subject.setGevraagdeRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        subject.setZoekRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        subject.setZoekCriteria(Collections.singletonList(criterium));

        final Adresvraag result = subject.toMedebewonersAdresvraag("bag_sleutel");
        assertEquals(Arrays.asList("01.01.10", "01.01.20"), result.getGevraagdeRubrieken());
        assertEquals(null, result.getZoekRubrieken());
        assertEquals(SoortDienst.ZOEK_PERSOON, result.getSoortDienst());
        assertEquals(Collections.singletonList(
                "<Naam: Persoon.Adres.IdentificatiecodeNummeraanduiding, Waarde: bag_sleutel>"),
                result.getZoekCriteria().stream().map(ZoekCriterium::toString).collect(Collectors.toList()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toMedebewonersAdresvraagVoorAdresIdentificatie() {
        final Adresvraag subject = new Adresvraag();
        subject.setSoortIdentificatie(SoortIdentificatie.ADRES);
        subject.toMedebewonersAdresvraag("bag_sleutel");
    }

    private void assertGelijk(final Adresvraag expected, final Adresvraag actual) {
        assertEquals("partij code", expected.getPartijCode(), actual.getPartijCode());
        assertEquals("soor identificatie", expected.getSoortIdentificatie(), actual.getSoortIdentificatie());
        assertGelijk(expected.getZoekCriteria(), actual.getZoekCriteria());
        assertEquals("zoek rubrieken", expected.getZoekRubrieken(), actual.getZoekRubrieken());
        assertEquals("gevraagde rubrieken", expected.getGevraagdeRubrieken(), actual.getGevraagdeRubrieken());
    }

    private void assertGelijk(final List<ZoekCriterium> expected, final List<ZoekCriterium> actual) {
        if ((expected == null) && (actual == null)) {
            return;
        }
        if ((expected == null) || (actual == null)) {
            Assert.fail("zoek criteria niet gelijk (een van de lijsten is null");
        }
        assertEquals("aantal zoek criteria", expected.size(), actual.size());
        for (int index = 0; index < expected.size(); index++) {
            assertGelijk(expected.get(index), actual.get(index));
        }
    }

    private void assertGelijk(final ZoekCriterium expected, final ZoekCriterium actual) {
        if ((expected == null) && (actual == null)) {
            return;
        }
        if ((expected == null) || (actual == null)) {
            Assert.fail("zoek criteria niet gelijk (een van de objecten is null");
        }
        assertEquals("naam", expected.getNaam(), actual.getNaam());
        assertEquals("waarde", expected.getWaarde(), actual.getWaarde());
        assertGelijk(expected.getOf(), actual.getOf());
    }


    private Adresvraag reserialize(final Adresvraag object) {
        final String serialized = serializer.serialiseerNaarString(object);
        LOGGER.info("Serialized: {}", serialized);
        return serializer.deserialiseerVanuitString(serialized, Adresvraag.class);
    }
}
