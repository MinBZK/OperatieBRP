/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import org.junit.Before;
import org.junit.Test;

public class BasisvraagTest {

    private Basisvraag basisvraag = new Basisvraag();

    @Before
    public void setup() {
        basisvraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        basisvraag.setPartijCode("001234");
        basisvraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        basisvraag.setZoekRubrieken(Arrays.asList("01.01.10", "01.01.20"));

        ZoekCriterium z1 = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer");
        z1.setWaarde("1234567890");
        ZoekCriterium z2 = new ZoekCriterium("Persoon.Adres.Postcode");
        z2.setWaarde("1234AA");

        basisvraag.setZoekCriteria(Arrays.asList(z1, z2));
    }

    @Test
    public void testToString() {
        assertEquals(
                "<SoortDienst: ZOEK_PERSOON, PartijCode: 001234, GevraagdeRubrieken: [01.01.10, 01.01.20], ZoekRubrieken: [01.01.10, 01.01.20], ZoekCriteria: "
                        + "[<Naam: Persoon.Identificatienummers.Administratienummer, Waarde: 1234567890>, <Naam: Persoon.Adres.Postcode, Waarde: 1234AA>]>",
                basisvraag.toString());
    }
}
