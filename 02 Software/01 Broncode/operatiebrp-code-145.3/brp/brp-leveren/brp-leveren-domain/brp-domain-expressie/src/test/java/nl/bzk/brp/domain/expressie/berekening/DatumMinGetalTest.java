/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import static org.junit.Assert.assertEquals;

import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;
import org.junit.Test;

public class DatumMinGetalTest {

    @Test
    public void testDatumVolledigBekend() {
        final DatumLiteral datumLiteral = new DatumLiteral(20171221);
        final GetalLiteral getalLiteral = new GetalLiteral(11L);
        final Expressie res = new DatumMinGetal().apply(datumLiteral, getalLiteral);

        assertEquals(new DatumLiteral(20171210).alsString(), res.alsString());
    }

    @Test
    public void testDatumNietVolledigBekend() {
        final DatumLiteral datumLiteral = new DatumLiteral(20170000);
        final GetalLiteral getalLiteral = new GetalLiteral(11L);
        final Expressie res = new DatumMinGetal().apply(datumLiteral, getalLiteral);

        assertEquals(NullLiteral.INSTANCE.alsString(), res.alsString());
    }

}