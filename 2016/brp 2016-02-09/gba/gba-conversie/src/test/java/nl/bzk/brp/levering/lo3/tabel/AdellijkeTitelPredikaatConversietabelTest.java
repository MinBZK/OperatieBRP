/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AdellijkeTitelPredikaatAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link AdellijkeTitelPredikaatConversietabel}.
 */

public class AdellijkeTitelPredikaatConversietabelTest {

    private static final String X1 = "X1";
    private static final String X2 = "X2";
    private static final String X3 = "X3";

    @Test
    public void test() throws ReflectiveOperationException {
        final List<ConversieAdellijkeTitelPredikaat> list = new ArrayList<>();
        list.add(maakConversietabelRegel(X1, Geslachtsaanduiding.MAN, "Y", "Z"));
        list.add(maakConversietabelRegel(X2, Geslachtsaanduiding.MAN, "A", null));
        list.add(maakConversietabelRegel(X3, Geslachtsaanduiding.VROUW, null, "B"));

        final AdellijkeTitelPredikaatConversietabel subject = new AdellijkeTitelPredikaatConversietabel(list);

        Assert.assertEquals(
            X1,
            subject.converteerNaarLo3(
                new AdellijkeTitelPredikaatPaar(new BrpCharacter('Y', null), new BrpCharacter('Z', null), BrpGeslachtsaanduidingCode.MAN)).getWaarde());

        Assert.assertEquals(
            X2,
            subject.converteerNaarLo3(new AdellijkeTitelPredikaatPaar(new BrpCharacter('A', null), null, BrpGeslachtsaanduidingCode.MAN)).getWaarde());

        Assert.assertEquals(
            X3,
            subject.converteerNaarLo3(new AdellijkeTitelPredikaatPaar(null, new BrpCharacter('B', null), BrpGeslachtsaanduidingCode.VROUW)).getWaarde());

    }

    private ConversieAdellijkeTitelPredikaat maakConversietabelRegel(
        final String lo3,
        final Geslachtsaanduiding brpGeslacht,
        final String brpAdellijkeTitel,
        final String brpPredikaat) throws ReflectiveOperationException
    {
        final AdellijkeTitel adellijkeTitel;
        if (brpAdellijkeTitel == null) {
            adellijkeTitel = null;
        } else {
            adellijkeTitel = new AdellijkeTitel(new AdellijkeTitelCodeAttribuut(brpAdellijkeTitel), null, null);
        }

        final Predicaat predikaat;
        if (brpPredikaat == null) {
            predikaat = null;
        } else {
            predikaat = new Predicaat(new PredicaatCodeAttribuut(brpPredikaat), null, null);
        }

        return ReflectionUtils.instantiate(
            ConversieAdellijkeTitelPredikaat.class,
            new LO3AdellijkeTitelPredikaatAttribuut(lo3),
            brpGeslacht,
            adellijkeTitel,
            predikaat);
    }
}
