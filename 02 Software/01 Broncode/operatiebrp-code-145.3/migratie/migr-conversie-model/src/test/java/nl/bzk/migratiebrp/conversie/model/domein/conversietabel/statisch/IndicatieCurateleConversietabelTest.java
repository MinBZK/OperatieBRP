/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Assert;
import org.junit.Test;

public class IndicatieCurateleConversietabelTest {
    private final IndicatieCurateleConversietabel subject = new IndicatieCurateleConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);

    @Test
    public void converteerNaarBrp() {
        Assert.assertEquals(null, subject.converteerNaarBrp(null));
        Assert.assertEquals(null, subject.converteerNaarBrp(new Lo3IndicatieCurateleregister(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.converteerNaarBrp(new Lo3IndicatieCurateleregister("1", ONDERZOEK)).getWaarde());
        Assert.assertEquals(null, subject.converteerNaarBrp(new Lo3IndicatieCurateleregister("2", ONDERZOEK)));
    }

    @Test
    public void converteerNaarLo3() {
        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpBoolean(null, ONDERZOEK)));
        Assert.assertEquals(null, subject.converteerNaarLo3(new BrpBoolean(false, ONDERZOEK)));
        Assert.assertEquals("1", subject.converteerNaarLo3(new BrpBoolean(true, ONDERZOEK)).getWaarde());
    }

    @Test
    public void valideerLo3() {
        Assert.assertEquals(true, subject.valideerLo3(null));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3IndicatieCurateleregister(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerLo3(new Lo3IndicatieCurateleregister("1", ONDERZOEK)));
        Assert.assertEquals(false, subject.valideerLo3(new Lo3IndicatieCurateleregister("2", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        Assert.assertEquals(true, subject.valideerBrp(null));
        Assert.assertEquals(true, subject.valideerBrp(new BrpBoolean(null, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpBoolean(false, ONDERZOEK)));
        Assert.assertEquals(true, subject.valideerBrp(new BrpBoolean(true, ONDERZOEK)));
    }

}
