/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link AdellijkeTitelPredikaatConversietabel}.
 */

public class AdellijkeTitelPredikaatConversietabelTest {

    private static final String X1 = "X1";
    private static final String X2 = "X2";
    private static final String X3 = "X3";

    @Test
    public void test() {
        final List<AdellijkeTitelPredikaat> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, Geslachtsaanduiding.MAN, AdellijkeTitel.B, Predicaat.H));
        list.add(maakConversietabelRegel(X2, Geslachtsaanduiding.MAN, AdellijkeTitel.G, null));
        list.add(maakConversietabelRegel(X3, Geslachtsaanduiding.VROUW, null, Predicaat.J));

        final AdellijkeTitelPredikaatConversietabel subject = new AdellijkeTitelPredikaatConversietabel(list);

        Assert.assertEquals(
            X1,
            subject.converteerNaarLo3(
                new AdellijkeTitelPredikaatPaar(new BrpCharacter('B', null), new BrpCharacter('H', null), BrpGeslachtsaanduidingCode.MAN)).getWaarde());

        Assert.assertEquals(
            X2,
            subject.converteerNaarLo3(new AdellijkeTitelPredikaatPaar(new BrpCharacter('G', null), null, BrpGeslachtsaanduidingCode.MAN)).getWaarde());

        Assert.assertEquals(
            X3,
            subject.converteerNaarLo3(new AdellijkeTitelPredikaatPaar(null, new BrpCharacter('J', null), BrpGeslachtsaanduidingCode.VROUW)).getWaarde());

    }

    private AdellijkeTitelPredikaat maakConversietabelRegel(
        final String lo3,
        final Geslachtsaanduiding brpGeslacht,
        final AdellijkeTitel brpAdellijkeTitel,
        final Predicaat brpPredikaat)
    {

        final AdellijkeTitelPredikaat result = new AdellijkeTitelPredikaat(lo3, brpGeslacht);
        result.setAdellijkeTitel(brpAdellijkeTitel);
        result.setPredikaat(brpPredikaat);
        return result;
    }
}
