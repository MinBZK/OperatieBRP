/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.Collections;
import java.util.Map.Entry;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class AbstractAangeverRedenWijzigingVerblijfConversietabelTest {
    private final AbstractAangeverRedenWijzigingVerblijfConversietabel subject = new AbstractAangeverRedenWijzigingVerblijfConversietabel(
        Collections.<Entry<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>>emptyList())
    {
    };

    private static final Lo3AangifteAdreshouding LO3 = new Lo3AangifteAdreshouding("S");
    private static final AangeverRedenWijzigingVerblijfPaar BRP = new AangeverRedenWijzigingVerblijfPaar(
        new BrpAangeverCode('I'),
        new BrpRedenWijzigingVerblijfCode('P'));
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void bepaalOnderzoekLo3() {
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(new Lo3AangifteAdreshouding("S", null)));
        Assert.assertEquals(ONDERZOEK, subject.bepaalOnderzoekLo3(new Lo3AangifteAdreshouding("S", ONDERZOEK)));
    }

    @Test
    public void bepaalOnderzoekBrp() {
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(new AangeverRedenWijzigingVerblijfPaar(null, null)));
        Assert.assertEquals(ONDERZOEK, subject.bepaalOnderzoekBrp(new AangeverRedenWijzigingVerblijfPaar(
            new BrpAangeverCode('I', null),
            new BrpRedenWijzigingVerblijfCode('P', ONDERZOEK))));
        Assert.assertEquals(ONDERZOEK, subject.bepaalOnderzoekBrp(new AangeverRedenWijzigingVerblijfPaar(
            new BrpAangeverCode('I', ONDERZOEK),
            new BrpRedenWijzigingVerblijfCode('P', null))));
    }

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
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getBrpAangeverCode().getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getBrpRedenWijzigingVerblijfCode().getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getBrpAangeverCode().getOnderzoek());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getBrpRedenWijzigingVerblijfCode().getOnderzoek());
        Assert.assertEquals(BRP.getBrpAangeverCode().getWaarde(), subject.voegOnderzoekToeBrp(BRP, null).getBrpAangeverCode().getWaarde());
        Assert.assertEquals(BRP.getBrpRedenWijzigingVerblijfCode().getWaarde(), subject.voegOnderzoekToeBrp(BRP, null)
                                                                                       .getBrpRedenWijzigingVerblijfCode()
                                                                                       .getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getBrpAangeverCode().getOnderzoek());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getBrpRedenWijzigingVerblijfCode().getOnderzoek());
        Assert.assertEquals(BRP.getBrpAangeverCode().getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getBrpAangeverCode().getWaarde());
        Assert.assertEquals(BRP.getBrpRedenWijzigingVerblijfCode().getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK)
                                                                                       .getBrpRedenWijzigingVerblijfCode()
                                                                                       .getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getBrpAangeverCode().getOnderzoek());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getBrpRedenWijzigingVerblijfCode().getOnderzoek());
    }
}
