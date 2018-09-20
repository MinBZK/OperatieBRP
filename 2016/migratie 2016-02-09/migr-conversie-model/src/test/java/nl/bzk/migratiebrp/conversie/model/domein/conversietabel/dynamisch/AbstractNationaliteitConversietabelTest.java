/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class AbstractNationaliteitConversietabelTest {
    private final AbstractNationaliteitConversietabel subject = new AbstractNationaliteitConversietabel() {

        @Override
        public boolean valideerLo3(final Lo3NationaliteitCode input) {
            return false;
        }

        @Override
        public boolean valideerBrp(final BrpNationaliteitCode input) {
            return false;
        }
    };

    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);
    private static final Lo3NationaliteitCode LO3 = new Lo3NationaliteitCode("12", ONDERZOEK);
    private static final Lo3NationaliteitCode LO3_ALLEEN_ONDERZOEK = new Lo3NationaliteitCode(null, ONDERZOEK);
    private static final BrpNationaliteitCode BRP = new BrpNationaliteitCode((short) 24, ONDERZOEK);
    private static final BrpNationaliteitCode BRP_ALLEEN_ONDERZOEK = new BrpNationaliteitCode(null, ONDERZOEK);

    @Test
    public void converteerNaarBrp() {
        Assert.assertEquals(null, subject.converteerNaarBrp(null));
        Assert.assertEquals(Short.valueOf((short) 12), subject.converteerNaarBrp(LO3).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarBrp(LO3).getOnderzoek());
        Assert.assertEquals(null, subject.converteerNaarBrp(LO3_ALLEEN_ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarBrp(LO3_ALLEEN_ONDERZOEK).getOnderzoek());
    }

    @Test
    public void converteerNaarLo3() {
        Assert.assertEquals(null, subject.converteerNaarLo3(null));
        Assert.assertEquals("0024", subject.converteerNaarLo3(BRP).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(BRP).getOnderzoek());
        Assert.assertEquals(null, subject.converteerNaarLo3(BRP_ALLEEN_ONDERZOEK).getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.converteerNaarLo3(BRP_ALLEEN_ONDERZOEK).getOnderzoek());
    }
}
