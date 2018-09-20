/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class AanduidingHuisnummerConversietabelTest {
    private final AanduidingHuisnummerConversietabel subject = new AanduidingHuisnummerConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void converteerNaarBrp() {
        Assert.assertEquals(null, subject.converteerNaarBrp(null));
        Assert.assertEquals(new BrpAanduidingBijHuisnummerCode("BOVEN"), subject.converteerNaarBrp(new Lo3AanduidingHuisnummer("BOVEN")));
    }

    @Test
    public void converteerNaarLo3() {
        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals(new Lo3AanduidingHuisnummer(null, ONDERZOEK), subject.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode(null, ONDERZOEK)));
        Assert.assertEquals(new Lo3AanduidingHuisnummer("by", null), subject.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("by", null)));
        Assert.assertEquals(new Lo3AanduidingHuisnummer("to", null), subject.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("to", null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerNaarLo3Illegal() {
        subject.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("boven", null));
    }

    @Test
    public void valideerLo3() {
        Assert.assertEquals(true, subject.valideerLo3(null));
        Assert.assertEquals(false, subject.valideerLo3(new Lo3AanduidingHuisnummer("BOVEN")));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3AanduidingHuisnummer("by")));
    }

    @Test
    public void valideerBrp() {
        Assert.assertEquals(true, subject.valideerBrp(null));
        Assert.assertEquals(true, subject.valideerBrp(new BrpAanduidingBijHuisnummerCode(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpAanduidingBijHuisnummerCode("by", null)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpAanduidingBijHuisnummerCode("to", null)));
        Assert.assertEquals(false, subject.valideerBrp(new BrpAanduidingBijHuisnummerCode("boven", null)));
    }
}
