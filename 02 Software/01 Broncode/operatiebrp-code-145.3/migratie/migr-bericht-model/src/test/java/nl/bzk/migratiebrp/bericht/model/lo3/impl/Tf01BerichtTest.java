/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.junit.Test;

public class Tf01BerichtTest extends AbstractLo3BerichtTestBasis {

    @Test
    public void testLeeg() {
        final Tf01Bericht tf01Bericht = new Tf01Bericht();
        tf01Bericht.setMessageId(MessageIdGenerator.generateId());
        assertEquals("Tf01", tf01Bericht.getBerichtType());
        assertEquals(null, tf01Bericht.getStartCyclus());
    }

    @Test
    public void testFoutredenM() throws IOException, BerichtInhoudException, ClassNotFoundException {

        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setLo3Persoonslijst(maakPersoonslijst("123456789", "987654321", "3333"));
        tb01Bericht.setBronPartijCode("3333");
        tb01Bericht.setDoelPartijCode("5555");

        final Tf01Bericht tf01Bericht = new Tf01Bericht(tb01Bericht, Tf01Bericht.Foutreden.M);
        tf01Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01Bericht);
        assertEquals("Tf01", tf01Bericht.getBerichtType());
        assertEquals(null, tf01Bericht.getStartCyclus());
        assertEquals(Collections.singletonList("0000000000"), tf01Bericht.getGerelateerdeAnummers());
    }

    @Test
    public void testFoutredenV() throws IOException, BerichtInhoudException, ClassNotFoundException {

        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1B25342");
        tb01Bericht.setLo3Persoonslijst(maakPersoonslijst("123456789", null, "3333"));
        tb01Bericht.setBronPartijCode("3333");
        tb01Bericht.setDoelPartijCode("5555");

        final Tf01Bericht tf01Bericht = new Tf01Bericht(tb01Bericht, Tf01Bericht.Foutreden.V);
        tf01Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tf01Bericht);
        assertEquals("Tf01", tf01Bericht.getBerichtType());
        assertEquals(null, tf01Bericht.getStartCyclus());
        assertEquals(Collections.singletonList("0000000000"), tf01Bericht.getGerelateerdeAnummers());
        assertEquals(Tf01Bericht.Foutreden.V, tf01Bericht.getFoutreden());
        assertEquals(tb01Bericht.getHeaderWaarde(Lo3HeaderVeld.AKTENUMMER), tf01Bericht.getAktenummer());
    }
}
