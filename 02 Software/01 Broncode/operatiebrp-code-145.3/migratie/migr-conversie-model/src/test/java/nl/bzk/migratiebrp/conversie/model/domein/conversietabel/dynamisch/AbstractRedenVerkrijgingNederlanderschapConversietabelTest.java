/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import org.junit.Test;

public class AbstractRedenVerkrijgingNederlanderschapConversietabelTest {
    private final AbstractRedenVerkrijgingNederlanderschapConversietabel subject = new AbstractRedenVerkrijgingNederlanderschapConversietabel(
            Collections.emptyList()) {
    };

    private static final Lo3RedenNederlandschapCode LO3 = new Lo3RedenNederlandschapCode("012");
    private static final BrpRedenVerkrijgingNederlandschapCode BRP = new BrpRedenVerkrijgingNederlandschapCode("012");
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);

    @Test
    public void voegOnderzoekToeLo3() {
        assertEquals(null, subject.voegOnderzoekToeLo3(null, null));
        assertEquals(null, subject.voegOnderzoekToeLo3(null, ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.voegOnderzoekToeLo3(null, ONDERZOEK).getOnderzoek());
        assertEquals(LO3.getWaarde(), subject.voegOnderzoekToeLo3(LO3, null).getWaarde());
        assertEquals(null, subject.voegOnderzoekToeLo3(LO3, null).getOnderzoek());
        assertEquals(LO3.getWaarde(), subject.voegOnderzoekToeLo3(LO3, ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.voegOnderzoekToeLo3(LO3, ONDERZOEK).getOnderzoek());
    }

    @Test
    public void voegOnderzoekToeBrp() {
        assertEquals(null, subject.voegOnderzoekToeBrp(null, null));
        assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getOnderzoek());
        assertEquals(BRP.getWaarde(), subject.voegOnderzoekToeBrp(BRP, null).getWaarde());
        assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getOnderzoek());
        assertEquals(BRP.getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getOnderzoek());
    }

}
