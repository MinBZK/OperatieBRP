/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.junit.Assert;
import org.junit.Test;

public class Af01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String A_NUMMER = "9876543210";
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0599";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Af01Bericht bericht = new Af01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "X");
        bericht.setHeader(Lo3HeaderVeld.GEMEENTE, "0517");
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Af01Bericht af01Bericht = new Af01Bericht();
        af01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "B");
        af01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, A_NUMMER);
        af01Bericht.setBronPartijCode(BRON_GEMEENTE);
        af01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        af01Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(af01Bericht);

        Assert.assertEquals("Af01", af01Bericht.getBerichtType());
        Assert.assertNull(af01Bericht.getStartCyclus());
        Assert.assertEquals(A_NUMMER, af01Bericht.getHeaderWaarde(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("B", af01Bericht.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));

        final Af01Bericht controleBericht = new Af01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "B");
        controleBericht.setHeader(Lo3HeaderVeld.A_NUMMER, A_NUMMER);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(af01Bericht.getMessageId());

        Assert.assertTrue(af01Bericht.equals(af01Bericht));
        Assert.assertTrue(controleBericht.equals(af01Bericht));

        Assert.assertFalse(af01Bericht.equals(new Lq01Bericht()));
    }
}
