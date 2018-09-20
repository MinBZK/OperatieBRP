/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class NaamgebruikConversietabelTest {
    private final NaamgebruikConversietabel subject = new NaamgebruikConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void converteerNaarBrp() {
        Assert.assertEquals("E", subject.converteerNaarBrp(null).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarBrp(null).getOnderzoek());
        Assert.assertEquals("E", subject.converteerNaarBrp(new Lo3AanduidingNaamgebruikCode(null, ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarBrp(new Lo3AanduidingNaamgebruikCode(null, ONDERZOEK)).getOnderzoek());
        Assert.assertEquals("E", subject.converteerNaarBrp(new Lo3AanduidingNaamgebruikCode("E", ONDERZOEK)).getWaarde());
        Assert.assertEquals("X", subject.converteerNaarBrp(new Lo3AanduidingNaamgebruikCode("X", ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarBrp(new Lo3AanduidingNaamgebruikCode("X", ONDERZOEK)).getOnderzoek());
    }

    @Test
    public void converteerNaarLo3() {
        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpNaamgebruikCode(null, ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(new BrpNaamgebruikCode(null, ONDERZOEK)).getOnderzoek());
        Assert.assertEquals("E", subject.converteerNaarLo3(new BrpNaamgebruikCode("E", ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(new BrpNaamgebruikCode("E", ONDERZOEK)).getOnderzoek());
        Assert.assertEquals("X", subject.converteerNaarLo3(new BrpNaamgebruikCode("X", ONDERZOEK)).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(new BrpNaamgebruikCode("X", ONDERZOEK)).getOnderzoek());
    }

    @Test
    public void valideerLo3() {
        Assert.assertEquals(true, subject.valideerLo3(null));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3AanduidingNaamgebruikCode(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3AanduidingNaamgebruikCode("E", ONDERZOEK)));
        Assert.assertEquals(false, subject.valideerLo3(new Lo3AanduidingNaamgebruikCode("X", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        Assert.assertEquals(true, subject.valideerBrp(null));
        Assert.assertEquals(true, subject.valideerBrp(new BrpNaamgebruikCode(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpNaamgebruikCode("E", ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpNaamgebruikCode("X", ONDERZOEK)));
    }

}
