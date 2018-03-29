/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import org.junit.Assert;
import org.junit.Test;

public class PersoonsvraagTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Test
    public void test() throws IOException {
        final Persoonsvraag subject = new Persoonsvraag();
        assertGelijk(subject, reserialize(subject));
        subject.setPartijCode("0007656");
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

    private void assertGelijk(final Persoonsvraag expected, final Persoonsvraag actual) {
        Assert.assertEquals("partij code", expected.getPartijCode(), actual.getPartijCode());
        assertGelijk(expected.getZoekCriteria(), actual.getZoekCriteria());
        Assert.assertEquals("zoek rubrieken", expected.getZoekRubrieken(), actual.getZoekRubrieken());
        Assert.assertEquals("gevraagde rubrieken", expected.getGevraagdeRubrieken(), actual.getGevraagdeRubrieken());
    }

    private void assertGelijk(final List<ZoekCriterium> expected, final List<ZoekCriterium> actual) {
        if ((expected == null) && (actual == null)) {
            return;
        }
        if ((expected == null) || (actual == null)) {
            Assert.fail("zoek criteria niet gelijk (een van de lijsten is null");
        }
        Assert.assertEquals("aantal zoek criteria", expected.size(), actual.size());
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
        Assert.assertEquals("naam", expected.getNaam(), actual.getNaam());
        Assert.assertEquals("waarde", expected.getWaarde(), actual.getWaarde());
        assertGelijk(expected.getOf(), actual.getOf());
    }

    private Persoonsvraag reserialize(final Persoonsvraag object) throws IOException {
        final String serialized = serializer.serialiseerNaarString(object);
        LOGGER.info("Serialized: {}", serialized);
        return serializer.deserialiseerVanuitString(serialized, Persoonsvraag.class);
    }
}
