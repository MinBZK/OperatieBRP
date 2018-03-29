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

public class Vb01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String MELDING = "Er is een fout opgetreden";
    private static final Integer LENGTE_MELDING = Integer.valueOf(25);
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0599";

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setMessageId(MessageIdGenerator.generateId());
        vb01Bericht.setBericht(MELDING);
        testFormatAndParseBericht(vb01Bericht);
    }

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setMessageId(MessageIdGenerator.generateId());
        vb01Bericht.setBericht("");
        testFormatAndParseBericht(vb01Bericht);
    }

    @Test
    public void testNull() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setMessageId(MessageIdGenerator.generateId());
        vb01Bericht.setBericht(null);
        testFormatAndParseBericht(vb01Bericht);
    }

    @Test
    public void testBerichtViaConstructor() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht(MELDING);
        vb01Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(vb01Bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setBronPartijCode(BRON_GEMEENTE);
        vb01Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        vb01Bericht.setBericht(MELDING);
        vb01Bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(vb01Bericht);

        Assert.assertEquals("Vb01", vb01Bericht.getBerichtType());
        Assert.assertEquals("uc501-gba", vb01Bericht.getStartCyclus());
        Assert.assertEquals(MELDING, vb01Bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
        Assert.assertEquals(String.format("%05d", LENGTE_MELDING), vb01Bericht.getHeaderWaarde(Lo3HeaderVeld.LENGTE_BERICHT));

        final Vb01Bericht controleBericht = new Vb01Bericht();
        controleBericht.setBericht(MELDING);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(vb01Bericht.getMessageId());

        Assert.assertTrue(vb01Bericht.equals(vb01Bericht));
        Assert.assertTrue(controleBericht.equals(vb01Bericht));

        Assert.assertFalse(vb01Bericht.equals(new Lq01Bericht()));
    }
}
