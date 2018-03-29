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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht.Foutreden;
import org.junit.Assert;
import org.junit.Test;

public class Lf01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String A_NUMMER = "9876543210";
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0599";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lf01Bericht bericht = new Lf01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);

        Assert.assertFalse(bericht.bevatVerwijsgegevens());

        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "V");
        Assert.assertFalse(bericht.bevatVerwijsgegevens());
        bericht.setHeader(Lo3HeaderVeld.GEMEENTE, "0517");
        testFormatAndParseBericht(bericht);
        Assert.assertTrue(bericht.bevatVerwijsgegevens());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lf01Bericht lf01Bericht = new Lf01Bericht();
        lf01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        lf01Bericht.setANummer(A_NUMMER);
        lf01Bericht.setBronPartijCode(BRON_GEMEENTE);
        lf01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        lf01Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(lf01Bericht);

        Assert.assertNull(lf01Bericht.getStartCyclus());
        Assert.assertEquals(A_NUMMER, lf01Bericht.getANummer());
        Assert.assertEquals(Foutreden.G, lf01Bericht.getFoutreden());

        final Lf01Bericht controleBericht = new Lf01Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        controleBericht.setANummer(A_NUMMER);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(lf01Bericht.getMessageId());

        Assert.assertTrue(lf01Bericht.equals(lf01Bericht));
        Assert.assertTrue(controleBericht.equals(lf01Bericht));

        Assert.assertFalse(lf01Bericht.equals(new Lq01Bericht()));
    }
}
