/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import org.junit.Assert;
import org.junit.Test;

public class BrpDatumTijdTest {

    @Test(expected = NullPointerException.class)
    public void ConstructorWaardeNull() {
        new BrpDatumTijd(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDatumTijd() {
        BrpDatumTijd.fromDatumTijd(-1L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDatum() {
        BrpDatumTijd.fromDatum(null, null);
    }

    @Test
    public void testBrpDatumTijd() {
        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102, null).compareTo(BrpDatumTijd.fromDatum(19800102, null)) == 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102, null).compareTo(BrpDatumTijd.fromDatum(19900102, null)) == 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102, null).compareTo(BrpDatumTijd.fromDatum(20000102, null)) == 0);

        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102, null).compareTo(BrpDatumTijd.fromDatum(19900102, null)) < 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102, null).compareTo(BrpDatumTijd.fromDatum(20000102, null)) < 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102, null).compareTo(BrpDatumTijd.fromDatum(20000102, null)) < 0);

        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102, null).compareTo(BrpDatumTijd.fromDatum(19800102, null)) > 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102, null).compareTo(BrpDatumTijd.fromDatum(19900102, null)) > 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102, null).compareTo(BrpDatumTijd.fromDatum(19800102, null)) > 0);
    }

    @Test(expected = NullPointerException.class)
    public void testBrpDatumTijdNull() {
        BrpDatumTijd.fromDatum(19800102, null).compareTo(null);
    }

    @Test
    public void testRondConversieVanuitLO3() {
        final Lo3Datumtijdstempel lo3bron = new Lo3Datumtijdstempel(20121204072930000L);
        final BrpDatumTijd brp = BrpDatumTijd.fromLo3Datumtijdstempel(lo3bron);
        final Lo3Datumtijdstempel lo3resultaat = brp.converteerNaarLo3Datumtijdstempel();

        Assert.assertTrue(lo3bron.equals(lo3resultaat));
    }

    @Test
    public void testRondConversieVanuitLO3MetMillis() {
        final Lo3Datumtijdstempel lo3bron = new Lo3Datumtijdstempel(20121204072930888L);
        final BrpDatumTijd brp = BrpDatumTijd.fromLo3Datumtijdstempel(lo3bron);
        final Lo3Datumtijdstempel lo3resultaat = brp.converteerNaarLo3Datumtijdstempel();

        Assert.assertEquals(lo3bron, lo3resultaat);
    }
}
