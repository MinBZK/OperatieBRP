/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.Collections;
import java.util.Map.Entry;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

import org.junit.Assert;
import org.junit.Test;

public class AbstractVoorvoegselConversietabelTest {
    private final AbstractVoorvoegselConversietabel subject = new AbstractVoorvoegselConversietabel(
        Collections.<Entry<Lo3String, VoorvoegselScheidingstekenPaar>>emptyList())
    {
    };

    private static final Lo3String LO3 = new Lo3String("S");
    private static final VoorvoegselScheidingstekenPaar BRP = new VoorvoegselScheidingstekenPaar(new BrpString("VD"), new BrpCharacter('S'));
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test
    public void bepaalOnderzoekLo3() {
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(new Lo3String("S", null)));
        Assert.assertEquals(ONDERZOEK, subject.bepaalOnderzoekLo3(new Lo3String("S", ONDERZOEK)));
    }

    @Test
    public void bepaalOnderzoekBrp() {
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(new VoorvoegselScheidingstekenPaar(null, null)));
        Assert.assertEquals(
            ONDERZOEK,
            subject.bepaalOnderzoekBrp(new VoorvoegselScheidingstekenPaar(new BrpString("VD", null), new BrpCharacter('S', ONDERZOEK))));
        Assert.assertEquals(
            ONDERZOEK,
            subject.bepaalOnderzoekBrp(new VoorvoegselScheidingstekenPaar(new BrpString("VD", ONDERZOEK), new BrpCharacter('S', null))));
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
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getVoorvoegsel().getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getVoorvoegsel().getOnderzoek());
        Assert.assertEquals(BRP.getVoorvoegsel().getWaarde(), subject.voegOnderzoekToeBrp(BRP, null).getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getVoorvoegsel().getOnderzoek());
        Assert.assertEquals(BRP.getVoorvoegsel().getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getVoorvoegsel().getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getVoorvoegsel().getOnderzoek());
    }

}
