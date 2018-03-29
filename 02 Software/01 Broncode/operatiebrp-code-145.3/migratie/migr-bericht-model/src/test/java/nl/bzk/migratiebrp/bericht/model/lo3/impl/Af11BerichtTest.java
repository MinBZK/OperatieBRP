/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht.Foutreden;
import org.junit.Assert;
import org.junit.Test;

public class Af11BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String A_NUMMER = "9876543210";
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Af11Bericht bericht = new Af11Bericht();
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
        final Af11Bericht af11Bericht = new Af11Bericht();
        af11Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        af11Bericht.setANummer(A_NUMMER);
        af11Bericht.setBronPartijCode(BRON_GEMEENTE);
        af11Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        af11Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(af11Bericht);

        Assert.assertNull(af11Bericht.getStartCyclus());
        Assert.assertEquals(A_NUMMER, af11Bericht.getANummer());
        Assert.assertEquals(Foutreden.G, af11Bericht.getFoutreden());

        final Af11Bericht controleBericht = new Af11Bericht();
        controleBericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
        controleBericht.setANummer(A_NUMMER);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(af11Bericht.getMessageId());

        Assert.assertEquals(af11Bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(af11Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(af11Bericht.equals(af11Bericht));
        Assert.assertTrue(controleBericht.equals(af11Bericht));
        Assert.assertEquals(Collections.emptyList(), af11Bericht.getGerelateerdeAnummers());

        Assert.assertFalse(af11Bericht.equals(new Lq01Bericht()));

        af11Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, "0517");
        Assert.assertEquals("0517", af11Bericht.getGemeente());
    }
}
