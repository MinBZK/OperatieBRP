/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;

import org.junit.Assert;
import org.junit.Test;

public class BrpDatumTijdTest {

    @Test
    public void testBrpDatumTijd() {
        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102).compareTo(BrpDatumTijd.fromDatum(19800102)) == 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102).compareTo(BrpDatumTijd.fromDatum(19900102)) == 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102).compareTo(BrpDatumTijd.fromDatum(20000102)) == 0);

        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102).compareTo(BrpDatumTijd.fromDatum(19900102)) < 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102).compareTo(BrpDatumTijd.fromDatum(20000102)) < 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(19800102).compareTo(BrpDatumTijd.fromDatum(20000102)) < 0);

        Assert.assertTrue(BrpDatumTijd.fromDatum(19900102).compareTo(BrpDatumTijd.fromDatum(19800102)) > 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102).compareTo(BrpDatumTijd.fromDatum(19900102)) > 0);
        Assert.assertTrue(BrpDatumTijd.fromDatum(20000102).compareTo(BrpDatumTijd.fromDatum(19800102)) > 0);
    }

    @Test
    public void testRondConversieVanuitLO3() {
        final Lo3Datumtijdstempel lo3bron = new Lo3Datumtijdstempel(20121204072930000L);
        final BrpDatumTijd brp = lo3bron.converteerNaarBrpDatumTijd();
        final Lo3Datumtijdstempel lo3resultaat = brp.converteerNaarLo3Datumtijdstempel();

        Assert.assertTrue(lo3bron.equals(lo3resultaat));
    }

    @Test
    public void testRondConversieVanuitLO3MetMillis() {
        final Lo3Datumtijdstempel lo3bron = new Lo3Datumtijdstempel(20121204072930888L);
        final BrpDatumTijd brp = lo3bron.converteerNaarBrpDatumTijd();
        final Lo3Datumtijdstempel lo3resultaat = brp.converteerNaarLo3Datumtijdstempel();

        Assert.assertEquals(lo3bron, lo3resultaat);
    }

}
