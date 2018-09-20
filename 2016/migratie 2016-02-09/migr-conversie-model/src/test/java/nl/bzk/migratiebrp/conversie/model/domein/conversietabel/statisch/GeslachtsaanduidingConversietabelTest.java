/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class GeslachtsaanduidingConversietabelTest {
    private final GeslachtsaanduidingConversietabel subject = new GeslachtsaanduidingConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void converteerNaarBrp() {
        Assert.assertEquals(null, subject.converteerNaarBrp(null));
        Assert.assertEquals(null, subject.converteerNaarBrp(new Lo3Geslachtsaanduiding(null, ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarBrp(new Lo3Geslachtsaanduiding(null, ONDERZOEK)).getOnderzoek());
        Assert.assertEquals("M", subject.converteerNaarBrp(new Lo3Geslachtsaanduiding("M", null)).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarBrp(new Lo3Geslachtsaanduiding("M", null)).getOnderzoek());
        Assert.assertEquals("V", subject.converteerNaarBrp(new Lo3Geslachtsaanduiding("V", null)).getWaarde());
        Assert.assertEquals("O", subject.converteerNaarBrp(new Lo3Geslachtsaanduiding("O", null)).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarBrp(new Lo3Geslachtsaanduiding("X", ONDERZOEK)).getWaarde());
    }

    @Test
    public void converteerNaarLo3() {
        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode(null, ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode(null, ONDERZOEK)).getOnderzoek());
        Assert.assertEquals("M", subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode("M", null)).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode("M", null)).getOnderzoek());
        Assert.assertEquals("V", subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode("V", null)).getWaarde());
        Assert.assertEquals("O", subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode("O", null)).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpGeslachtsaanduidingCode("X", ONDERZOEK)).getWaarde());
    }

    @Test
    public void valideerLo3() {
        Assert.assertEquals(true, subject.valideerLo3(null));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3Geslachtsaanduiding(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3Geslachtsaanduiding("M", null)));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3Geslachtsaanduiding("V", null)));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3Geslachtsaanduiding("O", null)));
        Assert.assertEquals(false, subject.valideerLo3(new Lo3Geslachtsaanduiding("X", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        Assert.assertEquals(true, subject.valideerBrp(null));
        Assert.assertEquals(true, subject.valideerBrp(new BrpGeslachtsaanduidingCode(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpGeslachtsaanduidingCode("M", null)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpGeslachtsaanduidingCode("X", ONDERZOEK)));
    }
}
