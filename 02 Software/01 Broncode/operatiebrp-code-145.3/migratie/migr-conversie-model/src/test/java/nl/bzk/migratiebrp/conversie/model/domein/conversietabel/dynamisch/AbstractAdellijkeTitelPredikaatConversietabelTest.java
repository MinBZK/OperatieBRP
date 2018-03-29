/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractAdellijkeTitelPredikaatConversietabelTest {
    private AbstractAdellijkeTitelPredikaatConversietabel subject;

    @Before
    public void setup() {
        final List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> data = new ArrayList<>();
        data.add(
                new SimpleEntry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>(
                        new Lo3AdellijkeTitelPredikaatCode("X"),
                        new AdellijkeTitelPredikaatPaar(new BrpCharacter('X'), new BrpCharacter('Y'), BrpGeslachtsaanduidingCode.MAN)));

        subject = new AbstractAdellijkeTitelPredikaatConversietabel(data) {
        };

    }

    private static final Lo3AdellijkeTitelPredikaatCode LO3 = new Lo3AdellijkeTitelPredikaatCode("S");
    private static final AdellijkeTitelPredikaatPaar BRP =
            new AdellijkeTitelPredikaatPaar(new BrpCharacter('A'), new BrpCharacter('B'), new BrpGeslachtsaanduidingCode("M"));
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);

    @Test
    public void bepaalOnderzoekLo3() {
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekLo3(new Lo3AdellijkeTitelPredikaatCode("S", null)));
        Assert.assertEquals(ONDERZOEK, subject.bepaalOnderzoekLo3(new Lo3AdellijkeTitelPredikaatCode("S", ONDERZOEK)));
    }

    @Test
    public void bepaalOnderzoekBrp() {
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(null));
        Assert.assertEquals(null, subject.bepaalOnderzoekBrp(new AdellijkeTitelPredikaatPaar(null, null, new BrpGeslachtsaanduidingCode("P", null))));
        Assert.assertEquals(
                ONDERZOEK,
                subject.bepaalOnderzoekBrp(
                        new AdellijkeTitelPredikaatPaar(
                                new BrpCharacter('I', null),
                                new BrpCharacter('P', ONDERZOEK),
                                new BrpGeslachtsaanduidingCode("P", ONDERZOEK))));
        Assert.assertEquals(
                ONDERZOEK,
                subject.bepaalOnderzoekBrp(
                        new AdellijkeTitelPredikaatPaar(
                                new BrpCharacter('I', ONDERZOEK),
                                new BrpCharacter('P', null),
                                new BrpGeslachtsaanduidingCode("P", null))));
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
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getAdellijkeTitel().getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(null, ONDERZOEK).getAdellijkeTitel().getOnderzoek());
        Assert.assertEquals(BRP.getAdellijkeTitel().getWaarde(), subject.voegOnderzoekToeBrp(BRP, null).getAdellijkeTitel().getWaarde());
        Assert.assertEquals(null, subject.voegOnderzoekToeBrp(BRP, null).getAdellijkeTitel().getOnderzoek());
        Assert.assertEquals(BRP.getAdellijkeTitel().getWaarde(), subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getAdellijkeTitel().getWaarde());
        Assert.assertEquals(ONDERZOEK, subject.voegOnderzoekToeBrp(BRP, ONDERZOEK).getAdellijkeTitel().getOnderzoek());
    }

    @Test
    public void isBijzondereSituatieLB035VanToepassing() {
        Assert.assertTrue(subject.isBijzondereSituatieLB035VanToepassing(new Lo3AdellijkeTitelPredikaatCode("X"), new Lo3Geslachtsaanduiding("V")));
        Assert.assertFalse(subject.isBijzondereSituatieLB035VanToepassing(new Lo3AdellijkeTitelPredikaatCode("X"), new Lo3Geslachtsaanduiding("M")));
    }
}
