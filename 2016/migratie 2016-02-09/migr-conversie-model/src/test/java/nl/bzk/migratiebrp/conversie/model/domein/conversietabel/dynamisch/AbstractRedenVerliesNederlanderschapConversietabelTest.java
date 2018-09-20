/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.Collections;
import java.util.Map.Entry;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

import org.junit.Assert;
import org.junit.Test;

public class AbstractRedenVerliesNederlanderschapConversietabelTest {
    private final AbstractRedenVerliesNederlanderschapConversietabel subject = new AbstractRedenVerliesNederlanderschapConversietabel(
        Collections.<Entry<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>>emptyList())
    {
    };

    private static final Lo3RedenNederlandschapCode LO3 = new Lo3RedenNederlandschapCode("12");
    private static final BrpRedenVerliesNederlandschapCode BRP = new BrpRedenVerliesNederlandschapCode((short) 12);
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void voegOnderzoekToeLo3() {
        Assert.assertEquals(null, subject.voegOnderzoekToeLo3(null, null));
        Assert.assertEquals(null, subject.voegOnderzoekToeLo3(null, ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeLo3(null, ONDERZOEK).getOnderzoek());
        Assert.assertEquals(LO3.getWaarde(), subject.voegOnderzoekToeLo3(LO3, null).getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeLo3(LO3, null).getOnderzoek());
        Assert.assertEquals(LO3.getWaarde(), subject.voegOnderzoekToeLo3(LO3, ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeLo3(LO3, ONDERZOEK).getOnderzoek());
    }

    @Test
    public void voegOnderzoekToeBrp() {
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, null));
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getOnderzoek());
        Assert.assertEquals(BRP.getWaarde(), subject.voegOnderzoekToeBrp(BRP, null).getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getOnderzoek());
        Assert.assertEquals(BRP.getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getOnderzoek());
    }

}
